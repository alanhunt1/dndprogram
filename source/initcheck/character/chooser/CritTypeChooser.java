package initcheck.character.chooser;

import javax.swing.JComboBox;

public class CritTypeChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public CritTypeChooser() {
		super();

		addItem("Edged");
		addItem("Blunt");
		addItem("vs Animals");
		addItem("Piercing");

	}
}
