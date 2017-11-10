package initcheck.character.chooser;

import initcheck.DCharacter;
import initcheck.DCharacterListing;
import initcheck.database.InitDBC;

import java.util.Vector;

import javax.swing.JComboBox;

public class PlayerChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public PlayerChooser() {
		super();
		InitDBC db = new InitDBC();
		Vector<DCharacterListing> v = db.getPartyNames("All Parties", "All Players");
		//addItem("All Parties");
		for (int i = 0; i < v.size(); i++) {
			addItem(v.get(i));
		}
	}
	
	public void setParty(String party){
		removeAllItems();
		InitDBC db = new InitDBC();
		Vector<DCharacterListing> v = db.getPartyNames(party, "All Players");
		
		for (int i = 0; i < v.size(); i++) {
			
			addItem(v.get(i));
		}
	}
	
	public void setSelectedPlayer(DCharacter w) {
		for (int i = 0; i < getItemCount(); i++) {
			DCharacter w2 = (DCharacter) getItemAt(i);
			if (w2.getID() == w.getID()) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
