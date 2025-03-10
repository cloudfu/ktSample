package com.example.ktsample.ui.component.login

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.ktsample.sample.coroutine.testSimpleEvent
import com.example.ktsample.data.OAUTH_CLIENT_ID
import com.example.ktsample.data.OAUTH_REDIRECT_URI
import com.example.ktsample.data.OAUTH_USER_SCOPE
import com.example.ktsample.databinding.ActivityLoginBinding
import com.example.ktsample.sample.coroutine.testCancelFlow
import com.example.ktsample.ui.component.engine.EngineViewModel
import com.example.ktsample.ui.base.BaseActivity
import com.example.ktsample.ui.component.list.ListPokemonViewModel
import com.example.ktsample.ui.component.list.RecyclerViewActivity
import com.example.ktsample.utils.NetworkMonitor
import com.example.ktsample.utils.generateRandomString
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity @Inject constructor(): BaseActivity() {

    private val TAG = "LoginActivity"

//    private val mLoginViewModel: LoginViewModel by lazy{
//        ViewModelProvider(this)[LoginViewModel::class.java]
//    }

    /***
     * 需要在依赖注入的ViewModel添加@HiltViewModel
     * 才能在Activity 中使用 val mLoginViewModel: LoginViewModel by viewModels()
     * */
    private val mLoginViewModel: LoginViewModel by viewModels()
    private val mListPokemonViewModel: ListPokemonViewModel by viewModels()


    /***
     * 需要在依赖注入的ViewModel添加//@ActivityRetainedScoped
     * 才能在Activity 中使用
     * @Inject
     * lateinit var mLoginViewModel: LoginViewModel
     * */
//    @Inject
//    lateinit var mLoginViewModel: LoginViewModel

    private lateinit var mBinding : ActivityLoginBinding

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var engineViewModel: EngineViewModel

    override fun observeViewModel() {
        // TODO: Observer()
        mLoginViewModel.loginResponse.observe(this, Observer() {
            Log.i(TAG, it.toString())
        })

        mLoginViewModel.cityList.observe(
            this
        ) { it ->
            if (it.state.isSucceed()) {

                for (city in it.data?.cities!!) {
                    Log.i(TAG, "${it.source}: $city")
                }
            } else if (it.state.isLoading()) {
                Log.i(TAG, it.state.getStateMessage())
            }
        }

        networkMonitor.observe(this){
            Log.i(TAG,"network:$it.toString()")
        }
    }

    override fun initViewBinding() {
        testSimpleEvent()
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initEvent()
//        engineViewModel.deliver()
    }

    private fun initEvent(){
        mBinding.btnLogin.setOnClickListener{
            mListPokemonViewModel.initDatabase(this)
//            mListPokemonViewModel.fetchNextPokemonList()
//            mListPokemonViewModel.fetchNextPokemonLst()

//            this.startActivity(Intent(this, RecyclerViewActivity::class.java))

//            requestTokenCode();
//            val userName: String = mBinding.txtUserName.text.trim().toString();
//            val userPwd: String = mBinding.txtUserPwd.text.trim().toString()
//            mLoginViewModel.doLogin(userName, userPwd)
//            mLoginViewModel.getAuthToken()
        }
    }

    private fun requestTokenCode(){
        val state = generateRandomString(5)
        val OAUTH_URL = "https://github.com/login/oauth/authorize?" +
                "client_id=$OAUTH_CLIENT_ID" +
                "&redirect_uri=$OAUTH_REDIRECT_URI" +
                "&scope=$OAUTH_USER_SCOPE" +
                "&state=$state"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(OAUTH_URL))
        startActivity(intent)
    }

}