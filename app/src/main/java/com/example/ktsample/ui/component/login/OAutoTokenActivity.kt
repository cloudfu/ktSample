package com.example.ktsample.ui.component.login

import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import com.example.ktsample.data.OAUTH_CLIENT_ID
import com.example.ktsample.data.OAUTH_CLIENT_SECRET
import com.example.ktsample.data.OAUTH_REDIRECT_URI
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.databinding.ActivityOauthTokenBinding
import com.example.ktsample.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OAutoTokenActivity @Inject constructor(): BaseActivity() {

    private lateinit var mBinding: ActivityOauthTokenBinding
    private val mLoginViewModel: LoginViewModel by viewModels()

    override fun observeViewModel() {
    }

    override fun initViewBinding() {
        mBinding = ActivityOauthTokenBinding.inflate(layoutInflater)
        val uri: Uri? = intent.data
        if (uri != null) {
            // 提取授权码
            val code = uri.getQueryParameter("code")
            if (code != null && code != "") {
                Log.i("TAG", code)
                mLoginViewModel.getAuthToken(OAuthTokenRequest(OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, code, OAUTH_REDIRECT_URI))
            }
        }
    }


}