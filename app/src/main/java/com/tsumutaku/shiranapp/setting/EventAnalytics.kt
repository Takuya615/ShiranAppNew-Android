package com.tsumutaku.shiranapp.setting

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R

class EventAnalytics {

    //MainActivity
    fun tapFab(context: Context){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id-DailyTask")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Fabのタップ数")
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Daily")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }

    //SaveData
    fun totalAndTask(context: Context,total:Int,task:Int){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Daily")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "総日数とタスク")
            bundle.putString("count", "総日${total}日で タスク時間${task}秒")
            firebaseAnalytics.logEvent("totalAndTask",bundle)
        }
    }
    //SavaData
    fun doneDayly(context: Context){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Daily")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "毎日更新数")
            firebaseAnalytics.logEvent("doneDaily",bundle)
        }
    }
    //SavaData
    fun doneNotEveryDay(context: Context,diff: Int){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Daily")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "継続しっぱい数")
            bundle.putString("count", "${diff} 日ぶりの更新")
            firebaseAnalytics.logEvent("doneNotEveryday",bundle)
        }
    }

    //CameraDialogFragment
    fun loseEnemy(context: Context,enemy:String){
        if(!MainActivity.debag){
            val prefs = context.getSharedPreferences( "", Context.MODE_PRIVATE)
            val total = prefs.getInt(context.getString(R.string.score_totalDay),0)
            val task = prefs.getInt(context.getString(R.string.prefs_taskTime),0)
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Battle")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "負けた数")
            bundle.putString("count", "${enemy}  に総日${total}日目、タスク時間${task}秒で負けた。")
            firebaseAnalytics.logEvent("loseEnemy",bundle)
        }
    }
    //CameraDialogFragment
    fun totalBattle(context: Context){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Battle")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "総バトル回数")
            firebaseAnalytics.logEvent("totalBattle",bundle)
        }
    }
    //CameraDialogFragment
    fun levelUp(context: Context,level: Int){
        if(!MainActivity.debag){
            val prefs = context.getSharedPreferences( "", Context.MODE_PRIVATE)
            val total = prefs.getInt(context.getString(R.string.score_totalDay),0)
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "レベルアップ")
            bundle.putString(FirebaseAnalytics.Param.LEVEL, "level")
            bundle.putString(FirebaseAnalytics.Param.CHARACTER,"Player-継続-${total}日目")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP,bundle)
        }
    }

    //MainActivity
    fun share(context: Context){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Text")
            bundle.putString(FirebaseAnalytics.Param.METHOD, "SNS")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,bundle)
        }
    }
    //NotificationsFragment
    fun lastCharacterReleased(context: Context){
        if(!MainActivity.debag){
            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Int")
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ラストキャラ使われた")
            firebaseAnalytics.logEvent("LastCharaReleased",bundle)
        }
    }
}

