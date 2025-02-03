package com.example.ktsample.ui.component.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ktsample.data.repository.DataRepository
import com.example.ktsample.data.city.CityList
import com.example.ktsample.data.login.LoginResponse
import com.example.ktsample.data.login.OAuthTokenRequest
import com.example.ktsample.data.login.OAuthTokenResponse
import com.example.ktsample.data.repository.ResultPackage
import com.example.ktsample.ui.component.engine.EngineViewModel
import com.example.ktsample.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//@ActivityRetainedScoped
class LoginViewModel @Inject constructor(private val dataRepository: DataRepository): BaseViewModel() {

//    private val viewModelJob = SupervisorJob()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val TAG = "LoginViewModel"

    private var _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get()= _loginResponse

    private var _cityList = MutableLiveData<ResultPackage<CityList>>()
    val cityList: LiveData<ResultPackage<CityList>> get() = _cityList

    private var _oAuthToken = MutableLiveData<ResultPackage<OAuthTokenResponse>>()
    val mOAuthTokenResponse: LiveData<ResultPackage<OAuthTokenResponse>>
        get()= _oAuthToken

    @Inject
    lateinit var engineViewModel: EngineViewModel

//    private val remoteService: ApiService by lazy {
//        dataRepository.getRemoteService(ApiService::class.java)
//    }

    fun doLogin(userName: String, userPwd: String) {
        engineViewModel.deliver()
        viewModelScope.launch {
            dataRepository.getCities().collect {
                Log.i(TAG, "${it.state}: $it")
                _cityList.value = it
            }
        }
    }

    fun getAuthToken(codeTokenRequest: OAuthTokenRequest){
        viewModelScope.launch {
            dataRepository.getOAuthToken(codeTokenRequest).collect {
                _oAuthToken.value = it
                if(!it.state.isLoading()){
                    Log.i(TAG, it.data.toString())
                }else{
                    Log.i(TAG, "数据加载中...")
                }
            }
        }
    }
}