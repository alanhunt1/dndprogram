package initcheck.character.chooser;

import javax.swing.JComboBox;

public class TrapTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public TrapTypeChooser() {
		super();
		addItem("Mechanical");
		addItem("Magic Device");
		addItem("Spell");
		
	}
}
