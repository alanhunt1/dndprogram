package initcheck;

import javax.swing.JDialog;


public class MonsterPictureDialog extends JDialog{
	static final long serialVersionUID = 1;
		public MonsterPictureDialog(final InitProgram owner, 
																String picture, String name){

				super(owner.getFrame(), name, false);
				getContentPane().add(new MonsterPicturePanel(picture, name));
				pack();
				setVisible(true);
		}
}
