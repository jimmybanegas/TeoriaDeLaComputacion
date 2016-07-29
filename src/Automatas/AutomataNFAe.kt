package Automatas

import java.util.*

/**
 * Created by Affisa-Jimmy on 29/7/2016.
 */
class AutomataNFAe : Automata() {
    override fun evaluar(cadena: String): Boolean {
        val evaluar = cadena.toCharArray()
        if (!cadena.isEmpty()) {
            if (!(verificarCadena(evaluar) as Boolean))
                return false
        }

        var finales = mutableListOf<Estado>()

        finales.add(estadoInicial)

        finales = terminarDeEvaluarCadena(finales, evaluar, 0)

        if (finales.isEmpty()) {
            return false
        }
        for (estado in estadosDeAceptacion) {
            for (estadoFinal in finales) {
                if (estado.nombre.equals(estadoFinal.nombre))
                    return true
            }
        }
        return false
    }

    private fun terminarDeEvaluarCadena(finales: MutableList<Estado>, evaluar: CharArray, i: Int): MutableList<Estado> {
        val nuevoFinales = mutableListOf<Estado> ()
      //  finales = clausura(finales)

         val finales = clausura(finales)

        if (i === evaluar.size)
            return finales
        for (transicion in transiciones) {
            for (estado in finales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals((evaluar[i]))) {
                    nuevoFinales.add(transicion.destino as Estado)
                    break
                }
        }
        if (nuevoFinales.isEmpty()) {
            return nuevoFinales
        }
        return terminarDeEvaluarCadena(nuevoFinales, evaluar, i + 1)
    }

    private fun clausura(finales: MutableList<Estado>): MutableList<Estado> {
        var nuevoFinales = mutableListOf<Estado>()
        var temporal = mutableListOf<Estado>()

        for (transicion in transiciones) {
            for (estado in finales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals('ε')) {
                    nuevoFinales.add(transicion.destino!!)
                    break
                }
        }
        if (!nuevoFinales.isEmpty())
            temporal = clausura(nuevoFinales)
        if (!temporal.isEmpty()) {
            for (estado in temporal) {
                finales.add(estado)
            }
        }
        return finales
    }

    private fun verificarCadena(evaluar: CharArray): Any {
        var stay = true
        for (i in 0..evaluar.size - 1) {
            for (c in alfabeto) {
                if (c == evaluar[i]) {
                    stay = true
                    break
                } else {
                    stay = false
                }
            }
        }
        return stay
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        for (transicion in transaccionesItems) {
            if (transicion.origen?.nombre.equals(v1.nombre)
                    && transicion.destino?.nombre.equals(v2.nombre)
                    && transicion.simbolo.equals(simbolo))
                return true
        }
        return false
    }

    override fun ConvertiraDFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun simbolosDeTransicionesEstanEnAlfabeto() : Boolean{
        for(transicion in transiciones){
            if(transicion.simbolo != 'ε'){
                if(!alfabeto.contains(transicion.simbolo)){
                    return false
                }
            }
        }
        return true
    }
}