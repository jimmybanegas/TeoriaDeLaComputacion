package src.Automatas

import Automatas.Automata
import Automatas.AutomataDFA
import Automatas.Estado
import Automatas.Transicion
import com.mxgraph.model.mxCell
import java.util.*

/**
 * Created by Affisa-Jimmy on 8/9/2016.
 */
class MaquinaTuring : Automata(){
    var cinta: ArrayList<String> = ArrayList<String>()
    var posicionActualCinta: Int = 5

    override fun evaluar(cadena: String): Boolean {

        if (!this.cinta.isEmpty()) {
            this.cinta = ArrayList()
            this.posicionActualCinta = 5
        }
        for (i in 0..4)
            this.cinta.add("B")
        for (i in 0..cadena.length - 1)
            this.cinta.add(cadena[i].toString())
        for (i in 0..4)
            this.cinta.add("B")

        val evaluar = cadena.toCharArray()
        if (!cadena.isEmpty()) {
            if (!verificarCadena(evaluar))
                return false
        }
        var finales: ArrayList<Estado>  = ArrayList()
        finales.add(estadoInicial)
        finales = terminarDeEvaluarCadena(finales)
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

    private fun verificarCadena(evaluar: CharArray): Boolean {
        var stay = true
        for (i in evaluar.indices) {
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

    private fun terminarDeEvaluarCadena(finales: ArrayList<Estado>): ArrayList<Estado> {
        val nuevosFinales: List<Estado>
        nuevosFinales = ArrayList()
        for (transicion in transiciones) {
            val t = transicion.simboloS.split(",")
            for (estado in finales)
                if (transicion.origen?.nombre.equals(estado.nombre) && t[0] == cinta[posicionActualCinta]) {
                    cinta.set(posicionActualCinta, t[1])
                    if (t[2] == "R")
                        posicionActualCinta += 1
                    else
                        posicionActualCinta -= 1
                    nuevosFinales.add(transicion.destino as Estado)
                    break
                }
            if (!nuevosFinales.isEmpty())
                break
        }
        if (nuevosFinales.isEmpty())
            return finales
        return terminarDeEvaluarCadena(nuevosFinales)

    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertirADFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertirAER(): String {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minimizar(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun obtenerComplemento(): Automata {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: String): Boolean {
        for (transicion in transicionesItems) {
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simboloS.equals(simbolo) )
                return true
        }
        return false
    }

    fun agregarTransicionPda(nombre:String, origen: Estado, destino:Estado, arista: mxCell){
        transiciones.add(Transicion(origen,destino,nombre,arista as Object))
    }

    override fun simbolosDeTransicionesEstanEnAlfabeto() : Boolean {
        /*  for(transicion in transiciones){
              if(!alfabeto.contains(transicion.simbolo)){
                  return false
              }
          }*/
        return true
    }
}