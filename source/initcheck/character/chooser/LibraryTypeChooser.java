package initcheck.character.chooser;

import javax.swing.JComboBox;

public class LibraryTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;
	
	public LibraryTypeChooser(){
		super();
		addItem("Feat");
		addItem("Skill");
		addItem("Class");
		addItem("Class Ability");
		addItem("Race");
		addItem("Armor");
		addItem("Specific Armor");
		addItem("Weapon");
		addItem("Specific Weapon");
		addItem("Spell");
		addItem("Domain");
		addItem("Equipment");
		addItem("Service");
		addItem("Material");
		addItem("Weapon Ability");
		addItem("Armor Ability");
		addItem("Wonderous Item");
		addItem("Artifact");
		addItem("Ring");
		addItem("Rod");
		addItem("Staff");
		addItem("Monster");
		addItem("Deity");
		addItem("Trap");
		addItem("Poison");
		
	}
	
}
