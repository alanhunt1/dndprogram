package initcheck.dungeon;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class DungeonControlPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	int cols = 50;
	
	int rows = 50;
	 
	int levels = 1;
	
	JTextField partyLevelField = new JTextField(2);

	PanelButton encounterButton = new PanelButton("Update Encounters", 100);

	PanelButton visButton = new PanelButton("Reset Visibility", 80);

	PanelButton saveButton = new PanelButton("Save Map");

	PanelButton loadButton = new PanelButton("Load Map");

	PanelButton lockButton = new PanelButton("Edit");

	PanelButton generateButton = new PanelButton("Generate Map", 80);

	PanelButton smoothenButton = new PanelButton("Remove Dead Ends", 100);

	JButton test = new JButton(new ImageIcon("images/hole.gif"));

	JTextField nameField = new JTextField("Enter name here               ");

	JPanel controls0 = new JPanel();

	JPanel controls1 = new JPanel();

	JPanel controls2 = new JPanel();

	JPanel controls3 = new JPanel();

	JPanel controls4 = new JPanel();

	TiledPanel main = new TiledPanel();

	ButtonGroup modeGroup = new ButtonGroup();

	JRadioButton wallMode = new JRadioButton("wall");

	JRadioButton roomMode = new JRadioButton(" ", new ImageIcon(
			"images/room.gif"));

	JRadioButton corridorMode = new JRadioButton("corridor");

	JRadioButton holeMode = new JRadioButton(" ", new ImageIcon(
			"images/hole.gif"));

	JRadioButton waterMode = new JRadioButton(" ", new ImageIcon(
			"images/water.gif"));

	JRadioButton bridgeMode = new JRadioButton(" ", new ImageIcon(
			"images/bridge.gif"));

	JRadioButton doorMode = new JRadioButton(" ", new ImageIcon(
			"images/door.gif"));

	JRadioButton secretDoorMode = new JRadioButton("secret door");

	JRadioButton stairMode = new JRadioButton(" ", new ImageIcon(
			"images/stair.gif"));

	MapEditorDungeonGUI owner = null;

	JPanel modePanel;

	JComboBox drawMode;

	MapStyleChooser mapStyle = new MapStyleChooser();

	DrawStyleChooser drawStyle = new DrawStyleChooser();

	int ypos = 0;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public DungeonControlPanel(MapEditorDungeonGUI owner, ImageIcon bg) {
		super(bg);
		this.owner = owner;

		drawMode = new DrawPointChooser();
		buildComponents();
		init();

	}

	public int drawMode() {
		String mode = (String) drawMode.getSelectedItem();
		
		if (mode.equals("Wall")) {
			return Dungeon.wall;
		} else if (mode.equals("Room")) {
			return Dungeon.room;
		} else if (mode.equals("Corridor")) {
			return Dungeon.corridor;
		} else if (mode.equals("Hole")) {
			return Dungeon.hole;
		} else if (mode.equals("Water")) {
			return Dungeon.water;
		} else if (mode.equals("Bridge")) {
			return Dungeon.bridge;
		} else if (mode.equals("Door")) {
			return Dungeon.door;
		} else if (mode.equals("Secret Door")) {
			return Dungeon.secretdoor;
		} else if (mode.equals("Stair")) {
			return Dungeon.staircircle;
		} else if (mode.equals("Trap")) {
			return Dungeon.trap;
		} else if (mode.equals("Land")) {
			return Dungeon.land;
		}
		return -1;
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	public void buildComponents() {
		setOpaque(false);

		// default drawing mode
		wallMode.setSelected(true);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;

		if (!owner.isLocked()) {
			lockButton.setText("Lock");
		}

		JPanel sharedPanel = new JPanel();
		sharedPanel.setOpaque(false);
		sharedPanel.add(lockButton);
		//sharedPanel.add(printButton);

		doLayout(sharedPanel, 0, ypos);

		JPanel generatePanel = new JPanel();
		generatePanel.setOpaque(false);
		generatePanel.add(smoothenButton);

		doLayout(generatePanel, 1, ypos);
		// ypos++;

		doLayout(visButton, 3, ypos);
		// ypos++;

		doLayout(partyLevelField, 4, ypos);
		doLayout(encounterButton, 5, ypos);
		// ypos++;

		doLayout(mapStyle, 6, ypos);
		//JLabel dmode = new JLabel("Drawing Mode");
		//dmode.setForeground(Color.white);
		//doLayout(dmode, 7, ypos);

		//doLayout(drawStyle, 8, ypos);
		//doLayout(drawMode, 9, ypos);
		add(main);
	}

	public void init() {

		mapStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeMapStyle();
			}
		});

		lockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeModes();
			}
		});

		smoothenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDeadEnds();
			}
		});

		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMap();
			}
		});

		encounterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEncounters();
			}
		});

		

		drawMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDrawMode();
			}
		});

		drawStyle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDrawStyle();
			}
		});

		visButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetVisibility();
			}
		});
	}

	public void changeMapStyle() {
		owner.setMapStyle((String) mapStyle.getSelectedItem());
	}

	public void resetVisibility() {
		owner.resetVisibility();
	}

	public void setDrawMode() {
		owner.setDrawMode(drawMode());
	}

	public void setDrawStyle() {
		owner.setDrawStyle((String) drawStyle.getSelectedItem());
		invalidate();
		main.remove(drawMode);
		String style = (String) drawStyle.getSelectedItem();
		if (style.equals("Point")) {
			drawMode = new DrawPointChooser();
		} else if (style.equals("Line")) {
			drawMode = new DrawLineChooser();
		} else if (style.equals("Area")) {
			drawMode = new DrawAreaChooser();
		} else if (style.equals("Flood")) {
			drawMode = new DrawFloodChooser();
		}
		doLayout(drawMode, 9, ypos);
		drawMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDrawMode();
			}
		});
		validate();
		setDrawMode();
	}

	public void printMap() {
		owner.printMap();
	}

	public void saveMap() {
		owner.saveDungeon(nameField.getText());
	}

	public void loadMap() {
		owner.loadDungeon();
	}

	public void updateEncounters() {
		try {
			double partyLevel = Double.parseDouble(partyLevelField.getText());
			owner.updateEncounters(partyLevel);
		} catch (NumberFormatException e) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog(owner.getFrame(), "Error",
					"You must enter a valid party level before updating encounters.");
		}
	}

	public void setMapSquare(MapSquare m) {

		// squareInfo.setMapSquare(m);
	}

	public void removeDeadEnds() {
		owner.makeSparse();
	}

	public void changeModes() {
		if (lockButton.getText().equals("Lock")) {
			owner.lockDungeon();
			owner.invalidate();
			remove(main);
			lockButton.setText("Edit");
			main.removeAll();
			buildComponents();
			owner.validate();
		} else {
			owner.invalidate();
			owner.unlockDungeon();
			remove(main);
			lockButton.setText("Lock");
			main.removeAll();
			buildComponents();
			owner.validate();
		}
	}

}
