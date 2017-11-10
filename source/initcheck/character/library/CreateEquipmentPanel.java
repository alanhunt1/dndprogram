package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ItemTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Equipment;
import initcheck.database.EquipmentDAO;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateEquipmentPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextField cost = new JTextField(20);

	private JTextField weight = new JTextField(20);

	private JTextField notes = new JTextField(20);

	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private Equipment equipment;

	private ItemTypeChooser typeChooser = new ItemTypeChooser();

	private SourceChooser sourceChooser = new SourceChooser();
	
	// private JCheckBox equipmentCheck = new JCheckBox("Equipment Check
	// Penalty");

	EquipmentDAO db = new EquipmentDAO();

	LibraryPanel owner;

	public CreateEquipmentPanel(LibraryPanel owner) {
		equipment = new Equipment();

		init();
		this.owner = owner;
	}

	public CreateEquipmentPanel(Equipment f, LibraryPanel owner) {

		this.owner = owner;
		setEquipment(f);
		init();

	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		main.doLayout(new JLabel("Source"), 0, main.ypos);
		main.doLayout(sourceChooser, 1, main.ypos);
		main.ypos++;
		
		main.doLayout(new JLabel("Name"), 0, main.ypos);
		main.doLayout(name, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Cost"), 0, main.ypos);
		main.doLayout(cost, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Weight"), 0, main.ypos);
		main.doLayout(weight, 1, main.ypos);
		main.ypos++;

		main.doLayout(new JLabel("Type"), 0, main.ypos);
		main.doLayout(typeChooser, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Notes"), 0, main.ypos);
		main.doLayout(notes, 1, main.ypos);

		main.ypos++;

		main.doLayout(new JLabel("Description"), 0, main.ypos);
		main.doLayout(descScroll, 1, main.ypos);
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveEquipment();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrev();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void setEquipment(Equipment f) {
		this.equipment = f;

		name.setText(f.getItemName());
		cost.setText(f.getCost());
		weight.setText(f.getWeight());
		notes.setText(f.getNotes());
		description.setText(f.getDescription());
		typeChooser.setSelectedItem(f.getType());
		sourceChooser.setSelectedItem(f.getSource());
	}

	public void showNext() {
		owner.incSelected();
		setEquipment((Equipment) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setEquipment((Equipment) owner.getSelected());
	}

	public void saveEquipment() {
		try {
			equipment.setItemName(name.getText());
			equipment.setCost(cost.getText());
			equipment.setWeight(weight.getText());
			@SuppressWarnings("unused")
			int i = Integer.parseInt(weight.getText());
			equipment.setNotes(notes.getText());
			equipment.setDescription(description.getText());
			equipment.setType((String) typeChooser.getSelectedItem());
			equipment.setSource((String)sourceChooser.getSelectedItem());
			db.addOrUpdateEquipment(equipment);
			owner.updateList();
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error!",
					"You must enter an integer value for the weight");

		}
	}

}
