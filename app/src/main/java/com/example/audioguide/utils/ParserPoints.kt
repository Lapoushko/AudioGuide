package com.example.audioguide.utils

/**
 * Парсер точек маршрута
 *
 * @constructor Create empty Parser points
 */
class ParserPoints {
    /**
     * Парсинг строки координат
     *
     * @param pointsText текущая строка
     * @return список координат
     */
    fun parsePoints(pointsText: String) : List<Double>{
        if (pointsText.isBlank() && pointsText.isEmpty()){
            return listOf()
        }
        val points: MutableList<Double> = mutableListOf()
        pointsText.split(',').forEach {
            points.add(it.toDouble())
        }
        return points
    }
}