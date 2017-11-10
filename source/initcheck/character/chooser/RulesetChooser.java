package initcheck.character.chooser;

import java.util.Vector;

import javax.swing.JComboBox;

import initcheck.database.Ruleset;
import initcheck.database.RulesetDAO;

public class RulesetChooser extends JComboBox<Ruleset> {
	private static final long serialVersionUID = 1L;

	public RulesetChooser() {
		super();
		addItem(new Ruleset());
		RulesetDAO db = new RulesetDAO();
		System.out.println("GETTING RULESETS");
		Vector<Ruleset> v = db.selectRuleset(new Ruleset());
		for (int i = 0; i < v.size(); i++) {
			
			Ruleset f = (Ruleset) v.get(i);
			addItem(f);
		}
	}

	public RulesetChooser(String s) {
		super();
		addItem(new Ruleset());
		RulesetDAO db = new RulesetDAO();
		Vector<Ruleset> v = db.selectRuleset(new Ruleset());
		for (int i = 0; i < v.size(); i++) {
			Ruleset f = (Ruleset) v.get(i);
			addItem(f);
		}
	}
	public void setSelectedRuleset(String id) {
		if (id == null)
			return;
		for (int i = 0; i < getItemCount(); i++) {
			Ruleset wa = (Ruleset) getItemAt(i);
			if (id.equals(wa.getRulesetId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
	public void setSelectedRuleset(Ruleset w) {
		if (w == null || w.getRulesetName() == null || w.getRulesetId() == null)
			return;
		for (int i = 0; i < getItemCount(); i++) {
			Ruleset wa = (Ruleset) getItemAt(i);
			if (w.getRulesetId().equals(wa.getRulesetId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
