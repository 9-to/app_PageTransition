package com.example.pagetransition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val dai = 0
    val chu = 1
    val sho = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val id = intent.getIntExtra("MY_CHOICE", 0)
        val omikuji : Int
        omikuji = when(id){
            R.id.imagebutton1 -> {
                chooseImage.setImageResource(R.drawable.sushi_chakinzushi1)
                dai
            }
            R.id.imagebutton2 -> {
                chooseImage.setImageResource(R.drawable.inarizushi)
                chu
            }
            R.id.imagebutton3 -> {
                chooseImage.setImageResource(R.drawable.sushi_chakinzushi2)
                sho
            }
            else -> sho
        }
        val sushi = getSushi()
        when(sushi){
            dai -> sushiImage.setImageResource(R.drawable.sushi_aburi_toro)
            chu -> sushiImage.setImageResource(R.drawable.sushi_nodoguro)
            sho -> sushiImage.setImageResource(R.drawable.sushi_kai_hokkigai)
        }
        val gameResult = (sushi-omikuji+3)%3
        when (gameResult){
            0 -> resultText.setText(R.string.result1)
            1 -> resultText.setText(R.string.result2)
            2 -> resultText.setText(R.string.result3)
        }
        backButton.setOnClickListener{finish()}
        saveData(omikuji,sushi,gameResult)
    }
   private fun saveData(omikuji: Int, sushi: Int, gameResult: Int){
       val pref = PreferenceManager.getDefaultSharedPreferences(this)
       val gameCount = pref.getInt("GAME_COUNT", 0)
       val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
       val lastSushi = pref.getInt("LAST_SUSHI", 0)
       val lastGameResult = pref.getInt("GAME_RESULT", -1)

       val edtWinningStreakCount: Int =
           when{
               lastGameResult == 2 && gameResult == 2 -> winningStreakCount + 1
               else -> 0
           }
       val editor = pref.edit()
       editor.putInt("GAME_COUNT", gameCount + 1)
           .putInt("WINNING_STREAK_COUNT", edtWinningStreakCount)
           .putInt("LAST_OMIKUJI", omikuji)
           .putInt("lAST_SUSHI", sushi)
           .putInt("BEFORE_LAST_SUSHI", lastSushi)
           .putInt("GAME_RESULT", gameResult)
           .apply()
   }
    private fun getSushi():Int{
        var sushi = (Math.random()*3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastOmikuji = pref.getInt("LAST_OMIKUJI", 0)
        val lastSushi = pref.getInt("LAST_SUSHI", 0)
        val beforeLastSushi = pref.getInt("BEFORE_LAST_SUSHI", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)
        if (gameCount == 1){
            if(gameResult == 2){
                while(lastSushi == sushi){
                    sushi = (Math.random()*3).toInt()
                }
            }else if(gameResult == 1){
                sushi = (lastOmikuji -1 +3) % 3
            }
        } else if(winningStreakCount > 0){
            if(beforeLastSushi == lastSushi){
                while(lastSushi == sushi){
                    sushi = (Math.random()*3).toInt()
                }
            }
        }
        return sushi
    }
}