package app.test.xyz

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.databinding.ActivityItemDetailsBinding
import app.test.xyz.modal.selectedHomeData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.test.xyz.TinyDB


class ItemDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding
    lateinit var rootView: View
    lateinit var email: String

    init {
        System.loadLibrary("keys")
    }

    external fun Hatbc(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
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
        rootView = findViewById<View>(android.R.id.content)

        binding.appName.text = selectedHomeData!!.app_name
        binding.uploaderName.text = selectedHomeData!!.uploader_name
        binding.instructions.text = selectedHomeData!!.instruction
        binding.tvComplete.text = selectedHomeData!!.test_complete
        binding.tvTotallimit.text = selectedHomeData!!.totallimit

        binding.testingLinkButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(selectedHomeData!!.app_link)))
        }
        binding.uploadTestingProofButton.setOnClickListener {
            showInstallPopup()
        }
        email = TinyDB.getString(this, "email_id", "").toString()
    }

    private fun showInstallPopup() {

        val customLayout = LayoutInflater.from(this).inflate(R.layout.popup_sent_request, null)

        val builder = AlertDialog.Builder(this, R.style.updateDialogTheme)
        builder.setView(customLayout)
        builder.setCancelable(true)

        val popupDialog = builder.create()
        popupDialog.show()

        val okaybtn = customLayout.findViewById<MaterialCardView>(R.id.sentRequest)
        val textt = customLayout.findViewById<EditText>(R.id.et_campaingn)

        okaybtn.setOnClickListener {
            if (textt.text.toString() == "") {

                val snackbar = Snackbar.make(rootView, "Enter Campaign Id !", Snackbar.LENGTH_LONG)
                snackbar.view.setBackgroundColor(Color.RED)
                snackbar.show()

            } else {
                val queue = Volley.newRequestQueue(this)

                val url =
                    com.test.xyz.Companion.siteUrl + "send_request.php?email=$email&campaign=${selectedHomeData?.c_id}&myCampaign=${textt.text}"

                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        if (response.contains("Successful")) {
                            popupDialog.dismiss()
                        }
                        Toast.makeText(this, "Your Request Send Successful !", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    { error ->

                    })
                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            }


        }

    }
}