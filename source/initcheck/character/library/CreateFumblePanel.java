package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.FumbleCategoryChooser;
import initcheck.database.Fumble;
import initcheck.database.FumbleDAO;
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

public class CreateFumblePanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private FumbleCategoryChooser category = new FumbleCategoryChooser();

	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private Fumble equipment;

	// private JCheckBox equipmentCheck = new JCheckBox("Equipment Check
	// Penalty");

	FumbleDAO db = new FumbleDAO();

	LibraryPanel owner;

	public CreateFumblePanel(LibraryPanel owner) {
		equipment = new Fumble();

		init();
		this.owner = owner;
	}

	public CreateFumblePanel(Fumble f, LibraryPanel owner) {

		this.owner = owner;
		this.equipment = f;

		category.setSelectedItem(f.getCategory());
		description.setText(f.getDescription());

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

		doLayout(new JLabel("Category"), 0, ypos);
		doLayout(category, 1, ypos);
		ypos++;

		doLayout(new JLabel("Description"), 0, ypos);
		doLayout(descScroll, 1, ypos);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFumble();
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

	public void saveFumble() {

		equipment.setCategory((String) category.getSelectedItem());
		equipment.setDescription(description.getText());
		db.addOrUpdateFumble(equipment);
		owner.updateList();

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
