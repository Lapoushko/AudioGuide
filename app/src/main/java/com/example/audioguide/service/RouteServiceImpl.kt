package com.example.audioguide.service

import com.example.audioguide.dao.RouteDAO
import com.example.audioguide.model.Route

/**
 * Сервис, отвечающий за работу с данными о маршрутах
 */
class RouteServiceImpl(routeDao: RouteDAO) : RouteService{

    private val routeDAO = routeDao

    override fun getRoutes(): List<Route> {
        return routeDAO.getListRoutes()
    }

}