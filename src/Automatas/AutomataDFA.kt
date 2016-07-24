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

      /*  if (!verificarCadena(evaluar))
            return false*/

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

    private fun verificarCadena(evaluar: CharArray): Boolean {
        println(evaluar.size)
        println(alfabeto.size)
        /*for (i in 0..evaluar.size-1 ) {
            for (c in alfabeto) {
                if (c.equals( evaluar[i])) {
                    return true
                } else {
                    return false
                }
            }
        }*/
        return true
    }

    private fun estadoInicialEsDeAceptacion(): Boolean {
        for (estado in estados){
            if(estado.nombre.toString().equals(estadoInicial.nombre.toString()))
                return true
            return false
        }
        return false
        //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validarTransicion(v1 : Estado, nombre: Char): Boolean {
       // return transiciones.({ transicion -> transicion.origen?.nombre.equals(v1.nombre) && transicion.nombre.equals((nombre)) })
        println("v1 nombre "+v1.nombre)
        println("char nombre "+nombre)
        println("transiones size "+transaccionesItems.size)

        for (transicion in transaccionesItems) {
            println("entre")
            println("transiones origen nombre "+transicion.origen?.nombre)
            println("transiones nombre "+transicion.nombre)
            if (transicion.origen?.nombre.equals(v1.nombre.toString()) && transicion.nombre.equals(nombre.toString())) {
                return true
            } else {
                return false
            }
        }
        return false
    }

}