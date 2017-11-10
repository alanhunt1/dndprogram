package initcheck.dungeon;

import javax.swing.JComboBox;

public class RenderTypeChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public RenderTypeChooser() {
		super();
		addItem("Color");
		addItem("Pattern");
		addItem("Image");
		
	}

}
