package initcheck.character.chooser;

import javax.swing.JComboBox;

public class HitDieChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public HitDieChooser() {
		super();
		addItem("D4");
		addItem("D6");
		addItem("D8");
		addItem("D10");
		addItem("D12");
		addItem("D20");
	}
}
