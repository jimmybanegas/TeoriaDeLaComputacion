package Ventanas

import Automatas.Automata

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
open class Validaciones {
    companion object {

        fun validarDatosDeAutomata(automata: Automata): String{
            if(automata?.estadosEstanVacios()){
                return "No hay ningun estado"
            }
            if(automata?.estadoInicialEstaVacio()){
                return "Estado inicial vacio"
            }
            if(automata?.estadosDeAceptacionEstanVacios()){
                return "No hay ningún estado de aceptacion"
            }
            if(automata?.transicionesEstanVacias()){
                return "No hay transiciones"
            }

            return ""
        }
    }
}

