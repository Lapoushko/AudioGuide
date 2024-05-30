package com.example.audioguide.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.audioguide.model.Route
import com.example.audioguide.utils.ParserInformation
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


/**
 * DAO для выполнения CRUD операций с БД маршрутов
 */
class RouteDAO(private val context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    private lateinit var listRoutes: List<Route>
    private lateinit var parserInformation: ParserInformation
    init {
        setPath()
        copyDataBase()
    }
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
                parserInformation.parser(cursor.getString(cursor.getColumnIndexOrThrow(PATHS_AUDIO))),
                parserInformation.parser(cursor.getString(cursor.getColumnIndexOrThrow(POINTS)))
            )
            list.add(route)
        }
        listRoutes = list
        cursor.close()
        db.close()
        return listRoutes
    }

    private fun setPath(){
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DATABASE_PATH = context.applicationInfo.dataDir + "/databases/"
        else
            DATABASE_PATH = "/data/data/" + context.packageName + "/databases/"
    }

    /**
     * Создание базы данных
     *
     * @param db база данных
     */
    override fun onCreate(db: SQLiteDatabase?) {
//        copyDataBase()
//        setPath()
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT," +
                INFO + " TEXT," +
                PATHS_IMAGE + " TEXT," +
                PATHS_AUDIO + " TEXT," +
                POINTS + " TEXT" + ")")
        db?.execSQL(query)
    }

    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput: InputStream = context.assets.open(DATABASE_NAME)
        val mOutput: OutputStream = FileOutputStream(DATABASE_PATH + DATABASE_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DATABASE_PATH + DATABASE_NAME)
        return dbFile.exists()
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
         * Database path
         */
        private var DATABASE_PATH = ""
        /**
         * Database Version
         */
        private const val DATABASE_VERSION = 13

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

        /**
         * Points
         */
        const val POINTS = "points"
    }

}