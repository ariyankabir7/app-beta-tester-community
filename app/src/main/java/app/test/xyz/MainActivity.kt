package app.test.xyz

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import com.test.xyz.TinyDB

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val insetsController = ViewCompat.getWindowInsetsController(v)
            insetsController?.isAppearanceLightStatusBars = true
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val fadeInAnimation: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val logoCard = findViewById<MaterialCardView>(R.id.imageview)

        // Set up a listener for the fade-in animation
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Animation start
            }

            override fun onAnimationEnd(animation: Animation) {
                if (!TinyDB.getBoolean(this@MainActivity, "isLogin", false)) {

                    startActivity(Intent(this@MainActivity, GoogleLoginActivity::class.java))
                    finish()

                } else {
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                    finish()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {
                // Animation repeat
            }
        })

        // Apply the animation to the logo card's image view
        logoCard.startAnimation(fadeInAnimation)
    }
}
