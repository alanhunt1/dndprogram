package initcheck.character.chooser;

import javax.swing.JComboBox;

public class SaveTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public SaveTypeChooser() {
		super();
		addItem("FORTITUDE");
		addItem("REFLEX");
		addItem("WILL");
	}
}
