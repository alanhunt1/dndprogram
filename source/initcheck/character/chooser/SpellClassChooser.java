package initcheck.character.chooser;

import initcheck.database.Domain;
import initcheck.database.DomainDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class SpellClassChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public SpellClassChooser() {
		super();

		// add the spellcasting classes
		addItem("Bard");
		addItem("Beastmaster");
		addItem("Blackguard");
		addItem("Cleric");
		addItem("Druid");
		addItem("Paladin");
		addItem("Ranger");
		addItem("Sorceror/Wizard");

		// add the domains
		DomainDAO ddb = new DomainDAO();
		Vector<Domain> domains = ddb.getDomains();
		for (int i = 0; i < domains.size(); i++){
			Domain d = domains.get(i);
			addItem(d.getDomainName());
		}
		
	}
}
