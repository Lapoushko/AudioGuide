package com.example.audioguide.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.audioguide.R
import com.example.audioguide.dao.RouteDAO
import com.example.audioguide.service.RouteServiceImpl
import com.example.audioguide.utils.RouteListControllerImpl

/**
 * Класс, запускающий меню экскурсий
 */
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var routeListController: RouteListControllerImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //включить контент из activity main

        val routeDao = RouteDAO(this,null)
        val routeService = RouteServiceImpl(routeDao)
        recyclerView = findViewById(R.id.recyclerView)
        routeListController = RouteListControllerImpl(routeService)
        routeListController.setupRecyclerView(recyclerView)
//        for (i in routeService.getRoute()) {
//            val toast = Toast.makeText(applicationContext, i.nameRoute, Toast.LENGTH_SHORT)
//            toast.show()
//        }

//        val button = findViewById<Button>(R.id.button2)
//        button.setOnClickListener {
//            startActivity(Intent(this@MainActivity, Route::class.java)) //запуск activity route
//        }
        //В Android Studio код, который вы привели, использует ViewCompat.setOnApplyWindowInsetsListener
        // для обработки системных отступов (insets) в приложении. Это позволяет вам
        // динамически изменять отступы ваших представлений (views) в зависимости от
        // системных элементов интерфейса, таких как статусная строка, панель навигации и т.д.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}