package com.example.audioguide.model

/**
 * Маршрут экскурсии
 */
class Route(private val name: String,
            private val info: List<String>,
            private val paths: List<String>) {
    /**
     * Информация экскурсии
     */
    var listInformation = ArrayList<String>()
        get() {
            return info as ArrayList<String>
        }
        private set(value) {
            field = value
        }

    /**
     * Список всех путей к изображениям
     */
    var listPathsImage = ArrayList<String>()
        get() {
            return paths as ArrayList<String>
        }
        private set(value) {
            field = value
        }

    /**
     * Имя маршрута
     */
    var nameRoute: String = ""
        get(){
            return name
        }
        private set(value) {
            field = value
        }
}