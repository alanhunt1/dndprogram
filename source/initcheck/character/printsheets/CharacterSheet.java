package initcheck.character.printsheets;

import initcheck.DCharacter;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class CharacterSheet extends PrintableSheet {

	
	private static final long serialVersionUID = 1L;

	protected DCharacter dc;

	protected boolean drawgraph = false;

	protected static final Font sansPlain5 = new Font("SansSerif", Font.PLAIN,
			5);

	protected static final Font sansPlain6 = new Font("SansSerif", Font.PLAIN,
			6);

	protected static final Font courierPlain6 = new Font("Courier", Font.PLAIN,
			6);

	protected static final Font sansPlain8 = new Font("SansSerif", Font.PLAIN,
			8);

	protected static final Font sansPlain10 = new Font("SansSerif", Font.PLAIN,
			10);

	protected static final Font sansPlain12 = new Font("SansSerif", Font.PLAIN,
			12);

	protected static final Font sansPlain14 = new Font("SansSerif", Font.PLAIN,
			14);

	protected static final Font sansBold8 = new Font("SansSerif", Font.BOLD, 8);

	protected static final Font sansBold10 = new Font("SansSerif", Font.BOLD,
			10);

	protected static final Font sansBold12 = new Font("SansSerif", Font.BOLD,
			12);

	protected static final Font sansItalic8 = new Font("SansSerif",
			Font.ITALIC, 8);

	protected static final Font nameFont = new Font("SansSerif", Font.BOLD, 20);

	protected static final Font playerNameFont = new Font("SansSerif",
			Font.PLAIN, 15);

	protected static final Font partyNameFont = new Font("SansSerif",
			Font.PLAIN, 11);

	protected ImageIcon icon;

	protected Image img;

	// we need to override the default method to specify the preferred size,
	// or the scrollbars will not work correctly.
	public Dimension getPreferredSize() {
		int width = (new Double(scale * (minx + maxx + 20))).intValue();
		int height = (new Double(scale * (miny + maxy + 20))).intValue();
		return new Dimension(width, height);
	}

	public final void paintComponent(Graphics ig) {
		if (img == null) {
			int width = (new Double(scale * (minx + maxx + 20))).intValue();
			int height = (new Double(scale * (miny + maxy + 20))).intValue();
			img = createImage(width, height);
		}
		if (img != null && icon == null) {
			icon = new ImageIcon(img);
			paintImage();
		}
		if (icon != null) {
			icon.paintIcon(this, ig, 0, 0);
		}
	}

	public void setScale(double scale) {
		this.scale = scale;
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		img = null;
		icon = null;
		repaint();
		paintImage();
		repaint();
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void paintImage() {

	}

	public void updateImage() {
		paintImage();
	}

	public void update(Graphics ig) {
		repaint();
	}

	public void repaint() {
		// since paint method draws everything,
		// there is no need to clear drawing area first
		paintComponent(this.getGraphics());
	}

}
