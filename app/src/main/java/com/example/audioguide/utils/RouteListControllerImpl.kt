package com.example.audioguide

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.audioguide.service.RouteServiceImpl
import com.example.audioguide.utils.RouteListController
import com.example.audioguide.utils.RouteMenuAdapter

/**
 * Контроллер маршрутов
 */
class RouteListControllerImpl(private val routeService: RouteServiceImpl) : RouteListController {
    override fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = RouteMenuAdapter(routeService.getRoute())
    }
}