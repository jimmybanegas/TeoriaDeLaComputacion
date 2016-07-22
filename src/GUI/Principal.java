package GUI;

import Automatas.AutomataDFA;

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
                AutomataDFA automataDFA = new AutomataDFA();

                DFA dfaWindow = new DFA(automataDFA);

                //Mostrar ventana de DFA
                //ConfigurationForWindows.SetConfigurations(dfaWindow);
                break;
            case 1:
               /* Turing turingWindow = new Turing();

                //Mostrar ventana de Turing
                ConfigurationForWindows.SetConfigurations(turingWindow);*/
                break;
            default:
                dispose();
        }
       // dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        setVisible(false);
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
