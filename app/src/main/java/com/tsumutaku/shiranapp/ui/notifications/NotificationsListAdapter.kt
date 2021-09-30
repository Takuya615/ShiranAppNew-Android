package com.tsumutaku.shiranapp.ui.notifications

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsumutaku.shiranapp.R

class NotificationsListAdapter(
    val level: Int
) : RecyclerView.Adapter<NotificationsListAdapter.CustomViewHolder>() {
    private val customList: MutableList<Characters> = NotificationsViewModel().characters
    //リスナー
    lateinit var listener: OnItemClickListener

    // ViewHolderクラス(別ファイルに書いてもOK)
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        val image: ImageView
        val item: View
        init {
            textView = view.findViewById<TextView>(R.id.content)
            image = view.findViewById<ImageView>(R.id.image)
            item = view.findViewById(R.id.itemCharacter)
        }
    }

    // getItemCount onCreateViewHolder onBindViewHolderを実装
    // 上記のViewHolderクラスを使ってViewHolderを作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.list_item_notifications, parent, false)
        return CustomViewHolder(item)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return customList.size
    }

    // ViewHolderに表示する画像とテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        if(customList[position].level > level){
            holder.image.setImageResource(R.drawable.ic_help)
            holder.image.setBackgroundColor(Color.GRAY)
            holder.textView.text = "Lv.${customList[position].level}で解放"
        }else{
            holder.image.setImageResource(customList[position].image)
            holder.textView.text = customList[position].name
            holder.item.setOnClickListener {
                listener.onItemClickListener(it, position, customList[position].id)
            }
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