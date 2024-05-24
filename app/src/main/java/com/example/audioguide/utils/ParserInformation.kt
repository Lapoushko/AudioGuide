package com.example.audioguide.utils

/**
 * Парсер информации о маршруте
 */
class ParserInformation() {
    /**
     * Парсер информации
     */
    fun parser(info: String): List<String>{
        if (info.isEmpty() || info.isBlank()){
            return emptyList()
        }
        return info.split(';')
    }
}