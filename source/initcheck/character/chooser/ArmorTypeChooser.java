package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ArmorTypeChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ArmorTypeChooser() {
		super();
		addItem("ARMOR");
		addItem("SHIELD");

	}
}
