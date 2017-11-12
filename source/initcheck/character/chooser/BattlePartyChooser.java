package initcheck.character.chooser;

import initcheck.database.BattleHistoryDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class BattlePartyChooser extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;

	public BattlePartyChooser() {
		super();
		BattleHistoryDAO db = new BattleHistoryDAO();
		Vector<String> v = db.getParties();

		addItem("All Parties");
		for (int i = 0; i < v.size(); i++) {
			addItem(v.get(i));
		}
	}
}
