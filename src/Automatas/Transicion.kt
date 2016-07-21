package Automatas

import com.mxgraph.model.mxCell
import java.lang.Object

class Transicion {
    var origen: Estado? = null
//    get() {return origen}
   // set(value) {field=origen}

    var destino: Estado? = null
  //  get() {return destino}
  //  set(value) {field=destino}

    var vertice: mxCell? = null
  //  get() {return vertice}
  //  set(value) {field=vertice}

    var nombre: String = ""
//    get() {return nombre}
  //  set(value) {field=nombre}

    //Constructor Default
    constructor()

    constructor(origen: Estado, destino: Estado, nombre: String, vertice: mxCell) {
        this.origen = origen
        this.destino = destino
        this.vertice = vertice
        this.nombre = nombre
    }
}