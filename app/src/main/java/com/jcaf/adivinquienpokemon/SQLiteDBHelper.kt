package com.jcaf.adivinquienpokemon

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jcaf.adivinquienpokemon.SQLiteEjemploContract.SQL_CREATE_TABLA1
import com.jcaf.adivinquienpokemon.SQLiteEjemploContract.SQL_CREATE_TABLA2
import com.jcaf.adivinquienpokemon.SQLiteEjemploContract.SQL_DELETE_TABLA1
import  com.jcaf.adivinquienpokemon.SQLiteEjemploContract.SQL_DELETE_TABLA2

class SQLiteDBHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLA1)
        db.execSQL(SQL_CREATE_TABLA2)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLA1)
        db.execSQL(SQL_DELETE_TABLA2)
        onCreate(db)
    }

    companion object {
        // Si cambiamos el esquema de la base de datos hay que incrementar el número de versión de
        // este objeto
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SQLiteEjemplo.db"
    }
}