package com.example.kursova

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(listArray: ArrayList<ListItem_Teacher>, context:Context):RecyclerView.Adapter<MyAdapter.ViewHolder>() { //Констрпукутор із масива новостворенного типу і контекст
                                                                                                                        //І наслідуєм від RecyclerView.Adapter
                                                                                                                        //Також добавили клас ViewHolder, а також методи наслідуванного класа
    var listArrayR = listArray //Нова змінна для того, щоб можна було відобразити кількість елементів в RecyclerView
    var contextR = context //Також робимо змінну Context, яка буде видима на рівні класу

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvTitle = view.findViewById<TextView>(R.id.text_shuvar) //Находимо назву
        val im = view.findViewById<ImageView>(R.id.image_shuvar) // находимо зображення
        val tvContent = view.findViewById<TextView>(R.id.tvContent) // находимо текст контенту

        fun bind(listItem:ListItem_Teacher, context: Context){ //метод для заповнення всіх елементів, метод буде запускатися в onBindViewHolder
            tvTitle.text = listItem.titlteText //Заповняємо наші змінні із масива
            im.setImageResource(listItem.image_id)
            tvContent.text = listItem.contentText
            itemView.setOnClickListener(){ // Для можливості натискання на елемент

                Toast.makeText(context, "Pressed : ${tvTitle.text}",Toast.LENGTH_SHORT).show() // відображаємо, що нажали на елемент
                val i = Intent(context,ContentActivity::class.java).apply { //apply-для передавання значення іншому файлу
                    putExtra("title", tvTitle.text.toString())  //Передаємо значення назви, контенту і фотграфії
                    putExtra("content", tvContent.text.toString()) //Перший параметр ключ, по якому ми будемо ловити
                    putExtra("image", listItem.image_id)            // По другому самий зміст
                }
                context.startActivities(arrayOf(i)) //Переходиом на нове актівіті
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //Метод для "надування" і заповнення масива
        val inflater = LayoutInflater.from(contextR) //'Малюємо' відносно контекста
        return ViewHolder(inflater.inflate(R.layout.item_loyaut,parent, false)) // Находимо кожний елемент
    }


    override fun getItemCount(): Int { //Метод який вертає скільки елементів буде в масиві
        return  listArrayR.size //Розмір з масива
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listItem = listArrayR.get(position) //Вибираємо позицію
        holder.bind(listItem, contextR) //Викликаємо метод bind, з відповідним елементом по позиції
    }
    fun updateAdapter(listArray: List<ListItem_Teacher>) // Функція для обновлення адаптера
    {
        listArrayR.clear() // очищажмо старий масив
        listArrayR.addAll(listArray) //передаємо новий масив
        notifyDataSetChanged() //Сповіщаємо адаптеру про зміни
    }
}