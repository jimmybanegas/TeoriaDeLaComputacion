package Automatas

import com.mxgraph.model.mxCell

/**Created by Jimmy Banegas on 19-Jul-16.
 */
class AutomataDFA : Automata() {

    override fun convertirADFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun evaluar(cadena: String) : Boolean {
        var estadoActual = this.estadoInicial
        for (i in 0..cadena.length - 1) {

            val transicionActual = obtenerTransicionConSimbolo(estadoActual.nombre, cadena[i]) ?: return false

            estadoActual = transicionActual.destino!!
        }

        for (estado in estadosDeAceptacion) {
            if (estado.nombre.equals(estadoActual.nombre))
                return true
        }

        return false

    }

    private fun obtenerTransicionConSimbolo(nombre: String, simbolo: Char): Transicion? {
        for (trans in transiciones) {
            if (trans.origen?.nombre.equals(nombre) && trans.simbolo.equals(simbolo))
                return trans
        }
        return null
    }

    override fun transicionYaExiste(v1 : Estado, v2: Estado, simbolo: Char): Boolean {
        for (transicion in transicionesItems) {
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simbolo.equals(simbolo) )
                return true
        }
        return false
    }

}