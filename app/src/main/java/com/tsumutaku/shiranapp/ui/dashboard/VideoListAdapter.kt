package com.tsumutaku.shiranapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsumutaku.shiranapp.R

class VideoListAdapter(
    private val customList: MutableList<String>,
    private val customList2: MutableList<String>
) : RecyclerView.Adapter<VideoListAdapter.CustomViewHolder>() {

    //リスナー
    lateinit var listener: OnItemClickListener

    // ViewHolderクラス(別ファイルに書いてもOK)
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView
        val deleate: Button
        init {
            textView = view.findViewById<TextView>(R.id.content)
            textView2 = view.findViewById<TextView>(R.id.content2)
            deleate = view.findViewById<Button>(R.id.itemdeleate)
        }
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.list_item_video, parent, false)
        return CustomViewHolder(item)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return customList.size
    }

    // ViewHolderに表示する画像とテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.textView.text = customList[position]
        holder.textView2.text = customList2[position]

        holder.textView.setOnClickListener {
            listener.onItemClickListener(it, position, customList[position])
        }

        holder.deleate.setOnClickListener {
            listener.onItemClickListener(it, position, customList[position])
        }

    }
    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

}