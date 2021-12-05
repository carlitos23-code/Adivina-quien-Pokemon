package com.jcaf.adivinquienpokemon

import android.provider.BaseColumns

// Este objeto funge como un contrato entre la aplicación y la base de datos.

/*  Nos permite tener la estructura de nuestra base de datos, con objetos
internos que
    representan las tablas de la base de datos y sus columnas
    Con esto tenemos un archivo en donde podemos aplicar los cambios a
nuestra base de datos
    sin hacer cambios en cada uno de los archivos donde se utilizan sus
instrucciones */

object SQLiteEjemploContract {
    // Definición de métodos para crear las tablas

    // Crear tabla 1
    const val SQL_CREATE_TABLA1 =
        "CREATE TABLE ${Tabla1.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${Tabla1.COLUMN_1} TEXT, " +
                "${Tabla1.COLUMN_2} TEXT)"

    // Borrar tabla 1
    const val SQL_DELETE_TABLA1 =
        "DROP TABLE IF EXISTS ${Tabla1.TABLE_NAME}"

    object Tabla1 : BaseColumns {  // Tabla 1 que extiende de la interfaz  de BaseColumns
        const val TABLE_NAME = "Tabla1"
        const val COLUMN_1 = "Columna1"
        const val COLUMN_2 = "Columna2"
    }

    // Crear tabla 2
    const val SQL_CREATE_TABLA2 =
        "CREATE TABLE ${Tabla2.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${Tabla2.COLUMN_1} TEXT, " +
                "${Tabla2.COLUMN_2} TEXT)"

    object Tabla2 : BaseColumns {   // Tabla 2 que extiende de la  interfaz de BaseColumns
        const val TABLE_NAME = "Tabla2"
        const val COLUMN_1 = "Columna1"
        const val COLUMN_2 = "Columna2"
    }

    // Borrar tabla 2
    const val SQL_DELETE_TABLA2 =
        "DROP TABLE IF EXISTS ${Tabla2.TABLE_NAME}"
}