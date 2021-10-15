package com.tsumutaku.shiranapp.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.camera.SaveData
import com.tsumutaku.shiranapp.camera.SimpleDialogFragment
import com.tsumutaku.shiranapp.databinding.FragmentNotificationsBinding
import com.tsumutaku.shiranapp.setting.EventAnalytics
import com.tsumutaku.shiranapp.ui.dashboard.VideoListAdapter

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : NotificationsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "スケット"
            setDisplayHomeAsUpEnabled(false)//表示
        }

        val prefs = requireContext().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val level = prefs.getInt(requireContext().getString(R.string.score_level),0)
        adapter = NotificationsListAdapter(level)
        val layoutManager = LinearLayoutManager(requireContext())

        // アダプターとレイアウトマネージャーをセット
        val itemDecoration = DividerItemDecoration(requireActivity().applicationContext, DividerItemDecoration.VERTICAL) // こいつが必要
        binding.recyclerView.addItemDecoration(itemDecoration)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        // インターフェースの実装
        adapter.setOnItemClickListener(object : NotificationsListAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                when (view.getId()) {
                    R.id.itemCharacter -> {

                        if(NotificationsViewModel().characters[position].id == "rabbit"){
                            EventAnalytics().lastCharacterReleased(requireContext())
                        }

                        //ここでキャラごとのViewを変更する
                        if(NotificationsViewModel().characters[position].id == "wanwan"){
                            val dialog = NotificationsDialogFragment2(NotificationsViewModel().characters[position])
                            dialog.show(requireActivity().supportFragmentManager, "simple")
                        }else{
                            val dialog = NotificationsDialogFragment(NotificationsViewModel().characters[position])
                            dialog.show(requireActivity().supportFragmentManager, "simple")
                        }

                    }

                }
            }
        })
    }

}