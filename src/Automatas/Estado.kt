@file:Suppress("UNREACHABLE_CODE")

package Automatas
import java.lang.Object;

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado {
    var  vertice: Object? = null
//    get() {return vertice}
    set(value) {field=vertice}

    var nombre: String = ""
   // get() {return nombre}
    set(value) {field=nombre}

    constructor(nombre: String, vertice: Object) {
        this.nombre = nombre
        this.vertice = vertice
    }

    constructor()
}