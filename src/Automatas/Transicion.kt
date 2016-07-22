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

    var vertice: Object? = null
  //  get() {return vertice}
  //  set(value) {field=vertice}

    var nombre: String = ""
//    get() {return nombre}
  //  set(value) {field=nombre}

    constructor(origen: Estado, destino: Estado, nombre: String, vertice: Object) {
        this.origen = origen
        this.destino = destino
        this.vertice = vertice
        this.nombre = nombre
    }
}