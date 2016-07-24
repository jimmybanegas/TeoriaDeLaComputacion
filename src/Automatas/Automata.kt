package Automatas


import com.mxgraph.model.mxCell

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
abstract class Automata {

    var alfabeto = mutableListOf<Char>()
    val alfabetoItems: List<Char> get() = alfabeto

    val transiciones = mutableListOf<Transicion>()
    val transaccionesItems: List<Transicion> get() = transiciones.toList()

    val estados = mutableListOf<Estado>()
    val estadosItems: List<Estado> get() = estados.toList()

    var estadosDeAceptacion = mutableListOf<Estado>()
    val aceptacionItems: List<Estado> get() = estadosDeAceptacion.toList()
    // set(value) {field = estadosDeAceptacion}

    var estadoInicial = Estado()
   // get() {return estadoInicial}
   // set(value) {field = estadoInicial}

    abstract fun evaluar(cadena:String): Boolean

    abstract fun validarTransicion(v1 : Estado, nombre: Char) : Boolean

    open fun agregarTransicion(nombre:String, origen: Estado, destino:Estado, arista: mxCell){
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
      //  if(this.alfabeto.isEmpty()){
            for (i in 0..alfabeto.size -1) {
                this.alfabeto.add(alfabeto[i].toChar())
            }
        //}
        println("\r\nTamano alfabeto creado : "+this.alfabeto.size)
        return true
    }

    override fun toString(): String{
        return (("Tamaño de estados: "+ estados.size) + "\n"+
                ("Tamaño de estados aceptacion: "+ estadosDeAceptacion.size) +  "\n"+
                ("Tamaño transiciones : "+ transiciones.size)+ "\n"+
                ("Inicial : "+ estadoInicial.nombre)+ "\n")
    }
}

