package Automatas

import com.mxgraph.model.mxCell

/**Created by Jimmy Banegas on 19-Jul-16.
 */
class AutomataDFA : Automata() {

    override fun evaluar(cadena: String) : Boolean {
        if (cadena.isEmpty()) {
            return estadoInicialEsDeAceptacion()
        }
        val evaluar = cadena.toCharArray()

        println(" evaluar cadena.tochararry "+evaluar.size)
        println("\r\nTamano alfabeto en evaluar : "+alfabeto.size)

        var fin = estadoInicial
        var stay = true

        println(" stay "+ stay)
        for (i in evaluar.indices) {
            for (transicion in transiciones) {
                if (transicion.origen?.nombre.toString().equals(fin.nombre.toString()) &&
                        transicion.nombre.toString().equals(evaluar[i].toString())) {
                    fin = transicion.destino as Estado
                    stay = true
                    break
                } else {
                    stay = false
                }
            }
        }
        if (!stay) {
            return false
        }
        for (estado in estadosDeAceptacion) {
            if (estado.nombre.toString().equals(fin.nombre.toString()))
                return true
        }
        return false
    }

    private fun estadoInicialEsDeAceptacion(): Boolean {
        for (estado in estados){
            when {
                estado.nombre.toString().equals(estadoInicial.nombre.toString()) -> return true
                else -> return false
            }
        }
        return false
    }

    override fun validarTransicion(v1 : Estado, nombre: Char): Boolean {
        for (transicion in transaccionesItems) {
            if (transicion.origen?.nombre.equals(v1.nombre) && transicion.nombre.equals(nombre)) return true
            else return false
        }
        return false
    }

}