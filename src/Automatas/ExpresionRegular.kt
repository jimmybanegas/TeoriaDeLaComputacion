package Automatas

import org.unitec.regularexpresion.RegularExpressionParser
import org.unitec.regularexpresion.tree.*


/**
 * Created by Jimmy Banegas on 08-Aug-16.
 */
open class ExpresionRegular {

    companion object{

        var index = 0
        var NameOfStates = "q0,q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15,q16,17,q18,q19,q20,q21,q22,q23,q24,q25"
        var nombresEstados = NameOfStates.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        fun Convertir(expresion: String): AutomataNFAe? {
            var rootNode: Node? = null

            try {

                rootNode = RegularExpressionParser().Parse(expresion)


            } catch (e: Exception) {

                e.printStackTrace()

            }

            return obtenerNFAE(rootNode)
        }

        private fun obtenerNFAE(rootNode: Node?): AutomataNFAe? {
            if (rootNode is CharNode) {
                val estadoOrigen = Estado(nombresEstados[index++],null!!)
                val estadoDestino = Estado(nombresEstados[index++],null!!)
                val transicion = Transicion(estadoOrigen, estadoDestino, rootNode.toString()[0],null!!)


                val nfaEpislon = AutomataNFAe()
                nfaEpislon.estados.add(estadoOrigen)
                nfaEpislon.estados.add(estadoDestino)
                nfaEpislon.transiciones.add(transicion)
                nfaEpislon.estadoInicial = estadoOrigen
                nfaEpislon.estadosDeAceptacion.add(estadoDestino)

                return nfaEpislon
            } else if (rootNode is ORNode) {
                val orNode = rootNode as ORNode
                val nuevoNfa = AutomataNFAe()
                val estadoInicial = Estado(nombresEstados[index++],null!!)
                val estadoFinal = Estado(nombresEstados[index++],null!!)
                nuevoNfa.estados.add(estadoInicial)
                nuevoNfa.estados.add(estadoFinal)
                nuevoNfa.estadoInicial = estadoInicial
                val nfaIzquierda = obtenerNFAE(orNode.getLeftNode())
                nuevoNfa.Unir(nfaIzquierda)
                nuevoNfa.setTransition(Transicion(estadoInicial, nfaIzquierda!!.estadoInicial, 'E',null!!))

                val nfaDerecha = obtenerNFAE(orNode.getRightNode())
                nuevoNfa.Unir(nfaDerecha)
                nuevoNfa.setTransition(Transicion(estadoInicial, nfaDerecha!!.estadoInicial, 'E',null!!))
                nuevoNfa.setTransition(Transicion(nfaIzquierda!!.estadosDeAceptacion.iterator().next(), estadoFinal, 'E',null!!))
                nuevoNfa.setTransition(Transicion(nfaDerecha!!.estadosDeAceptacion.iterator().next(), estadoFinal, 'E',null!!))
                nuevoNfa.StateFinals.clear()
                nuevoNfa.StateFinals.add(estadoFinal)
                return nuevoNfa

            } else if (rootNode is ANDNode) {
                val andNode = rootNode
                val nuevoNfa = AutomataNFAe()
                val nfaDerecha = obtenerNFAE(andNode.getRightNode())
                nuevoNfa.Unir(nfaDerecha)
                val nfaIzquierda = obtenerNFAE(andNode.getLeftNode())
                nuevoNfa.Unir(nfaIzquierda)
                nuevoNfa.Initial = nfaIzquierda?.estadoInicial
                nuevoNfa.StateFinals.add(nfaDerecha?.estadosDeAceptacion?.iterator()?.next())
                val transicionNueva = Transicion(nfaIzquierda?.estadosDeAceptacion!!.iterator().next(), nfaDerecha!!.estadoInicial, 'E',null!!)
                nuevoNfa.setTransition(transicionNueva)
                return nuevoNfa

            } else {
                val estadoInicial = Estado(nombresEstados[index++],null!!)
                val estadoFinal = Estado(nombresEstados[index++],null!!)
                val nuevoNfa = AutomataNFAe()
                nuevoNfa.estados.add(estadoInicial)
                nuevoNfa.estados.add(estadoFinal)
                nuevoNfa.estadoInicial = estadoInicial

                val nfa = obtenerNFAE((rootNode as RepeatNode).getNode())
                nuevoNfa.Unir(nfa)

                nuevoNfa.setTransition(Transicion(estadoInicial, nfa?.estadoInicial!!, 'E',null!!))
                nuevoNfa.setTransition(Transicion(nfa?.estadosDeAceptacion?.iterator()?.next()!!, nfa?.estadoInicial!!, 'E',null!!))
                nuevoNfa.setTransition(Transicion(nfa!!.estadosDeAceptacion.iterator().next(), estadoFinal, 'E',null!!))
                nuevoNfa.StateFinals.add(estadoFinal)
                nuevoNfa.setTransition(Transicion(nuevoNfa.estadoInicial, estadoFinal, 'E',null!!))
                return nuevoNfa

            }
        }
    }
}