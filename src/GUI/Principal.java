package GUI;

import javax.swing.*;
import java.awt.event.*;

public class Principal extends JDialog {
    private JPanel contentPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cmbSelectedAutomata;
    private JLabel lblChooseAuotmata;

    public Principal() {
        setContentPane(contentPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here

        switch (cmbSelectedAutomata.getSelectedIndex()){
            case 0:
                DFA dfaWindow = new DFA();

                dfaWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                       // super.windowClosing(e);
                        new Principal().setVisible(true);
                    }
                });

                //Mostrar ventana de DFA
                dfaWindow.pack();
                dfaWindow.setLocation(500,300);
                dfaWindow.setVisible(true);

                //Ocultar el parent o Principal.form
                this.setVisible(false);
                this.dispose();


            case 1:
                Turing turingWindow = new Turing();

                turingWindow.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosing(WindowEvent e) {
                        //super.windowClosing(e);
                        new Principal().setVisible(true);
                    }
                });

                //Mostrar ventana de Turing
                turingWindow.pack();
                turingWindow.setLocation(500,300);
                turingWindow.setVisible(true);

                //Ocultar el parent o Principal.form
                this.setVisible(false);
                this.dispose();


        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Principal dialog = new Principal();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
