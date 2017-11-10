package initcheck.dungeon;

import initcheck.InitColor;
import initcheck.InitImage;
import initcheck.database.Tag;
import initcheck.database.Trap;
import initcheck.utils.StrManip;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class MapSquare implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	// rendering types
	public static final int BASIC = 1;

	public static final int SHADED = 2;

	public static final int TEXTURE = 3;

	int type = 0;

	boolean visible = false;

	boolean visited = false;

	ArrayList<Integer> visArray = new ArrayList<Integer>(10);

	int xpos = 0;

	int ypos = 0;

	int zpos = 0;

	int roomNumber = -1;

	String notes = null;

	String dmNotes = null;

	// these are calculated in the setColors routine
	Color foreground = Color.black;

	Color background = Color.darkGray;

	// these are calculated in the setDirections routine
	boolean goesNorth = false;

	boolean goesSouth = false;

	boolean goesEast = false;

	boolean goesWest = false;

	Door northDoor = null;

	Door southDoor = null;

	Door eastDoor = null;

	Door westDoor = null;

	Trap trap = null;

	// variables used in calculating the shortest path
	boolean marked;

	boolean considered;

	boolean stair = false;

	boolean corridor = false;

	boolean room = false;

	int cost = 99999999;

	private int renderType = 1;

	private String toolTip = "";

	private PaletteType paletteType;

	private String mapLink;

	private String mapImage;

	public String getMapLink() {
		return mapLink;
	}

	public void setMapLink(String mapLink) {
		this.mapLink = mapLink;
	}

	public PaletteType getPaletteType() {
		return paletteType;
	}

	public BufferedImage getMapImage() {
		if (mapImage == null && paletteType != null) {
			mapImage = paletteType.getMapImage();
		}
		

		return InitImage.getBufferedImage(mapImage);
	}

	public double getTimeModifier() {
		if (paletteType != null) {
			return paletteType.getMoveRate();
		}
		return 1.0;
	}

	public void setPaletteType(PaletteType paletteType) {
		this.paletteType = paletteType;
	}

	public int getRenderType() {
		return renderType;
	}

	public void setRenderType(int renderType) {
		this.renderType = renderType;
	}

	public String getToolTip() {
		if (StrManip.isNullOrEmpty(toolTip)) {
			return notes;
		}
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public MapSquare(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("Type")) {
				setType(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Xpos")) {
				setXpos(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Visible")) {
				setVisible(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("VisArray")) {
				visArray.add(new Integer(t.getTagBody()));
			}
			if (t.getTagName().equals("RoomNumber")) {
				setRoomNumber(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Notes")) {
				if (!t.getTagBody().equals("")
						&& !t.getTagBody().equals("null")) {
					setNotes(t.getTagBody());
				}
			}
			if (t.getTagName().equals("DMNotes")) {
				if (!t.getTagBody().equals("")
						&& !t.getTagBody().equals("null")) {
					setDmNotes(t.getTagBody());
				}
			}
			if (t.getTagName().equals("NorthDoor")) {
				northDoor = (new Door(Tag.getTag(t.getTagBody()).getTagBody()));
			}
			if (t.getTagName().equals("SouthDoor")) {
				southDoor = (new Door(Tag.getTag(t.getTagBody()).getTagBody()));
			}
			if (t.getTagName().equals("EastDoor")) {
				eastDoor = (new Door(Tag.getTag(t.getTagBody()).getTagBody()));
			}
			if (t.getTagName().equals("WestDoor")) {
				westDoor = (new Door(Tag.getTag(t.getTagBody()).getTagBody()));
			}
			if (t.getTagName().equals("Trap")) {
				trap = (new Trap(t.getTagBody()));
			}
			if (t.getTagName().equals("Xpos")) {
				xpos = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Ypos")) {
				ypos = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Zpos")) {
				zpos = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("RenderType")) {
				renderType = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("ToolTip")) {
				toolTip = t.getTagBody();
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);

		}

	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MapSquare>\n");
		sb.append("<Type>" + type + "</Type>");
		sb.append("<Visible>" + visible + "</Visible>\n");
		for (int i = 0; i < visArray.size(); i++) {
			sb.append("<VisArray>" + visArray.get(i) + "</VisArray>\n");
		}
		sb.append("<RoomNumber>" + roomNumber + "</RoomNumber>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<DMNotes>" + dmNotes + "</DMNotes>\n");
		if (northDoor != null) {
			sb.append("<NorthDoor>\n" + northDoor.exportFormat()
					+ "</NorthDoor>\n");
		}
		if (southDoor != null) {
			sb.append("<SouthDoor>\n" + southDoor.exportFormat()
					+ "</SouthDoor>\n");
		}
		if (eastDoor != null) {
			sb.append("<EastDoor>\n" + eastDoor.exportFormat()
					+ "</EastDoor>\n");
		}
		if (westDoor != null) {
			sb.append("<WestDoor>\n" + westDoor.exportFormat()
					+ "</WestDoor>\n");
		}
		if (trap != null) {
			sb.append(trap.exportFormat());
		}
		sb.append("<Xpos>" + xpos + "</Xpos>");
		sb.append("<Ypos>" + ypos + "</Ypos>");
		sb.append("<Zpos>" + zpos + "</Zpos>");
		sb.append("<RenderType>" + renderType + "</RenderType>");
		sb.append("<ToolTip>" + toolTip + "</ToolTip>");
		sb.append("</MapSquare>\n");
		return sb.toString();
	}

	/**
	 * Get the Cost value.
	 * 
	 * @return the Cost value.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Set the Cost value.
	 * 
	 * @param newCost
	 *            The new Cost value.
	 */
	public void setCost(int newCost) {
		this.cost = newCost;
	}

	/**
	 * Get the Considered value.
	 * 
	 * @return the Considered value.
	 */
	public boolean isConsidered() {
		return considered;
	}

	/**
	 * Set the Considered value.
	 * 
	 * @param newConsidered
	 *            The new Considered value.
	 */
	public void setConsidered(boolean newConsidered) {
		if (newConsidered) {
			foreground = Color.red;
			background = Color.red;
		} else {
			setColors();
		}
		this.considered = newConsidered;
	}

	/**
	 * Get the Marked value.
	 * 
	 * @return the Marked value.
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * Set the Marked value.
	 * 
	 * @param newMarked
	 *            The new Marked value.
	 */
	public void setMarked(boolean newMarked) {
		if (newMarked) {
			foreground = Color.green;
			background = Color.green;
		} else {
			setColors();
		}
		this.marked = newMarked;
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		MapSquare clone = new MapSquare();
		clone.type = type;
		clone.visible = visible;
		clone.roomNumber = roomNumber;
		clone.notes = notes;
		clone.dmNotes = dmNotes;
		clone.foreground = foreground;
		clone.background = background;
		clone.goesNorth = goesNorth;
		clone.goesSouth = goesSouth;
		clone.goesEast = goesEast;
		clone.goesWest = goesWest;
		clone.northDoor = northDoor;
		clone.southDoor = southDoor;
		clone.eastDoor = eastDoor;
		clone.westDoor = westDoor;
		clone.trap = trap;
		clone.visArray = (ArrayList<Integer>) visArray.clone();
		return clone;
	}

	public boolean hasTrap() {
		return trap != null;
	}

	public void setTrap(Trap t) {
		trap = t;
	}

	public Trap getTrap() {
		return trap;
	}

	public void setDoor(String direction) {
		if (direction.equals("NORTH")) {
			northDoor = new Door();
		} else if (direction.equals("SOUTH")) {
			southDoor = new Door();
		} else if (direction.equals("EAST")) {
			eastDoor = new Door();
		} else if (direction.equals("WEST")) {
			westDoor = new Door();
		}
	}

	public void setDoor(String direction, String type) {
		if (direction.equals("NORTH")) {
			northDoor = new Door();
			northDoor.setType(type);
		} else if (direction.equals("SOUTH")) {
			southDoor = new Door();
			southDoor.setType(type);
		} else if (direction.equals("EAST")) {
			eastDoor = new Door();
			eastDoor.setType(type);
		} else if (direction.equals("WEST")) {
			westDoor = new Door();
			westDoor.setType(type);
		}
	}

	/**
	 * Set the WestDoor value.
	 * 
	 * @param newWestDoor
	 *            The new WestDoor value.
	 */
	public void setWestDoor(boolean newWestDoor) {
		if (newWestDoor) {
			setDoor("WEST");
		} else {
			westDoor = null;
		}
	}

	/**
	 * Set the SouthDoor value.
	 * 
	 * @param newSouthDoor
	 *            The new SouthDoor value.
	 */
	public void setSouthDoor(boolean newSouthDoor) {
		if (newSouthDoor) {
			setDoor("SOUTH");
		} else {
			southDoor = null;
		}
	}

	/**
	 * Set the EastDoor value.
	 * 
	 * @param newEastDoor
	 *            The new EastDoor value.
	 */
	public void setEastDoor(boolean newEastDoor) {
		if (newEastDoor) {
			setDoor("EAST");
		} else {
			eastDoor = null;
		}
	}

	/**
	 * Set the NorthDoor value.
	 * 
	 * @param newNorthDoor
	 *            The new NorthDoor value.
	 */
	public void setNorthDoor(boolean newNorthDoor) {
		if (newNorthDoor) {
			setDoor("NORTH");
		} else {
			northDoor = null;
		}

	}

	public Door getNorthDoor() {
		return northDoor;
	}

	public Door getSouthDoor() {
		return southDoor;
	}

	public Door getEastDoor() {
		return eastDoor;
	}

	public Door getWestDoor() {
		return westDoor;
	}

	public MapSquare() {

	}

	public void resetDoors() {
		// remove all the doors it may have had previously
		northDoor = null;
		southDoor = null;
		eastDoor = null;
		westDoor = null;
	}

	public MapSquare(int x, int y, int z) {
		xpos = x;
		ypos = y;
		zpos = z;
	}

	public boolean isStair() {
		return (type >= 5 && type <= 9);
	}

	public boolean isCorridor() {
		return corridor;
	}

	public boolean goesNorth() {
		return goesNorth;
	}

	public boolean goesSouth() {
		return goesSouth;
	}

	public boolean goesEast() {
		return goesEast;
	}

	public boolean goesWest() {
		return goesWest;
	}

	public boolean hasNorthDoor() {
		return northDoor != null;
	}

	public boolean hasSouthDoor() {
		return southDoor != null;
	}

	public boolean hasEastDoor() {
		return eastDoor != null;
	}

	public boolean hasWestDoor() {
		return westDoor != null;
	}

	public boolean hasDoors() {
		return northDoor != null || southDoor != null || eastDoor != null
				|| westDoor != null;
	}

	public boolean hasSecretDoors() {
		return (northDoor != null && northDoor.isSecret())
				|| (southDoor != null && southDoor.isSecret())
				|| (eastDoor != null && eastDoor.isSecret())
				|| (westDoor != null && westDoor.isSecret());
	}

	/**
	 * Get the Background value.
	 * 
	 * @return the Background value.
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Set the Background value.
	 * 
	 * @param newBackground
	 *            The new Background value.
	 */
	public void setBackground(Color newBackground) {
		this.background = newBackground;
	}

	/**
	 * Get the Foreground value.
	 * 
	 * @return the Foreground value.
	 */
	public Color getForeground() {
		return foreground;
	}

	/**
	 * Set the Foreground value.
	 * 
	 * @param newForeground
	 *            The new Foreground value.
	 */
	public void setForeground(Color newForeground) {
		this.foreground = newForeground;
	}

	/**
	 * Get the DmNotes value.
	 * 
	 * @return the DmNotes value.
	 */
	public String getDmNotes() {

		return dmNotes;
	}

	/**
	 * Set the DmNotes value.
	 * 
	 * @param newDmNotes
	 *            The new DmNotes value.
	 */
	public void setDmNotes(String newDmNotes) {
		this.dmNotes = newDmNotes;
	}

	/**
	 * Get the Notes value.
	 * 
	 * @return the Notes value.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the Notes value.
	 * 
	 * @param newNotes
	 *            The new Notes value.
	 */
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}

	/**
	 * Get the RoomNumber value.
	 * 
	 * @return the RoomNumber value.
	 */
	public int getRoomNumber() {
		return roomNumber;
	}

	/**
	 * Set the RoomNumber value.
	 * 
	 * @param newRoomNumber
	 *            The new RoomNumber value.
	 */
	public void setRoomNumber(int newRoomNumber) {
		this.roomNumber = newRoomNumber;
	}

	/**
	 * Get the Visible value.
	 * 
	 * @return the Visible value.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Set the Visible value.
	 * 
	 * @param newVisible
	 *            The new Visible value.
	 */
	public void setVisible(boolean newVisible) {
		this.visible = newVisible;
	}

	public boolean isVisible(int group) {
		return visArray.contains(group);
	}

	public void setVisible(boolean visible, int group) {
		if (visible) {
			if (!isVisible(group)) {
				visArray.add(group);
			}
		} else {
			for (int i = 0; i < visArray.size(); i++) {
				int g = visArray.get(i);
				if (g == group) {
					visArray.remove(i);
				}
			}
		}
	}

	public void mergeVisible(int a, int b) {
		if (isVisible(b)) {
			setVisible(true, a);
			setVisible(false, b);
		}

		for (int i = 0; i < visArray.size(); i++) {
			int group = visArray.get(i);
			if (group > b) {
				visArray.set(i, group - 1);
			}
		}
	}

	/**
	 * Get the Type value.
	 * 
	 * @return the Type value.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the Type value.
	 * 
	 * @param newType
	 *            The new Type value.
	 */
	public void setType(int newType) {

		// block updates of type if this is a room. The only way to get rid
		// of a room is to delete the room.
		if (room) {
			return;
		}

		this.type = newType;
		corridor = false;
		if (type == 3 || (type >= 12 && type < 20)
				|| (type >= 22 && type <= 26)) {
			corridor = true;
		}
		if (type == 2 || type >= 29 && type < 37) {
			room = true;
		}
		setColors();
		setDirections();
		mapImage = null;
	}

	public void setDirections() {

		// reset the directions
		goesNorth = false;
		goesSouth = false;
		goesEast = false;
		goesWest = false;

		// evaluate the type, and set directions accordingly
		if (type == Dungeon.cnorthsouth || type == Dungeon.necorner
				|| type == Dungeon.nwcorner || type == Dungeon.nintersect
				|| type == Dungeon.wintersect || type == Dungeon.eintersect
				|| type == Dungeon.allintersect) {
			goesNorth = true;
		}

		if (type == Dungeon.cnorthsouth || type == Dungeon.secorner
				|| type == Dungeon.swcorner || type == Dungeon.sintersect
				|| type == Dungeon.wintersect || type == Dungeon.eintersect
				|| type == Dungeon.allintersect) {
			goesSouth = true;
		}

		if (type == Dungeon.ceastwest || type == Dungeon.necorner
				|| type == Dungeon.secorner || type == Dungeon.eintersect
				|| type == Dungeon.nintersect || type == Dungeon.sintersect
				|| type == Dungeon.allintersect) {
			goesEast = true;
		}

		if (type == Dungeon.ceastwest || type == Dungeon.nwcorner
				|| type == Dungeon.swcorner || type == Dungeon.wintersect
				|| type == Dungeon.nintersect || type == Dungeon.sintersect
				|| type == Dungeon.allintersect) {
			goesWest = true;
		}
	}

	public void setColors() {
		// Determine colorings
		if (room || type == Dungeon.stairnorth || type == Dungeon.stairsouth
				|| type == Dungeon.staireast || type == Dungeon.stairwest
				|| type == Dungeon.staircircle) {

			background = Color.blue;
			background = background.darker();

			foreground = background.darker();
		}
		// corridor
		else if ((type >= 12 && type <= 20) || (type > 21 && type <= 26)) {
			background = Color.white.darker().darker();
			foreground = background.darker();
		} else if (type == Dungeon.wall) {
			background = Color.black;
			foreground = Color.black;
		} else if (type == Dungeon.bridge) {
			foreground = new Color(102, 51, 0);
			background = foreground.brighter().brighter().brighter().brighter()
					.brighter();
		} else if (type == Dungeon.water) {
			foreground = Color.black;
			background = Color.blue;
		} else if (type == Dungeon.hole) {
			foreground = Color.black;
			background = Color.gray;
		} else if (type == Dungeon.land) {
			foreground = Color.black;
			background = InitColor.openLand;
		} else if (type == Dungeon.lightforest) {
			foreground = Color.black;
			background = InitColor.lightForest;
		} else if (type == Dungeon.medforest) {
			foreground = Color.black;
			background = InitColor.medForest;
		} else if (type == Dungeon.heavyforest) {
			foreground = Color.black;
			background = InitColor.heavyForest;
		} else if (type == Dungeon.road) {
			foreground = Color.black;
			background = InitColor.road;
		} else {
			background = Color.darkGray;
			foreground = Color.black;
		}
	}

	/**
	 * Get the Xpos value.
	 * 
	 * @return the Xpos value.
	 */
	public int getXpos() {
		return xpos;
	}

	/**
	 * Set the Xpos value.
	 * 
	 * @param newXpos
	 *            The new Xpos value.
	 */
	public void setXpos(int newXpos) {
		this.xpos = newXpos;
	}

	/**
	 * Get the Ypos value.
	 * 
	 * @return the Ypos value.
	 */
	public int getYpos() {
		return ypos;
	}

	/**
	 * Set the Ypos value.
	 * 
	 * @param newYpos
	 *            The new Ypos value.
	 */
	public void setYpos(int newYpos) {
		this.ypos = newYpos;
	}

	/**
	 * Get the Zpos value.
	 * 
	 * @return the Zpos value.
	 */
	public int getZpos() {
		return zpos;
	}

	/**
	 * Set the Zpos value.
	 * 
	 * @param newZpos
	 *            The new Zpos value.
	 */
	public void setZpos(int newZpos) {
		this.zpos = newZpos;
	}

	public void setCorridor(boolean corridor) {
		this.corridor = corridor;
	}

	public void setStair(boolean stair) {
		this.stair = stair;
	}

	public boolean isRoom() {
		return room;
	}

	public void setRoom(boolean room) {
		this.room = room;
		if (room) {
			setType(Dungeon.room);
		}
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
