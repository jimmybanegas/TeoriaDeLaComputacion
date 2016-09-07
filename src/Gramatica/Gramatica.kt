package src.Gramatica

import Automatas.Estado
import Automatas.Transicion
import src.Automatas.AutomataPDA

/**
 * Created by Jimmy Banegas on 06-Sep-16.
 */
open class Gramatica {
    companion object{
        fun gramaticaAPda (gramatica :String) : AutomataPDA{
            val automataPda = AutomataPDA()

            val inicial = Estado("qo")
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

            val producciones2 = mutableListOf<String>()
            for (elem in alphabet)
                  producciones2.add(elem.split(":")[1])

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

            println("PRODUCCIONES2")
            for (prod in producciones2)
                println(prod)

            println("TERMINALES")
            for (no in terminales.distinct())
                println(no)

           /* var pdaAlphabet = "E"
            for(i in 0..(g.terminals.size-1)){
                pdaAlphabet = pdaAlphabet + "," + g.terminals.get(i)
            }
            PDA.globalAlphabet = pdaAlphabet

            var pdaPileAlphabet = pdaAlphabet + ",Z"
            for(i in 0..(g.nonTerminals.size-1)){
                pdaPileAlphabet = pdaPileAlphabet + "," + g.nonTerminals.get(i)
            }
            PDA.globalGammaAlphabet = pdaPileAlphabet*/

           /* var newDelta: String
            newDelta = "delta(q0,E,Z)=(q1,"+g.startSymbol+"Z)"
            PDA.globalDeltas.add(newDelta)
*/
            val axioma = notTerminales[0]

            val simbolo = "ε,z0,"+axioma.toString()+"z0"
            val transicion1 = Transicion(inicial,segundo,simbolo)
            val transicion2 = Transicion(segundo,tercero,"ε,z0,ε")

          //  newDelta = "delta(q1,E,Z)=(q2,Z)"

            automataPda.transiciones.add(transicion1)
            automataPda.transiciones.add(transicion2)

            //PDA.globalDeltas.add(newDelta)
            for(i  in 0..(alphabet.size-1)){
                //var productionParts = g.productions.get(i).split(',')
              //  newDelta = "delta(q1,E,"+productionParts.get(0)+")=(q1,"+productionParts.get(1)+")"
                //for (elem in alphabet)
                //val todas = prod.split(":")
            //    val ax = prod.split(":")[0]

              //  val todas = prod2.split(",")
               // for (cada in todas){
                //for(elem in todas){
                    val ind = alphabet[i].split(":")
                    val ax = ind[0]
                    val rest = ind[1].split(",")


                    for (elem in rest){
                        val simb = "ε,"+ax.toString()+","+elem.toString()

                        val transicion = Transicion(segundo,segundo,simb)
                        automataPda.transiciones.add(transicion)
                    }

               // }
                    //val transicion = Transicion(segundo,segundo,"ε,"+axioma+","+todas[0])
                   // val transicion2 = Transicion(segundo,segundo,"ε,"+axioma+","+todas[1])
                   // automataPda.transiciones.add(transicion)
                    //automataPda.transiciones.add(transicion2)
               // }

              //  PDA.globalDeltas.add(newDelta)
            }
            for (terminal in terminales){
                val simbolos = terminal.toString()+","+terminal.toString()+",ε"
                val transicion = Transicion(segundo,segundo,simbolos)
                if(!automataPda.transicionYaExiste(segundo,segundo,simbolos))
                    automataPda.transiciones.add(transicion)
            }
          /*  for(i in 0..(g.terminals.size-1)){
                newDelta = "delta(q1,"+g.terminals.get(i)+","+g.terminals.get(i)+")=(q1,E)"
                PDA.globalDeltas.add(newDelta)
            }*/

            return automataPda
        }
    }
}