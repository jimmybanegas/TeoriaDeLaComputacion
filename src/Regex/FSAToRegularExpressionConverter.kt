package src.Regex

import Automatas.Automata
import Automatas.AutomataDFA
import Automatas.Estado
import Automatas.Transicion
import com.mxgraph.model.mxCell
import java.io.Serializable
import java.util.*

/**
 * Created by Jimmy Banegas on 20-Aug-16.
 */

open class FSAToRegularExpressionConverter : Serializable{
    companion object {

        /**
         * Returns true if automaton can be converted to a regular
         * expression (i.e. it has a unique initial and final state and it is a
         * finite state automaton, and the initial state is not the final state).

         * @param automaton
         * *            the automaton to convert
         * *
         * @return true if automaton can be converted to a regular
         * *         expression.
         */
        fun isConvertable(automaton: Automata): Boolean {
            if (automaton !is AutomataDFA)
                return false
            val finalStates = automaton.estadosDeAceptacion
            if (finalStates.size != 1) {
                return false
            }

            val initialState = automaton.estadoInicial
            if (finalStates[0] === initialState) {
                return false
            }
            return true
        }


        /**
         * Returns true if there are more removable states in automaton.

         * @param automaton
         * *            the automaton
         * *
         * @return true if there are more removable states in automaton.
         */
        fun areRemovableStates(automaton: Automata): Boolean {
            val states = automaton.estados
            for (k in states.indices) {
                if (isRemovable(states[k], automaton))
                    return true
            }
            return false
        }

        /**
         * Returns true if state is a removable state (i.e. it is not
         * the unique initial or final state).

         * @param state
         * *            the state to remove.
         * *
         * @param automaton
         * *            the automaton.
         * *
         * @return true if state is a removable state
         */
        fun isRemovable(state: Estado, automaton: Automata): Boolean {
            val finalStates = automaton.estadosDeAceptacion
            val initialState = automaton.estadoInicial
            if (state === finalStates[0] || state === initialState)
                return false
            return true
        }


        /**
         * Returns a Transition object that represents the transition between the
         * states with ID's p and q, with expression
         * as the transition label.

         * @param p
         * *            the ID of the from state.
         * *
         * @param q
         * *            the ID of the to state.
         * *
         * @param expression
         * *            the expression
         * *
         * @param automaton
         * *            the automaton
         * *
         * @return a Transition object that represents the transition between the
         * *         states with ID's p and q, with
         * *         expression as the transition label.
         */
        fun getTransitionForExpression(p: Int, q: Int,expression: String, automaton: Automata): Transicion {
            val fromState = automaton.getStateWithID(p)
            val toState = automaton.getStateWithID(q)

            val transition = Transicion(fromState, toState,expression)

            return transition
        }


        /**
         * Returns the expression on the transition between fromState
         * and toState in automaton.

         * @param fromState
         * *            the from state
         * *
         * @param toState
         * *            the to state
         * *
         * @param automaton
         * *            the automaton
         * *
         * @return the expression on the transition between fromState
         * *         and toState in automaton.
         */
        fun getExpressionBetweenStates(fromState: Estado, toState: Estado,
                                       automaton: Automata): String {
            var   transitions = automaton.getTransitionsFromStateToState(
                     fromState, toState)



          /*   if(transitions.size>0){
               val trans = transitions[0]


               return trans.simboloS
           }*/

            val trans = transitions[0]


            return trans.simboloS
        }

        /**
         * Returns the expression obtained from evaluating the following equation:
         * r(pq) = r(pq) + r(pk)r(kk)*r(kq), where p, q, and k represent the IDs of
         * states in automaton.

         * @param p
         * *            the from state
         * *
         * @param q
         * *            the to state
         * *
         * @param k
         * *            the state being removed.
         * *
         * @param automaton
         * *            the automaton.
         * *
         * @return the expression obtained from evaluating the following equation:
         * *         r(pq) = r(pq) + r(pk)r(kk)*r(kq), where p, q, and k represent the
         * *         IDs of states in automaton.
         */
        fun getExpression(p: Int, q: Int, k: Int, automaton: Automata): String {
            val fromState = automaton.getStateWithID(p)
            val toState = automaton.getStateWithID(q)
            val removeState = automaton.getStateWithID(k)

            val pq = getExpressionBetweenStates(fromState, toState, automaton)
            val pk = getExpressionBetweenStates(fromState, removeState,
                    automaton)
            val kk = getExpressionBetweenStates(removeState, removeState,
                    automaton)
            val kq = getExpressionBetweenStates(removeState, toState, automaton)

            val temp1 = star(kk)
            val temp2 = concatenate(pk, temp1)
            val temp3 = concatenate(temp2, kq)
            val label = or(pq, temp3)
            return label
        }

        /**
         * Returns the expression that represents r1 concatenated
         * with r2. (essentialy just the two strings concatenated).

         * @param r1
         * *            the first part of the expression.
         * *
         * @param r2
         * *            the second part of the expression.
         * *
         * @return the expression that represents r1 concatenated
         * *         with r2. (essentialy just the two strings
         * *         concatenated).
         */
        fun concatenate(r1: String, r2: String): String {
           if(r1.equals("null") || r2.equals("null")){
               return ""
           }
            var r1 = r1
            var r2 = r2
            if (r1 == EMPTY || r2 == EMPTY)
                return EMPTY
            else if (r1 == LAMBDA)
                return r2
            else if (r2 == LAMBDA)
                return r1
            if (Discretizer.or(r1).size > 1)
                r1 = addParen(r1)
            if (Discretizer.or(r2).size > 1)
                r2 = addParen(r2)
            return r1 + r2
        }

        /**
         * Returns the expression that represents r1 kleene-starred.

         * @param r1
         * *            the expression being kleene-starred.
         * *
         * @return the expression that represents r1 kleene-starred.
         */
        fun star(r1: String): String {
            if(r1.equals("null")){
                return ""
            }
            var r1 = r1
            if (r1 == EMPTY || r1 == LAMBDA)
                return LAMBDA
            if (Discretizer.or(r1).size > 1 || Discretizer.cat(r1).size > 1) {
                r1 = addParen(r1)
            } else {
                if (r1.endsWith(KLEENE_STAR))
                    return r1
            }
            return r1 + KLEENE_STAR
        }

        /**
         * Returns the string that represents r1 or'ed with r2.

         * @param r1
         * *            the first expression
         * *
         * @param r2
         * *            the second expression
         * *
         * @return the string that represents r1 or'ed with r2.
         */
        fun or(r1: String, r2: String): String {
            if(r1.equals("null") || r2.equals("null")){
                return ""
            }
            var r1 = r1
            var r2 = r2
            if (r1 == EMPTY)
                return r2
            if (r2 == EMPTY)
                return r1
            if (r1 == LAMBDA && r2 == LAMBDA)
                return LAMBDA
            if (r1 == LAMBDA)
                r1 = LAMBDA_DISPLAY
            if (r2 == LAMBDA)
                r2 = LAMBDA_DISPLAY
            // if(needsParens(r1)) r1 = addParen(r1);
            // if(needsParens(r2)) r2 = addParen(r2);
            return r1 + OR + r2
        }

        /**
         * Completely reconstructs automaton, removing all
         * transitions and state and adding all transitions in transitions.

         * @param state
         * *            the state to remove.
         * *
         * @param transitions
         * *            the transitions returned for removing state.
         * *
         * @param automaton
         * *            the automaton.
         */
        fun removeState(state: Estado, transitions: MutableList<Transicion>,
                        automaton: Automata) {

            val oldTransitions = automaton.transiciones
           /* for (k in oldTransitions.indices) {
                automaton.borrarTransicion(oldTransitions[k])
            }*/

           /* val iterator = automaton.transiciones.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                    automaton.transiciones.remove(item)
            }*/
            automaton.transiciones.clear()

           automaton.estados.remove(state)
           // automaton.borrarEstado(state)
            println("REMOVED STATE " +state.nombre)

            for (i in transitions.indices) {
                automaton.transiciones.add(transitions[i])
            }
        }


        /**
         * Returns a list of all transitions for automaton created by
         * removing state.

         * @param state
         * *            the state to remove.
         * *
         * @param automaton
         * *            the automaton.
         * *
         * @return a list of all transitions for automaton created by
         * *         removing state.
         */
        fun getTransitionsForRemoveState(state: Estado,
                                         automaton: Automata): MutableList<Transicion>? {
            if (!isRemovable(state, automaton))
                return null
            val list = mutableListOf<Transicion>()
            val k = state.getID()
            val states = automaton.estados
            for (i in states.indices) {
                val p = states[i].getID()
                if (p != k) {
                    for (j in states.indices) {
                        val q = states[j].getID()
                        if (q != k) {
                            val exp = getExpression(p, q, k, automaton)
                            list.add(getTransitionForExpression(p, q, exp,
                                    automaton))
                        }
                    }
                }
            }
          //  return list.toArray(arrayOfNulls<Transicion>(0))
            return list
        }

        /**
         * Adds a new transition to automaton between fromState
         * and toState on the symbol for the empty set.

         * @param fromState
         * *            the from state for the transition
         * *
         * @param toState
         * *            the to state for the transition
         * *
         * @param automaton
         * *            the automaton.
         * *
         * @return the FSATransition that was created
         */
        fun addTransitionOnEmptySet(fromState: Estado,
                                    toState: Estado, automaton: Automata): Transicion {
            val t = Transicion(fromState, toState, EMPTY)
            automaton.transiciones.add(t)
            return t
        }

        /**
         * Removes all transitions in transitions from automaton,
         * replacing them with a single transition in automaton
         * between fromState and toState labeled with
         * a regular expression that represents the labels of all the removed
         * transitions Or'ed together (e.g. a + (b*c) + (d+e)).

         * @param fromState
         * *            the from state for transitions and for the
         * *            newly created transition.
         * *
         * @param toState
         * *            the to state for transitions and for the newly
         * *            created transition.
         * *
         * @param transitions
         * *            the transitions being removed and combined into a single
         * *            transition
         * *
         * @param automaton
         * *            the automaton
         * *
         * @return the transition that replaced all of these
         */
        fun combineToSingleTransition(fromState: Estado,
                                      toState: Estado, transitions: Array<Transicion>, automaton: Automata): Transicion {
            var label = transitions[0].simboloS
            automaton.transiciones.remove(transitions[0])
            for (i in 1..transitions.size - 1) {
                label = or(label, transitions[i].simboloS)
                automaton.transiciones.remove(transitions[i])
            }
            val t = Transicion(fromState, toState, label)
            automaton.transiciones.add(t)
            return t
        }

        /**
         * Makes all final states in automaton non-final, adding
         * transitions from these states to a newly created final state on lambda.

         * @param automaton
         * *            the automaton
         */
        fun getSingleFinalState(automaton: Automata) {
            //StatePlacer sp = new StatePlacer();
            val finalState = Estado()
            //Estado finalState = automaton
            //	.createState(sp.getPointForState(automaton));

            for (k in automaton.estadosDeAceptacion.indices) {
                val state = automaton.estadosDeAceptacion[k]
                automaton.transiciones.add(Transicion(state, finalState, LAMBDA))
                automaton.estadosDeAceptacion.remove(state)
            }
            automaton.estadosDeAceptacion.add(finalState)
        }


        /**
         * Converts automaton to an equivalent automaton with a
         * single transition between all combinations of states. (if there are
         * currently more than one transition between two states, it combines them
         * into a single transition by or'ing the labels of all the transitions. If
         * there is no transition between two states, it creates a transition and
         * labels it with the empty set character (EMPTY).

         * @param automaton
         * *            the automaton.
         */
        fun convertToSimpleAutomaton(automaton: Automata) {
            if (!isConvertable(automaton))
                getSingleFinalState(automaton)
            val states = automaton.estados as Array<Estado>
            for (k in states.indices) {
                for (j in states.indices) {
                    val transitions = automaton.getTransitionsFromStateToState(states[k], states[j])
                    if (transitions.size == 0) {
                        addTransitionOnEmptySet(states[k], states[j], automaton)
                    }
                    if (transitions.size > 1) {
                        combineToSingleTransition(states[k], states[j], transitions as Array<Transicion>, automaton)
                    }
                }
            }
        }

        /**
         * Converts automaton into a generalized transition graph
         * with only two states, a unique initial state, and a unique final state.

         * @param automaton
         * *            the automaton.
         */
        fun convertToGTG(automaton: Automata) {
            var finalStates = automaton.estadosDeAceptacion
            val initialState = automaton.estadoInicial
         //   var states = automaton.estados
            println("ENTRA")

            if (finalStates[0].nombre == initialState.nombre) {
                var v1 = Any()
                var estado = Estado("x", v1 as Object)

                automaton.agregarEstado(estado.nombre, v1)

                var transicion = Transicion(initialState, estado, "\u03BB")

                var transicion2 = Transicion(estado, estado, EMPTY)
                var transicion3 = Transicion(estado, initialState, EMPTY)

                automaton.transiciones.add(transicion)
                automaton.transiciones.add(transicion2)
                automaton.transiciones.add(transicion3)
                //automaton.crearTodasLasTransicionesVacias()

                automaton.estadosDeAceptacion.clear()
                automaton.estadosDeAceptacion.add(estado)

            }
            automaton.crearTodasLasTransicionesVacias()

              var states = automaton.getStates()
                finalStates = automaton.estadosDeAceptacion
        //   try {
                for (k in states.indices) {
                    if (states[k].nombre != finalStates[0].nombre && states[k].nombre != initialState.nombre) {
                        println("SE PUEDE REMOVER " + states[k].nombre)
                        //  automaton.crearTodasLasTransicionesVacias()
                        val transitions = getTransitionsForRemoveState(states[k], automaton)
                        removeState(states[k], transitions as MutableList<Transicion>, automaton)
                        // k-=1
                       // states = automaton.estados
                        for (estado in automaton.estados) {
                            println(estado)
                        }
                    }
                }
            //}catch ( ex:Exception ){

            //}



        }
         /*   val iterator = automaton.estados.iterator()

            while (iterator.hasNext()) {

                val k = iterator.next()

                if (k.nombre != automaton.estadosDeAceptacion[0].nombre && k.nombre != automaton.estadoInicial.nombre) {
                    println("SE PUEDE REMOVER "+k.nombre)
                    //  automaton.crearTodasLasTransicionesVacias()
                    val transitions = getTransitionsForRemoveState(k, automaton)
                   // removeState(k, transitions!!, automaton)

                    automaton.transiciones.clear()

                    automaton.estados.remove(k)
                    // automaton.borrarEstado(k)
                    println("REMOVE STATE")

                    /*for (i in transitions) {
                        automaton.transiciones.add(transitions[i])
                    }*/
                    val iterator = transitions?.iterator()
                    while (iterator!!.hasNext()) {
                        val item = iterator.next()
                       // if (item.satisfiesCondition()) {
                        automaton.transiciones.add(item)
                       // }
                    }

                    for(estado in automaton.estados){
                        println(estado)
                    }

                    println("REMUEVE")
                }


            }*/


          /*  for (k in automaton.estados) {
                    if (k.nombre != finalStates[0].nombre && k.nombre != initialState.nombre) {
                        println("SE PUEDE REMOVER "+k.nombre)
                        //  automaton.crearTodasLasTransicionesVacias()
                        val transitions = getTransitionsForRemoveState(k, automaton)
                        removeState(k, transitions!!, automaton)

                        for(estado in automaton.estados){
                            println(estado)
                        }

                        println("REMUEVE")
                    }
                    println("SALE")
                }
            }*/


        /**
         * Returns true if word is one character long and it is a
         * letter.

         * @param word
         * *            the word
         * *
         * @return true if word is one character long and it is a
         * *         letter.
         */
        fun isSingleCharacter(word: String): Boolean {
            if (word.length != 1)
                return false
            val ch = word[0]
            return Character.isLetter(ch)
        }

        /**
         * Returns true if word needs parens. (i.e. it is an '+' (OR)
         * expression)

         * @param word
         * *            the word.
         * *
         * @return true if word needs parens. (i.e. it is an '+' (OR)
         * *         expression)
         */
        fun needsParens(word: String): Boolean {
            for (k in 0..word.length - 1) {
                val ch = word[k]
                if (ch == '+')
                    return true
            }
            return false
        }

        /**
         * Returns a string of word surrounded by parentheses. i.e. (),
         * unless it is unnecessary.

         * @param word
         * *            the word.
         * *
         * @return a string of word surrounded by parentheses.
         */
        fun addParen(word: String): String {
            return LEFT_PAREN + word + RIGHT_PAREN
        }

        /**
         * Returns a non-unicoded version of word for debug purposes.

         * @param word
         * *            the expression to output
         * *
         * @return a non-unicoded version of word for debug purposes.
         */
        fun getExp(word: String): String {
            if (word == LAMBDA)
                return "lambda"
            else if (word == EMPTY)
                return "empty"
            return word
        }

        /**
         * Returns the expression for the values of ii, ij, jj, and ji determined
         * from the GTG with a unique initial and final state.

         * @param ii
         * *            the expression on the loop off the initial state
         * *
         * @param ij
         * *            the expression on the arc from the initial state to the final
         * *            state.
         * *
         * @param jj
         * *            the expression on the loop off the final state.
         * *
         * @param ji
         * *            the expression on the arc from the final state to the initial
         * *            state.
         * *
         * @return the expression for the values of ii, ij, jj, and ji determined
         * *         from the GTG with a unique initial and final state.
         */
        fun getFinalExpression(ii: String, ij: String, jj: String, ji: String): String {
            val temp = concatenate(star(ii), concatenate(ij, concatenate(
                    star(jj), ji)))

            println(temp)

            val temp2 = concatenate(star(ii), concatenate(ij, star(jj)))
            println(temp2)
            // String expression =
            // concatenate(star(concatenate(LEFT_PAREN,concatenate(temp,RIGHT_PAREN))),
            // temp2);
           val expression = concatenate(star(temp), temp2)
           return expression
          //  return temp
        }

        /**
         * Returns the expression on the loop off the initial state of automaton.

         * @param automaton
         * *            a generalized transition graph with only two states, a unique
         * *            initial and final state.
         * *
         * @return the expression on the loop off the initial state of automaton.
         */
        fun getII(automaton: Automata): String {
            val initialState = automaton.estadoInicial
            return getExpressionBetweenStates(initialState, initialState, automaton)
        }

        /**
         * Returns the expression on the arc from the initial state to the final
         * state of automaton.

         * @param automaton
         * *            a generalized transition graph with only two states, a unique
         * *            initial and final state.
         * *
         * @return the expression on the arc from the initial state to the final
         * *         state of automaton.
         */
        fun getIJ(automaton: Automata): String {
            val initialState = automaton.estadoInicial
            val finalStates = automaton.estadosDeAceptacion
            return getExpressionBetweenStates(initialState, finalStates[0], automaton)
        }

        /**
         * Returns the expression on the loop off the final state of automaton

         * @param automaton
         * *            a generalized transition graph with only two states, a unique
         * *            initial and final state.
         * *
         * @return the expression on the loop off the final state of automaton
         */
        fun getJJ(automaton: Automata): String {
            val finalStates = automaton.estadosDeAceptacion
            return getExpressionBetweenStates(finalStates[0], finalStates[0], automaton)
        }

        /**
         * Returns the expression on the arc from the final state to the initial
         * state of automaton

         * @param automaton
         * *            a generalized transition graph with only two states, a unique
         * *            initial and final state.
         * *
         * @return the expression on the arc from the final state to the initial
         * *         state of automaton
         */
        fun getJI(automaton: Automata): String {
            val initialState = automaton.estadoInicial
            val finalStates = automaton.estadosDeAceptacion
            return getExpressionBetweenStates(finalStates[0], initialState, automaton)
        }

        /**
         * Returns the expression for the generalized transition graph automaton
         * with two states, a unique initial and unique final state. Evaluates to
         * the expression r = (r(ii)*r(ij)r(jj)*r(ji))*r(ii)*r(ij)r(jj)*. where
         * r(ij) represents the expression on the transition between state i (the
         * initial state) and state j (the final state)

         * @param automaton
         * *            the generalized transition graph with two states (a unique
         * *            initial and final state).
         * *
         * @return the expression for the generalized transition graph automaton
         * *         with two states, a unique initial and unique final state
         */
        fun getExpressionFromGTG(automaton: Automata): String {
            val ii = getII(automaton)
            println("inicial - inicial " + ii)
            val ij = getIJ(automaton)
            println("inicial - final " + ij)
            val jj = getJJ(automaton)
            println("final - final " + jj)
            val ji = getJI(automaton)
            println("final - inicial " + ji)

            return getFinalExpression(ii, ij, jj, ji)
        }

        /**
         * Returns the regular expression that represents automaton.

         * @param automaton
         * *            the automaton
         * *
         * @return the regular expression that represents automaton.
         */
        fun convertToRegularExpression(automaton: Automata): String? {
            if (!isConvertable(automaton))
                return null
            convertToGTG(automaton)
            return getExpressionFromGTG(automaton)
        }

        /* the string for the empty set. */
        val EMPTY = "\u00F8"

        /* the string for lambda. */
        val LAMBDA_DISPLAY = "e"

        val LAMBDA = ""

        /* the string for the kleene star. */
        val KLEENE_STAR = "*"

        /* the string for the or symbol. */
        val OR = "+"

        /** right paren.  */
        val RIGHT_PAREN = ")"

        /** left paren.  */
        val LEFT_PAREN = "("

    }
}
