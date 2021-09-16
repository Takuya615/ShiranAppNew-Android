package com.tsumutaku.shiranapp.camera

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.R


class SimpleDialogFragment(val score: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val totalDay = prefs.getInt(getString(R.string.score_totalDay),1)
        val builder = AlertDialog.Builder(activity)
        builder
            .setTitle("${totalDay}日目 スコア${score}")
            //.setMessage("Here Message")
            .setPositiveButton("done") { dialog, id ->
                println("dialog:$dialog which:$id")
            }

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().finish()
        SaveData().editScores(requireContext(),score)
        SaveData().WriteToStore(score)
    }

}