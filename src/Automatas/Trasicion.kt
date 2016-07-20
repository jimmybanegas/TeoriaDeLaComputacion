package Automatas

import org.omg.CORBA.Object

class Trasicion {
    private var origen: Estado? = null
    private var destino: Estado? = null
    private var vertice: Object? = null
    private var nombre: String = ""

    //Constructor Default
    constructor()

    constructor(origen: Estado, destino: Estado, nombre: String, vertice: Object) {
        this.origen = origen
        this.destino = destino
        this.vertice = vertice
        this.nombre = nombre
    }
}