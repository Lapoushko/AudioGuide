package com.example.audioguide.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.audioguide.databinding.ActivityConfirmationBinding
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage
import java.io.Serializable

/**
 * Активность подтверждения выбора маршрута
 */
class ConfirmationActivity : AppCompatActivity() {
    private lateinit var route: Route
    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        route = getSerializable(this, "route", Route::class.java)
        setupUI()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.confirm)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    /**
     * Установка UI
     */
    private fun setupUI() {
        binding.nameRoute.text = route.nameRoute
        binding.descriptionRoute.text = route.descriptionText
        val loaderImage = LoaderImage()
        loaderImage.loadImage(route.listPathsImage[0], binding.routeImage)

        binding.confirmButton.setOnClickListener {
            val context = this
            val intent = Intent(context, RouteActivity::class.java)
            intent.putExtra("route", route)// Передача объекта Route
            context.startActivity(intent)
        }
        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    /**
     * Передача данных из предыдущей активности
     */
    private fun <T : Serializable?> getSerializable(
        activity: Activity,
        name: String,
        clazz: Class<T>
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.intent.getSerializableExtra(name, clazz)!!
        } else {
            activity.intent.getSerializableExtra(name) as T
        }
    }
}