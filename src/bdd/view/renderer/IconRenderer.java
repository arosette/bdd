package bdd.view.renderer;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconRenderer {

    public static ImageIcon makeIcon(String iconPath) {

	Image image = null;

	try {
	    InputStream is = IconRenderer.class.getResourceAsStream(iconPath);
	    image = ImageIO.read(is);
	}

	catch (IOException e) {
	    e.printStackTrace();
	}

	return new ImageIcon(image);
    }
}
