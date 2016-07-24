package Ventanas

import Automatas.Automata
import Automatas.AutomataDFA
import Automatas.Estado
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants
import com.mxgraph.util.mxEvent
import com.mxgraph.util.mxEventObject
import com.mxgraph.view.mxGraph
import java.awt.Color
import java.awt.event.*
import javax.swing.*

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
class ventanaAutomata(automata: Automata) : JFrame() {
    // Variables declaration - do not modify
    private var jButtonEvaluarAutomata: javax.swing.JButton? = null
    private var jLabel1: javax.swing.JLabel? = null
    private var jLabel2: javax.swing.JLabel? = null
    private var jLabel3: javax.swing.JLabel? = null
    private var jLabel4: javax.swing.JLabel? = null
    private var jTextFieldAlfabeto: javax.swing.JTextField? = null
    private var jTextFieldCadena: javax.swing.JTextField? = null

    protected var graph = mxGraph()
    private var graphComponent: mxGraphComponent? = null

    protected var contadorEstados = 0
    protected var automata: Automata? = null
    // End of variables declaration

    init {
        initComponents()
        this.automata = automata
    }

    fun initComponents() {
        createMenuBar();

        jLabel1 = javax.swing.JLabel()
        jLabel3 = javax.swing.JLabel()
        jLabel4 = javax.swing.JLabel()
        jTextFieldAlfabeto = javax.swing.JTextField()
        (jTextFieldAlfabeto as JTextField).addKeyListener( object: KeyAdapter() {
            override fun keyReleased(event: KeyEvent) {
                val content = (jTextFieldAlfabeto as JTextField).text
                if (content != "") {
                    jTextFieldCadena?.isEnabled = true
                } else {
                    jTextFieldCadena?.isEnabled = false
                }
            }
        });

        jTextFieldCadena = javax.swing.JTextField()
        (jTextFieldCadena as JTextField).isEnabled = false

        (jTextFieldCadena as JTextField).addKeyListener( object: KeyAdapter() {
            override fun keyReleased(event: KeyEvent) {
                val content = (jTextFieldCadena as JTextField).text
                if (content != "") {
                    jButtonEvaluarAutomata?.isEnabled = true
                } else {
                    jButtonEvaluarAutomata?.isEnabled = false
                }
            }
        });
        jLabel2 = javax.swing.JLabel()
        jButtonEvaluarAutomata = javax.swing.JButton()
        (jButtonEvaluarAutomata as JButton).isEnabled = false

        defaultCloseOperation = javax.swing.WindowConstants.DISPOSE_ON_CLOSE

        title = "Automata Finito Deterministico"

        contentPane.background = Color.WHITE
        (jButtonEvaluarAutomata as JButton).background = Color.WHITE
        graph = mxGraph()
        graph.isAllowLoops = true
        graph.isDisconnectOnMove = false
        graph.isConnectableEdges = false
        graph.isEdgeLabelsMovable = false
        graphComponent = mxGraphComponent(graph)
        (graphComponent as mxGraphComponent).viewport.background = Color.WHITE
        (graphComponent as mxGraphComponent).addKeyListener(object: KeyAdapter() {

            override fun keyReleased(e: KeyEvent) {}

            override fun  keyTyped(e: KeyEvent) { }

            override fun keyPressed(e: KeyEvent){
                // e.keyCode
                if (e.getKeyCode() == KeyEvent.VK_DELETE){
                  //  if (!graph.isSelectionEmpty()) {
                        delFunction()
                        //graph.model.remove(e.component.remove(e.source as MenuComponent?))
                       // bar.getdeleteaction().actionPerformed(null);
                        println("BORRAR")
                    }
            }

            private fun delFunction() {
                graph.model.beginUpdate()
                try {
                    var cells = graph.selectionCell

                    println(cells)
                    //  setEnabledButton(cells, true)
                  /*  val x = graph.getCellBounds(cells[0]).x
                    val y = graph.getCellBounds(cells[0]).y*/
                    //var p = Point(x.toInt(), y.toInt())
                    //  cells = graph.getChildCells(cells)
                    graph.model.remove(cells)
                    // statusLabel.setText("Status: Unsaved")
                }finally{
                    graph.model.endUpdate()
                }

            }
        })

        (graphComponent as mxGraphComponent).connectionHandler?.addListener(mxEvent.CONNECT) { sender: Any, evt: mxEventObject ->
                val edge = evt.getProperty("cell") as mxCell

               val style = graph.stylesheet.defaultEdgeStyle
                style.put(mxConstants.STYLE_ROUNDED, true)
                style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW)
            // Settings for edges
          //  edge = HashMap<String, Any>()
          /*  style.put(mxConstants.STYLE_ROUNDED, true)
            style.put(mxConstants.STYLE_ORTHOGONAL, false)
            style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_LOOP)
            style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR)
            style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC)
            style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ARROW_SPACING)
            style.put(mxConstants.STYLE_ALIGN, mxConstants.ARROW_OVAL)
            style.put(mxConstants.STYLE_STROKECOLOR, "#000000") // default is #6482B9
            style.put(mxConstants.STYLE_FONTCOLOR, "#446299")*/

            graph.stylesheet.defaultEdgeStyle = style

                //Validar que el target de la arista no sea null, si es nulo o no colisiona con otro estado, lo elimino
                if (edge.target == null  ) {
                    graph.model.remove(evt.getProperty("cell"))
                    return@addListener
                }

               //Los mxCell de orogen y destino
                val origen = edge.getSource() as mxCell
                val destino = edge.getTarget() as mxCell

                //Ahora tengo que ver a que estado pertenecen dichos Objetos del grafico
                val v1 = automata?.obtenerEstadoPorVertice(origen)
                val v2 = automata?.obtenerEstadoPorVertice(destino)

                if (v2 == null  ) {
                    graph.model.remove(evt.getProperty("cell"))
                    return@addListener
                }

                val nombre = nombrarTransicion()

                if (nombre.toInt() == 0) {
                    graph.model.remove(evt.getProperty("cell"))
                    return@addListener
                }

                var a = automata?.validarTransicion(v1 as Estado, nombre)

                 if ((a as Boolean)) {
                     ConfigurationForWindows.messageDialog(contentPane,"Ya existe transicion")
                     graph.model.remove(evt.getProperty("cell"))
                     return@addListener
                 }
                val name = nombre
                edge.setValue(name)

                automata?.agregarTransicion(name,v1 as Estado, v2, edge)

                print(automata.toString())

        }

