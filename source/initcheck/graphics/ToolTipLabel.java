package initcheck.graphics;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JToolTip;

public class ToolTipLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public ToolTipLabel() {
		super();
	}

	public ToolTipLabel(String s) {
		super("<html><u>" + s + "</u></html>");
		setForeground(Color.blue);
	}

	public JToolTip createToolTip() {
		return new JMultiLineToolTip();
	}

}
