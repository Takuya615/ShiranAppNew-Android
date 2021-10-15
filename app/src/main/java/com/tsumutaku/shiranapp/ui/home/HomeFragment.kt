package com.tsumutaku.shiranapp.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.camera.SimpleDialogFragment
import com.tsumutaku.shiranapp.databinding.FragmentHomeBinding
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super .onViewCreated(view,savedInstanceState)
        prefs = requireActivity().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)

        /*val ncd = prefs.getInt(getString(R.string.score_continuous),0)//連続日数
        val revival = prefs.getInt(getString(R.string.score_recover),0)//復活回数
        binding.retryCounter.text = revival.toString()
        binding.contiCounter.text = ncd.toString()*/


    }

    override fun onResume() {
        super.onResume()
        //prefs = requireActivity().getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val ncd = prefs.getInt(getString(R.string.score_continuous),0)//連続日数
        val revival = prefs.getInt(getString(R.string.score_recover),0)//復活回数
        binding.retryCounter.text = revival.toString()
        binding.contiCounter.text = ncd.toString()

        val times = SimpleDialogFragment.timesScore(requireContext())
        if(times>1.0){
            binding.image.setImageResource(R.drawable.c_dog)
        }
    }


}