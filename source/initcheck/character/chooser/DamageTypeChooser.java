package initcheck.character.chooser;

import javax.swing.JComboBox;

public class DamageTypeChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public DamageTypeChooser() {
		super();
		addItem("");
		addItem("Blunt");
		addItem("Piercing");
		addItem("Slashing");
		addItem("Blunt, Piercing");
		addItem("Blunt, Slashing");
		addItem("Slashing, Piercing");
		addItem("Blunt, Piercing, Slashing");
	}
}
