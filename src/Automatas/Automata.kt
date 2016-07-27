package Automatas


import com.mxgraph.model.mxCell
import com.mxgraph.util.mxConstants
import com.mxgraph.view.mxGraph
import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata : Serializable {

    var alfabeto = mutableListOf<Char>()
    val alfabetoItems: List<Char> get() = alfabeto

    val transiciones = mutableListOf<Transicion>()
    val transaccionesItems: List<Transicion> get() = transiciones.toList()

    val estados = mutableListOf<Estado>()
    val estadosItems: List<Estado> get() = estados.toList()

    var estadosDeAceptacion = mutableListOf<Estado>()
    val estadosDeAceptacionItems: List<Estado> get() = estadosDeAceptacion.toList()

    var estadoInicial = Estado()

    abstract fun evaluar(cadena:String): Boolean

    abstract fun evaluar(cadena: String, estadoActual: Estado): Boolean

    abstract fun transicionYaExiste(v1 : Estado, v2: Estado, simbolo: Char) : Boolean

    open fun agregarTransicion(nombre:Char, origen: Estado, destino:Estado, arista: mxCell){
        transiciones.add(Transicion(origen,destino,nombre,arista as Object))
    }

    open fun agregarEstado(nombre: String, vertice: Object)  {
        estados.add(Estado(nombre,vertice))
    }

    open fun agregarEstadoAceptacion(estado: Estado){
        estadosDeAceptacion.add(estado)
    }

    open fun obtenerEstadoPorVertice(vertice: mxCell):Estado?{
        this.estados.forEach { estado ->
            if (estado.vertice?.equals(vertice)!!) {
                return estado
            }
        }
        return null
    }

    open fun obtenerEstadoPorNombre(nombre: String):Estado?{
        this.estados.forEach { estado ->
            if (estado.nombre.equals(nombre)) {
                return estado
            }
        }
        return null
    }

    fun ponerPosicionEstado(name: String, posX: Double, posY: Double) {
        for (s in estados) {
            if (s.nombre.equals(name)) {
                s.definirPosicionEnGrafico(posX, posY)
            }
        }
        for (s in estadosDeAceptacion) {
            if (s.nombre.equals(name)) {
                s.definirPosicionEnGrafico(posX, posY)
            }
        }
    }

    open fun estadosEstanVacios():Boolean {
        if (estados.isEmpty())
            return true
        return false
    }

    open fun estadoInicialEstaVacio(): Boolean{
        if (estadoInicial.nombre.isEmpty())
            return true
        return false
    }

    open fun estadosDeAceptacionEstanVacios(): Boolean{
        if (estadosDeAceptacion.isEmpty())
            return true
        return false
    }

    open fun transicionesEstanVacias(): Boolean{
        if (transiciones.isEmpty())
            return true
        return false
    }

    open fun crearAlfabeto(alfabeto: CharArray): Boolean{
        //Validar que no vengan letras repetidas en el array de char porque no es necesario
        for (i in 0..alfabeto.size -1) {
            for (j in i + 1..alfabeto.size-1) {
                if (alfabeto[j] === alfabeto[i]) {
                    return false
                }
            }
        }
        this.alfabeto.clear()
        // Si pasa ese filtro, procedemos a llenar el arreglo de chars
            for (i in 0..alfabeto.size -1) {
                this.alfabeto.add(alfabeto[i].toChar())
            }

        return true
    }

    open fun simbolosDeTransicionesEstanEnAlfabeto() : Boolean{
        for(transicion in transiciones){
            if(!alfabeto.contains(transicion.simbolo)){
                return false
            }
        }
        return true
    }

    open fun dibujarAutomata(graph: mxGraph) {
        val parent = graph.defaultParent

        for (s in this.estados) {
            if(s.posX == 0.0&& s.posY ==0.0){
                val rand = Random()
                 s.posX = (rand.nextInt(600) + 1).toDouble()
                 s.posY = (rand.nextInt(300) + 1).toDouble()
            }
            graph.model.beginUpdate()
            if (estadoInicial.nombre.equals(s.nombre) && this.estadosDeAceptacion.any { it.nombre == s.nombre }){
                graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightyellow")
            }else if (estadoInicial.nombre.equals(s.nombre) && !this.estadosDeAceptacion.any { it.nombre == s.nombre }) {
                graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightyellow")
            }else if(this.estadosDeAceptacion.any { it.nombre == s.nombre } && !estadoInicial.nombre.equals(s.nombre)) {
                graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightgreen")
            }else if(!this.estadosDeAceptacion.any { it.nombre == s.nombre }  && !estadoInicial.nombre.equals(s.nombre)){
                graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightgreen")
            }

            graph.model.endUpdate()
        }

        for (t in this.transaccionesItems) {
            val style = graph.stylesheet.defaultEdgeStyle
            style.put(mxConstants.STYLE_ROUNDED, true)
            style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SEGMENT)
            graph.stylesheet.defaultEdgeStyle = style

            val vertex = getVertexInGraph(t.origen?.nombre,graph )

            val vertex2 = getVertexInGraph(t.destino?.nombre,graph )

            graph.model.beginUpdate()

            graph.insertEdge(parent, null, t.simbolo, vertex, vertex2)

            graph.model.endUpdate()
        }
    }

    private fun getVertexInGraph(nombre: String?, graph: mxGraph): Object? {
        var vertex = Object()
        val parent = graph.getDefaultParent()
        for (i in 0..graph.model.getChildCount(parent) - 1) {

            vertex = graph.model.getChildAt(parent, i) as Object
            if ((vertex as mxCell).value.toString() == nombre)
                return vertex
        }
        return vertex
    }

    override fun toString(): String{
        return (("Tamaño de estados: "+ estados.size) + "\n"+
                ("Tamaño de estados aceptacion: "+ estadosDeAceptacion.size) +  "\n"+
                ("Tamaño transiciones : "+ transiciones.size)+ "\n"+
                ("Tamaño alfabeto : "+ alfabeto.size)+ "\n"+
                ("Inicial : "+ estadoInicial.nombre)+ "\n")
    }

    open fun Clear(){
        this.estados.clear()
        this.estadosDeAceptacion.clear()
        this.transiciones.clear()
        this.alfabeto.clear()
        this.estadoInicial = Estado()
    }

    fun getNextTransitions(currentState: Estado): MutableList<Transicion> {

       var nextTransitions = mutableListOf<Transicion>();

        if(currentState!=null) {
            for (t in transiciones) {
                if (t.origen?.nombre.equals(currentState.nombre)) {
                    nextTransitions.add(t);
                }
            }
        }
        return  nextTransitions;
    }

    abstract fun ConvertiraDFA() : AutomataDFA

}

