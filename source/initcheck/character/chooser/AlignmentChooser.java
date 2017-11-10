package initcheck.character.chooser;

import javax.swing.JComboBox;

public class AlignmentChooser extends JComboBox {
	private static final long serialVersionUID = 1L;

	public AlignmentChooser() {
		super();

		addItem("LAWFUL GOOD");
		addItem("NEUTRAL GOOD");
		addItem("CHAOTIC GOOD");
		addItem("LAWFUL NEUTRAL");
		addItem("TRUE NEUTRAL");
		addItem("CHAOTIC NEUTRAL");
		addItem("LAWFUL EVIL");
		addItem("NEUTRAL EVIL");
		addItem("CHAOTIC EVIL");
		addItem("ANY GOOD");
		addItem("ANY EVIL");
		addItem("ANY NEUTRAL");
		addItem("ANY LAWFUL");
		addItem("ANY CHAOTIC");
	}
}
