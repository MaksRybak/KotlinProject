package com.example.kursova

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    var adapter:MyAdapter? = null
    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(this)
        var list = ArrayList<ListItem_Teacher>()

        var rcView: RecyclerView = findViewById(R.id.rcView)

        list.addAll(fillArrays(resources.getStringArray(R.array.teacher),get_imageId(R.array.teacher_image),resources.getStringArray(R.array.teacher_content)))

        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(this)
        drawerLayout.openDrawer(GravityCompat.START)
        adapter = MyAdapter(list,this)
        rcView.adapter = adapter
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.id_teacher -> {
                Toast.makeText(this, "Teachers",Toast.LENGTH_SHORT)
                adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.teacher),get_imageId(R.array.teacher_image),resources.getStringArray(R.array.teacher_content))) //Заповняємо адаптер масивом
            }
            R.id.id_students ->{
                Toast.makeText(this, "Students",Toast.LENGTH_SHORT)
                adapter?.updateAdapter(fillArrays(resources.getStringArray(R.array.students),get_imageId(R.array.student_image),resources.getStringArray(R.array.students)))
            }
            R.id.id_position -> {
                Toast.makeText(this,"Position",Toast.LENGTH_SHORT)
                var i = Intent(this, MapsActivity::class.java)
                startActivity(i)
            }
            R.id.id_price -> {
                Toast.makeText(this, "Price",Toast.LENGTH_SHORT)
                var i = Intent(this, PriceActivity::class.java)
                startActivity(i)
            }
            R.id.id_social ->
            {
                Toast.makeText(this, "Social",Toast.LENGTH_SHORT)
                var i = Intent(this, SocialActivity::class.java)
                startActivity(i)
            }
            R.id.id_blogger ->{
                Toast.makeText(this, "Blog",Toast.LENGTH_SHORT)
                var i = Intent(this, BloggerActivity::class.java)
                startActivity(i)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



    fun fillArrays(titleArray : Array<String>, imageArray:IntArray,contentArray:Array<String>):List<ListItem_Teacher> //Приймає 3 значення
    {
        var listItemArray = ArrayList<ListItem_Teacher>()
        for (n in 0..titleArray.size-1) //пробігаємось по всіх елементах в масиві
        {
            var listItem = ListItem_Teacher(imageArray[n], titleArray[n],contentArray[n]) //Берем з кожної позиції
            listItemArray.add(listItem) //Заповняє масив
        }
        return listItemArray // Вертає заповнене значення
    }




    fun get_imageId(imageArrayId : Int): IntArray // Функція для розшифровування зображення
    {
        var tArray:TypedArray = resources.obtainTypedArray(imageArrayId) //За допомогою цієї змінни будем розшифровувати
        val count = tArray.length() //Довжина
        val ids = IntArray(count) //Масив розміром довжини
        for(i in ids.indices)
        {
            ids[i] = tArray.getResourceId(i,0) //Берем індетифікатор і заповнюємо масив
        }
        tArray.recycle() // для можливості перевикористання
        return ids //вертаємо масив
    }
}


