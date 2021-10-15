package com.tsumutaku.shiranapp.camera

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.databinding.FragmentNotificationsDialogBinding
import com.tsumutaku.shiranapp.databinding.FragmentShowEnemyDialogBinding
import com.tsumutaku.shiranapp.setting.boss

class showEnemyDialogFragment(val boss: boss) : DialogFragment() {

    private var _binding: FragmentShowEnemyDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_show_enemy_dialog, null)
        _binding = FragmentShowEnemyDialogBinding.bind(dialogView)

        binding.image.setImageResource(boss.image)
        binding.textView.text = "${boss.name}があらわれた"
        /*binding.button.setOnClickListener {
            val text = binding.editText.text.toString()
            prefs.edit().putString(character.id,text).apply()
            val firstTime = prefs.getBoolean("first${character.id}",false)
            if (!firstTime){
                SaveData.levelUP(requireContext(),character.score)
                prefs.edit().putBoolean("first${character.id}",true).apply()
                AlertDialog.Builder(requireContext())
                    .setTitle("スコアボーナス　＋${character.score}p　")
                    //.setMessage("メッセージ")
                    .setPositiveButton("OK", { dialog, which ->})
                    .show()
            }
            dialog.dismiss()
        }*/

        dialog.setContentView(dialogView)
        //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))//TRANSPARENT))    // 全画面
        //dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//全画面

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}