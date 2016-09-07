package Ventanas

import Automatas.*
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants
import com.mxgraph.util.mxEvent
import com.mxgraph.util.mxEventObject
import com.mxgraph.view.mxGraph
import src.Automatas.AutomataPDA
import src.Regex.FSAToRegularExpressionConverter
import java.awt.Color
import java.awt.Rectangle
import java.awt.event.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
class ventanaAutomata(automata: Automata) : JFrame() {
    // Variables declaration - do not modify
    private var jButtonEvaluarAutomata: javax.swing.JButton? = null
    private var fc: JFileChooser? = null
    private var jLabel1: javax.swing.JLabel? = null
    private var jLabel2: javax.swing.JLabel? = null
    private var jLabel3: javax.swing.JLabel? = null
    private var jLabel4: javax.swing.JLabel? = null
    private var jTextFieldAlfabeto: javax.swing.JTextField? = null
    private var jTextFieldCadena: javax.swing.JTextField? = null

    private var menubar = JMenuBar()

    protected var graph = mxGraph()
    private var graphComponent: mxGraphComponent? = null

    protected var contadorEstados = 0
    public var subirDos = false
    protected var automata: Automata? = null
    // End of variables declaration

    init {
        initComponents()
        this.automata = automata

        println(automata)

        for(tran in automata.transiciones)
            println(tran)

        if(!automata.estadosEstanVacios()){
            automata.dibujarAutomata(this.graph)
            contadorEstados = automata.estados.size
        }

     //   initComponents()

        //Hacer las conversiones visibles para los automatas NFA Solamente
        if(automata is AutomataDFA){
           // menubar.getComponent(2).isVisible = false
            menubar.getMenu(2).getMenuComponent(0).isVisible = false
            menubar.getMenu(2).getMenuComponent(1).isVisible = true
            menubar.getMenu(2).getMenuComponent(2).isVisible = true
            menubar.getMenu(2).getMenuComponent(3).isVisible = true
        }else if(automata is AutomataNFAe){
            //  menubar.getComponent(2).isEnabled = false
            menubar.getMenu(2).getMenuComponent(0).isVisible = true
            menubar.getMenu(2).getMenuComponent(1).isVisible = false
            menubar.getMenu(2).getMenuComponent(2).isVisible = false
            menubar.getMenu(2).getMenuComponent(3).isVisible = false
        }else if(automata is AutomataNFA){
            menubar.getMenu(2).getMenuComponent(0).isVisible = true
            menubar.getMenu(2).getMenuComponent(1).isVisible = false
            menubar.getMenu(2).getMenuComponent(2).isVisible = false
            menubar.getMenu(2).getMenuComponent(3).isVisible = false
        }
        else if( automata is AutomataPDA){
            menubar.getMenu(2).getMenuComponent(0).isVisible = false
            menubar.getMenu(2).getMenuComponent(1).isVisible = false
            menubar.getMenu(2).getMenuComponent(2).isVisible = false
            menubar.getMenu(2).getMenuComponent(3).isVisible = false
        }
    }

