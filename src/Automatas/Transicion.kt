package Automatas

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */

class Transicion {
    var origen: Estado? = null
//    get() {return origen}
   // set(value) {field=origen}

    var destino: Estado? = null
  //  get() {return destino}
  //  set(value) {field=destino}

    var arista: Object? = null
  //  get() {return vertice}
  //  set(value) {field=vertice}

    var nombre: String = ""
//    get() {return nombre}
  //  set(value) {field=nombre}

    constructor(origen: Estado, destino: Estado, nombre: String, arista: Object) {
        this.origen = origen
        this.destino = destino
        this.arista = arista
        this.nombre = nombre
    }
}