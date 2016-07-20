package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Affisa-Jimmy on 20/7/2016.
 */
public class ConfigurationForWindows {

    public static void SetConfigurations(JDialog dialog){
        dialog.pack();
        dialog.setSize(800, 500);
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - dialog.getWidth()) / 2;
        final int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        dialog.setVisible(true);
    }
}
