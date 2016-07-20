package Automatas

import com.mxgraph.model.mxCell
import org.omg.CORBA.Object

/**
 * Created by Affisa-Jimmy on 20/7/2016.
 */
class MaquinaDeTuring : Automata() {
    override fun agregarEstado(nombre: String, vertice: java.lang.Object) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun agregarTransicion(nombre: String, origen: Estado, destino: Estado, vertice: java.lang.Object) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun evaluar(cadena: String) : Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}