        (graphComponent as mxGraphComponent).graphControl.addMouseListener(object : MouseAdapter() {
                override fun mousePressed(e: MouseEvent) {
                // TODO Auto-generated method stub
                if (e.clickCount === 2 && e.button === MouseEvent.BUTTON1) {
                    val x = e.getX()
                    val y = e.getY()
                    graph.model.beginUpdate()
                    try {
                        val parent = graph.defaultParent
                        val name = (contadorEstados++).toString()
                        val esDeAceptacion: Boolean

                        val dialogButton2 = JOptionPane.YES_NO_OPTION
                        val aceptacion = JOptionPane.showConfirmDialog(graphComponent,"¿Es estado de aceptación?", "Aceptación", dialogButton2)
                        if (aceptacion == 0) {
                            esDeAceptacion = true
                        } else {
                            esDeAceptacion = false
                        }

                         var v1 = Any()

                        //Para definir el estado inicial si el contador de estados es cero y pintarlo de otro color
                        if(contadorEstados-1==0)
                        {
                            if(esDeAceptacion){
                                //Este sería un estado inicial y de aceptacion a la vez
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;" + "fillColor=lightyellow")

                                //Agregar a lista de estados de aceptacion
                                automata?.agregarEstadoAceptacion(estado = Estado("q"+name,v1 as Object))
                            }else{

                                //Este sería un estado inicial pero no de aceptacion
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;" + "fillColor=lightyellow")
                            }
                            //Setear el estado inicial a mi DFA, lo haremos sea o no de aceptacion
                            automata?.estadoInicial= Estado("q"+name,v1 as Object)
                        }
                        else{
                            if(esDeAceptacion){
                                //Este sería un estado no inicial pero de aceptacion
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;" + "fillColor=lightgreen")

                                //Agregar a lista de estados de aceptacion
                                automata?.agregarEstadoAceptacion(estado = Estado("q"+name,v1 as Object))

                            }else
                            {
                                //Este sería un estado normal, no de aceptacion y no inicial
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;" + "fillColor=lightgreen")
                            }
                        }

                        //Agregar a la lista general de estados
                        automata?.agregarEstado("q" + name, v1 as Object)

                        //Imprimir
                        println(automata.toString())

                    } finally {
                        graph.model.endUpdate()
                    }
                }
            }
        })

        (jLabel1 as JLabel).setText("Ingresar alfabeto:");

