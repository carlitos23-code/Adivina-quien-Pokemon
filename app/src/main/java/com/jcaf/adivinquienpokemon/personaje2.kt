package com.jcaf.adivinquienpokemon

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class personaje2: LinearLayout {

    lateinit var rasgos: Caracteristicas
    var Nombre: String = ""
    var choose: Boolean = false
    private lateinit var imagen: ImageView
    private lateinit var mensajeNombre: TextView
    private lateinit var Btneliminar: Button
    var imagenTacha=0
    var imagenOrg = 0



    constructor(ctx: Context): super(ctx){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet): super(ctx, attrs){
        inicializar()
    }

    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr: Int): super(ctx, attrs, defStyleAttr){
        inicializar()
    }

    fun inicializar(){
        val imagenN=R.drawable.adivina
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.carta2, this, true)

        imagen= findViewById(R.id.imagenPers2)
        mensajeNombre = findViewById(R.id.NombrePersonaje2)

        Btneliminar = findViewById(R.id.eliminar)
        asignarEventos()

    }

    fun inicializarCarta(imagenN: Int,nombre:String,imagenT: Int, elegido: Boolean){
        imagenTacha=imagenT
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.carta, this, true)
        imagen= findViewById(R.id.imagenPers2)
        mensajeNombre = findViewById(R.id.NombrePersonaje2)
        Btneliminar = findViewById(R.id.eliminar)
        Nombre=nombre
        choose= elegido
        if(choose){
            mensajeNombre.text = "????????"
            imagen.setImageResource(R.drawable.adivina)
            imagenOrg= imagenN
            Btneliminar.visibility= Button.GONE
        }else{
            mensajeNombre.text = nombre
            imagen.setImageResource(imagenN)
        }
        asignarEventos()
    }

    fun inicializarCaracteristicas( aux:Caracteristicas ){
        rasgos = aux
    }

    fun cambiarImagen(){
        imagen.setImageResource(imagenTacha)

    }

    fun asignarEventos(){
        Btneliminar.setOnClickListener {
            cambiarImagen()
        }
    }

    fun destpate(){
        if(choose){
            imagen.setImageResource(imagenOrg)
            mensajeNombre.text= Nombre
        }
    }
}