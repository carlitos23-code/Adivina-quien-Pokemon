package com.jcaf.adivinquienpokemon
import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.DatabaseUtils
import android.graphics.Typeface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class PlayerVsComputer : AppCompatActivity() {
    private var cartas= arrayListOf<personaje>()
    private var characterDelete= arrayListOf<Int>()
    private lateinit var personajeEncontrar:personaje
    private lateinit var incorrecto: MediaPlayer
    private lateinit var correcto: MediaPlayer
    private lateinit var regresarB : Button
    private lateinit var siguienteB : Button
    private lateinit var enviarQuestion: Button
    private lateinit var pregunta : TextView
    private lateinit var puntos: TextView
    private lateinit var gameOver: TextView
    private lateinit var score: TextView
    private lateinit var pikaGif:pl.droidsonroids.gif.GifImageView
    val personajesId= arrayListOf<Int>()
    val caracteristicas=arrayListOf<Caracteristicas>()
    val personajes= arrayListOf<Int>()
    val preguntasRealizadas= arrayListOf<Int>()
    val personajesAleatorios = arrayListOf<Int>()
    val nombresPersonajes= arrayListOf<String>()
    val preguntas = arrayListOf<String>()
    val personajesTacha = arrayListOf<Int>()
    private var indexPregunta=0
    private var puntaje=0
    private var aumentar=10
    private var ganar=0
    private var num=(0..23).random()
    private var nombrePlayer=""
    var bandGanaste=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_player_vs_computer)
        incorrecto = MediaPlayer.create(this, R.raw.incorrecto)
        correcto = MediaPlayer.create(this, R.raw.correcto)
        pregunta=findViewById(R.id.pregunta)
        regresarB=findViewById(R.id.regresar)
        siguienteB=findViewById(R.id.siguiente)
        personajeEncontrar=findViewById(R.id.personajeE)
        enviarQuestion = findViewById(R.id.PreguntaBo)
        puntos=findViewById(R.id.puntos)
        score=findViewById(R.id.score)
        gameOver=findViewById(R.id.gameover)
        pikaGif=findViewById(R.id.gifWin)
        nombrePlayer = intent.getSerializableExtra("Jugador") as String

        /*gameOver.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")
        puntos.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")
        pregunta.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")
        score.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")
        enviarQuestion.typeface= Typeface.createFromAsset(assets,"fonts/PokemonSolid.ttf")*/
        preguntas.addAll(listOf("¿Tu personaje es un pokemon?", "¿Tu personaje es un protagonista?",
            "¿Tu personaje es un pokemon legendario", "¿Tu personaje es hombre?",
            "¿Tu personaje es un pokemon tipo fuego?","¿Tu personaje aparce en la primera serie?", "¿Tu personaje es un pokemon tipo agua?", "¿Tu personaje es un pokemon tipo electrico?",
            "¿Tu personaje es un pokemon tipo normal?", "¿Tu personaje ayuda a los pokemones?","¿Es entrenador?","¿Puede volar?"
            ,"¿Tiene cabello negro?","¿Es del equipo rocket?"));
        personajes.addAll(listOf(R.drawable.arceus,R.drawable.articuno,R.drawable.ash,R.drawable.brock,
            R.drawable.bulbasaur,R.drawable.charizad,R.drawable.dawn,R.drawable.dialga,R.drawable.eevee,
            R.drawable.enfermera_joy,R.drawable.flareon,R.drawable.gary_oak,R.drawable.giovanni,
            R.drawable.groudon,R.drawable.horsea,R.drawable.james,R.drawable.jessie,R.drawable.jigglypuff,
            R.drawable.magikarp,R.drawable.meowth,R.drawable.misty2,R.drawable.moltres,R.drawable.oficial_jenny,
            R.drawable.pikachu))
        personajesTacha.addAll(listOf(R.drawable.arceustacha,R.drawable.articunotacha,R.drawable.ashtacha,R.drawable.brocktacha,
            R.drawable.bulbasaurtacha,R.drawable.charizadtacha,R.drawable.dawntacha,R.drawable.dialgatacha,R.drawable.eeveetacha,
            R.drawable.enfermera_joytacha,R.drawable.flareontacha,R.drawable.gary_oaktacha,R.drawable.giovannitacha,
            R.drawable.groudontacha,R.drawable.horseatacha,R.drawable.jamestacha,R.drawable.jessietacha,R.drawable.jigglypufftacha,
            R.drawable.magikarptacha,R.drawable.meowthtacha,R.drawable.misty2tacha,R.drawable.moltrestacha,R.drawable.oficial_jennytacha,
            R.drawable.pikachutacha))
        personajesId.addAll(listOf(R.id.card1,R.id.card2,R.id.card3,R.id.card4,R.id.card5,R.id.card6,
            R.id.card7,R.id.card8,R.id.card9,R.id.card10,R.id.card11,R.id.card12,R.id.card13,R.id.card14,
            R.id.card15,R.id.card16,R.id.card17,R.id.card18,R.id.card19,R.id.card20,R.id.card21,R.id.card22,
            R.id.card23,R.id.card24))
        nombresPersonajes.addAll(listOf("Arceus","Articuno","Ash","Brock","Bulbasaur","Charizard","Dawn",
            "Dialga", "Eevee","Joy","Flareon","Gary Oak","Giovanni","Groudon","Horsea","James","Jessie",
            "Jigglypuff","Magikarp","Meowth","Misty","Moltres","Jenny","Pikachu"))
        caracteristicas.addAll(listOf(
            /*arceus*/Caracteristicas(true, false,false,false,false,false,false,false,true,false,false,false,false,false),
            /*articuno*/Caracteristicas(true, false,true,false,false,true,false,false,false,false,false,true,false,false),
            /*ash*/Caracteristicas(false, true,false,true,false,true,false,false,false,true,true,false,true,false),
            /*brock*/Caracteristicas(false, true,false,true,false,true,false,false,false,true,true,false,false,false),
            /*bulbasaur*/Caracteristicas(true, false,false,false,false,true,false,false,false,false,false,false,false,false),
            /*charizard*/Caracteristicas(true, false,false,false,true,true,false,false,false,false,false,true,false,false),
            /*Dawn*/Caracteristicas(false, false,false,false,false,false,false,false,false,false,true,false,true,false),
            /*Dialga*/Caracteristicas(true, false,true,false,false,false,false,false,false,false,false,true,false,false),
            /*Eevee*/Caracteristicas(true, false,false,false,false,true,false,false,true,false,false,false,false,false),
            /*Joy*/Caracteristicas(false, false,false,false,false,false,false,false,false,true,false,false,false,false),
            /*Flareon*/Caracteristicas(true, false,false,false,true,true,false,false,false,false,false,false,false,false),
            /*Gary Oak*/Caracteristicas(false, false,false,true,false,true,false,false,false,false,true,false,false,false),
            /*Geovany*/Caracteristicas(false,false,false,true, false,  true, false, false, false, false, true, false,true,true),
            /*Groudon*/Caracteristicas(true,false,false,false,false,false,false,false,false,false,false,false,false,false),
            /*Horsea*/Caracteristicas(true,false,false,false,false,true,true,false,false,false, false, true,false,false),
            /*James*/Caracteristicas(false,false,false,true,false,true,false,false,false,false,true,false,false,true),
            /*Jessie*/Caracteristicas(false,false,false,false,false,true,false,false,false,false,true,false,false,true),
            /*Jigglypuff*/Caracteristicas(true,false,false,false, false, true, false,false, true, false, false, true,false,false),
            /*Magikarp*/Caracteristicas(true, false,false,false,false,true,true,false,false,false,false,false,false,false),
            /*Meowth*/Caracteristicas(true, false,false,false,false,true,false,false,true,false,false,false,false,true),
            /*Misty*/Caracteristicas(false, true,false,false,false,true,false,false,false,true,true,false,false,false),
            /*Moltres*/Caracteristicas(true, false,true,false,true,true,false,false,false,false,false,true,false,false),
            /*Jenny*/Caracteristicas(false, false,false,false,false,true,false,false,false,true,false,false,false,false),
            /*pikachu*/Caracteristicas(true, true,false,false,false,true,false,true,false,false,false,false,false,false)))


        var c=0
        var num:Int

        while (c<24){
            num=(0..23).random()

            if(c==0){
                personajesAleatorios.add(num)
                c++
            }else{

                if( checkaRepetido(num)){
                    personajesAleatorios.add(num)
                    c++
                }
            }
        }

        for (i in personajesAleatorios) {
            cartas.add(findViewById(personajesId[i]))

        }
        for (i in personajesAleatorios) {
            cartas[i].inicializarCarta(personajes[i],nombresPersonajes[i],personajesTacha[i], false)
            cartas[i].inicializarCaracteristicas(caracteristicas[i])

        }

        pregunta.text=preguntas[indexPregunta]
        regresarB.setOnClickListener{
            if(indexPregunta==0){
                indexPregunta=preguntas.size-1
            }else{
                indexPregunta=indexPregunta-1
            }
            pregunta.text=preguntas[indexPregunta]
        }
        siguienteB.setOnClickListener {
            if(indexPregunta==preguntas.size-1){
                indexPregunta=0
            }else{
                indexPregunta=indexPregunta+1
            }
            pregunta.text=preguntas[indexPregunta]
        }

        enviarQuestion.setOnClickListener {
            if(bandGanaste){
                val intent = Intent(this, Records::class.java)
                startActivity(intent)

            }else{
                var banderita=true
                for (p in preguntasRealizadas){
                    if(p == indexPregunta){
                        banderita=false
                    }
                }
                if(banderita){
                    preguntasRealizadas.add(indexPregunta)
                    descartarPersonajes(indexPregunta)
                }
            }

        }




        generaPsjAl()

    }

    fun descartarPersonajes(i: Int){
        var j=0
        for (p in cartas){
            if(p.rasgos.vecCaract[i]!= personajeEncontrar.rasgos.vecCaract[i] && checkaDelete(personajesAleatorios[j]) ){
                p.cambiarImagen()
                characterDelete.add(personajesAleatorios[j])
                if(personajeEncontrar.rasgos.vecCaract[i] == true){
                    puntaje+=aumentar
                    puntos.text=puntaje.toString()
                    //correcto.start()
                }
                ganar++
            }
            j++
        }
        if(ganar==23){
            ganaste()
        }
        if(personajeEncontrar.rasgos.vecCaract[i] == true){
            correcto.start()
        }else{
            incorrecto.start()
        }


    }

    fun ganaste(){
        puntaje+=100
        puntos.text=puntaje.toString()
        gameOver.text="Ganaste " +nombrePlayer+" !!"
        personajeEncontrar.destpate()
        pikaGif.visibility= View.VISIBLE
        bandGanaste=true
        enviarQuestion.text="Records"
        guardarBD()

    }

    fun guardarBD(){
        val dbHelper = SQLiteDBHelper(this)
        // Obtenemos el id actual
        val dbR = dbHelper.readableDatabase
        val n = DatabaseUtils.queryNumEntries(dbR,
            SQLiteEjemploContract.Tabla1.TABLE_NAME, null)

        // Para ingresar info en la base de datos -------------------
        //-------------------------------------
        // Obtener el repositorio de la bd en modo de escritura
        val db = dbHelper.writableDatabase

        // Crear un nuevo mapa de valores, en donde los nombres de las columnas son las llaves
        val values = ContentValues().apply {
            put(SQLiteEjemploContract.Tabla1.COLUMN_1, nombrePlayer)
            put(SQLiteEjemploContract.Tabla1.COLUMN_2, puntaje.toString())
        }
        // Insertamos una nueva fila, regresando la llave primaria de la nueva fila
        val newRowId = db?.insert(SQLiteEjemploContract.Tabla1.TABLE_NAME, null, values)

        //actualizarLista(adaptador, datos, dbHelper)
    }

    fun checkaRepetido(num: Int): Boolean{
        for (i in personajesAleatorios){
            if (num==i){
                return false
            }
        }
        return true
    }

    fun checkaDelete(num: Int): Boolean{
        for (i in characterDelete){
            if (num==i){
                return false
            }
        }
        return true
    }

    fun generaPsjAl(){


        personajeEncontrar.inicializar()
        personajeEncontrar.inicializarCarta(personajes[num],nombresPersonajes[num],personajesTacha[num], true)
        personajeEncontrar.inicializarCaracteristicas(cartas[num].rasgos)

    }
}