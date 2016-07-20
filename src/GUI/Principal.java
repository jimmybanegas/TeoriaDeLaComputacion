package GUI;

import javax.swing.*;
import java.awt.*;
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

                //Mostrar ventana de DFA
                ConfigurationForWindows.SetConfigurations(dfaWindow);

             /*   dfaWindow.pack();
                dfaWindow.setSize(200, 200);
                final Toolkit toolkit = Toolkit.getDefaultToolkit();
                final Dimension screenSize = toolkit.getScreenSize();
                final int x = (screenSize.width - dfaWindow.getWidth()) / 2;
                final int y = (screenSize.height - dfaWindow.getHeight()) / 2;
                dfaWindow.setLocation(x, y);

                dfaWindow.setVisible(true);*/
                break;
            case 1:
                Turing turingWindow = new Turing();

                ConfigurationForWindows.SetConfigurations(turingWindow);

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
