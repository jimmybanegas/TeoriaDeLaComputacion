package Automatas

import java.util.*

/**
 * Created by Jimmy Ramos on 25-Jul-16.
 */
class AutomataNFA : Automata() {
    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: String): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun obtenerComplemento(): Automata {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minimizar(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertirAER(): String {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertirADFA(): AutomataDFA {
        val dfa = AutomataDFA()

        dfa.estadoInicial = this.estadoInicial

        val actuales = mutableListOf<Estado>()

        actuales.add(estadoInicial)

        for (estado in estadosDeAceptacion) {
            if (estado.nombre.equals(estadoInicial.nombre)) {
                dfa.estadosDeAceptacion.add(estado)
            }
        }

        dfa.estados.add(estadoInicial)

        obtenerEstadosTransicionesDFA(dfa, actuales, 0)

        return dfa
    }

    private fun obtenerEstadosTransicionesDFA(dfa: AutomataDFA, actuales: MutableList<Estado>, i: Int) {
        val nuevosActuales = mutableListOf<Estado>()

        if (i == alfabeto.size)
            return

        for (transicion in transiciones) {
            for (estado in actuales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals(alfabeto[i].toChar())) {
                    nuevosActuales.add(transicion.destino as Estado)
                    break
                }
        }

        obtenerEstadosTransicionesDFA(dfa, actuales, i + 1)

        if (!nuevosActuales.isEmpty()) {
            if (siEsIgualAlgunEstadoDFA(nuevosActuales, dfa)) {
                crearTransicionDFA(nuevosActuales, dfa, actuales, i)
                return
            }

            val nuevoEstado = Estado()

            nuevoEstado.nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

            dfa.estados.add(nuevoEstado)

            agregarEstadosFinalesDFA(nuevosActuales, dfa)
            crearTransicionDFA(nuevosActuales, dfa, actuales, i)
            obtenerEstadosTransicionesDFA(dfa, nuevosActuales, 0)
        }
    }

    private fun siEsIgualAlgunEstadoDFA(nuevosActuales: MutableList<Estado>, dfa: AutomataDFA): Boolean {
        var nombre = ""

            nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        for (estado in dfa.estados) {
            if (estado.nombre.equals(nombre)) {
                return true
            }
        }
        return false
    }

    private fun agregarEstadosFinalesDFA(nuevosActuales: MutableList<Estado>, dfa: AutomataDFA) {
        for (estadofin in estadosDeAceptacion)
            for (estado in nuevosActuales) {
                if (estadofin.nombre.equals(estado.nombre)) {
                    var nombre = ""

                    nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

                    val v1 = obtenerEstado(nombre, dfa)

                    dfa.estadosDeAceptacion.add(v1 as Estado)
                }
            }
    }

    private fun obtenerEstado(nombre: String, dfa: AutomataDFA): Estado? {
        for (estado in dfa.estados) {
            if (estado.nombre.equals(nombre)) {
                return estado
            }
        }
        return null
    }

    private fun crearTransicionDFA(nuevosActuales: MutableList<Estado>, dfa: AutomataDFA, actuales: MutableList<Estado>, i: Int) {
        val nuevaTransicion = Transicion()
        var nombre = ""
        nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        nuevaTransicion.destino = obtenerEstado(nombre, dfa)
        println(nuevaTransicion.destino.toString())

        nombre = ""

        nombre = actuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        nuevaTransicion.origen = obtenerEstado(nombre, dfa)
        nuevaTransicion.simbolo = (alfabeto[i].toChar())

        dfa.transiciones.add(nuevaTransicion)

    }

    override fun evaluar(cadena: String): Boolean {
        var estadoActual = this.estadoInicial

        val trans =  this.obtenerTransicionesSiguientes(estadoActual) as ArrayList<Transicion>;

        if(!cadena.isEmpty()){
            val primero = cadena[0];
            val resultados =  hashSetOf<Boolean>();

            for (t in trans){
                if(t.simbolo ==primero ){
                    estadoActual = t.destino!!;
                    resultados.add(evaluar(cadena.substring(1), estadoActual)) ;
                }
            }

            for( value in resultados){
                if(value){ return true;}
            }

        }else{
            for (s in  estadosDeAceptacion) {
                if (estadoActual.nombre.equals(s.nombre)) {
                    return true;
                }
            }
        }
        return false;
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {

        var currentState = estadoActual

        var trans = this.obtenerTransicionesSiguientes(currentState) as ArrayList<Transicion>;

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
        for (transicion in transicionesItems) {
            if (transicion.origen?.nombre.equals(v1.nombre)
                    && transicion.destino?.nombre.equals(v2.nombre)
                    && transicion.simbolo.equals(simbolo))
                return true
        }
        return false
    }
}