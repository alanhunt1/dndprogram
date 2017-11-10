package initcheck.character.chooser;

import javax.swing.JComboBox;

public class LevelChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public LevelChooser() {
		super();
		addItem("CHARACTER");
		addItem("SPELLCASTER");
		addItem("ARCANE SPELLCASTER");
		addItem("ARCANE UNPREPARED");
		addItem("DIVINE SPELLCASTER");
	}
}
