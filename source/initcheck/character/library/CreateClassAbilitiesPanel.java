package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.ClassAbilities;
import initcheck.database.ClassAbilitiesDAO;
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

public class CreateClassAbilitiesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JPanel buttonPanel = new JPanel();

	private ClassAbilities classAbilities;

	private SourceChooser sourceChooser = new SourceChooser();
	
	ClassAbilitiesDAO db = new ClassAbilitiesDAO();

	LibraryPanel owner;

	public CreateClassAbilitiesPanel(LibraryPanel owner) {
		classAbilities = new ClassAbilities();

		init();
		this.owner = owner;
	}

	public void setClassAbilities(ClassAbilities f){
		this.classAbilities = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		sourceChooser.setSelectedItem(f.getSource());
	}
	
	public CreateClassAbilitiesPanel(ClassAbilities f, LibraryPanel owner) {

		this.owner = owner;
		setClassAbilities(f);
		init();

	}

	public void init() {

		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;

		int ypos = 0;

		doLayout(new JLabel("Source"), 0, ypos);
		doLayout(sourceChooser, 1, ypos);
		ypos++;
		
		doLayout(new JLabel("Ability Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Description"), 0, ypos, 2, 1);
		ypos++;

		c.weightx = 1.0;
		c.weighty = 0.5;

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
				saveClassAbilities();
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
		setClassAbilities((ClassAbilities) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setClassAbilities((ClassAbilities) owner.getSelected());
	}
	
	public void saveClassAbilities() {
		classAbilities.setName(name.getText());
		classAbilities.setDescription(description.getText());
		classAbilities.setSource((String)sourceChooser.getSelectedItem());
		int i = db.addOrUpdateClassAbilities(classAbilities);
		classAbilities.setId("" + i);
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
