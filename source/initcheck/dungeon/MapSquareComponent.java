package initcheck.dungeon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class MapSquareComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	ImageIcon icon;
	Image img;
	public boolean update = false;
	Dungeon m = null;
	MapSquare currSquare;
	public MapSquareComponent(){
		
	}
	
	public MapSquareComponent(Dungeon m){
		this.m = m;
	}

	public void update(Graphics ig) {

		Graphics2D g = (Graphics2D) ig;
		if (g != null) {
			// fill it in with the background color
			g.fill(new Rectangle(0, 0, getWidth(), getHeight()));

			update = true;
			// build the map
			updateImage();
			update = false;
		}
	}

	public void updateImage() {
		paintImage();
		repaint();
	}
	
	public void repaint() {
		paintComponent(this.getGraphics());
	}
	
	public final void paintComponent(Graphics ig) {
		
		
	
		// if we don't have an image buffered, create it.
		if (img == null) {
			
			img = createImage(40,40);
			
		}

		// make sure it is in a paintable form.
		if (img != null && icon == null) {
			icon = new ImageIcon(img);
			
			paintImage();
		}

		// paint our buffered image on the screen
		if (icon != null) {
			
			
			icon.paintIcon(this, ig, 0, 0);
			
			
		}
	}
	
	public final void paintImage() {
		paintImage(null);
	}

	public final void paintImage(Graphics ig) {

		if (ig == null) {
			if (icon == null || icon.getImage() == null
					|| icon.getImage().getGraphics() == null) {
				
				return;
			}
			ig = icon.getImage().getGraphics();
		}
	

		// cast to a graphics2d object for performance improvements
		Graphics2D g = (Graphics2D) ig;
	

		// make sure that we actually have a map to paint on!
		if (m == null) {
			
			return;
		}
		
	
		MapSquareRenderer renderer = new MapSquareRenderer(m);
		
	

		

		renderer.render(g, currSquare, 0, 0, currSquare.zpos, 40, DrawingBoard.EDITOR,
				 		-1, -1, -1,
						(Square)null, false);
			
		
	}
	
	public Dimension getPreferredSize() {
		if (m == null) {
			return super.getPreferredSize();
		}
		return new Dimension(40,40);
	}

	
	public void setSquare(MapSquare m){
		
		currSquare = m;
	}
	
}
