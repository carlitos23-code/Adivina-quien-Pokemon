package com.jcaf.adivinquienpokemon

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class registro : AppCompatActivity() {
    private lateinit var usuario: EditText
    private lateinit var contrase: EditText
    private lateinit var nombre: EditText
    private lateinit var botonReg: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_registro)

        mAuth = FirebaseAuth.getInstance()

        usuario = findViewById(R.id.usuario)
        contrase = findViewById(R.id.contraseña)
        nombre = findViewById(R.id.name)
        botonReg = findViewById(R.id.RegButton)

        botonReg.setOnClickListener {
            singup(nombre.text.toString(),usuario.text.toString(), contrase.text.toString())
        }
    }

    private fun singup(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserDataBase(name, email, mAuth.currentUser?.uid!!)

                    var intent = Intent(this, LoginActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserDataBase(name: String, email: String, uid: String) {
        db = FirebaseDatabase.getInstance().getReference()
        db.child("user").child(uid).setValue(user(name,email,uid))

    }
}