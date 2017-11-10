package initcheck.character.chooser;

import initcheck.database.Domain;
import initcheck.database.DomainDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class DomainChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public DomainChooser() {
		super();
		DomainDAO db = new DomainDAO();
		Vector<Domain> v = db.getDomains();
		for (int i = 0; i < v.size(); i++) {
			Domain f = (Domain) v.get(i);
			addItem(f);
		}
	}

	public void setSelectedSkill(Domain s) {
		for (int i = 0; i < getItemCount(); i++) {
			Domain sk = (Domain) getItemAt(i);
			if (s.getId().equals(sk.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
