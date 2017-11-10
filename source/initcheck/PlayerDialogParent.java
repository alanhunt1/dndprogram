package initcheck;

import java.util.Vector;

import javax.swing.JFrame;

public interface PlayerDialogParent{
		
		public JFrame getFrame();
		public void setChars(Vector<DCharacter> chars, String party);
		public void setOpponents(Vector<DCharacter> chars);
}
