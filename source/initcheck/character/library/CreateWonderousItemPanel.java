package initcheck.character.library;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Wonderous;
import initcheck.database.WonderousItemsDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateWonderousItemPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private SourceChooser source = new SourceChooser();
	
	private JTextField name = new JTextField(20);

	private JTextField cost = new JTextField(20);

	private JTextField weight = new JTextField(20);

	private JTextField level = new JTextField(20);

	private JTextField casterLevel = new JTextField(20);

	private JTextField spells = new JTextField(20);

	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private Wonderous wonderousItem;

	WonderousItemsDAO db = new WonderousItemsDAO();

	LibraryPanel owner;

	public CreateWonderousItemPanel(LibraryPanel owner) {
		wonderousItem = new Wonderous();

		init();
		this.owner = owner;
	}

	public CreateWonderousItemPanel(Wonderous f, LibraryPanel owner) {

		this.owner = owner;
		this.wonderousItem = f;

		name.setText(f.getName());
		cost.setText(f.getPrice());
		weight.setText(f.getWeight());
		level.setText(f.getLevel());
		casterLevel.setText(f.getCasterLevel());
		description.setText(f.getDescription());
		spells.setText(f.getSpells());
		source.setSelectedItem(f.getSource());
		init();

	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new JLabel("Source"), 0, ypos);
		doLayout(source, 1, ypos);
		ypos++;

		
		doLayout(new JLabel("Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Cost"), 0, ypos);
		doLayout(cost, 1, ypos);

		ypos++;

		doLayout(new JLabel("Weight"), 0, ypos);
		doLayout(weight, 1, ypos);
		ypos++;

		doLayout(new JLabel("Level"), 0, ypos);
		doLayout(level, 1, ypos);

		ypos++;

		doLayout(new JLabel("Caster Level"), 0, ypos);
		doLayout(casterLevel, 1, ypos);
		ypos++;

		doLayout(new JLabel("Prereqs"), 0, ypos);
		doLayout(spells, 1, ypos);
		ypos++;

		doLayout(new JLabel("Description"), 0, ypos);
		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(descScroll, 1, ypos);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWonderousItem();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void saveWonderousItem() {
		try {
			wonderousItem.setSource((String)source.getSelectedItem());
			wonderousItem.setName(name.getText());
			wonderousItem.setPrice(cost.getText());
			wonderousItem.setWeight(weight.getText());
			@SuppressWarnings("unused")
			int i = Integer.parseInt(weight.getText());
			wonderousItem.setLevel(level.getText());
			wonderousItem.setDescription(description.getText());
			wonderousItem.setCasterLevel(casterLevel.getText());
			wonderousItem.setSpells(spells.getText());
			db.addOrUpdateWonderousItem(wonderousItem);
			owner.updateList();
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error!",
					"You must enter an integer value for the weight");

		}
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

}
