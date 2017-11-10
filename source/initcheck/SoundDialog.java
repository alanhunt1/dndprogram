package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SoundDialog extends JDialog {
		
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");
		private ImageIcon display = new ImageIcon("images/dietcokebig.gif");
		
		
		private int width = 300;
		private int height = 600;
		
		public void setHeight (int i){
				height = i;
				init();
		}

		public void setSound(String sound){
				
				init();
		}

		public void setBackgroundImage(String image){
				bgImage = new ImageIcon(image);
				init();
		}
		
		public void setDisplayImage(String image){
				display = new ImageIcon(image);
				init();
		}
		
	

		public SoundDialog(final InitClient owner, String image, 
											 String sound){
				
				setDisplayImage(image);
				init();
				
		}
		
		
		public SoundDialog(final InitClient owner){
				
				super(owner.getFrame(), "GET ME A COKE!!!", true);
				
				init();
											
		}
		
		private void init(){

				ImageIcon icon = new ImageIcon("images/deadoff.gif");
				ImageIcon icon2 = new ImageIcon("images/deadon.gif");
				RolloverButton iconButton = new RolloverButton(icon2, icon);
				JButton displayBtn = new JButton(display);
				displayBtn.setContentAreaFilled(false);
				displayBtn.setBorderPainted(false);
				displayBtn.setFocusPainted(false);
				
				TiledPanel contents = new TiledPanel(bgImage);
				contents.setLayout(new BorderLayout());
				iconButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});				
							
				contents.add(displayBtn, BorderLayout.CENTER);
				JPanel btnPanel = new JPanel();
				btnPanel.setOpaque(false);
				btnPanel.add(iconButton);
				contents.add(btnPanel, BorderLayout.SOUTH);
				
				getContentPane().add(contents);

				setSize(new Dimension(width, height));
				setLocation(100, 100);
				
		}

	
		
}
