package initcheck.character.chooser;

import javax.swing.JComboBox;

public class WeaponCategoryChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WeaponCategoryChooser() {
		super();
		addItem("Simple");
		addItem("Martial");
		addItem("Exotic");
		addItem("Ammunition");
	}
}
