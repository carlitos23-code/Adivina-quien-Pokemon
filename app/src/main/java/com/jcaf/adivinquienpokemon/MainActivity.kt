package com.jcaf.adivinquienpokemon

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var playerVsComputer: Button
    private lateinit var playerVsPlayer:Button
    private lateinit var records: Button
    private lateinit var cancion: MediaPlayer
    var nombre=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_main)
        playerVsComputer=findViewById(R.id.pvc)
        playerVsPlayer=findViewById(R.id.pvp)
        records=findViewById(R.id.records)
        cancion = MediaPlayer.create(this,R.raw.intro)
        cancion.start()

        playerVsComputer.setOnClickListener {
            showdialog()
        }
        records.setOnClickListener {
            cancion.stop()
            val intent = Intent(this, Records::class.java)
            startActivity(intent)
        }
        playerVsPlayer.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            finish()
            cancion.stop()
            startActivity(intent)
        }

    }
    fun showdialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Ingresa tu nombre")


        val input = EditText(this)

        input.setHint("Aceptar")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)


        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            cancion.stop()

            nombre = input.text.toString()
            val intent = Intent(this, PlayerVsComputer::class.java)
            intent.putExtra("Jugador", nombre)
            startActivity(intent)

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()

    }


}