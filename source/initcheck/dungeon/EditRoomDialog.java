package initcheck.dungeon;

import initcheck.InitImage;
import initcheck.MapEditor;
import initcheck.MonsterGroup;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EditRoomDialog extends TiledDialog implements
		ListSelectionListener {

	private static final long serialVersionUID = 1L;
	JTextField widthField = new JTextField(5);
	JTextField heightField = new JTextField(5);
	JTextField xField = new JTextField(5);
	JTextField yField = new JTextField(5);
	JTextField titleField = new JTextField(10);
	TiledTextArea notes = new TiledTextArea();
	JScrollPane notesScroll = new JScrollPane(notes);
	TiledList roomList = new TiledList();
	JScrollPane scroll = new JScrollPane(roomList);

	Vector<Room> rooms = new Vector<Room>();

	PanelButton saveButton = new PanelButton("Save Changes", 80);

	TiledGridPanel main = new TiledGridPanel();

	TiledList encounterList = new TiledList();
	JScrollPane encounterScroll = new JScrollPane(encounterList);

	DungeonGUI map = null;
	MapEditor owner = null;

	PanelButton modEncounter = new PanelButton("Modify");

	PanelButton delEncounter = new PanelButton("Remove");

	PanelButton addEncounter = new PanelButton("Add");
	
	PanelButton refresh = new PanelButton("Refresh");
	
	public EditRoomDialog(DungeonGUI map, MapEditor owner) {

		super();

		this.map = map;
		this.owner = owner;

		buildComponents();
		init();
	}

	public void setX(String x) {
		xField.setText(x);
	}

	public void setY(String y) {
		yField.setText(y);
	}

	public void setWidth(String w) {
		widthField.setText(w);
	}

	public void setHeight(String h) {
		heightField.setText(h);
	}

	public void buildComponents() {

		int ypos = 0;
		rooms = map.getRooms();
		roomList.setListData(rooms);
		if (rooms.size() > 0) {
			roomList.setSelectedIndex(0);
		}
		roomList.setCellRenderer(new GenericListCellRenderer());
		encounterList.setCellRenderer(new GenericListCellRenderer());
		main.doLayout(roomList, 0, main.ypos, 1, 4);

		TiledGridPanel positionPanel = new TiledGridPanel(InitImage.lightRocks);
		positionPanel.doLayout(new JLabel("X : "), 0, positionPanel.ypos);
		positionPanel.doLayout(xField, 1, positionPanel.ypos);
		positionPanel.doLayout(new JLabel("Y : "), 2, positionPanel.ypos);
		positionPanel.doLayout(yField, 3, positionPanel.ypos);
		positionPanel.incYPos();
		positionPanel.doLayout(new JLabel("Width : "), 0, positionPanel.ypos);
		positionPanel.doLayout(widthField, 1, positionPanel.ypos);
		positionPanel.doLayout(new JLabel("Height : "), 2, positionPanel.ypos);
		positionPanel.doLayout(heightField, 3, positionPanel.ypos);
		positionPanel.incYPos();

		main.doLayout(positionPanel, 1, main.ypos);

		main.incYPos();
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		
		infoPanel.add(titleField, BorderLayout.NORTH);
		notes.setWrapStyleWord(true);
		notes.setLineWrap(true);
		infoPanel.add(notesScroll, BorderLayout.CENTER);
		infoPanel.setOpaque(false);
		main.setWeightX(1);
		main.setWeightY(1);
		main.doLayout(infoPanel, 1, main.ypos);
		main.setWeightX(0);
		main.setWeightY(0);
		main.incYPos();
		main.doLayout(encounterList, 1, main.ypos);

		
		modEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modEncounter();
			}
		});
		
		delEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeEncounter();
			}
		});
		
		addEncounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEncounter();
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRoomList();
			}
		});
		
		TiledPanel buttonPanel = new TiledPanel(InitImage.lightRocks);
		buttonPanel.setOpaque(false);
		buttonPanel.add(modEncounter);
		buttonPanel.add(delEncounter);
		buttonPanel.add(addEncounter);
		
		main.incYPos();
		main.doLayout(buttonPanel, 1, main.ypos);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setOpaque(false);
		generatePanel.add(refresh);
		generatePanel.add(saveButton);
		
		ypos++;

		roomList.addListSelectionListener(this);

		setMainWindow(main);
		setButtonBar(generatePanel);

	}

	public void updateRoomList(){
		rooms = map.getRooms();
		roomList.setListData(rooms);
		if (rooms.size() > 0) {
			roomList.setSelectedIndex(0);
		}
	}
	
	public void setRoom(int i){
		roomList.setSelectedIndex(i);
	}
	
	public void modEncounter() {
		int idx = roomList.getSelectedIndex();

		if (idx >= 0) {
			Room r = rooms.get(idx);
			map.showMonsterDialog(r);
		}
	}
	public void removeEncounter() {
		int idx = roomList.getSelectedIndex();

		if (idx >= 0) {
			Room r = rooms.get(idx);
			r.setMonsters(new Vector<MonsterGroup>());
			r.setText("");
		}
	}

	public void addEncounter() {
		int idx = roomList.getSelectedIndex();

		if (idx >= 0) {
			Room r = rooms.get(idx);
			map.showMonsterDialog(r);
		}

	}

	public void init() {
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRoom();
			}
		});
	}

	public void saveRoom() {
		int idx = roomList.getSelectedIndex();

		if (idx >= 0) {
			Room r = rooms.get(idx);
			r.setTitle(titleField.getText());
			r.setText(notes.getText());
		}

		owner.saveDungeon();
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			int idx = roomList.getSelectedIndex();

			if (idx >= 0) {
				Room r = rooms.get(idx);
				xField.setText("" + r.getLeft());
				yField.setText("" + r.getTop());
				widthField.setText("" + (r.getRight() - r.getLeft()));
				heightField.setText("" + (r.getBottom() - r.getTop()));
				titleField.setText(r.getTitle());
				notes.setText(r.getText());
				encounterList.setListData(r.getMonsters());
				roomList.ensureIndexIsVisible(idx);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

}
