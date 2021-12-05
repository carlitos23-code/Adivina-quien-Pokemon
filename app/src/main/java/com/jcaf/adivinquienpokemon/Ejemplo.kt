package com.jcaf.adivinquienpokemon


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Ejemplo (val id : String, val col1 : String, val col2 :
String)

class Adaptador (private val datos : MutableList<Ejemplo>)
    : RecyclerView.Adapter<Adaptador.AdapViewHolder>() {

    class AdapViewHolder(val item : View) :
        RecyclerView.ViewHolder(item) {
        val lblId : TextView = item.findViewById(R.id.lblId) as
                TextView
        val lblCol1 : TextView = item.findViewById(R.id.lblCol1) as
                TextView
        val lblCol2 : TextView = item.findViewById(R.id.lblCol2) as
                TextView

        fun bindEjemplo( ejemplo : Ejemplo ){
            lblId.text = ejemplo.id
            lblCol1.text = ejemplo.col1
            lblCol2.text = ejemplo.col2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            AdapViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bd, parent, false) as LinearLayout
        return AdapViewHolder(item)
    }

    override fun onBindViewHolder(holder: AdapViewHolder, position: Int)
    {
        val ejemplo = datos[position]
        holder.bindEjemplo(ejemplo)
    }
    override fun getItemCount(): Int = datos.size
}