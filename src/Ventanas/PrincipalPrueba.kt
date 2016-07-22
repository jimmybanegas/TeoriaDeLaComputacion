package Ventanas

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
open class PrincipalPrueba(s: String) : JFrame() {
    // Variables declaration - do not modify
    private var jButton1: javax.swing.JButton? = null
    private var jButton2: javax.swing.JButton? = null
    private var jLabel1: javax.swing.JLabel? = null
    private var jTextField1: javax.swing.JTextField? = null
    private var jTextField2: javax.swing.JTextField? = null
    // End of variables declaration


    init {
        initComponents()
    }

    public fun initComponents() {
       // throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        jButton1 = javax.swing.JButton()
        jButton2 = javax.swing.JButton()
        jLabel1 = javax.swing.JLabel()
        jTextField1 = javax.swing.JTextField()
        jTextField2 = javax.swing.JTextField()

        defaultCloseOperation = javax.swing.WindowConstants.EXIT_ON_CLOSE

       (jButton1 as JButton).text = "jButton1"

        (jButton2 as JButton).text = "jButton2"

        (jLabel1 as JLabel).text = "jLabel1"

        (jTextField1 as JTextField).text = "jTextField1"

        (jTextField2 as JTextField).text = "jTextField2"

        val layout = javax.swing.GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(38, 38, 38).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel1).addComponent(jButton2).addComponent(jButton1)).addContainerGap(289, java.lang.Short.MAX_VALUE.toInt())))
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(37, 37, 37).addComponent(jButton1).addGap(40, 40, 40).addComponent(jButton2).addGap(18, 18, 18).addComponent(jLabel1).addGap(18, 18, 18).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(69, java.lang.Short.MAX_VALUE.toInt())))

        pack()
    }

}

fun main(args : Array<String>) {
    var principal = PrincipalPrueba("Hola")
    principal.initComponents()
    principal.setVisible(true)
}