package src.Ventanas

import Automatas.Automata
import Automatas.AutomataDFA
import Ventanas.Validaciones
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.FileInputStream
import java.io.ObjectInputStream
import javax.swing.*
import Automatas.Estado
import Automatas.Transicion
import Ventanas.ConfigurationForWindows
import Ventanas.ventanaAutomata
import java.util.*

/**
 * Created by Jimmy Banegas on 28-Aug-16.
 */
class seleccioanarAutomatas (operation : String) : JFrame() {

    // Variables declaration - do not modify
    private var jButton1: javax.swing.JButton? = null
    private var jButton2: javax.swing.JButton? = null
    private var jButton3: javax.swing.JButton? = null
    private var jButton4: javax.swing.JButton? = null
    private var jDialog1: javax.swing.JDialog? = null
    // End of variables declaration

    protected var automata1: Automata? = null
    protected var automata2: Automata? = null
    protected var automataFinal: Automata? = null
    protected var operacion = operation

    init {
        initComponents()
    }

    fun initComponents() {
        jDialog1 = javax.swing.JDialog()
        jButton1 = javax.swing.JButton()
        jButton2 = javax.swing.JButton()
        jButton3 = javax.swing.JButton()
        jButton4 = javax.swing.JButton()

        val jDialog1Layout = javax.swing.GroupLayout((jDialog1 as JDialog).contentPane)
        (jDialog1 as JDialog).contentPane.layout = jDialog1Layout
        jDialog1Layout.setHorizontalGroup(
                jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, java.lang.Short.MAX_VALUE.toInt()))
        jDialog1Layout.setVerticalGroup(
                jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, java.lang.Short.MAX_VALUE.toInt()))

        defaultCloseOperation = javax.swing.WindowConstants.DISPOSE_ON_CLOSE

        (jButton1 as JButton).text = "Continuar"
        (jButton1 as JButton).addActionListener(ActionListener { evt -> evaluarCadena(evt) })

        (jButton2 as JButton).text = "Cancelar"
        (jButton2 as JButton).addActionListener(ActionListener { evt -> cerrar(evt) })

        (jButton3 as JButton).text = "Seleccionar automata 1"
        (jButton3 as JButton).addActionListener(ActionListener { evt -> seleccionar1(evt) })

        (jButton4 as JButton).text = "Seleccionar automata 2"
        (jButton4 as JButton).addActionListener(ActionListener { evt -> seleccionar2(evt) })

        val layout = javax.swing.GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(78, 78, 78).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(jButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jButton2)).addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()).addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap(72, java.lang.Short.MAX_VALUE.toInt())))
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(55, 55, 55).addComponent(jButton3).addGap(48, 48, 48).addComponent(jButton4).addGap(58, 58, 58).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton2).addComponent(jButton1)).addContainerGap(54, java.lang.Short.MAX_VALUE.toInt())))

        pack()
    }

    private fun seleccionar2(evt: ActionEvent) {
        val fc = JFileChooser()
        val returnVal =  fc.showOpenDialog(this@seleccioanarAutomatas)

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val file = fc.selectedFile
            //This is where a real application would open the file.
            try {
                println(file?.absolutePath)
                val door = FileInputStream(file?.absolutePath)

                val reader = ObjectInputStream(door)

                val x: Automata
                x = reader.readObject() as Automata
                automata2 = x

                println(automata2)

            } catch (ex: Exception) {
                println("Exception thrown during Opening: " + ex.toString())
            }
        } else {

        }
    }

    private fun seleccionar1(evt: ActionEvent) {
        val fc2 = JFileChooser()
        val returnVal =  fc2.showOpenDialog(this@seleccioanarAutomatas)

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            val file = fc2?.selectedFile
            //This is where a real application would open the file.
            try {
                println(file?.absolutePath)
                val door = FileInputStream(file?.absolutePath)

                val reader = ObjectInputStream(door)

                val x: Automata
                x = reader.readObject() as Automata


                automata1 = x

                println(automata1)
                println(x)

            } catch (ex: Exception) {
                println("Exception thrown during Opening: " + ex.toString())
            }
        } else {

        }
    }

    private fun cerrar(e: ActionEvent) {
       // this.setVisible(false);
        this.dispose()
    }

    private fun evaluarCadena(evt: java.awt.event.ActionEvent) {
        if(!(automata1?.estadosEstanVacios() as Boolean) && !(automata2?.estadosEstanVacios() as Boolean)){

            val automataFinal = procesarAutomatas(automata1,automata2,operacion)

            val frame = automataFinal.let { it -> ventanaAutomata(it) }

            ConfigurationForWindows.SetConfigurations(frame, "Automata Finito Determinístico")

            frame.pack()
            frame.isVisible = true
        }
    }

    private fun procesarAutomatas(automata1: Automata?, automata2: Automata?, operacion: String): Automata {
        val result = AutomataDFA()

        val automata1Characters = automata1?.alfabeto
        val automata2Characters = automata2?.alfabeto

        val automata1UniqueCharacters = automata1Characters?.subtract(automata2Characters!!)
        val newAlphabetCharacters = automata1UniqueCharacters?.union(automata2Characters!!)?.toList()


        result.alfabeto = newAlphabetCharacters as MutableList<Char>

        for (elem in result.alfabeto){
            println("\n"+ elem)
        }

        val nombre = automata1?.estadoInicial?.nombre + "." + automata2?.estadoInicial?.nombre

        val initialState = Estado(nombre)

        result.estadoInicial = initialState
        result.estados.add(initialState)
        val statesToProcess = ArrayList<String>()
        statesToProcess.add(initialState.nombre)
        val processedStates = ArrayList<String>()

        var continueInWhile = true
        var considerAutomata1ToProcess = true
        var considerAutomata2ToProcess = true

        var automata1State = ""
        var automata2State = ""

        while(continueInWhile){

            val possibleStatesToProcess = statesToProcess.subtract(processedStates)
            if(possibleStatesToProcess.size==0){
                continueInWhile = false
            } else {

                val currentState = possibleStatesToProcess.first()
                considerAutomata1ToProcess = false
                considerAutomata2ToProcess = false

                if(currentState.contains('.')){
                    considerAutomata1ToProcess = true
                    considerAutomata2ToProcess = true

                    val currentStateParts = currentState.split('.')
                    automata1State = currentStateParts[0]
                    automata2State = currentStateParts[1]
                } else {
                    for(i in 0..(automata1?.estados?.size?.minus(1) as Int)){
                        if(automata1?.estados?.get(i)?.nombre.equals(currentState)){
                            considerAutomata1ToProcess = true
                            break
                        }
                    }
                    for(i in 0..(automata2?.estados?.size?.minus(1) as Int)){
                        if(automata2?.estados?.get(i)?.nombre.equals(currentState)){
                            considerAutomata2ToProcess = true
                            break
                        }
                    }

                    if(considerAutomata1ToProcess){
                        automata1State = currentState
                    }
                    if(considerAutomata2ToProcess){
                        automata2State = currentState
                    }
                }

                var newPossibleState = ""
                for(a in 0..newAlphabetCharacters.size-1){
                    newPossibleState = ""
                    if(considerAutomata1ToProcess){
                       /* for(d in 0..(automata1?.transiciones?.size?.minus(1) as Int)){
                            var deltaData1 = automata1?.transiciones?.get(d).split('(').get(1).split('=')
                            var deltaData2 = deltaData1.get(0).split(')').get(0).split(',')
                            if((deltaData2.get(0).equals(automata1State)) && (deltaData2.get(1).equals(newAlphabetCharacters?.get(a)))){
                                newPossibleState = deltaData1.get(1)
                                break
                            }
                        }*/
                        for (transicion in automata1?.transiciones!!){
                            if((transicion.origen?.nombre.equals(automata1State)) &&
                                    (transicion.simbolo.equals(newAlphabetCharacters[a]))){
                                newPossibleState = transicion.destino?.nombre as String
                                break
                            }
                        }

                       /* val delta ="(q0,0)=q0/q1"
                        var deltaData1 = delta.split('(').get(1).split('=')
                        var deltaData2 = deltaData1[0].split(')').get(0).split(',')

                        println(deltaData1)
                        println(deltaData2)
                        print(deltaData2.get(0))
                        print(deltaData2.get(1))
                        print(deltaData1.get(1))*/

                    }
                    if(considerAutomata2ToProcess){
                        /*for(d in 0..(automata2?.transiciones?.size?.minus(1) as Int)){
                            var deltaData1 = automata2?.transiciones?.get(d).split('(').get(1).split('=')
                            var deltaData2 = deltaData1.get(0).split(')').get(0).split(',')
                            if((deltaData2.get(0).equals(automata2State)) && (deltaData2.get(1).equals(newAlphabetCharacters.get(a)))){
                                if(considerAutomata1ToProcess){
                                    newPossibleState = newPossibleState + "." + deltaData1.get(1)
                                } else {
                                    newPossibleState = deltaData1.get(1)
                                }
                                break
                            }
                        }*/
                        for (transicion in automata2?.transiciones!!){
                            if((transicion.origen?.nombre.equals(automata2State)) &&
                                    (transicion.simbolo.equals(newAlphabetCharacters[a]))){
                                if(considerAutomata1ToProcess){
                                    newPossibleState = newPossibleState + "." + transicion.destino?.nombre.toString()
                                } else {
                                    newPossibleState = transicion.destino?.nombre.toString()
                                }
                                break
                            }
                        }
                    }

                    if(newPossibleState != ""){

                        var newStateAlreadyAdded = false
                        for(p in 0..(result.estados.size-1)){
                            if(result.estados[p].nombre.equals(newPossibleState)){
                                newStateAlreadyAdded = true
                                break
                            }
                        }
                        if(!newStateAlreadyAdded) {
                            result.estados.add(Estado(newPossibleState))
                        }


                        val newDelta = "delta(" + currentState + "," + newAlphabetCharacters.get(a) + ")=" + newPossibleState
                        //result.transiciones.add(newDelta)
                        result.transiciones.add(Transicion(Estado(currentState),Estado(newPossibleState), newAlphabetCharacters[a]))
                        statesToProcess.add(newPossibleState)
                    }
                }
                processedStates.add(currentState)
            }
        }

        var isStateInAutomata1Acceptance = false
        var isStateInAutomata2Acceptance = false
        var isThisStateAcceptance = false

        for(r in 0..(result.estados.size-1)){

            val stateParts = ArrayList<String>()
            if(result.estados[r].nombre.contains('.')){
                val tStateParts = result.estados[r].nombre.split('.')
                for(t in 0..(tStateParts.size-1)){
                    stateParts.add(tStateParts[t])
                }
            } else {
                stateParts.add(result.estados[r].nombre)
            }

            isStateInAutomata1Acceptance = false
            isStateInAutomata2Acceptance = false

            for(s in 0..(stateParts.size-1)){

                for(i in 0..(automata1?.estadosDeAceptacion?.size?.minus(1) as Int)){
                    if(automata1?.estadosDeAceptacion?.get(i)?.nombre.equals(stateParts[s])){
                        isStateInAutomata1Acceptance = true
                        break
                    }
                }

                for(j in 0..(automata2?.estadosDeAceptacion?.size?.minus(1) as Int)){
                    if(automata2?.estadosDeAceptacion?.get(j)?.nombre.equals(stateParts[s])){
                        isStateInAutomata2Acceptance = true
                        break
                    }
                }
            }


            isThisStateAcceptance = false

            if(operacion.equals("union")){
                if(isStateInAutomata1Acceptance || isStateInAutomata2Acceptance){
                    isThisStateAcceptance = true
                }
            }

            if(operacion.equals("intersection")){
                if(isStateInAutomata1Acceptance && isStateInAutomata2Acceptance){
                    isThisStateAcceptance = true
                }
            }

            if(operacion.equals("difference")){
                if(isStateInAutomata1Acceptance && (!isStateInAutomata2Acceptance)){
                    isThisStateAcceptance = true
                }
            }

            if(isThisStateAcceptance){
                result.estadosDeAceptacion.add(result.estados.get(r))
            }
        }

        return result
    }
}