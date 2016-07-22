package Ventanas

import Automatas.AutomataDFA
import Automatas.Estado
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxEvent
import com.mxgraph.util.mxEventObject
import com.mxgraph.view.mxGraph
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
class DFA (automataDFA: AutomataDFA) : JFrame() {
    // Variables declaration - do not modify
    private var jButtonProbarAutomata: javax.swing.JButton? = null
    private var jLabel1: javax.swing.JLabel? = null
    private var jLabel2: javax.swing.JLabel? = null
    private var jLabel3: javax.swing.JLabel? = null
    private var jLabel4: javax.swing.JLabel? = null
    private var jTextFieldAlfabeto: javax.swing.JTextField? = null
    private var jTextFieldCadena: javax.swing.JTextField? = null

    protected var graph = mxGraph()
    private var graphComponent: mxGraphComponent? = null

    protected var contadorEstados = 0
    protected var dfa: AutomataDFA? = null
    // End of variables declaration

    init {
        initComponents()
        dfa = automataDFA
    }

    fun initComponents() {
        jLabel1 = javax.swing.JLabel()
        jLabel3 = javax.swing.JLabel()
        jLabel4 = javax.swing.JLabel()
        jTextFieldAlfabeto = javax.swing.JTextField()
        jTextFieldCadena = javax.swing.JTextField()
        jLabel2 = javax.swing.JLabel()
        jButtonProbarAutomata = javax.swing.JButton()

        defaultCloseOperation = javax.swing.WindowConstants.DISPOSE_ON_CLOSE
        title = "Automata Finito Deterministico"
        contentPane.background = Color.WHITE
        (jButtonProbarAutomata as JButton).setBackground(Color.WHITE)
        graph = mxGraph()
        graph.isAllowLoops = true
        graph.isDisconnectOnMove = false
        graph.isConnectableEdges = false
        graph.isEdgeLabelsMovable = false
        graphComponent = mxGraphComponent(graph)
        (graphComponent as mxGraphComponent).viewport.background = Color.WHITE
        (graphComponent as mxGraphComponent).connectionHandler?.addListener(mxEvent.CONNECT) { sender: Any, evt: mxEventObject ->
            val edge = evt.getProperty("cell") as mxCell
            val origen = edge.getSource() as mxCell
            val destino = edge.getTarget() as mxCell
            val v1 = obtenerEstado(origen)
            val v2 = obtenerEstado(destino)
            if (v2 == null) {
                graph.model.remove(evt.getProperty("cell"))
                //return@graphComponent.getConnectionHandler().addListener
            }
            val nombre = escogerTransicion()
            if (nombre.toInt() == 0) {
                graph.model.remove(evt.getProperty("cell"))
              //  return@graphComponent.getConnectionHandler().addListener
            }
           /* if (!dfa?.CheckTransition(v1, nombre)) {
                showMessage("No se puede agregar una Transicion con el mismo valor!")
                graph.model.remove(evt.getProperty("cell"))
                return@graphComponent.getConnectionHandler().addListener
            }*/
            val name = nombre.toString()
            edge.setValue(name)
            dfa?.agregarTransicion(name,v1, v2, edge)
        }

        (graphComponent as mxGraphComponent).getGraphControl().addMouseListener(object : MouseAdapter() {
            override fun mouseReleased(e: MouseEvent) {
                if (e.isPopupTrigger()) {
                   // jPopupMenuForm.show(contentPane, e.getX(), e.getY())
                }
            }

            override fun mousePressed(e: MouseEvent) {
                // TODO Auto-generated method stub
                if (e.getClickCount() === 2 && e.getButton() === MouseEvent.BUTTON1) {
                    val x = e.getX()
                    val y = e.getY()
                    graph.model.beginUpdate()
                    try {
                        val parent = graph.defaultParent
                        val name = (contadorEstados++).toString()
                        val v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;" + "fillColor=lightblue")

                        dfa?.agregarEstado("q" + name, v1 as Object)
                    } finally {
                        graph.model.endUpdate()
                    }
                }
            }
        })

        (jLabel1 as JLabel).setText("Ingresar alfabeto:");

        (jLabel2 as JLabel).setText("Ingresar cadena:");

      /*  (jLabel4 as JLabel).setText("TIP: No olvidar agregar todo el alfabeto. Es posible que no acepte cadena!");

        (jLabel3 as JLabel).setText("PROTIP: Doble-Click para agregar Estado!");*/

        (jButtonProbarAutomata as JButton).setText("Evaluar Automata")
        (jButtonProbarAutomata as JButton).addActionListener({ e: ActionEvent -> probarCadenaEnAutomata(e) })

        val layout = javax.swing.GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel1)
                                .addGap(25, 25, 25)
                                .addComponent(jTextFieldAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel2)
                                .addGap(25, 25, 25)
                                .addComponent(jTextFieldCadena, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jButtonProbarAutomata)
                                .addContainerGap(30, java.lang.Short.MAX_VALUE.toInt()))
                        .addComponent(jLabel4).addComponent(jLabel3)
                        .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jTextFieldAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldCadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2).addComponent(jButtonProbarAutomata))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())))
    }

    private fun escogerTransicion(): Char {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun obtenerEstado(origen: mxCell): Estado {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun probarCadenaEnAutomata(e: ActionEvent) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


fun main(args : Array<String>) {

    val automataDFA = AutomataDFA()

    var dfa = DFA(automataDFA)

    dfa.initComponents()
    dfa.setVisible(true)
}