package com.example.audioguide.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Загружает изображения
 */
class LoaderImage {
    /**
     * Загрузить изображение
     */
    fun loadImage(path: String, image: ImageView) {
        Picasso.get()
            .load(path)
            .into(image)
    }
}