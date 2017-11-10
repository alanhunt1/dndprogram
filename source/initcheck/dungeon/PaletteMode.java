package initcheck.dungeon;

import javax.swing.ImageIcon;

public class PaletteMode {

	String name;
	ImageIcon icon; 
	int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PaletteMode(String name, String icon){
		this.name = name;
		this.icon = new ImageIcon(icon);
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = new ImageIcon(icon);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
