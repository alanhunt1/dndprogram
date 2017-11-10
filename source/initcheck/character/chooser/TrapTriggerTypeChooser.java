package initcheck.character.chooser;

import javax.swing.JComboBox;

public class TrapTriggerTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public TrapTriggerTypeChooser() {
		super();
		addItem("Proximity");
		addItem("Location");
		addItem("Touch");
		addItem("Timed");
		addItem("Spell");
		
	}
}
