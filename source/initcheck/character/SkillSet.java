package initcheck.character;

import initcheck.database.PlayerSkills;
import initcheck.database.Skill;

import java.io.Serializable;
import java.util.Vector;

public class SkillSet implements Serializable {

	private static final long serialVersionUID = 1L;

	Vector<PlayerSkills> skillList = new Vector<PlayerSkills>();

	public SkillSet() {

	}

	public int getSkillsUsed() {
		return getSkillsUsed(null);
	}

	public int getCrossClassSkillsUsed() {
		int ranks = 0;
		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getRanks() != null
					&& !sk.getRanks().equals(sk.getEffectiveRanks())) {
				ranks += Integer.parseInt(sk.getRanks());
			}
		}
		return ranks;
	}

	public int getSkillsUsed(String classId) {
		
		int ranks = 0;
		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (classId == null || sk.getClassId().equals(classId)) {
				if (sk.getRanks() != null) {
					ranks += Integer.parseInt(sk.getRanks());
				}
			}
		}
		return ranks;
	}

	public int getClassSkillsUsed() {
		int ranks = 0;
		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);

			if (sk.getRanks() != null
					&& sk.getRanks().equals(sk.getEffectiveRanks())) {
				ranks += Integer.parseInt(sk.getRanks());
			}

		}
		return ranks;
	}

	public boolean hasSkill(String s, String cclass) {

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equals(s) && sk.getClassId().equals(cclass)) {
				return true;
			}
		}
		return false;
	}

	public Calculation getScore(Calculation calc, String s) {

		double ranks = Double.parseDouble(calc.getDisplayValue());

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equals(s) && sk.getRanks() != null) {
				int mod = Integer.parseInt(sk.getMods());
				if (mod > 0) {
					ranks += mod;
					calc.addElement("User Modifier : " + mod);
				}
				int misc = Integer.parseInt(sk.getMisc());
				if (misc > 0) {
					ranks += misc;
					calc.addElement("Misc Modifier : " + misc);
				}
			}
		}

		calc.setDisplayValue("" + ranks);

		return calc;
	}

	public int getMisc(String s) {

		int ranks = 0;

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equals(s) && sk.getRanks() != null) {
				
				int misc = Integer.parseInt(sk.getMisc());
				if (misc > 0) {
					ranks += misc;
					
				}
			}
		}

		return ranks;
	}

	public int getMod(String s) {

		int ranks = 0;

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equals(s) && sk.getRanks() != null) {
				int mod = Integer.parseInt(sk.getMods());
				if (mod > 0) {
					ranks += mod;
					
				}
				
			}
		}

		return ranks;
	}
	
	
	public Calculation getRankCalculation(String s) {

		Calculation calc = new Calculation();

		double ranks = 0;
		boolean bonusApplied = false;
		boolean bonus2Applied = false;

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equalsIgnoreCase(s) && sk.getEffectiveRanks() != null) {

				ranks += Double.parseDouble(sk.getEffectiveRanks());
				calc.addElement("Effective Ranks " + sk.getEffectiveRanks());

				// check for synergy bonus
				if (sk.getSynBonus() != null && !sk.getSynBonus().equals("0")
						&& !bonusApplied) {

					double synRanks = Double.parseDouble(getBaseRanks(Integer
							.parseInt(sk.getSynValue())));
					if (synRanks >= Double.parseDouble(sk.getSynRanks())) {
						ranks += Double.parseDouble(sk.getSynBonus());
						calc.addElement("Synergy Bonus " + sk.getSynBonus());
					}
					bonusApplied = true;
				}

				// check for synergy bonus 2
				if (sk.getSynBonus2() != null && !sk.getSynBonus2().equals("0")
						&& !bonus2Applied) {

					double synRanks2 = Double.parseDouble(getBaseRanks(Integer
							.parseInt(sk.getSynValue2())));
					if (synRanks2 >= Double.parseDouble(sk.getSynRanks2())) {
						ranks += Double.parseDouble(sk.getSynBonus2());
						calc.addElement("Synergy Bonus " + sk.getSynBonus());
					}
					bonus2Applied = true;
				}
			}
		}

		calc.setDisplayValue("" + ranks);
		return calc;
	}

	public Skill getSkill(String s) {
		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getSkill().equals(s) && sk.getRanks() != null) {
				return sk;
			}
		}
		return null;
	}

	public String getBaseRanks(int id) {
		double ranks = 0;

		for (int i = 0; i < skillList.size(); i++) {
			Skill sk = (Skill) skillList.get(i);
			if (sk.getId().equals("" + id) && sk.getRanks() != null) {

				ranks += Double.parseDouble(sk.getEffectiveRanks());

			}
		}
		return "" + ranks;
	}

	public void add(PlayerSkills s) {
		skillList.add(s);
	}

	public int size() {
		return skillList.size();
	}

	public PlayerSkills get(int i) {
		return skillList.get(i);
	}

	public Vector<PlayerSkills> getSkillList() {
		return skillList;
	}

	public void setSkillList(Vector<PlayerSkills> skillList) {
		this.skillList = skillList;
	}

}
