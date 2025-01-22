package com.example.ktsample.ui.component.login

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.databinding.ActivityLoginBinding
import com.example.ktsample.di.Truck
import com.example.ktsample.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val TAG = "LoginActivity"
    private val mLoginViewModel: LoginViewModel by viewModels()
    private lateinit var mBinding : ActivityLoginBinding

    @Inject
    lateinit var track: Truck

    override fun observeViewModel() {
        // TODO: Observer()
        mLoginViewModel.loginResponse.observe(this, Observer() {
            Log.i(TAG, it.toString())
        })
    }

    override fun initViewBinding() {
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initEvent()
        track.deliver()
    }

    private fun initEvent(){
        mBinding.btnLogin.setOnClickListener{
            val userName: String = mBinding.txtUserName.text.trim().toString();
            val userPwd: String = mBinding.txtUserPwd.text.trim().toString()
            mLoginViewModel.doLogin(userName, userPwd)
        }
    }

}