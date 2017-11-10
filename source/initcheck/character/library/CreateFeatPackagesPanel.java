package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.FeatPackageFeatPanel;
import initcheck.database.FeatPackage;
import initcheck.database.FeatPackageDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateFeatPackagesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	FeatPackageFeatPanel featsPanel;

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private FeatPackage featPackage;

	FeatPackageDAO db = new FeatPackageDAO();

	LibraryPanel owner;

	public CreateFeatPackagesPanel(LibraryPanel owner) {
		featPackage = new FeatPackage();

		init();
		this.owner = owner;
	}

	public CreateFeatPackagesPanel(FeatPackage f, LibraryPanel owner) {

		this.owner = owner;
		this.featPackage = f;

		description.setText(f.getDescription());
		name.setText(f.getPackageName());

		init();

	}

	public void init() {

		featsPanel = new FeatPackageFeatPanel(featPackage);

		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new Label("Package Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Package Feats"), 0, ypos, 2, 1);
		ypos++;

		c.weightx = 1.0;
		c.weighty = 0.5;

		doLayout(featsPanel, 0, ypos, 2, 1);
		ypos++;

		doLayout(descScroll, 0, ypos, 2, 1);

		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFeatPackage();
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

	public void saveFeatPackage() {
		featPackage.setPackageName(name.getText());
		featPackage.setDescription(description.getText());

		int i = db.addOrUpdateFeatPackage(featPackage);
		featPackage.setId("" + i);
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

	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

}
