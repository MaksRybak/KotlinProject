package com.example.kursova

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_post_details.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import javax.xml.transform.OutputKeys

class PostDetailsActivity : AppCompatActivity() {

    private var postId:String? = null //отримає від intent, було передано в intent від Adapter Post
    private var TAG = "POST_DETAILS_TAG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        //отримаємо ідентифікатор повідомлення з наміру
        postId = intent.getStringExtra("postId")
        Log.d(TAG, "onCreate: $postId")

        //налаштування
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        loadPostDetails()

    }

    private fun loadPostDetails() {
        val url = ("https://www.googleapis.com/blogger/v3/blogs/${Constants.BLOG_ID}/posts/$postId?key=${Constants.API_KEY}")
        Log.d(TAG, "LoadPostDetails: $url")
        //запит на api
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            //успішно отримана відповідь
            Log.d(TAG, "loadPostDetails: $response")
            // Відповідь подано у формі об’єкта JSON
            try {
                val jsonObject = JSONObject(response)

                //-----ОТРИМАТИ Дані------------
                val title = jsonObject.getString("title")
                val published = jsonObject.getString("published")
                val content = jsonObject.getString("content")
                val url = jsonObject.getString("url")
                val displayName = jsonObject.getJSONObject("author").getString("displayName")
                //cтандартний час GMT за належним форматом
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")//convert from ...
                val dateFormat2 = SimpleDateFormat("dd/MM/yyyy K:mm a")//convert to .../2020V
                var formattedDate = ""
                try {
                    val date = dateFormat.parse(published)
                    formattedDate = dateFormat2.format(date)
                } catch (e: Exception) {
                    //у випадку винятку встановіть те саме, що ми отримали від api
                    formattedDate = published
                    e.printStackTrace()
                }
                //----Встановимо дані ------
                //actionBar.subtitle = title
                titleTv.text = title
                publishInfoTv.text =
                    "By $displayName $formattedDate" //e.g. By Rybak Maksym 00.00.0000
                //вміст містить веб-сторінку, таку як html, тому завантажить у webView
                webView.loadDataWithBaseURL(null, content, "text/html", OutputKeys.ENCODING, null)


            } catch (e: Exception) {
                Log.d(TAG, "loadPostDetails: ${e.message}")
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }) { error ->
            //не вдалося отримати відповідь, показати повідомлення про помилку
            Log.d(TAG, "loadPostDetails: ${error.message}")
            Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
        }

        //Додасть запит до черги
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()//перейде до попередньої активності з того місця, куди ми прибули, коли клацнула кнопка назад кнопки дії
        return super.onSupportNavigateUp()
    }
}