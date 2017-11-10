package initcheck.database;

import java.io.Serializable;

import initcheck.graphics.TiledListItem;

public class PlayerFeats extends Feat implements Serializable, Cloneable,
		Exportable, TiledListItem {

	private static final long serialVersionUID = 1L;

	public void init(Feat f) {
		this.id = f.getId();

		this.featName = f.getFeatName();
		this.description = f.getDescription();
		this.type = f.getType();
		this.type2 = f.getType2();
		this.type3 = f.getType3();
		this.value = f.getValue();
		this.featType = f.getFeatType();
		this.attackBonus = f.getAttackBonus();
		this.damageBonus = f.getDamageBonus();
		this.rangeLimit = f.getRangeLimit();
		this.override = f.getOverride();
		this.instanceId = f.getInstanceId();
		this.special = f.getSpecial();

		this.shortText = f.getShortText();
		this.saveFeat = f.isSaveFeat();
		this.hpFeat = f.isHpFeat();
		this.acFeat = f.isAcFeat();
		this.statFeat = f.isStatFeat();
		this.initFeat = f.isInitFeat();
		this.skillFeat = f.isSkillFeat();
		this.tempBonus = f.isTempBonus();
		this.tempType = f.getTempType();

		this.prerequisites = f.getPrerequisites();
		this.effects = f.getEffects();
	}

	public PlayerFeats(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setInstanceId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatId")) {
				setId(t.getTagBody());
				FeatDAO fdb = new FeatDAO();
				Feat feat = fdb.getFeat(this.id);
				init(feat);
			}
			if (t.getTagName().equals("FeatMod")) {
				setValue(t.getTagBody());
			}
			if (t.getTagName().equals("Override")) {
				setOverride(t.getTagBody());
			}
			if (t.getTagName().equals("Special")) {
				setSpecial(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerFeats>\n");
		sb.append("<Id>" + instanceId + "</Id>\n");
		sb.append("<FeatId>" + id + "</FeatId>\n");
		sb.append("<FeatMod>" + value + "</FeatMod>\n");
		sb.append("<Override>" + override + "</Override>\n");
		sb.append("<Special>" + special + "</Special>\n");
		sb.append("</PlayerFeats>\n");
		return sb.toString();
	}

	public PlayerFeats() {

	}

}
