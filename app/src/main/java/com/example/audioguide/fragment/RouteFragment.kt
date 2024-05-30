package com.example.audioguide.fragment

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.audioguide.activity.MainActivity
import com.example.audioguide.databinding.FragmentRouteBinding
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage

/**
 * Фрагмент маршрута
 *
 * @constructor Create empty Route fragment
 */
class RouteFragment : Fragment() {

    private lateinit var route: Route
    private var level = 0
    private lateinit var binding: FragmentRouteBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(layoutInflater, container, false)

        mediaPlayer = MediaPlayer.create(requireContext(), route.audioPath[level].toUri())
        binding.nextButton.setOnClickListener {
            loadNextRoute()
        }
        setupUI()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(route: Route, pos: Int) =
            RouteFragment().apply {
                this.route = route
                level = pos
            }
    }

    /**
     *      Установка UI
     *      */
    private fun setupUI() {
        binding.routeName.text = route.nameRoute
        binding.description.text = route.listInformation[level]

        binding.buttonPlay.setOnClickListener {
            mediaPlayer.start()
        }
        binding.buttonStop.setOnClickListener {
            mediaPlayer.pause()
        }
        mediaPlayer.seekTo(0)
        initSeekBar()

        val loaderImage = LoaderImage()
        loaderImage.loadImage(route.listPathsImage[level], binding.routeImage)

    }

    /**
    Загрузка следующего контента
     **/
    private fun loadNextRoute() {
        if (level < route.listInformation.size - 1) {
            level++
            mediaPlayer = MediaPlayer.create(requireContext(), route.audioPath[level].toUri())
        }
        if (level >= route.listInformation.size - 1) {
            binding.nextButton.text = "Завершить экскурсию"
            binding.nextButton.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }
        setupUI()
    }

    /**
    Init seek bar
    Иницаилазция бара аудио
     **/
    private fun initSeekBar() {
        binding.seekbarMusic.max = mediaPlayer.duration

        binding.seekbarMusic.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        mediaPlayer.setOnPreparedListener {
            binding.seekbarMusic.progress = mediaPlayer.currentPosition
        }

        mediaPlayer.setOnCompletionListener {
            // Обработка завершения воспроизведения
        }
    }
}