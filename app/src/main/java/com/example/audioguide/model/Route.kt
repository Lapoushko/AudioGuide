package com.example.audioguide.model

import java.io.Serializable

/**
 * Маршрут экскурсии
 */
class Route(
    private val id: Int,
    private val name: String,
    private val description: String,
    private val info: List<String>,
    private val paths: List<String>,
    private val audio: List<String>
) : Serializable {
    val idRoute: Int
        get() = id
    val descriptionText: String
        get() = description
    val listInformation: List<String>
        get() = info

    val listPathsImage: List<String>
        get() = paths

    val nameRoute: String
        get() = name
    val audioPath: List<String>
        get() = audio
}