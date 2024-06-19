package app.test.xyz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.databinding.ActivityMyCampaignDetailsBinding
import app.test.xyz.modal.SeletedMycampaignData
import app.test.xyz.modal.selectedHomeData

class MyCampaignDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyCampaignDetailsBinding
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMyCampaignDetailsBinding.inflate(layoutInflater)
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
        binding.textViewTestingLink.text= SeletedMycampaignData!!.app_name
        binding.textViewLink.text = SeletedMycampaignData!!.app_link

        binding.buttonJoinRequest.setOnClickListener {

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            // Set the selected image to the ImageView
            findViewById<ImageView>(R.id.iv_app_icon).setImageURI(imageUri)
        }
    }


}