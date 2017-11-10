package initcheck.graphics;

import initcheck.InitImage;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TiledPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected ImageIcon bgImage = InitImage.steel;

	private ImageIcon cornerImage = new ImageIcon("images/topleft-corner.gif");

	private boolean paintCorner = false;

	private boolean paintBackground = true;

	public boolean isPaintCorner() {
		return paintCorner;
	}

	public void setPaintCorner(boolean paintCorner) {
		this.paintCorner = paintCorner;
	}

	public TiledPanel() {
		super();
	}

	public TiledPanel(String s) {
		super();
		bgImage = new ImageIcon(s);
	}

	public TiledPanel(ImageIcon i) {
		super();
		bgImage = i;
	}

	public void forceRepaint() {
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (isPaintBackground()) {
			if (bgImage != null) {
				Dimension dim = getSize();
				int imgW = bgImage.getIconWidth();
				int imgH = bgImage.getIconHeight();
				int panW = (int) dim.getWidth();
				int panH = (int) dim.getHeight();
				int xTiles = panW / imgW;
				int yTiles = panH / imgH;

				for (int i = 0; i < xTiles + 1; i++) {
					for (int j = 0; j < yTiles + 1; j++) {
						// Draw image
						g.drawImage(bgImage.getImage(), i * imgW, j * imgH,
								this);
					}
				}
				if (paintCorner) {
					g.drawImage(cornerImage.getImage(), 0, 0, this);
				}
			}
		}
	}

	public boolean isPaintBackground() {
		return paintBackground;
	}

	public void setPaintBackground(boolean paintBackground) {
		this.paintBackground = paintBackground;
	}

	public ImageIcon getBgImage() {
		return bgImage;
	}

	public void setBgImage(ImageIcon bgImage) {
		this.bgImage = bgImage;
	}

	public ImageIcon getCornerImage() {
		return cornerImage;
	}

	public void setCornerImage(ImageIcon cornerImage) {
		this.cornerImage = cornerImage;
	}
}
