package Automatas

import java.lang.Object;
/**
 * Created by Affisa-Jimmy on 20/7/2016.
 */
class AutomataDFA : Automata() {
    override fun agregarEstado(nombre: String, vertice: Object) {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        estados.add(Estado(nombre,vertice))
    }

    override fun agregarTransicion(nombre: String, origen: Estado, destino: Estado, vertice: Object) {
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
        for (estado in estadosFinales) {
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
    }

    fun CheckTransition(v1 : Estado, name: Char): Boolean {
        return false
    }

}