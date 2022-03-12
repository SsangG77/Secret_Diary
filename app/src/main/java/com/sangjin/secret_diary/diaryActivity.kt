package com.sangjin.secret_diary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

//작성한 글을 저장해야한다.
//글의 내용이 변경될때마다가 아니라 0.5초동안 변경이 없으면 저장한다.
//다른 스레드에서 SharedPreference를 실행하게 하고 메인스레드의 핸들러를 이용해서 0.5초마다 이 스레드를 종작시키게 한다.


class diaryActivity : AppCompatActivity() {
    private val diaryText by lazy { findViewById<EditText>(R.id.diaryText_et) }
    val hander = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)
        val pref = getSharedPreferences("text", Context.MODE_PRIVATE)
        val runnable = Runnable {
            pref.edit {
                putString("text", diaryText.text.toString())
            }
        }
        diaryText.setText(pref.getString("text", ""))
        diaryText.addTextChangedListener {
            hander.removeCallbacks(runnable)
            hander.postDelayed(runnable, 500)
        }
    }
}