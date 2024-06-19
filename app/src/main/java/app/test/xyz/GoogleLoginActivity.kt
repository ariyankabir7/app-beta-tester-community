package app.test.xyz

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.databinding.ActivityGoogleLoginBinding
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.recaptcha.RecaptchaClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.test.xyz.Companion
import com.test.xyz.TinyDB
import com.test.xyz.Utils
import com.wn.agecalculator.extrazz.videoplayyer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Base64
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class GoogleLoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityGoogleLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    val Req_Code: Int = 123
    private lateinit var recaptchaClient: RecaptchaClient
    init {
        System.loadLibrary("keys")
    }

    external fun Hatbc(): String
    external fun HatMc(): String
    external fun HatGy(): String
    external fun HatTy(): String


    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityGoogleLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val insetsController = ViewCompat.getWindowInsetsController(v)
            insetsController?.isAppearanceLightStatusBars = true
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Hatbc())
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.cvGoogleLoginBtn.setOnClickListener {
            loginUser()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        Utils.showLoadingPopUp(this@GoogleLoginActivity)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val amarBal = if ((account.id?.length ?: 0) < 32) {
                    account.id + account.email!!.substring(0, 32 - account.id!!.length)
                } else {
                    ""
                }
                TinyDB.saveString(this@GoogleLoginActivity, "love", amarBal)
                login(
                    account.idToken.toString(),
                    account.email.toString(),
                    account.displayName.toString(),
                    account.photoUrl.toString(),
                    "not needed now"
                )
                Utils.dismissLoadingPopUp()

            } else {
                Utils.dismissLoadingPopUp()
                Toast.makeText(this, task.result.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(
        token: String,
        accountName: String,
        name: String,
        profile: String,
        ctoken: String
    ) {
        val url = "${com.test.xyz.Companion.siteUrl}googlelogin.php"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val deviceid: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val time = System.currentTimeMillis()

        val stringRequest = @RequiresApi(Build.VERSION_CODES.O)
        object : StringRequest(
            Method.POST, url,
            { response ->
                Utils.dismissLoadingPopUp()
                if (response.contains("Try A")) {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                    val gso =
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(Hatbc())
                            .requestEmail()
                            .build()
                    GoogleSignIn.getClient(this@GoogleLoginActivity, gso).signOut()

                } else {
                    val ytes = Base64.getDecoder().decode(response)

                    val decng = String(ytes, Charsets.UTF_8)

                    val b1 = videoplayyer.decrypt(
                        decng,
                        TinyDB.getString(this@GoogleLoginActivity, "love", "")!!
                    )
                    val ytes2 = Base64.getDecoder()
                        .decode(b1)

                    val b2 = String(ytes2, Charsets.UTF_8)

                    if (b2.contains("Already Ac :")) {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(Hatbc())
                                .requestEmail()
                                .build()
                        GoogleSignIn.getClient(this@GoogleLoginActivity, gso).signOut()
                        Toast.makeText(
                            this@GoogleLoginActivity,
                            b2,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                    } else if (b2.contains("Successful")) {
                        TinyDB.saveBoolean(this@GoogleLoginActivity, "isLogin", true)
                        val signupData = b2.split(",")
                        var yourString = signupData[0]
                        yourString = yourString.substring(0, yourString.length - HatTy().toInt())
                        Toast.makeText(
                            this@GoogleLoginActivity,
                            signupData[0],
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        TinyDB.saveString(this@GoogleLoginActivity, "email", yourString)
                        TinyDB.saveString(this@GoogleLoginActivity, "email_id", accountName)
                        TinyDB.saveString(this@GoogleLoginActivity, "status", "login")
                        TinyDB.saveString(this@GoogleLoginActivity, "name", name)
                        TinyDB.saveString(this@GoogleLoginActivity, "profile", profile)
                        startActivity(
                            Intent(
                                this@GoogleLoginActivity,
                                HomeActivity::class.java
                            )
                        )
                        finish()
                    } else {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(Hatbc())
                                .requestEmail()
                                .build()
                        GoogleSignIn.getClient(this@GoogleLoginActivity, gso).signOut()
                        Toast.makeText(
                            this@GoogleLoginActivity,
                            b2,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }


            },
            Response.ErrorListener { error ->
                Utils.dismissLoadingPopUp()
                Toast.makeText(this, "Internet Slow", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                val dbit32 = videoplayyer.encrypt(deviceid, HatGy()).toString()
                val tbit32 =
                    videoplayyer.encrypt(time.toString(), HatGy()).toString()
                val token32 =
                    videoplayyer.encrypt(token, HatGy())
                        .toString()
                val embit32 =
                    videoplayyer.encrypt(ctoken, HatGy())
                        .toString()

                val den64 =
                    Base64.getEncoder().encodeToString(dbit32.toByteArray())
                val ten64 =
                    Base64.getEncoder().encodeToString(tbit32.toByteArray())
                val emen64 =
                    Base64.getEncoder().encodeToString(embit32.toByteArray())
                val token64 =
                    Base64.getEncoder().encodeToString(token32.toByteArray())

                val encodemap: MutableMap<String, String> = HashMap()
                encodemap["deijvfijvmfhvfvhfbhbchbfybebd"] = den64
                encodemap["waofhfuisgdtdrefssfgsgsgdhddgd"] = ten64
                encodemap["fdvbdfbhbrthyjsafewwt5yt5"] = token64
                encodemap["sdhkoutewevjpoyrewwgioknbvfdfio"] = emen64


                val jason = Json.encodeToString(encodemap)

                val den264 =
                    Base64.getEncoder().encodeToString(jason.toByteArray())

                val final =
                    URLEncoder.encode(den264, StandardCharsets.UTF_8.toString())

                params["dase"] = final


                return params
            }
        }
        queue.add(stringRequest)
    }


    private fun loginUser() {
        if (!isSecure(this)) {
            finish()
        }
        Toast.makeText(this@GoogleLoginActivity, "Login with your Email ID", Toast.LENGTH_SHORT)
            .show()
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)

    }

    fun isSecure(context: Context): Boolean {
        // Check if the device is using a VPN
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        val vpnInfo = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false


        // Return true if none of the conditions are met
        return !(vpnInfo)
    }
}
