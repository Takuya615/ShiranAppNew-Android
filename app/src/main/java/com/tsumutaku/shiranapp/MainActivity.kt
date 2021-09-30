package com.tsumutaku.shiranapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tsumutaku.shiranapp.databinding.ActivityMainBinding
import com.tsumutaku.shiranapp.setting.PrivacyPolicyActivity
import com.tsumutaku.shiranapp.setting.tutorial.TutorialCoachMarkActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.tsumutaku.shiranapp.camera.CameraX2Activity
import com.tsumutaku.shiranapp.setting.IntroActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val prefs = getSharedPreferences( "preferences_key_sample", Context.MODE_PRIVATE)
        val level = prefs.getInt(getString(R.string.score_level),0)
        binding.textView.text = "Lv.${level}"
        val Tuto1 : Boolean = prefs.getBoolean("Tuto1",false)
        if(!Tuto1){
            val intent= Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

        binding.fab.setOnClickListener { view ->
            //progressbar.visibility = android.widget.ProgressBar.VISIBLE
            if(!MainActivity.debag){
                val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.METHOD, "DAYLY-MOVIE")
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
            }

            val intent= Intent(this, CameraX2Activity::class.java)
            startActivity(intent)
            //finish()
        }
        coachMark()
    }
    fun coachMark(){
        Handler(Looper.getMainLooper()).postDelayed({
            val Coach = TutorialCoachMarkActivity(this)
            Coach.CoachMark1(this,this)
        }, 1000)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent3= Intent(this, IntroActivity::class.java)
        val intent4= Intent(this, PrivacyPolicyActivity::class.java)

        when (item.itemId) {
            /*   ¸友人の検索エンジン
            R.id.action_search -> {
                startActivity(Intent(this, FriendSearchActivity::class.java))
                true
            }*/
            R.id.action_settings -> startActivity(intent3)

            R.id.action_privacy_policy -> startActivity(intent4)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        val debag = true//　　　　　デバック時　全てのログとFirebaseイベントログを作動させない 本番でfalse
        val TAG = "MainActivity"
    }
}