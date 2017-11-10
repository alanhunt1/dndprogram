package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ItemTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ItemTypeChooser() {
		super();
		addItem("Weapon/Ammo");
		addItem("Armor/Shield");
		addItem("Magic");
		addItem("Equip/Misc");

	}
}
