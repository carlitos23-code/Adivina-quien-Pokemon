package com.jcaf.adivinquienpokemon

class Caracteristicas {
    val vecCaract= arrayListOf<Boolean>()

    constructor( isPokemon2: Boolean, protagonista2: Boolean, isLegendario2: Boolean, Masculino2: Boolean, tipoFuego2: Boolean,
                 PrimeraGeneracion2:Boolean, tipoAgua2: Boolean, tipoelectrico2: Boolean, tipoNormal2: Boolean,
                 ayuda2: Boolean, isEstrenador2 :Boolean, vuela2:Boolean, cabelloNegro:Boolean, teamR:Boolean){

        vecCaract.addAll(listOf(isPokemon2, protagonista2, isLegendario2, Masculino2, tipoFuego2, PrimeraGeneracion2, tipoAgua2,
            tipoelectrico2, tipoNormal2, ayuda2, isEstrenador2, vuela2,cabelloNegro,teamR))

    }
}