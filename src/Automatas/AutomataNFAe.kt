package Automatas

/**
 * Created by Affisa-Jimmy on 29/7/2016.
 */
class AutomataNFAe : Automata() {
    override fun evaluar(cadena: String): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado, simbolo: Char): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun ConvertiraDFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}