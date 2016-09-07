package Automatas

import java.io.Serializable

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */

class Transicion : Serializable {
    var origen: Estado? = null
    var destino: Estado? = null
    var arista: Object? = null
    var simbolo: Char = '\u0000'
    var simboloS: String = "";

    constructor(origen: Estado, destino: Estado, simbolo: Char, arista: Object) {
        this.origen = origen
        this.destino = destino
        this.arista = arista
        this.simbolo = simbolo
        this.simboloS = simbolo.toString()
    }

    constructor(origen: Estado, destino: Estado, simboloS: String, arista: Object) {
        this.origen = origen
        this.destino = destino
        this.arista = arista
        this.simboloS = simboloS
        this.simbolo = simboloS[0]
    }

    override fun toString():String {
       return " \nOrigen: " +this.origen.toString() +"\r"+
               " Destino: "+ this.destino.toString()+ "\r"+
               " Arista :"+this.arista +"\r"+
               " Simbolo: "+ this.simbolo+"\r"+
               " Simbolos: "+ this.simboloS+"\r"
    }

    constructor()

    constructor(origen: Estado, destino: Estado, simbolo: Char){
        this.origen = origen
        this.destino = destino
        this.simbolo = simbolo
        this.simboloS = simbolo.toString()
        this.arista = null
    }

    constructor(origen: Estado, destino: Estado, simboloS: String){
        this.origen = origen
        this.destino = destino
        this.simbolo = simboloS[0]
        this.simboloS = simboloS.toString()
        this.arista = null
    }
}