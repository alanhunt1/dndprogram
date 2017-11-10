package initcheck.character.chooser;

import javax.swing.JComboBox;

public class TrapResetTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public TrapResetTypeChooser() {
		super();
		addItem("Manual");
		addItem("Automatic");
		addItem("None");
		addItem("Repair");
		
		
	}
}
