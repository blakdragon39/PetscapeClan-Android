package com.blakdragon.petscapeoffline

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.blakdragon.petscapeoffline.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val googleSignInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            handleGoogleSignInResult(result.data!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.bGoogleSignIn.setOnClickListener { googleSignIn() }
    }

    private fun googleSignIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        val client = GoogleSignIn.getClient(this, options)
        googleSignInResult.launch(client.signInIntent)
    }

    private fun handleGoogleSignInResult(data: Intent) = lifecycleScope.launch {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
            Timber.tag("OFFLINE").i(account.displayName)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}

class LoginViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val displayName = MutableLiveData("")

    val registering = MutableLiveData(false)

    val loginEnabled = MediatorLiveData<Boolean>()
    val registerEnabled = MediatorLiveData<Boolean>()

    init {
        loginEnabled.addSource(email) { checkLoginEnabled() }
        loginEnabled.addSource(password) { checkLoginEnabled() }

        registerEnabled.addSource(email) { checkRegisterEnabled() }
        registerEnabled.addSource(password)  { checkRegisterEnabled() }
        registerEnabled.addSource(displayName)  { checkRegisterEnabled() }
    }

    fun login() = viewModelScope.launch {

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