package com.tsumutaku.shiranapp.setting

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mCreateAccountListener: OnCompleteListener<AuthResult>
    private lateinit var mLoginListener: OnCompleteListener<AuthResult>
    private var mIsCreateAccount = false
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        title = "ログイン　/ アカウント作成"
        auth = Firebase.auth
        binding.createButton.setOnClickListener { v ->
            // キーボードが出てたら閉じる
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            binding.progressBar.visibility = View.VISIBLE

            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            if(email.isNotEmpty()&&password.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if(!MainActivity.debag){
                                val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
                                val bundle = Bundle()
                                bundle.putString(FirebaseAnalytics.Param.METHOD, "Sign_Up!")
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
                            }
                            if (MainActivity.debag){
                                Log.d(TAG, "createUserWithEmail:success")
                            }
                            //val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            if (MainActivity.debag){
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)}
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(baseContext, "アカウント作成 失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        binding.loginButton.setOnClickListener { v ->

            // キーボードが出てたら閉じる
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            binding.progressBar.visibility = View.VISIBLE

            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if(!MainActivity.debag){
                                val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
                                val bundle = Bundle()
                                bundle.putString(FirebaseAnalytics.Param.METHOD, "Login")
                                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
                            }
                            if (MainActivity.debag){
                                Log.d(TAG, "signInWithEmail:success")}
                            //val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            // If sign in fails, display a message to the user.
                            if (MainActivity.debag){
                                Log.w(TAG, "signInWithEmail:failure", task.exception)}
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(baseContext, "ログイン 失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.PPbtn.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
    }
}