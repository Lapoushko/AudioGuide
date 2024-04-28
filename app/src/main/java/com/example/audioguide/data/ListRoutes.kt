package com.example.audioguide.data

import com.example.audioguide.model.Route

/**
 * Список маршрутов для теста без подключения к базе данных
 */
class ListRoutes {
    private val _route = arrayListOf(
            Route(0,"маршрут 1",
        arrayListOf("Пункт 1", "Пункт 2"),
        arrayListOf("https://storage.yandexcloud.net/images-audioguide/test_photo.jpg", "C:\\Users\\vadim\\AndroidStudioProjects\\AudioGuide\\app\\src\\main\\res\\drawable\\test_photo.jpg")),

        Route(1,"маршрут 2",
            arrayListOf("Пункт 1", "Пункт 2"),
            arrayListOf("https://storage.yandexcloud.net/images-audioguide/cat.jpg", "C:\\Users\\vadim\\AndroidStudioProjects\\AudioGuide\\app\\src\\main\\res\\drawable\\test_photo.jpg")),
        Route(2,"маршрут 3",
            arrayListOf("Пункт 1", "Пункт 2"),
            arrayListOf("https://storage.yandexcloud.net/images-audioguide/cat.jpg", "C:\\Users\\vadim\\AndroidStudioProjects\\AudioGuide\\app\\src\\main\\res\\drawable\\test_photo.jpg"))
        )
    val route: ArrayList<Route>
        get() {return this._route }
}