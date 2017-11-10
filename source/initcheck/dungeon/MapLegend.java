package initcheck.dungeon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class MapLegend extends JComponent {

	private static final long serialVersionUID = 1L;

	int sz = 30;

	int cw = 1;

	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}

	public void update(Graphics g) {
		repaint();
	}

	public void repaint() {
		paintComponent(this.getGraphics());
	}

	public void paintComponent(Graphics g) {

		int cw = 2;
		int xpos = 5;
		int ypos = 5;

		g.setColor(Color.black);
		g.fillRect(0, 0, 50, 50);

		Rectangle center = new Rectangle();

		center.setBounds(xpos, ypos, sz * 3 / 5 + 1, sz * 3 / 5 + 1);
		g.setColor(Color.black);

		Color bg = Color.white;
		bg = bg.darker().darker();
		Color fg = bg.darker();

		g.setColor(bg);
		g.fillRect(xpos, ypos, sz, sz);
		g.setColor(fg);
		g.drawRect(xpos, ypos, sz, sz);

		// draw corner pieces
		g.fillRect(xpos, ypos, cw * sz / 10 + 1, cw * sz / 10 + 1);
		g.fillRect(xpos + sz * (10 - cw) / 10, ypos, cw * sz / 10 + 1, cw * sz
				/ 10 + 1);
		g.fillRect(xpos, ypos + sz * (10 - cw) / 10, cw * sz / 10 + 1, cw * sz
				/ 10 + 1);
		g.fillRect(xpos + sz * (10 - cw) / 10, ypos + sz * (10 - cw) / 10, cw
				* sz / 10 + 1, cw * sz / 10 + 1);
	}

}
