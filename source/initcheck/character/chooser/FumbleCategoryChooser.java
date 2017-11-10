package initcheck.character.chooser;

import javax.swing.JComboBox;

public class FumbleCategoryChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public FumbleCategoryChooser() {
		super();
		addItem("Frequent");
		addItem("Infrequent");
		addItem("Rare");
		addItem("Very Rare");
		addItem("One In A Million");
	}
}
