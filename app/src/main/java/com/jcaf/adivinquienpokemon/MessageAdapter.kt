package com.jcaf.adivinquienpokemon

import android.content.Context
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val MessageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      if (viewType == 1){
          val view= LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
          return ReceiveViewHolder(view)
      }else{
          val view= LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
          return SentViewHolder(view)
      }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == SentViewHolder::class.java){
            //Hace lo de enviar holder
            val currentMessage =    MessageList[position]
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.mensaje
        }else{
            //holder recive
            val viewHolder = holder as ReceiveViewHolder
            val currentMessage =    MessageList[position]
            holder.reciveMessage.text = currentMessage.mensaje
        }

    }

    override fun getItemViewType(position: Int): Int {
        val currentMassage = MessageList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMassage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return MessageList.size
    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent_message)

    }
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val reciveMessage = itemView.findViewById<TextView>(R.id.text_recive_message)
    }
}