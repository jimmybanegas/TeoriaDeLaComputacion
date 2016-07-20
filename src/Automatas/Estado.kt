@file:Suppress("UNREACHABLE_CODE")

package Automatas

import org.omg.CORBA.Object


/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado {
    private var  vertice: Object? = null
    private var nombre: String = ""

    constructor(nombre: String, vertice: Object) {
        this.nombre = nombre
        this.vertice = vertice
    }

    constructor()
}