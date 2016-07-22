package Automatas

import com.mxgraph.model.mxCell

/**Created by Jimmy Banegas on 19-Jul-16.
 */
class AutomataDFA : Automata() {

    override fun crearAlfabeto(alfabeto: CharArray): Boolean {
        for (i in 0..alfabeto.size - 1) {
            for (j in i + 1..alfabeto.size - 1) {
                if (alfabeto[j] === alfabeto[i]) {
                    return false
                }
            }
        }
        for (i in 0..alfabeto.size - 1) {
            this.alfabeto .add(alfabeto[i].toChar())
        }
        return true
    }

    override fun estadosEstanVacios(): Boolean {
        if (estados?.isEmpty())
            return true
        return false
    }

    override fun estadoInicialEstaVacio(): Boolean {
        if (estadoInicial?.nombre.isEmpty())
            return true
        return false
    }

    override fun estadosDeAceptacionEstanVacios(): Boolean {
        if (estadosDeAceptacion?.isEmpty())
            return true
        return false
    }

    override fun obtenerEstadoPorVertice(vertice: mxCell): Estado? {
        for (estado in this?.estados!!) {
            if (estado.vertice?.equals(vertice)!!) {
                return estado
            }
        }
        return null
    }

    override fun agregarTransicion(nombre: String, origen: Estado, destino: Estado, arista: mxCell) {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        transiciones.add(Transicion(origen,destino,nombre,arista as Object))
    }

    override fun toString(): String {
        return (("Tamaño de estados: "+ estados.size) + "\n"+
                ("Tamaño de estados aceptacion: "+ estadosDeAceptacion.size) +  "\n"+
                ("Tamaño transiciones : "+ transiciones.size)+ "\n"+
                ("Inicial : "+ estadoInicial.nombre)+ "\n")
    }

    override fun agregarEstado(nombre: String, vertice: Object) {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        estados.add(Estado(nombre,vertice))

        println("Desde kotlin object "+vertice.toString())
        println("Desde kotlin object "+nombre)
    }

    override fun evaluar(cadena: String) : Boolean {
     //   throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (cadena.isEmpty()) {
            return estadoInicialEsDeAceptacion()
        }

        val evaluar = cadena.toCharArray()
        if (!verificarCadena(evaluar))
            return false

        var fin = estadoInicial
        var stay = true

        for (i in evaluar.indices) {
            for (transicion in transaccionesItems) {
                if (transicion.origen?.nombre.equals(fin.nombre) && transicion.nombre.equals(evaluar[i].toString())) {
                    fin = transicion.destino!!
                    stay = true
                    break
                } else {
                    stay = false
                }
            }
        }
        if (!stay) {
            return false
        }
        for (estado in estadosDeAceptacion) {
            if (estado.nombre.equals(fin.nombre))
                return true
        }
        return false
    }

    private fun estadoInicialEsDeAceptacion(): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun verificarCadena(evaluar: CharArray): Boolean {
       //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        for (i in 0..evaluar.size - 1) {
            for (c in alfabeto) {
                if (c.equals( evaluar[i])) {
                    return true
                } else {
                   return false
                }
            }
        }
        return true
    }

    fun ExisteEstadoInicial() : Estado? {
        if (estadoInicial.nombre!=""){
            //println(estadoInicial.nombre)
            return estadoInicial
        }
        else
            return null
    }

    fun verificarTransicion(v1 : Estado, nombre: Char): Boolean {
       // return transiciones.({ transicion -> transicion.origen?.nombre.equals(v1.nombre) && transicion.nombre.equals((nombre)) })
        /*println("v1 nombre "+v1.nombre)
        println("char nombre "+nombre)
        println("transiones size "+transaccionesItems.size)*/

        for (transicion in transaccionesItems) {
            /*println("entre")
            println("transiones origen nombre "+transicion.origen?.nombre)
            println("transiones nombre "+transicion.nombre)*/
            if (transicion.origen?.nombre.equals(v1.nombre.toString()) && transicion.nombre.equals(nombre.toString())) {
                return true
            } else {
                return false
            }
        }
        return false
    }

    override fun agregarEstadoAceptacion(estado: Estado){
        estadosDeAceptacion.add(estado)
    }
}