package src.Gramatica

import Automatas.Estado
import Automatas.Transicion
import src.Automatas.AutomataPDA
import src.Ventanas.seleccioanarAutomatas

/**
 * Created by Jimmy Banegas on 06-Sep-16.
 */
open class Gramatica {
    companion object{
        fun gramaticaAPda (gramatica :String) : AutomataPDA{
            val automataPda = AutomataPDA()

            val inicial = Estado("qo")

            automataPda.estados.add(inicial)
            automataPda.estadoInicial = inicial

            val segundo = Estado("q1")
            val tercero = Estado("q2")

            automataPda.estados.add(segundo)
            automataPda.estados.add(tercero)

            automataPda.estadosDeAceptacion.add(tercero)

            //Todas las posibles entradas
            val pdaAlphabet= gramatica.split("...")

            val notTerminales = mutableListOf<String>()

            for (elem in pdaAlphabet)
                notTerminales.add(elem.split(":")[0])

            val producciones = mutableListOf<String>()

            for (elem in pdaAlphabet)
                for(pr in (elem.split(":")[1].split(",")))
                    producciones.add(pr)

            val producciones2 = mutableListOf<String>()
            for (elem in pdaAlphabet)
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
            val transicion1 = Transicion(inicial,segundo,"ε,z0,"+axioma)
            val transicion2 = Transicion(segundo,tercero,"ε,z0,z0")

          //  newDelta = "delta(q1,E,Z)=(q2,Z)"

            automataPda.transiciones.add(transicion1)
            automataPda.transiciones.add(transicion2)

            //PDA.globalDeltas.add(newDelta)
            for(prod in producciones2){
                //var productionParts = g.productions.get(i).split(',')
              //  newDelta = "delta(q1,E,"+productionParts.get(0)+")=(q1,"+productionParts.get(1)+")"

                val todas = prod.split(",")
               // for (cada in todas){
                    val transicion = Transicion(segundo,segundo,"ε,"+todas[0]+","+todas[1])
                    automataPda.transiciones.add(transicion)
               // }

              //  PDA.globalDeltas.add(newDelta)
            }
            for (terminal in terminales){
                val transicion = Transicion(segundo,segundo,""+terminal+","+terminal+","+"ε")
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