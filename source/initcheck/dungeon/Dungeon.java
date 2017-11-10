package initcheck.dungeon;

import initcheck.CreateEncounterDialog;
import initcheck.InitLogger;
import initcheck.character.LoadPartyProgressPanel;
import initcheck.database.Monster;
import initcheck.database.RoomDescriptionDAO;
import initcheck.database.Tag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Dungeon implements Serializable {

	private static final long serialVersionUID = 1L;

	// various square types
	public static final int init = 0;

	public static final int wall = 1;

	public static final int room = 2;

	public static final int corridor = 3;

	public static final int door = 4;

	// stair types
	public static final int stairnorth = 5;// staircase square. north is high

	// end

	public static final int stairsouth = 6;

	public static final int staireast = 7;

	public static final int stairwest = 8;

	public static final int staircircle = 9;

	public static final int air = 10;

	public static final int hole = 10;

	public static final int bridge = 11;

	public static final int secretdoor = 20;

	public static final int water = 21;

	public static final int display = 1;

	public static final int offmap = -1;

	public static final int trap = 27;

	// corridor types
	public static final int cnorthsouth = 12;

	public static final int ceastwest = 13;

	public static final int ceast = 14;

	public static final int cwest = 15;

	// corner types
	public static final int necorner = 16;

	public static final int nwcorner = 17;

	public static final int secorner = 18;

	public static final int swcorner = 19;

	// intersection types
	public static final int nintersect = 22;

	public static final int sintersect = 23;

	public static final int eintersect = 24;

	public static final int wintersect = 25;

	public static final int allintersect = 26;

	public static final int land = 28;

	// room types for wall drawing
	public static final int trcorner = 29;

	public static final int tlcorner = 30;

	public static final int brcorner= 31;

	public static final int blcorner = 32;

	public static final int top = 33;

	public static final int right = 34;

	public static final int left = 35;

	public static final int bottom = 36;	
	
	// terrain types for outdoor maps
	public static final int lightforest = 37;
	
	public static final int medforest = 38;
	
	public static final int heavyforest = 39;
	
	public static final int lightmountain = 40;
	
	public static final int medmountain = 41;
	
	public static final int heavymountain = 42;
	
	public static final int road = 43;
	
	public static final int town = 44;
	
	public static final int impdesert = 45;
	
	public static final int lightdesert = 46;
	
	public static final int meddesert = 47;
	
	public static final int heavydesert = 48;
	
	public static final int impmountain = 49;
	
	public static final int impforest = 50;
	
	public static final int impswamp = 51;
	
	public static final int lightswamp = 52;
	
	public static final int medswamp = 53;
	
	public static final int heavyswamp = 54;
	
	public static final int maplink = 55;
	
	// array locations for neighboring squares
	public static final int NORTH = 0;

	public static final int NORTHEAST = 1;

	public static final int EAST = 2;

	public static final int SOUTHEAST = 3;

	public static final int SOUTH = 4;

	public static final int SOUTHWEST = 5;

	public static final int WEST = 6;

	public static final int NORTHWEST = 7;

	public static final int SQUARE = 0;

	public static final int HEX = 1;

	public String name;

	// generation variables
	public int asizex;

	public int asizey;

	public int asizez;

	public float openness = 3;

	public int curliness = 3;

	public int numRooms = 15;

	public int maxRoomWidth = 10;

	public int maxRoomHeight = 10;

	public int minRoomWidth = 3;

	public int minRoomHeight = 3;

	public int doorchance = 3;

	public int secretdoorchance = 4;

	public int deadEndChance = 3;

	public int sparseness = 10;

	public int encounterChance = 4;

	public int partySize;

	public int visited[][][];

	public int dead[][][];

	public int directionarr[][][];

	public MapSquare squares[][][];

	public Vector<Room> rooms = new Vector<Room>();

	// Entrance - from which algorithms start
	// private Square entry;

	// types of monsters to use in random encounters
	private Vector<Monster> randomMonsterTypes;

	// use random monsters in generating encounters?
	public boolean useRandomMonsters = false;

	// is the map locked for display, or in edit mode?
	public boolean locked = false;

	// current position of the generation algos
	private int currX;

	private int currY;

	private int currZ;

	// current direction of corridor generator
	private int direction = 1;

	private Random r;

	// current position of the party
	private int currentXPosition = 0;

	private int currentYPosition = 0;

	private int currentLevel = 0;

	// current group displayed on the map
	private int currentGroup = 0;

	private InitLogger logger = new InitLogger(this);

	private ArrayList<Path> pathList = new ArrayList<Path>(100);

	static String loadError;

	double partyLevel = 6.0;

	// time counter variables
	int timeCount = 0;

	int timeIncrement = 180;

	int mappedIncrement = 60;

	int roomIncrement = 180;

	int totalTime;

	// type of map - can be SQUARE or HEX
	int style = SQUARE;

	String type = "Dungeon";

	String fileName = "";
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Dungeon(String fileName){
		this.fileName = fileName;
	}
	
	public Dungeon(String s, LoadPartyProgressPanel lpp) {

		int roomCount = 0;
		Tag t = Tag.getTag(s);

		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();

			if (t.getTagName().equals("Name")) {
				name = (t.getTagBody());
			}
			if (t.getTagName().equals("Width")) {
				asizex = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Height")) {
				asizey = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Levels")) {
				asizez = Integer.parseInt(t.getTagBody());
				init(asizex, asizey, asizez);
				lpp.setMaxValue(asizex * asizey * asizez);
			}

			if (t.getTagName().equals("MapSquare")) {
				MapSquare msq = new MapSquare(t.getTagBody());
				squares[msq.getXpos()][msq.getYpos()][msq.getZpos()] = msq;
				lpp.incValue();
			}

			if (t.getTagName().equals("RoomCount")) {
				roomCount = Integer.parseInt(t.getTagBody());
				lpp.signal("Creating Rooms");
				lpp.setValue(0);
				lpp.setMaxValue(roomCount);
			}

			if (t.getTagName().equals("Room")) {
				rooms.add(new Room(t.getTagBody()));
				lpp.incValue();
			}
			if (t.getTagName().equals("CurrX")) {
				currentXPosition = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("CurrY")) {
				currentYPosition = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("CurrZ")) {
				currentLevel = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Group")) {
				currentGroup = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("TimeCount")) {
				timeCount = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("TimeIncrement")) {
				timeIncrement = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("MappedIncrement")) {
				mappedIncrement = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("RoomIncrement")) {
				roomIncrement = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("TotalTime")) {
				totalTime = Integer.parseInt(t.getTagBody());
			}
			if (t.getTagName().equals("Style")) {
				style = Integer.parseInt(t.getTagBody());
			}

			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);

		}
	}

	public String exportFormat() {
		StringBuilder sb = new StringBuilder();
		sb.append("<Dungeon>\n");
		sb.append("<Name>").append(name).append("</Name>\n");
		sb.append("<Width>" + asizex + "</Width>\n");
		sb.append("<Height>" + asizey + "</Height>\n");
		sb.append("<Levels>" + asizez + "</Levels>\n");

		// squares
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					sb.append(squares[i][j][k].exportFormat());
				}
			}
		}

		sb.append("<RoomCount>" + rooms.size() + "</RoomCount>\n");

		// rooms
		for (int i = 0; i < rooms.size(); i++) {
			sb.append(((Room) rooms.get(i)).exportFormat());
		}

		sb.append("<CurrX>" + currentXPosition + "</CurrX>\n");
		sb.append("<CurrY>" + currentYPosition + "</CurrY>\n");
		sb.append("<CurrZ>" + currentLevel + "</CurrZ>\n");
		sb.append("<Group>" + currentGroup + "</Group>\n");
		sb.append("<TimeCount>" + timeCount + "</TimeCount>\n");
		sb.append("<TimeIncrement>" + timeIncrement + "</TimeIncrement>\n");
		sb.append("<MappedIncrement>" + mappedIncrement
				+ "</MappedIncrement>\n");
		sb.append("<RoomIncrement>" + roomIncrement + "</RoomIncrement>\n");
		sb.append("<TotalTime>" + totalTime + "</TotalTime>\n");
		sb.append("<Style>" + style + "</Style>\n");
		sb.append("</Dungeon>\n");
		return sb.toString();
	}

	/**
	 * Get the Style value.
	 * 
	 * @return the Style value.
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * Set the Style value.
	 * 
	 * @param newStyle
	 *            The new Style value.
	 */
	public void setStyle(int newStyle) {
		this.style = newStyle;
	}

	/**
	 * Get the RandomMonsterTypes value.
	 * 
	 * @return the RandomMonsterTypes value.
	 */
	public Vector<Monster> getRandomMonsterTypes() {
		return randomMonsterTypes;
	}

	/**
	 * Set the RandomMonsterTypes value.
	 * 
	 * @param newRandomMonsterTypes
	 *            The new RandomMonsterTypes value.
	 */
	public void setRandomMonsterTypes(Vector<Monster> newRandomMonsterTypes) {
		this.randomMonsterTypes = newRandomMonsterTypes;
	}

	/**
	 * Get the PartySize value.
	 * 
	 * @return the PartySize value.
	 */
	public int getPartySize() {
		return partySize;
	}

	/**
	 * Set the PartySize value.
	 * 
	 * @param newPartySize
	 *            The new PartySize value.
	 */
	public void setPartySize(int newPartySize) {
		this.partySize = newPartySize;
	}

	/**
	 * Get the TotalTime value.
	 * 
	 * @return the TotalTime value.
	 */
	public int getTotalTime() {
		return totalTime;
	}

	/**
	 * Set the TotalTime value.
	 * 
	 * @param newTotalTime
	 *            The new TotalTime value.
	 */
	public void setTotalTime(int newTotalTime) {
		this.totalTime = newTotalTime;
	}

	public void mergeVisibility(int a, int b) {
		

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					squares[i][j][k].mergeVisible(a, b);
				}
			}
		}
	}

	/**
	 * Get the CurrentGroup value.
	 * 
	 * @return the CurrentGroup value.
	 */
	public int getCurrentGroup() {
		return currentGroup;
	}

	/**
	 * Set the CurrentGroup value.
	 * 
	 * @param newCurrentGroup
	 *            The new CurrentGroup value.
	 */
	public void setCurrentGroup(int newCurrentGroup) {
		this.currentGroup = newCurrentGroup;
	}

	/**
	 * Get the LoadError value.
	 * 
	 * @return the LoadError value.
	 */
	public static String getLoadError() {
		return loadError;
	}

	/**
	 * Set the LoadError value.
	 * 
	 * @param newLoadError
	 *            The new LoadError value.
	 */
	public static void setLoadError(String newLoadError) {
		loadError = newLoadError;
	}

	/**
	 * Get the RoomIncrement value.
	 * 
	 * @return the RoomIncrement value.
	 */
	public int getRoomIncrement() {
		return roomIncrement;
	}

	/**
	 * Set the RoomIncrement value.
	 * 
	 * @param newRoomIncrement
	 *            The new RoomIncrement value.
	 */
	public void setRoomIncrement(int newRoomIncrement) {
		this.roomIncrement = newRoomIncrement;
	}

	/**
	 * Get the MappedIncrement value.
	 * 
	 * @return the MappedIncrement value.
	 */
	public int getMappedIncrement() {
		return mappedIncrement;
	}

	/**
	 * Set the MappedIncrement value.
	 * 
	 * @param newMappedIncrement
	 *            The new MappedIncrement value.
	 */
	public void setMappedIncrement(int newMappedIncrement) {
		this.mappedIncrement = newMappedIncrement;
	}

	public String insertMap(Dungeon d, int x, int y, int z) {

		// we can only "paste in" a single layer.
		if (d.asizez > 1) {
			return "You cannot paste in multi-layer maps!";
		}

		// copy the cells
		for (int i = 0; i < d.asizex; i++) {
			for (int j = 0; j < d.asizey; j++) {
				visited[x + i][y + j][currentLevel] = d.visited[i][j][0];
				directionarr[x + i][y + j][currentLevel] = d.directionarr[i][j][0];
				dead[x + i][y + j][currentLevel] = d.dead[i][j][0];
				squares[x + i][y + j][currentLevel] = (MapSquare) (d.squares[i][j][0]
						.clone());
			}
		}

		// add in the rooms
		for (int i = 0; i < d.rooms.size(); i++) {
			Room r = (Room) ((Room) d.rooms.get(i)).clone();

			// translate the room coordinates to the new map coordinates
			r.setTop(r.getTop() + y);
			r.setBottom(r.getBottom() + y);
			r.setRight(r.getRight() + x);
			r.setLeft(r.getLeft() + x);
			r.setLevel(z);
			rooms.add(r);
		}
		return null;
	}

	/**
	 * Get the TimeIncrement value.
	 * 
	 * @return the TimeIncrement value.
	 */
	public int getTimeIncrement() {
		return timeIncrement;
	}

	/**
	 * Set the TimeIncrement value.
	 * 
	 * @param newTimeIncrement
	 *            The new TimeIncrement value.
	 */
	public void setTimeIncrement(int newTimeIncrement) {
		this.timeIncrement = newTimeIncrement;
	}

	/**
	 * Get the TimeCount value.
	 * 
	 * @return the TimeCount value.
	 */
	public int getTimeCount() {
		return timeCount;
	}

	/**
	 * Set the TimeCount value.
	 * 
	 * @param newTimeCount
	 *            The new TimeCount value.
	 */
	public void incTimeCount(int increment) {
		timeCount += increment;
		totalTime += increment;
	}

	/**
	 * Set the TimeCount value.
	 * 
	 * @param newTimeCount
	 *            The new TimeCount value.
	 */
	public void decTimeCount(int increment) {
		timeCount -= increment;
		totalTime -= increment;
	}

	public void resetTimeCount() {
		timeCount = 0;
		totalTime += 9 * 60 * 60;
	}

	/**
	 * Get the PartyLevel value.
	 * 
	 * @return the PartyLevel value.
	 */
	public double getPartyLevel() {
		return partyLevel;
	}

	/**
	 * Set the PartyLevel value.
	 * 
	 * @param newPartyLevel
	 *            The new PartyLevel value.
	 */
	public void setPartyLevel(double newPartyLevel) {
		this.partyLevel = newPartyLevel;
	}

	public static String getTypeDescription(int i) {
		switch (i) {
		case 1:
			return "Wall";
		case 2:
			return "Room";
		case 3:
			return "Corridor";
		case 4:
			return "Door";
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			return "Stairs";
		case 10:
			return "Hole";
		case 11:
			return "Bridge";
		case 12:
			return "North - South Corridor";
		case 13:
			return "East - West Corridor";
		case 14:
		case 15:
			return "Dead End";
		case 16:
			return "North-East Corner";
		case 17:
			return "North-West Corner";
		case 18:
			return "South-East Corner";
		case 19:
			return "South-West Corner";
		case 20:
			return "Secret Door";
		case 21:
			return "Water";
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
			return "Intersection";
		case 28:
			return "Plains";
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:	
				return "Room";
		case 37:
			return "Light Forest";
		case 38:
			return "Medium Forest";
		case 39:
			return "Heavy Forest";	
		case 40:
			return "Light Mountains";
		case 41:
			return "Medium Mountains";
		case 42:
			return "Heavy Mountains";
		case 43:
			return "Road";
		case 44:
			return "Town";
		case 45: return "Impassable Desert";
		case 46: return "Light Desert";
		case 47: return "Medium Desert";
		case 48: return "Heavy Desert";
		}
		return "Unknown Type " + i;
	}

	public Square getCurrentPosition() {
		return new Square(currentXPosition, currentYPosition, currentLevel);
	}

	public void setCurrentPosition(int x, int y, int level) {
		currentXPosition = x;
		currentYPosition = y;
		currentLevel = level;
	}

	public Room getRoom(int x, int y, int z) {
		
		int roomNumber = squares[x][y][z].getRoomNumber();
		if (roomNumber >= 0) {
			return getRoom(roomNumber + 1);
		}
		return null;
	}

	public Room getRoom(int i) {
		
		if (rooms != null && rooms.size() >= i) {
			return (Room) rooms.get(i - 1);
		}
		return null;
	}

	public Vector<Room> getRooms(){
		return rooms;
	}
	
	
	public void markVisible(int x, int y, int level) {
		markVisible(x, y, level, true);
	}
	
	public void markVisible(int x, int y, int level, boolean visible) {
		// mark the cell visible
		squares[x][y][level].setVisible(visible, currentGroup);

		// if it is a room, mark the whole room visible
		if (squares[x][y][level].isRoom()) {

			Room r = (Room) rooms.get(squares[x][y][level].getRoomNumber());

			
			for (int i = r.getTop(); i < r.getTop() + r.getHeight(); i++) {
				for (int j = r.getLeft(); j < r.getLeft() + r.getWidth(); j++) {
					squares[j][i][level].setVisible(visible, currentGroup);
				}
			}

			// mark the doors visible as well.
			Vector<Square> doors = r.getDoors();
			for (int i = 0; i < doors.size(); i++) {
				Square s = (Square) doors.get(i);
				squares[s.getX()][s.getY()][level].setVisible(visible,
						currentGroup);
			}
		}
	}

	public Dungeon(int sizex, int sizey, int sizez) {
		init(sizex, sizey, sizez);
	}

	public Dungeon(int sizex, int sizey) {
		init(sizex, sizey, 1);
	}

	private void init(int sizex, int sizey, int sizez) {
		asizex = sizex;
		asizey = sizey;
		asizez = sizez;
		visited = new int[sizex][sizey][sizez]; // initialize array; all 0

		dead = new int[sizex][sizey][sizez]; // initialize array; all 0
		directionarr = new int[sizex][sizey][sizez]; // initialize array; all
		// 0

		squares = new MapSquare[sizex][sizey][sizez];
		initializeSquares();
		r = new Random();

		rooms = new Vector<Room>();
		// entry = new Square(0, 1, asizez - 1);
	}

	public void removeLevel(int level){
		
		// don't remove non-existant levels
		if (level > asizez){
			return;
		}
		
		asizez -= 1;

		int[][][] newvisited = new int[asizex][asizey][asizez]; // initialize
		// array; all 0
		int[][][] newdead = new int[asizex][asizey][asizez]; // initialize
		// array; all 0
		int[][][] newdirectionarr = new int[asizex][asizey][asizez]; // initialize
		// array;
		// all 0
		MapSquare[][][] newsquares = new MapSquare[asizex][asizey][asizez];

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				int oldK = 0;
				
				for (int k = 0; k < asizez; k++) {
					if (k == level){
						oldK++;	
					}
					newsquares[i][j][k] = squares[i][j][oldK];
					newvisited[i][j][k] = visited[i][j][oldK];
					newdead[i][j][k] = dead[i][j][oldK];
					newdirectionarr[i][j][k] = directionarr[i][j][oldK];
					oldK++;
					
				}
				
			}
		}
		visited = newvisited;
		dead = newdead;
		directionarr = newdirectionarr;
		squares = newsquares;
		
		// remove room discriptions from deleted level
		Vector<Room> newRooms = new Vector<Room>();
		for (int i = 0; i < rooms.size(); i++){
			Room r = (Room)rooms.get(i);
			if (r.getLevel() != level){
				if (r.getLevel() > level){
					r.setLevel(r.getLevel()-1);
				}
				newRooms.add(r);
			}
		}
		rooms = newRooms;
	}
	
	public void addLevel(int level) {
		asizez += 1;

		int[][][] newvisited = new int[asizex][asizey][asizez]; // initialize
		// array; all 0
		int[][][] newdead = new int[asizex][asizey][asizez]; // initialize
		// array; all 0
		int[][][] newdirectionarr = new int[asizex][asizey][asizez]; // initialize
		// array;
		// all 0
		MapSquare[][][] newsquares = new MapSquare[asizex][asizey][asizez];

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				int k;
				for (k = 0; k < asizez - 1; k++) {

					newsquares[i][j][k] = squares[i][j][k];
					newvisited[i][j][k] = visited[i][j][k];
					newdead[i][j][k] = dead[i][j][k];
					newdirectionarr[i][j][k] = directionarr[i][j][k];
				}
				newsquares[i][j][k] = new MapSquare();
				newvisited[i][j][k] = 0;
				newdead[i][j][k] = 0;
				newdirectionarr[i][j][k] = 0;
			}
		}
		visited = newvisited;
		dead = newdead;
		directionarr = newdirectionarr;
		squares = newsquares;
	}

	
	
	public void randomizeLevel(int level){
		
		
		
		// reset the arrays for this level.
		for (int i = 0; i < asizex; i++){
			for (int j = 0; j < asizey; j++){
				visited[i][j][level] = 0;
				squares[i][j][level] = new MapSquare();
				dead[i][j][level] = 0;
				directionarr[i][j][level] = 0;
			}
		}
		// now pick a random spot to start generation
		currX = getRandom(asizex) - 1;
		currY = getRandom(asizey) - 1;
		currZ = level;
		
		while (visited[currX][currY][currZ] == 1) {
			currX = getRandom(asizex) - 1;
			currY = getRandom(asizey) - 1;
		}

		// mark it visited, and set it to be a corridor.
		visited[currX][currY][currZ] = 1;
		squares[currX][currY][currZ].setType(corridor);
		directionarr[currX][currY][currZ] = 1;

		logger.log("Starting in cell [" + currX + "][" + currY + "][" + currZ
				+ "]");

		// initialize variables for progress reporting
		//int totalSquares = asizex * asizey * asizez;
		//int progressStep = totalSquares / 20;
		int step = 0;

		// signal the progress monitor that corridor generation is starting
		//comp.signal("Generating Random Corridors");

		/*
		 * From the current cell, pick a random direction (north, south, east,
		 * or west). If (1) there is no cell adjacent to the current cell in
		 * that direction, or (2) if the adjacent cell in that direction has
		 * been visited, then that direction is invalid, and you must pick a
		 * different random direction. If all directions are invalid, pick a
		 * different random visited cell in the grid and start this step over
		 * again.
		 */
		while (!isComplete()) {
			step++;
			//if (comp != null && step % progressStep == 0) {
			//	comp.incValue();
			//}

			// printDungeon();
			boolean found = getRandAdjacent();

			if (!found) {
				boolean newPosFound = false;
				dead[currX][currY][currZ] = 1;

				while (newPosFound == false) {
					currX = getRandom(asizex) - 1;
					currY = getRandom(asizey) - 1;
					currZ = level;

					if (visited[currX][currY][currZ] == 1
							&& dead[currX][currY][currZ] != 1) {
						newPosFound = true;
					}
				}
			} else {

				visited[currX][currY][currZ] = 1;
				directionarr[currX][currY][currZ] = direction;
				squares[currX][currY][currZ].setType(corridor);

				/*
				 * when you fill in a cell, fill in walls around the previous
				 * cell
				 */
				switch (direction) {
				case 1:
					addEastWestWall(currY + 1);
					break;
				case 2:
					addEastWestWall(currY - 1);
					break;
				case 3:
					addNorthSouthWall(currX - 1);
					break;
				case 4:
					addNorthSouthWall(currX + 1);
					break;
				}
			}
		}

		//comp.signal("Removing Dead Ends");
		
		// remove some dead ends by chopping them off
		for (int i = 0; i < sparseness; i++) {
			makeSparse();
		}

		// remove others by connecting them
		removeDeadEnds();

		// remove some dead ends by chopping them off
		for (int i = 0; i < sparseness; i++) {
			makeSparse();
		}

		//if (comp != null) {
		//	comp.setValue(25);
		//}

		
		// make the rooms
		makeRooms(level, asizez, numRooms, maxRoomWidth, maxRoomHeight, null);
		

		//if (comp != null) {
		//	comp.setValue(95);
		//}

		//comp.signal("Cleaning Up");

		// evaluate the corridors
		evaluateCorridors();

		// add doors
		addDoors();
		//comp.setValue(100);
	}
	
	public static Dungeon load(String filename) {
		return load(filename, true);
	}

	public static Dungeon load(String filename, boolean lock) {
		
		Dungeon m = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			GZIPInputStream gzis = new GZIPInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(gzis);
			m = (Dungeon) in.readObject();
			// always lock the dungeon when you load it. Heh heh, lock and load!
			// I'm such a geek.
			m.locked = lock;
			in.close();
		} catch (java.io.InvalidClassException err) {
			loadError = "The map you attempted to load is not compatible with the current version of the map editor.";

			m = null;
		} catch (IOException err) {
			err.printStackTrace();
			m = null;
		} catch (ClassNotFoundException err) {
			err.printStackTrace();
			m = null;
		}
		if (m != null){
			m.fileName = filename;
		}
		return m;
	}

	public static Dungeon importDungeon(String filename,
			LoadPartyProgressPanel lpp, int modLength) {
		Dungeon dungeon = null;
		try {
			FileReader fos = new FileReader(filename);
			BufferedReader reader = new BufferedReader(fos);
			String line = reader.readLine();
			StringBuffer charString = new StringBuffer(modLength);

			lpp.signal("Importing File");

			// read in the file one line at a time
			while (line != null) {
				charString.append(line);

				// if we are reading in a dungeon, process appropriately.
				if (line.startsWith("<Dungeon>")) {
					while (line != null && line.indexOf("</Dungeon>") < 0) {
						
						line = reader.readLine();
						lpp.incValue(line.length() + 1);
						if (!line.startsWith("<")) {
							charString.append("\n");
						}
						charString.append(line);
					}
					if (line != null) {
						String tagBody = "";
						Tag t = Tag.getTag(charString.toString());
						if (t != null) {

							tagBody = t.getTagBody();
						}
						lpp.signal("Creating Dungeon");
						lpp.setValue(0);
						
						dungeon = new Dungeon(tagBody, lpp);
						line = "";
						charString = new StringBuffer();
					}
				} else {
					line = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dungeon;
	}

	public static void export(String filename, Dungeon m) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter writer = new PrintWriter(fos);
			writer.write(m.exportFormat());
			writer.close();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public static void save(String filename, Dungeon m) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			GZIPOutputStream gzos = new GZIPOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(gzos);
			out.writeObject(m);
			out.flush();
			out.close();
			
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	private int getRandom(int i) {
		int j = r.nextInt(i);
		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

	private int getRandom(int i, int max) {
		int j = r.nextInt(max);

		while (j + 1 < i) {
			j = r.nextInt(max);
		}

		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

	public boolean getRandAdjacent() {

		boolean lookedNorth = false;
		boolean lookedSouth = false;
		boolean lookedEast = false;
		boolean lookedWest = false;

		boolean foundAdjacent = false;

		while (!foundAdjacent) {

			if (getRandom(curliness) == 1) {
				direction = getRandom(4);
			}

			switch (direction) {
			case 1:
				lookedNorth = true;
				if ((currY - 1) >= 0 && (currY - 1) < asizey
						&& visited[currX][currY - 1][currZ] == 0) {
					foundAdjacent = true;
					currY = currY - 1;
				}
				break;
			case 2:
				lookedSouth = true;
				if ((currY + 1) >= 0 && (currY + 1) < asizey
						&& visited[currX][currY + 1][currZ] == 0) {
					foundAdjacent = true;
					currY = currY + 1;
				}
				break;
			case 3:
				lookedEast = true;
				if ((currX + 1) >= 0 && (currX + 1) < asizex
						&& visited[currX + 1][currY][currZ] == 0) {
					foundAdjacent = true;
					currX = currX + 1;
				}
				break;
			case 4:
				lookedWest = true;
				if ((currX - 1) >= 0 && (currX - 1) < asizex
						&& visited[currX - 1][currY][currZ] == 0) {
					foundAdjacent = true;
					currX = currX - 1;
				}
				break;
			}

			if (lookedNorth && lookedSouth && lookedEast && lookedWest) {
				break;
			}
		}
		// logger.log("found it");
		return foundAdjacent;
	}

	public boolean isComplete() {

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					if (squares[i][j][k].getType() == init
							&& dead[i][j][k] == 0) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public int countNeighbors(int i, int j, int k) {

		int count = 0;

		// check the northern neighbor
		if (i + 1 < asizex && squares[i + 1][j][k].getType() != wall) {
			count++;
		}

		// check the southern neighbor
		if (i - 1 >= 0 && squares[i - 1][j][k].getType() != wall) {
			count++;
		}

		// check the eastern neighbor
		if (j + 1 < asizey && squares[i][j + 1][k].getType() != wall) {
			count++;
		}

		// check the western neighbor
		if (j - 1 >= 0 && squares[i][j - 1][k].getType() != wall) {
			count++;
		}

		return count;
	}

	public boolean makeSparse() {

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					int numExits = countNeighbors(i, j, k);
					if (numExits < 2) {
						if (squares[i][j][k].isCorridor()) {
							squares[i][j][k].setType(wall);
						}
					}
				}
			}
		}

		return true;
	}

	public void setSquare(int x, int y, int z, int type) {
		squares[x][y][z].setType(type);
	}

	public void makeTunnel(int direction) {

		switch (direction) {
		case 1:
			while ((currY - 1) >= 0 && (currY - 1) < asizey
					&& squares[currX][currY - 1][currZ].getType() == wall) {
				currY = currY - 1;
				squares[currX][currY][currZ].setType(corridor);
				directionarr[currX][currY][currZ] = direction;
			}
			break;
		case 2:
			while ((currY + 1) >= 0 && (currY + 1) < asizey
					&& squares[currX][currY + 1][currZ].getType() == wall) {
				currY = currY + 1;
				squares[currX][currY][currZ].setType(corridor);
				directionarr[currX][currY][currZ] = direction;
			}
			break;
		case 3:
			while ((currX + 1) >= 0 && (currX + 1) < asizex
					&& squares[currX + 1][currY][currZ].getType() == wall) {
				currX = currX + 1;
				squares[currX][currY][currZ].setType(corridor);
				directionarr[currX][currY][currZ] = direction;
			}
			break;
		case 4:
			while ((currX - 1) >= 0 && (currX - 1) < asizex
					&& squares[currX - 1][currY][currZ].getType() == wall) {
				currX = currX - 1;
				squares[currX][currY][currZ].setType(corridor);
				directionarr[currX][currY][currZ] = direction;
			}
			break;
		}
	}

	public void removeDeadEnds() {
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					int numExits = countNeighbors(i, j, k);
					if (numExits < 2) {
						int dir = directionarr[i][j][k];
						currX = i;
						currY = j;
						currZ = k;
						if (squares[i][j][k].getType() == 3
								&& getRandom(deadEndChance) == 1) {
							makeTunnel(dir);
						}
					}
				}
			}
		}
	}

	public boolean bordersCorridor(int i, int j, int k) {

		// check the northern neighbor
		if (i + 1 < asizex && squares[i + 1][j][k].isCorridor()) {
			return true;
		}

		// check the southern neighbor
		if (i - 1 >= 0 && squares[i - 1][j][k].isCorridor()) {
			return true;
		}

		// check the eastern neighbor
		if (j + 1 < asizey && squares[i][j + 1][k].isCorridor()) {
			return true;
		}

		// check the western neighbor
		if (j - 1 >= 0 && squares[i][j - 1][k].isCorridor()) {
			return true;
		}

		return false;
	}

	public Room bordersRoom(int i, int j, int k) {

		// check the northern neighbor
		if (i + 1 < asizex && squares[i + 1][j][k].isRoom()) {
			return (Room) rooms.get(squares[i + 1][j][k].getRoomNumber());
		}

		// check the southern neighbor
		if (i - 1 >= 0 && squares[i - 1][j][k].isRoom()) {
			return (Room) rooms.get(squares[i - 1][j][k].getRoomNumber());
		}

		// check the eastern neighbor
		if (j + 1 < asizey && squares[i][j + 1][k].isRoom()) {
			return (Room) rooms.get(squares[i][j + 1][k].getRoomNumber());
		}

		// check the western neighbor
		if (j - 1 >= 0 && squares[i][j - 1][k].isRoom()) {
			return (Room) rooms.get(squares[i][j - 1][k].getRoomNumber());
		}

		return null;
	}

	public void addDoors() {
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					Room r = bordersRoom(i, j, k);
					if (squares[i][j][k].isCorridor() && r != null) {

						if (!r.hasDoor(i, j)) {
							r.addDoor(i, j);
							// add secret doors
							if (getRandom(secretdoorchance) == 1) {
								MapSquare nb[] = new MapSquare[8];
								getNeighbors(i, j, k, nb);
								if (isRoom(nb[Dungeon.NORTH])) {
									squares[i][j][k].setDoor("NORTH", "secret");
								}
								if (isRoom(nb[Dungeon.SOUTH])) {
									squares[i][j][k].setDoor("SOUTH", "secret");
								}
								if (isRoom(nb[Dungeon.EAST])) {
									squares[i][j][k].setDoor("EAST", "secret");
								}
								if (isRoom(nb[Dungeon.WEST])) {
									squares[i][j][k].setDoor("WEST", "secret");
								}
							}
							// add normal doors
							else {
								MapSquare nb[] = new MapSquare[8];
								getNeighbors(i, j, k, nb);
								if (isRoom(nb[Dungeon.NORTH])) {
									squares[i][j][k].setNorthDoor(true);
								}
								if (isRoom(nb[Dungeon.SOUTH])) {
									squares[i][j][k].setSouthDoor(true);
								}
								if (isRoom(nb[Dungeon.EAST])) {
									squares[i][j][k].setEastDoor(true);
								}
								if (isRoom(nb[Dungeon.WEST])) {
									squares[i][j][k].setWestDoor(true);
								}

							}
						}
					}
				}
			}
		}
	}

	public void generateBuck() {
		generateBuck(0, null);
	}

	public void generateBuck(int level) {
		generateBuck(level, null);
	}

	public void generateBuck(int level, GenerateDungeonPanel comp) {

		if (level != 0) {
			// initialize the arrays, in case we are re-generating
			init(asizex, asizey, asizez);
		}

		/*
		 * Fill in entrys and exits in all of the levels so that they correspond
		 * correctly.
		 */

		// for the first level, pick a random spot for the entry
		int rx = getRandom(asizex) - 1;
		int ry = getRandom(asizey) - 1;

		// put a staircase there
		squares[rx][ry][0].setType(staircircle);
		visited[rx][ry][0] = 1;
		logger.log("put lvl 1 entry at " + rx + " by " + ry);

		for (int i = 0; i < asizez - 1; i++) {
			// for each level, pick an exit point
			rx = getRandom(asizex) - 1;
			ry = getRandom(asizey) - 1;

			// put a staircase there
			squares[rx][ry][i].setType(Dungeon.staircircle);
			visited[rx][ry][i] = 1;
			logger.log("put lvl " + i + " exit at " + rx + " by " + ry);

			// and put a corresponding staircase on the next level
			// down
			squares[rx][ry][i + 1].setType(Dungeon.staircircle);
			visited[rx][ry][i + 1] = 1;
		}

		// now pick a random spot to start generation
		currX = getRandom(asizex) - 1;
		currY = getRandom(asizey) - 1;
		currZ = getRandom(asizez) - 1;
		while (visited[currX][currY][currZ] == 1) {
			currX = getRandom(asizex) - 1;
			currY = getRandom(asizey) - 1;
			currZ = getRandom(asizez) - 1;
		}

		// mark it visited, and set it to be a corridor.
		visited[currX][currY][currZ] = 1;
		squares[currX][currY][currZ].setType(corridor);
		directionarr[currX][currY][currZ] = 1;

		logger.log("Starting in cell [" + currX + "][" + currY + "][" + currZ
				+ "]");

		// initialize variables for progress reporting
		int totalSquares = asizex * asizey * asizez;
		int progressStep = totalSquares / 20;
		int step = 0;

		// signal the progress monitor that corridor generation is starting
		comp.signal("Generating Random Corridors");

		/*
		 * From the current cell, pick a random direction (north, south, east,
		 * or west). If (1) there is no cell adjacent to the current cell in
		 * that direction, or (2) if the adjacent cell in that direction has
		 * been visited, then that direction is invalid, and you must pick a
		 * different random direction. If all directions are invalid, pick a
		 * different random visited cell in the grid and start this step over
		 * again.
		 */
		while (!isComplete()) {
			step++;
			if (comp != null && step % progressStep == 0) {
				comp.incValue();
			}

			// printDungeon();
			boolean found = getRandAdjacent();

			if (!found) {
				boolean newPosFound = false;
				dead[currX][currY][currZ] = 1;

				while (newPosFound == false) {
					currX = getRandom(asizex) - 1;
					currY = getRandom(asizey) - 1;
					currZ = getRandom(asizez) - 1;

					if (visited[currX][currY][currZ] == 1
							&& dead[currX][currY][currZ] != 1) {
						newPosFound = true;
					}
				}
			} else {

				visited[currX][currY][currZ] = 1;
				directionarr[currX][currY][currZ] = direction;
				squares[currX][currY][currZ].setType(corridor);

				/*
				 * when you fill in a cell, fill in walls around the previous
				 * cell
				 */
				switch (direction) {
				case 1:
					addEastWestWall(currY + 1);
					break;
				case 2:
					addEastWestWall(currY - 1);
					break;
				case 3:
					addNorthSouthWall(currX - 1);
					break;
				case 4:
					addNorthSouthWall(currX + 1);
					break;
				}
			}
		}

		comp.signal("Removing Dead Ends");

		// remove some dead ends by chopping them off
		for (int i = 0; i < sparseness; i++) {
			makeSparse();
		}

		// remove others by connecting them
		removeDeadEnds();

		// remove some dead ends by chopping them off
		for (int i = 0; i < sparseness; i++) {
			makeSparse();
		}

		if (comp != null) {
			comp.setValue(25);
		}

		for (int i = 0; i < asizez; i++) {
			// make the rooms
			makeRooms(i, asizez, numRooms, maxRoomWidth, maxRoomHeight, comp);
		}

		if (comp != null) {
			comp.setValue(95);
		}

		comp.signal("Cleaning Up");

		// evaluate the corridors
		evaluateCorridors();

		// add doors
		addDoors();
		comp.setValue(100);

	}

	public void addNorthSouthWall(int i) {
		if (currY - 1 >= 0 && squares[i][currY - 1][currZ].getType() == init) {
			squares[i][currY - 1][currZ].setType(1);
			visited[i][currY - 1][currZ] = 1;
		}
		if (currY + 1 < asizey
				&& squares[i][currY + 1][currZ].getType() == init) {
			squares[i][currY + 1][currZ].setType(1);
			visited[i][currY + 1][currZ] = 1;
		}
	}

	public void addEastWestWall(int i) {

		if (currX - 1 >= 0 && squares[currX - 1][i][currZ].getType() == init) {
			squares[currX - 1][i][currZ].setType(1);
			visited[currX - 1][i][currZ] = 1;
		}
		if (currX + 1 < asizex
				&& squares[currX + 1][i][currZ].getType() == init) {
			squares[currX + 1][i][currZ].setType(1);
			visited[currX + 1][i][currZ] = 1;
		}
	}

	public void makeRooms(int zlevel, int tlevel, GenerateDungeonPanel comp) {
		makeRooms(zlevel, tlevel, numRooms, maxRoomWidth, maxRoomHeight, comp);
	}

	// build all the rooms
	public void makeRooms(int zlevel, int tlevel, int count, int maxwidth,
			int maxheight, GenerateDungeonPanel comp) {
		int roomCount = count;
		int roomWidth = maxwidth;
		int roomHeight = maxheight;
		int progressAmt = 70 / (count * tlevel);

		for (int i = 0; i < roomCount; i++) {
			if (comp != null){
			comp.signal("Level " + (zlevel + 1) + " : Building room " + (i + 1)
					+ " of " + count);
			}
			int width = getRandom(minRoomWidth, roomWidth);
			int height = getRandom(minRoomHeight, roomHeight);
			placeRoom(width, height, zlevel);
			if (comp != null){
				comp.incValue(progressAmt);
			}
		}
	}

	// place a room on the map
	public void placeRoom(int x, int y, int z) {
		int hiScore = 9999999;
		int bestx = 0;
		int besty = 0;

		// find the best spot for the room. This is done by
		// calculating a "score" for each room, where the lowest
		// score wins.
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				int score = calcScore(i, j, x, y, z);
				if (score < hiScore) {
					hiScore = score;
					bestx = i;
					besty = j;
				}
			}
		}

		addRoom(bestx, besty, z, x, y);

	}

	public void removeRoom(int x, int y, int z) {
		// find the room number
		int roomNumber = squares[x][y][z].getRoomNumber();
		

		// switch all squares in that room to be walls
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					if (squares[i][j][k].getRoomNumber() == roomNumber) {
						squares[i][j][k] = new MapSquare();
						squares[i][j][k].setType(wall);
						squares[i][j][k].setRoomNumber(-1);
					} else if (squares[i][j][k].getRoomNumber() > roomNumber) {
						squares[i][j][k].setRoomNumber(squares[i][j][z]
								.getRoomNumber() - 1);
					}
				}
			}
		}
		

		// remove the room from the room vector
		rooms.removeElementAt(roomNumber);

	}

	public String addRoom(Room r, int x, int y, int z) {
		String errors = null;
		addRoom(x, y, z, r.getWidth(), r.getHeight());
		Room newRoom = (Room) rooms.get(rooms.size() - 1);
		newRoom.setMonsters(r.getMonsters());

		return errors;
	}

	public String getRoomDescription() {
		// add a random room description
		RoomDescriptionDAO rddb = new RoomDescriptionDAO();
		String desc = "";

		// a 1 in 4 chance that there is a lighting description
		if (getRandom(4) == 1) {
			desc += "  " + rddb.getRandomDescription("Lighting");
		}

		// a 1 in 3 chance that there is a wall description
		if (getRandom(3) == 1) {
			desc += "  " + rddb.getRandomDescription("Walls");
		}

		// a 1 in 4 chance that there is a ceiling description
		if (getRandom(4) == 1) {
			desc += "  " + rddb.getRandomDescription("Ceiling");
		}

		// a 1 in 5 chance that there is a smell description
		if (getRandom(5) == 1) {
			desc += "  " + rddb.getRandomDescription("Smell");
		}

		// a 1 in 3 chance that there is a floor description
		if (getRandom(3) == 1) {
			desc += "  " + rddb.getRandomDescription("Floor");
		}

		// a 1 in 8 chance that there is a sound description
		if (getRandom(8) == 1) {
			desc += "  " + rddb.getRandomDescription("Sounds");
		}

		// a 1 in 3 chance that there is a content description
		if (getRandom(3) == 1) {
			desc += "  " + rddb.getRandomDescription("Contents");
		}

		return desc;
	}

	public void addRoom(int x, int y, int z, int width, int height) {

		boolean isConnected = false;

		// create the room
		for (int xpos = x; xpos < x + width; xpos++) {
			for (int ypos = y; ypos < y + height; ypos++) {
				if (bordersCorridor(xpos, ypos, z)) {
					isConnected = true;
				}
				// don't overwrite any stairways.
				if (squares[xpos][ypos][z].getType() != staircircle) {
					if (xpos == x){
						if (ypos == y){
							squares[xpos][ypos][z].setType(tlcorner);
						}else if (ypos == y+height-1){
							squares[xpos][ypos][z].setType(blcorner);
						}else{
							squares[xpos][ypos][z].setType(left);
						}
					}else if (xpos == x+width-1){
						if (ypos == y){
							squares[xpos][ypos][z].setType(trcorner);
						}else if (ypos == y+height-1){
							squares[xpos][ypos][z].setType(brcorner);
						}else{
							squares[xpos][ypos][z].setType(right);
						}
					}else if (ypos == y){
						squares[xpos][ypos][z].setType(top);
					}else if (ypos == y+height-1){
						squares[xpos][ypos][z].setType(bottom);
					}else{
						squares[xpos][ypos][z].setType(room);
					}
					squares[xpos][ypos][z].setRoomNumber(rooms.size());
				}
			}
		}

		String desc = getRoomDescription();

		// if we have an encounter in the room, generate it
		String text = "";
		Vector<String> monsters = null;
		CreateEncounterDialog ced = new CreateEncounterDialog();
		if (randomMonsterTypes != null && randomMonsterTypes.size() > 0) {
			ced.setValidMonsters(randomMonsterTypes);
		}
		if (getRandom(100) <= encounterChance) {

			int partySize = 12;
			int difficulty = getRandom(6) - 1;
			int density = getRandom(5) - 1;
			int densityRange = 5;
			int diversity = getRandom(4) - 1;
			int diversityRange = 4;

			monsters = ced.getRandomEncounter(partyLevel, partySize,
					difficulty, density, densityRange, diversity,
					diversityRange);

		}

		// add it to the room vector, which is used to
		// draw the room numbers
		Room r = new Room(y, y + height, x, x + width, z, "Room "
				+ rooms.size(), text);
		if (monsters != null) {
			r.setMonsters(ced.getMonsterGroups());
		}
		r.setText(desc);
		rooms.addElement(r);
		

		// if the room is not bordered by a corridor (no access to it)
		// tunnel out to one.
		if (!isConnected) {
			// logger.log("TUNNELING FROM ROOM "+rooms.size());
			if (x > asizex / 2) {
				currX = x;
				currY = y + (height / 2);
				currZ = z;
				// logger.log("Going West From "+currX+","+currY);
				makeTunnel(4);
			} else {
				currX = x + width - 1;
				currY = y + (height / 2);
				currZ = z;
				// logger.log("Going East From "+currX+","+currY);
				makeTunnel(3);
			}
		}

	}

	public void updateEncounters() {
		for (int i = 0; i < rooms.size(); i++) {
			updateEncounters((Room) rooms.get(i));
		}
	}

	public void updateEncounters(Room r) {

		CreateEncounterDialog ced = new CreateEncounterDialog();
		if (randomMonsterTypes != null && randomMonsterTypes.size() > 0) {
			ced.setValidMonsters(randomMonsterTypes);
		}
		if (getRandom(100) <= encounterChance) {

			int partySize = 12;
			int difficulty = getRandom(6) - 1;
			int density = getRandom(5) - 1;
			int densityRange = 5;
			int diversity = getRandom(4) - 1;
			int diversityRange = 4;

			ced.getRandomEncounter(partyLevel, partySize, difficulty, density,
					densityRange, diversity, diversityRange);

		}

		r.setMonsters(ced.getMonsterGroups());

	}

	public int calcScore(int i, int j, int x, int y, int z) {

		int score = 0;

		for (int xpos = i; xpos < i + x; xpos++) {
			for (int ypos = j; ypos < j + y; ypos++) {
				if (xpos >= asizex || ypos >= asizey) {
					return 9999999;
				}
				score += scoreCell(xpos, ypos, z);
			}
		}

		return score;
	}

	public int scoreCell(int x, int y, int z) {
		int score = 0;

		if (x + 1 < asizex && squares[x + 1][y][z].isCorridor()) {
			score += 1;
		}
		if (x - 1 >= 0 && squares[x - 1][y][z].isCorridor()) {
			score += 1;
		}
		if (y + 1 < asizey && squares[x][y + 1][z].isCorridor()) {
			score += 1;
		}
		if (y - 1 >= 0 && squares[x][y - 1][z].isCorridor()) {
			score += 1;
		}
		if (x + 1 < asizex && squares[x + 1][y][z].isRoom()) {
			score += 10;
		}
		if (x - 1 >= 0 && squares[x - 1][y][z].isRoom()) {
			score += 10;
		}
		if (y + 1 < asizey && squares[x][y + 1][z].isRoom()) {
			score += 10;
		}
		if (y - 1 >= 0 && squares[x][y - 1][z].isRoom()) {
			score += 10;
		}

		if (squares[x][y][z].isCorridor()) {
			score += 3;
		}
		if (squares[x][y][z].isRoom()) {
			score += 100;
		}
		if (squares[x][y][z].getType() == staircircle) {
			score += 100;
		}
		return score;
	}

	public void printDungeon(int i) {
		StringBuffer row = new StringBuffer();

		for (int y = 0; y < asizey; y++) {
			for (int x = 0; x < asizex; x++) {
				if (squares[x][y][i].getType() == Dungeon.room) {
					row.append(" ");
				} else if (squares[x][y][i].getType() == Dungeon.corridor) {
					row.append(".");
				} else if (squares[x][y][i].getType() == Dungeon.door) {
					row.append("D");
				} else if (squares[x][y][i].getType() == Dungeon.wall) {
					row.append("X");
				} else if (squares[x][y][i].getType() == Dungeon.room) {
					row.append("R");
				} else if (squares[x][y][i].getType() == Dungeon.staircircle) {
					row.append("O");
				} else if (squares[x][y][i].getType() == 16) {
					row.append("n");
				} else if (squares[x][y][i].getType() == 17) {
					row.append("s");
				} else if (squares[x][y][i].getType() == 18) {
					row.append("e");
				} else if (squares[x][y][i].getType() == 19) {
					row.append("w");
				}

				else {
					row.append("" + squares[x][y][i].getType());
				}
			}
			logger.log(row.toString());
			logger.log("\n");
			row = new StringBuffer();
		}
		logger.log("-----------------------------------------------");
		logger.log("\n");
	}

	public static void main(String[] args) {

		int sizex = 60;
		int sizey = 30;
		int sizez = 5;
		@SuppressWarnings("unused")
		int sparseness = 10;

		try {
			if (args.length > 0) {
				sizex = Integer.parseInt(args[0]);
			}
			if (args.length > 1) {
				sizey = Integer.parseInt(args[1]);
			}
			if (args.length > 2) {
				sparseness = Integer.parseInt(args[2]);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (sizex < 5)
			sizex = 5;
		if (sizey < 5)
			sizey = 5;

		// Initialize
		Dungeon m = new Dungeon(sizex, sizey, sizez);

		// generate the map
		m.generateBuck(5);

		for (int i = 0; i < sizez; i++) {
			// print out the dungeon
			m.printDungeon(i);
		}

	}

	// check corridors for what type they are, i.e. intersection, corridor,
	// north-south,
	// etc.
	public boolean isCorridor(MapSquare m) {
		
		return m.getType() == 3 || (m.getType() >= 12 && m.getType() <= 26);
	}

	public boolean isRoom(MapSquare m){
		return (m.getType() == 2 || m.getType() >= 29 && m.getType() < 37);
	}
	
	public void evaluateCorridors() {

		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					if (isCorridor(squares[i][j][k])) {
						evaluateCorridor(i, j, k);
					}
				}
			}
		}
	}

	public void evaluateCorridor(int i, int j, int k) {
		MapSquare nb[] = new MapSquare[8];
		getNeighbors(i, j, k, nb);

		if (isCorridor(nb[NORTH])) {
			if (isCorridor(nb[SOUTH])) {
				if (isCorridor(nb[EAST])) {
					if (isCorridor(nb[WEST])) {
						// four way intersection
						squares[i][j][k].setType(allintersect);
					} else {
						// t section facing east
						squares[i][j][k].setType(eintersect);
					}
				} else {
					if (isCorridor(nb[WEST])) {
						// t section facing west
						squares[i][j][k].setType(wintersect);
					} else {
						// simple north/south corridor
						squares[i][j][k].setType(cnorthsouth);
					}
				}
			} else {
				if (isCorridor(nb[EAST])) {
					if (isCorridor(nb[WEST])) {
						// t section facing north
						squares[i][j][k].setType(nintersect);
					} else {
						// northeast corner
						squares[i][j][k].setType(necorner);
					}
				} else {
					if (isCorridor(nb[WEST])) {
						// northwest corner
						squares[i][j][k].setType(nwcorner);
					} else {
						// dead end
						squares[i][j][k].setType(cnorthsouth);
					}
				}
			}
		} else if (isCorridor(nb[SOUTH])) {
			if (isCorridor(nb[EAST])) {
				if (isCorridor(nb[WEST])) {
					// t section facing south
					squares[i][j][k].setType(sintersect);
				} else {
					// southeast corner
					squares[i][j][k].setType(secorner);
				}
			} else {
				if (isCorridor(nb[WEST])) {
					// southwest corner
					squares[i][j][k].setType(swcorner);
				} else {
					// dead end
					squares[i][j][k].setType(cnorthsouth);
				}
			}
		} else {
			squares[i][j][k].setType(ceastwest);
		}
	}

	public void initializeSquares() {
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					squares[i][j][k] = new MapSquare(i, j, k);
				}
			}
		}
	}

	public void drawLine(int type, int x1, int y1, int x2, int y2, int z) {

		int deltax = Math.abs(x2 - x1); // The difference between the x's
		int deltay = Math.abs(y2 - y1); // The difference between the y's
		int x = x1; // Start x off at the first pixel
		int y = y1; // Start y off at the first pixel
		int num = 0;
		int den = 0;
		int numpixels = 0;
		int xinc1 = 0;
		int yinc1 = 0;
		int numadd = 0;
		int xinc2 = 0;
		int yinc2 = 0;

		if (x2 >= x1) // The x-values are increasing
		{
			xinc1 = 1;
			xinc2 = 1;
		} else // The x-values are decreasing
		{
			xinc1 = -1;
			xinc2 = -1;
		}

		if (y2 >= y1) // The y-values are increasing
		{
			yinc1 = 1;
			yinc2 = 1;
		} else // The y-values are decreasing
		{
			yinc1 = -1;
			yinc2 = -1;
		}

		if (deltax >= deltay) // There is at least one x-value for every
		// y-value
		{
			xinc1 = 0; // Don't change the x when numerator >= denominator
			yinc2 = 0; // Don't change the y for every iteration
			den = deltax;
			num = deltax / 2;
			numadd = deltay;
			numpixels = deltax; // There are more x-values than y-values
		} else // There is at least one y-value for every x-value
		{
			xinc2 = 0; // Don't change the x for every iteration
			yinc1 = 0; // Don't change the y when numerator >= denominator
			den = deltay;
			num = deltay / 2;
			numadd = deltax;
			numpixels = deltay; // There are more y-values than x-values
		}

		for (int curpixel = 0; curpixel <= numpixels; curpixel++) {
			squares[x][y][z].setType(type); // Draw the current pixel
			squares[x][y][z].resetDoors();
			num += numadd; // Increase the numerator by the top of the fraction
			if (num >= den) // Check if numerator >= denominator
			{
				num -= den; // Calculate the new numerator value
				x += xinc1; // Change the x as appropriate
				y += yinc1; // Change the y as appropriate
			}
			x += xinc2; // Change the x as appropriate
			y += yinc2; // Change the y as appropriate
		}
	}

	public void fillArea(int type, int x, int y, int z) {
		int oldType = squares[x][y][z].getType();
		floodFill(type, oldType, x, y, z);
	}

	public void floodFill(int type, int oldType, int x, int y, int z) {

		squares[x][y][z].setType(type);
		squares[x][y][z].resetDoors();

		if (x > 0 && squares[x - 1][y][z].getType() == oldType) {
			floodFill(type, oldType, x - 1, y, z);
		}
		// if (x > 0 && y > 0 && squares[x-1][y-1][z] != null &&
		// squares[x-1][y-1][z].getType() == oldType){
		// floodFill(type, oldType, x-1, y-1, z);
		// }
		if (y > 0 && squares[x][y - 1][z].getType() == oldType) {
			floodFill(type, oldType, x, y - 1, z);
		}
		// if (x < asizex-1 && y > 0 && squares[x+1][y-1][z].getType() ==
		// oldType){
		// floodFill(type, oldType, x+1, y-1, z);
		// }
		if (x < asizex - 1 && squares[x + 1][y][z].getType() == oldType) {
			floodFill(type, oldType, x + 1, y, z);
		}
		// if (x < asizex-1 && y < asizey-1 && squares[x+1][y+1][z].getType() ==
		// oldType){
		// floodFill(type, oldType, x+1, y+1, z);
		// }
		if (y < asizey - 1 && squares[x][y + 1][z].getType() == oldType) {
			floodFill(type, oldType, x, y + 1, z);
		}
		// if (x > 0 && y < asizey-1 && squares[x-1][y+1][z].getType() ==
		// oldType){
		// floodFill(type, oldType, x-1, y+1, z);
		// }
	}

	public void clearPath(ArrayList<Square> path) {
		for (int i = 0; i < asizex; i++) {
			for (int j = 0; j < asizey; j++) {
				for (int k = 0; k < asizez; k++) {
					squares[i][j][k].setMarked(false);
					squares[i][j][k].setConsidered(false);
					squares[i][j][k].setCost(99999999);

				}
			}
		}
		// for (int i = 0; i < path.size(); i++){
		// Square s = (Square)path.get(i);
		// squares[s.getX()][s.getY()][s.getZ()].setMarked(false);
		// }
		pathList = new ArrayList<Path>();
	}

	public void markPath(ArrayList<Square> path) {
		for (int i = 0; i < path.size(); i++) {
			Square s = (Square) path.get(i);
			squares[s.getX()][s.getY()][s.getZ()].setMarked(true);
		}
	}

	public Path findShortestPath(Square a, Square b, Path path) {
		if (squares[b.getX()][b.getY()][b.getZ()].getType() == Dungeon.wall) {
			return null;
		}
		// if we are at the last node in the path, return the path.

		int count = 0;
		while (!hasOneStepPath((Square) path.get(path.size() - 1), b)) {
			count++;

			a = (Square) path.get(path.size() - 1);

			// otherwise, calculate all of the possible paths from here.

			// Look East
			Square eastSquare = a.incX();
			Path eastPath = null;
			if (hasOneStepPath(a, eastSquare)
					&& !path.contains(eastSquare)
					&& squares[eastSquare.getX()][eastSquare.getY()][eastSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[eastSquare.getX()][eastSquare.getY()][eastSquare.getZ()].setConsidered(true);
				squares[eastSquare.getX()][eastSquare.getY()][eastSquare.getZ()]
						.setCost(path.size() + 1);
				eastPath = (Path) (path.clone());
				eastPath.add(eastSquare);
				insertPath(eastPath);
			}

			// Look South
			Square southSquare = a.incY();
			Path southPath = null;
			if (hasOneStepPath(a, southSquare)
					&& !path.contains(southSquare)
					&& squares[southSquare.getX()][southSquare.getY()][southSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[southSquare.getX()][southSquare.getY()][southSquare.getZ()].setConsidered(true);
				squares[southSquare.getX()][southSquare.getY()][southSquare
						.getZ()].setCost(path.size() + 1);
				southPath = (Path) (path.clone());
				southPath.add(southSquare);
				insertPath(southPath);
			}

			// Look West
			Square westSquare = a.decX();
			Path westPath = null;
			if (hasOneStepPath(a, westSquare)
					&& !path.contains(westSquare)
					&& squares[westSquare.getX()][westSquare.getY()][westSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[westSquare.getX()][westSquare.getY()][westSquare.getZ()].setConsidered(true);
				squares[westSquare.getX()][westSquare.getY()][westSquare.getZ()]
						.setCost(path.size() + 1);
				westPath = (Path) (path.clone());
				westPath.add(westSquare);
				insertPath(westPath);
			}

			// Look North
			Square northSquare = a.decY();
			Path northPath = null;
			if (hasOneStepPath(a, northSquare)
					&& !path.contains(northSquare)
					&& squares[northSquare.getX()][northSquare.getY()][northSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[northSquare.getX()][northSquare.getY()][northSquare.getZ()].setConsidered(true);
				squares[northSquare.getX()][northSquare.getY()][northSquare
						.getZ()].setCost(path.size() + 1);
				northPath = (Path) (path.clone());
				northPath.add(northSquare);
				insertPath(northPath);
			}

			// Look up
			Square upSquare = a.decZ();
			Path upPath = null;
			if (hasOneStepPath(a, upSquare)
					&& !path.contains(upSquare)
					&& squares[upSquare.getX()][upSquare.getY()][upSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[upSquare.getX()][upSquare.getY()][upSquare.getZ()].setConsidered(true);
				squares[upSquare.getX()][upSquare.getY()][upSquare.getZ()]
						.setCost(path.size() + 1);
				upPath = (Path) (path.clone());
				upPath.add(upSquare);
				insertPath(upPath);
			}

			// Look down
			Square downSquare = a.incZ();
			Path downPath = null;
			if (hasOneStepPath(a, downSquare)
					&& !path.contains(downSquare)
					&& squares[downSquare.getX()][downSquare.getY()][downSquare
							.getZ()].getCost() > path.size() + 1) {
				// squares[downSquare.getX()][downSquare.getY()][downSquare.getZ()].setConsidered(true);
				squares[downSquare.getX()][downSquare.getY()][downSquare.getZ()]
						.setCost(path.size() + 1);
				downPath = (Path) (path.clone());
				downPath.add(downSquare);
				insertPath(downPath);
			}

			path = (Path) pathList.get(0);
			pathList.remove(0);
		}
		path.add(b);
		return path;
	}

	public void insertPath(Path p) {
		boolean inserted = false;
		for (int i = 0; i < pathList.size(); i++) {
			Path curr = (Path) pathList.get(i);
			if (p.getCost() <= curr.getCost()) {
				pathList.add(i, p);
				inserted = true;
				break;
			}
		}
		if (!inserted) {
			pathList.add(p);
		}
	}

	public boolean hasOneStepPath(Square a, Square b) {

		// if the target square is only one square away in any direction from
		// the
		// start square, and it is not a wall, then return true.
		int x1 = a.getX();
		int x2 = b.getX();
		int y1 = a.getY();
		int y2 = b.getY();
		int z1 = a.getZ();
		int z2 = b.getZ();

		if ((Math.abs(x1 - x2) + Math.abs(y1 - y2) + Math.abs(z1 - z2)) == 1
				&& x2 >= 0 && x2 <= asizex - 1 && y2 >= 0 && y2 <= asizey - 1
				&& z2 >= 0 && z2 <= asizez - 1) {

			MapSquare asq = squares[x1][y1][z1];
			MapSquare bsq = squares[x2][y2][z2];

			return (squares[x2][y2][z2].getType() != Dungeon.wall
					&& !((!asq.isStair() || !bsq.isStair()) && z1 != z2)
					&& !(asq.isCorridor() && bsq.isRoom() && !asq
							.hasDoors()) && !(asq.isRoom()
					&& bsq.isCorridor() && !bsq.hasDoors()));
		}
		return false;

	}

	public void doExtendedVision(int x, int y, int z) {
		for (int i = x - 3; i < x + 4; i++) {
			for (int j = y - 3; j < y + 4; j++) {
				if ((Math.abs(x - i) + Math.abs(y - j) <= 3) && i >= 0
						&& i <= asizex - 1 && j >= 0 && j <= asizey - 1
						&& hasVisiblePath(x, y, i, j, z, 3)) {

					squares[i][j][z].setVisible(true, currentGroup);

				}
			}
		}
	}

	public boolean hasVisiblePath(int x1, int y1, int x2, int y2, int z,
			int length) {

		if (length < 1) {
			return false;
		}

		if (hasOneStepVisiblePath(x1, y1, x2, y2, z)) {
			return true;
		}

		// Look East
		if (hasOneStepVisiblePath(x1, y1, x1 + 1, y1, z)
				&& hasVisiblePath(x1 + 1, y1, x2, y2, z, length - 1)) {
			return true;
		}

		// Look South
		if (hasOneStepVisiblePath(x1, y1, x1, y1 + 1, z)
				&& hasVisiblePath(x1, y1 + 1, x2, y2, z, length - 1)) {
			return true;
		}

		// Look West
		if (hasOneStepVisiblePath(x1, y1, x1 - 1, y1, z)
				&& hasVisiblePath(x1 - 1, y1, x2, y2, z, length - 1)) {
			return true;
		}

		// Look North
		if (hasOneStepVisiblePath(x1, y1, x1, y1 - 1, z)
				&& hasVisiblePath(x1, y1 - 1, x2, y2, z, length - 1)) {
			return true;
		}

		return false;
	}

	public boolean hasOneStepVisiblePath(int x1, int y1, int x2, int y2, int z) {
		// if the target square is only one square away in any direction from
		// the
		// start square, and it is not a wall, then return true.
		return ((Math.abs(x1 - x2) + Math.abs(y1 - y2)) == 1 && x2 >= 0
				&& x2 <= asizex - 1 && y2 >= 0 && y2 <= asizey - 1
				&& squares[x2][y2][z].getType() != Dungeon.wall
				&& !squares[x2][y2][z].isRoom()
				&& !squares[x1][y1][z].isRoom());
	}

	public void getNeighbors(int x, int y, int z, MapSquare[] a) {
		MapSquare offMap = new MapSquare();
		// Fills array of neighboring squares. Off-map = -1
		if (x > 0) {
			if (y > 0)
				a[NORTHWEST] = squares[x - 1][y - 1][z];
			else
				a[NORTHWEST] = offMap;
			if (y < asizey - 1)
				a[SOUTHWEST] = squares[x - 1][y + 1][z];
			else
				a[SOUTHWEST] = offMap;
			a[WEST] = squares[x - 1][y][z];
		} else {
			a[WEST] = offMap;
			a[NORTHWEST] = offMap;
			a[SOUTHWEST] = offMap;
		}
		if (x < asizex - 1) {
			if (y > 0)
				a[NORTHEAST] = squares[x + 1][y - 1][z];
			else
				a[NORTHEAST] = offMap;
			if (y < asizey - 1)
				a[SOUTHEAST] = squares[x + 1][y + 1][z];
			else
				a[SOUTHEAST] = offMap;
			a[EAST] = squares[x + 1][y][z];
		} else {
			a[EAST] = offMap;
			a[NORTHEAST] = offMap;
			a[SOUTHEAST] = offMap;
		}
		if (y > 0)
			a[NORTH] = squares[x][y - 1][z];
		else
			a[NORTH] = offMap;
		if (y < asizey - 1)
			a[SOUTH] = squares[x][y + 1][z];
		else
			a[SOUTH] = offMap;
	}

}
