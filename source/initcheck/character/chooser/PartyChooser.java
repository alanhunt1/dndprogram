package initcheck.character.chooser;

import initcheck.database.Party;
import initcheck.database.PartyDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class PartyChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public PartyChooser() {
		super();
		PartyDAO db = new PartyDAO();
		Vector<Party> v = db.getParties();
		//addItem("All Parties");
		for (int i = 0; i < v.size(); i++) {
			addItem(v.get(i));
		}
	}
	
	public void setCampaign(String campaignId, boolean dmMode){
		removeAllItems();
		PartyDAO db = new PartyDAO();
		Party p = new Party();
		p.setCampaignId(campaignId);
		if (!dmMode){
			p.setPartyType("PC");
		}
		Vector<Party> v = db.selectParty(p);
		p.setName("All Parties");
		addItem(p);
		for (int i = 0; i < v.size(); i++) {
			
			addItem(v.get(i));
		}
	}
	
	public void setSelectedParty(Party w) {
		for (int i = 0; i < getItemCount(); i++) {
			Party w2 = (Party) getItemAt(i);
			if (w2.getId() != null && w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
