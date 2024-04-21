package com.example.audioguide

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.audioguide.service.RouteServiceImpl
import com.example.audioguide.utils.RouteListController
import com.example.audioguide.utils.RouteMenuAdapter

/**
 * Контроллер маршрутов
 */
class RouteListControllerImpl(private val routeService: RouteServiceImpl, private val context: Context) : RouteListController {
    override fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = RouteMenuAdapter(routeService.getRoute(), context)
    }
}