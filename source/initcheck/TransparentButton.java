package initcheck;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TransparentButton extends JButton {
	
	static final long serialVersionUID = 1;

	static final Color shadow = new Color(105, 19, 15);

	static final Color hlight = new Color(162, 80, 30);

	static final Color lhlight = new Color(182, 39, 20);

	static final Color background = new Color(148, 24, 16);

	public TransparentButton(ImageIcon s) {
		super(s);
		// setFont(font);
		//setBackground(background);
		setBorder(null);// lhlight
		setOpaque(false);
		//setForeground(Color.white);
		setPreferredSize(new Dimension(20, 20));
	}

	public TransparentButton(ImageIcon s, int w, int h) {
		super(s);
		// setFont(font);
		//setBackground(background);
		setBorder(null);// lhlight
		setOpaque(false);
		//setForeground(Color.white);
		setPreferredSize(new Dimension(w, h));
	}
	
}
