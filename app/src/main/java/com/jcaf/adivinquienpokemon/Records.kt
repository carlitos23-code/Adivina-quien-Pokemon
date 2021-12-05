package com.jcaf.adivinquienpokemon

import android.content.pm.ActivityInfo
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Records : AppCompatActivity() {
    private lateinit var recView : RecyclerView
    private lateinit var records : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_records)

        recView = findViewById(R.id.recView)
        records = findViewById(R.id.records)
        //records.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")
        // Para acceder a la base de datos en necesario que creemos una instancia de la clase Helper
        val dbHelper = SQLiteDBHelper(this)

        var datos = mutableListOf<Ejemplo>()

        // Le asignamos los datos al adaptador
        val adaptador = Adaptador(datos)

        actualizarLista(adaptador, datos, dbHelper)

        recView.setHasFixedSize(true)
        recView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false)
        recView.adapter = adaptador


    }

    fun actualizarLista(adaptador: Adaptador, datos :
    MutableList<Ejemplo>, dbHelper : SQLiteDBHelper){
        // Para leer info de la base de datos ---------------------------
        //---------------------------------
        // Para acceder a la base de datos en necesario que creemos una
        //instancia de la clase Helper

        val dbReader = dbHelper.readableDatabase

        // Proyección que especifica qué columnas de la base de datos
        //vamos a usar
        val projection = arrayOf(
            BaseColumns._ID,
            SQLiteEjemploContract.Tabla1.COLUMN_1,
            SQLiteEjemploContract.Tabla1.COLUMN_2)

        // Hacemos el query, retorna un cursor con los resultados
        val cursor = dbReader.query(
            SQLiteEjemploContract.Tabla1.TABLE_NAME,        // Nombre de  la tabla
            projection,                             // Listado de columnas a seleccionar
            null,                                   // Columnas para la cláusula WHERE
            null,                               // Valores para la cláusula where
            null,                                   // Columnas a agrupar
            null,                                    // Filtro por grupos
            null                                    // Orden
        )

        // Llenamos el array de datos del recyclerview
        datos.clear()
        with(cursor){
            while (moveToNext()){
                val id =
                    getString(getColumnIndexOrThrow(BaseColumns._ID))
                val col1 =
                    getString(getColumnIndexOrThrow(SQLiteEjemploContract.Tabla1.COLUMN_1))
                val col2 =
                    getString(getColumnIndexOrThrow(SQLiteEjemploContract.Tabla1.COLUMN_2))

                datos.add(Ejemplo(id, col1, col2))
            }
        }
        adaptador.notifyDataSetChanged()
    }
}