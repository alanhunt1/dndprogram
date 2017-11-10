package initcheck.character.chooser;

import javax.swing.JComboBox;

public class RacialAbilityTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public RacialAbilityTypeChooser() {
		super();
		addItem("EXTRA");
		addItem("SPECIAL");
		addItem("STAT");
		addItem("SKILL");
		addItem("PROFICIENCY");
		addItem("SAVING THROW");
		addItem("BONUS");
		addItem("SPEAKS");
	}
}
