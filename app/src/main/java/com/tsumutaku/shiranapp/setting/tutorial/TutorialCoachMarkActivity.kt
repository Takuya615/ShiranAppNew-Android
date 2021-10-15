package com.tsumutaku.shiranapp.setting.tutorial

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.TextView
import com.tsumutaku.shiranapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.Circle
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.SimpleTarget

class TutorialCoachMarkActivity(context: Context) {
    val prefs = context.getSharedPreferences( "preferences_key_sample", Context.MODE_PRIVATE)
    val Tuto1 : Boolean = prefs.getBoolean("Tuto1",false)
    val Tuto2 : Boolean = prefs.getBoolean("Tuto2",false)
    val Tuto3 : Boolean = prefs.getBoolean("Tuto3",false)
    val g : SharedPreferences.Editor = prefs.edit()

    //メイン画面でのコーチマーク
    fun CoachMark1(activity: Activity, context: Context){
        if(!Tuto1){
            g.putBoolean("Tuto1", true).commit()

            val target1 = activity.findViewById<FloatingActionButton>(R.id.fab)
            val Target1 = sreateCircleUI(target1,activity,"　１日１回\n ココからカメラへ移動します","",0f,-2f)

            // コーチマークを作成
            Spotlight.with(activity)
                // コーチマーク表示される時の背景の色
                .setOverlayColor(R.color.colorCoachMark)
                // 表示する時間
                .setDuration(1000L)
                // 表示するスピード
                .setAnimation(DecelerateInterpolator(1f))
                // 注目されたいところ（複数指定も可能）
                .setTargets(Target1)
                // 注目されたいところ以外をタップする時に閉じられるかどうか
                .setClosedOnTouchedOutside(true)
                // コーチマーク表示される時になんかする
                .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
                    override fun onStarted() {
                        //Toast.makeText(context, "spotlight is started", Toast.LENGTH_SHORT).show()
                    }
                    override fun onEnded() {
                        //val intent= Intent(context, GoalSettingActivity::class.java)
                        //activity.startActivity(intent)
                        //activity.recreate()
                    }
                })
                .start()
        }else if(!Tuto2 && Tuto3){
            g.putBoolean("Tuto2", true).commit()
            val target1 = activity.findViewById<FloatingActionButton>(R.id.fab)
            val Target1 = sreateCircleUI(target1,activity,"　チュートリアル達成！！",
                "ここまではチュートリアルです\nもう一度ここをタップ\n１日１回 敵が現れ、たおすとボーナス報酬がでます。",0f,-4f)
            Spotlight.with(activity)
                .setOverlayColor(R.color.colorCoachMark)
                .setDuration(1000L)
                .setAnimation(DecelerateInterpolator(1f))
                .setTargets(Target1)
                .setClosedOnTouchedOutside(true)
                .start()
        }
    }

    //メイン画面でタスクボタンのコーチマーク
    /*
    fun CoachMark2(activity: Activity, context: Context){
        if(!Tuto2){
            g.putBoolean("Tuto2", true)
            g.commit()

            val target1 = activity.findViewById<BottomNavigationView>(R.id.nav_view)
            val x = target1.width.toFloat()
            val y = target1.height.toFloat()
            val Target1 = sreateUI(target1,activity,"　カンタンすぎましたか？",
                "より毎日つづけやすくするため、１回の撮影時間はほんの数秒です。\n ただし、¸この時間は３日おきに自動で伸びていきます。",-x,-y,-8f)

            val Target2 = sreateUI(target1,activity,"　継続できる人に", "週4日以上を、６０日間つづけられれば、そのまま習慣になる確率がぐっと上がります。"
                ,-x,-y,-8f)

            // コーチマークを作成
            Spotlight.with(activity)
                // コーチマーク表示される時の背景の色
                .setOverlayColor(R.color.colorCoachmark)
                // 表示する時間
                .setDuration(500L)
                // 表示するスピード
                .setAnimation(DecelerateInterpolator(1f))
                // 注目されたいところ（複数指定も可能）
                .setTargets(Target1,Target2)
                // 注目されたいところ以外をタップする時に閉じられるかどうか
                .setClosedOnTouchedOutside(true)
                .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
                    override fun onStarted() {    }
                    override fun onEnded() {    }
                })
                .start()
        }
    }*/



    //カメラ撮影画面でのコーチマーク
    fun CoachMark3(activity: Activity, target:View,target2:View,target3:View){
        if(!Tuto3){
            g.putBoolean("Tuto3", true)
            g.commit()

            //val target = activity.findViewById<TextView>(R.id.timer)
            val Target = sreateUI(target,activity,"タイマー",
                "その日のタイムリミットが表示されます\n" +
                        "(初回は５秒)",5f,5f,2f)

            //val target2 = activity.findViewById<ImageButton>(R.id.capture_button)
            val Target2 = sreateCircleUI(target2,activity,"タップしてはじめる",
                "３秒後にスタート。\n タイムリミットまでスコアを記録します。\n" +
                        "(録画機能はありません)",5f,-2f)

            //val target3 = activity.findViewById<TextView>(R.id.scoreBoard)
            val Target3 = sreateUI(target3,activity,"スコア",
                "STARTすると、ココにスコアが表示されます。",5f,5f,-4f)


            // コーチマークを作成
            Spotlight.with(activity)
                // コーチマーク表示される時の背景の色
                .setOverlayColor(R.color.colorCoachMark)
                // 表示する時間
                .setDuration(1000L)
                // 表示するスピード
                .setAnimation(DecelerateInterpolator(1f))
                // 注目されたいところ（複数指定も可能）
                .setTargets(Target,Target2,Target3)//
                // 注目されたいところ以外をタップする時に閉じられるかどうか
                .setClosedOnTouchedOutside(true)
                // コーチマーク表示される時になんかする
                .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
                    override fun onStarted() {
                    }
                    override fun onEnded() {
                    }
                })
                .start()
        }

    }



    //引数（ボタン、アクティビティ、タイトル、本文、光X軸より広く、光Y軸より広く、文字列下なら正、文字列上なら負のFloat）　　四角の場合
    fun sreateUI(target: View, activity: Activity, title:String?, scrip:String, plusX:Float, plusY:Float, plusP:Float): SimpleTarget {

        val targetLocation = IntArray(2)
        target.getLocationInWindow(targetLocation)
        val targetX = targetLocation[0] + target.width/2f
        val targetY = targetLocation[1] + target.height/2f

        val UIwidth = target.width.toFloat() + plusX
        val UIheight = target.height.toFloat() + plusY

        //val OverLayPointY = targetLocation[1] + target.height * plusP

        // 注目されたいところを設定する
        val firstTarget = SimpleTarget.Builder(activity)
            .setPoint(targetX,targetY)//ハイライトの位置
            .setShape(RoundedRectangle(UIheight,UIwidth,25f))//ハイライトの大きさ
            .setTitle(title)
            .setDescription(scrip)
            .setOverlayPoint(2f,targetLocation[1] + target.height * plusP )//文字列の位置
            .build()

        return firstTarget
    }

    //引数（ボタン、アクティビティ、タイトル、本文、光円半径、文字列下なら正、文字列上なら負のFloat）　　円の場合
    fun sreateCircleUI(target: View, activity: Activity, title:String?, scrip:String, radius:Float, pointY:Float): SimpleTarget {

        val targetLocation = IntArray(2)
        target.getLocationInWindow(targetLocation)
        val targetX = targetLocation[0] + target.width/2f
        val targetY = targetLocation[1] + target.height/2f
        val targetRadius = 78f + radius
        val firstTarget = SimpleTarget.Builder(activity)
            .setPoint(targetX,targetY)//ハイライトの位置
            .setShape(Circle(targetRadius))//ハイライトの大きさ
            .setTitle(title)
            .setDescription(scrip)
            .setOverlayPoint(2f, targetLocation[1] + target.height * pointY)//文字列の位置
            .build()


        return firstTarget
    }


    fun reset(){
        //g.putBoolean("Tuto0", false)
        g.putBoolean("Tuto1", false)
        g.putBoolean("Tuto2", false)
        g.putBoolean("Tuto3", false)
        g.putBoolean("Tuto4", false)
        g.putBoolean("Tuto5", false)
        g.commit()
    }
}