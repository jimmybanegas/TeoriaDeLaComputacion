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
                val estadoOrigen = Estado(nombresEstados[index++])
                val estadoDestino = Estado(nombresEstados[index++])
                val transicion = Transicion(estadoOrigen, estadoDestino, rootNode as Char)

                val nfaEpislon = AutomataNFAe()
                nfaEpislon.estados.add(estadoOrigen)
                nfaEpislon.estados.add(estadoDestino)
                nfaEpislon.transiciones.add(transicion)
                nfaEpislon.estadoInicial = estadoOrigen
                nfaEpislon.estadosDeAceptacion.add(estadoDestino)

                return nfaEpislon
            } else if (rootNode is ORNode) {
                val orNode = rootNode
                val nuevoNfa = AutomataNFAe()
                val estadoInicial = Estado(nombresEstados[index++])
                val estadoFinal = Estado(nombresEstados[index++])
                nuevoNfa.estados.add(estadoInicial)
                nuevoNfa.estados.add(estadoFinal)
                nuevoNfa.estadoInicial = estadoInicial
                val nfaIzquierda = obtenerNFAE(orNode.getLeftNode())
                nuevoNfa.Unir(nfaIzquierda!!)
                nuevoNfa.transiciones.add(Transicion(estadoInicial, nfaIzquierda.estadoInicial, 'E'))

                val nfaDerecha = obtenerNFAE(orNode.getRightNode())
                nuevoNfa.Unir(nfaDerecha!!)
                nuevoNfa.transiciones.add(Transicion(estadoInicial, nfaDerecha.estadoInicial, 'E'))
                nuevoNfa.transiciones.add(Transicion(nfaIzquierda.estadosDeAceptacion.iterator().next(), estadoFinal, 'E'))
                nuevoNfa.transiciones.add(Transicion(nfaDerecha.estadosDeAceptacion.iterator().next(), estadoFinal, 'E'))
                nuevoNfa.estadosDeAceptacion.clear()
                nuevoNfa.estadosDeAceptacion.add(estadoFinal)
                return nuevoNfa

            } else if (rootNode is ANDNode) {
                val andNode = rootNode
                val nuevoNfa = AutomataNFAe()
                val nfaDerecha = obtenerNFAE(andNode.getRightNode())
                nuevoNfa.Unir(nfaDerecha!!)
                val nfaIzquierda = obtenerNFAE(andNode.getLeftNode())
                nuevoNfa.Unir(nfaIzquierda!!)
                nuevoNfa.estadoInicial = nfaIzquierda.estadoInicial
                nuevoNfa.estadosDeAceptacion.add(nfaDerecha.estadosDeAceptacion.iterator().next())
                val transicionNueva = Transicion(nfaIzquierda.estadosDeAceptacion.iterator().next(), nfaDerecha.estadoInicial, 'E')
                nuevoNfa.transiciones.add(transicionNueva)
                return nuevoNfa

            } else {
                val estadoInicial = Estado(nombresEstados[index++])
                val estadoFinal = Estado(nombresEstados[index++])
                val nuevoNfa = AutomataNFAe()
                nuevoNfa.estados.add(estadoInicial)
                nuevoNfa.estados.add(estadoFinal)
                nuevoNfa.estadoInicial = estadoInicial

                val nfa = obtenerNFAE((rootNode as RepeatNode).getNode())
                nuevoNfa.Unir(nfa!!)

                nuevoNfa.transiciones.add(Transicion(estadoInicial, nfa.estadoInicial, 'E'))
                nuevoNfa.transiciones.add(Transicion(nfa.estadosDeAceptacion.iterator().next(), nfa.estadoInicial, 'E'))
                nuevoNfa.transiciones.add(Transicion(nfa.estadosDeAceptacion.iterator().next(), estadoFinal, 'E'))
                nuevoNfa.estadosDeAceptacion.add(estadoFinal)
                nuevoNfa.transiciones.add(Transicion(nuevoNfa.estadoInicial, estadoFinal, 'E'))
                return nuevoNfa
            }
        }


        private fun obtainInverse(rootNode: Node): String {
            if (rootNode is CharNode)
                return rootNode.value
            else if (rootNode is ORNode) {
                return "(" +
                        obtainInverse(rootNode.leftNode) + "+" +
                        obtainInverse(rootNode.rightNode) +
                        ")"
            } else if (rootNode is ANDNode) {
                return "(" +
                        obtainInverse(rootNode.rightNode) + "." +
                        obtainInverse(rootNode.leftNode) +
                        ")"
            } else {
                return "(" +
                        obtainInverse((rootNode as RepeatNode).node) +
                        ")*"
            }

        }
    }
}