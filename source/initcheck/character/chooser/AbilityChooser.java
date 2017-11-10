package initcheck.character.chooser;

import javax.swing.JComboBox;

public class AbilityChooser extends JComboBox {
	private static final long serialVersionUID = 1L;

	public AbilityChooser() {
		super();
		addItem("BAB");
		addItem("SPEAK LANGUAGE");
		addItem("CAST ARCANE LEVEL");
		addItem("CAST DIVINE LEVEL");
		addItem("SPELL RESISTANCE");
		addItem("CAST ARCANE UNPREPARED");
	}
}
