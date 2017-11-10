package initcheck.character.chooser;

import javax.swing.JComboBox;

public class RarityChooser extends JComboBox {
	private static final long serialVersionUID = 1L;

	public RarityChooser() {
		super();

		addItem("unavailable");
		addItem("common");
		addItem("uncommon");
		addItem("rare");
		addItem("very rare");
		
	}
}
