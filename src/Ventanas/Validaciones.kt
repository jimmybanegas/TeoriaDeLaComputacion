package Ventanas

import Automatas.Automata
import Automatas.Estado
import com.mxgraph.view.mxGraph
import src.Automatas.AutomataPDA

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
            if(!(automata is AutomataPDA) && automata.estadosDeAceptacionEstanVacios()){
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

                for (estado in automata.estadosItems){
                    if(estado.vertice == cell){

                        //Si borramos el estado inicial tenemos que resetearlo
                        if(automata.estadoInicial.vertice == cell){
                            automata.estadoInicial = Estado()
                        }

                        //Borrar el estado si está en estados de aceptacion
                        for (estado in automata.estadosDeAceptacionItems){
                            if(estado.vertice == cell){
                                automata.estadosDeAceptacion.remove(estado)
                            }
                        }

                        //Luego borrarlo de el arreglo de estados general
                        automata.estados.remove(estado)

                        //Cuando un estado a borrar tenga una transicion asociada esta o estas se borrarán con él
                        for (transicion in automata.transicionesItems){
                            if(transicion.destino?.vertice == cell || transicion.origen?.vertice == cell){
                                automata.transiciones.remove(transicion)
                                println("Transicion asociada borrada "+ transicion.simbolo)
                            }
                        }

                        //Remover la representación gráfica
                        graph.model.remove(cell)
                    }
                }

                for (transicion in automata.transicionesItems){
                    if(transicion.arista == cell){

                        automata.transiciones.remove(transicion)

                        graph.model.remove(cell)
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

