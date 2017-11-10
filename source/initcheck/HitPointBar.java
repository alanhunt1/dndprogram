package initcheck;

import java.awt.Color;

import javax.swing.JProgressBar;

public class HitPointBar extends JProgressBar {
	
	static final long serialVersionUID = 1;
	
		public HitPointBar(){
				super(0, 100);
				setStringPainted(true);
				setForeground(InitColor.red);
				setBackground(Color.black);
		}

		public HitPointBar(int hp, int current){
				super(0, hp);
				setValue(current);
				setStringPainted(true);
		}

		public void setValues(int hp, int current){
				setMaximum(hp);
				setValue(current);	
		}	

		public void setSelected(boolean selected){
				if (selected){
						setForeground(Color.white);
				}else{
						setForeground(InitColor.red);
				}
		}
}
