/**
 * Created by Jimmy Banegas on 19-Jul-16.
 */
import Automatas.AutomataDFA
import Ventanas.ConfigurationForWindows
import Ventanas.SetConfigurations

fun main(args: Array<String>) {
    println("hola desde Kotlin")

    var automataDFA = AutomataDFA()

    val dialog = Ventanas.DFA(automataDFA)

    ConfigurationForWindows.SetConfigurations(dialog)
    //dialog.pack()

 //   dialog.setSize(720, 600);

  //  dialog.title = "Proyecto 21141016"

   /* val  toolkit = Toolkit.getDefaultToolkit();
    val  screenSize = toolkit.getScreenSize();
    val x = (screenSize.width - dialog.getWidth()) / 2;
    val  y = (screenSize.height - dialog.getHeight()) / 2;
    dialog.setLocation(x, y);

    dialog.isVisible = true*/
}

