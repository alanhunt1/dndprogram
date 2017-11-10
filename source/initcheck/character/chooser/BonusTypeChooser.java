package initcheck.character.chooser;

import javax.swing.JComboBox;

public class BonusTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public BonusTypeChooser() {
		super();
		addItem("");
		addItem("Armor");
		addItem("Circumstance");
		addItem("Competence");
		addItem("Deflection");
		addItem("Dodge");
		addItem("Enhancement");
		addItem("Haste");
		addItem("Inherent");
		addItem("Insight");
		addItem("Luck");
		addItem("Morale");
		addItem("Natural Armor");
		addItem("Profane");
		addItem("Resistance");
		addItem("Sacred");
	}
}
