package initcheck.character.chooser;

import javax.swing.JComboBox;

public class ArtifactTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public ArtifactTypeChooser() {
		super();

		addItem("Minor");
		addItem("Major");
		

	}
}
