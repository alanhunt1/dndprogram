package initcheck;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MapEditorMenuBar extends JMenuBar implements ActionListener {
	static final long serialVersionUID = 1;

	// the server
	MapEditor owner;

	// the file menu
	private JMenu menu;

	private JMenuItem exit;

	private JMenuItem load;

	private JMenuItem save;
	
	private JMenuItem saveAs;

	private JMenuItem newmap;

	private JMenuItem genmap;

	private JMenuItem insertMap;

	private JMenuItem exportMap;

	private JMenuItem importMap;

	private JMenuItem printMap;
	
	private JMenuItem closeMap;

	// the options menu
	private JMenu optionMenu;

	private JMenuItem undo;

	private JMenuItem toggleMiniMap;

	// the tools menu
	private JMenu toolsMenu;

	private JMenuItem addLevel;

	private JMenuItem removeLevel;
	
	private JMenuItem randomizeLevel;
	
	private JMenuItem addRoom;

	private JMenuItem drawLine;

	private JMenuItem editRooms;
	
	
	public MapEditorMenuBar(MapEditor owner) {
		super();
		this.owner = owner;

		// Build the file menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		this.add(menu);

		// load
		load = new JMenuItem("Load Map");
		load.addActionListener(this);
		menu.add(load);

		// save
		save = new JMenuItem("Save Map");
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menu.add(save);

		// save as
		saveAs = new JMenuItem("Save As");
		saveAs.addActionListener(this);
		menu.add(saveAs);
		
		// print
		printMap = new JMenuItem("Print Map");
		printMap.addActionListener(this);
		menu.add(printMap);

		// export
		exportMap = new JMenuItem("Export Map");
		exportMap.addActionListener(this);
		menu.add(exportMap);

		// import
		importMap = new JMenuItem("Import Map");
		importMap.addActionListener(this);
		menu.add(importMap);

		// new
		newmap = new JMenuItem("New Map");
		newmap.addActionListener(this);
		menu.add(newmap);

		// new
		genmap = new JMenuItem("Generate Map");
		genmap.addActionListener(this);
		menu.add(genmap);

		// import
		insertMap = new JMenuItem("Insert Map");
		insertMap.addActionListener(this);
		menu.add(insertMap);

		// exit
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		//		 exit
		closeMap = new JMenuItem("Close Map");
		closeMap.addActionListener(this);
		menu.add(closeMap);
		
		// build the options menu
		optionMenu = new JMenu("Options");
		this.add(optionMenu);

		undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		optionMenu.add(undo);

		toggleMiniMap = new JMenuItem("Toggle Mini Map");
		toggleMiniMap.addActionListener(this);
		toggleMiniMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		optionMenu.add(toggleMiniMap);

		// build the tools menu
		toolsMenu = new JMenu("Edit");
		this.add(toolsMenu);

		addLevel = new JMenuItem("Add New Level");
		addLevel.addActionListener(this);
		toolsMenu.add(addLevel);

		removeLevel = new JMenuItem("Remove Level");
		removeLevel.addActionListener(this);
		toolsMenu.add(removeLevel);
		
		randomizeLevel = new JMenuItem("Randomize Level");
		randomizeLevel.addActionListener(this);
		toolsMenu.add(randomizeLevel);
		
		addRoom = new JMenuItem("Add New Room");
		addRoom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		addRoom.addActionListener(this);
		toolsMenu.add(addRoom);

		drawLine = new JMenuItem("Draw Line");
		drawLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		drawLine.addActionListener(this);
		toolsMenu.add(drawLine);
		
		editRooms = new JMenuItem("Edit Rooms");
		editRooms.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		editRooms.addActionListener(this);
		toolsMenu.add(editRooms);

	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source == exit) {
			System.exit(0);
		} else if (source == load) {
			owner.loadDungeon();
		} else if (source == save) {
			owner.saveDungeon();
		} else if (source == saveAs) {
			owner.saveDungeonAs();
		} else if (source == newmap) {
			owner.newDungeon();
		} else if (source == genmap) {
			owner.generateDungeon();
		} else if (source == insertMap) {
			owner.importDungeon();
		} else if (source == addLevel) {
			owner.addLevel();
		} else if (source == removeLevel) {
			owner.removeLevel();
		} else if (source == randomizeLevel) {
			owner.randomizeLevel();
		} else if (source == undo) {
			owner.undo();
		} else if (source == addRoom) {
			owner.addRoom();
		} else if (source == drawLine) {
			owner.drawLine();
		} else if (source == importMap) {
			owner.importMap();
		} else if (source == exportMap) {
			owner.exportMap();
		} else if (source == printMap) {
			owner.printMap();
		} else if (source == toggleMiniMap) {
			owner.getMap().toggleMiniMap();
		} else if (source == closeMap) {
			owner.closeMap();
		} else if (source == editRooms) {
			owner.editRooms();
		}
	}
}
