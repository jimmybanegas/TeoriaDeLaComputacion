package GUI;

import Automatas.AutomataDFA;
import Automatas.Estado;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class DFA extends JDialog {
   // private static final AutomataDFA automataDFA = new AutomataDFA();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textArea1;
    private JTextField textArea2;
    private JPanel panelGraph;
    private JButton crearEstadoButton;
    private JButton crearTransicionButton;
    private JButton eliminarEstadoButton;
    private JButton eliminarTransiciónButton;
    private static mxGraphComponent graphComponent;
    protected static mxGraph graph = new mxGraph();
    protected int valor = 0;
   protected static AutomataDFA automataDFA;

    public DFA(Object automataDFA) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.automataDFA = (AutomataDFA) automataDFA;

       /* if(((AutomataDFA) automataDFA).getEstadoInicial().getNombre()!=""){
            graph.getModel().setStyle(((AutomataDFA) automataDFA).getEstadoInicial().getVertice(), "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                    +"fillColor=lightgreen");
        }
        */

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        crearEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCrearEstado();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //Configurar el grafico
        graph = new mxGraph();
        graph.setAllowLoops(true);
        graph.setDisconnectOnMove(false);
        graph.setConnectableEdges(false);
        graph.setEdgeLabelsMovable(false);
        graphComponent = new mxGraphComponent(graph);
        graphComponent.getViewport().setBackground(Color.WHITE);
        graphComponent.getConnectionHandler().addListener(mxEvent.CONNECT, (Object sender, mxEventObject evt) -> {
            mxCell edge =(mxCell)evt.getProperty("cell");
            mxCell origen = (mxCell) edge.getSource();
            mxCell destino = (mxCell) edge.getTarget();
            Estado v1 = obtenerEstado(origen);
            Estado v2 = obtenerEstado(destino);
            if(v2 == null){
                graph.getModel().remove(evt.getProperty("cell"));
                return;
            }
            char nombre = escogerTransicion();
            if(nombre==0){
                graph.getModel().remove(evt.getProperty("cell"));
                return;
            }
            /*if(!automataDFA.CheckTransition(v1, nombre)){
                showMessage("No se puede agregar una Transicion con el mismo valor!");
                graph.getModel().remove(evt.getProperty("cell"));
                return;
            }*/
            String name = String.valueOf(nombre);
            edge.setValue(name);
            DFA.automataDFA.agregarTransicion(name,v1,v2,edge);
        });
     /*   graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger())
                {
                    jPopupMenuForm.show(getContentPane(),e.getX(),e.getY());
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1){
                    long x = e.getX();
                    long y = e.getY();
                    graph.getModel().beginUpdate();
                    try {
                        Object parent = graph.getDefaultParent();
                        String name = String.valueOf(valor++);
                        Object v1 = graph.insertVertex(parent, null, "q"+name,x, y, 50, 50,
                                "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                                        +"fillColor=lightblue");

                        automataDFA.agregarEstado("q"+name,v1);
                    } finally {
                        graph.getModel().endUpdate();
                    }
                }
            }
        });*/

        panelGraph.setLayout(new BorderLayout());
        panelGraph.add(graphComponent,BorderLayout.CENTER);
    }

    private void onCrearEstado() {
        Random rand = new Random();
        long x  = rand.nextInt(600) + 1;
        long y  = rand.nextInt(300) + 1;

        graph.getModel().beginUpdate();
        try {
            Object parent = graph.getDefaultParent();
            String name = String.valueOf(valor++);
            boolean esInicial;
            boolean esDeAceptacion;
            boolean cambiarInicial = false;
            Estado v2 = null;

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int inicial = JOptionPane.showConfirmDialog(this, "¿Es estado Inicial?", "Inicial", dialogButton);
            if(inicial == 0) {

               if(automataDFA.getEstadoInicial().getNombre()!=""){
                   int dialogButton2 = JOptionPane.YES_NO_OPTION;
                   int inicial2 = JOptionPane.showConfirmDialog(this, "Ya hay inicial, ¿desea cambiarlo?", "Inicial", dialogButton2);

                   if(inicial2==0){
                       esInicial = true;
                       cambiarInicial=true;
                       v2 = automataDFA.getEstadoInicial();

                       //System.out.println("Regreso  "+ );

                      /* graph.getModel().setStyle(v2, "resizable=0;editable=0;whiteSpace=wrap;"
                               +"fillColor=lightyellow");
                       graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{v2}); //changes the color to red
                       graphComponent.refresh();*/
                   }

                   else
                       esInicial = false;

               }else
               {
                   esInicial = true;
               }


            } else {
                esInicial =false;
            }

            int dialogButton2 = JOptionPane.YES_NO_OPTION;
            int aceptacion = JOptionPane.showConfirmDialog(this, "¿Es estado de aceptación?", "Aceptación", dialogButton2);
            if(aceptacion == 0) {
                esDeAceptacion = true;
            } else {
                esDeAceptacion = false;
            }

            Object v1 = new mxCell();

            Estado estado = new Estado("q"+name,(mxCell) v1);

            if(esInicial&& esDeAceptacion){
                 v1 = graph.insertVertex(parent, null, "q"+name,x, y, 50, 50,
                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightgreen;strokeColor=black");

                automataDFA.setEstadoInicial(estado);
                automataDFA.agregarEstadoAceptacion(estado);
            }
            else if(!esInicial && !esDeAceptacion){
                 v1 = graph.insertVertex(parent, null, "q"+name,x, y, 50, 50,
                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightyellow;strokeColor=black");
            }
            else if(esInicial && !esDeAceptacion){
                 v1 = graph.insertVertex(parent, null, "q"+name,x, y, 50, 50,
                        "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightgreen;strokeColor=black");

                automataDFA.setEstadoInicial(estado);
            }
            else if (!esInicial && esDeAceptacion){
                 v1 = graph.insertVertex(parent, null, "q"+name,x, y, 50, 50,
                        "resizable=0;editable=0;shape=doubleEllipse;whiteSpace=wrap;fillColor=lightyellow;strokeColor=black");
                automataDFA.agregarEstadoAceptacion(estado);
            }

            automataDFA.agregarEstado("q"+name,(mxCell) v1);

            if(cambiarInicial){
               /* graph.getModel().setStyle(v2,  "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;fillColor=lightyellow;strokeColor=black");
                System.out.println("v2 "+ v2);
                System.out.println("v1 "+ v1);
                /*graph.getModel().setStyle(v1, "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                        +"fillColor=lightgreen");*/
             //   automataDFA.setEstadoInicial(estado);*/
                CambiarEstadoInicial(estado,v2);
            }


         /*   System.out.println("Imp estado "+ estado.getNombre());
            System.out.println("Imp estado "+ estado.getVertice());*/

        } finally {
            graph.getModel().endUpdate();

            System.out.println("Tamaño Estado " +automataDFA.getEstados().size());

            for (  Estado item : automataDFA.getEstadosItems()) {
                System.out.println("Estado " +item.getNombre());
            }

            for (  Estado item : automataDFA.getAceptacionItems()) {
                System.out.println("Aceptacion " +item.getNombre());
            }

            System.out.println("Inicial " +automataDFA.getEstadoInicial().getNombre());

        }
    }

    private void CambiarEstadoInicial(Estado estadoInicial, Estado estado) {
        graph.getModel().setStyle(estadoInicial.getVertice(), "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                +"fillColor=red");
        graph.getModel().setStyle(estado.getVertice(), "resizable=0;editable=0;shape=ellipse;whiteSpace=wrap;"
                +"fillColor=red");
       // automataDFA.setEstadoInicial(estado);
    }

    private void showMessage(String s) {

    }

    private char escogerTransicion(){
        char nombre = 0;
        while(true){
            String name = JOptionPane.showInputDialog("Digite nombre de Transicion:");
            if(name==null||name.isEmpty()){
                showMessage("No se guardo nada!!");
                break;
            }
            char[] na=name.toCharArray();
            if(na.length>1){
                showMessage("Solo puede ingresar una letra o caracter!");
            }else if(na.length==1){
                nombre=na[0];
                break;
            }
        }
        return nombre;
    }
  /*  private Estado obtenerEstado(mxCell origen) {
        return null;
    }*/

    private void onOK() {
        // add your code here
       // dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        this.setVisible(false);
        this.dispose();
    }

    public static mxGraph getGraph() {
        return graph;
    };

    private void agregarTrasicion(java.awt.event.ActionEvent evt) {
       // if(prologoEstado())
        //    return;
        String name =JOptionPane.showInputDialog("Digite nombre de Estado partida:");

        if(name==null || name.isEmpty()){
            showMessage("No se agrego nada!!");
            return;
        }
        Estado v1 = obtenerEstado(name);
        if(v1==null){
            showMessage("No existe etado con ese nombre!");
            return;
        }
        name =JOptionPane.showInputDialog("Digite nombre de Estado destino:");
        if(name==null || name.isEmpty()){
            showMessage("No se agrego nada!!");
            return;
        }
        Estado v2 = obtenerEstado(name);
        if(v2==null){
            showMessage("No existe etado con ese nombre!");
            return;
        }
        char nombre = escogerTransicion();
        if(nombre==0){
            return;
        }
        if(!automataDFA.CheckTransition(v1, nombre)){
            showMessage("No se puede agregar una Transicion con el mismo valor!");
            return;
        }
       // NewTransition add= new NewTransition(v1,v2,nombre);
    }

    private Estado obtenerEstado(mxCell vertex) {
        for(Estado estado:automataDFA.getEstados())
        {
            if(estado.getVertice().equals(vertex))
            {
                return estado;

            }
        }
        return null;
    }

    private Estado obtenerEstado(String nombre){
        for(Estado estado:automataDFA.getEstados())
        {
            if(estado.getNombre().equals(nombre))
            {
                return estado;

            }
        }
        return null;
    }

    public static void main(String[] args) {
        DFA dialog = new DFA(automataDFA);
        ConfigurationForWindows.SetConfigurations(dialog);
       // ConfigurationForWindows.setGraph(graph, graphComponent);
        System.exit(0);
    }
}
