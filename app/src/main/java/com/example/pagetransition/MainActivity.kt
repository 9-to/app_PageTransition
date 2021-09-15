package com.example.pagetransition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imagebutton1.setOnClickListener{ onOmikujiButtonTapped(it)}
        imagebutton2.setOnClickListener{ onOmikujiButtonTapped(it)}
        imagebutton3.setOnClickListener{ onOmikujiButtonTapped(it)}

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit{
            clear()
        }
    }
    fun onOmikujiButtonTapped(view: View?){
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("MY_CHOICE", view?.id)
        startActivity(intent)
    }
}