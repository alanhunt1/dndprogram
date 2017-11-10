package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ExtraTimingChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ExtraTimingChooser() {
		super();
		addItem("");
		addItem("AT 1st LEVEL");
		addItem("PER LEVEL");
		addItem("EVERY 2 LEVELS");
	}
}
