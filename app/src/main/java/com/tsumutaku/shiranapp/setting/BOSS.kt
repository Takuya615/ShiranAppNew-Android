package com.tsumutaku.shiranapp.setting

import android.content.Context
import android.content.SharedPreferences
import com.tsumutaku.shiranapp.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class BOSS {

    fun isExit(context:Context):boss?{
        val prefs = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val setday: String? = prefs.getString(context.getString(R.string.prefs_dayly_check), "2021-01-28")//前回利用した日
        val now = LocalDate.now() //2019-07-28T15:31:59.754
        val day1 = LocalDate.parse(setday)//2019-08-28T10:15:30.123
        val different = ChronoUnit.DAYS.between(day1, now).toInt() // diff: 30

        if(different > 365){return null}
        if(different == 0){return null}


        return null
    }
    fun showBoss(){

    }
    fun enemyList():Array<boss>{
        val list = arrayOf(
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy1", "スライム",170.0f,0,50),
            boss("enemy2","おばけ",200.0f, 0,60),
            boss("enemy2","おばけ",200.0f, 0,60),
            boss("enemy2","おばけ",200.0f, 0,60),
            boss("enemy2","おばけ",200.0f, 0,60),
            boss("enemy2","おばけ",200.0f, 0,60),
            boss("enemy3", "コブラ",350.0f, 0,90),
            boss("enemy3", "コブラ",350.0f, 0,90),
            boss("enemy3", "コブラ",350.0f, 0,90),
            boss("enemy3", "コブラ",350.0f, 0,90),
            boss("enemy4", "あばれ牛", 600.0f, 0, 200),
            boss("enemy5","あばれグマ", 800.0f, 0, 300),
        )
        return list
    }
    fun bossList():Array<boss>{
        val list = arrayOf(
            boss("", "", 0.0f, -1, 0),
        boss("boss1",  "BOSS",  630.0f,  6,  700),
        boss("boss1","BOSS", 900.0f, 12,  1000),
        boss( "boss2", "BOSS", 1170.0f, 18, 1200),
        boss("boss2", "BOSS", 1440.0f,24,1500),
        boss("boss2", "BOSS", 1710.0f, 30, 1800)
        )
        return list
    }
}

data class boss(
    val name: String,
    val image:String,
    val maxHP: Float,
    val encount: Int,
    val bonus: Int

)