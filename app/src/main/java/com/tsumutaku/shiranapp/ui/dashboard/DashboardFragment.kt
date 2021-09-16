package com.tsumutaku.shiranapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : VideoListAdapter
    private lateinit var mdateList:MutableList<String>
    private lateinit var scoreList:MutableList<String>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var coll : CollectionReference
    //private lateinit var userId:String
    private lateinit var UriString:String
    var isfriend:Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mdateList = mutableListOf<String>()
        scoreList = mutableListOf()
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val user = mAuth.currentUser

        //val friendUid = uidrequireActivity().intent.getStringExtra("friendUid")

        //¸友達の動画確認ようコード
        //val uid = notificationsViewModel.modelUid
        //if(uid != user!!.uid) { isfriend = true}
        val uid = user!!.uid

        coll =db.collection(uid)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "活動記録"
            setDisplayHomeAsUpEnabled(false)//表示
        }

        coll/*.whereEqualTo("friend", false)*/.get()//.orderBy("date", Query.Direction.DESCENDING)
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val date = document.data["date"].toString()
                    val score = document.data["score"].toString()
                    mdateList.add(date)
                    scoreList.add(score)

                }
                mdateList.reverse()
                scoreList.reverse()

                adapter.notifyDataSetChanged()
                binding.progressBar2.visibility = View.INVISIBLE
            }
        //.addOnFailureListener { exception -> Log.w("TAG", "Error getting documents: ", exception) }

        adapter = VideoListAdapter(mdateList,scoreList)
        val layoutManager = LinearLayoutManager(requireContext())

        // アダプターとレイアウトマネージャーをセット
        val itemDecoration = DividerItemDecoration(requireActivity().applicationContext, DividerItemDecoration.VERTICAL) // こいつが必要
        binding.recyclerView.addItemDecoration(itemDecoration)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        // インターフェースの実装
        adapter.setOnItemClickListener(object : VideoListAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                when (view.getId()) {
                    /*R.id.itemTextView -> {

                        UriString = muriList[position]
                        val intent = Intent(requireActivity(), GalleryActivity::class.java)
                        intent.putExtra("selectedName", UriString)
                        intent.putExtra("friendUid", uid)
                        intent.putExtra("date", mdateList[position])
                        startActivity(intent)
                        //Toast.makeText(applicationContext, "${clickedText}がタップされました", Toast.LENGTH_LONG).show()
                    }*/
                    R.id.itemdeleate -> {
                        coll.document(mdateList[position]).delete()
                        Toast.makeText(requireContext(), "${clickedText}を削除しました", Toast.LENGTH_LONG).show()

                        mdateList.remove(mdateList[position])
                        scoreList.remove(scoreList[position])
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, mdateList.size)
                        //adapter.notifyDataSetChanged()

                    }
                }
            }
        })
    }
}