package com.example.ktsample.ui.component.login

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.ktsample.R
import com.example.ktsample.data.Const.OAUTH_CLIENT_ID
import com.example.ktsample.data.Const.OAUTH_CLIENT_SECRET
import com.example.ktsample.data.Const.OAUTH_REDIRECT_URI
import com.example.ktsample.data.Const.PORTAL_LIST
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.databinding.ActivityPortalBinding
import com.example.ktsample.ui.adatper.PortalAdapter
import com.example.ktsample.ui.base.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PortalActivity @Inject constructor(): BindingActivity<ActivityPortalBinding>(R.layout.activity_portal) {

    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private fun init(){
        val uri: Uri? = intent.data
        val code = uri?.getQueryParameter("code")
        code.let {
            if(!it.isNullOrEmpty()){
                Timber.d("github oauth code:$it")
                mViewModel.getAuthToken(OAuthTokenRequest(OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, it, OAUTH_REDIRECT_URI))
            }
        }

        binding{
            val adapter = PortalAdapter()
            listPortal.adapter = adapter
            adapter.submitList(PORTAL_LIST)
        }
        observer()
    }

    private fun observer() {
        mViewModel.mOAuthTokenResponse.observe(this){
            if(it.state.isLoading()){
                mBinding.layoutLoading.visibility = View.VISIBLE
                Timber.d("Loading...")
            }
            else{
                mBinding.labUserToken.text = it.data?.accessToken
                mBinding.layoutLoading.visibility = View.GONE
                Timber.d("finish...")
            }
        }
    }
}