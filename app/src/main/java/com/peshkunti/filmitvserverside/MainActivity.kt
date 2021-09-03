package com.peshkunti.filmitvserverside

//import com.google.firebase.firestore.FirebaseFirestore

import android.app.Activity
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.peshkunti.filmitvserverside.ui.theme.FilmiTVServerSideTheme
import java.util.*

lateinit var firebaseAuth: FirebaseAuth
const val TAG = "SignInActivity"
const val REQUEST_CODE_GOOGLE_SIGN_IN = 1 /* unique request id */

class MainActivity : ComponentActivity() {
/*
    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    private var someActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data = result.data
                doSomeOperations()
            }
        })

    private fun doSomeOperations() {
        Log.d(TAG, "doSomeOperations: OK")
    }

    private fun openSomeActivityForResult() {
        val intent = Intent(this, SomeActivity::class.java)
        someActivityResultLauncher.launch(intent)
    }
*/

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val clientId = stringResource(id = R.string.client_id_web)

/*
            val composableScope = rememberCoroutineScope()
            val context = LocalContext.current
            val activity = LocalContext.current as Activity
            val loginResultHandler: ActivityResultLauncher<IntentSenderRequest> =
                registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult? ->
                }
*/

            FilmiTVServerSideTheme {
                Surface() {
                    SignIn()

                    /*
                        val assetManager: AssetManager = this.assets
                        val isUserLoginIn = mutableStateOf<Boolean>(true)
                        firebaseAuth = FirebaseAuth.getInstance()

                        val mUser = firebaseAuth.currentUser
                        mUser?.run {
                            getIdToken(true)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val idToken: String? = task.result.token
                                        Log.d(TAG, "onCreate: idToken = $idToken")
                                    } else {
                                        Log.e(
                                            TAG,
                                            "onCreate: Error ${task.exception!!.localizedMessage}",
                                            task.exception!!.cause
                                        )
                                    }
                                }
                        } ?: Log.d(TAG, "onCreate: firebaseAuth.currentUser = null")

                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("975201646073-ps56i1ra6bnn8jcvggmvn8kqao5lqko9.apps.googleusercontent.com")
                            .requestEmail()
                            .build()

                        var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)
                    UserLogin(
                        isUserLoginIn = isUserLoginIn,
                        firebaseAuth = firebaseAuth,
                    )
                    LazyColumn {
                        item {
                            Text(text = "erfgerg", color = MaterialTheme.colors.primary)
                            AddEvents()
                            LoadAssets(assetManager)
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GoogleButton {
                            delay(2000L)
                            false
                        }
                    }
*/
                }
            }
        }
    }
}

@Composable
fun SignIn() {
    val context = LocalContext.current
    val signInClient = Identity.getSignInClient(context)
    val beginSignInRequest: BeginSignInRequest =
        BeginSignInRequest
            .builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest
                    .GoogleIdTokenRequestOptions
                    .builder()
                    .setSupported(true)
                    .setServerClientId(stringResource(id = R.string.client_id_web))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

    val beginSignIn: Task<BeginSignInResult> =
        signInClient
            .beginSignIn(beginSignInRequest)

    beginSignIn
        .addOnSuccessListener { beginSignInResult: BeginSignInResult? ->
            try {
                startIntentSenderForResult(
                    context as Activity,
                    beginSignInResult!!.pendingIntent.intentSender,
                    REQUEST_CODE_GOOGLE_SIGN_IN,
                    null,
                    0,
                    0,
                    0,
                    null
                )
                Log.d(TAG, "SignIn: ")
            } catch (e: IntentSender.SendIntentException) {
                Log.e(TAG, "Google Sign-in failed")
            }
        }
        .addOnFailureListener { e: Exception? ->
            Log.e(TAG, "Google Sign-in failed", e)
        }

    /*
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
                    try {
                        val credential =
                            Identity.getSignInClient(this).getSignInCredentialFromIntent(data)
                        // Signed in successfully - show authenticated UI
                        updateUI(credential)
                    } catch (e: ApiException) {
                        // The ApiException status code indicates the detailed failure reason.
                    }
                }
            }
        }

    */

    fun updateUI(credential: SignInCredential) {
        Log.d(TAG, "updateUI: $credential")
    }
}

/*
    private val loginResultHandler: ActivityResultLauncher<IntentSenderRequest> =
        registerForActivityResult(StartIntentSenderForResult()) { result: ActivityResult? ->

        }
*/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEvents() {
    val firestore = Firebase.firestore

//    val tz: ZoneId = ZoneId.systemDefault()
//    val localDateTime: LocalDateTime = LocalDateTime.now()
//
//    // convert LocalDateTime to Date
//    val date = Date.from(localDateTime.atZone(tz).toInstant())
//
//    // convert LocalDateTime to Timestamp
//    val seconds = localDateTime.atZone(tz).toEpochSecond()
//    val nanos = localDateTime.nano
//    val timestamp = com.google.firebase.Timestamp(seconds, nanos)

    val eventDTO: EventDTO = EventDTO(
        dataTime = Timestamp.now(),
        channel = Channel.BNT.toString(),
        fresh = true,
        presentation = "Това е проба 3"
    )
    val docData: HashMap<String, *> = hashMapOf(
        "dataTime" to eventDTO.dataTime,
        "channel" to eventDTO.channel,
        "fresh" to eventDTO.fresh,
        "presentation" to eventDTO.presentation
    )
    firestore
        .collection("events")
        .document("${eventDTO.dataTime?.seconds}_${eventDTO.channel}")
        .set(docData)
        .addOnSuccessListener {
            Log.d(
                TAG,
                "DocumentSnapshot successfully written!"
            )
        }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
}

