package initcheck.character.chooser;

import initcheck.database.Campaign;
import initcheck.database.CampaignDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class CampaignChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public CampaignChooser() {
		super();
		CampaignDAO db = new CampaignDAO();
		Vector<Campaign> v = db.getCampaigns();
		for (int i = 0; i < v.size(); i++) {
			Campaign f = (Campaign) v.get(i);
			addItem(f);
		}

	}

	public void setSelectedCampaign(Campaign w) {
		for (int i = 0; i < getItemCount(); i++) {
			Campaign w2 = (Campaign) getItemAt(i);
			if (w2.getId().equals(w.getId())) {
				setSelectedIndex(i);
				break;
			}
		}

	}
}
