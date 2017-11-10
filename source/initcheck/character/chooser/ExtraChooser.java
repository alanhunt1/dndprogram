package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ExtraChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ExtraChooser() {
		super();
		addItem("");
		addItem("FEAT(S)");
		addItem("SKILL POINT(S)");
		addItem("METAMAGIC FEAT(S)");
	}
}
