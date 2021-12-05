package com.jcaf.adivinquienpokemon

import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecycle: RecyclerView
    private lateinit var box_messages: EditText
    private lateinit var sentBtn: Button
    private lateinit var adaptador: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var imprimir: TextView
    private lateinit var bd: DatabaseReference

    private var nombrePlayer=""
    private lateinit var generaPer: Button
    private var cartas= arrayListOf<personaje2>()
    private lateinit var personajeEncontrar:personaje

    private lateinit var incorrecto: MediaPlayer
    private lateinit var correcto: MediaPlayer

    private lateinit var gameOver: TextView

    private lateinit var arriesgar: EditText
    private lateinit var btnArriesgar:Button
    private lateinit var textArriesgar: TextView
    private lateinit var pikaGif:pl.droidsonroids.gif.GifImageView
    private lateinit var pikaLose:pl.droidsonroids.gif.GifImageView
    val personajesId= arrayListOf<Int>()
    private var caracteristicas= Caracteristicas(false,false,false,false,false,false,false,false,false,false,false,false,false,false)
    val personajes= arrayListOf<Int>()
    val personajesAleatorios = arrayListOf<Int>()
    val nombresPersonajes= arrayListOf<String>()
    val personajesTacha = arrayListOf<Int>()
    private var puntaje=0
    private var aumentar=10
    private var ganar=0
    private var numGeneraAl=0
    var bandGanaste=false
    var generaOne=true




    var reciverRoom: String? = null
    var senterRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("Name")
        nombrePlayer = name!!
        val receiverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        bd = FirebaseDatabase.getInstance().getReference()

        senterRoom = receiverUid + senderUid
        reciverRoom = senderUid + receiverUid


        textArriesgar=findViewById(R.id.textChido)
        generaPer=findViewById(R.id.generarPersonaje)
        messageRecycle = findViewById(R.id.charRecycleView)
        box_messages = findViewById(R.id.mensaje)
        sentBtn = findViewById(R.id.PreguntaBo)
        imprimir = findViewById(R.id.imprimir)
        incorrecto = MediaPlayer.create(this, R.raw.incorrecto)
        correcto = MediaPlayer.create(this, R.raw.correcto)
        personajeEncontrar=findViewById(R.id.personajeE)

        gameOver=findViewById(R.id.gameover)
        pikaGif=findViewById(R.id.gifWin)
        pikaLose=findViewById(R.id.gifLose)
        arriesgar=findViewById(R.id.arriesgar)
        btnArriesgar=findViewById(R.id.btnArriesgar)


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
        personajesId.addAll(listOf(R.id.car1,R.id.car2,R.id.car3,R.id.car4,R.id.car5,R.id.car6,
            R.id.car7,R.id.car8,R.id.car9,R.id.car10,R.id.car11,R.id.car12,R.id.car13,R.id.car14,
            R.id.car15,R.id.car16,R.id.car17,R.id.car18,R.id.car19,R.id.car20,R.id.car21,R.id.car22,
            R.id.car23,R.id.car24))
        nombresPersonajes.addAll(listOf("Arceus","Articuno","Ash","Brock","Bulbasaur","Charizard","Dawn",
            "Dialga", "Eevee","Joy","Flareon","Gary Oak","Giovanni","Groudon","Horsea","James","Jessie",
            "Jigglypuff","Magikarp","Meowth","Misty","Moltres","Jenny","Pikachu"))

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
            cartas[i].inicializarCaracteristicas(caracteristicas)

        }


        messageList = ArrayList()
        adaptador = MessageAdapter(this,messageList)

        messageRecycle.layoutManager = LinearLayoutManager(this)
        messageRecycle.adapter = adaptador

        //añade info al recycler
        bd.child("chats").child(senterRoom!!).child("mensajes")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (p in snapshot.children){
                        val mensaje = p.getValue(Message::class.java)
                        messageList.add(mensaje!!)
                    }
                    adaptador.notifyDataSetChanged()
                    if(messageList.size > 0){
                        var i = messageList.size-1
                        if (senderUid !=  messageList[i].senderId ){
                            if(!generaOne){
                                if(messageList[i].mensaje=="JSDHJCAF"&& senderUid !=  messageList[i].senderId){
                                    lose()
                                }else if(messageList[i].mensaje=="JCAFJSDH" && senderUid !=  messageList[i].senderId){
                                    win()
                                }else{
                                    imprimir.text = messageList[i].mensaje
                                }

                            }


                            sentBtn.visibility = Button.VISIBLE
                            if(generaOne){
                                var ix=0
                                for(psj in cartas){

                                    if(messageList[i].mensaje==psj.Nombre){
                                        if(messageList[i].senderId==receiverUid) {
                                            personajeEncontrar.inicializar()
                                            personajeEncontrar.inicializarCarta(personajes[ix],psj.Nombre, personajesTacha[ix],true)
                                            personajeEncontrar.inicializarCaracteristicas(caracteristicas)
                                            personajeEncontrar.destpate()
                                            generaOne = false
                                            imprimir.text = ""

                                            Toast.makeText(baseContext, "es: $senderUid",
                                                Toast.LENGTH_SHORT).show()
                                        }
                                        println(senderUid)


                                    }
                                    ix++
                                }
                            }

                        }
                        box_messages.setText("")


                    }

                }



                override fun onCancelled(error: DatabaseError) {

                }

            })

        generaPer.setOnClickListener {
             numGeneraAl=(0..23).random()
            val message = nombresPersonajes[numGeneraAl]

            val messageObject = Message(message, senderUid!!)
            bd.child("chats").child(senterRoom!!).child("mensajes").push().setValue(messageObject)
                .addOnSuccessListener {
                    bd.child("chats").child(reciverRoom!!).child("mensajes").push().setValue(messageObject)
                }
            imprimir.text =""
            generaPer.visibility = Button.GONE
        }


        sentBtn.setOnClickListener{
            val message = box_messages.text.toString()
            val messageObject = Message(message, senderUid!!)
            bd.child("chats").child(senterRoom!!).child("mensajes").push().setValue(messageObject)
                .addOnSuccessListener {
                    bd.child("chats").child(reciverRoom!!).child("mensajes").push().setValue(messageObject)
                }
            imprimir.text =""
            sentBtn.visibility = Button.GONE

        }

        btnArriesgar.setOnClickListener {

            if(arriesgar.text.toString()==nombresPersonajes[numGeneraAl]){

                val message = "JSDHJCAF"
                val messageObject = Message(message, senderUid!!)
                bd.child("chats").child(senterRoom!!).child("mensajes").push().setValue(messageObject)
                    .addOnSuccessListener {
                        bd.child("chats").child(reciverRoom!!).child("mensajes").push().setValue(messageObject)
                    }
                win()
            }else{
                lose()
                val message = "JCAFJSDH"
                val messageObject = Message(message, senderUid!!)
                bd.child("chats").child(senterRoom!!).child("mensajes").push().setValue(messageObject)
                    .addOnSuccessListener {
                        bd.child("chats").child(reciverRoom!!).child("mensajes").push().setValue(messageObject)
                    }
            }
        }



    }

    fun win(){
        gameOver.text= "Ganaste c:!!"
        pikaGif.visibility= View.VISIBLE
        sentBtn.visibility= View.GONE
        arriesgar.visibility= View.GONE
        btnArriesgar.visibility= View.GONE
        textArriesgar.visibility= View.GONE
        correcto.start()
    }

    fun lose(){
        gameOver.text = "Perdiste :c !!"
        pikaLose.visibility= View.VISIBLE
        sentBtn.visibility= View.GONE
        arriesgar.visibility= View.GONE
        btnArriesgar.visibility= View.GONE
        textArriesgar.visibility= View.GONE
        incorrecto.start()
    }

    fun checkaRepetido(num: Int): Boolean{
        for (i in personajesAleatorios){
            if (num==i){
                return false
            }
        }
        return true
    }
}