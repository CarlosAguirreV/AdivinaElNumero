package com.codigobase.adivinaelnumero.modelo

import java.io.Serializable

class Registro : Serializable {
    private var cadenaNumero: String = ""
    private var aciertos: Int = 0
    private var posibles: Int = 0

    constructor(cadenaNumero: String, aciertos: Int, posibles: Int) {
        this.cadenaNumero = cadenaNumero
        this.aciertos = aciertos
        this.posibles = posibles
    }

    fun getCadenaNumero(): String {
        return this.cadenaNumero
    }

    fun getAciertos(): Int {
        return this.aciertos
    }

    fun getPosibles(): Int {
        return this.posibles
    }

    override fun toString(): String {
        return "$cadenaNumero  ($aciertos aciertos $posibles posibles)"
    }
}