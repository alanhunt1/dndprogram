package initcheck.dungeon;

import javax.swing.JComboBox;

public class DrawAreaChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DrawAreaChooser() {
		super();
		addItem("Wall");
		addItem("Room");
		addItem("Corridor");
		addItem("Hole");
		addItem("Water");
		addItem("Bridge");
		addItem("Land");
	}
}
