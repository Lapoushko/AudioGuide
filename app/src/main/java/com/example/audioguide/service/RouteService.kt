package com.example.audioguide.service

import com.example.audioguide.model.Route

/**
 * Сервис получения маршрута
 */
interface RouteService {
    /**
     * получить маршрут
     * @return список всех маршрутов
     */
    fun getRoute(): List<Route> = ArrayList()
}