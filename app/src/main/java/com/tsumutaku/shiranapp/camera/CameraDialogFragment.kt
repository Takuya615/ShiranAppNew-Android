package com.tsumutaku.shiranapp.camera

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs


class SimpleDialogFragment(var score: Int): DialogFragment() {

    var timesScore = 1.0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val totalDay = prefs.getInt(getString(R.string.score_totalDay),1)
        timesScore = timesScore(prefs)
        if(MainActivity.debag){Log.e("score", "ビフォアー${score}")}
        score = (score * timesScore).toInt()
        var message = ""
        if (timesScore > 1.0){message = "×${timesScore}"}

        if(MainActivity.debag){Log.e("score", "スコア${timesScore}倍　アフター${score}")}

        val builder = AlertDialog.Builder(activity)
        builder
            .setTitle("${totalDay}日目 スコア${score}")
            .setMessage(message)
            .setPositiveButton("done") { dialog, id ->
            }

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        SaveData().editScores(requireContext(),score)
        SaveData().WriteToStore(requireContext(),score)
        requireActivity().finish()
    }

    companion object {
        fun timesScore(pref: SharedPreferences): Double{
            //val pref = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
            val settime = pref.getString("wanwan","")
            if (settime!!.isNotEmpty()){
                val now = LocalTime.now() //2019-07-28T15:31:59.754
                val time1 = LocalTime.parse(settime)//2019-08-28T10:15:30.123
                val different = ChronoUnit.MINUTES.between(time1, now).toInt() // diff: 30
                if (abs(different)<15){
                    return 1.2
                }
            }
            return 1.0
        }
    }

}