package initcheck.graphics;

import java.io.Serializable;

import initcheck.InitImage;

import javax.swing.ImageIcon;

public class Skin implements Serializable{

	private static final long serialVersionUID = 1L;
	boolean tileLists = true;
	boolean tileTextAreas = true;
	boolean tilePanels = true;
	String name = "Default";
	boolean useGraphics = true;
	ImageIcon backgroundPanelIcon = InitImage.darkRocks;
	ImageIcon foregroundPanelIcon = InitImage.lightRocks;
	
	public ImageIcon getBackgroundPanelIcon() {
		return backgroundPanelIcon;
	}

	public void setBackgroundPanelIcon(ImageIcon backgroundPanelIcon) {
		this.backgroundPanelIcon = backgroundPanelIcon;
	}

	public boolean isUseGraphics() {
		return useGraphics;
	}

	public void setUseGraphics(boolean useGraphics) {
		this.useGraphics = useGraphics;
	}

	public String toString(){
		return name;
	}
	
	public boolean isTileLists() {
		return tileLists;
	}

	public void setTileLists(boolean tileLists) {
		this.tileLists = tileLists;
	}

	public Skin(){
		
	}

	public boolean isTileTextAreas() {
		return tileTextAreas;
	}

	public void setTileTextAreas(boolean tileTextAreas) {
		this.tileTextAreas = tileTextAreas;
	}

	public boolean isTilePanels() {
		return tilePanels;
	}

	public void setTilePanels(boolean tilePanels) {
		this.tilePanels = tilePanels;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageIcon getForegroundPanelIcon() {
		return foregroundPanelIcon;
	}

	public void setForegroundPanelIcon(ImageIcon foregroundPanelIcon) {
		this.foregroundPanelIcon = foregroundPanelIcon;
	}
	
}