package initcheck.dungeon;

import java.util.Vector;

public class TerrainPalette implements MapPalette {

	Vector<PaletteType> types = new Vector<PaletteType>();

	Vector<PaletteMode> modes = new Vector<PaletteMode>();

	PaletteMode selectedMode;
	
	PaletteType selectedType;
	
	public TerrainPalette() {
		PaletteMode pm = new PaletteMode("Point", "images/pointicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Line", "images/lineicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Area", "images/areaicon.jpg");
		modes.add(pm);
		pm = new PaletteMode("Flood", "images/fillicon.jpg");
		modes.add(pm);
		
		PaletteType pt = new PaletteType("Open Terrain", "images/grass.jpg", Dungeon.land);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		
		pt = new PaletteType("Bridge", "images/bridgeicon.jpg",Dungeon.bridge);
		pt.addMode("Point");
		pt.addMode("Line");
		types.add(pt);
		
		pt = new PaletteType("Water", "images/watericon.jpg",Dungeon.water);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		
		pt = new PaletteType("Road", "images/road.jpg", Dungeon.road);
		pt.addMode("Point");
		pt.addMode("Line");
		types.add(pt);
		
		pt = new PaletteType("Light Forest", "images/lightforest.jpg", Dungeon.lightforest);
		pt.addRenderImage("LightTrees1");
		pt.addRenderImage("LightTrees2");
		pt.addRenderImage("LightTrees3");		
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
	
		pt = new PaletteType("Medium Forest", "images/medforest.jpg", Dungeon.medforest);
		pt.setMoveRate(0.5);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
	
		pt = new PaletteType("Heavy Forest", "images/heavyforest.jpg", Dungeon.heavyforest);
		pt.setMoveRate(0.25);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
	
		pt = new PaletteType("Dense Forest", "images/heavyforest.jpg", Dungeon.impforest);
		pt.setMoveRate(0.125);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		types.add(pt);
		
		pt = new PaletteType("Light Mountains", "images/lightmountainicon.jpg", Dungeon.lightmountain);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("LightMountain");
		types.add(pt);
		
		pt = new PaletteType("Medium Mountains", "images/medmountainicon.jpg", Dungeon.medmountain);
		pt.setMoveRate(0.5);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("MediumMountain");
		types.add(pt);
		
		pt = new PaletteType("Heavy Mountains", "images/heavymountainicon.jpg", Dungeon.heavymountain);
		pt.setMoveRate(0.25);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("HeavyMountain");
		types.add(pt);
		
		pt = new PaletteType("Impassable Mountains", "images/heavymountainicon.jpg", Dungeon.impmountain);
		pt.setMoveRate(0.125);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("HeavyMountain");
		types.add(pt);
			
		pt = new PaletteType("Light Desert", "images/lightdeserticon.gif",Dungeon.lightdesert);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("LightDesert");
		types.add(pt);
		
		pt = new PaletteType("Medium Desert", "images/meddeserticon.gif",Dungeon.meddesert);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("LightDesert");
		types.add(pt);
		
		pt = new PaletteType("Heavy Desert", "images/deserticon.gif",Dungeon.heavydesert);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("LightDesert");
		types.add(pt);
		
		pt = new PaletteType("Impassible Desert", "images/deserticon.gif",Dungeon.impdesert);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("Desert");
		types.add(pt);
		
		pt = new PaletteType("Swamp", "images/swampicon.gif",Dungeon.water);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
		pt.setRenderType(MapSquare.TEXTURE);
		pt.addRenderImage("Swamp");
		types.add(pt);
		

		pt = new PaletteType("Town", "images/town.jpg", Dungeon.town);
		pt.addMode("Point");
		types.add(pt);
		
		pt = new PaletteType("Hole", "images/holeicon.jpg",Dungeon.hole);
		pt.addMode("Point");
		pt.addMode("Line");
		pt.addMode("Area");
		pt.addMode("Flood");
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
