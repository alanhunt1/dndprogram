package initcheck.character.chooser;

import javax.swing.JComboBox;

public class WeaponRangeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WeaponRangeChooser() {
		super();
		addItem("");
		addItem("Melee");
		addItem("Ranged");
	}
}
