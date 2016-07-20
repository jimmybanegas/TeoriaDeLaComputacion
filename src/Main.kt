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

fun main(args: Array<String>) {
    println("hola desde Kotlin")

    val dialog = Principal()
    dialog.pack()
    dialog.alignmentX
    dialog.alignmentY
    dialog.isVisible = true

}

