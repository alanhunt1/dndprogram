package initcheck.character.library;

import initcheck.InitImage;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.ArmorTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.ArmorAbilities;
import initcheck.database.ArmorAbilitiesDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateArmorAbilitiesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private ArmorTypeChooser armorType = new ArmorTypeChooser();

	private JTextField bonus = new JTextField(5);

	private JTextField casterLevel = new JTextField(5);

	private JTextField prereq = new JTextField(20);

	private JTextField cost = new JTextField(5);

	private TiledTextArea description = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private TiledGridPanel main = new TiledGridPanel(InitImage.lightRocks);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");
	
	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private ArmorAbilities armorAbilities;

	ArmorAbilitiesDAO db = new ArmorAbilitiesDAO();

	LibraryPanel owner;

	private SourceChooser sourceChooser = new SourceChooser();
	
	public CreateArmorAbilitiesPanel(LibraryPanel owner) {

		this.owner = owner;
		this.armorAbilities = new ArmorAbilities();
		init();

	}

	public CreateArmorAbilitiesPanel(ArmorAbilities f, LibraryPanel owner) {
		this.owner = owner;
		setArmorAbilities(f);

		init();
	}

	public void setArmorAbilities(ArmorAbilities f){
		this.armorAbilities = f;
		description.setText(f.getDescription());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		name.setText(f.getAbilityName());
		armorType.setSelectedItem(f.getArmorType());
		bonus.setText(f.getBonus());
		casterLevel.setText(f.getCasterLevel());
		prereq.setText(f.getPrereq());
		cost.setText(f.getCost());
		sourceChooser.setSelectedItem(f.getSource());
	}
	
	public void init() {

		

		int ypos = 0;

		main.doLayout(new JLabel("Source"), 0, ypos);
		main.doLayout(sourceChooser, 1, ypos);
		ypos++;

		
		main.doLayout(new JLabel("Ability Name"), 0, ypos);
		main.doLayout(name, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Armor/Shield"), 0, ypos);
		main.doLayout(armorType, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Enhancement Cost"), 0, ypos);
		main.doLayout(bonus, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Caster Level"), 0, ypos);
		main.doLayout(casterLevel, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("GP Cost"), 0, ypos);
		main.doLayout(cost, 1, ypos);
		ypos++;

		main.doLayout(new JLabel("Prereqs"), 0, ypos);
		main.doLayout(prereq, 1, ypos);
		ypos++;

		main.setWeightX(1.0);
		main.setWeightY(1.0);
		
		main.doLayout(descScroll, 0, ypos, 4, 1);
		ypos++;
		
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveArmorAbilities();
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
		setArmorAbilities((ArmorAbilities) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setArmorAbilities((ArmorAbilities) owner.getSelected());
	}
	
	public void saveArmorAbilities() {
		armorAbilities.setAbilityName(name.getText());
		armorAbilities.setDescription(description.getText());
		armorAbilities.setArmorType((String) armorType.getSelectedItem());
		armorAbilities.setBonus(bonus.getText());
		armorAbilities.setCost(cost.getText());
		armorAbilities.setCasterLevel(casterLevel.getText());
		armorAbilities.setPrereq(prereq.getText());
		armorAbilities.setSource((String)sourceChooser.getSelectedItem());
		Vector<String> errors = armorAbilities.validate();
		if (errors.size() > 0){
			String msg = "";
			for (int i = 0; i < errors.size(); i++){
				msg += errors.get(i);
				msg += "\r\n";
			}
			MessageDialog.display("Errors", msg);
		}else{
			db.addOrUpdateArmorAbilities(armorAbilities);
			owner.updateList();
		}
	}



}
