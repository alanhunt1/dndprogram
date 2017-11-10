package initcheck.dungeon;

import initcheck.MonsterGroup;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.database.Trap;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SquareInfoPanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	DungeonGUI owner = null;

	PanelButton runEncounter = new PanelButton("Run", 75);

	PanelButton modEncounter = new PanelButton("Modify", 75);

	PanelButton delEncounter = new PanelButton("Remove", 75);

	PanelButton addEncounter = new PanelButton("Add", 75);

	PanelButton genDesc = new PanelButton("Rnd", 75);

	PanelButton pathButton = new PanelButton("Find Path", 80);

	PanelButton sendMapButton = new PanelButton("Send To Client", 100);

	PanelButton followLinkButton = new PanelButton("Follow Map Link", 100);
	
	PanelButton saveDmNotes = new PanelButton("Save DM Notes", 100);

	PanelButton savePlayerNotes = new PanelButton("Save Player Notes", 100);

	PanelButton saveMapLink = new PanelButton("Save Map Link", 100);
	
	MapSquare square;

	GridPanel infoPanel = new GridPanel();

	JLabel header = new JLabel("Square Information");

	JTextArea notes = new JTextArea(5, 20);

	JTextArea playerNotes = new JTextArea(5, 20);
	
	JTextField mapLink = new JTextField();

	JTextArea roomInfo = new JTextArea(10, 20);

	JLabel type = new JLabel("Type : ");

	JLabel room = new JLabel("Room Number : N/A");

	JScrollPane infoScroll = new JScrollPane(roomInfo,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JScrollPane playerScroll = new JScrollPane(playerNotes,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	JScrollPane dmScroll = new JScrollPane(notes,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	Room r;

	public SquareInfoPanel(DungeonGUI owner, ImageIcon bg) {
		super(bg);

		header.setForeground(Color.white);
		type.setForeground(Color.white);
		room.setForeground(Color.white);

		this.owner = owner;
		init();
		roomInfo.setLineWrap(true);
		roomInfo.setWrapStyleWord(true);
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		playerNotes.setLineWrap(true);
		playerNotes.setWrapStyleWord(true);

		setLayout(new BorderLayout());
		// set the layout
		add(infoPanel, BorderLayout.CENTER);
		runEncounter.setEnabled(false);
		runEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runEncounter();
			}
		});
		modEncounter.setEnabled(false);
		modEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modEncounter();
			}
		});
		delEncounter.setEnabled(false);
		delEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEncounter();
			}
		});
		addEncounter.setEnabled(false);
		addEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEncounter();
			}
		});
		genDesc.setEnabled(false);
		genDesc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateDescription();
			}
		});

		saveDmNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDmNotes();
			}
		});

		saveMapLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMapLink();
			}
		});
		
		savePlayerNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePlayerNotes();
			}
		});

		sendMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMap();
			}
		});
		
		followLinkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				followLink();
			}
		});

		pathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPath();
			}
		});

	}

	public void setPath() {
		owner.setMoveMode(DrawingBoard.PATH);
	}

	public void sendMap() {
		owner.sendMap();
	}
	
	public void followLink() {
		owner.followMapLink(mapLink.getText());
	}

	public void saveDmNotes() {
		square.setDmNotes(notes.getText());
	}

	public void saveMapLink() {
		square.setMapLink(mapLink.getText());
	}
	
	public void savePlayerNotes() {
		if (square != null){
			square.setNotes(playerNotes.getText());
		}
	}

	public void runEncounter() {
		owner.runEncounter(r.getMonsters());
		owner.showListTab();
	}

	public void modEncounter() {
		owner.showMonsterDialog(r);
	}

	public void removeEncounter() {

		r.setMonsters(new Vector<MonsterGroup>());
		r.setText("");
		showSquareInfo();
	}

	public void addEncounter() {
		owner.showMonsterDialog(r);
		showSquareInfo();
	}

	public void setMapSquare(MapSquare square) {

		this.square = square;
		showSquareInfo();
	}

	public void generateDescription() {
		owner.generateDescription(r);
		showSquareInfo();
	}

	public void showSquareInfo() {
		
		notes.setText(square.getDmNotes());
		mapLink.setText(square.getMapLink());
		
		type.setText("Type : " + Dungeon.getTypeDescription(square.getType()));
		playerNotes.setText(square.getNotes());
		int roomNumber = square.getRoomNumber();
		if (roomNumber >= 0) {

			r = owner.getRoom(roomNumber + 1);
			room.setText("Room Number : " + (roomNumber + 1) + " ("
					+ (r.getWidth() * 10) + " X " + (r.getHeight() * 10) + ")");
			if (r != null) {
				roomInfo.setText(r.getText());
				roomInfo.setCaretPosition(0);
				if (r.getMonsters().size() > 0) {
					runEncounter.setEnabled(true);
					modEncounter.setEnabled(true);
					delEncounter.setEnabled(true);
					addEncounter.setEnabled(false);
					genDesc.setEnabled(true);
				} else {
					runEncounter.setEnabled(false);
					modEncounter.setEnabled(false);
					delEncounter.setEnabled(false);
					addEncounter.setEnabled(true);
					genDesc.setEnabled(true);
				}
			}
		} else {
			if (square.getTrap() != null){
				Trap trap = square.getTrap();
				String trapStr = trap.toString()+" "+trap.getSpotDc()+"/"+trap.getDisableDc()+" "+trap.getDescription();
				roomInfo.setText(trapStr);
			}else{
				roomInfo.setText("");
			}
			room.setText("Room Number : N/A");
			
			runEncounter.setEnabled(false);
			modEncounter.setEnabled(false);
			delEncounter.setEnabled(false);
			addEncounter.setEnabled(false);
			genDesc.setEnabled(false);
		}
	}

	public void init() {

		infoPanel.setBorder(BorderFactory.createEmptyBorder(5, // top
				5, // left
				5, // bottom
				5) // right
				);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(sendMapButton);
		buttonPanel.add(pathButton);
		buttonPanel.add(followLinkButton);
		buttonPanel.setOpaque(false);
		infoPanel.setOpaque(false);

		if (owner.getMode() == ClientServerDungeonGUI.DISPLAY_SERVER) {
			infoPanel.doLayout(buttonPanel, 0);
			infoPanel.incYPos();
		}

		infoPanel.doLayout(header, 0, 2, 1);
		infoPanel.incYPos();

		infoPanel.doLayout(type, 0);
		infoPanel.incYPos();

		infoPanel.doLayout(room, 0);
		infoPanel.incYPos();

		JLabel linklabel = new JLabel("Map Link");
		linklabel.setForeground(Color.white);
		infoPanel.doLayout(linklabel, 0);
		infoPanel.incYPos();
		infoPanel.doLayout(mapLink, 0);
		infoPanel.incYPos();
		JPanel buttonWrap = new JPanel();
		buttonWrap.add(saveMapLink);
		buttonWrap.setOpaque(false);
		infoPanel.doLayout(buttonWrap, 0);
		infoPanel.incYPos();
		
		JLabel dmnoteslabel = new JLabel("DM Notes");
		dmnoteslabel.setForeground(Color.white);
		infoPanel.doLayout(dmnoteslabel, 0);

		infoPanel.incYPos();

		infoPanel.setWeightX(1);
		infoPanel.setWeightY(0.3);
		infoPanel.doLayout(dmScroll, 0);
		infoPanel.setWeightX(0);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();
		JPanel subpanel = new JPanel();
		subpanel.add(saveDmNotes);
		subpanel.setOpaque(false);
		infoPanel.doLayout(subpanel, 0);
		infoPanel.incYPos();

		JLabel playernotesLabel = new JLabel("Player Notes");
		playernotesLabel.setForeground(Color.white);
		infoPanel.doLayout(playernotesLabel, 0);

		infoPanel.incYPos();
		infoPanel.setWeightX(1);
		infoPanel.setWeightY(0.3);
		infoPanel.doLayout(playerScroll, 0);
		infoPanel.setWeightX(0);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();
		JPanel subpanel2 = new JPanel();
		subpanel2.setOpaque(false);
		subpanel2.add(savePlayerNotes);
		infoPanel.doLayout(subpanel2, 0);
		infoPanel.incYPos();

		JLabel roomnotesLabel = new JLabel("Room Notes");
		roomnotesLabel.setForeground(Color.white);
		infoPanel.doLayout(roomnotesLabel, 0);

		infoPanel.incYPos();
		infoPanel.setWeightX(1);
		infoPanel.setWeightY(0.3);
		infoPanel.doLayout(infoScroll, 0);
		infoPanel.setWeightX(0);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();

		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.add(addEncounter);
		if (owner.getMode() != MapEditorDungeonGUI.GENERATE) {
			buttons.add(runEncounter);
		}
		buttons.add(modEncounter);
		buttons.add(delEncounter);
		buttons.add(genDesc);
		infoPanel.doLayout(buttons, 0);

	}
}
