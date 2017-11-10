package initcheck.character.chooser;

import javax.swing.JComboBox;

public class SpellSchoolChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public SpellSchoolChooser() {
		super();
		addItem("Abjuration");
		addItem("Conjuration");
		addItem("Divination");
		addItem("Enchantment");
		addItem("Evocation");
		addItem("Illusion");
		addItem("Necromancy");
		addItem("Transmutation");
		addItem("Universal");
	}
}
