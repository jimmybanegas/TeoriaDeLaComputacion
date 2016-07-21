package Automatas


import com.mxgraph.model.mxCell

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata {

    val alfabeto = mutableListOf(String)
    //val alfabetoItems: List<String> get() = alfabeto.toList()

    val transiciones = mutableListOf<Transicion>()
    val transaccionesItems: List<Transicion> get() = transiciones.toList()

    val estados = mutableListOf<Estado>()
    val estadosItems: List<Estado> get() = estados.toList()

    var estadosDeAceptacion = mutableListOf<Estado>()
    val aceptacionItems: List<Estado> get() = estadosDeAceptacion.toList()
       // set(value) {field = estadosDeAceptacion}

    var estadoInicial = Estado()
//    get() {return estadoInicial}
   // set(value) {field = estadoInicial}

    abstract fun agregarEstado(nombre: String, vertice: mxCell)

    abstract fun agregarTransicion(nombre:String, origen: Estado, destino:Estado, vertice: mxCell)

    abstract fun evaluar(cadena:String): Boolean

    abstract fun agregarEstadoAceptacion(estado: Estado)
}

