package com.example.kursova

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import java.text.SimpleDateFormat

// конструктор оголошень
class AdapterPost(
    private val context:Context,
    private val postArrayList: ArrayList<ModelPost>
):RecyclerView.Adapter<AdapterPost.HolderPost>(){
    /* Клас ViewHolder, містить initsUI подання row_post*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPost {
        //надуваємо layout row_post.xml
        val view = LayoutInflater.from(context).inflate(R.layout.row_post, parent, false)
        return HolderPost(view)
    }

    override fun onBindViewHolder(holder: HolderPost, position: Int) {
        //отримуємо дані, встановлюємо дані, формуємо дані, обробляємо клацання тощо
        val model = postArrayList[position] //отримуємо дані в певній позиції / індексі списку

        //Получаємо дані
        val authorName = model.authorName
        val content = model.content // у форматі HTML, ми перетворимо на простий текст
        val id = model.id
        val published = model.published //дата публікації, потрібно форматувати
        val selfLink = model.selfLink
        val title = model.title
        val updated = model.updated
        val url = model.url// дані відредаговано

        //Перетворення вмісту HTML на простий
        val document = Jsoup.parse(content)
        try {
            //отримаємо зображення, в публікації може бути багатопластинка або зображення, спробуваємо отримати перше
            val elements = document.select("img")
            val image = elements[0].attr("src")
            //Вибрати зображення
            Picasso.get().load(image).placeholder(R.drawable.ic_image_black).into(holder.imageIv)
        }
        catch (e:Exception){
            //виняток при отриманні зображення, може бути через відсутність зображення у дописі тощо
            holder.imageIv.setImageResource(R.drawable.ic_image_black)
        }
        //format date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")//convert from ...
        val dateFormat2 = SimpleDateFormat("dd/MM/yyyy K:mm a")//convert to .../2020V
        var formattedDate = ""
        try{
            val date = dateFormat.parse(published)
            formattedDate = dateFormat2.format(date)
        }
        catch (e:Exception)
        {
            //у випадку винятку встановимо те саме, що ми отримали від api
            formattedDate = published
            e.printStackTrace()
        }

        holder.titleTv.text = title
        holder.descriptionTv.text = document.text()
        holder.publishInfoTv.text = "By $authorName $formattedDate"

        /* обробимо клік елемента
         * покажемо деталі публікації
         * передамо postId, використовуючи намір показати його деталі*/
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PostDetailsActivity::class.java)
            intent.putExtra("postId", id) //ключ, значення: передамо ідентифікатор допису, який буде використовуватися для отримання деталей допису в PostDetailsActivity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        //повертає кількість елементів / записів / списку_розмір
       return postArrayList.size
    }

    inner class HolderPost(itemView: View):RecyclerView.ViewHolder(itemView){
        //init UI Views
        var moreBtn : ImageButton = itemView.findViewById(R.id.moreBtn)
        var titleTv :TextView = itemView.findViewById(R.id.titleTv)
        var publishInfoTv :TextView = itemView.findViewById(R.id.publishInfoTv)
        var imageIv :ImageView = itemView.findViewById(R.id.imageIv)
        var descriptionTv:TextView = itemView.findViewById(R.id.desciptionTv)
    }



}