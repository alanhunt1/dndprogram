package initcheck.dungeon;

import initcheck.graphics.TiledPanel;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public class TerrainPreviewPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	private double size = 5;
	
	public TerrainPreviewPanel(double size){
		this.size = size;
	}
	
	public void setImage(ImageIcon display){
		init(display, size);
	}
	
	public TerrainPreviewPanel(ImageIcon display, double size){
		this.size = size;
		init(display, size);
	}
	
	public void init(ImageIcon image, double size){
		
		ImageIcon display = new ImageIcon(image.getImage());
		
		setPreferredSize(new Dimension(200, 200));
		
		// read in the picture from the file
		int height = display.getIconHeight();
		int width = display.getIconWidth();

		// pull out the image
		Image img = display.getImage();

		// and scale the image to fit inside a reasonable bound
		int max = height;

		if (width > max) {
			max = width;
		}

		double ratio = new Double(max).doubleValue() / size;

		if (ratio == 0) {
			ratio = size / max;
			display.setImage(img.getScaledInstance(new Double(width * ratio)
					.intValue(), new Double(height * ratio).intValue(),
					Image.SCALE_FAST));
		} else {

			display.setImage(img.getScaledInstance(new Double(width / ratio)
					.intValue(), new Double(height / ratio).intValue(),
					Image.SCALE_FAST));
		}

		bgImage = display;
		forceRepaint();
	}
	
}
