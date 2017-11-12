package initcheck.character.chooser;

import javax.swing.JComboBox;

public class CasterClassChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public CasterClassChooser() {
		super();

		addItem("Sorceror");
		addItem("Wizard");
		addItem("Bard");
		addItem("Cleric");
		addItem("Paladin");
		addItem("Druid");
		addItem("Ranger");
	}
}
