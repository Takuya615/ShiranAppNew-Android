package com.tsumutaku.shiranapp.setting

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.camera.CameraX2Activity
import com.tsumutaku.shiranapp.databinding.ActivityIntroBinding
import com.tsumutaku.shiranapp.setting.tutorial.VideoPlayerActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "　しらんプリ　説明"
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingButtons()
        val prefs = getSharedPreferences( "preferences_key_sample", Context.MODE_PRIVATE)
        val Tuto1 : Boolean = prefs.getBoolean("Tuto1",false)
        if(!Tuto1){
            val intent= Intent(this, VideoPlayerActivity::class.java)
            startActivity(intent)
        }
    }
    //戻るボタンを押すと今いるviewを削除する
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun settingButtons(){
        binding.video.setOnClickListener {
            val intent= Intent(this, VideoPlayerActivity::class.java)
            startActivity(intent)
        }

        binding.q1.setOnClickListener {
            val dialog = introFragment(
                "アプリの流れ",
                """
                    
            ルールはカンタン。
            毎日、運動した記録をつけるだけ。
            
            
            初日はたったの５秒からスタート。
            ５秒　６秒　７秒　
            ・・・　
            ２４０秒
            いつの間にか４分間のHIITができるようになっています。
            
                """.trimIndent())
            dialog.show(supportFragmentManager, "intro")
        }

        binding.q2.setOnClickListener {
            val dialog = introFragment(
                "HIITって何？",
                """
                    
            ハイ インテンシティ インターバル トレーニングの略で、
            
            　　　20秒　ハードな運動
            　　　　　　　↓
            　　　10秒　休む
            　　　　　　　↓
            　　　20秒　ハードな運動
            　　　　　　　↓
            　　　10秒　休む
            を繰り返す方法のことです。
            
            1分 HIITは、45分 ランニングに匹敵する身体機能アップ効果が確認されています。
            近年、もっとも効率の良い運動として注目を浴びています。
            
            
                """.trimIndent())
            dialog.show(supportFragmentManager, "intro")
        }
        binding.q3.setOnClickListener {
            val dialog = introFragment(
                "HIITのメリット",
                """
                    
            以下のような効果が科学的に確認されています。
            ①　ダイエット効果が高い！
            ②　空腹感がやわらぐ！
            ③　寿命が伸びる！
            ④　若返る！
            ⑤　疲れにくい体になる！
            ⑥　鬱や不安症にも効く！
            ⑦　心肺機能が向上！
            ⑧　基礎代謝が上がる！
            
            
                """.trimIndent())
            dialog.show(supportFragmentManager, "intro")
        }
        binding.q4.setOnClickListener {
            val dialog = introFragment(
                "なぜ半年？",
                """
                    
            習慣は日を重ねれば重ねるほど強固になります。
            これまで運動を全くしてこなかった人でも、より確実に身につけられるよう半年間としています。
            
            ロンドン大学の研究によると、運動のような負担の大きなタスクを習慣にするには時間がかかり、最大254日（８ヶ月強）かかる人もいたそうです。
            
            焦りは禁物
            
            
                """.trimIndent())
            dialog.show(supportFragmentManager, "intro")
        }
    }
}


class introFragment(val title:String,val script:String) : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(script)
            .setPositiveButton("OK"){dialog,id ->
            }
        return builder.create()
    }
}