package initcheck.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JList;

import initcheck.InitImage;

public class TiledList<T> extends JList<TiledListItem> {

	private static final long serialVersionUID = 1L;

	private ImageIcon bgImage = InitImage.paperMiddle;

	private ImageIcon ulImage = InitImage.paperUl;

	private ImageIcon leftImage = InitImage.paperLeft;

	private ImageIcon llImage = InitImage.paperLl;

	private ImageIcon topImage = InitImage.paperTop;

	private ImageIcon bottomImage = InitImage.paperBottom;

	private ImageIcon urImage = InitImage.paperUr;

	private ImageIcon lrImage = InitImage.paperLr;

	private ImageIcon rightImage = InitImage.paperRight;

	private boolean paintBackground = true;

	public boolean isPaintBackground() {
		return paintBackground;
	}

	public void setPaintBackground(boolean paintBackground) {
		this.paintBackground = paintBackground;
	}

	public TiledList() {
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}

	public TiledList(final Vector<TiledListItem> listData) {
		super(listData);
		setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	}
	
	public void setStrings(Vector<String> v){
		Vector<TiledListString> v2 = new Vector<TiledListString>();
		for (String s:v){
			v2.add(new TiledListString(s));
		}
		setListData(v2);
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

	public ImageIcon getBgImage() {
		return bgImage;
	}

	public void setBgImage(ImageIcon bgImage) {
		this.bgImage = bgImage;
	}

	public ImageIcon getBottomImage() {
		return bottomImage;
	}

	public void setBottomImage(ImageIcon bottomImage) {
		this.bottomImage = bottomImage;
	}

	public ImageIcon getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(ImageIcon leftImage) {
		this.leftImage = leftImage;
	}

	public ImageIcon getLlImage() {
		return llImage;
	}

	public void setLlImage(ImageIcon llImage) {
		this.llImage = llImage;
	}

	public ImageIcon getLrImage() {
		return lrImage;
	}

	public void setLrImage(ImageIcon lrImage) {
		this.lrImage = lrImage;
	}

	public ImageIcon getRightImage() {
		return rightImage;
	}

	public void setRightImage(ImageIcon rightImage) {
		this.rightImage = rightImage;
	}

	public ImageIcon getTopImage() {
		return topImage;
	}

	public void setTopImage(ImageIcon topImage) {
		this.topImage = topImage;
	}

	public ImageIcon getUlImage() {
		return ulImage;
	}

	public void setUlImage(ImageIcon ulImage) {
		this.ulImage = ulImage;
	}

	public ImageIcon getUrImage() {
		return urImage;
	}

	public void setUrImage(ImageIcon urImage) {
		this.urImage = urImage;
	}

}
