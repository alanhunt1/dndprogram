package initcheck.database;

public class PlayerSkills extends Skill implements Exportable {

	private static final long serialVersionUID = 1L;

	public PlayerSkills() {

	}

	public PlayerSkills(Skill s) {
		init(s);
	}

	public void init(Skill s) {
		this.id = s.getId();
		this.skill = s.getSkill();
		this.ability = s.getAbility();
		this.description = s.getDescription();
		this.trainedOnly = s.getTrainedOnly();
		this.class1 = s.getClass1();
		this.class2 = s.getClass2();
		this.class3 = s.getClass3();
		this.class4 = s.getClass4();
		this.class5 = s.getClass5();
		this.armorCheck = s.getArmorCheck();
		this.ranks = s.getRanks();
		this.notes = s.getNotes();
		this.mods = s.getMods();
		this.misc = s.getMisc();
		this.classId = s.getClassId();
		this.instanceId = s.getInstanceId();
		this.effectiveRanks = s.getEffectiveRanks();
		this.crossClassOverride = s.getCrossClassOverride();
		this.synBonus = s.getSynBonus();
		this.synValue = s.getSynValue();
		this.synRanks = s.getSynRanks();
		this.synBonus2 = s.getSynBonus2();
		this.synRanks2 = s.getSynRanks2();
		this.synValue2 = s.getSynValue2();
	}

	public PlayerSkills(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setInstanceId(t.getTagBody());
			}
			if (t.getTagName().equals("SkillId")) {
				setId(t.getTagBody());
				SkillDAO skdb = new SkillDAO();
				Skill sk = skdb.getSkill(this.id);
				init(sk);
			}
			if (t.getTagName().equals("Ranks")) {
				setRanks(t.getTagBody());
			}
			if (t.getTagName().equals("ClassId")) {
				setClassId(t.getTagBody());
			}
			if (t.getTagName().equals("Mods")) {
				setMods(t.getTagBody());
			}
			if (t.getTagName().equals("Misc")) {
				setMisc(t.getTagBody());
			}
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("EffectiveRanks")) {
				setEffectiveRanks(t.getTagBody());
			}
			if (t.getTagName().equals("CrossClassOverride")) {
				setCrossClassOverride(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerSkill>\n");
		sb.append("<Id>" + instanceId + "</Id>\n");
		sb.append("<SkillId>" + id + "</SkillId>\n");
		sb.append("<Ranks>" + ranks + "</Ranks>\n");
		sb.append("<ClassId>" + classId + "</ClassId>\n");
		sb.append("<Mods>" + mods + "</Mods>\n");
		sb.append("<Misc>" + misc + "</Misc>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<EffectiveRanks>" + effectiveRanks + "</EffectiveRanks>\n");
		sb.append("<CrossClassOverride>" + crossClassOverride
				+ "</CrossClassOverride>\n");
		sb.append("</PlayerSkill>\n");
		return sb.toString();
	}
}
