package Ventanas

import Automatas.Automata
import Automatas.Estado
import Automatas.Transicion
import com.mxgraph.view.mxGraph

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
open class Validaciones {
    companion object {

        fun validarDatosDeAutomata(automata: Automata): String{
            if(automata.estadosEstanVacios()){
                return "No hay ningun estado"
            }
            if(automata.estadoInicialEstaVacio()){
                return "Estado inicial vacio"
            }
            if(automata.estadosDeAceptacionEstanVacios()){
                return "No hay ningún estado de aceptacion"
            }
            if(automata.transicionesEstanVacias()){
                return "No hay transiciones"
            }

            return ""
        }


        fun delFunction(graph: mxGraph, automata: Automata) {
            graph.model.beginUpdate()
            try {
                var cell = graph.selectionCell

                // println(cell.toString())

                for (estado in automata.estadosItems){
                    if(estado.vertice == cell){

                        //Si borramos el estado inicial tenemos que resetearlo
                        if(automata.estadoInicial.vertice == cell){
                            automata.estadoInicial = Estado()
                        }

                        //Borrar el estado si está en estados de aceptacion
                        for (estado in automata.estadosDeAceptacionItems){
                            if(estado.vertice == cell){
                                (automata.estadosDeAceptacion as MutableList<Estado>).remove(estado)
                            }
                        }

                        //Luego borrarlo de el arreglo de estados general
                        (automata.estados as MutableList<Estado>).remove(estado)

                        //Cuando un estado a borrar tenga una transicion asociada esta o estas se borrarán con él
                        for (transicion in automata.transaccionesItems){
                            if(transicion.destino?.vertice == cell || transicion.origen?.vertice == cell){
                                (automata.transiciones as MutableList<Transicion>).remove(transicion)
                                println("Transicion asociada borrada "+ transicion.simbolo)
                            }
                        }

                        //Remover la representación gráfica
                        graph.model.remove(cell)
                        println("Estado borrado")

                        println("\n"+ automata.toString())
                    }
                }

                for (transicion in automata.transaccionesItems){
                    if(transicion.arista == cell){

                        (automata.transiciones as MutableList<Transicion>).remove(transicion)

                        graph.model.remove(cell)

                        println("Transicion borrada")

                        println("\n"+ automata.toString())
                    }
                }

            }finally{
                graph.model.endUpdate()
            }
        }

        fun decimalDigitValue(c: Char): Int {
            if (c !in '0'..'9')
                throw IllegalArgumentException("Out of range")
            return c.toInt() - '0'.toInt() // Explicit conversions to numbers
        }
    }
}

