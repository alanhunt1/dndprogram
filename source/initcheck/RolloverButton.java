package initcheck;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class RolloverButton extends JButton {

		public RolloverButton(final ImageIcon on, final ImageIcon off){

				setIcon(off);

				addMouseListener(new MouseListener(){
								public void mouseClicked(MouseEvent e) {}
								public void mousePressed(MouseEvent e){}
								public void mouseReleased(MouseEvent e){}
								public void mouseEntered(MouseEvent e){
										setIcon(on);
								}
								public void mouseExited(MouseEvent e){
										setIcon(off);
								}
						});
				
				setContentAreaFilled(false);
				setBorderPainted(false);
				setFocusPainted(false);
		}
}
