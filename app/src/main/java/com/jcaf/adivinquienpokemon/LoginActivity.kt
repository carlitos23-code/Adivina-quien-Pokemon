package com.jcaf.adivinquienpokemon

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var usuario: EditText
    private lateinit var contrase: EditText
    private lateinit var botonLog: Button
    private lateinit var botonReg: Button
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_login2)

        usuario = findViewById(R.id.usuario)
        contrase = findViewById(R.id.contraseña)

        botonLog= findViewById(R.id.LgButton)
        botonReg = findViewById(R.id.RegButton)

        mAuth = FirebaseAuth.getInstance()

        botonReg.setOnClickListener {
            val intet = Intent(this,registro::class.java)
            startActivity(intet)
        }

        botonLog.setOnClickListener {
            login(usuario.text.toString(), contrase.text.toString())

        }


    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "cuenta existente",
                        Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MostrarConectados::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "La cuenta no existe registrate",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }
}