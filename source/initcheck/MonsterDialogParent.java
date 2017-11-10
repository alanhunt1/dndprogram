package initcheck;

import javax.swing.JFrame;

public interface MonsterDialogParent{
		
		public JFrame getFrame();
		public void runEncounter(Encounter e, boolean newEncounter);
		public void saveEncounter(Encounter e);
}
