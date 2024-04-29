package com.example.audioguide.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.audioguide.databinding.ActivityRouteBinding
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage
import java.io.Serializable

/**
 * Активность текущего маршрута
 */
class RouteActivity : AppCompatActivity() {
    private lateinit var route: Route
    private lateinit var binding: ActivityRouteBinding

    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRouteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        route = getSerializable(this, Route::class.java)

        binding.nextButton.setOnClickListener {
            loadNextRoute()
        }
        setupUI()
    }

    /**
     * Установка UI
     */
    private fun setupUI() {
        binding.routeName.text = route.nameRoute
        binding.description.text = route.listInformation[level]

        val loaderImage = LoaderImage()
        loaderImage.loadImage(route.listPathsImage[level], binding.routeImage)

    }

    /**
     * Передача данных из предыдущей активности
     */
    private fun <T : Serializable?> getSerializable(
        activity: Activity,
        clazz: Class<T>
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.intent.getSerializableExtra("route", clazz)!!
        } else {
            activity.intent.getSerializableExtra("route") as T
        }
    }

    /**
     * Загрузка следующего контента
     */
    private fun loadNextRoute() {
        if (level < route.listInformation.size - 1) {
            level++
        }
        if (level >= route.listInformation.size - 1) {
            binding.nextButton.text = "Завершить экскурсию"
            binding.nextButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        setupUI()
    }
}