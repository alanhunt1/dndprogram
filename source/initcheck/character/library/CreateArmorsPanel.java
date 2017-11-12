package initcheck.character.library;

import initcheck.InitImage;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.ArmorGradeChooser;
import initcheck.character.chooser.ArmorTypeChooser;
import initcheck.character.chooser.ShieldGradeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Armor;
import initcheck.database.ArmorDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateArmorsPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	
	private JTextField name = new JTextField(20);

	private SourceChooser sourceChooser = new SourceChooser();
	
	private JTextField cost = new JTextField(20);

	private JTextField armorBonus = new JTextField(20);

	private JTextField maxDex = new JTextField(20);

	private JTextField armorCheck = new JTextField(20);

	private JTextField speed30 = new JTextField(20);

	private JTextField speed20 = new JTextField(20);

	private JTextField arcaneFail = new JTextField(20);

	private JTextField weight = new JTextField(20);

	private TiledTextArea description = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private TiledGridPanel main = new TiledGridPanel(InitImage.lightRocks);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private JPanel buttonPanel = new JPanel();

	private Armor armor;

	private ArmorTypeChooser atc = new ArmorTypeChooser();

	private JComboBox<?> gradeChooser;

	JPanel typePanel = new JPanel();

	// private JCheckBox armorCheck = new JCheckBox("Armor Check Penalty");

	ArmorDAO db = new ArmorDAO();

	LibraryPanel owner;

	public CreateArmorsPanel(LibraryPanel owner) {

		this.owner = owner;
		this.armor = new Armor();
		init();

	}

	public CreateArmorsPanel(Armor f, LibraryPanel owner) {

		this.owner = owner;
		this.armor = f;

		description.setText(f.getDescription());
		sourceChooser.setSelectedItem(f.getSource());
		name.setText(f.getName());
		cost.setText(f.getCost());
		armorBonus.setText(f.getArmorbonus());
		maxDex.setText(f.getMaxdex());
		armorCheck.setText(f.getCheckpenalty());
		speed30.setText(f.getSpeed30());
		speed20.setText(f.getSpeed20());
		weight.setText(f.getWeight());
		arcaneFail.setText(f.getArcanefail());
		init();

	}
	
	public void setArmor(Armor f) {

		
		this.armor = f;

		description.setText(f.getDescription());
		sourceChooser.setSelectedItem(f.getSource());
		name.setText(f.getName());
		cost.setText(f.getCost());
		armorBonus.setText(f.getArmorbonus());
		maxDex.setText(f.getMaxdex());
		armorCheck.setText(f.getCheckpenalty());
		speed30.setText(f.getSpeed30());
		speed20.setText(f.getSpeed20());
		weight.setText(f.getWeight());
		arcaneFail.setText(f.getArcanefail());
		atc.setSelectedItem(f.getType());
		
	}
	

	public void init() {

		typePanel.add(atc);
		atc.setSelectedItem(armor.getType());

		if (armor.getType() != null && armor.getType().equals("ARMOR")) {
			gradeChooser = new ArmorGradeChooser();
			gradeChooser.setSelectedItem(armor.getGrade());
		} else {
			gradeChooser = new ShieldGradeChooser();
			gradeChooser.setSelectedItem(armor.getGrade());
		}
		typePanel.add(gradeChooser);
		typePanel.setOpaque(false);
		
		int ypos = 0;

		main.doLayout(new JLabel("Source"), 0, ypos, 2, 1);
		main.doLayout(sourceChooser, 2, ypos, 2, 1);
		ypos++;
		
		main.doLayout(new JLabel("Armor Name"), 0, ypos, 2, 1);
		main.doLayout(name, 2, ypos, 2, 1);
		ypos++;

		main.doLayout(typePanel, 0, ypos, 4, 1);
		ypos++;

		main.doLayout(new JLabel("Cost (in GP)"), 0, ypos);
		main.doLayout(cost, 1, ypos);
		main.doLayout(new JLabel("Armor Check Penalty"), 2, ypos);
		main.doLayout(armorCheck, 3, ypos);
		ypos++;

		main.doLayout(new JLabel("Armor Bonus"), 0, ypos);
		main.doLayout(armorBonus, 1, ypos);
		main.doLayout(new JLabel("Speed 30"), 2, ypos);
		main.doLayout(speed30, 3, ypos);
		ypos++;

		main.doLayout(new JLabel("Max Dex Bonus"), 0, ypos);
		main.doLayout(maxDex, 1, ypos);
		main.doLayout(new JLabel("Speed 20"), 2, ypos);
		main.doLayout(speed20, 3, ypos);
		ypos++;

		main.doLayout(new JLabel("Arcane Failure %"), 0, ypos);
		main.doLayout(arcaneFail, 1, ypos);
		main.doLayout(new JLabel("Weight"), 2, ypos);
		main.doLayout(weight, 3, ypos);
		ypos++;

		main.doLayout(descScroll, 0, ypos, 4, 1);
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
				saveArmor();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		atc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchType();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setArmor((Armor) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setArmor((Armor) owner.getSelected());
	}
	
	
	public void switchType() {
		
		invalidate();
		typePanel.remove(gradeChooser);
		if (((String) atc.getSelectedItem()).equals("ARMOR")) {
			gradeChooser = new ArmorGradeChooser();
			gradeChooser.setSelectedItem(armor.getGrade());
		} else {
			gradeChooser = new ShieldGradeChooser();
			gradeChooser.setSelectedItem(armor.getGrade());
		}
		typePanel.add(gradeChooser);
		validate();
	}

	public void saveArmor() {
		armor.setSource((String)sourceChooser.getSelectedItem());
		armor.setName(name.getText());
		armor.setDescription(description.getText());
		armor.setCost(cost.getText());
		armor.setArmorbonus(armorBonus.getText());
		armor.setMaxdex(maxDex.getText());
		armor.setType((String) atc.getSelectedItem());
		armor.setGrade((String) gradeChooser.getSelectedItem());
		armor.setCheckpenalty(armorCheck.getText());
		armor.setSpeed30(speed30.getText());
		armor.setSpeed20(speed20.getText());
		armor.setWeight(weight.getText());
		armor.setArcanefail(arcaneFail.getText());

		Vector<String> errors = armor.validate();
		if (errors.size() > 0){
			String msg = "";
			for (int i = 0; i < errors.size(); i++){
				msg += errors.get(i);
				msg += "\r\n";
			}
			MessageDialog.display("Errors", msg);
		}else{
			db.addOrUpdateArmor(armor);
			owner.updateList();
		}
	}

	

}
