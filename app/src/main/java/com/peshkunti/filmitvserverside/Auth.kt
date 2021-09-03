package com.peshkunti.filmitvserverside
/*

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GithubAuthProvider

@Composable
fun UserLogin(isUserLoginIn: MutableState<Boolean>, firebaseAuth: FirebaseAuth) {
    val idToken = stringResource(R.string.client_id_web)
    val context: Context = LocalContext.current
    val icon: Int = R.drawable.ic_google_logo
    
    val request: GetSignInIntentRequest = GetSignInIntentRequest
        .builder()
        .setServerClientId(idToken)
        .build()

    val oneTabClient: Task<PendingIntent> = remember { 
        Identity
            .getSignInClient(context) 
            .getSignInIntent(request) 
    }

    val signInRequest: BeginSignInRequest = remember {
        BeginSignInRequest
            .builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest
                    .GoogleIdTokenRequestOptions
                    .builder()
                    .setSupported(true)
                    .setServerClientId(idToken)
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    val signUpRequest: BeginSignInRequest = remember {
        BeginSignInRequest
            .builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest
                    .GoogleIdTokenRequestOptions
                    .builder()
                    .setSupported(true)
                    .setServerClientId(idToken)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    Box() {
        Column {
            Button(
                onClick = {
                    AppCompatActivity()
                        .oneTabGoogleSignIn(
                            oneTabClient,
                            signInRequest,
                            signUpRequest,
                            firebaseAuth,
                            isUserLoginIn
                        )
                },
                modifier = Modifier
                    .padding(34.dp)

            ) {
                Text(text = "Sign in Google ...")
            }
        }
    }
}

fun AppCompatActivity.oneTabGoogleSignIn(
    oneTabClient: Task<PendingIntent>,
    signInRequest: BeginSignInRequest,
    signUpRequest: BeginSignInRequest,
    firebaseAuth: FirebaseAuth,
    userLoginInState: MutableState<Boolean>
) {
    oneTabClient
        .addOnSuccessListener(this) { result: PendingIntent ->
            performAuthentication(
                oneTabClient,
                signInRequest,
                firebaseAuth,
                userLoginInState,
            )
        }
        .addOnFailureListener(this) { exception: Exception ->
            oneTabGoogleSignUp(
                oneTabClient,
                signUpRequest,
                firebaseAuth,
                userLoginInState,
            )
            Log.d(TAG, "oneTabGoogleSignIn: Unable to login: ${exception.localizedMessage}")
        }
}

fun AppCompatActivity.oneTabGoogleSignUp(
    oneTabClient: Task<PendingIntent>,
    signUpRequest: BeginSignInRequest,
    firebaseAuth: FirebaseAuth,
    userLoginInState: MutableState<Boolean>
) {
    oneTabClient
        .addOnSuccessListener(this) { result: PendingIntent ->
            performAuthentication(
                oneTabClient = oneTabClient,
                result = result,
                firebaseAuth = firebaseAuth,
                userLoginInState = userLoginInState
            )
        }
        .addOnFailureListener(this) { e: Exception ->
            Log.e(
                TAG,
                "oneTabGoogleSignUp: Error while creating account ${e.localizedMessage}",
                e.cause
            )
        }
}

fun AppCompatActivity.performAuthentication(
    oneTabClient: Task<PendingIntent>,
    result: BeginSignInRequest,
    firebaseAuth: FirebaseAuth,
    userLoginInState: MutableState<Boolean>
) {
    try {
        val startForResult: ActivityResultLauncher<IntentSenderRequest> =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult: ActivityResult? ->
                if (activityResult?.resultCode == AppCompatActivity.RESULT_OK) {
                    try {
                        val credentials: SignInCredential =
                            oneTabClient.getSignInCredentialFromIntent(activityResult.data)
                        val idToken: String? = credentials.googleIdToken
                        loginWithFirebase(idToken, firebaseAuth, userLoginInState)
                    } catch (apiException: ApiException) {
                        when (apiException.statusCode) {
                            CommonStatusCodes.CANCELED -> {
                                Log.d(TAG, "performAuthentication: One-tap dialog was closed.")
                            }
                            CommonStatusCodes.NETWORK_ERROR -> {
                                Toast.makeText(this, "Network error!", Toast.LENGTH_LONG).show()
                                Log.d(TAG, "performAuthentication: Unable to login")
                            }
                        }
                    }
                }
            }
        startForResult.launch(
            IntentSenderRequest
                .Builder(result!!.pendingIntent.intentSender)
                .build()
        )
    } catch (e: IntentSender.SendIntentException) {
        Log.e(
            TAG,
            "performAuthentication: Couldn't start One Tap UI:  ${e.localizedMessage}",
            e.cause
        )
    }
}

fun loginWithFirebase(
    idToken: String?,
    firebaseAuth: FirebaseAuth,
    userLoginInState: MutableState<Boolean>
) {
    val credential = GithubAuthProvider.getCredential(idToken!!)
    firebaseAuth
        .signInWithCredential(credential)
        .addOnCompleteListener { result: Task<AuthResult> ->
            if (result.isSuccessful) {
                userLoginInState.value = true
                Log.d(TAG, "loginWithFirebase: Successful")
            } else
                Log.d(TAG, "loginWithFirebase: Error ${result.exception}")
        }
}
*/
