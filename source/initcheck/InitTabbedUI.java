package initcheck;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class InitTabbedUI extends BasicTabbedPaneUI {

	private ImageIcon bgImage = new ImageIcon("images/wood.jpg");

	protected void normalizeTabRuns(int tabPlacement, int tabCount, int start,
			int max) {

	}

	protected boolean shouldRotateTabRuns(int tabPlacement) {
		return false;
	}

	protected boolean shouldPadTabRun(int tabPlacement, int run) {
		return false;
	}

	

	
	protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
			int tabIndex, Rectangle iconRect, Rectangle textRect) {

		
		Rectangle selRect = getTabBounds(tabIndex, calcRect);

		g.drawImage(bgImage.getImage(), selRect.x, selRect.y, selRect.width,
				selRect.height, null);
		super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);

	}

}
