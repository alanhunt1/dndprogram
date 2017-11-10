package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.DomainSpellPanel;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Domain;
import initcheck.database.DomainDAO;
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

public class CreateDomainPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JTextArea power = new JTextArea(5, 50);

	private JScrollPane powerScroll = new JScrollPane(power);

	private JPanel main = new JPanel();

	DomainSpellPanel spellsPanel;

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JPanel buttonPanel = new JPanel();

	private Domain domain;

	private SourceChooser sourceChooser = new SourceChooser();
	
	DomainDAO db = new DomainDAO();

	LibraryPanel owner;

	public CreateDomainPanel(LibraryPanel owner) {
		domain = new Domain();

		init();
		this.owner = owner;
	}

	public CreateDomainPanel(Domain f, LibraryPanel owner) {

		this.owner = owner;
		setDomain(f);
		init();

	}
	
	public void setDomain(Domain f){
		this.domain = f;

		description.setText(f.getDescription());
		name.setText(f.getDomainName());
		power.setText(f.getDomainPower());
		sourceChooser.setSelectedItem(f.getSource());
	}

	public void init() {

		spellsPanel = new DomainSpellPanel(domain);

		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		power.setWrapStyleWord(true);
		power.setLineWrap(true);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new Label("Source"), 0, ypos);
		doLayout(sourceChooser, 1, ypos);
		ypos++;
		
		doLayout(new Label("Domain Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Granted Power"), 0, ypos, 2, 1);
		ypos++;
		doLayout(powerScroll, 0, ypos, 2, 1);
		ypos++;

		doLayout(new JLabel("Domain Spells"), 0, ypos, 2, 1);
		ypos++;

		c.weightx = 1.0;
		c.weighty = 0.5;

		doLayout(spellsPanel, 0, ypos, 2, 1);
		ypos++;

		doLayout(descScroll, 0, ypos, 2, 1);

		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDomain();
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

	public void showNext() {
		owner.incSelected();
		setDomain((Domain) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setDomain((Domain) owner.getSelected());
	}

	
	public void saveDomain() {
		domain.setDomainName(name.getText());
		domain.setDescription(description.getText());
		domain.setDomainPower(power.getText());
		domain.setSource((String)sourceChooser.getSelectedItem());
		int i = db.addOrUpdateDomain(domain);
		domain.setId("" + i);
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
