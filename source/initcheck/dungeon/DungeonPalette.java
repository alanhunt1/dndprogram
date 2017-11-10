package initcheck.dungeon;

import java.util.Vector;

public class DungeonPalette implements MapPalette {

	Vector<PaletteType> types = new Vector<PaletteType>();

	Vector<PaletteMode> modes = new Vector<PaletteMode>();

	PaletteMode selectedMode;
	
	PaletteType selectedType;
	
	public DungeonPalette() {
		PaletteMode pm = new PaletteMode("Point", "images/pointicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Line", "images/lineicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Area", "images/areaicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Flood", "images/fillicon.jpg");
		modes.add(pm);
		
		PaletteType pt = new PaletteType("Wall", "images/wallicon.jpg", Dungeon.wall);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		pt = new PaletteType("Door", "images/dooricon.jpg",Dungeon.door);
		pt.addMode("Point");
		types.add(pt);
		pt = new PaletteType("Secret Door", "images/secretdooricon.jpg", Dungeon.secretdoor);
		pt.addMode("Point");
		types.add(pt);
		pt = new PaletteType("Corridor", "images/corridoricon.jpg",Dungeon.corridor);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		pt = new PaletteType("Hole", "images/holeicon.jpg",Dungeon.hole);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		pt = new PaletteType("Water", "images/watericon.jpg",Dungeon.water);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		pt = new PaletteType("Bridge", "images/bridgeicon.jpg",Dungeon.bridge);
		pt.addMode("Point");
		pt.addMode("Line");
		types.add(pt);
		pt = new PaletteType("Stair", "images/stairicon.jpg",Dungeon.staircircle);
		pt.addMode("Point");
		types.add(pt);
		pt = new PaletteType("Room", "images/roomicon.jpg",Dungeon.room);
		pt.addMode("Area");
		types.add(pt);
		pt = new PaletteType("Trap", "images/trapicon.jpg",Dungeon.trap);
		pt.addMode("Point");
		types.add(pt);
	}

	public PaletteType getSelectedType() {
		return null;
	}

	public PaletteMode getSeletedMode() {
		return null;
	}

	public void setSelectedType(PaletteType type) {
	}

	public void setSelectedMode(PaletteMode mode) {
	}

	public void setTypes(Vector<PaletteType> types) {
		this.types = types;
	}

	public void setModes(Vector<PaletteMode> modes) {
		this.modes = modes;
	}

	public Vector<PaletteMode> getModes() {
		return modes;
	}

	public Vector<PaletteType> getTypes() {
		return types;
	}
}
