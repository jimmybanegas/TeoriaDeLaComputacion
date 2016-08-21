package Automatas

/**
 * Created by Affisa-Jimmy on 29/7/2016.
 */
class AutomataNFAe : Automata() {
    override fun transicionYaExiste(v1: Estado, v2: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertirAER(): String {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String): Boolean {
        val evaluar = cadena.toCharArray()
        if (!cadena.isEmpty()) {
            if (!(verificarCadena(evaluar) as Boolean))
                return false
        }

        var finales = mutableListOf<Estado>()

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

    private fun terminarDeEvaluarCadena(finales: MutableList<Estado>, evaluar: CharArray, i: Int): MutableList<Estado> {
        val nuevoFinales = mutableListOf<Estado> ()

         val finales = clausura(finales)

        if (i === evaluar.size)
            return finales
        for (transicion in transiciones) {
            for (estado in finales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals((evaluar[i]))) {
                    nuevoFinales.add(transicion.destino as Estado)
                    break
                }
        }
        if (nuevoFinales.isEmpty()) {
            return nuevoFinales
        }
        return terminarDeEvaluarCadena(nuevoFinales, evaluar, i + 1)
    }

    private fun clausura(finales: MutableList<Estado>): MutableList<Estado> {
        var nuevoFinales = mutableListOf<Estado>()
        var temporal = mutableListOf<Estado>()

        for (transicion in transiciones) {
            for (estado in finales)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals('ε')) {
                    nuevoFinales.add(transicion.destino!!)
                    break
                }
        }
        if (!nuevoFinales.isEmpty())
            temporal = clausura(nuevoFinales)
        if (!temporal.isEmpty()) {
            for (estado in temporal) {
                finales.add(estado)
            }
        }
        return finales
    }

    private fun verificarCadena(evaluar: CharArray): Any {
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

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        for (transicion in transiciones) {
            if (transicion.origen?.nombre.equals(v1.nombre)
                    && transicion.destino?.nombre.equals(v2.nombre)
                    && transicion.simbolo.equals(simbolo))
                return true
        }
        return false
    }

    override fun convertirADFA(): AutomataDFA {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        var nfa = convertirANFA()

        var dfa = AutomataDFA()

        dfa.transiciones = nfa.transiciones
        dfa.estadosDeAceptacion = nfa.estadosDeAceptacion
        dfa.estados = nfa.estados
        dfa.alfabeto = nfa.alfabeto
        dfa.estadoInicial = nfa.estadoInicial

        return dfa
    }

    fun convertirANFA() : AutomataNFA{
        val nfa = AutomataNFA()
        var actuales= mutableListOf<Estado>()
        actuales.add(estadoInicial)
        actuales = clausura(actuales)
        val nuevoEstado = Estado()
        nuevoEstado.nombre = actuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        nfa.estadoInicial = nuevoEstado
        nfa.estados.add(nuevoEstado)
        actuales.clear()
        actuales.add(estadoInicial)
        obtenerEstadosTransicionesNFA(nfa, actuales, 0)

        return nfa
    }

    private fun obtenerEstadosTransicionesNFA(nfa: AutomataNFA, actuales: MutableList<Estado>, i: Int) {
        var nuevosActuales = mutableListOf<Estado>()
        var nuevosActualesClausura = mutableListOf<Estado>()
        var actualesClausura = mutableListOf<Estado> ()

        for (estado in actuales)
            actualesClausura.add(estado)

        actualesClausura = clausura(actualesClausura)
        if (i === alfabeto.size)
            return

        nuevosActuales = obtenerEstados(actualesClausura, i)

        if (!nuevosActuales.isEmpty()) {
            for (estado in nuevosActuales)
                nuevosActualesClausura.add(estado)
            nuevosActualesClausura = clausura(nuevosActualesClausura)
            if (!(siEsIgualAlgunEstadoNFA(nuevosActualesClausura, nfa) as Boolean)) {
                val nuevoEstado = Estado()
                nuevoEstado.nombre = nuevosActualesClausura.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

                nfa.estados.add(nuevoEstado)
                agregarEstadosFinalesNFA(nuevosActualesClausura, nfa)
                obtenerEstadosTransicionesNFA(nfa, nuevosActuales, 0)
            }
            if (siEsIgualAlgunEstadoNFA(actualesClausura, nfa) as Boolean) {
                agregarEstadosFinalesNFA(actualesClausura, nfa)
                crearTransicionNFA(nuevosActualesClausura, nfa, actualesClausura, i)
                obtenerEstadosTransicionesNFA(nfa, actuales, i + 1)
            }
        }
    }

    private fun crearTransicionNFA(actualesDestino: MutableList<Estado>, nfa: AutomataNFA, actuales: MutableList<Estado>, pos: Int) {
        val nuevaTransicion = Transicion()
        var nombre = ""
        nombre = actualesDestino.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

       nuevaTransicion.destino = obtenerEstado(nombre, nfa) as Estado?
        nombre = ""
        nombre = actuales.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        nuevaTransicion.origen = obtenerEstado(nombre, nfa) as Estado?
        nuevaTransicion.simbolo = alfabeto[pos]
        nfa.transiciones.add(nuevaTransicion)
    }

    private fun agregarEstadosFinalesNFA(nuevosActualesClausura: MutableList<Estado>, nfa: AutomataNFA) {
        for (estadofin in estadosDeAceptacion)
            for (estado in nuevosActualesClausura) {
                if (estadofin.nombre.equals(estado.nombre)) {
                    var nombre = ""
                    nombre = nuevosActualesClausura.map({ estadotemp -> estadotemp.nombre }).reduce {  a, b -> b + a  }

                    val v1 = obtenerEstado(nombre, nfa)
                    nfa.estadosDeAceptacion.add(v1 as Estado)
                }
            }
    }

    private fun  obtenerEstado(nombre: String, nfa: AutomataNFA): Any? {
        for (estado in nfa.estados) {
            if (estado.nombre.equals(nombre)) {
                return estado

            }
        }
        return null
    }

    private fun siEsIgualAlgunEstadoNFA(nuevosActualesClausura: MutableList<Estado>, nfa: AutomataNFA): Any {
        var nombre = ""
        nombre = nuevosActualesClausura.map({ estado -> estado.nombre }).reduce {  a, b -> b + a  }

        for (estado in nfa.estados) {
            if (estado.nombre.equals(nombre)) {
                return true
            }
        }
        return false
    }

    private fun obtenerEstados(actualesClausura: MutableList<Estado>, pos: Int): MutableList<Estado> {
        var nuevosActuales = mutableListOf<Estado>()

        for (transicion in transiciones) {
            for (estado in actualesClausura)
                if (transicion.origen?.nombre.equals(estado.nombre) && transicion.simbolo.equals((alfabeto[pos]))) {
                    nuevosActuales.add(transicion.destino!!)
                    break
                }
        }
        return nuevosActuales
    }

    fun Unir(nfaepsilon: AutomataNFAe) {
        for (a in nfaepsilon.estados) {
            this.estados.add(a)
        }
        for (t in nfaepsilon.transiciones) {
            this.transiciones.add(t)
        }
    }

    override fun simbolosDeTransicionesEstanEnAlfabeto() : Boolean{
        for(transicion in transiciones){
            if(transicion.simbolo != 'ε'){
                if(!alfabeto.contains(transicion.simbolo)){
                    return false
                }
            }
        }
        return true
    }
}