    fun initComponents() {
        createMenuBar()

        jLabel1 = javax.swing.JLabel()
        jLabel3 = javax.swing.JLabel()
        jLabel4 = javax.swing.JLabel()

        jTextFieldAlfabeto = javax.swing.JTextField()
        (jTextFieldAlfabeto as JTextField).addKeyListener( object: KeyAdapter() {
            override fun keyReleased(event: KeyEvent) {
                val content = (jTextFieldAlfabeto as JTextField).text
                if (content != "") {
                    if(!((automata?.crearAlfabeto(jTextFieldAlfabeto?.text?.toCharArray() as CharArray)) as Boolean)){
                        ConfigurationForWindows.messageDialog(contentPane,"Alfabeto invalido")
                        menubar.getComponent(2).isEnabled = false
                    }else{
                        jTextFieldCadena?.isEnabled = true
                        jButtonEvaluarAutomata?.isEnabled = true
                        menubar.getComponent(2).isEnabled = true
                    }
                } else {
                    jTextFieldCadena?.isEnabled = false
                    jButtonEvaluarAutomata?.isEnabled = false
                    menubar.getComponent(2).isEnabled = false
                }
            }
        })

        jTextFieldCadena = javax.swing.JTextField()
        (jTextFieldCadena as JTextField).isEnabled = false

        (jTextFieldCadena as JTextField).addKeyListener( object: KeyAdapter() {
            override fun keyReleased(event: KeyEvent) {
                /*val content = (jTextFieldCadena as JTextField).text
                if (content != "") {
                    jButtonEvaluarAutomata?.isEnabled = true
                } else {
                    jButtonEvaluarAutomata?.isEnabled = false
                }*/
            }
        })

        jLabel2 = javax.swing.JLabel()
        jButtonEvaluarAutomata = javax.swing.JButton()
        (jButtonEvaluarAutomata as JButton).isEnabled = false

        defaultCloseOperation = javax.swing.WindowConstants.DISPOSE_ON_CLOSE

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
                if (e.keyCode == KeyEvent.VK_DELETE){
                    Validaciones.delFunction(graph, automata as Automata)
                }
            }
        })

        (graphComponent as mxGraphComponent).connectionHandler?.addListener(mxEvent.CONNECT) { sender: Any, evt: mxEventObject ->
                val edge = evt.getProperty("cell") as mxCell

                val style = graph.stylesheet.defaultEdgeStyle
            //    style.put(mxConstants.STYLE_ROUNDED, true)
                style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SEGMENT)
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

                if(!(automata is AutomataPDA)){
                    val simbolo = nombrarTransicion()

                    if (simbolo.toInt() == 0) {
                        graph.model.remove(evt.getProperty("cell"))
                        return@addListener
                    }

                    val a = automata?.transicionYaExiste(v1 as Estado, v2, simbolo)

                    if ((a as Boolean)) {
                        ConfigurationForWindows.messageDialog(contentPane,"Ya existe transicion")
                        graph.model.remove(evt.getProperty("cell"))
                        return@addListener
                    }
                    val name = simbolo
                    edge.value = name

                    automata?.agregarTransicion(name,v1 as Estado, v2, edge)
                }else{
                    val simbolo = nombrarTransicionPda()

                    val a = automata?.transicionYaExiste(v1 as Estado, v2, simbolo)

                    if ((a as Boolean)) {
                        ConfigurationForWindows.messageDialog(contentPane,"Ya existe transicion")
                        graph.model.remove(evt.getProperty("cell"))
                        return@addListener
                    }

                    val name = simbolo
                    edge.value = name

                    (automata as AutomataPDA).agregarTransicionPda(name,v1 as Estado, v2, edge)

                }

        }

        (graphComponent as mxGraphComponent).graphControl.addMouseListener(object : MouseAdapter() {
                override fun mousePressed(e: MouseEvent) {
                // TODO Auto-generated method stub
                if (e.clickCount === 2 && e.button === MouseEvent.BUTTON1) {
                    val x = e.x
                    val y = e.y
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
                        if(automata?.estadoInicialEstaVacio() as Boolean)
                        {
                            if(esDeAceptacion){
                                //Este sería un estado inicial y de aceptacion a la vez
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightyellow")

                                //Agregar a lista de estados de aceptacion
                                automata?.agregarEstadoAceptacion(estado = Estado("q"+name,v1 as Object))
                            }else{

                                //Este sería un estado inicial pero no de aceptacion
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightyellow")
                            }
                            //Setear el estado inicial a mi DFA, lo haremos sea o no de aceptacion
                            automata?.estadoInicial= Estado("q"+name,v1 as Object)
                        }
                        else{
                            if(esDeAceptacion){
                                //Este sería un estado no inicial pero de aceptacion
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightgreen")

                                //Agregar a lista de estados de aceptacion
                                automata?.agregarEstadoAceptacion(estado = Estado("q"+name,v1 as Object))

                            }else
                            {
                                //Este sería un estado normal, no de aceptacion y no inicial
                                v1 = graph.insertVertex(parent, null, "q" + name, x.toDouble(), y.toDouble(), 50.toDouble(), 50.toDouble(),
                                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightgreen")
                            }
                        }

                        //Agregar a la lista general de estados
                        automata?.agregarEstado("q" + name, v1 as Object)

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
                                .addComponent(jLabel2)
                                .addComponent(jButtonEvaluarAutomata))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(graphComponent, 500, javax.swing.GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())))
    }

    private fun nombrarTransicionPda(): String {
        var nombre = ""
        while (true) {
            val name = JOptionPane.showInputDialog("Simbolo / nombre:")
            if (name == null || name.isEmpty()) {
                ConfigurationForWindows.messageDialog(contentPane,"Cancelado")
                break
            }
            val na = name.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (na.size != 3) {
                ConfigurationForWindows.messageDialog(contentPane,"Ingresar caracter a consumir, pila a consumir, pila a agregar\nEjemplo: 0,z0,0z0")
            } else if (na.size == 3) {
                nombre = name
                break
            }
        }
        return nombre
    }

    private fun createMenuBar() {
        menubar = JMenuBar()

        val file = JMenu("Archivo")
        file.mnemonic = KeyEvent.VK_F

        val ayuda = JMenu("Ayuda")
        ayuda.mnemonic = KeyEvent.VK_H

        val conversiones = JMenu("Operaciones")
        conversiones.mnemonic = KeyEvent.VK_C

        val eMenuItem = JMenuItem("Salir")
        eMenuItem.mnemonic = KeyEvent.VK_E
        eMenuItem.toolTipText = "Salir de la application"
        eMenuItem.addActionListener {
            this.isVisible = false
            this.dispose()
        }

        val limpiarMenuItem = JMenuItem("Limpiar")
        limpiarMenuItem.mnemonic = KeyEvent.VK_E
        limpiarMenuItem.toolTipText = "Limpiar datos"
        limpiarMenuItem.addActionListener {
            graph.model.beginUpdate()
            graph.removeCells(graph.getChildVertices(graph.defaultParent));
            graph.model.endUpdate()

            automata?.Limpiar()

            this.contadorEstados = 0

            println(automata)
        }

        val guardarMenuItem = JMenuItem("Guardar")
        guardarMenuItem.mnemonic = KeyEvent.VK_G
        guardarMenuItem.toolTipText = "Guardar Automata"
        guardarMenuItem.addActionListener {

            val parent = graph.defaultParent
            val cells = graphComponent?.getCells(Rectangle(1000, 500), parent)

            for (obj in cells!!) {
                val cell = obj as mxCell
                if (automata?.obtenerEstadoPorNombre(cell.value.toString()) != null) {
                    if (cell.value.toString() == automata?.obtenerEstadoPorNombre(cell.value.toString())?.nombre) {
                        automata?.ponerPosicionEstado(cell.value.toString(), cell.geometry.x, cell.geometry.y)
                    }
                }
            }

            val fileFilter = FileNameExtensionFilter("ser files (*.ser)", "ser")
            fc = JFileChooser()

            (fc as JFileChooser).addChoosableFileFilter(fileFilter)
            (fc as JFileChooser).fileFilter = fileFilter

            val returnVal = (fc as JFileChooser).showSaveDialog(this)
              if (returnVal == JFileChooser.APPROVE_OPTION) {
                  val file = fc?.selectedFile
                  //This is where a real application would save the file.
                  try {
                      val fos = FileOutputStream(file?.absolutePath + ".ser")
                      val oos = ObjectOutputStream(fos)
                      oos.writeObject(automata)
                      oos.close()
                  } catch (ex: Exception) {
                      println("Exception thrown during Saving: " + ex.toString())
                  }

              } else {

              }
        }

        val abrirMenuItem = JMenuItem("Abrir")
        abrirMenuItem.mnemonic = KeyEvent.VK_A
        abrirMenuItem.toolTipText = "Abrir Automata"
        abrirMenuItem.addActionListener(object : ActionListener {
           override fun actionPerformed(event: ActionEvent) {
               fc = JFileChooser()
               val returnVal =  (fc as JFileChooser).showOpenDialog(this@ventanaAutomata)

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                   val file = fc?.selectedFile
                   //This is where a real application would open the file.
                   try {
                       println(file?.absolutePath)
                       val door = FileInputStream(file?.absolutePath)

                       val reader = ObjectInputStream(door)

                       val x: Automata
                       x = reader.readObject() as Automata
                       x.dibujarAutomata(graph)

                       automata = x

                       println(automata)

                       for ( estado in automata?.estados!!){
                          var x = 0
                           if( Validaciones.decimalDigitValue(estado.nombre[1]) > x){
                               x = Validaciones.decimalDigitValue(estado.nombre[1])
                           }
                           contadorEstados = x+1
                       }

                   } catch (ex: Exception) {
                       println("Exception thrown during Opening: " + ex.toString())
                   }
               } else {

               }
           }
        })


        val instrucciones = JMenuItem("Instrucciones")
        instrucciones.mnemonic = KeyEvent.VK_I
        instrucciones.toolTipText = "Cómo usarlo"
        instrucciones.addActionListener {
        val instrucciones = "1. Debe agregar primero el alfabeto\r\n" +
              "2. Deberá agregar la cadena a evaluar\r\n" +
              "3. Para agregar un nuevo estado debe dar doble clic sobre el panel\r\n"+
              "4. Para borrar una transicion o estado, seleccionarlo y luego presionar tecla delete\r\n"+
              "\n El estado inicial se marca en amarillo, los demás en verde y con doble círculo \n" +
              "si es de aceptación, si se borra el inicial, el próximo agregado se tomará como inicial"

               JOptionPane.showMessageDialog(contentPane,instrucciones, "Instrucciones",JOptionPane.INFORMATION_MESSAGE);
        }

        val convertirADFAMenuItem = JMenuItem("Convertir a DFA")
        convertirADFAMenuItem.mnemonic = KeyEvent.VK_T
        convertirADFAMenuItem.toolTipText = "Convertir a DFA"
        convertirADFAMenuItem.addActionListener {
            /*graph.model.beginUpdate()
            graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
            graph.model.endUpdate()*/

            val nuevo = automata?.convertirADFA()

            println(" nuevo resultado"+ nuevo)

           // automata?.Limpiar()

           // this.automata = nuevo

          //  this.contadorEstados = 0

           // automata?.dibujarAutomata(graph)
            val frame = nuevo?.let { it1 -> ventanaAutomata(it1) }

            ConfigurationForWindows.SetConfigurations(frame!!, "Automata Finito Determinístico")

            frame.pack()
            frame.isVisible = true

        }

        val convertirAExpresionMenuItem = JMenuItem("Convertir a ER")
        convertirAExpresionMenuItem.mnemonic = KeyEvent.VK_E
        convertirAExpresionMenuItem.toolTipText = "Convertir a ER"
        convertirAExpresionMenuItem.addActionListener {

            //  val expresionRegular = automata?.convertirAER()

            val expresionRegular = FSAToRegularExpressionConverter.convertToRegularExpression(automata!!)?.replace("λ","")

            if(!expresionRegular.isNullOrEmpty()){
                JOptionPane.showMessageDialog(contentPane,"La expresión regular es : " + expresionRegular, "Success",JOptionPane.INFORMATION_MESSAGE);
                println(" Expresion regular"+ expresionRegular)
            }
            else{
                ConfigurationForWindows.messageDialog(contentPane,"No se pudo convertir  ER");
            }

        }


        val minimizarMenuItem = JMenuItem("Minimizar")
        minimizarMenuItem.mnemonic = KeyEvent.VK_E
        minimizarMenuItem.toolTipText = "Minimizar"
        minimizarMenuItem.addActionListener {

            val nuevo = automata?.minimizar()

            val frame = nuevo?.let { it -> ventanaAutomata(it) }

            ConfigurationForWindows.SetConfigurations(frame!!, "Automata Finito Determinístico")

            frame.pack()
            frame.isVisible = true
        }

        val complementoMenuItem = JMenuItem("Obtener complemento")
        complementoMenuItem.toolTipText = "Complemento"
        complementoMenuItem.addActionListener {

            val nuevo = automata?.obtenerComplemento()

            val frame = nuevo?.let { it -> ventanaAutomata(it) }

            ConfigurationForWindows.SetConfigurations(frame!!, "Automata Finito Determinístico")

            frame.pack()
            frame.isVisible = true
        }

        //Submenus de Archivo
        file.add(guardarMenuItem)
        file.add(abrirMenuItem)
        file.add(limpiarMenuItem)
        file.add(eMenuItem)

        //Submenus de Ayuda
        ayuda.add(instrucciones)

        //Submenus de Conversiones
        conversiones.add(convertirADFAMenuItem)
        conversiones.add(convertirAExpresionMenuItem)
        conversiones.add(minimizarMenuItem)
        conversiones.add(complementoMenuItem)

        menubar.add(file)
        menubar.add(ayuda)
        menubar.add(conversiones)

        jMenuBar = menubar
}

