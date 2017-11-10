package initcheck.character.chooser;

import javax.swing.JComboBox;

public class PartyTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public PartyTypeChooser() {
		super();
		addItem("PC");
		addItem("DM");
	}
}
