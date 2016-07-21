package GUI;

import Automatas.AutomataDFA;
import Automatas.Estado;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
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
          /*  if(!automataDFA.CheckTransition(v1, nombre)){
                showMessage("No se puede agregar una Transicion con el mismo valor!");
                graph.getModel().remove(evt.getProperty("cell"));
                return;
            }
            String name = String.valueOf(nombre);
            edge.setValue(name);
            DFA.automataDFA.agregarTransicion(name,v1,v2,edge);*/
        });
      /*  graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger())
                {
                   // jPopupMenuForm.show(getContentPane(),e.getX(),e.getY());
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

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int inicial = JOptionPane.showConfirmDialog(this, "¿Es estado Inicial?", "Inicial", dialogButton);
            if(inicial == 0) {

               if(automataDFA.ExisteEstadoInicial()){
                   int dialogButton2 = JOptionPane.YES_NO_OPTION;
                   int inicial2 = JOptionPane.showConfirmDialog(this, "¿Ya hay inicial, desea cambiarlo?", "Inicial", dialogButton2);

                   if(inicial2==0)
                       esInicial = true;
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

            Object v1 = new Object();

            Estado estado = new Estado("q"+name,v1);

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

            automataDFA.agregarEstado("q"+name,v1);

            System.out.println("Imp estado "+ estado.getNombre());

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

    private void showMessage(String s) {

    }

    private char escogerTransicion() {
            return 0;
    }

    private Estado obtenerEstado(mxCell origen) {
        return null;
    }

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

    public static void main(String[] args) {
        DFA dialog = new DFA(automataDFA);
        ConfigurationForWindows.SetConfigurations(dialog);
       // ConfigurationForWindows.setGraph(graph, graphComponent);
        System.exit(0);
    }
}
