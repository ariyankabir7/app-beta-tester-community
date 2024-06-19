import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.test.xyz.databinding.FragmentSettingsBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.test.xyz.Companion
import com.test.xyz.TinyDB

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
lateinit var email:String
lateinit var name:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        email = TinyDB.getString(requireContext(), "email_id","").toString()
        name = TinyDB.getString(requireContext(), "name","").toString()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUserData()

        binding.cvYoutube.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=bzSTpdcs-EI")
                )
            )
        }
        binding.cvWhatsapp.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
                setPackage("com.whatsapp")
            }
            startActivity(sendIntent)
        }
        binding.cvTelegram.setOnClickListener {
            val telegram = Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/InfotechAvl_bot"))
            startActivity(telegram)
        }
        binding.cvInstagram.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")
                )
            )
        }
        binding.cvDev.setOnClickListener {
            val google = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(google)
        }

    }

    private fun fetchUserData() {
        val url = "${Companion.siteUrl}user_value.php?email=$email"
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                if (response.contains(",")) {
                    val allData = response.trim().split(",")
                    val name = allData[0]
                    val email = allData[1]

                    // Process the data as needed
                    binding.tvUserName.text = name
                    binding.tvEmail.text = email
                }
            },
            { error ->
                // Handle errors here
            }
        )

        queue.add(stringRequest)
    }
}
