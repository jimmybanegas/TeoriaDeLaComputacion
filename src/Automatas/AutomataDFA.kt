package Automatas

import com.mxgraph.model.mxCell

/**Created by Jimmy Banegas on 19-Jul-16.
 */
class AutomataDFA : Automata() {

    override fun ConvertiraDFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {

        var actual = estadoActual

        for (i in 0..cadena.length - 1) {

            val transicionActual = obtenerTransicionConSimbolo(actual.nombre, cadena[i]) ?: return false

            actual = transicionActual.destino!!
        }

        for (State in estadosDeAceptacion) {
            if (State.nombre.equals(estadoActual.nombre))
                return true
        }

        return false
    }


    override fun evaluar(cadena: String) : Boolean {

        var estadoActual = this.estadoInicial
        for (i in 0..cadena.length - 1) {

            val transicionActual = obtenerTransicionConSimbolo(estadoActual.nombre, cadena[i]) ?: return false

            estadoActual = transicionActual.destino!!
        }

        for (State in estadosDeAceptacion) {
            if (State.nombre.equals(estadoActual.nombre))
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
        for (transicion in transaccionesItems) {
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simbolo.equals(simbolo) )
                return true
        }
        return false
    }

}