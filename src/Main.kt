/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.view.mxGraph
import javafx.application.Application
import javafx.embed.swing.SwingNode
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import GUI.Principal
import java.awt.Dimension
import java.awt.Toolkit

fun main(args: Array<String>) {
    println("hola desde Kotlin")

    val dialog = Principal()
    dialog.pack()

    dialog.setSize(720, 300);

    dialog.title = "Proyecto 21141016"

    val  toolkit = Toolkit.getDefaultToolkit();
    val  screenSize = toolkit.getScreenSize();
    val x = (screenSize.width - dialog.getWidth()) / 2;
    val  y = (screenSize.height - dialog.getHeight()) / 2;
    dialog.setLocation(x, y);

    dialog.isVisible = true
}

