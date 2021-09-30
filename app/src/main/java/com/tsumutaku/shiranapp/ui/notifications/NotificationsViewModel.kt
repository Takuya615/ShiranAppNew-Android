package com.tsumutaku.shiranapp.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsumutaku.shiranapp.R

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ただいま工事中"
    }
    val text: LiveData<String> = _text

    val characters = mutableListOf<Characters>(
        Characters("margin",1,400,R.drawable.c_margin,"ランプの魔神",
            """
                
                運動する場所を１つにしぼる。
                スマホを設置する位置を決めて、そこでのみ運動しましょう。

                記入例）寝室のカベにスマホを立てかけて、ヨガマットの上で運動する。

                Exp ＋４００p
            """.trimIndent()),
        Characters("wanwan",3,0,R.drawable.c_dog,"わんわん",
            """
                
                決まった時間に運動する。
                決めた時間から ±15分以内だと、わんわんが力を貸してくれます。

                毎回のスコア 1.2倍
            """.trimIndent()),
        Characters("rabbit",5,1500,R.drawable.c_rabbit,"ニト",
            """
                
                運動の種類を1つにしぼる。
                「今日は何をしようか？」と迷うと、運動がめんどくさく感じることもあります。
                定期的に変えてもいいので、あらかじめ運動の種類は１つに決めましょう。

                記入例）　この２週間はバーピーだけをする。
                Exp ＋１５００p
            """.trimIndent()),

    )

}

data class Characters(
    val id: String,
    val level: Int,
    val score: Int,
    val image: Int,
    val name: String,
    val script: String
)