package com.sangjin.secret_diary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import com.sangjin.secret_diary.databinding.ActivityMainBinding

//비밀번호를 입력했을때 맞는지 체크
//맞으면 다음액티비티로 넘긴다.
//비밀번호를 변경할때 기존의 비밀번호가 맞아야 변경이 가능해야함.
//비밀번호를 설정할때 그 비밀번호를 저장해야한다.
//비밀번호를 변경했으면 그 비밀번호를 새로 저장한다.

class MainActivity : AppCompatActivity() {

    private val openBtn by lazy { findViewById<Button>(R.id.openBtn) }
    private val changeBtn by lazy { findViewById<Button>(R.id.changeNumberBtn) }
    private val numberPicker1 by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker2 by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private val numberPicker3 by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }
    }
    private var changeMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openBtn.setOnClickListener {

            val passwordPrefence = getSharedPreferences("password", Context.MODE_PRIVATE)
            val savedPassword = passwordPrefence.getString("password", "000")
            val password = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changeMode) {
                Toast.makeText(this, "비밀번호 변경모드 입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (savedPassword == password) {
                startActivity(Intent(this, diaryActivity::class.java))
            } else {
                errorMessage()
                return@setOnClickListener
            }
        }

        changeBtn.setOnClickListener {

            val passwordPrefence = getSharedPreferences("password", Context.MODE_PRIVATE)
            val savedPassword = passwordPrefence.getString("password", "000")
            val password = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changeMode) { //변경가능상태
                passwordPrefence.edit {
                    putString("password", password)
                    commit()
                }
                changeBtn.setBackgroundColor(Color.parseColor("#F3137A"))
                changeMode = false
            } else { //변경가능상태가 아닐때
                if (password == savedPassword) {
                    changeBtn.setBackgroundColor(Color.BLACK)
                    changeMode = true
                } else {
                    errorMessage()
                    return@setOnClickListener
                }
            }
        }
    }

    private fun errorMessage() {
        AlertDialog.Builder(this)
            .setTitle("오류")
            .setMessage("비밀번호가 틀렸습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}
