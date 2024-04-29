package com.example.audioguide.utils

import androidx.recyclerview.widget.RecyclerView

/**
 * Интерфейс контроллера маршрутов
 */
interface RouteListController {
    /**
     * Установить RecyclerView
     */
    fun setupRecyclerView(recyclerView: RecyclerView)
}