package archnemesis;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

class ScreenRecorder {
	/**
	 * Takes a Screenshot of your left screen half, since the inventory is on the left side. I assume he'll always take the primary monitor.
	 */
	public static void go() {
		try {
			Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			screenSize.width = screenSize.width/2;
			BufferedImage image = new Robot().createScreenCapture(screenSize);
			File file = new File("Screenshot.png");
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
