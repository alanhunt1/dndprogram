package initcheck.graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class TiledFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ImageIcon bgImage = null;

	public TiledFrame(ImageIcon i) {
		super();
		bgImage = i;
	}

	public void paint(Graphics g) {
		super.paint(g); // paint background

		if (bgImage != null) {
			// Draw image
			g.drawImage(bgImage.getImage(), 0, 0, this); // 85x62 image
		}
		// g.drawImage(image, 90, 0, 300, 62, this);
	}

}
