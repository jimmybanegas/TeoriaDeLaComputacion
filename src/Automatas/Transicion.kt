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

    constructor(origen: Estado, destino: Estado, simbolo: Char, arista: Object) {
        this.origen = origen
        this.destino = destino
        this.arista = arista
        this.simbolo = simbolo
    }

    override fun toString():String {
       return " \nOrigen: " +this.origen.toString() +"\r\n"+
               " Destino: "+ this.destino.toString()+ "\r\n"+
               " Arista :"+this.arista +"\r\n"+
               " Simbolo: "+ this.simbolo
    }

    constructor()

    constructor(origen: Estado, destino: Estado, simbolo: Char){
        this.arista = null
    }
}