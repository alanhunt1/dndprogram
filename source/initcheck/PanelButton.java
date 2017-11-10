package initcheck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;

public class PanelButton extends JButton {
	
	static final long serialVersionUID = 1;

	static final Color shadow = new Color(105, 19, 15);

	static final Color hlight = new Color(162, 80, 30);

	static final Color lhlight = new Color(182, 39, 20);

	static final Color background = new Color(148, 24, 16);

	public PanelButton(String s, boolean size) {
		super(s);
		// setFont(font);
		setBackground(background);
		//setBorder(new BasicBorders.ButtonBorder(shadow,// shadow
		//		Color.black,// darkshadow
		//		hlight,// hlight
		//		lhlight));// lhlight
		setForeground(Color.white);
		if (size) {
			setPreferredSize(new Dimension(100, 20));
		}
		setBorder(null);
	    setMargin(new Insets(1,1,1,1));  
	}

	public PanelButton(String s) {
		super(s);
		// setFont(font);
		setBackground(background);
		//setBorder(new BasicBorders.ButtonBorder(shadow,// shadow
	//			Color.black,// darkshadow
	//			hlight,// hlight
	//			lhlight));// lhlight
		setForeground(Color.white);
		setBorder(null);
		setPreferredSize(new Dimension(100, 20));
		setMargin(new Insets(1,1,1,1));  
	}

	public PanelButton(String s, int width) {
		super(s);
		// setFont(font);
		setBackground(background);
		//setBorder(new BasicBorders.ButtonBorder(shadow,// shadow
		//		Color.black,// darkshadow
		//		hlight,// hlight
		//		lhlight));// lhlight
		setForeground(Color.white);
		setBorder(null);
		setMargin(new Insets(1,1,1,1));  
		setPreferredSize(new Dimension(width, 20));
	}
}
