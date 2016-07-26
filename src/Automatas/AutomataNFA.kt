package Automatas

import java.util.*

/**
 * Created by Jimmy Ramos on 25-Jul-16.
 */
class AutomataNFA : Automata() {
    override fun evaluar(cadena: String): Boolean {
       // throw UnsupportedOperationException()
        var currentState = this.estadoInicial

        var trans = ArrayList<Transicion>();

        trans = this.getNextTransitions(currentState) as ArrayList<Transicion>;

        if(!cadena.isEmpty()){
            var first = cadena[0];
            var  results =  hashSetOf<Boolean>();

            for (t in trans){
                if(t.simbolo ==first ){
                    currentState = t.destino!!;
                    results.add(evaluar(cadena.substring(1), currentState)) ;
                }
            }

            for( value in results){
                if(value){ return true;}
            }

        }else{
            for (s in  estadosDeAceptacion) {
                if (currentState.nombre.equals(s.nombre)) {
                    return true;
                }
            }
        }
        return false;
    }

    private fun evaluar(cadena: String, currentState: Estado): Boolean {
        var currentState = this.estadoInicial

        var trans = ArrayList<Transicion>();

        trans = this.getNextTransitions(currentState) as ArrayList<Transicion>;

        if(!cadena.isEmpty()){
            var first = cadena[0];
            var  results =  hashSetOf<Boolean>();

            for (t in trans){
                if(t.simbolo ==first ){
                    currentState = t.destino!!;
                    results.add(evaluar(cadena.substring(1), currentState)) ;
                }
            }

            for( value in results){
                if(value){ return true;}
            }

        }else{
            for (s in  estadosDeAceptacion) {
                if (currentState.nombre.equals(s.nombre)) {
                    return true;
                }
            }
        }
        return false;
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        throw UnsupportedOperationException()
    }
}