package Automatas

import com.mxgraph.model.mxCell
import java.util.*

/**Created by Jimmy Banegas on 19-Jul-16.
 */
class AutomataDFA : Automata() {
    override fun minimizar(): AutomataDFA {
         val stateSetMapping = HashMap<Estado, Set<Estado>>()

         val sets = partition(this, stateSetMapping)

         return createMinimizedAutomaton(this, stateSetMapping, sets)
    }

    private fun createMinimizedAutomaton(automataDFA: AutomataDFA, stateSetMapping: HashMap<Estado, Set<Estado>>, sets: Set<Set<Estado>> ): AutomataDFA {
         val minimizedAutomaton = AutomataDFA()
         minimizedAutomaton.alfabeto = automataDFA.alfabeto

         val minimizedSetStateMapping = HashMap<Set<Estado>, Estado>()

         for (set in sets) {
             val minimizedState = Estado()

             minimizedSetStateMapping.put(set, minimizedState)
         }

         val minimizedStates = LinkedList<Estado>()

         for (set in sets) {
             if (set.isEmpty()) { // non-final sets can be empty
                 continue
             }
             val state = set.iterator().next()

             val minimizedState = minimizedSetStateMapping.get(set)

             val randon = Random()
            // minimizedState = minimizedSetStateMapping[set]

             minimizedState?.nombre = "q"+randon.nextInt().toString()

             for (c in automataDFA.alfabeto) {
                 val toState = automataDFA.SearchDestiny(state, c)

                 if (toState != null) {
                     val toStateSet = stateSetMapping.get(toState)

                     val minimizedToState = minimizedSetStateMapping.get(toStateSet)
                                     // transicion(minimizedState, minimizedToState, c)

                   //  var  tran = Transicion(minimizedState, minimizedToState, c);

                //     println(" tran creada" +tran)
                     val v1 = mxCell()

                     minimizedAutomaton.agregarTransicion(c, minimizedState as Estado, minimizedToState as Estado, v1)
                 }
             }

             var initial = false
             for (s in set) {
                 if (automataDFA.estadoInicial.nombre.equals(s.nombre)) {
                     initial = true
                     break
                 }
             }

             if (initial) {
                 minimizedStates.addFirst(minimizedState)
             } else {
                 minimizedStates.addLast(minimizedState)
             }

             if (state.isAccept(this)) {
                 //minimizedState.setAccept(true)
                 println(state)
                 println(minimizedState)
                 minimizedAutomaton.estadosDeAceptacion.add(minimizedState!!)
             }


         }

         var first = true
         for (minimizedState in minimizedStates) {
             if (first) {
                 minimizedAutomaton.estados.add(minimizedState)
                 minimizedAutomaton.estadoInicial = minimizedState
                 first = false
             } else {
                 minimizedAutomaton.estados.add(minimizedState)
             }
         }

         return minimizedAutomaton
     }

    private fun partition(automataDFA: AutomataDFA, stateSetMapping: HashMap<Estado, Set<Estado>>): Set<Set<Estado>>  {

         //Separa los estados en dos sets FINALSTATES y NONFINALSTATES
         var sets = initSets(automataDFA, stateSetMapping)

         println(sets.size)

         for (set in sets){
             println(set.size)
             for (estado in set)
                 println(estado.nombre)

         }

         var partition: Set<Set<Estado>>? = null

         while (!sets.equals(partition)) {
             partition = sets
             sets = LinkedHashSet<Set<Estado>>()

             for (set in partition) {
                 split(automataDFA, set, stateSetMapping, sets)
             }
         }

         for (set in sets){
             println(set.size)
             for (estado in set)
                 println(estado.nombre)

         }

         return sets
     }

