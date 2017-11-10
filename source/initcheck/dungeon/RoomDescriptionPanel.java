package initcheck.dungeon;

import initcheck.PanelButton;
import initcheck.database.RoomDescription;
import initcheck.database.RoomDescriptionDAO;
import initcheck.graphics.TiledGridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RoomDescriptionPanel extends TiledGridPanel implements
		ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	private JComboBox type = new JComboBox();

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JList shortDescList = new JList();

	private JScrollPane shortDescScroll = new JScrollPane(shortDescList);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton addButton = new PanelButton("Add");

	public RoomDescriptionPanel() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		type.addItem("Lighting");
		type.addItem("Walls");
		type.addItem("Ceiling");
		type.addItem("Floor");
		type.addItem("Smell");
		type.addItem("Sounds");
		type.addItem("Contents");

		type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDescriptions();
			}
		});

		type.setSelectedItem("Lighting");
		JLabel typeLabel = new JLabel("Type");
		typeLabel.setOpaque(true);
		
		doLayout(typeLabel, 0, ypos);
		doLayout(type, 1, ypos);
		incYPos();

		shortDescList.addListSelectionListener(this);
		shortDescList.setCellRenderer(new RoomDescriptionCellRenderer());
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				shortDescScroll, descScroll);

		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(split, 0, ypos, 2, 1);
		incYPos();
		setWeightX(0);
		setWeightY(0);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(saveButton);
		buttonPanel.add(addButton);
		doLayout(buttonPanel, 0, ypos, 2, 1);

		setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				20, // bottom
				20) // right
		);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDescription();
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDescription();
			}
		});

	}

	public void loadDescriptions() {
		RoomDescriptionDAO db = new RoomDescriptionDAO();
		RoomDescription rd = new RoomDescription();
		rd.setDescType((String) type.getSelectedItem());
		Vector<RoomDescription> shortDesc = db.selectRoomDescription(rd);
		shortDescList.setListData(shortDesc);
	}

	public void saveDescription() {

		RoomDescriptionDAO db = new RoomDescriptionDAO();
		RoomDescription rd = (RoomDescription) shortDescList.getSelectedValue();
		rd.setDescription(description.getText());
		db.updateRoomDescription(rd);
	}

	public void addDescription() {
		RoomDescriptionDAO db = new RoomDescriptionDAO();
		RoomDescription rd = new RoomDescription();
		rd.setDescType((String) type.getSelectedItem());
		rd.setDescription(description.getText());
		db.addRoomDescription(rd);
		loadDescriptions();
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			RoomDescription f = (RoomDescription) shortDescList
					.getSelectedValue();
			if (f != null) {
				description.setText(f.getDescription());
				description.setCaretPosition(0);
				shortDescList.ensureIndexIsVisible(shortDescList
						.getSelectedIndex());
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
}
