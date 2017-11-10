package initcheck.dungeon;

import initcheck.utils.RandomRoll;

import java.awt.Image;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;

public class PaletteType implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String name;

	private ImageIcon icon;

	private int id;

	private Vector<String> validModes = new Vector<String>();

	private int renderType = MapSquare.BASIC;

	private int travelTime = 30;

	private double moveRate = 1.0;

	private String mapImage = null;

	private Vector<String> renderImages = new Vector<String>();

	public void addRenderImage(String i) {
		renderImages.add(i);
	}

	public Vector<String> getValidModes() {
		return validModes;
	}

	public void setValidModes(Vector<String> validModes) {
		this.validModes = validModes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addMode(String s) {
		validModes.add(s);
	}

	public boolean supportsMode(String s) {
		return validModes.contains(s);
	}

	public PaletteType(String name, String icon, int id) {
		this.name = name;
		setIcon(icon);
		this.id = id;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		//		 read in the picture from the file
		ImageIcon display = new ImageIcon(icon);
		int height = display.getIconHeight();
		int width = display.getIconWidth();

		// pull out the image
		Image img = display.getImage();

		// and scale the image to fit inside a reasonable bound
		int max = height;

		if (width > max) {
			max = width;
		}

		double ratio = new Double(max).doubleValue() / 30.0;

		if (ratio == 0) {
			ratio = 250 / max;
			display.setImage(img.getScaledInstance(new Double(width * ratio)
					.intValue(), new Double(height * ratio).intValue(),
					Image.SCALE_FAST));
		} else {

			display.setImage(img.getScaledInstance(new Double(width / ratio)
					.intValue(), new Double(height / ratio).intValue(),
					Image.SCALE_FAST));
		}

		
		this.icon = display;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMapImage() {

		mapImage = renderImages.get(RandomRoll.getRandom(renderImages.size()));

		
		return mapImage;
	}

	public int getRenderType() {
		return renderType;
	}

	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public int getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(int travelTime) {
		this.travelTime = travelTime;
	}

	public double getMoveRate() {
		return moveRate;
	}

	public void setMoveRate(double moveRate) {
		this.moveRate = moveRate;
	}

}
