package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ArmorTypeChooser;
import initcheck.character.chooser.RarityChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.SpecificArmor;
import initcheck.database.SpecificArmorDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateSpecificArmorPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	
	
	private SourceChooser source = new SourceChooser();
	
	private RarityChooser minorRating = new RarityChooser();
	
	private RarityChooser mediumRating = new RarityChooser();
	
	private RarityChooser majorRating = new RarityChooser();
	
	private ArmorTypeChooser typeChooser = new ArmorTypeChooser();
	
	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private JPanel buttonPanel = new JPanel();

	private SpecificArmor SpecificArmor;

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	SpecificArmorDAO db = new SpecificArmorDAO();

	LibraryPanel owner;

	public CreateSpecificArmorPanel(LibraryPanel owner) {
		SpecificArmor = new SpecificArmor();

		init();
		this.owner = owner;
	}

	public CreateSpecificArmorPanel(SpecificArmor f, LibraryPanel owner) {

		this.owner = owner;
		this.SpecificArmor = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		
		minorRating.setSelectedItem(f.getMinorLevel());
		mediumRating.setSelectedItem(f.getMediumLevel());
		majorRating.setSelectedItem(f.getMajorLevel());
		typeChooser.setSelectedItem(f.getType());
		init();

	}

	public void setSpecificArmor(SpecificArmor f){
		
		this.SpecificArmor = f;

		description.setText(f.getDescription());
		name.setText(f.getName());
		source.setSelectedItem(f.getSource());
		
		minorRating.setSelectedItem(f.getMinorLevel());
		mediumRating.setSelectedItem(f.getMediumLevel());
		majorRating.setSelectedItem(f.getMajorLevel());
		typeChooser.setSelectedItem(f.getType());
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

		main.doLayout(new JLabel("Type"), 0, ypos);
		main.doLayout(typeChooser, 1, ypos);
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
				saveSpecificArmor();
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
		setSpecificArmor((SpecificArmor) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setSpecificArmor((SpecificArmor) owner.getSelected());
	}
	
	public void saveSpecificArmor() {
		SpecificArmor.setName(name.getText());
		
		SpecificArmor.setDescription(description.getText());
		SpecificArmor.setMinorLevel((String)minorRating.getSelectedItem());
		SpecificArmor.setMediumLevel((String)mediumRating.getSelectedItem());
		SpecificArmor.setMajorLevel((String)majorRating.getSelectedItem());
		SpecificArmor.setSource((String)source.getSelectedItem());
		SpecificArmor.setType((String)typeChooser.getSelectedItem());
		int i = db.addOrUpdateSpecificArmor(SpecificArmor);
		SpecificArmor.setId("" + i);
		owner.updateList();
	}

}
