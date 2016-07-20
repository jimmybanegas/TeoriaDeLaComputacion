package GUI;

import Automatas.Estado;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Affisa-Jimmy on 20/7/2016.
 */
public class ConfigurationForWindows {

    //Clase creada para tener en un solo lugar la configuración de tamaños de ventanas, posición y demás configuraciones

    public static void SetConfigurations(JDialog dialog) {
        dialog.pack();
        dialog.setSize(1000, 500);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        dialog.setVisible(true);
    }

    public static void SetLookAndFeel() {

    }

}
