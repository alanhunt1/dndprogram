package initcheck.character.chooser;

import javax.swing.JComboBox;

public class PrereqTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public PrereqTypeChooser() {
		super();
		addItem("--SELECT--");
		addItem("Feat");
		addItem("Feat Class");
		addItem("Proficiency");
		addItem("Stat");
		addItem("Skill");
		addItem("Level");
		addItem("Ability");
		addItem("Alignment");
		addItem("Race");
		addItem("Class");
		addItem("Domain");
		addItem("Save");
		addItem("Min Size");
		addItem("Max Size");
		addItem("Misc");
	}
}
