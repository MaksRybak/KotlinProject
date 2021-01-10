package com.example.kursova

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_loyaut.*

class ContentActivity:AppCompatActivity(){ //Наслідування від класу AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_loyaut) //Запускає layout з контентом
        tvTitle.text = intent.getStringExtra("title") //Приймає по ключу від передаваємого значення
        textView3.text = intent.getStringExtra("content") //з myAdapter
        im.setImageResource(intent.getIntExtra("image",R.mipmap.ic_test))
    }
}