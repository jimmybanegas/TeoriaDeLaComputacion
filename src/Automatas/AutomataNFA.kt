package Automatas

import java.util.*

/**
 * Created by Jimmy Ramos on 25-Jul-16.
 */
class AutomataNFA : Automata() {

    override fun ConvertiraDFA(): AutomataDFA {
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

        System.out.println("Inicial " + dfa.estadoInicial.nombre)

        System.out.println("Tamaño estados: " + dfa.estados.size)

        for (estado in dfa.estados) {
            System.out.println(estado.nombre)
        }

        System.out.println("Tamaño finales: " + dfa.estadosDeAceptacion.size)

        for (estado in dfa.estadosDeAceptacion) {
            System.out.println(estado.nombre)
        }

        System.out.println("Tamaño transiciones: " + dfa.transiciones.size)
        for (transicion in dfa.transiciones) {
            println(transicion.simbolo + " " + transicion.origen?.nombre + " " + transicion.destino?.nombre)
        }

        return dfa
    }

    private fun obtenerEstadosTransicionesDFA(dfa: AutomataDFA, actuales: MutableList<Estado>, i: Int) {
      //  val nuevosActuales: mutableListOf<Estado>
        val nuevosActuales = mutableListOf<Estado>()

       // nuevosActuales = mutableListOf()
        if (i == alfabeto.size)
            return
        for (transicion in transiciones) {
            for (estado in actuales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals(alfabeto[i])) {
                    nuevosActuales.add(transicion.destino!!)
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

            nuevoEstado.nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> a + b  }
         //   nuevoEstado.nombre = nuevosActuales.map { estado -> estado.nombre }.reduce { plus, nuevoEstado -> nuevoEstado };
            // nuevoEstado.nombre = nuevosActuales

      //      if(nuevoEstado.nombre.contains("[")){
                dfa.estados.add(nuevoEstado)
          //  }

            agregarEstadosFinalesDFA(nuevosActuales, dfa)
            crearTransicionDFA(nuevosActuales, dfa, actuales, i)
            obtenerEstadosTransicionesDFA(dfa, nuevosActuales, 0)
        }
    }

    private fun siEsIgualAlgunEstadoDFA(nuevosActuales: MutableList<Estado>, dfa: AutomataDFA): Boolean {
        var nombre = ""

//        nombre = nuevosActuales.map({ estado -> estado.nombre }).toString()//
            nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> a + b  }

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

                   //nombre = nuevosActuales.map({ estadotemp -> estadotemp.nombre }).toString()
                    //nombre =  nuevosActuales.map { estado -> estado.nombre }.reduce { plus, nuevoEstado -> nuevoEstado };
                    nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> a + b  }

                    val v1 = obtenerEstado(nombre, dfa)

                    dfa.estadosDeAceptacion.add(v1!!)
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
      //  nombre = nuevosActuales.map({ estado -> estado.nombre }).toString()
        nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> a + b  }
       // nombre =  nuevosActuales.map { estado -> estado.nombre }.reduce { plus, nuevoEstado -> nuevoEstado };

        nuevaTransicion.destino = obtenerEstado(nombre, dfa)

        nombre = ""
       // nombre = actuales.map({ estado -> estado.nombre }).toString()
       // nuevosActuales.map { estado -> estado.nombre }.reduce { plus, nuevoEstado -> nuevoEstado };
        nombre = nuevosActuales.map({ estado -> estado.nombre }).reduce {  a, b -> a + b  }

        nuevaTransicion.origen = obtenerEstado(nombre, dfa)
        nuevaTransicion.simbolo = (alfabeto[i])

      /*  if(nuevaTransicion.destino != null && nuevaTransicion.origen!= null ){
            if(!transicionYaExiste(nuevaTransicion.origen!!, nuevaTransicion.destino!!,nuevaTransicion.simbolo))*/
                dfa.transiciones.add(nuevaTransicion)
    //    }

    }

    override fun evaluar(cadena: String): Boolean {
       // throw UnsupportedOperationException()
        var currentState = this.estadoInicial

        val trans =  this.getNextTransitions(currentState) as ArrayList<Transicion>;

        if(!cadena.isEmpty()){
            val first = cadena[0];
            val results =  hashSetOf<Boolean>();

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
            if (transicion.origen?.nombre.equals(v1.nombre)
                    && transicion.destino?.nombre.equals(v2.nombre)
                    && transicion.simbolo.equals(simbolo))
                return true
        }
        return false
    }
}