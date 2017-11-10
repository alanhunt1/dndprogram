package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ShieldGradeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ShieldGradeChooser() {
		super();
		addItem("Buckler");
		addItem("Small");
		addItem("Large");
		addItem("Tower");
	}
}