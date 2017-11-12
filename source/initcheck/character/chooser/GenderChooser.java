package initcheck.character.chooser;

import javax.swing.JComboBox;

public class GenderChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public GenderChooser() {
		super();
		addItem("M");
		addItem("F");

	}
}
