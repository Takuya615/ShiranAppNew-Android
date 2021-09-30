package com.tsumutaku.shiranapp.ui.notifications

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.camera.SaveData
import com.tsumutaku.shiranapp.databinding.FragmentHomeBinding
import com.tsumutaku.shiranapp.databinding.FragmentNotificationsDialogBinding

class NotificationsDialogFragment(val character: Characters) : DialogFragment() {

    private var _binding: FragmentNotificationsDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val setText = prefs.getString(character.id,"")

        val dialog = Dialog(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_notifications_dialog, null)
        _binding = FragmentNotificationsDialogBinding.bind(dialogView)

        binding.image.setImageResource(character.image)
        binding.textView.text = character.script
        binding.editText.text = Editable.Factory.getInstance().newEditable(setText)
        binding.button.setOnClickListener {
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
        }

        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))//TRANSPARENT))    // 全画面
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//全画面

        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}