package src.Gramatica

import Automatas.Estado
import com.mxgraph.model.mxCell
import src.Automatas.AutomataPDA

/**
 * Created by Jimmy Banegas on 06-Sep-16.
 */
open class Gramatica {
    companion object{
        fun gramaticaAPda (gramatica :String) : AutomataPDA{
            val automataPda = AutomataPDA()

            val inicial = Estado("q0")
            val segundo = Estado("q1")
            val tercero = Estado("q2")

            automataPda.estados.add(inicial)
            automataPda.estados.add(segundo)
            automataPda.estados.add(tercero)

            automataPda.estadoInicial = inicial
            automataPda.estadosDeAceptacion.add(tercero)
            //Todas las posibles entradas
            val alphabet= gramatica.split("...")

            val notTerminales = mutableListOf<String>()

            for (elem in alphabet)
                notTerminales.add(elem.split(":")[0])

            val producciones = mutableListOf<String>()

            for (elem in alphabet)
                for(pr in (elem.split(":")[1].split(",")))
                    producciones.add(pr)

            val terminales = mutableListOf<String>()
            for (produccion in producciones){
                val charpr= produccion.toCharArray()
                for (chara in charpr){
                    if(!notTerminales.contains(chara.toString()))
                          terminales.add(chara.toString())
                }
                terminales.distinct()
            }

            println("no TERMINALES")
            for (terminal in notTerminales)
                println(terminal)

            println("PRODUCCIONES")
            for (prod in producciones)
                println(prod)


            println("TERMINALES")
            for (no in terminales.distinct())
                println(no)

            val axioma = notTerminales[0]

            val simbolo = "ε,z0,"+axioma.toString()+"z0"
            val simbolo2 = "ε,z0,ε"

            val arista = mxCell()

            automataPda.agregarTransicionPda(simbolo,inicial,segundo,arista)
            automataPda.agregarTransicionPda(simbolo2,segundo,tercero,arista)

            for (terminal in terminales.distinct()){
                if(terminal!="ε"){
                    val simbolos = terminal.toString()+","+terminal.toString()+",ε"

                    if(!automataPda.transicionYaExiste(segundo,segundo,simbolos))
                        automataPda.agregarTransicionPda(simbolos,segundo,segundo,arista)

                }
            }

            for(i  in 0..(alphabet.size-1)){
                    val ind = alphabet[i].split(":")
                    val ax = ind[0]
                    val rest = ind[1].split(",")

                    for (elem in rest){
                        if(ax!= "ε" ) {
                            val simb = "ε,"+ax.toString()+","+elem.toString()
                            automataPda.agregarTransicionPda(simb,segundo,segundo,arista)
                        }
                    }
            }

            return automataPda
        }
    }
}