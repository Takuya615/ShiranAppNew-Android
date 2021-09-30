package com.tsumutaku.shiranapp.ui.notifications

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.google.type.DateTime
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.camera.SaveData
import com.tsumutaku.shiranapp.databinding.FragmentNotificationsDialog2Binding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


class NotificationsDialogFragment2(val character: Characters) :
    DialogFragment() {

    private var _binding: FragmentNotificationsDialog2Binding? = null
    private val binding get() = _binding!!
    //private var isClose: Boolean = false
    private lateinit var prefs: SharedPreferences
    private lateinit var introDialog:Dialog// = Dialog(requireContext())


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val setText = prefs.getString(character.id,"00:00")

        introDialog = Dialog(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_notifications_dialog2, null)
        _binding = FragmentNotificationsDialog2Binding.bind(dialogView)

        binding.image.setImageResource(character.image)
        binding.textView.text = character.script
        binding.timerText.text = setText
        binding.button.setOnClickListener {
            showTimePicker(character.id)
        }

        introDialog.setContentView(dialogView)
        introDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))//TRANSPARENT))    // 全画面
        introDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//全画面

        return introDialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun showTimePicker(id:String) {
        class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current time as the default values for the picker
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)

                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute, true)
            }

            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
                val time = "${hourOfDay}:${minute}"
                binding.timerText.text = time
                prefs.edit().putString(id,time).apply()

                introDialog.dismiss()
                AlertDialog.Builder(requireContext())
                    .setTitle("時間を設定しました")
                    //.setMessage("メッセージ")
                    .setPositiveButton("OK", { dialog, which ->})
                    .show()
            }


        }

        TimePickerFragment().show(requireActivity().supportFragmentManager, "TAG")
    }
}

