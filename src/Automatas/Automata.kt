package Automatas

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata {

    val alfabeto = listOf<String>()

    val transiciones = listOf<Trasicion>()

    val estados = listOf<Estado>()

    val estadosFinales = listOf<Estado>()

    val estadoInicial = Estado()
   /* get() {return _estadoInicial}
    set(value) {field = value}*/

    abstract fun agregarEstado(nombre: String, vertice: java.lang.Object)

    abstract fun agregarTransicion(nombre:String, origen: Estado, destino:Estado, vertice: java.lang.Object)

    abstract fun evaluar(cadena:String)
}

