package initcheck.character.chooser;

import initcheck.database.Skill;
import initcheck.database.SkillDAO;

import java.util.Vector;

import javax.swing.JComboBox;

public class SkillChooser extends JComboBox {

	private static final long serialVersionUID = 1L;

	public SkillChooser() {
		super();
		SkillDAO db = new SkillDAO();
		Vector<Skill> v = db.getSkills();
		for (int i = 0; i < v.size(); i++) {
			Skill f = (Skill) v.get(i);
			addItem(f);
		}
	}

	public void setSelectedSkill(Skill s) {
		for (int i = 0; i < getItemCount(); i++) {
			Skill sk = (Skill) getItemAt(i);
			if (s.getId().equals(sk.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}

	public void setSelectedSkill(String s) {
		for (int i = 0; i < getItemCount(); i++) {
			Skill sk = (Skill) getItemAt(i);
			if (s.equals(sk.getId())) {
				setSelectedIndex(i);
				break;
			}
		}
	}
}
