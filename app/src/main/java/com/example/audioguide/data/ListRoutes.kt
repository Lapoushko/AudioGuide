package com.example.audioguide.data

import com.example.audioguide.model.Route

/**
 * Список маршрутов для теста без подключения к базе данных
 */
class ListRoutes {
    private val _route = arrayListOf(
        Route(
            0, "На радиоволнах Екатеринбурга",
            "Тур «На радиоволнах Екатеринбург» предлагает увлекательное путешествие в " +
                    "историю и культуру радио в этом прекрасном городе. Во время экскурсии вы " +
                    "познакомитесь с рядом фасцинирующих объектов, связанных с " +
                    "развитием радиовещания.",
            arrayListOf(
                "Институт ИРИТ-РТФ. Шествие к памятнику попова ",
                "Музей «Свердловск: говорит Москва!» внутри",
                "Телеграфный аппарат морзе "
            ),
            arrayListOf(
                "https://storage.yandexcloud.net/images-audioguide/rtf_champion.jpg",
                "https://storage.yandexcloud.net/images-audioguide/say_moscow.jpg",
                "https://storage.yandexcloud.net/images-audioguide/telegraph.jpg"
            )
        ),

        Route(
            1, "маршрут 2",
            "dsa",
            arrayListOf("Пункт 1", "Пункт 2"),
            arrayListOf(
                "https://storage.yandexcloud.net/images-audioguide/cat.jpg",
                "C:\\Users\\vadim\\AndroidStudioProjects\\AudioGuide\\app\\src\\main\\res\\drawable\\test_photo.jpg"
            )
        ),
        Route(
            2, "маршрут 3",
            "dsad",
            arrayListOf("Пункт 1", "Пункт 2"),
            arrayListOf(
                "https://storage.yandexcloud.net/images-audioguide/cat.jpg",
                "C:\\Users\\vadim\\AndroidStudioProjects\\AudioGuide\\app\\src\\main\\res\\drawable\\test_photo.jpg"
            )
        )
    )
    val route: ArrayList<Route>
        get() {
            return this._route
        }
}