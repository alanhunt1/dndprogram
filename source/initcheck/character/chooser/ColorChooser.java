package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ColorChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public ColorChooser() {
		super();
		addItem("Green");
		addItem("Blue");
		addItem("Red");
		addItem("Yellow");
		addItem("Orange");
		addItem("Gray");

	}
}
