package Automatas

import com.mxgraph.model.mxCell

/**
 * Created by Affisa-Jimmy on 20/7/2016.
 */
class AutomataDFA : Automata() {
    override fun agregarEstado(nombre: String, vertice: Object) {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        estados.add(Estado(nombre,vertice))

        println("Desde kotlin object "+vertice.toString())
        println("Desde kotlin object "+nombre)
    }

    override fun agregarTransicion(nombre: String, origen: Estado, destino: Estado, vertice: Object) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun agregarEstado(nombre: String,vertice: mxCell) {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
     //   estados.add(Estado(nombre,vertice))
        println("Desde kotlin mxcell "+vertice.toString())
        println("Desde kotlin mxcell "+nombre)
    }

    override fun agregarTransicion(nombre: String, origen: Estado, destino: Estado, vertice: mxCell) {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        transiciones.add(Transicion(origen,destino,nombre,vertice))
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
            for (transicion in transiciones) {
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

    private fun verificarCadena(evaluar: CharArray): Boolean {
       //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        for (i in 0..evaluar.size - 1) {
            for (c in alfabeto) {
                if (c.equals( evaluar[i])) {
                    return true
                    break
                } else {
                   return false
                }
            }
        }
        return true
    }

    private fun estadoInicialEsDeAceptacion(): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
     //   return estadosFinales().anyMatch({ estado -> estado.nombreEstado.equals(estadoInicial.nombreEstado) })
    }

    fun ExisteEstadoInicial() : Estado? {
        if (estadoInicial.nombre!=""){
            //println(estadoInicial.nombre)
            return estadoInicial
        }
        else
            return null
    }

    fun CheckTransition(v1 : Estado, name: Char): Boolean {
        return false
    }

    override fun agregarEstadoAceptacion(estado: Estado){
        estadosDeAceptacion.add(estado)
    }
}