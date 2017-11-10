package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Rings;
import initcheck.database.RingsDAO;
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

public class CreateRingPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	private JTextField name = new JTextField(20);

	private JTextField cost = new JTextField(20);

	private JTextField level = new JTextField(20);

	private JTextField casterLevel = new JTextField(20);

	private JTextField prereqs = new JTextField(20);

	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private JPanel main = new JPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");
	
	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private Rings ring;

	private SourceChooser sourceChooser = new SourceChooser();
	
	RingsDAO db = new RingsDAO();

	LibraryPanel owner;

	public CreateRingPanel(LibraryPanel owner) {
		ring = new Rings();

		init();
		this.owner = owner;
	}

	public CreateRingPanel(Rings f, LibraryPanel owner) {

		this.owner = owner;
	setRing(f);

		init();

	}

	public void setRing(Rings f){
		this.ring = f;

		name.setText(f.getName());
		cost.setText(f.getCost());

		level.setText(f.getLevel());
		casterLevel.setText(f.getCasterLevel());
		description.setText(f.getDescription());
		prereqs.setText(f.getPrereqs());
		sourceChooser.setSelectedItem(f.getSource());
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
		doLayout(sourceChooser, 1, ypos);
		ypos++;
		
		doLayout(new JLabel("Name"), 0, ypos);
		doLayout(name, 1, ypos);
		ypos++;

		doLayout(new JLabel("Cost"), 0, ypos);
		doLayout(cost, 1, ypos);

		ypos++;

		doLayout(new JLabel("Level"), 0, ypos);
		doLayout(level, 1, ypos);

		ypos++;

		doLayout(new JLabel("Caster Level"), 0, ypos);
		doLayout(casterLevel, 1, ypos);
		ypos++;

		doLayout(new JLabel("Prereqs"), 0, ypos);
		doLayout(prereqs, 1, ypos);
		ypos++;

		doLayout(new JLabel("Description"), 0, ypos);
		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(descScroll, 1, ypos);

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
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
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRings();
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

	public void showNext() {
		owner.incSelected();
		setRing((Rings) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setRing((Rings) owner.getSelected());
	}
	
	public void saveRings() {

		ring.setName(name.getText());
		ring.setCost(cost.getText());

		ring.setLevel(level.getText());
		ring.setDescription(description.getText());
		ring.setCasterLevel(casterLevel.getText());
		ring.setPrereqs(prereqs.getText());
		ring.setSource((String)sourceChooser.getSelectedItem());
		db.addOrUpdateRings(ring);
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
