package initcheck.generator;

import javax.swing.JComboBox;

public class StoreTypeChooser extends JComboBox {
	private static final long serialVersionUID = 1L;

	public StoreTypeChooser() {
		super();
		addItem("General");
		addItem("Magic");
		addItem("Arms and Armor");
		addItem("Clothing");
		addItem("Illicit");
		addItem("Holy");
	}
}
