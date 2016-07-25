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
        var fin = estadoInicial
        var stay = true

        for (i in evaluar.indices) {
            for (transicion in transiciones) {
                println("\n Transicion actual" +transicion.toString())
                println(" i actual :"+i)
                println(" fin actual :"+fin.toString())

                if (transicion.origen?.nombre.toString().equals(fin.nombre.toString()) &&
                        transicion.simbolo.toString().equals(evaluar[i].toString())) {

                    println(" fin cambió")
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
            println(" fin actual en estado de aceptacion :"+fin.toString())
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

    override fun transicionYaExiste(v1 : Estado, v2: Estado, simbolo: Char): Boolean {
        for (transicion in transaccionesItems) {
         /*if((transicion.origen?.nombre.equals(v1.nombre) && transicion.simbolo.equals(simbolo)) || (transicion.origen?.nombre.equals(v1.nombre)
                 && transicion.destino?.nombre.equals(v2.nombre) && transicion.simbolo.equals(simbolo)) )
         {*/
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simbolo.equals(simbolo) )
                return true
        }
        return false
    }

}