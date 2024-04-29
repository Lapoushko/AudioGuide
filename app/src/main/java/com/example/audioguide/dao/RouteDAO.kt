package com.example.audioguide.dao

import com.example.audioguide.data.ListRoutes
import com.example.audioguide.model.Route

/**
 * DAO для выполнения CRUD операций с БД маршрутов
 */
class RouteDAO {

    private val listRoutes = ListRoutes().route

    /**
     * Получение списка всех маршрутов из БД
     * @return возвращает список маршрутов
     */
    fun getListRoutes(): List<Route>{
        //TODO Вернуть список из базы данных
        return listRoutes
    }
}