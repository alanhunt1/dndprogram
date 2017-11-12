package initcheck.dungeon;

import initcheck.Encounter;
import initcheck.InitLogger;
import initcheck.InitProgram;
import initcheck.InitServer;
import initcheck.MessageDialog;
import initcheck.MonsterDialog;
import initcheck.MonsterDialogParent;
import initcheck.MonsterGroup;
import initcheck.PanelButton;
import initcheck.SplashScreen;
import initcheck.character.LoadPartyProgressPanel;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * This is the class that actually displays a map. Depending on the mode
 * parameter, it can display the controls to generate a new map or edit an
 * existing one, or it can display the map for use in the game.
 * 
 * @author <a href="mailto:hunt_a@machine.domain">Alan M. Hunt</a>
 * @version 1.0
 */
public class ClientServerDungeonGUI extends TiledPanel implements MapListener,
		ActionListener, DrawingBoardContainer, DungeonGUI{

	private static final long serialVersionUID = 1L;

	// the calling program - either client, server, or map editor
	InitProgram owner = null;

	/** Use with the map editor */
	public static final int GENERATE = 1;

	/** Use with the server */
	public static final int DISPLAY_SERVER = 3;

	/** Use with the client */
	public static final int DISPLAY_CLIENT = 4;

	int mode = GENERATE;

	// Controls
	int level; // the level of the dungeon that we are on

	// the level controls
	PanelButton downButton = new PanelButton("Down");

	PanelButton upButton = new PanelButton("Up");

	PanelButton centerButton = new PanelButton("Center");

	JLabel levelTxt = new JLabel("0");

	JLabel positionLabel = new JLabel("0, 0, 0");

	JComboBox<String> mapScale = new JComboBox<String>();

	// the time controls
	JLabel timeLabel = new JLabel("00:00:00");

	JLabel totalTimeLabel = new JLabel("000 Days 00 Hours");

	PanelButton resetTimeButton = new PanelButton("Rest");

	JComboBox<String> timeIncrement = new JComboBox<String>();

	PanelButton incTimeButton = new PanelButton("+", 20);

	PanelButton decTimeButton = new PanelButton("-", 20);

	// the map display
	DrawingBoard drawingArea = new DrawingBoard(this);
	
	JScrollPane scrollPane = null;	
	
	// the import dungeon to paste in
	Dungeon pastedungeon = null;

	
	ImageIcon backgroundImage;

	ClientSquareInfoPanel csip;

	SquareInfoPanel squareInfo;

	// MapNotesDialog md = new MapNotesDialog(this);
	private InitLogger logger = new InitLogger(this);

	private TiledDialog lpd;

	private MiniMapPanel miniMap = new MiniMapPanel(this);
	
	private boolean showMiniMap = false;
	
	JPanel contents = new JPanel();
	
	JPanel filler2 = new TiledPanel();
	
	MapTypeChooser mapType = new MapTypeChooser();
	
	PalettePanel palette;
	
	String fileName = null;
	
	/**
	 * Creates a new <code>DungeonGUI</code> instance. It takes in the image
	 * icon parameter to pass to its child components.
	 * 
	 * @param owner
	 *            either an InitServer or and InitClient.
	 * @param bg
	 *            an <code>ImageIcon</code> value
	 */
	public ClientServerDungeonGUI(InitProgram owner, ImageIcon bg, int mode) {
		super(bg);
		backgroundImage = bg;
		this.owner = owner;
		this.mode = mode;
		levelTxt.setForeground(Color.white);
		positionLabel.setForeground(Color.white);
		setDoubleBuffered(true);
		csip = new ClientSquareInfoPanel(this, backgroundImage);
		lpd = new TiledDialog(owner.getFrame(), "Importing Dungeon", false);
	}

	public int getPartySize() {

		if (mode == DISPLAY_SERVER) {
			return ((InitServer) owner).getPartySize();
		}

		String inputValue = JOptionPane
				.showInputDialog("How many party members will be in the dungeon?");
		return Integer.parseInt(inputValue);

	}

	public Dungeon getMap(){
		return drawingArea.getDungeon();
	}
	
	/**
	 * just a check to see if the map is being displayed. This is so that we
	 * don't need to draw it when the screen resizes, etc, if it is not.
	 * 
	 * @return true if the map panel is showing, or false if it is not.
	 */
	public boolean isMapShowing() {
		if (owner == null) {
			return false;
		}
		return owner.isMapShowing();
	}

	/**
	 * this sets the map square that gets displayed in the square info panel
	 * when in use mode.
	 * 
	 * @param m
	 *            The new map square to display in the square info panel
	 */
	public void updateSquare(MapSquare m) {
		
		squareInfo.setMapSquare(m);
		csip.setMapSquare(m);
		
		if (m.getTrap() != null){
			m.setVisited(true);
			owner.playSound("trap.wav");
			Vector<String> filenames = new Vector<String>();
			filenames.add("images/sandclarge.jpg");
		
			SplashScreen ss = new SplashScreen(owner.getFrame());
			ss.display(filenames, 3000);
		}
	}

	public void setPartyLevel(double i) {
		drawingArea.getDungeon().setPartyLevel(i);
	}

	/**
	 * update the time count label with the current number of seconds of time
	 * elapsed since the party last rested.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void updateTimeCount(int i) {
		int hours = i / (60 * 60);
		int mins = (i % (60 * 60)) / 60;
		int secs = i % 60;
		String label = "";
		if (hours < 10) {
			label += "0";
		}
		label += hours + ":";
		if (mins < 10) {
			label += "0";
		}
		label += mins + ":";
		if (secs < 10) {
			label += "0";
		}
		label += secs;
		timeLabel.setText(label);
		
		// if we are the server, send the time to the client
		if (mode == DISPLAY_SERVER){
			owner.updateTime(i);
		}
	}

	/**
	 * update the total time cound label with the number of seconds elapsed in
	 * the entire adventure.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void updateTotalTimeCount(int i) {
		int days = i / (60 * 60 * 24);
		int hours = i % (60 * 60 * 24) / (60 * 60);
		int mins = (i % (60 * 60)) / 60;
		int secs = i % 60;
		String label = "";
		if (days < 100) {
			label += "0";
		}
		if (days < 10) {
			label += "0";
		}
		label += days + " Days ";
		if (hours < 10) {
			label += "0";
		}
		label += hours + ":";
		if (mins < 10) {
			label += "0";
		}
		label += mins + ":";
		if (secs < 10) {
			label += "0";
		}
		label += secs;

		// label += hours + " Hours";
		totalTimeLabel.setText(label);
		
		// if we are the server, send the time to the client
		if (mode == DISPLAY_SERVER){
			owner.updateTotalTime(i);
		}
	}

	/**
	 * Describe <code>updateEncounters</code> method here.
	 * 
	 * @param level
	 *            a <code>double</code> value
	 */
	public void updateEncounters(double level) {
		drawingArea.getDungeon().setPartyLevel(level);
		drawingArea.getDungeon().updateEncounters();
	}

	/**
	 * Describe <code>runEncounter</code> method here.
	 * 
	 * @param m
	 *            a <code>Vector</code> value
	 */
	public void runEncounter(Vector<MonsterGroup> m) {
		((InitServer) owner).runEncounter(new Encounter(m), true);
	}

	/**
	 * Describe <code>showMonsterDialog</code> method here.
	 * 
	 * @param m
	 *            a <code>Vector</code> value
	 */
	public void showMonsterDialog(Room r) {
		MonsterDialog md = new MonsterDialog((MonsterDialogParent) owner);
		Encounter e = new Encounter();
		e.setMonsterGroups(r.getMonsters());
		md.setEncounter(e);
		md.setPartyLevel(drawingArea.getDungeon().getPartyLevel());
		md.setRoom(r);
		md.pack();
		md.setVisible(true);
	}

	public void generateDescription(Room r) {
		r.setText(drawingArea.getDungeon().getRoomDescription());
	}

	/**
	 * This method is used to switch to the main battle tab when the "run
	 * encounter" button is used from the dungeon control panel. It calls on the
	 * server to switch from the map tab to the battle list tab. Note that this
	 * call can only be used if the owner is the server, not the client or
	 * mapper.
	 * 
	 */
	public void showListTab() {
		((InitServer) owner).showListTab();
	}

	/**
	 * Describe <code>getViewPosition</code> method here.
	 * 
	 * @return a <code>Point</code> value
	 */
	public Point getViewPosition() {
		if (scrollPane != null) {
			JViewport vp = scrollPane.getViewport();
			if (vp != null) {
				return vp.getViewPosition();
			}
		}
		return new Point(0, 0);
	}

	/**
	 * This returns the parent component frame, for use in passing to dialogs or
	 * subframes.
	 * 
	 * @return a <code>JFrame</code> value
	 */
	public JFrame getFrame() {
		if (owner != null) {
			return owner.getFrame();
		}
		return null;
	}

	/**
	 * Describe <code>getDrawingSize</code> method here.
	 * 
	 * @return a <code>Rectangle</code> value
	 */
	public Rectangle getDrawingSize() {
		if (scrollPane != null) {
			JViewport vp = scrollPane.getViewport();
			if (vp != null) {
				return vp.getViewRect();
			}
		}
		
		return new Rectangle();
	}

	/**
	 * Describe <code>centerView</code> method here.
	 * 
	 */
	public void centerView() {
		if (scrollPane != null) {
			JViewport vp = scrollPane.getViewport();
			if (vp != null) {

				vp.setViewPosition(drawingArea.getViewpoint());
			}
		}
	}

	public void setView(int posx, int posy) {
		if (scrollPane != null) {
			JViewport vp = scrollPane.getViewport();
			if (vp != null) {

				vp.setViewPosition(drawingArea.getViewpoint(posx, posy));
			}
		}
	}
	
	public boolean offMap() {
		if (scrollPane != null) {
			JViewport vp = scrollPane.getViewport();
			Rectangle r = vp.getViewRect();
			if (!r.contains(drawingArea.currentXPosition * drawingArea.sz, drawingArea.currentYPosition * drawingArea.sz)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public void setFont(Font f) {
		if (csip != null) {
			csip.setFont(f);
		}
	}

	/**
	 * Describe <code>getDrawingBoard</code> method here.
	 * 
	 * @return a <code>DrawingBoard</code> value
	 */
	public DrawingBoard getDrawingBoard() {
		return drawingArea;
	}

	/**
	 * Describe <code>addDmNotes</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 * @param s
	 *            a <code>String</code> value
	 */
	public void addDmNotes(int x, int y, int z, String s) {
		if (s.equals("")) {
			drawingArea.getDungeon().squares[x][y][z].setDmNotes(null);
		} else {
			drawingArea.getDungeon().squares[x][y][z].setDmNotes(s);
		}
		drawingArea.repaint();
	}

	/**
	 * Describe <code>addPlayerNotes</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 * @param s
	 *            a <code>String</code> value
	 */
	public void addPlayerNotes(int x, int y, int z, String s) {
		if (s.equals("")) {
			drawingArea.getDungeon().squares[x][y][z].setNotes(null);
		} else {
			drawingArea.getDungeon().squares[x][y][z].setNotes(s);
		}
		drawingArea.repaint();

		owner.sendPlayerNotes(x, y, z, s);

	}

	/**
	 * Describe <code>setPlayerNotes</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 * @param s
	 *            a <code>String</code> value
	 */
	public void setPlayerNotes(int x, int y, int z, String s) {
		if (s.equals("")) {
			drawingArea.getDungeon().squares[x][y][z].setNotes(null);
		} else {
			drawingArea.getDungeon().squares[x][y][z].setNotes(s);
		}
		drawingArea.repaint();
	}

	/**
	 * Describe <code>updateMap</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 */
	public void updateMap(int x, int y, int z) {
		// if we are a client, then we want to draw the update
		if (mode == DISPLAY_CLIENT) {
			drawingArea.updateMap(x, y, z);
			setPosition(x, y, z);
			updateSquare(drawingArea.getDungeon().squares[x][y][z]);
			
		}
		// if we are a server, we want to send the update to the clients
		else {
			owner.updateMap(x, y, z);
			setPosition(x, y, z);
		}
	}

	public void updateMap() {
		if (mode == DISPLAY_CLIENT || mode == DISPLAY_SERVER) {
			drawingArea.updateMap(drawingArea.getCurrentXPosition(), drawingArea.getCurrentYPosition(), drawingArea.getCurrentLevel());
			setPosition(drawingArea.getCurrentXPosition(), drawingArea.getCurrentYPosition(), drawingArea.getCurrentLevel());
			updateSquare(drawingArea.getDungeon().squares[drawingArea.getCurrentXPosition()][drawingArea.getCurrentYPosition()][drawingArea.getCurrentLevel()]);
			
		}
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
	 * Describe <code>setPosition</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 */
	public void setPosition(int x, int y, int z) {

		positionLabel.setText(pad("" + x, 3) + "," + pad("" + y, 3) + ","
				+ pad("" + z, 3));
		enableButtons();
	}

	public void refreshPositionLabel(int x, int y, int z) {
		positionLabel.setText(pad("" + x, 3) + "," + pad("" + y, 3) + ","
				+ pad("" + z, 3));
	}

	public String pad(String s, int l) {

		if (s == null) {
			s = "";
		}

		if (s.length() > l) {
			return s.substring(0, l);
		}

		char[] chars = new char[l];
		for (int i = 0; i < s.length(); i++) {
			chars[i] = s.charAt(i);
		}
		for (int i = s.length(); i < l; i++) {
			chars[i] = ' ';
		}
		return new String(chars);
	}

	/**
	 * Set the Mode value.
	 * 
	 * @param newMode
	 *            The new Mode value.
	 */
	public void setMode(int newMode) {
		this.mode = newMode;
		if (newMode == DISPLAY_CLIENT) {
			drawingArea.setMode(DrawingBoard.CLIENT);
		}
	}

	public void resetVisibility() {
		drawingArea.resetVisibility();
	}

	/**
	 * Describe <code>loadPartyMap</code> method here.
	 * 
	 */
	public void loadPartyMap() {
		File f = new File("saves/" + owner.getParty() + "map.ser");
		if (f.exists()) {
			loadMap("saves/" + owner.getParty() + "map.ser");
		}
	}

	/**
	 * Describe <code>isLocked</code> method here.
	 * 
	 * @return a <code>boolean</code> value
	 */
	public boolean isLocked() {
		if (drawingArea != null) {
			if (drawingArea.getDungeon() != null) {
				return drawingArea.getDungeon().locked;
			}
		}
		return true;
	}

	/**
	 * Describe <code>getRoom</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 * @return a <code>Room</code> value
	 */
	public Room getRoom(int i) {
		if (drawingArea != null && drawingArea.getDungeon() != null) {
			return drawingArea.getDungeon().getRoom(i);
		}
		return null;
	}

	public Vector<Room> getRooms(){
		if (drawingArea != null && drawingArea.getDungeon() != null) {
			return drawingArea.getDungeon().getRooms();
		}
		return new Vector<Room>();
	}
	
	public void showRoomInfo(int i){
		
	}
	
	public void showSquare(MapSquare i){
		
	}
	
	/**
	 * Describe <code>lockDungeon</code> method here.
	 * 
	 */
	public void lockDungeon() {
		drawingArea.getDungeon().locked = true;
	}

	/**
	 * Describe <code>unlockDungeon</code> method here.
	 * 
	 */
	public void unlockDungeon() {
		drawingArea.getDungeon().locked = false;

	}

	/**
	 * Describe <code>loadMap</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void loadMap(String s) {
		logger.log("LOAD MAP");
		fileName = s;
		setTitle(fileName);
		if (mode == DISPLAY_SERVER || mode == DISPLAY_CLIENT){
			drawingArea.setDungeon(Dungeon.load(s, true));
		}else{
			drawingArea.setDungeon(Dungeon.load(s, false));
		}
		if (drawingArea.getDungeon() == null) {
			logger.log("Unable to load " + s);
			fileName = null;
			setTitle("");
		}
		loaded();
	}

	/**
	 * Describe <code>addLevel</code> method here.
	 * 
	 */
	public void addLevel() {
		drawingArea.addLevel();
	}

	public void randomizeLevel(){
		drawingArea.randomizeLevel();
	}
	
	public void removeLevel(int level){
		drawingArea.removeLevel(level);
	}
	
	/**
	 * Describe <code>init</code> method here.
	 * 
	 */
	public void init() {

		squareInfo = new SquareInfoPanel(this, backgroundImage);
		mapScale.addItem("3");
		mapScale.addItem("6");
		mapScale.addItem("9");
		mapScale.addItem("12");
		mapScale.addItem("15");
		mapScale.addItem("21");
		mapScale.addItem("24");
		mapScale.addItem("30");
		mapScale.addItem("45");
		mapScale.addItem("60");
		mapScale.addItem("99");

		mapScale.addActionListener(this);

		timeIncrement.addItem("6 Sec.");
		timeIncrement.addItem("1 Min.");
		timeIncrement.addItem("3 Min.");
		timeIncrement.addItem("10 Min.");
		timeIncrement.addItem("30 Min.");
		timeIncrement.setSelectedIndex(1);
		
		

		Font titleFont = new Font("SansSerif", Font.BOLD, 18);
		logger.log("init dungeon");
		drawingArea.setDungeon(new Dungeon(50, 50, 1));
		drawingArea.setLevel(0);

		scrollPane = new JScrollPane(drawingArea);

		TiledPanel frameControls = new TiledPanel(backgroundImage);

		frameControls.setLayout(new FlowLayout(FlowLayout.LEFT));
		// Create controls
		JLabel levelLabel = new JLabel("Level");
		levelLabel.setFont(titleFont);
		levelLabel.setForeground(Color.white);
		timeLabel.setFont(titleFont);
		totalTimeLabel.setFont(titleFont);
		timeLabel.setForeground(Color.white);
		totalTimeLabel.setForeground(Color.white);
		frameControls.add(levelLabel);
		frameControls.add(levelTxt);

		levelTxt.setFont(titleFont);
		frameControls.add(downButton);
		frameControls.add(upButton);
		positionLabel.setFont(new Font("Courier", Font.BOLD, 18));
		frameControls.add(positionLabel);
		frameControls.add(mapScale);
		frameControls.add(centerButton);

		JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel spacerPanel = new JPanel();
		spacerPanel.add(new JLabel("        "));
		spacerPanel.setOpaque(false);
		if (mode != DISPLAY_CLIENT){
		timePanel.add(timeIncrement);
		timePanel.add(incTimeButton);
		timePanel.add(decTimeButton);
		}
		timePanel.add(timeLabel);
		if (mode != DISPLAY_CLIENT){
		timePanel.add(resetTimeButton);
		}
		timePanel.add(totalTimeLabel);
		timePanel.setOpaque(false);
		frameControls.add(spacerPanel);
		frameControls.add(timePanel);
		frameControls.setOpaque(false);
		contents.setLayout(new BorderLayout());

		contents.add(frameControls, BorderLayout.SOUTH);
		
		
		contents.add(scrollPane, BorderLayout.CENTER);
		//TiledPanel spacer = new TiledPanel();
		//spacer.add(new JLabel("  "));
		//spacer.setOpaque(false);
		//contents.add(spacer, BorderLayout.EAST);
		filler2.setLayout(new BorderLayout());
		
		if (mode != DISPLAY_CLIENT) {
			contents.add(squareInfo, BorderLayout.WEST);
			contents.add(filler2, BorderLayout.EAST);
		} else {
			contents.add(csip, BorderLayout.NORTH);
			contents.add(filler2, BorderLayout.EAST);
			
		}
		
		
		drawingArea.addMapListener(this);
		
		mapType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMapType();
			}
		});
			
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level < drawingArea.getDungeon().asizez - 1)
					level++;
				drawingArea.setLevel(level);
				enableButtons();
				drawingArea.resizeImage();
			}
		});

		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (level > 0)
					level--;
				drawingArea.setLevel(level);
				enableButtons();
				drawingArea.resizeImage();
			}
		});

		centerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				centerView();
			}
		});

		resetTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingArea.resetTimeCount();
			}
		});

		incTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingArea.incTimeCount();
			}
		});

		decTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingArea.decTimeCount();
			}
		});

		mapScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMapScale(Integer.parseInt((String) mapScale
						.getSelectedItem()));
			}
		});

		timeIncrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTimeIncrement((String) timeIncrement.getSelectedItem());
			}
		});

		enableButtons();

		setLayout(new BorderLayout());
		add(contents, BorderLayout.CENTER);
	}

	public void setMapType(){
		
		String type = (String)mapType.getSelectedItem();
		palette.changePalette(type);
	}
	
	public void toggleMiniMap(){
		
		contents.invalidate();
		if (showMiniMap) {
			
			filler2.removeAll();

			showMiniMap = false;
		} else {
			
			filler2.add(miniMap, BorderLayout.CENTER);
			showMiniMap = true;
		}
		notifyMapRepaint();
		contents.validate();
		
		repaint();
	}
	
	public void setTimeIncrementWidget(int i){
		if (i == 6) {
			timeIncrement.setSelectedIndex(0);
		} else if (i == 60) {
			timeIncrement.setSelectedIndex(1);
		} else if (i == 180) {
			timeIncrement.setSelectedIndex(2);
		} else if (i == 600) {
			timeIncrement.setSelectedIndex(3);
		} else if (i == 1800) {
			timeIncrement.setSelectedIndex(4);
		} else{
			timeIncrement.setSelectedIndex(0);
		}
	}
	
	/**
	 * Describe <code>setTimeIncrement</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void setTimeIncrement(String s) {

		if (s.equals("6 Sec.")) {
			drawingArea.setTimeIncrement(6);
		} else if (s.equals("1 Min.")) {
			drawingArea.setTimeIncrement(60);
		} else if (s.equals("3 Min.")) {
			drawingArea.setTimeIncrement(180);
		} else if (s.equals("10 Min.")) {
			drawingArea.setTimeIncrement(600);
		} else if (s.equals("30 Min.")) {
			drawingArea.setTimeIncrement(1800);
		} else {
			logger.log("INVALID TIME INCREMENT " + s);
		}
	}

	/**
	 * Describe <code>sendMap</code> method here.
	 * 
	 */
	public void sendMap() {
		setMap(drawingArea.getDungeon());
	}

	/**
	 * Describe <code>setMap</code> method here.
	 * 
	 * @param d
	 *            a <code>Dungeon</code> value
	 */
	public void setMap(Dungeon d) {
		owner.setMap(d);
		enableButtons();
	}

	/**
	 * Get the CurrentGroup value.
	 * 
	 * @return the CurrentGroup value.
	 */
	public int getCurrentGroup() {
		return drawingArea.getCurrentGroup();
	}

	/**
	 * Set the CurrentGroup value.
	 * 
	 * @param newCurrentGroup
	 *            The new CurrentGroup value.
	 */
	public void setCurrentGroup(int newCurrentGroup) {
		drawingArea.setCurrentGroup(newCurrentGroup);
		
	}

	/**
	 * Describe <code>getCurrentPosition</code> method here.
	 * 
	 * @return a <code>Square</code> value
	 */
	public Square getCurrentPosition() {
		return drawingArea.getCurrentPosition();
	}

	/**
	 * Describe <code>setCurrentPosition</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param level
	 *            an <code>int</code> value
	 */
	public void setCurrentPosition(int x, int y, int level) {
		drawingArea.setCurrentPosition(x, y, level);
	}

	/**
	 * Describe <code>mergeVisibility</code> method here.
	 * 
	 * @param a
	 *            an <code>int</code> value
	 * @param b
	 *            an <code>int</code> value
	 */
	public void mergeVisibility(int a, int b) {
		drawingArea.mergeVisibility(a, b);
	}

	/**
	 * Describe <code>setMapScale</code> method here.
	 * 
	 * @param i
	 *            an <code>int</code> value
	 */
	public void setMapScale(int i) {
		drawingArea.setScale(i);
		scrollPane.setViewportView(drawingArea);

		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Describe <code>printMap</code> method here.
	 * 
	 */
	public void printMap() {
		drawingArea.printMap();
	}

	/**
	 * Describe <code>saveMap</code> method here.
	 * 
	 */
	public void saveMap() {
		File f = new File("saves/" + owner.getParty() + "map.ser");
		Dungeon.save(f.getPath(), drawingArea.getDungeon());
		File f2 = new File("saves/" + owner.getParty() + "map.xml");
		Dungeon.export(f2.getPath(), drawingArea.getDungeon());
	}

	public void notifyMapRepaint(){
		if (showMiniMap){
			
			miniMap.setIcon(drawingArea.getIcon());
			miniMap.updateImage();
		}
	}
	
	
	/**
	 * Describe <code>loadDungeon</code> method here.
	 * 
	 */
	public void loadDungeon(String filename) {
			fileName = filename;
			setTitle(fileName);
			Dungeon d = Dungeon.load(filename);

			if (d != null) {
				logger.log("Load Dungeon!");
				drawingArea.setDungeon(d);
				loaded();
			} else {
				@SuppressWarnings("unused")
				MessageDialog mesg = new MessageDialog(owner.getFrame(),
						"Load Error", Dungeon.getLoadError());

			}
		
	}
	
	/**
	 * Describe <code>loadDungeon</code> method here.
	 * 
	 */
	public void loadDungeon() {
		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(owner.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fc.getSelectedFile().getAbsolutePath();
			fileName = filename;
			setTitle(fileName);
			Dungeon d = Dungeon.load(filename);

			if (d != null) {
				logger.log("Load Dungeon!");
				drawingArea.setDungeon(d);
				loaded();
			} else {
				@SuppressWarnings("unused")
				MessageDialog mesg = new MessageDialog(owner.getFrame(),
						"Load Error", Dungeon.getLoadError());

			}
		}
	}

	/**
	 * Describe <code>importDungeon</code> method here.
	 * 
	 */
	public void importDungeon() {

		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(owner.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fc.getSelectedFile().getAbsolutePath();
			Dungeon d = Dungeon.load(filename);
			if (d == null) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog(getFrame(), "Load Error",
						Dungeon.loadError);
			} else {
				drawingArea.setPasteDungeon(d);
				drawingArea.setDrawMode(DrawingBoard.INSERT_MAP);
			}
		}
	}

	private void setTitle(String s){
		owner.setTitle(s);
	}
	
	
	public void saveDungeon(){
		saveDungeon("");
	}
	
	/**
	 * Describe <code>saveDungeon</code> method here.
	 * 
	 * @param s
	 *            a <code>String</code> value
	 */
	public void saveDungeon(String s) {
		drawingArea.getDungeon().name = s;

		if (fileName == null){
			JFileChooser fc = new JFileChooser("saves");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(owner.getFrame());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filename = fc.getSelectedFile().getAbsolutePath();
				Dungeon.save(filename, drawingArea.getDungeon());
				fileName = filename;
				setTitle(fileName);
			}
		} else {
			Dungeon.save(fileName, drawingArea.getDungeon());
		}
	}

	public void importMap() {
		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(owner.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fc.getSelectedFile().getAbsolutePath();
			File f = new File(filename);
			int modLength = new Long(f.length()).intValue();

			LoadPartyProgressPanel lpp = new LoadPartyProgressPanel(modLength,
					lpd);
			lpd.setMainWindow(lpp);
			lpd.pack();
			lpd.setVisible(true);

			Dungeon d = Dungeon.importDungeon(filename, lpp, modLength);
			lpd.setVisible(false);
			if (d == null) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog(getFrame(), "Load Error",
						Dungeon.loadError);
			} else {
				logger.log("Importing Map");
				drawingArea.setDungeon(d);
				loaded();
			}
		}
	}

	public void exportMap() {

		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(owner.getFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String filename = fc.getSelectedFile().getAbsolutePath();
			Dungeon.export(filename, drawingArea.getDungeon());
		}
	}

	/**
	 * Describe <code>setDrawMode</code> method here.
	 * 
	 * @param m
	 *            an <code>int</code> value
	 */
	public void setDrawMode(int m) {
		
		drawingArea.setDrawMode(m);
	}

	public void setPaletteType(PaletteType pt) {
		
		drawingArea.setPaletteType(pt);
	}
	
	public void setDrawStyle(String s) {
		drawingArea.setDrawStyle(s);
	}

	public String getDrawStyle() {
		return drawingArea.getDrawStyle();
	}
	
	public void setMapStyle(String s) {
		drawingArea.setMapStyle(s);
	}

	/**
	 * Describe <code>getDrawMode</code> method here.
	 * 
	 * @return an <code>int</code> value
	 */
	public int getDrawMode() {
		return 0;
	}

	/**
	 * Describe <code>setMoveMode</code> method here.
	 * 
	 * @param m
	 *            an <code>int</code> value
	 */
	public void setMoveMode(int m) {
		drawingArea.setMoveMode(m);
	}

	public void undo() {
		drawingArea.undo();
	}

	private void loaded() {
		
		enableButtons();
		if (drawingArea.getGraphics() == null){
			logger.log("error", "Graphics was null!");
		}
		drawingArea.update(drawingArea.getGraphics());
		
		
		scrollPane.setViewportView(drawingArea);

		setMap(drawingArea.getDungeon());
		drawingArea.requestTimeUpdate();
		drawingArea.resizeImage();
		setTimeIncrementWidget(drawingArea.getDungeon().getTimeIncrement());
		
		// and revalidate so it gets redrawn
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Describe <code>enableButtons</code> method here.
	 * 
	 */
	public void enableButtons() {
		// Update controls
		if (level == 0)
			upButton.setEnabled(false);
		if (level < drawingArea.getDungeon().asizez - 1)
			downButton.setEnabled(true);
		if (level >= drawingArea.getDungeon().asizez - 1)
			downButton.setEnabled(false);
		if (level > 0)
			upButton.setEnabled(true);

		levelTxt.setText(Integer.toString(level));

	}

	public void drawLine(int x, int y, int x2, int y2) {
		drawingArea.drawLine(x, y, x2, y2, level);
	}

	public void addRoom(int x, int y, int width, int height) {
		Square corner1 = new Square(x, y, level);
		Square corner2 = new Square(x + width - 1, y + height - 1, level);
		drawingArea.addRoom(corner1, corner2);
		drawingArea.updateImage();
	}

	/**
	 * Describe <code>newDungeon</code> method here.
	 * 
	 * @param x
	 *            an <code>int</code> value
	 * @param y
	 *            an <code>int</code> value
	 * @param z
	 *            an <code>int</code> value
	 */
	public void newDungeon(int x, int y, int z, String type) {
		// if we are creating a new dungeon, zero out the filename to avoid saving
		// over the current one.
		fileName = null;
		
		drawingArea.newDungeon(x, y, z, type);
		mapType.setSelectedItem(type);
		//palette.changePalette(type);
		enableButtons();
	}

	/**
	 * Describe <code>generateDungeon</code> method here.
	 * 
	 * @param rows
	 *            an <code>int</code> value
	 * @param cols
	 *            an <code>int</code> value
	 * @param levels
	 *            an <code>int</code> value
	 * @param numRooms
	 *            an <code>int</code> value
	 * @param curliness
	 *            an <code>int</code> value
	 * @param maxHeight
	 *            an <code>int</code> value
	 * @param maxWidth
	 *            an <code>int</code> value
	 * @param minHeight
	 *            an <code>int</code> value
	 * @param minWidth
	 *            an <code>int</code> value
	 * @param sparseness
	 *            an <code>int</code> value
	 * @param deadends
	 *            an <code>int</code> value
	 * @param cleararea
	 *            a <code>boolean</code> value
	 */
	public void generateDungeon(DungeonVars vars) {

		drawingArea.generateDungeon(vars);
		enableButtons();
	}

	/**
	 * Describe <code>makeSparse</code> method here.
	 * 
	 */
	public void makeSparse() {
		drawingArea.getDungeon().makeSparse();
		drawingArea.getDungeon().evaluateCorridors();
		drawingArea.updateImage();
	}

	/**
	 * Describe <code>addDoors</code> method here.
	 * 
	 */
	public void addDoors() {
		drawingArea.getDungeon().addDoors();
		drawingArea.updateImage();
	}

	/**
	 * Describe <code>actionPerformed</code> method here.
	 * 
	 * @param e
	 *            an <code>ActionEvent</code> value
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("repaint")) {
			// Callback from generator
			
			drawingArea.repaint();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void followMapLink(String link) {
		if (mode == DISPLAY_SERVER) {
			((InitServer) owner).followMapLink(link);
		}
		
	}

}
