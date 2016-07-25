package Automatas

import java.io.Serializable

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado : Serializable {
    var vertice: Object? = null
    var nombre: String = ""
    var posX: Double = 0.0
    var posY: Double = 0.0

    constructor(nombre: String, vertice: Object)  {
        this.nombre = nombre
        this.vertice = vertice
    }

    constructor()

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