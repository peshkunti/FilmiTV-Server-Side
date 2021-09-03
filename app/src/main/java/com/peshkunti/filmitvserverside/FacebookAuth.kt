package com.peshkunti.filmitvserverside
/*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@ExperimentalCoroutinesApi
private fun setupFacebookContinueButton() {
    binding?.buttonContinueFacebook?.setOnClickListener {
        beginLoginToFacebook()
        finishFacebookLogin { loginResult ->
            loginViewModel.loginFacebook(loginResult.accessToken.token)
        }
    }
}

private fun beginLoginToFacebook() {
    if (activity != null) {
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("email"))
    }
}

@ExperimentalCoroutinesApi
suspend fun getFacebookToken(): LoginResult =
    suspendCancellableCoroutine { continuation ->
        LoginManager.getInstance()
            .registerCallback(loginViewModel.callbackManager, object : FacebookCallback<LoginResult> {

                override fun onSuccess(loginResult: LoginResult) {
                    continuation.resume(loginResult){ }
                }

                override fun onCancel() {
                    // handling cancelled flow (probably don't need anything here)
                    continuation.cancel()
                }

                override fun onError(exception: FacebookException) {
                    // Facebook authorization error
                    continuation.resumeWithException(exception)
                }
            })
    }

@ExperimentalCoroutinesApi
fun FragmentActivity.finishFacebookLoginToThirdParty(
    onCredential: suspend (LoginResult) -> Unit
) {
    this.lifecycleScope.launchWhenStarted {
        try {
            val loginResult: LoginResult = getFacebookToken(loginViewModel.callbackManager)
            onCredential(loginResult)
        } catch (e: FacebookException) {
            Log.e("Facebook Error", e.toString())
        }
    }
}
*/
