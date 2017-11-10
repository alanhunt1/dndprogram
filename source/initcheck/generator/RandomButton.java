package initcheck.generator;

import initcheck.InitImage;

import java.awt.Dimension;

import javax.swing.JButton;

public class RandomButton extends JButton{

	private static final long serialVersionUID = 1L;

	public RandomButton(){
		super();
		setIcon(InitImage.dice);
		setPreferredSize(new Dimension(20,20));
	}
	
}
