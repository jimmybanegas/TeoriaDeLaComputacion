package Automatas

import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
class Estado : Serializable {
    var vertice: Object? = null
    var nombre: String = ""
    var posX: Double = 0.0
    var posY: Double = 0.0
    private var Id: Int = 0

    constructor(nombre: String, vertice: Object)  {
        this.nombre = nombre
        this.vertice = vertice

        val rn = Random()
        Id = rn.nextInt()
    }

    constructor()

    fun definirPosicionEnGrafico(posX: Double, posY: Double) {
        this.posX = posX
        this.posY = posY
    }

    override fun toString(): String {
        return "\nNombre: " +this.nombre +"\r\n"
                " Vertice: "+ this.vertice.toString()+ "\r\n"
              //  " Pos x :"+this.posX +"\r\n"+
               // " Pos y: "+ this.posX
    }

    constructor(nombre: String){
        this.nombre = nombre
        this.vertice = null
        val rn = Random()
        this.Id = rn.nextInt()
    }

    fun getID(): Int {
        return this.Id
    }

    fun isAccept(automataDFA: AutomataDFA): Boolean {
        for (State in automataDFA.estadosDeAceptacion) {
            if (State.nombre.equals(this.nombre))
                return true

        }
        return false
    }

   /* fun isAccept(): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

}