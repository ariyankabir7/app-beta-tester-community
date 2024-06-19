package app.test.xyz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import app.test.xyz.adapter.ViewPagerAdapter
import app.test.xyz.databinding.ActivityHomeBinding
import app.test.xyz.fragment.HomeFragment
import app.test.xyz.fragment.JoinFragment
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private var mViewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
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
        inVisiableUnderLine()
        binding.llHomeUnder.visibility = View.VISIBLE

        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = mViewPagerAdapter

        // Set up the ImageView click listeners
        binding.navHome.setOnClickListener {
            binding.viewPager.currentItem = 0
            binding.llHomeUnder.visibility
        }

        binding.navJoin.setOnClickListener {
            binding.viewPager.currentItem = 1
            binding.llJoinUnder.visibility
        }

        binding.navCampaign.setOnClickListener {
            binding.viewPager.currentItem = 2
            binding.llCampaignUnder.visibility
        }

        binding.navProfile.setOnClickListener {
            binding.viewPager.currentItem = 3
            binding.llProfileUnder.visibility
        }
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {

                    0 -> {
                        inVisiableUnderLine()
                        binding.llHomeUnder.visibility = View.VISIBLE
                    }

                    1 -> {
                        inVisiableUnderLine()
                        binding.llJoinUnder.visibility = View.VISIBLE
                    }

                    2 -> {
                        inVisiableUnderLine()
                        binding.llCampaignUnder.visibility = View.VISIBLE
                    }

                    3 -> {
                        inVisiableUnderLine()
                        binding.llProfileUnder.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }


        })
        binding.fab.setOnClickListener {
            val intent = Intent(this, InsertCampaignActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showPpopupDialog() {
        AlertDialog.Builder(this, R.style.updateDialogTheme).setView(R.layout.back_popup)
            .setCancelable(true).create().apply {
                show()

                findViewById<MaterialButton>(R.id.buttonCancel)?.setOnClickListener {
                    dismiss()
                }
                findViewById<MaterialButton>(R.id.buttonConfirm)?.setOnClickListener {
                    dismiss()
                    super.onBackPressed()
                    finish()
                }
            }
    }

    fun inVisiableUnderLine() {
        binding.llHomeUnder.visibility = View.GONE
        binding.llJoinUnder.visibility = View.GONE
        binding.llCampaignUnder.visibility = View.GONE
        binding.llProfileUnder.visibility = View.GONE
    }

    override fun onBackPressed() {
        showPpopupDialog()
        super.onBackPressed()
    }
}
