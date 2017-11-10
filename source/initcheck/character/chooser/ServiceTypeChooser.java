package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ServiceTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ServiceTypeChooser() {
		super();
		addItem("Holy");
		addItem("Arcane");
		addItem("Mundane");
		addItem("Illegal");
	}
}
