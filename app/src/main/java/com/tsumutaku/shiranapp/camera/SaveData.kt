package com.tsumutaku.shiranapp.camera

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tsumutaku.shiranapp.MainActivity
import com.tsumutaku.shiranapp.R
import com.tsumutaku.shiranapp.setting.EventAnalytics
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

class SaveData {

    //private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun WriteToStore(context: Context, score:Int){

        if(MainActivity.debag){Log.e("tag", "スコアと日付を記録")}
        val date= Calendar.getInstance().getTime()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val StrDate =dateFormat.format(date).toString()

        val dateList = SaveData.loadArrayList(context,context.getString(R.string.prefs_date_list))
        val scoreList = SaveData.loadArrayList(context,context.getString(R.string.prefs_score_list))
        dateList.add(StrDate)
        scoreList.add(score.toString())
        saveArrayList(context,context.getString(R.string.prefs_date_list),dateList)
        saveArrayList(context,context.getString(R.string.prefs_score_list),scoreList)
    }


    fun editScores(context: Context, IntensityPoint:Int):Int{

        //var newCon: Int = 0//連続
        //var newRec: Int = 0//復活
        //var newtot: Int = 0//総日数
        val prefs = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
        val save: SharedPreferences.Editor = prefs.edit()

        val now = LocalDate.now() //2019-07-28T15:31:59.754
        val setday: String? = prefs.getString(context.getString(R.string.prefs_dayly_check), null)//前回利用した日
        if(setday == null){ //初回
            val cal = Calendar.getInstance()
            cal.time = Date()
            cal.add(Calendar.DATE,-1)
            val lDate = cal.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            save.putString(context.getString(R.string.prefs_dayly_check),lDate.toString())
            save.apply()
            return 0
        }

        val day1 = LocalDate.parse(setday)//2019-08-28T10:15:30.123
        val different = ChronoUnit.DAYS.between(day1, now).toInt() // diff: 30
        //val different = 1

        var continuous = prefs.getInt(context.getString(R.string.score_continuous), 0)
        var recover = prefs.getInt(context.getString(R.string.score_recover), 0)
        var totalD = prefs.getInt(context.getString(R.string.score_totalDay), 0)
        var DoNot = prefs.getInt(context.getString(R.string.score_doNotDay), 0)
        var time = prefs.getInt(context.getString(R.string.prefs_taskTime), 5)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month= cal.get(Calendar.MONTH)//　　注意　　1月が　0　１２月が　11
        val key = "$year-$month"
        val jsonList: ArrayList<String> = loadArrayList(context,key)
        //val numbers = arrayListOf<String>()

        if (different == 0){return different}
        if (different == 1) {
            EventAnalytics().doneDayly(context)
            if(MainActivity.debag){Log.e("tag", "デイリー達成")}
            continuous = continuous + 1//継続日数
            recover = recover//復活数
            totalD = totalD + 1//総日数
            jsonList.add(IntensityPoint.toString())//今日の分のスコア
            time = addTime(context,totalD,time)

            //levelUP(context,IntensityPoint)
        }
        if (different >= 2) {
            EventAnalytics().doneNotEveryDay(context,different)
            if(MainActivity.debag){Log.e("tag", "継続失敗で更新")}
            continuous = 0//継続リセット
            recover = recover + 1//復活数
            totalD = totalD + 1//総日数
            DoNot = DoNot + different - 1
            //levelUP(context,IntensityPoint)

            if(different > now.dayOfMonth){// さぼりが翌月まで続いている場合
                for (i in 2..now.dayOfMonth) {
                    jsonList.add("")
                }
                if(MainActivity.debag){
                    Log.e("tag", "翌月までさぼり続いてるパティーン")}
            }else{
                for (i in 2..different) {// 今月中のさぼりは今月分にカウントされる
                    jsonList.add("")
                }
                if(MainActivity.debag){
                    Log.e("tag", "今月のさぼりパティーン")}
            }
            //jsonList.forEach{ numbers.add(it) }
            jsonList.add(IntensityPoint.toString())//今日の分のスコア
            time = addTime(context,totalD,time)

        }
        /*
        if (different == 1) {
            if(MainActivity.debag){Log.e("tag", "デイリー更新！！")}
            newCon = continuous + 1//継続日数
            newRec = recover//復活数
            newtot = totalD + 1//総日数
            jsonList.add(IntensityPoint.toString())//今日の分のスコア
            time = addTime(totalD,time)
            levelUP(context,IntensityPoint)

        } else if (different >= 2) {
            if(MainActivity.debag){Log.e("tag", "継続失敗で更新")}
            newCon = 0//継続リセット
            newRec = recover + 1//復活数
            newtot = totalD + 1//総日数
            DoNot = DoNot + different - 1
            levelUP(context,IntensityPoint)

            if(different > now.dayOfMonth){// さぼりが翌月まで続いている場合
                for (i in 2..now.dayOfMonth) {
                    jsonList.add("")
                }
                if(MainActivity.debag){
                    Log.e("tag", "翌月までさぼり続いてるパティーン")}
            }else{
                for (i in 2..different) {// 今月中のさぼりは今月分にカウントされる
                    jsonList.add("")
                }
                if(MainActivity.debag){
                    Log.e("tag", "今月のさぼりパティーン")}
            }
            //jsonList.forEach{ numbers.add(it) }
            jsonList.add(IntensityPoint.toString())//今日の分のスコア
            time = addTime(totalD,time)

        } else if (different == 0) {
            if(MainActivity.debag){Log.e("tag", "今日のデイリーは既に終わっている")}
            newCon = continuous//継続日数
            newRec = recover//復活数
            newtot = totalD//総日数
        }*/
        if(MainActivity.debag){ Log.e("tag", "生成された配列は$jsonList ")}
        saveArrayList(context,key, jsonList)

        save.putInt(context.getString(R.string.score_continuous), continuous)
        save.putInt(context.getString(R.string.score_recover), recover)
        save.putInt(context.getString(R.string.score_totalDay), totalD)
        save.putInt(context.getString(R.string.score_doNotDay), DoNot)
        save.putString(context.getString(R.string.prefs_dayly_check),now.toString())
        save.putInt(context.getString(R.string.prefs_taskTime),time)
        save.apply()
        return different
    }

    //デイリー
    private fun addTime(context: Context,td:Int,taskTime:Int):Int{
        var time = taskTime
        if(td %2 != 0 && time<240) {
            when {
                td < 61 -> time = time + 1
                60 < td && td < 91 -> time = time + 2
                90 < td && td < 121 -> time = time + 3
                120 < td && td < 151 -> time = time + 4
                150 < td && td < 181 -> time = time + 5
                else -> time = time + 5
            }
            EventAnalytics().totalAndTask(context,td,taskTime)
            return time
        }
        if(MainActivity.debag){Log.e("tag", "time加算　${taskTime} -> ${time}")}
        return time
    }

    companion object{
        // リストの保存
        fun saveArrayList(context: Context,key: String, arrayList:List<Any> ) {//ArrayList<String>
            if(MainActivity.debag){Log.d("tag","保存されたリスト名は$key")}

            val prefs = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
            val shardPrefEditor = prefs.edit()

            val jsonArray = JSONArray(arrayList)
            shardPrefEditor.putString(key, jsonArray.toString())
            shardPrefEditor.apply()
            if(MainActivity.debag){Log.d("tag","保存されたリスト$jsonArray")}

        }

        // リストの読み込み     初日の時間も記録したうえでlistで返す
        fun loadArrayList(context: Context,key:String): ArrayList<String> {
            if(MainActivity.debag){Log.d("tag","呼び出された　リスト名は$key")}

            val shardPreferences = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)//this.getPreferences(Context.MODE_PRIVATE)

            val jsonArray = JSONArray(shardPreferences.getString(key, "[]"))//

            val list = ArrayList<String>()
            //val jsonArray = jsonObject as JSONArray?
            val len = jsonArray.length()
            for (i in 0 until len) {
                list.add(jsonArray[i].toString())
            }
            if(MainActivity.debag){Log.d("tag","呼び出された　リスト名は$list")}

            return list
        }

        fun levelUP(context: Context,score: Int): Pair<Int,Int>{
            val prefs = context.getSharedPreferences("preferences_key_sample", Context.MODE_PRIVATE)
            val level = prefs.getInt(context.getString(R.string.score_level),0)
            var nLevel = 0
            var tScore = prefs.getInt(context.getString(R.string.score_totalScore),0)
            val levelTable = arrayListOf<Int>(
                100,1120,2030,3080,4270,5600,7070,8680,10430,12320,14350,16520,18830,21280,23870,
                26600,29470,32480,35630,38920,42350,45920,49630,53480,57470,61600,65170,68180,71330,74620,
                78120,999999
            )
            tScore = tScore + score

            for (i in 0 until levelTable.count()-1){
                if (tScore < levelTable[i]){
                    nLevel = i
                    break
                }
            }
            val shardPrefEditor = prefs.edit()
            shardPrefEditor.putInt(context.getString(R.string.score_totalScore),tScore)
            shardPrefEditor.putInt(context.getString(R.string.score_level),nLevel)
            shardPrefEditor.apply()
            return level to nLevel
        }
    }
}