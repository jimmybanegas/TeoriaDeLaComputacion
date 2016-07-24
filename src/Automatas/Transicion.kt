package Automatas

import java.io.Serializable

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */

class Transicion : Serializable {
    var origen: Estado? = null
//    get() {return origen}
   // set(value) {field=origen}

    var destino: Estado? = null
  //  get() {return destino}
  //  set(value) {field=destino}

    var arista: Object? = null
  //  get() {return vertice}
  //  set(value) {field=vertice}

    var simbolo: Char
//    get() {return nombre}
  //  set(value) {field=nombre}

    constructor(origen: Estado, destino: Estado, simbolo: Char, arista: Object) {
        this.origen = origen
        this.destino = destino
        this.arista = arista
        this.simbolo = simbolo
    }

    override fun toString():String {
       return " Origen: " +this.origen.toString() +"\r\n"+
               " Destino: "+ this.destino.toString()+ "\r\n"+
               " Arista :"+this.arista +"\r\n"+
               " Simbolo: "+ this.simbolo
    }
}