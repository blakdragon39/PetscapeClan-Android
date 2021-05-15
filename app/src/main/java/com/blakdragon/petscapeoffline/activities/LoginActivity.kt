package com.blakdragon.petscapeoffline.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.transition.TransitionManager
import com.blakdragon.petscapeoffline.R
import com.blakdragon.petscapeoffline.databinding.ActivityLoginBinding
import com.blakdragon.petscapeoffline.models.User
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
        viewModel.passwordVisible.observe(this, Observer { visible -> changePasswordInputType(visible) })

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
        if (viewModel.registering.value == true) {
            viewModel.registering.value = false
        } else {
            super.onBackPressed()
        }
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

    private fun changePasswordInputType(passwordVisible: Boolean) {
        val position = binding.etPassword.selectionStart
        binding.etPassword.transformationMethod = if (passwordVisible) null else PasswordTransformationMethod()
        binding.etPassword.setSelection(position)
    }

    private fun handleLogin(user: User) {
        Timber.tag("USER").i(user.displayName)
        Timber.tag("USER").i(user.token)
    }
}