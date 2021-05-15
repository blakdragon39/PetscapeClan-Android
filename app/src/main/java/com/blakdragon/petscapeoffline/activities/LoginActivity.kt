package com.blakdragon.petscapeoffline.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.transition.TransitionManager
import com.blakdragon.petscapeoffline.R
import com.blakdragon.petscapeoffline.databinding.ActivityLoginBinding
import com.blakdragon.petscapeoffline.models.NetworkResult
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.network.NetworkInstance
import com.blakdragon.petscapeoffline.network.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.network.requests.LoginRequest
import com.blakdragon.petscapeoffline.network.requests.RegisterRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.data != null) {
            handleGoogleSignInResult(result.data!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.bGoogleSignIn.setOnClickListener { googleSignIn() }
        binding.ivBack.setOnClickListener { viewModel.registering.value = false }

        viewModel.registering.observe(this, Observer { transitionRegister(it) })
        viewModel.loginResult.observe(this, Observer { result ->
            if (!result.handled) {
                if (result.isSuccessful) {
                    handleLogin(result.getUnhandledData())
                } else {
                    handleError(result.getUnhandledException())
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.registering.value = false
    }

    private fun googleSignIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("285206861319-4rssk6blmo670m0o5itqqllmf9dvuj8s.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(this, options)
        googleSignInResult.launch(client.signInIntent)
    }

    private fun handleGoogleSignInResult(data: Intent) = lifecycleScope.launch {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
            viewModel.googleLogin(account.idToken!!)
        } catch (e: ApiException) {
            if (e.statusCode != GoogleSignInStatusCodes.SIGN_IN_CANCELLED || e.statusCode != GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS) {
                showMessage("signInResult:failed code=${e.statusCode}")
            }
        }
    }

    private fun transitionRegister(registering: Boolean) {
        binding.bLogin.isVisible = !registering
        binding.etDisplayName.isVisible = registering

        val cs = ConstraintSet().apply {
            clone(binding.constraints)
        }

        if (registering) {
            cs.clear(binding.ivBack.id, ConstraintSet.END)
            cs.connect(binding.ivBack.id, ConstraintSet.START, binding.root.id, ConstraintSet.START, resources.getDimensionPixelOffset(R.dimen.margin))
        } else {
            cs.clear(binding.ivBack.id, ConstraintSet.START)
            cs.connect(binding.ivBack.id, ConstraintSet.END, binding.root.id, ConstraintSet.START)
        }

        TransitionManager.beginDelayedTransition(binding.constraints)
        cs.applyTo(binding.constraints)
    }

    private fun handleLogin(user: User) {
        Timber.tag("USER").i(user.displayName)
        Timber.tag("USER").i(user.token)
    }
}

class LoginViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val displayName = MutableLiveData("")

    val registering = MutableLiveData(false)
    val busy = MutableLiveData(false)

    val loginEnabled = MediatorLiveData<Boolean>()
    val registerEnabled = MediatorLiveData<Boolean>()

    val loginResult = MutableLiveData<NetworkResult<User>>()

    //todo loading state

    init {
        listOf(email, password).forEach {
            loginEnabled.addSource(it) { checkLoginEnabled() }
        }

        listOf(registering, email, password, displayName).forEach {
            registerEnabled.addSource(it) { checkRegisterEnabled() }
        }
    }

    fun login() = viewModelScope.launch {
        busy.value = true

        try {
            val response = NetworkInstance.API.login(LoginRequest(email = email.value!!, password = password.value!!))
            loginResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            loginResult.value = NetworkResult(exception = e)
        }

        busy.value = false
    }

    fun googleLogin(idToken: String) = viewModelScope.launch {
        busy.value = true

        try {
            val response = NetworkInstance.API.googleLogin(GoogleLoginRequest(idToken))
            loginResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            loginResult.value = NetworkResult(exception = e)
        }

        busy.value = false
    }

    fun checkRegistering() {
        if (registering.value == true) {
            register()
        } else {
            registering.value = true
            checkRegisterEnabled()
        }
    }

    private fun register() = viewModelScope.launch {
        busy.value = true

        try {
            val response = NetworkInstance.API.register(RegisterRequest(
                email = email.value!!,
                password = password.value!!,
                displayName = displayName.value!!
            ))
            loginResult.value = NetworkResult(data = response)
        } catch (e: Exception) {
            loginResult.value = NetworkResult(exception = e)
        }

        busy.value = false
    }

    private fun checkLoginEnabled() {
        loginEnabled.value = email.value.isNullOrEmpty().not() && password.value.isNullOrEmpty().not()
    }

    private fun checkRegisterEnabled() {
        registerEnabled.value = registering.value == false ||
                (
                    email.value.isNullOrEmpty().not() &&
                    password.value.isNullOrEmpty().not() &&
                    displayName.value.isNullOrEmpty().not()
                )
    }
}