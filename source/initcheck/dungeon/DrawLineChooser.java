package initcheck.dungeon;

import javax.swing.JComboBox;

public class DrawLineChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DrawLineChooser() {
		super();
		addItem("Wall");
		addItem("Corridor");
		addItem("Hole");
		addItem("Water");
		addItem("Bridge");
		addItem("Land");
	}
}
