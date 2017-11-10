package initcheck.dungeon;

import javax.swing.JComboBox;

public class DrawStyleChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DrawStyleChooser() {
		super();
		addItem("Point");
		addItem("Line");
		addItem("Area");
		addItem("Flood");

	}
}
