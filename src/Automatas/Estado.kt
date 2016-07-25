package Automatas

import java.io.Serializable

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado : Serializable {
    var  vertice: Object? = null
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
    constructor(nombre: String, vertice: Object)  {
        this.nombre = nombre
        this.vertice = vertice
      /*  this.esInicial = esInicial
        this.esDeAceptacion = esDeAceptacion*/
    }

    constructor()

    var posX: Double = 0.0
    var posY: Double = 0.0

    fun definirPosicionEnGrafico(posX: Double, posY: Double) {
        this.posX = posX
        this.posY = posY
    }

    override fun toString(): String {
        return "\nNombre: " +this.nombre +"\r\n"
               // " Vertice: "+ this.vertice.toString()+ "\r\n"+
              //  " Pos x :"+this.posX +"\r\n"+
               // " Pos y: "+ this.posX
    }
}