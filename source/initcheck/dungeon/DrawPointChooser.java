package initcheck.dungeon;

import javax.swing.JComboBox;

public class DrawPointChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DrawPointChooser() {
		super();
		addItem("Wall");
		addItem("Door");
		addItem("Secret Door");
		addItem("Corridor");
		addItem("Hole");
		addItem("Water");
		addItem("Bridge");
		addItem("Stair");
		addItem("Land");
		addItem("Trap");
	}
}
