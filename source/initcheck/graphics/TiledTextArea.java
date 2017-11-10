package initcheck.graphics;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class TiledTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;

	private ImageIcon bgImage = new ImageIcon("images/papermiddle.jpg");

	private ImageIcon ulImage = new ImageIcon("images/paperul.jpg");

	private ImageIcon leftImage = new ImageIcon("images/paperleft.jpg");

	private ImageIcon llImage = new ImageIcon("images/paperll.jpg");

	private ImageIcon topImage = new ImageIcon("images/papertop.jpg");

	private ImageIcon bottomImage = new ImageIcon("images/paperbottom.jpg");

	private ImageIcon urImage = new ImageIcon("images/paperur.jpg");

	private ImageIcon lrImage = new ImageIcon("images/paperlr.jpg");

	private ImageIcon rightImage = new ImageIcon("images/paperright.jpg");

	private boolean paintBackground = true;

	public boolean isPaintBackground() {
		return paintBackground;
	}

	public void setPaintBackground(boolean paintBackground) {
		this.paintBackground = paintBackground;
	}

	public TiledTextArea(String s, int i, int j) {
		super(s, i, j);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	
	public TiledTextArea(int i, int j) {
		super(i, j);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	public TiledTextArea() {
		super();
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	public void paintComponent(Graphics g) {
		if (isPaintBackground()) {
			if (bgImage != null) {
				Dimension dim = getSize();
				int imgW = bgImage.getIconWidth();
				int imgH = bgImage.getIconHeight();
				int panW = (int) dim.getWidth();
				int panH = (int) dim.getHeight();
				int xTiles = panW / imgW;
				int yTiles = panH / imgH;
				int leftH = leftImage.getIconHeight();
				int leftTiles = panH / leftH;
				int rightH = rightImage.getIconHeight();
				int rightTiles = panH / rightH;
				int topW = topImage.getIconWidth();
				int topTiles = panW / topW;
				int bottomW = bottomImage.getIconWidth();
				int bottomTiles = panW / bottomW;

				for (int i = 0; i < xTiles + 1; i++) {
					for (int j = 0; j < yTiles + 1; j++) {
						// Draw image
						g.drawImage(bgImage.getImage(), i * imgW, j * imgH,
								this);
					}
				}

				for (int i = 0; i < leftTiles + 1; i++) {
					g.drawImage(leftImage.getImage(), 0, i * leftH, this);
				}

				for (int i = 0; i < rightTiles + 1; i++) {
					g.drawImage(rightImage.getImage(), panW
							- rightImage.getIconWidth(), i * rightH, this);
				}

				for (int i = 0; i < topTiles + 1; i++) {
					g.drawImage(topImage.getImage(), i * topW, 0, this);
				}

				for (int i = 0; i < bottomTiles + 1; i++) {
					g.drawImage(bottomImage.getImage(), i * bottomW, panH
							- bottomImage.getIconHeight(), this);
				}

				g.drawImage(ulImage.getImage(), 0, 0, this);
				g.drawImage(llImage.getImage(), 0, panH
						- llImage.getIconHeight(), this);
				g.drawImage(urImage.getImage(), panW - urImage.getIconWidth(),
						0, this);
				g.drawImage(lrImage.getImage(), panW - lrImage.getIconWidth(),
						panH - lrImage.getIconHeight(), this);

			}

			setOpaque(false);
		}
		super.paintComponent(g);
	}

}
