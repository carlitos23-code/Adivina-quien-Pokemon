package com.jcaf.adivinquienpokemon

class Message {
    var mensaje: String? = null
    var senderId: String? = null

    constructor(){}

    constructor(mensaje: String, enviador: String){
        this.mensaje = mensaje
        this.senderId = enviador
    }

}