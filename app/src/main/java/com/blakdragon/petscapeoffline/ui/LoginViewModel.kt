package com.blakdragon.petscapeoffline.ui

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blakdragon.petscapeoffline.models.NetworkResult
import com.blakdragon.petscapeoffline.models.User
import com.blakdragon.petscapeoffline.core.network.NetworkInstance
import com.blakdragon.petscapeoffline.core.network.requests.GoogleLoginRequest
import com.blakdragon.petscapeoffline.core.network.requests.LoginRequest
import com.blakdragon.petscapeoffline.core.network.requests.RegisterRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val displayName = MutableLiveData("")

    val registering = MutableLiveData(false)
    val busy = MutableLiveData(false)
    val passwordVisible = MutableLiveData(false)

    val loginEnabled = MediatorLiveData<Boolean>()
    val registerEnabled = MediatorLiveData<Boolean>()

    val loginResult = MutableLiveData<NetworkResult<User>>()

    init {
        listOf(email, password).forEach {
            loginEnabled.addSource(it) { checkLoginEnabled() }
        }

        listOf(registering, email, password, displayName).forEach {
            registerEnabled.addSource(it) { checkRegisterEnabled() }
        }
    }

    fun togglePasswordVisibility() {
        passwordVisible.value = passwordVisible.value?.not()
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
            val response = NetworkInstance.API.register(
                RegisterRequest(
                email = email.value!!,
                password = password.value!!,
                displayName = displayName.value!!
            )
            )
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