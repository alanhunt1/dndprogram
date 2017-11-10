package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.RarityChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.SpecificWeapon;
import initcheck.database.SpecificWeaponDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateSpecificWeaponPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	
	
	private SourceChooser source = new SourceChooser();
	
	private RarityChooser minorRating = new RarityChooser();
	
	private RarityChooser mediumRating = new RarityChooser();
	
	private RarityChooser majorRating = new RarityChooser();
	
	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private SpecificWeapon SpecificWeapon;

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	SpecificWeaponDAO db = new SpecificWeaponDAO();

	LibraryPanel owner;

	public CreateSpecificWeaponPanel(LibraryPanel owner) {
		SpecificWeapon = new SpecificWeapon();

		init();
		this.owner = owner;
	}

	public CreateSpecificWeaponPanel(SpecificWeapon f, LibraryPanel owner) {

		this.owner = owner;
		this.SpecificWeapon = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		
		minorRating.setSelectedItem(f.getMinorLevel());
		mediumRating.setSelectedItem(f.getMediumLevel());
		majorRating.setSelectedItem(f.getMajorLevel());
		
		init();

	}

	public void setSpecificWeapon(SpecificWeapon f){
		
		this.SpecificWeapon = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		
		minorRating.setSelectedItem(f.getMinorLevel());
		mediumRating.setSelectedItem(f.getMediumLevel());
		majorRating.setSelectedItem(f.getMajorLevel());
	
	}
	
	public void init() {

		description.setWrapStyleWord(true);
		description.setLineWrap(true);

		int ypos = main.ypos;

		main.doLayout(new Label("Source"), 0, ypos);
		main.doLayout(source, 1, ypos);
		ypos++;
		
		main.doLayout(new Label("Name"), 0, ypos);
		main.doLayout(name, 1, ypos);
		ypos++;

	

		main.doLayout(new JLabel("minor availability"), 0, ypos);
		main.doLayout(minorRating, 1, ypos);
		ypos++;
		
		main.doLayout(new JLabel("medium availability"), 0, ypos);
		main.doLayout(mediumRating, 1, ypos);
		ypos++;
		
		main.doLayout(new JLabel("major availability"), 0, ypos);
		main.doLayout(majorRating, 1, ypos);
		ypos++;
		
		main.setWeightX(1.0);
		main.setWeightY(0.5);

		main.doLayout(descScroll, 0, ypos, 2, 1);
		main.setWeightX(0);
		main.setWeightY(0);

		ypos++;
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
				saveSpecificWeapon();
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
		setSpecificWeapon((SpecificWeapon) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setSpecificWeapon((SpecificWeapon) owner.getSelected());
	}
	
	public void saveSpecificWeapon() {
		SpecificWeapon.setName(name.getText());
		
		SpecificWeapon.setDescription(description.getText());
		SpecificWeapon.setMinorLevel((String)minorRating.getSelectedItem());
		SpecificWeapon.setMediumLevel((String)mediumRating.getSelectedItem());
		SpecificWeapon.setMajorLevel((String)majorRating.getSelectedItem());
		SpecificWeapon.setSource((String)source.getSelectedItem());
		int i = db.addOrUpdateSpecificWeapon(SpecificWeapon);
		SpecificWeapon.setId("" + i);
		owner.updateList();
	}

}
