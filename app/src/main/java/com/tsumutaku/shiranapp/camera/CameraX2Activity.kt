package com.tsumutaku.shiranapp.camera

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.databinding.ActivityCameraX2Binding
import com.tsumutaku.shiranapp.databinding.ActivityMainBinding
import com.tsumutaku.shiranapp.setting.tutorial.TutorialCoachMarkActivity
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraX2Activity() : AppCompatActivity() {

    private lateinit var binding: ActivityCameraX2Binding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var graphicOverlay:GraphicOverlay
    private lateinit var captureButton: ImageButton
    private lateinit var captureText: TextView
    private lateinit var timer: TextView
    private lateinit var score: TextView
    private lateinit var sound:Sounds
    private var isCapture: Boolean = false
    private var time:Int = 0

    var runnable = Runnable {  }
    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraX2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)//画面をオンのままにしておく
        supportActionBar!!.hide()//Navigationを消す

        // Request camera permissions???
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        val prefs = getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        time = prefs.getInt(getString(R.string.prefs_taskTime),5)

        graphicOverlay = binding.graphicOverlay
        captureButton = binding.captureButton
        captureText = binding.captureButtonText
        score = binding.scoreBoard
        timer = binding.timer
        timer.text = setTimer()
        sound = Sounds.getInstance(this)
        // Set up the listener for take photo button
        captureButton.setOnClickListener { startScore() }
        cameraExecutor = Executors.newSingleThreadExecutor()

        //ポーズ検出
        runnable = Runnable {
            if (!graphicOverlay.isonBitmap()){
                val bitmap = binding.viewFinder.bitmap
                if(bitmap!=null){graphicOverlay.poseDetectionML(bitmap)}//　　GraphicOverlayクラスで、　ポーズ検出し、それを画面に描画する
                if(isCapture){
                    score.text = " Score ${graphicOverlay.ExerciseIntensity()}" //表示されているスコアの値に加算していくメソッド
                }
            }
            handler.postDelayed(runnable,50)
        }
        handler.post(runnable)

        CoachMark()
    }

    private fun CoachMark(){
        Handler(Looper.getMainLooper()).postDelayed({
            val Coach = TutorialCoachMarkActivity(this)
            Coach.CoachMark3(this,timer,captureButton,score)
        }, 1000)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA//DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))


    }

    private fun startScore() {
        captureButton.visibility = View.INVISIBLE
        captureText.visibility = View.INVISIBLE
        TimeRecorder(this)
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    private fun TimeRecorder(context: Context){
        val mTimer = Timer()
        val taskSec = time//一次的にタスクタイムを保存
        time = 4
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                time -= 1
                timer.text = setTimer()
                if (time == 0){
                    if(!isCapture){
                        isCapture = true
                        time = taskSec
                        timer.text = setTimer()
                        sound.playSound(Sounds.SOUND_DRUMROLL)

                    }else{
                        isCapture = false
                        sound.playSound(Sounds.SAD_TROMBONE)
                        mTimer.cancel()
                        //finish()
                        val dialog = SimpleDialogFragment(graphicOverlay.ExerciseIntensity())
                        dialog.show(supportFragmentManager, "simple")
                    }
                }
            }
        }, 1000, 1000)
    }
    private fun setTimer():String{
        //val time = prefs.getInt(context.getString(R.string.prefs_taskTime),5)
        if (time < 60){return time.toString()}
        val seconds = time % 60;
        val minite = (time / 60) % 60;
        val timer = String.format("%02d:%02d", minite, seconds)
        return  timer
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        handler.removeCallbacks(runnable)
        sound.close()

    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                finish()
            }
        }
    }

}