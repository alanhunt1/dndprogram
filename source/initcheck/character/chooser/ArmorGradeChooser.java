package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ArmorGradeChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ArmorGradeChooser() {
		super();
		addItem("Light");
		addItem("Medium");
		addItem("Heavy");
	}
}
