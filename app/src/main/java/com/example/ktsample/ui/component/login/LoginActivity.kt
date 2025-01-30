package com.example.ktsample.ui.component.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.databinding.ActivityLoginBinding
import com.example.ktsample.di.EngineViewModel
import com.example.ktsample.di.Truck
import com.example.ktsample.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity @Inject constructor(): BaseActivity() {

    private val TAG = "LoginActivity"

    private val mLoginViewModel: LoginViewModel by viewModels()
//    private val mLoginViewModel: LoginViewModel by lazy{
//        ViewModelProvider(this)[LoginViewModel::class.java]
//    }

    private lateinit var mBinding : ActivityLoginBinding

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
            if (it.isSucceed()) {

                for (city in it.data?.cities!!) {
                    Log.i(TAG, "${it.source}: $city")
                }
            } else if (it.isLoading()) {
                Log.i(TAG, it.state.getStateMessage())
            }
        }
    }

    override fun initViewBinding() {
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initEvent()
//        engineViewModel.deliver()
    }

    private fun initEvent(){
        mBinding.btnLogin.setOnClickListener{
            val userName: String = mBinding.txtUserName.text.trim().toString();
            val userPwd: String = mBinding.txtUserPwd.text.trim().toString()
            mLoginViewModel.doLogin(userName, userPwd)
        }
    }

}