package initcheck.dungeon;

import java.util.Vector;

public interface MapPalette {

	public PaletteType getSelectedType();
	public PaletteMode getSeletedMode();
	public void setSelectedType(PaletteType type);
	public void setSelectedMode(PaletteMode mode);
	public void setTypes(Vector<PaletteType> types);
	public void setModes(Vector<PaletteMode> modes);
	public Vector<PaletteMode> getModes();
	public Vector<PaletteType> getTypes();
	
}
