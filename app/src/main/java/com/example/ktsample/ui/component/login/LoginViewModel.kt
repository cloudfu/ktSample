package com.example.ktsample.ui.component.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ktsample.data.DataRepository
import com.example.ktsample.data.api.ApiService
import com.example.ktsample.data.login.LoginRequest
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get()= _loginResponse

    private val apiService: ApiService by lazy {
        DataRepository().getRemoteService(ApiService::class.java)
    }

    fun doLogin(userName: String, userPwd: String) {
        viewModelScope.launch {
            apiService.getCities().also {
                for(item in it){
                    Log.i("getCities", item.toString())
                }
            }
        }

//        var loginRequest = LoginRequest(userName, userPwd)
//        val loginResponse = LoginResponse("001", "cloud", "hua", "shanghai")
//        _loginResponse.value = loginResponse
//        apiService.getCities();
//    }
    }

}