package initcheck.dungeon;

import initcheck.InitLogger;
import initcheck.MessageDialog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

public class DrawingBoard extends JComponent implements KeyListener,
		MouseInputListener, MouseWheelListener, Printable {

	
	private static final long serialVersionUID = 1L;

	private Dungeon m;

	private Dungeon pasteDungeon;

	private DrawingBoardContainer owner;

	// scaling factor - pixel to square
	int sz = 10;

	private Font numberFont = new Font("SansSerif", Font.BOLD + Font.ITALIC,
			sz + 5);

	public int level = 0;

	public int currentXPosition = 0;

	public int currentYPosition = 0;

	public int currentLevel = 0;

	public int[] undoLevel = new int[1000];

	public int[] undoCurrentXPosition = new int[1000];

	public int[] undoCurrentYPosition = new int[1000];

	public int[] undoCurrentLevel = new int[1000];

	public int clickX = 0;

	public int clickY = 0;

	public int mouseX = 0;

	public int mouseY = 0;

	public int currX = 0;

	public int currY = 0;

	private Square roomCorner1 = null;

	// private Square roomCorner2 = null;
	private DungeonPopupMenu rClickMenu = null;

	private InitLogger logger = new InitLogger(this);

	public static final int CLIENT = 1;

	public static final int SERVER = 2;

	public static final int EDITOR = 3;

	public static final int VIEWER = 4;
	
	static final int NORMAL = 1;

	static final int PATH = 2;

	public static final int INSERT_MAP = 99;

	public static final int MOVE_ROOM = 98;

	public static final int WALL_AREA = 100;

	public boolean update = false;

	public int moveMode = NORMAL;

	public int mode = EDITOR;

	public int drawMode = 1;

	public int oldDrawMode = 1;
	
	private PaletteType paletteType = null;
	
	private int undoIndex = 1;

	RoomEditPanel roomEditor;

	MapNotesDialog md;

	ArrayList<MapListener> listeners = new ArrayList<MapListener>();

	ArrayList<Square> currPath = new ArrayList<Square>();

	ImageIcon icon;

	Image img;

	String drawStyle = "Point";

	// variables for moving a room
	Room moveRoom = null;

	int xOffset = 0;

	int yOffset = 0;

	boolean magnifyMode = false;
	
	public int getOldDrawMode() {
		return oldDrawMode;
	}

	public void setOldDrawMode(int oldDrawMode) {
		this.oldDrawMode = oldDrawMode;
	}

	public ImageIcon getIcon(){
		return icon;
	}
	
	/**
	 * Get the DrawStyle value.
	 * 
	 * @return the DrawStyle value.
	 */
	public String getDrawStyle() {
		return drawStyle;
	}

	/**
	 * Set the DrawStyle value.
	 * 
	 * @param newDrawStyle
	 *            The new DrawStyle value.
	 */
	public void setDrawStyle(String newDrawStyle) {
		this.drawStyle = newDrawStyle;
	}

	public void updateImage() {
		paintImage();
		repaint();
	}

	public void setMapStyle(String mapStyle) {
		if (mapStyle.equals("Square")) {
			m.setStyle(Dungeon.SQUARE);
		} else {
			m.setStyle(Dungeon.HEX);
		}
		resizeImage();

	}

	/**
	 * Get the Mode value.
	 * 
	 * @return the Mode value.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * Set the Mode value.
	 * 
	 * @param newMode
	 *            The new Mode value.
	 */
	public void setMode(int newMode) {
		this.mode = newMode;
	}

	public void resetUndo() {
		undoIndex = 1;
	}

	public void undo() {
		if (mode == EDITOR) {
			Dungeon undo = Dungeon.load("undo/UNDOMAP" + (undoIndex - 1)
					+ ".SER", m.locked);
			if (undo != null) {
				m = undo;
				level = undoLevel[undoIndex - 1];
				currentXPosition = undoCurrentXPosition[undoIndex - 1];
				currentYPosition = undoCurrentYPosition[undoIndex - 1];
				currentLevel = undoCurrentLevel[undoIndex - 1];
				updateImage();
				undoIndex--;
			} else {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog(owner.getFrame(), "Error",
						"No more undo information available.");
			}
		}
	}

	public void setUndo() {
		if (mode == EDITOR) {
			Dungeon.save("undo/UNDOMAP" + undoIndex + ".SER", m);
			undoLevel[undoIndex] = level;
			undoCurrentXPosition[undoIndex] = currentXPosition;
			undoCurrentYPosition[undoIndex] = currentYPosition;
			undoCurrentLevel[undoIndex] = currentLevel;
			undoIndex++;
		}
	}

	/**
	 * Get the MoveMode value.
	 * 
	 * @return the MoveMode value.
	 */
	public int getMoveMode() {
		return moveMode;
	}

	/**
	 * Set the MoveMode value.
	 * 
	 * @param newMoveMode
	 *            The new MoveMode value.
	 */
	public void setMoveMode(int newMoveMode) {
		if (moveMode == PATH) {
			m.clearPath(currPath);
		}
		this.moveMode = newMoveMode;
	}

	public void mergeVisibility(int a, int b) {
		m.mergeVisibility(a, b);
	}

	public void setPasteDungeon(Dungeon d) {
		pasteDungeon = d;
	}

	public void addMapListener(MapListener m) {
		listeners.add(m);
	}

	private void notifyListeners(MapEvent me) {
		String type = me.getEventType();
		for (int i = 0; i < listeners.size(); i++) {
			MapListener ml = (MapListener) listeners.get(i);
			if (type.equals("TimeCount")) {
				ml.updateTimeCount(m.getTimeCount());
				ml.updateTotalTimeCount(m.getTotalTime());
			}
			if (type.equals("MapPosition")) {
				ml.updateMap(me.getXPosition(), me.getYPosition(), level);
			}
			if (type.equals("MapSquare")) {
				ml.updateSquare(me.getCurrentSquare());
			}
			if (type.equals("NewMap")) {
				ml.setMap(m);
			}

		}
	}

	public Square getCurrentPosition() {
		return m.getCurrentPosition();
	}

	public void setCurrentPosition(int x, int y, int level) {
		m.setCurrentPosition(x, y, level);
		setPosition(x, y, level);
	}

	public void showRoomEditor(int i) {
		if (roomEditor == null) {
			roomEditor = new RoomEditPanel(this, m);
			roomEditor.setLocation(300, 200);
		}
		// the room vector is zero based, and the room numbering one based.
		roomEditor.setRoom(i + 1);
		roomEditor.setVisible(true);
	}

	/**
	 * Get the DrawMode value.
	 * 
	 * @return the DrawMode value.
	 */
	public int getDrawMode() {
		return drawMode;
	}

	/**
	 * Set the DrawMode value.
	 * 
	 * @param newDrawMode
	 *            The new DrawMode value.
	 */
	public void setDrawMode(int newDrawMode) {
		oldDrawMode = drawMode;
		this.drawMode = newDrawMode;
	}

	public void setPaletteType(PaletteType pt) {
		this.paletteType = pt;
	}
	
	public JFrame getFrame() {
		return owner.getFrame();
	}

	public DrawingBoard(){
		
	}
	
	public DrawingBoard(DrawingBoardContainer ownerframe) {
		owner = ownerframe;
		md = new MapNotesDialog(this);
		setBackground(Color.black);
		setAutoscrolls(true);

		// add the listener for mouseclicks	
		rClickMenu = new DungeonPopupMenu(this, mode);
	
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);
		setFocusable(true);
		setDoubleBuffered(true);
		setToolTipText("asdf");
	}

	// allow the user to move around the map using the arrow keys, and restrict
	// movement to valid choices (i.e. no walking through walls).
	public void keyPressed(KeyEvent e) {
		if (m.locked || e.getKeyCode() == KeyEvent.VK_M) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				MapSquare newsq = m.squares[currentXPosition - 1][currentYPosition][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentXPosition > 0
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition - 1, currentYPosition);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				MapSquare newsq = m.squares[currentXPosition + 1][currentYPosition][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];

				if (currentXPosition < m.asizex
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition + 1, currentYPosition);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				MapSquare newsq = m.squares[currentXPosition][currentYPosition - 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition > 0
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition, currentYPosition - 1);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				MapSquare newsq = m.squares[currentXPosition][currentYPosition + 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition < m.asizey
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition, currentYPosition + 1);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9 ) {
				MapSquare newsq = m.squares[currentXPosition + 1][currentYPosition - 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition > 0 && currentXPosition < m.asizex
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition+1, currentYPosition - 1);
				}
			}else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
				MapSquare newsq = m.squares[currentXPosition - 1][currentYPosition - 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition > 0 && currentXPosition > 0
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition -1 , currentYPosition - 1);
				}
			}else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				MapSquare newsq = m.squares[currentXPosition - 1][currentYPosition + 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition < m.asizey && currentXPosition > 0
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition -1 , currentYPosition + 1);
				}
			} else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				MapSquare newsq = m.squares[currentXPosition + 1][currentYPosition + 1][level];
				MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
				if (currentYPosition < m.asizey && currentXPosition < m.asizex
						&& newsq.getType() != Dungeon.wall
						&& !(newsq.isRoom()
								&& oldsq.isCorridor() && !oldsq.hasDoors())
						&& !(newsq.isCorridor()
								&& oldsq.isRoom() && !newsq
								.hasDoors())) {
					movePos(currentXPosition +1 , currentYPosition + 1);
				}
			}else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
				if (level < m.asizez) {
					MapSquare newsq = m.squares[currentXPosition][currentYPosition][level + 1];
					MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
					if (newsq.isStair() && oldsq.isStair()) {
						setLevel(level + 1);
						movePos(currentXPosition, currentYPosition);
					}
				}
			} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				if (level > 0) {
					MapSquare newsq = m.squares[currentXPosition][currentYPosition][level - 1];
					MapSquare oldsq = m.squares[currentXPosition][currentYPosition][level];
					if (newsq.isStair() && oldsq.isStair()) {
						setLevel(level - 1);
						movePos(currentXPosition, currentYPosition);
					}
				}
			}

			updateImage();
		}
		//if (e.getKeyCode() == KeyEvent.VK_M) {
		//	magnifyMode = true;
	//	}
	}

	public String getToolTipText(MouseEvent e){
			
		Rectangle r = this.getBounds();
		int fillx = (r.width - (m.asizex * sz)) / 2;
		int filly = (r.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;

		int x = e.getX();
		int y = e.getY();

		// calculate the map square clicked
		if (m.style == Dungeon.SQUARE) {
			x = (x - fillx) / sz;
			y = (y - filly) / sz;
		} else {

			y = new Double(new Double(y - filly).doubleValue()
					/ (new Double(sz).doubleValue() * 0.67)).intValue();

			if (y % 2 == 0) {
				x = ((x - fillx - (sz / 2)) / sz);
			} else {
				x = ((x - fillx) / sz);
			}

		}

		if (x >= 0 && y >= 0 && x < getDungeon().asizex && y < getDungeon().asizey){
			return getDungeon().squares[x][y][level].getToolTip();
		}
		return "";
	}
	
	public void setTimeIncrement(int i) {
		m.setTimeIncrement(i);
		// m.setMappedIncrement(i);
	}

	public double getTimeCount() {
		return m.getTimeCount();
	}

	public void resetTimeCount() {
		m.resetTimeCount();
		notifyListeners(new MapEvent("TimeCount"));
	}

	public void incTimeCount() {
		String inputValue = JOptionPane
				.showInputDialog("Add How Many Minutes?");
		int mins = Integer.parseInt(inputValue);
		m.incTimeCount(60 * mins);
		notifyListeners(new MapEvent("TimeCount"));
	}

	public void decTimeCount() {
		String inputValue = JOptionPane
				.showInputDialog("Remove How Many Minutes?");
		int mins = Integer.parseInt(inputValue);
		m.decTimeCount(60 * mins);
		notifyListeners(new MapEvent("TimeCount"));
	}

	public void movePos(int x, int y) {
		// setUndo();
		// m.visited[x][y][level]=Dungeon.display;
		m.setCurrentPosition(x, y, level);
		setPosition(x, y, level);

		// dont bother with time and visibility if this is the editor.
		//if (mode != EDITOR) {
			if (m.squares[x][y][level].isVisible(getCurrentGroup())) {
				m.incTimeCount(new Double(m.getTimeIncrement()
						* (1 / m.squares[x][y][level].getTimeModifier()))
						.intValue());
			} else {
				if (m.squares[x][y][level].isRoom()) {
					m.incTimeCount(m.getRoomIncrement());
				} else {
					m.incTimeCount(m.getTimeIncrement());
				}
			}

			m.doExtendedVision(x, y, level);
			markVisible(x, y, level);
		//}
			notifyListeners(new MapEvent("TimeCount"));
			notifyListeners(new MapEvent("MapPosition", x, y, level));
			notifyListeners(new MapEvent("MapSquare",
					getDungeon().squares[x][y][level]));
		

	}

	public void updateMap(int x, int y, int level) {
		m.setCurrentPosition(x, y, level);
		setPosition(x, y, level);
		m.doExtendedVision(x, y, level);
		markVisible(x, y, level);
		
		if (((DungeonGUI)owner).offMap()) {
			((DungeonGUI)owner).centerView();
		}
		updateImage();
	}

	public void doExtendedVision(int x, int y, int level) {

		m.doExtendedVision(x, y, level);
	}

	public void keyReleased(KeyEvent e) {
		//magnifyMode = false;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void updateMode() {

	}

	public void setScale(int i) {
		sz = i;
		numberFont = new Font("SansSerif", Font.BOLD + Font.ITALIC, sz + 5);
		resizeImage();
		update(this.getGraphics());
	}

	public int getScale() {
		return sz;
	}

	public boolean clickedOn(int objType) {
		if (getDungeon() != null && objType == Dungeon.secretdoor) {
			return getDungeon().squares[clickX][clickY][level].hasSecretDoors();
		}
		return (getDungeon() != null && getDungeon().squares[clickX][clickY][level]
				.getType() == objType);
	}

	public Square getScreenPos(Square s) {
		Rectangle r = this.getBounds();
		int fillx = (r.width - (m.asizex * sz)) / 2;
		int filly = (r.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;

		// calculate the map square clicked
		int x = (s.getX() * sz + fillx) + (sz / 2);
		int y = (s.getY() * sz + filly) + (sz / 2);

		return new Square(x, y, level);
	}

	public void setClickSquare(MouseEvent e) {
		Rectangle r = this.getBounds();
		int fillx = (r.width - (m.asizex * sz)) / 2;
		int filly = (r.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;

		int x = e.getX();
		int y = e.getY();

		// calculate the map square clicked
		x = (x - fillx) / sz;
		y = (y - filly) / sz;

		clickX = x;
		clickY = y;

	}

	public void setPosition(int x, int y, int z) {
		currentXPosition = x;
		currentYPosition = y;
		currentLevel = z;
		level = z;
	}

	public void addTrap(MapSquare m) {
		@SuppressWarnings("unused")
		TrapDialog td = new TrapDialog(owner.getFrame(), m);
	}

	public void revealDoor() {
		if (getDungeon().squares[clickX][clickY][level].hasNorthDoor()) {
			getDungeon().squares[clickX][clickY][level].getNorthDoor()
					.setSecret(false);
		}
		if (getDungeon().squares[clickX][clickY][level].hasSouthDoor()) {
			getDungeon().squares[clickX][clickY][level].getSouthDoor()
					.setSecret(false);
		}
		if (getDungeon().squares[clickX][clickY][level].hasEastDoor()) {
			getDungeon().squares[clickX][clickY][level].getEastDoor()
					.setSecret(false);
		}
		if (getDungeon().squares[clickX][clickY][level].hasWestDoor()) {
			getDungeon().squares[clickX][clickY][level].getWestDoor()
					.setSecret(false);
		}
		notifyListeners(new MapEvent("NewMap"));
		updateImage();
	}

	public void handleClick(MouseEvent e) {
		
		if (mode == VIEWER){
			return;
		}
		
		setUndo();
		requestFocusInWindow();
		Rectangle r = this.getBounds();
		int fillx = (r.width - (m.asizex * sz)) / 2;
		int filly = (r.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;

		int x = e.getX();
		int y = e.getY();

		// calculate the map square clicked
		if (m.style == Dungeon.SQUARE) {
			x = (x - fillx) / sz;
			y = (y - filly) / sz;
		} else {

			y = new Double(new Double(y - filly).doubleValue()
					/ (new Double(sz).doubleValue() * 0.67)).intValue();

			if (y % 2 == 0) {
				x = ((x - fillx - (sz / 2)) / sz);
			} else {
				x = ((x - fillx) / sz);
			}

		}

		clickX = x;
		clickY = y;

		// make sure it isn't a client or off the map...
		if (mode == CLIENT || x < 0 || x >= m.asizex || y < 0 || y >= m.asizey
				|| e.isPopupTrigger())
			return;

		// if the map is locked, then we want to update the current
		// position of the party on the map, and update the visible
		// portion of the map that will be displayed on the clients.
		if (m.locked) {
			if (moveMode == PATH) {
				System.out.println("BUILDING PATH");
				Square a = new Square(currentXPosition, currentYPosition,
						currentLevel);
				Square b = new Square(x, y, level);
				Path path = new Path();
				path.add(a);
				currPath = (m.findShortestPath(a, b, path)).getNodes();
				
				m.markPath(currPath);
				m.incTimeCount(m.getMappedIncrement() * (currPath.size() - 1));
				notifyListeners(new MapEvent("TimeCount"));
			}

			m.visited[x][y][level] = Dungeon.display;
			m.setCurrentPosition(x, y, level);
			setPosition(x, y, level);

			if (mode != EDITOR){
				m.doExtendedVision(x, y, level);
				markVisible(x, y, level);
			}
				notifyListeners(new MapEvent("MapPosition", x, y, level));
				notifyListeners(new MapEvent("MapSquare",
					getDungeon().squares[x][y][level]));
		
			
		}
		// if we are still editing a map, then we want to check and
		// see what drawing mode we are in to determine what to
		// draw.
		else {
			if (drawMode == MOVE_ROOM || drawMode == INSERT_MAP) {
				switch (drawMode) {
				case MOVE_ROOM:
					String moveErrors = m.addRoom(moveRoom, x + xOffset, y
							+ yOffset, level);
					if (moveErrors != null) {
						@SuppressWarnings("unused")
						MessageDialog dlg = new MessageDialog(owner.getFrame(),
								"Paste Error", moveErrors);
					}
					setDrawMode(((DungeonGUI) owner).getDrawMode());
					break;
				case INSERT_MAP:
					String errors = m.insertMap(pasteDungeon, x, y, level);
					if (errors != null) {
						@SuppressWarnings("unused")
						MessageDialog dlg = new MessageDialog(owner.getFrame(),
								"Paste Error", errors);
					}
					break;
				}
			}
			else if (drawStyle.equals("Point")) {
				switch (drawMode) {
				case -1:
					break;
				case Dungeon.door:
				case Dungeon.secretdoor:
					String type = "normal";
					if (drawMode == Dungeon.secretdoor) {
						type = "secret";
					}
					MapSquare nb[] = new MapSquare[8];
					m.getNeighbors(x, y, level, nb);
					if (isRoom(nb[Dungeon.NORTH]) && 
							nb[Dungeon.NORTH].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
						m.squares[x][y][level].setDoor("NORTH", type);
						Room room = m.getRoom(x, y-1, level);
						room.addDoor(x,y);
					}
					if (isRoom(nb[Dungeon.SOUTH]) && 
							nb[Dungeon.SOUTH].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
						m.squares[x][y][level].setDoor("SOUTH", type);
						Room room = m.getRoom(x, y+1, level);
						room.addDoor(x,y);
					}
					if (isRoom(nb[Dungeon.EAST])&& 
							nb[Dungeon.EAST].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
						m.squares[x][y][level].setDoor("EAST", type);
						Room room = m.getRoom(x+1, y, level);
						room.addDoor(x,y);
					}
					if (isRoom(nb[Dungeon.WEST])&& 
							nb[Dungeon.WEST].getRoomNumber() != m.squares[x][y][level].getRoomNumber()) {
						m.squares[x][y][level].setDoor("WEST", type);
						Room room = m.getRoom(x-1, y, level);
						room.addDoor(x,y);
					}
					break;
				case Dungeon.trap:
					addTrap(m.squares[x][y][level]);
					break;
				case Dungeon.stairnorth:
				case Dungeon.stairsouth:
				case Dungeon.staireast:
				case Dungeon.stairwest:
				case Dungeon.staircircle:
					if (level < m.asizez) {
						m.squares[x][y][level].setType(drawMode);
						m.squares[x][y][level + 1].setType(drawMode);
					}
					break;
				default:
					if (paletteType != null) {
						//m.squares[x][y][level].setImage(paletteType.getMapImage());
						m.squares[x][y][level].setRenderType(paletteType.getRenderType());
						m.squares[x][y][level].setPaletteType(paletteType);
					}
					m.squares[x][y][level].setType(drawMode);
					m.squares[x][y][level].resetDoors();
					if (drawMode == Dungeon.corridor) {
						m.evaluateCorridors();
					}
				}
			} else if (drawStyle.equals("Line")) {

				if (roomCorner1 != null) {

					drawLine(roomCorner1.getX(), roomCorner1.getY(), x, y,
							level);
				} else {

					roomCorner1 = new Square(x, y, level);
				}
			} else if (drawStyle.equals("Area")) {
				if (roomCorner1 != null) {
					if (drawMode == Dungeon.room) {
						addRoom(roomCorner1, new Square(x, y, level));
					} else {
						drawArea(roomCorner1, new Square(x, y, level));
					}
				} else {
					roomCorner1 = new Square(x, y, level);
				}
			} else if (drawStyle.equals("Flood")) {

				m.fillArea(drawMode, x, y, level);
			}
		}

		updateImage();
		setMoveMode(NORMAL);
	}

	public boolean isRoom(MapSquare type){
		return (type.getType() == 2 || type.getType() >= 29 && type.getType() < 37);
	}
	
	
	public void requestTimeUpdate() {
		notifyListeners(new MapEvent("TimeCount"));
	}

	public boolean isStair(int type) {
		return (type >= 5 && type <= 9);
	}

	public void wallArea(Square corner1, Square corner2) {
		drawArea(corner1, corner2);
	}

	public void drawArea(Square corner1, Square corner2) {
		int upperx = -1;
		int uppery = -1;
		int width = -1;
		int height = -1;

		// find the upper left corner
		if (corner1.getX() <= corner2.getX()) {
			upperx = corner1.getX();
			width = corner2.getX() - corner1.getX();
		} else if (corner1.getX() > corner2.getX()) {
			upperx = corner2.getX();
			width = corner1.getX() - corner2.getX();
		}

		if (corner1.getY() <= corner2.getY()) {
			uppery = corner1.getY();
			height = corner2.getY() - corner1.getY();
		} else if (corner1.getY() > corner2.getY()) {
			uppery = corner2.getY();
			height = corner1.getY() - corner2.getY();
		}

		if (upperx > -1 && uppery > -1) {
			for (int i = upperx; i <= upperx + width; i++) {
				for (int j = uppery; j <= uppery + height; j++) {
					if (m.squares[i][j][level].getType() != Dungeon.room) {
						m.squares[i][j][level].setType(drawMode);
						if (paletteType != null) {
						
							m.squares[i][j][level].setRenderType(paletteType.getRenderType());
							m.squares[i][j][level].setPaletteType(paletteType);
						}
					}
				}
			}
		}
		roomCorner1 = null;
	}

	public void resizeRoom() {
		Room room = m.getRoom(clickX, clickY, level);
		removeRoom();
		AddRoomDialog ard = new AddRoomDialog((DungeonGUI) owner);
		ard.setX("" + room.getLeft());
		ard.setY("" + room.getTop());
		ard.setWidth("" + room.getWidth());
		ard.setHeight("" + room.getHeight());
		ard.pack();
		ard.setVisible(true);
	}

	public void moveRoom() {
		moveRoom = m.getRoom(clickX, clickY, level);

		if (moveRoom != null) {
			xOffset = (moveRoom.getTop() - clickY);
			yOffset = (moveRoom.getLeft() - clickX);
			
			setDrawMode(MOVE_ROOM);
			
			removeRoom();
		}
	}

	public Square getCurrSquare() {
		Square s = new Square(currX, currY, level);
		return s;
	}

	public void drawLine(int x1, int y1, int x2, int y2, int level) {
		m.drawLine(drawMode, x1, y1, x2, y2, level);
		roomCorner1 = null;
		if (drawMode == Dungeon.corridor) {
			m.evaluateCorridors();
		}
		updateImage();
	}

	public void fillArea() {

	}

	public void addRoom(Square corner1, Square corner2) {
		int upperx = -1;
		int uppery = -1;
		int width = -1;
		int height = -1;

		// find the upper left corner
		if (corner1.getX() < corner2.getX()) {
			upperx = corner1.getX();
			width = corner2.getX() - corner1.getX();
		} else if (corner1.getX() > corner2.getX()) {
			upperx = corner2.getX();
			width = corner1.getX() - corner2.getX();
		}

		if (corner1.getY() < corner2.getY()) {
			uppery = corner1.getY();
			height = corner2.getY() - corner1.getY();
		} else if (corner1.getY() > corner2.getY()) {
			uppery = corner2.getY();
			height = corner1.getY() - corner2.getY();
		}

		if (upperx > -1 && uppery > -1) {
			m.addRoom(upperx, uppery, corner1.getZ(), width + 1, height + 1);
		}
		roomCorner1 = null;

	}

	public void newDungeon(int x, int y, int z, String type) {
		m = new Dungeon(x, y, z);
		level = 0;
		setLevel(level);
		m.setType(type);
		if (type.equals("Terrain")){
			m.setStyle(Dungeon.HEX);
		}
		resizeImage();
		update(this.getGraphics());
	}

	public void generateDungeon(DungeonVars vars) {

		m = new Dungeon(vars.getCols(), vars.getRows(), vars.getLevels());
		level = 0;
		setLevel(level);
		vars.setCleararea(true);

		m.numRooms = vars.getNumRooms();
		m.maxRoomWidth = vars.getMaxWidth();
		m.maxRoomHeight = vars.getMaxHeight();
		m.minRoomWidth = vars.getMinWidth();
		m.minRoomHeight = vars.getMinHeight();
		m.sparseness = vars.getSparseness();
		m.deadEndChance = vars.getDeadends();
		m.curliness = vars.getCurliness();
		m.encounterChance = vars.getEncounterChance();
		m.partyLevel = vars.getPartyLevel();
		m.setPartySize(vars.getPartySize());

		if (vars.getMonsters() != null) {
			m.setRandomMonsterTypes(vars.getMonsters());
		}
		m.generateBuck(0, vars.getComponent());
		resizeImage();
		updateImage();
		notifyListeners(new MapEvent("NewMap"));
	}

	public Dungeon getDungeon() {
		return m;
	}

	public void markVisible(int x, int y, int level) {
		m.markVisible(x, y, level);
	}

	public void resetVisibility() {
		for (int i = 0; i < m.asizex; i++) {
			for (int j = 0; j < m.asizey; j++) {
				for (int k = 0; k < m.asizez; k++) {
					m.squares[i][j][k].setVisible(false, m.getCurrentGroup());
				}
			}
		}
		updateImage();
	}

	public void randomizeLevel(){
	    
		m.randomizeLevel(level);
		update(this.getGraphics());
		notifyListeners(new MapEvent("NewMap"));
		
	}
	
	public void addLevel() {
		m.addLevel(m.asizez + 1);
		notifyListeners(new MapEvent("NewMap"));
	}

	public void removeLevel(int level){
		m.removeLevel(level);
		notifyListeners(new MapEvent("NewMap"));
	}
	
	public void setDungeon(Dungeon mz) {
		if (mz != null) {
			logger.log("LOADING DUNGEON");
			m = mz;
			Square currPos = mz.getCurrentPosition();
			this.currentXPosition = currPos.getX();
			this.currentYPosition = currPos.getY();
			this.currentLevel = currPos.getZ();

		} else {
			logger.log("error", "NULL DUNGEON");
		}
		updateImage();
	}

	public void setLevel(int lvl) {
		level = lvl;
	}

	public void update(Graphics ig) {

		Graphics2D g = (Graphics2D) ig;
		if (g != null) {
			// fill it in with the background color
			g.fill(new Rectangle(0, 0, getWidth(), getHeight()));

			update = true;
			// build the map
			updateImage();
			update = false;
		}
	}

	/**
	 * Get the CurrentGroup value.
	 * 
	 * @return the CurrentGroup value.
	 */
	public int getCurrentGroup() {
		return m.getCurrentGroup();
	}

	/**
	 * Set the CurrentGroup value.
	 * 
	 * @param newCurrentGroup
	 *            The new CurrentGroup value.
	 */
	public void setCurrentGroup(int newCurrentGroup) {
		m.setCurrentGroup(newCurrentGroup);
	}

	public void repaint() {

		// since paint method draws everything,
		// there is no need to clear drawing area first
		paintComponent(this.getGraphics());
	}

	// we need to override the default method to specify the preferred size,
	// or the scrollbars will not work correctly.
	public Dimension getPreferredSize() {
		if (m == null) {
			return super.getPreferredSize();
		}
		return new Dimension(m.asizex * sz, m.asizey * sz);
	}

	public Point getViewpoint() {

		Rectangle r2 = owner.getDrawingSize();
		int fillx = (r2.width - (m.asizex * sz)) / 2;
		int filly = (r2.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;
		int posx = currentXPosition * sz;
		int posy = currentYPosition * sz;
		int startx = posx - (r2.width / 2);
		int starty = posy - (r2.height / 2);
		return new Point(startx, starty);
	}

	public Point getViewpoint(int posx, int posy) {

		Rectangle r2 = owner.getDrawingSize();
		int fillx = (r2.width - (m.asizex * sz)) / 2;
		int filly = (r2.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;
		
		int startx = posx - (r2.width / 2);
		int starty = posy - (r2.height / 2);
		return new Point(startx, starty);
	}
	
	public void resizeImage() {
		if (m != null) {
			if (m.style == Dungeon.HEX) {

				int width = (m.asizex * sz) + m.asizex;
				int height = new Double(
						new Double(m.asizey * sz).doubleValue() * 0.75)
						.intValue();

				img = createImage(width, height);
			} else {
				img = createImage(m.asizex * sz, m.asizey * sz);
			}

			if (img != null) {
				icon = new ImageIcon(img);
			}
		}
		paintImage();
		repaint();
	}

	public final void paintComponent(Graphics ig) {
	
		Rectangle r2 = owner.getDrawingSize();
	
		// if we don't have an image buffered, create it.
		if (img == null) {
			if (m != null) {
				img = createImage(m.asizex * sz, m.asizey * sz);
			}
		}

		// make sure it is in a paintable form.
		if (img != null && icon == null) {
			icon = new ImageIcon(img);
			paintImage();
		}

		// paint our buffered image on the screen
		if (icon != null) {
			int fillx = (r2.width - (m.asizex * sz)) / 2;
			int filly = (r2.height - (m.asizey * sz)) / 2;

			if (fillx < 0)
				fillx = 0;
			if (filly < 0)
				filly = 0;
			
			icon.paintIcon(this, ig, fillx, filly);
			
			
		}

		// now handle any "overlay" painting, like drag and drop, or bandboxing.
		if (drawStyle.equals("Line") && roomCorner1 != null) {
			Graphics2D g = (Graphics2D) ig;
			g.setColor(Color.white);
			Square s = getScreenPos(roomCorner1);
			g.drawLine(s.getX(), s.getY(), mouseX, mouseY);
		}
		if (drawStyle.equals("Area") && roomCorner1 != null) {
			Graphics2D g = (Graphics2D) ig;
			g.setColor(Color.white);
			Square s = getScreenPos(roomCorner1);
			g.draw(new Rectangle(Math.min(s.getX(), mouseX), Math.min(s.getY(),
					mouseY), Math.abs(mouseX - s.getX()), Math.abs(mouseY
					- s.getY())));
		}
		if (drawMode == MOVE_ROOM) {
			Graphics2D g = (Graphics2D) ig;
			g.setColor(Color.white);
			g.draw(new Rectangle(mouseX + (xOffset * sz), mouseY
					+ (yOffset * sz), moveRoom.getWidth() * sz, moveRoom
					.getHeight()
					* sz));
		}
		if (drawMode == INSERT_MAP) {
			Graphics2D g = (Graphics2D) ig;
			g.setColor(Color.white);
			g.draw(new Rectangle(mouseX, mouseY, pasteDungeon.asizex * sz,
					pasteDungeon.asizey * sz));
		}
	}

	public final void paintImage() {
		paintImage(null);
	}

	public final void paintImage(Graphics ig) {

		if (ig == null) {
			if (icon == null || icon.getImage() == null
					|| icon.getImage().getGraphics() == null) {

				return;
			}
			ig = icon.getImage().getGraphics();
		}

		// don't paint anything if the map is not being displayed. This will
		// avoid redraws if we are on another tab, the window is minimized, etc.
		if (!owner.isMapShowing()) {
			return;
		}

		// cast to a graphics2d object for performance improvements
		Graphics2D g = (Graphics2D) ig;

		// find out how big an area we have to draw in
		Rectangle r = this.getBounds();
		Point p = owner.getViewPosition();

		// make sure that we actually have a map to paint on!
		if (m == null) {
			return;
		}
		
		MapSquare currSquare;
		MapSquareRenderer renderer = new MapSquareRenderer(m);
		
		// draw each square in the map.
		for (int x = 0; x < m.asizex; x++) {
			for (int y = 0; y < m.asizey; y++) {

				currSquare = m.squares[x][y][level];

				renderer.render(g, currSquare, x, y, level, sz, mode,
						currentXPosition, currentYPosition, currentLevel,
						roomCorner1);
			}
		}
				
		// draw the room numbers
		
		g.setFont(numberFont);

		for (int i = 0; i < m.rooms.size(); i++) {
			Room room = (Room) (m.rooms.elementAt(i));
			if (room.level == level
					&& (mode != CLIENT || m.squares[room.getLeft()][room
							.getTop()][level].isVisible(m.getCurrentGroup()))) {

				int x = ((room.left * 2 + (room.right - room.left)) * sz / 2 + sz / 5);
				int y = ((room.top * 2 + (room.bottom - room.top)) * sz / 2 + sz * 4 / 5);

				if (x > p.getX() && y > p.getY() && x < p.getX() + r.width
						&& y < p.getY() + r.height) {

					if (mode != CLIENT){
						if (room.monsters.size() > 0){
							g.setColor(Color.red);
						}else{
							g.setColor(Color.white);
						}
					}else{
						g.setColor(Color.white);
					}
					g.drawString(Integer.toString(i + 1), x, y);
				}
			}
		}
		
		owner.notifyMapRepaint();
	}

	
	public void printMap() {

		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		book.append(this, new PageFormat());
		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);
		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
			}
		}

	}

	public void setMapAsCurrent(){
		((MapImage)owner).setMapAsCurrent(); 
	}
	
	public void setMapLink(){
		
	}
	
	public void showDmNotes(int x, int y, int z) {
		String notes = null;
		notes = m.squares[x][y][z].getDmNotes();

		if (notes == null) {
			notes = "";
		}
		md.setText(notes);
		md.setMode(MapNotesDialog.SERVER);
		md.setPosition(x, y, z);
		md.setVisible(true);

	}

	public void showPlayerNotes(int x, int y, int z) {
		String notes = null;
		notes = m.squares[x][y][z].getNotes();
		if (notes == null) {
			notes = "";
		}

		md.setText(notes);
		md.setMode(MapNotesDialog.CLIENT);
		md.setPosition(x, y, z);
		md.setVisible(true);

	}

	public int print(Graphics g, PageFormat format, int pageIndex) {
		paintComponent(g);

		return Printable.PAGE_EXISTS;
	}

	public void showRoomNotes() {
		if (m.squares[clickX][clickY][level].isRoom()) {
			showRoomEditor(m.squares[clickX][clickY][level].getRoomNumber());
		}
	}

	public void showDmNotes() {
		showDmNotes(clickX, clickY, level);
	}

	public void showPlayerNotes() {
		showPlayerNotes(clickX, clickY, level);
	}

	public void addPlayerNotes(int x, int y, int z, String s) {
		m.squares[x][y][z].setNotes(s);
		updateImage();
	}

	public void addDmNotes(int x, int y, int z, String s) {
		m.squares[x][y][z].setDmNotes(s);
		updateImage();
	}

	public void removeRoom() {
		m.removeRoom(clickX, clickY, level);
		updateImage();
	}

	private void buildPopup(MouseEvent e) {

		if (mode == CLIENT) {
			// if we are on the client, only display a popup for
			// visible fields.
			if (m.squares[clickX][clickY][currentLevel].isVisible(m
					.getCurrentGroup())) {
				logger.log("Creating new popup client");
				rClickMenu = new DungeonPopupMenu(this, mode);
				rClickMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		} else {
			logger.log("Creating new popup server");
			rClickMenu = new DungeonPopupMenu(this, mode);
			rClickMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			
			// if we are in the middle of a multi-click operation, like
			// moving a room, or drawing a line or box, then cancel the
			// operation.
			if (drawStyle.equals("Line") || drawStyle.equals("Area")
					|| drawMode == INSERT_MAP || drawMode == MOVE_ROOM) {
				
				if (drawMode == INSERT_MAP || drawMode == MOVE_ROOM) {
					drawMode = oldDrawMode;
				}
				roomCorner1 = null;
				updateImage();
			}
			// otherwise, show the popup menu
			else {
				setClickSquare(e);
				buildPopup(e);
			}
		} else {
			handleClick(e);
		}
		if (e.getClickCount() == 2) {
			MapSquare s = getDungeon().squares[clickX][clickY][level];
			if (s.isRoom()){
				owner.showRoomInfo(s.getRoomNumber());
			}else{
				mouseX = e.getX();
				mouseY = e.getY();
				owner.showSquare(s);
			}
			// owner.showStatusDialog();
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (m != null && !m.locked) {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		if (drawMode == MOVE_ROOM) {
			handleClick(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (drawStyle.equals("Line") || drawStyle.equals("Area")
				|| drawMode == INSERT_MAP || drawMode == MOVE_ROOM) {

			mouseX = e.getX();
			mouseY = e.getY();
			repaint();
		}

		Rectangle r = this.getBounds();
		int fillx = (r.width - (m.asizex * sz)) / 2;
		int filly = (r.height - (m.asizey * sz)) / 2;
		if (fillx < 0)
			fillx = 0;
		if (filly < 0)
			filly = 0;

		int x = e.getX();
		int y = e.getY();

		// calculate the map square clicked
		x = (x - fillx) / sz;
		y = (y - filly) / sz;
		((DrawingBoardContainer) owner).refreshPositionLabel(x, y, level);

		currX = x;
		currY = y;

	}

	public void mouseDragged(MouseEvent e) {
		if (!m.locked) {
			if (drawMode != MOVE_ROOM) {
				handleClick(e);
				moveRoom();
			}
			mouseMoved(e);
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		if (magnifyMode) {
			
			int scale = e.getWheelRotation();
			if (scale < 1) {
				setScale(sz + 3);
			} else {
				setScale(sz - 3);
			}
		}
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getCurrentXPosition() {
		return currentXPosition;
	}

	public void setCurrentXPosition(int currentXPosition) {
		this.currentXPosition = currentXPosition;
	}

	public int getCurrentYPosition() {
		return currentYPosition;
	}

	public void setCurrentYPosition(int currentYPosition) {
		this.currentYPosition = currentYPosition;
	}

}
