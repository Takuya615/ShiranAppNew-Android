package com.tsumutaku.shiranapp.setting

import android.content.Context
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.tsumutaku.shiranapp.R

class bossData {

    fun isExit(context:Context):boss?{
        val prefs = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val setday: String? = prefs.getString(context.getString(com.tsumutaku.shiranapp.R.string.prefs_dayly_check), "2000-01-28")//前回利用した日
        val now = LocalDate.now() //2019-07-28T15:31:59.754
        val day1 = LocalDate.parse(setday)//2019-08-28T10:15:30.123
        val different = ChronoUnit.DAYS.between(day1, now).toInt() // diff: 30
        //val different = 1

        if(different > 365){return null}
        if(different == 0){return null}
        val list = bossList()
        val bossNum = prefs.getInt(context.getString(com.tsumutaku.shiranapp.R.string.bossNum),0)
        if(bossNum > 0){return list[bossNum]}

        val total = prefs.getInt(context.getString(com.tsumutaku.shiranapp.R.string.score_totalDay),0)
        for (i in 1..list.size-1){
            if(total == list[i].encount){
                prefs.edit().putInt(context.getString(com.tsumutaku.shiranapp.R.string.bossNum),i).apply()
                return list[i]
            }
        }

        val enemys = enemyList()
        return enemys.random()
    }

    fun enemyList():Array<boss>{
        val list = arrayOf(
            boss(R.drawable.enemy1, "スライム",320.0f,0,100),
            boss(R.drawable.enemy1, "スライム",340.0f,0,100),
            boss(R.drawable.enemy1, "スライム",360.0f,0,100),
            boss(R.drawable.enemy1, "スライム",380.0f,0,100),
            boss(R.drawable.enemy1, "スライム",400.0f,0,100),
            boss(R.drawable.enemy1, "スライム",420.0f,0,100),
            boss(R.drawable.enemy1, "スライム",440.0f,0,100),
            boss(R.drawable.enemy1, "スライム",460.0f,0,100),

            boss(R.drawable.enemy2,"おばけ",500.0f, 0,150),
            boss(R.drawable.enemy2,"おばけ",500.0f, 0,150),
            boss(R.drawable.enemy2,"おばけ",600.0f, 0,150),
            boss(R.drawable.enemy2,"おばけ",600.0f, 0,150),
            boss(R.drawable.enemy2,"おばけ",700.0f, 0,150),
            boss(R.drawable.enemy3, "コブラ",800.0f, 0,200),
            boss(R.drawable.enemy3, "コブラ",900.0f, 0,200),
            boss(R.drawable.enemy4, "あばれ牛", 1300.0f, 0, 400),
            boss(R.drawable.enemy5,"あばれグマ", 1500.0f, 0, 500),
        )
        return list
    }
    fun bossList():Array<boss>{
        val list = arrayOf(
            boss(0, "", 0.0f, -1, 0),
        boss(R.drawable.boss1,  "BOSS",  1000.0f,  7,  1000),
        boss(R.drawable.boss1,"BOSS", 2000.0f, 14,  2000),
        boss( R.drawable.boss2, "BOSS",3000.0f, 21, 3000),
        boss(R.drawable.boss2, "BOSS", 3500.0f,28,4000),
        boss(R.drawable.boss2, "BOSS", 4000.0f, 35, 5000)
        )
        return list
    }
}

data class boss(
    val image:Int,
    val name: String,
    val maxHP: Float,
    val encount: Int,
    val bonus: Int

)
