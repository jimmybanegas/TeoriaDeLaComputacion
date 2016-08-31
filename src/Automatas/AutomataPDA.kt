package src.Automatas

import Automatas.Automata
import Automatas.AutomataDFA
import Automatas.Estado
import Automatas.Transicion
import com.mxgraph.model.mxCell
import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 31/8/2016.
 */
class AutomataPDA : Automata(), Serializable {

    var simboloInicial: String? = "z0"
    var simboloActualDePila: String? = "z0"
    var stack: Stack<String>? = Stack()

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: String): Boolean {
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        for (transicion in transicionesItems) {
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simboloS.equals(simbolo) )
                return true
        }
        return false
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

    override fun evaluar(cadena: String): Boolean {

        this.simboloInicial = "z0"
        this.simboloActualDePila = "z0"
        stack?.push(simboloActualDePila)

        val evaluar = cadena.toCharArray()
        if (!cadena.isEmpty()) {
            if (!(verificarCadena(evaluar)))
                return false
        }
        var finales: ArrayList<Estado> = ArrayList()
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

    private fun terminarDeEvaluarCadena(finales: ArrayList<Estado>, evaluar: CharArray, pos: Int): ArrayList<Estado> {
        val nuevoFinales: List<Estado>
        nuevoFinales = ArrayList()
        val finales = ClausuraPDA(finales)
        if (pos === evaluar.size)
            return finales
        for (transicion in transiciones) {
            for (estado in finales) {
                val t = transicion.simboloS.split(",")
                if (t[0] == evaluar[pos].toString() && t[1] == simboloActualDePila
                        && transicion.origen?.nombre.equals(estado.nombre)) {
                    stack?.pop()
                    if (t[2].contains(simboloInicial.toString())) {
                        val sub = t[2].replace(simboloInicial.toString(), "")
                        stack?.push(simboloInicial)
                        if (!sub.isEmpty())
                            for (j in sub.length - 1 downTo 0) {
                                stack?.push(sub.get(j).toString())
                            }
                        nuevoFinales.add(transicion.destino as Estado)
                    } else {
                        if (t[2] != "ε") {
                            for (j in t[2].length - 1 downTo 0) {
                                stack?.push(t[2].get(j).toString())
                            }
                        }
                        nuevoFinales.add(transicion.destino as Estado)
                    }
                }
            }
            simboloActualDePila = stack?.pop()
            stack?.push(simboloActualDePila)
        }
        return terminarDeEvaluarCadena(nuevoFinales, evaluar, pos + 1)
    }

    private fun ClausuraPDA(finales: ArrayList<Estado>): ArrayList<Estado> {
        val nuevoFinales: List<Estado>
        var temporal: List<Estado>
        nuevoFinales = ArrayList()
        temporal = ArrayList()
        for (transicion in transiciones) {
            for (estado in finales) {
                val t = transicion.simboloS.split(",")
                if (t[0] == "ε" && t[1] == simboloActualDePila
                        && transicion.origen?.nombre.equals(estado.nombre)) {
                    stack?.pop()
                    if (t[2].contains(simboloInicial.toString())) {
                        val sub = t[2].replace(simboloInicial.toString(), "")
                        stack?.push(simboloInicial)
                        if (!sub.isEmpty()) {
                            for (j in sub.length - 1 downTo 0) {
                                stack?.push(sub.get(j).toString())
                            }
                            simboloActualDePila = sub.get(0).toString()
                        } else {
                            simboloActualDePila = stack?.pop()
                            stack?.push(simboloActualDePila)
                        }
                        nuevoFinales.add(transicion.destino as Estado)
                    } else {
                        if (t[2] != "ε") {
                            for (j in t[2].length - 1 downTo 0) {
                                stack?.push(t[2].get(j).toString())
                            }
                            simboloActualDePila = t[2].get(0).toString()
                        } else {
                            simboloActualDePila = stack?.pop()
                            stack?.push(simboloActualDePila)
                        }
                        nuevoFinales.add(transicion.destino as Estado)
                    }
                }
            }
        }
        if (!nuevoFinales.isEmpty())
            temporal = ClausuraPDA(nuevoFinales)
        if (!temporal.isEmpty()) {
            for (estado in temporal) {
                finales.add(estado)
            }
        }
        return finales
    }

    private fun verificarCadena(evaluar: CharArray): Boolean {
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