        (jLabel2 as JLabel).setText("Ingresar cadena:");


        (jButtonEvaluarAutomata as JButton).text = "Evaluar Automata"
        (jButtonEvaluarAutomata as JButton).addActionListener({ e: ActionEvent -> evaluarCadena(e) })

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
                                .addComponent(jButtonEvaluarAutomata)
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
                                .addComponent(jLabel2).addComponent(jButtonEvaluarAutomata))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())))
    }

    private fun createMenuBar() {
        val menubar = JMenuBar()

        val file = JMenu("Archivo")
        file.mnemonic = KeyEvent.VK_F

        val ayuda = JMenu("Ayuda")
        ayuda.mnemonic = KeyEvent.VK_H

        val eMenuItem = JMenuItem("Salir")
        eMenuItem.mnemonic = KeyEvent.VK_E
        eMenuItem.toolTipText = "Salir de la application"
        eMenuItem.addActionListener { System.exit(0) }

        val guardarMenuItem = JMenuItem("Guardar")
        guardarMenuItem.mnemonic = KeyEvent.VK_G
        guardarMenuItem.toolTipText = "Guardar Automata"
       /* guardarMenuItem.addActionListener(object : ActionListener {
            override fun actionPerformed(event: ActionEvent) {
                System.exit(0)
            }
        })*/

        val abrirMenuItem = JMenuItem("Abrir")
        abrirMenuItem.mnemonic = KeyEvent.VK_A
        abrirMenuItem.toolTipText = "Abrir Automata"
        /* guardarMenuItem.addActionListener(object : ActionListener {
             override fun actionPerformed(event: ActionEvent) {
                 System.exit(0)
             }
         })*/

        val instrucciones = JMenuItem("Instrucciones")
        instrucciones.mnemonic = KeyEvent.VK_I
        instrucciones.toolTipText = "Cómo usarlo"
        instrucciones.addActionListener {
            val instrucciones = "1. Debe agregar primero el alfabeto\r\n" +
                    "2. Deberá agregar la cadena a evaluar\r\n" +
                    "3. Para agregar un nuevo estado debe dar doble clic sobre el panel"

            JOptionPane.showMessageDialog(contentPane,instrucciones, "Instrucciones",JOptionPane.INFORMATION_MESSAGE);
        }

        //Submenus de Archivo
        file.add(guardarMenuItem)
        file.add(abrirMenuItem)
        file.add(eMenuItem)

        //Submenus de Ayuda
        ayuda.add(instrucciones)

        menubar.add(file)
        menubar.add(ayuda)

        jMenuBar = menubar
    }

    private fun nombrarTransicion(): Char {
        var nombre: Char = 0.toChar()
        while (true) {
            val name = JOptionPane.showInputDialog("Simbolo / nombre:")
            if (name == null || name.isEmpty()) {
                ConfigurationForWindows.messageDialog(contentPane,"Cancelado")
                break
            }
            val na = name.toCharArray()
            if (na.size > 1) {
                ConfigurationForWindows.messageDialog(contentPane,"Escriba solo un caracter")
            } else if (na.size == 1) {
                nombre = na[0]
                break
            }
        }
        return nombre
    }

    private fun evaluarCadena(e: ActionEvent) {
        val msg =  Validaciones.validarDatosDeAutomata(automata!!)
        if( msg != ""){
            ConfigurationForWindows.messageDialog(contentPane, msg);
            return
        }
        if(!((automata?.crearAlfabeto(jTextFieldAlfabeto?.text?.toCharArray() as CharArray)) as Boolean)){
            ConfigurationForWindows.messageDialog(contentPane,"Alfabeto invalido");
            return
        }
        if(!(automata?.simbolosDeTransicionesEstanEnAlfabeto() as Boolean)){
            ConfigurationForWindows.messageDialog(contentPane,"Los símbolos no están en el alfabeto");
            return
        }
        if(automata?.evaluar(jTextFieldCadena?.text.toString()) as Boolean){
            JOptionPane.showMessageDialog(contentPane,"Se acepta la cadena", "Success",JOptionPane.INFORMATION_MESSAGE);
            return;
        }else
        {
            ConfigurationForWindows.messageDialog(contentPane,"No se acepta la cadena");
        }
    }

}


fun main(args : Array<String>) {
    val automataDFA = AutomataDFA()

    var dfa = ventanaAutomata(automataDFA)

    automataDFA.toString()

    dfa.initComponents()

    dfa.isVisible = true
}