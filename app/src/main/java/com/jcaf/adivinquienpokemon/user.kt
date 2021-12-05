package com.jcaf.adivinquienpokemon

class user {
    var name: String? = null
    var password: String? = null
    var uid: String? = null

    constructor(){ }

    constructor(nombre: String, contra: String, num: String){
        this.name=nombre
        this.password = contra
        this.uid= num
    }

}