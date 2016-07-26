package Ventanas

import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JMenuBar

/**
 * Created by Affisa-Jimmy on 22/7/2016.
 */
class Principal {
    // Variables declaration - do not modify

    // Variables declaration - do not modify
    private var jButtonCerrar : JButton? = null ;
    private var jButtonOK : JButton? = null ;
    private var jComboBoxAutomata = null ;
    private var jLabel1 : javax.swing.JLabel? =null ;
    private var jLabel2: JLabel? = null ;
    private var jLabel3: JLabel? = null ;
    private var jLabel4: JLabel? = null ;
    private var jMenuBar1: JMenuBar? = null ;

    init {
        initComponents()
    }

    public fun initComponents() {
        jLabel1 = javax.swing.JLabel()
        jLabel2 = javax.swing.JLabel()
        jLabel3 = javax.swing.JLabel()
        jLabel4 = javax.swing.JLabel()
        jButtonCerrar = javax.swing.JButton()
        jButtonOK = javax.swing.JButton()
     //   jComboBoxAutomata = JComboBox<>()


    }
}

fun main(args : Array<String>) {
    var principal = Principal()

    principal.initComponents()

 /*   principal.initComponents()
    principal.setVisible(true)*/
}