package initcheck.character.chooser;

import javax.swing.JComboBox;

public class WeaponUseChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public WeaponUseChooser() {
		super();
		addItem("Normal");
		addItem("Two Handed");
		addItem("Primary");
		addItem("Secondary");
		addItem("Double Toss 1");
		addItem("Double Toss 2");
		addItem("Natural Weapon 1");
		addItem("Natural Weapon 2");
		addItem("Natural Weapon 3");
		addItem("Two Bladed");
	}
}
