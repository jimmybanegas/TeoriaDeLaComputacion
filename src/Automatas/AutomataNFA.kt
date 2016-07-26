package Automatas

import java.util.*

/**
 * Created by Jimmy Ramos on 25-Jul-16.
 */
class AutomataNFA : Automata() {

    override fun ConvertiraDFA() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String): Boolean {
       // throw UnsupportedOperationException()
        var currentState = this.estadoInicial

        var trans =  this.getNextTransitions(currentState) as ArrayList<Transicion>;

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

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {

        var currentState = estadoActual

        var trans = this.getNextTransitions(currentState) as ArrayList<Transicion>;

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
        for (transicion in transaccionesItems) {
            /*if((transicion.origen?.nombre.equals(v1.nombre) && transicion.simbolo.equals(simbolo)) || (transicion.origen?.nombre.equals(v1.nombre)
                    && transicion.destino?.nombre.equals(v2.nombre) && transicion.simbolo.equals(simbolo)) )
            {*/

            println (transicion.origen?.nombre + " - "+ v1.nombre)
            println (transicion.destino?.nombre + " - "+ v2.nombre)
            println (transicion.simbolo + " - "+ simbolo)

            if (transicion.origen?.nombre.equals(v1.nombre) && transicion.destino?.nombre.equals(v2.nombre) && transicion.simbolo.equals(simbolo))
                return true
        }
        return false
    }
}