private fun nombrarTransicion(): Char {
  var nombre: Char = 0.toChar()
  while (true) {
      var name = JOptionPane.showInputDialog("Simbolo / nombre:")
      if (name.isEmpty() && (automata is AutomataNFAe)) {
          name = 'ε'.toString()
      }

      if (name == null || name.isEmpty() && (automata is AutomataDFA || automata is AutomataNFA)) {
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
      if(!(automata?.simbolosDeTransicionesEstanEnAlfabeto() as Boolean)){
          ConfigurationForWindows.messageDialog(contentPane,"Los símbolos no están en el alfabeto");
          return
      }
        if(automata is AutomataDFA || automata is AutomataNFAe || automata is AutomataPDA){
            if(automata?.evaluar(jTextFieldCadena?.text.toString()) as Boolean){
                JOptionPane.showMessageDialog(contentPane,"Se acepta la cadena", "Success",JOptionPane.INFORMATION_MESSAGE);
                return;
            }else
            {
                ConfigurationForWindows.messageDialog(contentPane,"No se acepta la cadena");
            }
        }
        else{
            if(automata?.evaluar(jTextFieldCadena?.text.toString(), automata!!.estadoInicial) as Boolean){
                JOptionPane.showMessageDialog(contentPane,"Se acepta la cadena", "Success",JOptionPane.INFORMATION_MESSAGE);
                return;
            }else
            {
                ConfigurationForWindows.messageDialog(contentPane,"No se acepta la cadena");
            }
        }

    }
}


fun main(args : Array<String>) {

}