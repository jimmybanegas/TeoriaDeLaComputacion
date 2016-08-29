package Ventanas

import Automatas.AutomataDFA
import Automatas.AutomataNFA
import Automatas.AutomataNFAe
import Automatas.ExpresionRegular
import src.Ventanas.seleccioanarAutomatas
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
class Principal : JFrame(){

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var jButtonCerrar: javax.swing.JButton? = null
    private var jButtonOK: javax.swing.JButton? = null
    private var jComboBoxAutomata: JComboBox<*>? = null
    private var jLabel1: javax.swing.JLabel? = null
    private var jLabel2: javax.swing.JLabel? = null
    private var jLabel3: javax.swing.JLabel? = null
    private var jLabel4: javax.swing.JLabel? = null
    private var jMenuBar1: javax.swing.JMenuBar? = null
    // End of variables declaration//GEN-END:variables

    init {
        initComponents()
    }

    fun initComponents() {
        jButtonOK = javax.swing.JButton()
        jButtonCerrar = javax.swing.JButton()
        jLabel1 = javax.swing.JLabel()
        jLabel2 = javax.swing.JLabel()
        jLabel3 = javax.swing.JLabel()
        jLabel4 = javax.swing.JLabel()
        jMenuBar1 = javax.swing.JMenuBar()

        defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE

        var AutomatonStrings = arrayOf("Autotomata Finito Deterministico (DFA)",
                                        "Automata Finito No Determinista (NFA)",
                                        "Automata Finito No Determinista Epsilon (NFAε)" ,
                                        "Expresion Regular a(NFA-e)",
                                        "Union de automatas", "Interseccion de automatas", "Resta de automatas")

        jComboBoxAutomata = JComboBox(AutomatonStrings)


        (jButtonOK as JButton).text = "OK"
        setLocationRelativeTo(null)
        contentPane.background = Color.WHITE
        (jButtonCerrar as JButton).background = Color.WHITE
        (jButtonOK as JButton).background = Color.WHITE
        (jComboBoxAutomata as JComboBox<*>).setBackground(Color.WHITE)
        (jButtonOK as JButton).addActionListener({ evt -> jButtonOKActionPerformed(evt) })

        (jButtonCerrar as JButton).text = "Cerrar"
        (jButtonCerrar as JButton).addActionListener({ evt -> jButtonCerrarActionPerformed(evt) })

        (jLabel1 as JLabel).text = "Teoría de la computacion - UNITEC SPS "

        (jLabel2 as JLabel).text = "Proyecto desarrollado por: Jimmy Ramos - 21141016 "

        (jLabel3 as JLabel).text = "Lenguaje: Kotlin y JGraph"

        (jLabel4 as JLabel).text = "Seleccione el tipo de Autómata que quiere probar:"

        jMenuBar = jMenuBar1

        val layout = javax.swing.GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBoxAutomata, 0, 777, java.lang.Short.MAX_VALUE.toInt())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel4)
                                                        .addGroup(layout.createSequentialGroup()
                                                        .addGap(120, 120, 120)
                                                        .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(193, 193, 193).addComponent(jButtonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, java.lang.Short.MAX_VALUE.toInt()))).addContainerGap()))
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel1)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, java.lang.Short.MAX_VALUE.toInt())
                                .addComponent(jLabel4)
                                .addGap(27, 27, 27)
                                .addComponent(jComboBoxAutomata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButtonOK)
                                .addComponent(jButtonCerrar))
                                .addGap(148, 148, 148)))


        pack()

        (jComboBoxAutomata as JComboBox<*>).addActionListener { e ->
            val cb = e.source as JComboBox<*>
            val index = cb.selectedIndex

            when (index) {
                0 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 0
                }
                1 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 1
                }
                2 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 2
                }
                3 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 3
                }
                4 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 4
                }
                5 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 5
                }
                6 -> {
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 6
                }
                else ->
                    (jComboBoxAutomata as JComboBox<*>).selectedIndex = 0
            }
        }
    }

    private fun jButtonCerrarActionPerformed(evt: ActionEvent?) {
        this.setVisible(false);
        this.dispose();
    }

    private fun jButtonOKActionPerformed(evt: ActionEvent?) {
        var frame = JFrame()

        if (jComboBoxAutomata?.selectedIndex == 0) {
            //Este va a crear un automata DFA
            val automataDFA = AutomataDFA()
            frame = ventanaAutomata(automataDFA)

            ConfigurationForWindows.SetConfigurations(frame, "Automata Finito Determinístico")

        } else if (jComboBoxAutomata?.selectedIndex == 1) {
            //Este va a crear un automata NFA
            val automataNFA = AutomataNFA()
            frame = ventanaAutomata(automataNFA)

            ConfigurationForWindows.SetConfigurations(frame,"Automata Finito No Deterministico")

        } else if (jComboBoxAutomata?.selectedIndex == 2) {
            //Este va a crear un automata NFA epsilon
            val automataNFAe = AutomataNFAe()
            frame = ventanaAutomata(automataNFAe)

            ConfigurationForWindows.SetConfigurations(frame,"Automata Finito No Deterministico Epsilon")
        } else if (jComboBoxAutomata?.selectedIndex == 3) {
          //Este va a crear un automata NFA epsilon formado de la evaluación de la expresión regular
            var automataNFAe = AutomataNFAe()


            var expresion = JOptionPane.showInputDialog("Digite la expresión regular")

            if (expresion == null || expresion.isEmpty() ) {
                ConfigurationForWindows.messageDialog(contentPane,"Cancelado")
                return
            }else{
               automataNFAe = ExpresionRegular.Convertir(expresion) as AutomataNFAe
            }

            if(!automataNFAe.estadosEstanVacios()){
                frame = ventanaAutomata(automataNFAe)
                ConfigurationForWindows.SetConfigurations(frame,"Expresión regular a NFA-e")
            }
        }else if (jComboBoxAutomata?.selectedIndex == 4) {
            frame = seleccioanarAutomatas("union")
            ConfigurationForWindows.SetConfigurations(frame,"Union de automatas")
        }else if (jComboBoxAutomata?.selectedIndex == 5) {
            frame = seleccioanarAutomatas("intersection")
            ConfigurationForWindows.SetConfigurations(frame,"Interseccion de automatas")
        }else if (jComboBoxAutomata?.selectedIndex == 6) {
            frame = seleccioanarAutomatas("difference")
            ConfigurationForWindows.SetConfigurations(frame,"Diferencia de automatas")
        }

        frame.addWindowListener(object : WindowAdapter() {
           override fun windowClosing(windowEvent: WindowEvent) {
               this@Principal.setVisible(true)
           }
       })

       frame.pack()
       frame.isVisible = true
       this.isVisible = false
       this.dispose()
    }

}


fun main(args : Array<String>) {

    var principal = Principal()

    principal.initComponents()

    principal.setVisible(true)
}