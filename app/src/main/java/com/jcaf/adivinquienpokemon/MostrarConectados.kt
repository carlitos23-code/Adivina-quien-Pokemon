package com.jcaf.adivinquienpokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MostrarConectados : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var UserList: ArrayList<user>
    private lateinit var adaptador: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_conectados)

        UserList = ArrayList()
        adaptador = UserAdapter(this, UserList)
        mAuth = FirebaseAuth.getInstance()
        db= FirebaseDatabase.getInstance().getReference()
        recycler = findViewById(R.id.userReciycleView)
        recycler.layoutManager= LinearLayoutManager(this)
        recycler.adapter= adaptador

        db.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                UserList.clear()
                for(p in snapshot.children){
                    val currenUser = p.getValue(user::class.java)

                    if(mAuth.currentUser?.uid != currenUser?.uid)
                    UserList.add(currenUser!!)


                }
                adaptador.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}