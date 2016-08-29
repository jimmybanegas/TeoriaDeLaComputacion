package Ventanas

import java.awt.Container
import java.awt.Toolkit
import javax.naming.Context
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
open class ConfigurationForWindows {
    //Clase creada para tener en un solo lugar la configuración de tamaños de ventanas, posición y demás configuraciones
    companion object {

        fun SetConfigurations(dialog: JFrame, title: String){
            dialog.pack()
            dialog.setSize(1000, 500)
            val toolkit = Toolkit.getDefaultToolkit()
            val screenSize = toolkit.screenSize
            val x = (screenSize.width - dialog.width) / 2
            val y = (screenSize.height - dialog.height) / 2
            dialog.setLocation(x, y)

            dialog.title = title

            dialog.isVisible = true
        }

        fun  messageDialog(contentPane: Container?, s: String) {
            JOptionPane.showMessageDialog(contentPane, s, "Informacion", JOptionPane.ERROR_MESSAGE);
        }


        fun SetLookAndFeel() {

        }
    }
}
