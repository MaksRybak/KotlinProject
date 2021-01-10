package com.example.kursova

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_blogger.*
import org.json.JSONObject

class BloggerActivity : AppCompatActivity() {

    private var url = "" //повна URL-адреса для отримання повідомлення
    private var nextToken = ""

    private  lateinit var postArrayList : ArrayList<ModelPost>
    private  lateinit var adapterPost: AdapterPost

    private  lateinit var progressDialog : ProgressDialog
    private val TAG = "MAIN_TAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blogger)

        //Діалогове вікно прогресу налаштування
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Будь ласка зачекайте")

        //init та очищаємо список перед додаванням даних до нього
        postArrayList = ArrayList()
        postArrayList.clear()

        loadPosts()

        //обробляємо клацання, завантажуємо більше повідомлень
        loadMoreBtn.setOnClickListener{
            loadPosts()
        }
    }

    private fun loadPosts() {
        progressDialog.show()
        url = when(nextToken){
            "" ->{
                Log.d(TAG, "LoadPosts : NextPageToken is empty, more posts")
                ("https://www.googleapis.com/blogger/v3/blogs/${Constants.BLOG_ID}/posts?maxResult=${Constants.MAX_RESULTS}&key=${Constants.API_KEY}")//Берем силку з PostMan
            }
            "end" -> {
                Log.d(TAG, "LoadPosts: Next page token is end, no more posts i.e. loaded all posts")
                Toast.makeText(this, "No more posts...", Toast.LENGTH_SHORT).show()
                ("https://www.googleapis.com/blogger/v3/blogs//${Constants.BLOG_ID}/posts?maxResult=${Constants.MAX_RESULTS}&key=${Constants.API_KEY}")//Вставновлюємо значення з файла Constants
                progressDialog.dismiss()
                return
            }
            else ->{
                Log.d(TAG, "LoadPosts: NextPage Token: $nextToken")
                ("https://www.googleapis.com/blogger/v3/blogs/${Constants.BLOG_ID}/posts?maxResult=${Constants.MAX_RESULTS}&pageToken=$nextToken&key=${Constants.API_KEY}")
            }
        }

        Log.d(TAG, "LoadPosts: URL: $url")
        //дані запиту, Метод - GET
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            //ми отримали відповідь, тож спочатку закриваємо діалогове вікно
            progressDialog.dismiss()
            Log.d(TAG, "LoadPosts: $response")

            try {
                //ми маємо відповідь як об’єкт JSON
                val jsonObject = JSONObject(response)
                try {
                    nextToken = jsonObject.getString("nextPageToken")
                    Log.d(TAG, "laodPasts: NewPageToken: $nextToken")
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this, "Reached end of the page", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "loadPosts: Reached end of the page")
                    nextToken = "end"
                }
                //отримаємо дані масиву json з елементів json
                val jsonArray = jsonObject.getJSONArray("items")
                //продовжуємо отримувати дані untull завершаємо все
                for (i in 0 until jsonArray.length()) {
                    try {
                        val jsonObject01 = jsonArray.getJSONObject(i)
                        val id = jsonObject01.getString("id")
                        val title = jsonObject01.getString("title")
                        val content = jsonObject01.getString("content")
                        val published = jsonObject01.getString("published")
                        val updated = jsonObject01.getString("updated")
                        val url = jsonObject01.getString("url")
                        val selfLink = jsonObject01.getString("selfLink")
                        val authorName =
                            jsonObject01.getJSONObject("author").getString("displayName")
                        //val image = jsonObject01.getJSONObject("author").getString("image")
                        //встановлюємо дані
                        val modelPost = ModelPost(
                            "$authorName",
                            "$content",
                            "$id",
                            "$published",
                            "$selfLink",
                            "$title",
                            "$updated",
                            "$url"
                        )

                        //додаємо дані до списку
                        postArrayList.add(modelPost)
                    } catch (e: Exception) {
                        Log.d(TAG, "LoadPosts: 1 ${e.message}")
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                //адаптер налаштування
                adapterPost = AdapterPost(this@BloggerActivity, postArrayList)
                //встановлюємо адаптер на RecyclerView
                postsRv.adapter = adapterPost
                progressDialog.dismiss()


            } catch (e: Exception) {
                Log.d(TAG, "LoadPosts: 2 ${e.message}")
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }


        }, { error ->
            Log.d(TAG, "loadPosts: ${error.message}")
            Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
        })

        //додаємо запит в чергу
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

    }
}