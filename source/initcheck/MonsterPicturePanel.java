package initcheck;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MonsterPicturePanel extends JPanel{
	static final long serialVersionUID = 1;
		private ImageIcon display;
		private InitLogger logger = new InitLogger(this);

		public MonsterPicturePanel(String picture, String name){
				logger.log("Creating a new "+name+" with "+picture);
				display = new ImageIcon("images/"+picture);
				JButton displayBtn = new JButton(display);
				displayBtn.setContentAreaFilled(false);
				displayBtn.setBorderPainted(false);
				displayBtn.setFocusPainted(false);
				setLayout(new BorderLayout());
				add(displayBtn, BorderLayout.CENTER);
				add(new JLabel(name), BorderLayout.SOUTH);
			
		}
		
}
