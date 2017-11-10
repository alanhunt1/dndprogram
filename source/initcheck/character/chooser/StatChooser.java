package initcheck.character.chooser;

import javax.swing.JComboBox;

public class StatChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public StatChooser() {
		super();

		addItem("STRENGTH");
		addItem("DEXTERITY");
		addItem("CONSTITUTION");
		addItem("INTELLIGENCE");
		addItem("WISDOM");
		addItem("CHARISMA");

	}
}
