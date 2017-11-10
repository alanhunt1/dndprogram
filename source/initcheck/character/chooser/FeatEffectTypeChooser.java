package initcheck.character.chooser;

import javax.swing.JComboBox;

public class FeatEffectTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public FeatEffectTypeChooser() {
		super();
		addItem("--SELECT--");
		addItem("FEAT");
		addItem("PROFICIENCY");
		addItem("SKILL");
		addItem("SAVE");
	}
}
