package initcheck.dungeon;

import initcheck.InitColor;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LightLabel extends JPanel {

	private static final long serialVersionUID = 1L;

	public LightLabel(String s) {
		super();
		FlowLayout panelLayout = new FlowLayout(FlowLayout.RIGHT);
		panelLayout.setHgap(0);
		panelLayout.setVgap(0);
		setLayout(panelLayout);
		JLabel label = new JLabel(s);
		label.setForeground(Color.white);
		add(label);

		setBackground(InitColor.red);
	}

	public LightLabel(String s, int align) {
		super();
		FlowLayout panelLayout = new FlowLayout(FlowLayout.RIGHT);
		panelLayout.setHgap(0);
		panelLayout.setVgap(0);
		setLayout(panelLayout);
		JLabel label = new JLabel(s, align);
		label.setForeground(Color.white);
		add(label);

		setBackground(InitColor.red);
	}
}
