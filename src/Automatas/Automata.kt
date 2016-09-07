package Automatas


import com.mxgraph.model.mxCell
import com.mxgraph.util.mxConstants
import com.mxgraph.view.mxGraph
import src.Automatas.AutomataPDA
import src.Regex.FSAToRegularExpressionConverter
import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata : Serializable {

    var alfabeto = mutableListOf<Char>()

    var transiciones = mutableListOf<Transicion>()
    val transicionesItems: List<Transicion> get() = transiciones.toList()

    var estados = mutableListOf<Estado>()
    val estadosItems: List<Estado> get() = estados.toList()

    var estadosDeAceptacion = mutableListOf<Estado>()
    val estadosDeAceptacionItems: List<Estado> get() = estadosDeAceptacion.toList()

    var estadoInicial = Estado()

    abstract fun evaluar(cadena:String): Boolean

    abstract fun evaluar(cadena: String, estadoActual: Estado): Boolean

    abstract fun transicionYaExiste(v1 : Estado, v2: Estado, simbolo: Char) : Boolean

    abstract fun transicionYaExiste(v1 : Estado, v2: Estado) : Boolean

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
        graph.model.beginUpdate()

        try {

            for (s in this.estados) {
                if(s.posX == 0.0&& s.posY ==0.0){
                    val rand = Random()
                    s.posX = (rand.nextInt(600) + 1).toDouble()
                    s.posY = (rand.nextInt(300) + 1).toDouble()
                }
                if (estadoInicial.nombre.equals(s.nombre) && this.estadosDeAceptacion.any { it.nombre == s.nombre }){
                    val v = graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightyellow")
                    if(s.vertice == null){
                        s.vertice = v as Object
                    }
                }else if (estadoInicial.nombre.equals(s.nombre) && !this.estadosDeAceptacion.any { it.nombre == s.nombre }) {
                    val v = graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightyellow")
                    if(s.vertice == null){
                        s.vertice = v as Object
                    }
                }else if(this.estadosDeAceptacion.any { it.nombre == s.nombre } && !estadoInicial.nombre.equals(s.nombre)) {
                    val v = graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightgreen")
                    if(s.vertice == null){
                        s.vertice = v as Object
                    }
                }else if(!this.estadosDeAceptacion.any { it.nombre == s.nombre }  && !estadoInicial.nombre.equals(s.nombre)){
                    val v = graph.insertVertex(parent, null, s.nombre, s.posX, s.posY, 50.0, 50.0,"resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightgreen")
                    if(s.vertice == null){
                        s.vertice = v as Object
                    }
                }
            }

            for (t in this.transicionesItems) {
                val style = graph.stylesheet.defaultEdgeStyle
                style.put(mxConstants.STYLE_ROUNDED, true)
                style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SEGMENT)
                graph.stylesheet.defaultEdgeStyle = style

                val vertex = getVertexInGraph(t.origen?.nombre,graph )

                val vertex2 = getVertexInGraph(t.destino?.nombre,graph )

                var edge  = Any()
                if(!(this is AutomataPDA))
                     edge = graph.insertEdge(parent, null, t.simbolo, vertex, vertex2)
                else
                     edge = graph.insertEdge(parent, null, t.simboloS, vertex, vertex2)

                if(t.arista == null){
                    t.arista = edge as Object
                }
            }
        }finally {
            graph.model.endUpdate()
        }
    }

    private fun getVertexInGraph(nombre: String?, graph: mxGraph): Object? {
        var vertex = Object()
        val parent = graph.defaultParent
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

    open fun Limpiar(){
        this.estados.clear()
        this.estadosDeAceptacion.clear()
        this.transiciones.clear()
        this.alfabeto.clear()
        this.estadoInicial = Estado()
    }

    fun obtenerTransicionesSiguientes(estadoActual: Estado): MutableList<Transicion> {

       val transicionesSiguientes = mutableListOf<Transicion>();

        if(true) {
            for (t in transiciones) {
                if (t.origen?.nombre.equals(estadoActual.nombre)) {
                    transicionesSiguientes.add(t);
                }
            }
        }
        return  transicionesSiguientes;
    }

    abstract fun convertirADFA() : AutomataDFA

    abstract fun convertirAER(): String

    fun getStateWithID(id: Int): Estado {

      /*  val it = estados.iterator()
        while (it.hasNext()) {
            if (it.next().getID() == id)
                return it.next()
        }
        */
        for(estado in estados){
            if(estado.getID() == id){
               // println(estado.nombre)
                return  estado
            }
        }
        return null!!
    }

    fun crearTodasLasTransicionesVacias(){

        for(e in estados){
            var transicionesDeEstado = getTransitionsFromState(e)

            (transicionesDeEstado).forEach { t ->
                for (j in estados) {
                    if(!transicionYaExiste(t.origen as Estado,j)){
                        FSAToRegularExpressionConverter.addTransitionOnEmptySet(t.origen as Estado,j,this)
                        println("transicion creada de "+ (t.origen as Estado).nombre +" a "+j.nombre + "$" )
                    }
                }
            }

        }


    }

    fun getTransitionsFromStateToState(from: Estado, to: Estado): MutableList<Transicion> {
       /* val t = getTransitionsFromState(fromState)
        val list = ArrayList()
        for (i in t.indices)
            if (t[i].getToState() === to)
                list.add(t[i])
        return list.toArray(arrayOfNulls<Transicion>(0))*/

        val list = mutableListOf<Transicion>()
        for (item in transiciones) {
            if (item.origen?.nombre.equals(from.nombre) && item.destino?.nombre.equals(to.nombre)) {
                list.add(item)
            }
        }

        if(list.size == 0){
            FSAToRegularExpressionConverter.addTransitionOnEmptySet(from,to,this)
            for (item in transiciones) {
                if (item.origen?.nombre.equals(from.nombre) && item.destino?.nombre.equals(to.nombre)) {
                    list.add(item)
                }
            }
        }

        var simboloConcatenado = ""
        if(list.size > 1){
            for ( i in 0..list.size-1 ){
                 simboloConcatenado += list[i].simbolo
                 if(i < list.size-1){
                     simboloConcatenado+="+"
                 }
            }

            println("concatenado "+simboloConcatenado)

          /*  for (t in list){
                borrarTransicion(t)
            }*/

            list.clear()

            list.add(Transicion(from,to,simboloConcatenado))
        }

       /* for (item in transiciones) {
            if (item.origen?.nombre.equals(from.nombre) && item.destino?.nombre.equals(to.nombre) ) {
                transiciones.remove(item)
            }
        }*/

       // transiciones.add(Transicion(from,to,simboloConcatenado))

        /*for (item in transiciones) {
            if (item.origen?.nombre.equals(from.nombre) && item.destino?.nombre.equals(to.nombre)) {
                list.add(item)
            }
        }*/



        //toReturn
        System.out.println("from " + from.nombre + " to " + to.nombre + " transiciones : " + list.size)

        return list
    }

    private fun getTransitionsFromState(from: Estado): MutableList<Transicion> {
        /* if (transitionArrayFromStateMap.get(from) == null) {
            transitionArrayFromStateMap.get(from) = transitionFromStateMap.get(from).toArray(arrayOfNulls<Transition>(0)) as Array<Transition>
            transitionArrayFromStateMap.put(from, transitionArrayFromStateMap.get(from))
        }
        return transitionArrayFromStateMap.get(from)*/
        val list = mutableListOf<Transicion>()

        for (item in transiciones) {
            if (item.origen?.nombre.equals(from.nombre)) {
                list.add(item)
            }
        }
        return list
    }

    fun obtenerEstados(): Array<Estado> {
        //if (cachedStates == null) {
            val cachedStates = estados.toTypedArray()

        return cachedStates
    }

    abstract fun minimizar(): AutomataDFA

    abstract fun obtenerComplemento(): Automata

    abstract fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: String): Boolean
}

