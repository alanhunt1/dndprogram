package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ProficiencyTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ProficiencyTypeChooser() {
		super();
		addItem("WEAPON");
		addItem("ARMOR");
		addItem("SHIELD");
	}
}
