package com.example.audioguide.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.audioguide.model.Route
import com.example.audioguide.utils.ParserInformation

/**
 * DAO для выполнения CRUD операций с БД маршрутов
 */
class RouteDAO(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private lateinit var listRoutes: List<Route>
    private lateinit var parserInformation: ParserInformation

    /**
     * Получение списка всех маршрутов из БД
     * @return возвращает список маршрутов
     */
    fun getListRoutes(): List<Route> {
        val list = mutableListOf<Route>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        parserInformation = ParserInformation()
        while (cursor.moveToNext()) {
            val route = Route(
                cursor.getInt(0),
                cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                parserInformation.parser(cursor.getString(cursor.getColumnIndexOrThrow(INFO))),
                parserInformation.parser(cursor.getString(cursor.getColumnIndexOrThrow(PATHS_IMAGE))),
                parserInformation.parser(cursor.getString(cursor.getColumnIndexOrThrow(PATHS_AUDIO)))
            )
            list.add(route)
        }
        listRoutes = list
        cursor.close()
        db.close()
        return listRoutes
    }

    /**
     * Создание базы данных
     *
     * @param db база данных
     */
    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                INFO + " TEXT," +
                PATHS_IMAGE + " TEXT," +
                PATHS_AUDIO + " TEXT" + ")")
        db?.execSQL(query)
    }

    /**
     * Изменение базы данных
     *
     * @param db база данных
     * @param oldVersion старая версия
     * @param newVersion новая версия
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        /**
         * Database Name
         */
        private const val DATABASE_NAME = "audioguide.db"

        /**
         * Database Version
         */
        private const val DATABASE_VERSION = 4

        /**
         * Table Name
         */
        const val TABLE_NAME = "routes"

        /**
         * Id
         */
        const val ID = "id"

        /**
         * Name
         */
        const val NAME = "name"

        /**
         * Description
         */
        const val DESCRIPTION = "description"

        /**
         * Info
         */
        const val INFO = "info"

        /**
         * Paths Image
         */
        const val PATHS_IMAGE = "paths_image"

        /**
         * Paths Audio
         */
        const val PATHS_AUDIO = "paths_audio"
    }

}