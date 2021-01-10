package com.example.kursova

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SocialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_loyaut) //Обираємо відображаючий контент
    }

    fun instClick(view: View){ //Кнопка для відкриття інстаграму
        val url = "https://www.instagram.com/electronics_live/?hl=ru" //Силка на сайт інст факультета
        val intent = Intent(Intent.ACTION_VIEW) //Створюємо новий intent
        intent.data = Uri.parse(url) //Передаємо, що буде відкрита силка
        startActivity(intent) //Запускаємо
    }
    // Всі інші клавіші будуть робити за тим самим принципом
    fun telegClick(view: View){
        val url = "https://t.me/electronics_cs"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun faceClick(view: View){
        val url = "https://www.facebook.com/electronics.lnu/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun webClick(view: View){
        val url = "https://electronics.lnu.edu.ua"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    fun gmailClick(view: View){
        val url = "https://electronics.lnu.edu.ua"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}