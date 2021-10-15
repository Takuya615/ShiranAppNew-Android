package com.tsumutaku.shiranapp.camera

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.databinding.FragmentShowEnemyDialogBinding
import com.tsumutaku.shiranapp.setting.EventAnalytics
import com.tsumutaku.shiranapp.setting.boss
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs


class SimpleDialogFragment(var score: Int,val boss: boss?): DialogFragment() {

    //var timesScore = 1.0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val damage = prefs.getInt(requireContext().getString(R.string.bossDamage),0)
        val diff = SaveData().editScores(requireContext(),score)
        val dialog: Dialog = Dialog(requireContext())
        val bonus = timesScore(requireContext())
        var message = ""
        if(bonus > 1.0){ message = "キャラボーナス　×${bonus}" }

        if(diff <= 0){
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("   スコア${score}")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, id -> }
            return builder.create()
        }

        if (boss != null) {
            EventAnalytics().totalBattle(requireContext())
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.fragment_show_enemy_dialog, null)
            val image = dialogView.findViewById<ImageView>(R.id.image)
            val text = dialogView.findViewById<TextView>(R.id.textView)

            if (score+damage < boss.maxHP){
                image.setImageResource(boss.image)
                if (boss.encount == 0){
                    EventAnalytics().loseEnemy(requireContext(),boss.name)
                    message = "${boss.name}に負けました\n" + mes(score/2) + "\n"  + "ペナルティー　Exp 半分\n"+message
                }else{
                    prefs.edit().putInt(getString(R.string.bossDamage),score+damage).apply()
                    message = "${boss.name}に負けました\n" + mes(score/10) + "\n" + "ボスペナルティー　Exp 1/10\n" +message
                }
                text.text = message
            }else{
                prefs.edit()
                    .putInt(getString(R.string.bossNum),0)
                    .putInt(getString(R.string.bossDamage),0)
                    .apply()
                image.setImageResource(boss.image)
                image.setColorFilter(Color.argb(180, 255 , 255, 255))//(Color.parseColor(Color), PorterDuff.Mode.SRC_IN);
                message = "  VICTORY!!\n" + mes(score + boss.bonus) + "\n" +message + "討伐ボーナス　Exp ＋${boss.bonus}"
                text.text = message
            }

            dialog.setContentView(dialogView)
        }

        return dialog
    }

    fun mes(score: Int): String{
        var message = ""
        val lv = SaveData.levelUP(requireContext(),score)
        if (lv.first == lv.second){
            message = "Exp 獲得 ${score}p"
        }else{
            EventAnalytics().levelUp(requireContext(),lv.second)
            message = "レベルアップ！！ ${lv.first} → ${lv.second}"
        }
        return message
    }

    override fun onDestroy() {
        super.onDestroy()

        SaveData().WriteToStore(requireContext(),score)
        requireActivity().finish()
    }

    //キャラ補正ボーナス
    companion object {
        fun timesScore(context: Context): Float {
            val pref = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
            val settime = pref.getString("wanwan", "")
            if (settime!!.isNotEmpty()) {
                val now = LocalTime.now() //2019-07-28T15:31:59.754
                val time1 = LocalTime.parse(settime)//2019-08-28T10:15:30.123
                val different = ChronoUnit.MINUTES.between(time1, now).toInt() // diff: 30
                if (abs(different) < 15) {
                    return 1.2f
                }
            }
            return 1.0f
        }
    }

}