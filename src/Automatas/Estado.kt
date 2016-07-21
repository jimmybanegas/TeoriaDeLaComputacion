package Automatas
import com.mxgraph.model.mxCell
import java.lang.Object;

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado {
    var  vertice: mxCell? = null
   // get() {return vertice}
    //set(value) {field=vertice}

    var nombre: String = ""
 //   get() {return nombre}
    //get() = nombre
   // set(value) {field=nombre}

  /*  var esInicial: Boolean = false
    get() {return esInicial}
    set(value) {field = esInicial}

    var esDeAceptacion: Boolean = false
        get() {return esDeAceptacion}
        set(value) {field = esDeAceptacion}
*/
    constructor(nombre: String, vertice: mxCell)  {
        this.nombre = nombre
        this.vertice = vertice
      /*  this.esInicial = esInicial
        this.esDeAceptacion = esDeAceptacion*/
    }

    constructor()

    //   constructor() : this()
}