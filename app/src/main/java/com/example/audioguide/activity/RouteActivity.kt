package com.example.audioguide.activity

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.audioguide.R
import com.example.audioguide.databinding.ActivityRouteBinding
import com.example.audioguide.fragment.GoogleMapFragment
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * Активность текущего маршрута
 */
class RouteActivity : AppCompatActivity() {
    private lateinit var route: Route
    private lateinit var binding: ActivityRouteBinding
    private lateinit var mediaPlayer: MediaPlayer

    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRouteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        route = getSerializable(this, Route::class.java)

        mediaPlayer = MediaPlayer.create(this, route.audioPath[level].toUri())
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

        binding.buttonPlay.setOnClickListener() {
            mediaPlayer.start()
        }
        binding.buttonStop.setOnClickListener {
            mediaPlayer.pause()
        }

        if (level == route.listPathsImage.size - 1){
            binding.openMapBtn.isEnabled = false
        }else {

            binding.openMapBtn.setOnClickListener {
                binding.placeHolder.removeAllViews()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.placeHolder, GoogleMapFragment.newInstance(route, level))
                    .addToBackStack(null)
                    .commit()
            }
        }
        initSeekBar()

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

    /**
     * Init seek bar
     * Иницаилазция бара аудио
     */
    private fun initSeekBar() {
        binding.seekbarMusic.max = mediaPlayer.duration

        binding.seekbarMusic.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                try {
                    binding.seekbarMusic.progress = mediaPlayer.currentPosition
                    delay(1000) //
                } catch (e: Exception) {
                    binding.seekbarMusic.progress = 0
                    e.printStackTrace()
                }
            }
        }

    }
}