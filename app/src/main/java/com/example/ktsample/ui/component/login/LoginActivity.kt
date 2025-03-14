package com.example.ktsample.ui.component.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.ktsample.R
import com.example.ktsample.data.Const.OAUTH_CLIENT_ID
import com.example.ktsample.data.Const.OAUTH_REDIRECT_URI
import com.example.ktsample.data.Const.OAUTH_USER_SCOPE
import com.example.ktsample.databinding.ActivityLoginBinding
import com.example.ktsample.ui.base.BindingActivity
import com.example.ktsample.ui.component.pokemon.PokemonListActivity
import com.example.ktsample.utils.genRandomString
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity @Inject constructor(): BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val TAG = "LoginActivity"

    /***
     * 需要在依赖注入的ViewModel添加//@ActivityRetainedScoped
     * 才能在Activity 中使用
     * @Inject
     * lateinit var mLoginViewModel: LoginViewModel
     * */
//    @Inject
//    lateinit var mLoginViewModel: LoginViewModel

    /***
     * 需要在依赖注入的ViewModel添加@HiltViewModel
     * 才能在Activity 中使用 val mLoginViewModel: LoginViewModel by viewModels()
     * */
    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.init()
    }

    private fun init(){
        binding {
            btnLogin.setOnClickListener(View.OnClickListener {

                /***
                    github OAuth Setting
                    github/setting/Developer settings/OAuth App
                    Authorization callback URL: ktsample://getCallbackCode
                    val OAUTH_REDIRECT_URI = "ktsample://getCallbackCode"
                    <activity
                        android:name=".ui.component.login.PortalActivity"
                        android:exported="true">
                        <intent-filter>
                            <action android:name="android.intent.action.VIEW" />

                            <category android:name="android.intent.category.DEFAULT" />
                            <category android:name="android.intent.category.BROWSABLE" />
                            <!-- github/setting/Developer settings/OAuth App  -->
                            <!-- Authorization callback URL: ktsample://getCallbackCode -->
                            <data
                                android:host="getCallbackCode"
                                android:scheme="ktsample" />
                        </intent-filter>
                    </activity>
                 */

                startActivity(Intent(it.context, PokemonListActivity::class.java))


//                val githubOAthUri = mViewModel.getGithubOAuthUrl(
//                    OAUTH_CLIENT_ID, OAUTH_REDIRECT_URI, OAUTH_USER_SCOPE, genRandomString(5)
//                )
//                Timber.d("get github oauth uri: $githubOAthUri")
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubOAthUri))
//                startActivity(intent)
            })
        }
    }
}