    private fun split(automataDFA: AutomataDFA, set: Set<Estado>, stateSetMapping: HashMap<Estado, Set<Estado>>, sets: LinkedHashSet<Set<Estado>>) {
         var firstSet: Set<Estado>? = null
         var secondSet: Set<Estado>? = null

         var splitted = false

         //Get all Alfabet
         for (c in automataDFA.alfabeto) {
             firstSet = LinkedHashSet<Estado>()
             secondSet = LinkedHashSet<Estado>()

             var firstToSet: Set<Estado>? = null

             var first = true

             for (state in set) {
                 val toState = automataDFA.SearchDestiny(state, c)

                 val toSet = if (toState == null) null else stateSetMapping[toState]

                 if (first) {
                     firstToSet = toSet

                     firstSet.add(state)

                     first = false
                 } else if (firstToSet == null && toSet == null || firstToSet != null && firstToSet.equals(toSet)) {
                     firstSet.add(state)
                 } else {
                     secondSet.add(state)
                 }
             }

             if (!secondSet.isEmpty()) {
                 splitted = true

                 break
             }
         }

         if (splitted) {
             for (state in firstSet!!) {
                 stateSetMapping.put(state, firstSet)
             }

             for (state in secondSet!!) {
                 stateSetMapping.put(state, secondSet)
             }

             sets.add(firstSet)
             sets.add(secondSet)
         } else {
             sets.add(set)
         }
     }

    private fun SearchDestiny(OriginState: Estado, Symbol: Char): Estado? {
         for (s in transiciones) {
             if (s.simboloS.equals(Symbol.toString()) && s.origen?.nombre.equals(OriginState.nombre)) {
                 val Destiny = ShearchSate(s.destino?.nombre)
                 return Destiny
             }
         }
         return null
     }

    private fun initSets(automataDFA: AutomataDFA, stateSetMapping: HashMap<Estado, Set<Estado>>): Set<Set<Estado>> {
         val sets = LinkedHashSet<Set<Estado>>()

         val finalStates = LinkedHashSet<Estado>()
         val nonFinalStates = LinkedHashSet<Estado>()

         for (state in automataDFA.estados) {
             val set = if (state.isAccept(automataDFA)) finalStates else nonFinalStates

             set.add(state)
             println(state)

             stateSetMapping.put(state, set)
         }

         sets.add(finalStates)

         for(state in finalStates)
             println(state)

         sets.add(nonFinalStates)
         for(state in nonFinalStates)
             println(state)

         return sets
     }

    private fun ShearchSate(nombre: String?): Estado? {
        for (s in transiciones) {
            if (s.origen?.nombre.equals(nombre))
                return s.origen as Estado
        }
        return null
    }

    override fun transicionYaExiste(v1: Estado, v2: Estado): Boolean {
     //   println((" Parametros "+v1?.nombre + " "+(v2?.nombre) ))
        for (transicion in transiciones) {
           // println((" Transicion "+transicion.origen?.nombre + " "+(transicion.destino?.nombre) +" "+transicion.simboloS))

            if(transicion.origen?.nombre.equals(v1.nombre) && transicion.destino?.nombre.equals(v2.nombre)){

                        return true
            }

        }
        return false
    }

    override fun convertirAER(): String {
        return "(0.1)*"

        //return FSAToRegularExpressionConverter.convertToRegularExpression(this)
    }

    override fun convertirADFA(): AutomataDFA {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String, estadoActual: Estado): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluar(cadena: String) : Boolean {
        var estadoActual = this.estadoInicial
        for (i in 0..cadena.length - 1) {

            val transicionActual = obtenerTransicionConSimbolo(estadoActual.nombre, cadena[i]) ?: return false

            estadoActual = transicionActual.destino!!
        }

        for (estado in estadosDeAceptacion) {
            if (estado.nombre.equals(estadoActual.nombre))
                return true
        }

        return false

    }

    private fun obtenerTransicionConSimbolo(nombre: String, simbolo: Char): Transicion? {
        for (trans in transiciones) {
            if (trans.origen?.nombre.equals(nombre) && trans.simbolo.equals(simbolo))
                return trans
        }
        return null
    }

    override fun transicionYaExiste(v1 : Estado, v2: Estado, simbolo: Char): Boolean {
        for (transicion in transicionesItems) {
            if(transicion.origen?.nombre.equals(v1.nombre) &&transicion.simbolo.equals(simbolo) )
                return true
        }
        return false
    }

}