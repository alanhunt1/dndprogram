package initcheck.dungeon;

import initcheck.MapEditor;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

public class ThumbnailPanel extends JComponent implements MouseInputListener {

	private static final long serialVersionUID = 1L;

	ImageIcon icon;

	Image img;

	boolean paintMe = false;
	
	DungeonGUI owner;
	
	double ratio = 1.0;
	
	String filename;
	
	MapEditor editor;
	
	public ThumbnailPanel(DungeonGUI owner, String filename){
		this.owner = owner;
		this.filename = filename;
		setDoubleBuffered(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	public void setEditor(MapEditor e){
		editor = e;
	}
	
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public void updateImage() {

		repaint();
	}

	public void repaint() {

		// since paint method draws everything,
		// there is no need to clear drawing area first
		paintComponent(this.getGraphics());
	}

	//	 we need to override the default method to specify the preferred size,
	// or the scrollbars will not work correctly.
	public Dimension getPreferredSize() {
		
			return new Dimension(200, 200);
		
	}
	
	public final void paintComponent(Graphics ig) {
		
		if (icon != null) {
			int height = icon.getIconHeight();
			int width = icon.getIconWidth();

			// and scale the image to fit inside a reasonable bound
			int max = height;

			if (width > max) {
				max = width;
			}

			ratio = new Double(max).doubleValue() / 200.0;

			Image img = icon.getImage();

			Image imgSmall = img.getScaledInstance(new Double(width / ratio)
					.intValue(), new Double(height / ratio).intValue(),
					Image.SCALE_FAST);
			
			ImageIcon ii = new ImageIcon(imgSmall);
			
			Graphics2D g = (Graphics2D) ig;
			g.drawImage(ii.getImage(), getGraphicsConfiguration().getDefaultTransform(), this);
			
		}

	}

	public void mouseEntered(MouseEvent e) {

		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

	}

	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		((MapEditorDungeonGUI)owner).setTab(filename);
		if (editor != null){
			editor.showMapTab();
		}else{
			
		}
		//int mouseX = e.getX()*new Double(ratio).intValue();
		//int mouseY = e.getY()*new Double(ratio).intValue();
		//owner.setView(mouseX, mouseY);
	}

	public void mouseDragged(MouseEvent e) {

	}

}
