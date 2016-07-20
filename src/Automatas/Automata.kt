package Automatas

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata {

    val alfabeto = mutableListOf(String)

    val transiciones = mutableListOf<Trasicion>()

    val estados = mutableListOf<Estado>()

    val estadosFinales = mutableListOf<Estado>()

    val estadoInicial = Estado()
   /* get() {return _estadoInicial}
    set(value) {field = value}*/

    abstract fun agregarEstado(nombre: String, vertice: java.lang.Object)

    abstract fun agregarTransicion(nombre:String, origen: Estado, destino:Estado, vertice: java.lang.Object)

    abstract fun evaluar(cadena:String): Boolean
}

