package initcheck.character;

import java.awt.Font;

import javax.swing.JLabel;

public class BigLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	static Font bigFont = new Font("Courier", Font.BOLD, 30);

	public BigLabel(String s) {
		super(s);
		setFont(bigFont);
	}

}
