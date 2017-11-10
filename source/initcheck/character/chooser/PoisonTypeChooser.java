package initcheck.character.chooser;

import javax.swing.JComboBox;

public class PoisonTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public PoisonTypeChooser() {
		super();

		addItem("Contact");
		addItem("Ingested");
		addItem("Inhaled");
		addItem("Injury");
	}
}
