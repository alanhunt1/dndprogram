package initcheck.character;

import initcheck.DCharacter;
import initcheck.database.CharClassDAO;
import initcheck.database.ClassAdvancement;
import initcheck.database.Exportable;
import initcheck.database.Tag;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class SaveBlock implements Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	int baseFort = 0;

	int baseRef = 0;

	int baseWill = 0;

	int abilFort = 0;

	int abilRef = 0;

	int abilWill = 0;

	int miscFort = 0;

	int miscRef = 0;

	int miscWill = 0;

	String miscFortText;

	String miscRefText;

	String miscWillText;

	public SaveBlock() {

	}

	public SaveBlock(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("MiscFort")) {
				setMiscFort(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscFortText")) {
				setMiscFortText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscRef")) {
				setMiscRef(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscRefText")) {
				setMiscRefText(t.getTagBody());
			}
			if (t.getTagName().equals("MiscWill")) {
				setMiscWill(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("MiscWillText")) {
				setMiscWillText(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<SaveBlock>\n");
		sb.append("<Fort>" + baseFort + "</Fort>\n");
		sb.append("<Will>" + baseWill + "</Will>\n");
		sb.append("<Ref>" + baseRef + "</Ref>\n");
		sb.append("<MiscFort>" + miscFort + "</MiscFort>\n");
		sb.append("<MiscRef>" + miscRef + "</MiscRef>\n");
		sb.append("<MiscWill>" + miscWill + "</MiscWill>\n");
		sb.append("<MiscFortText>" + miscFortText + "</MiscFortText>\n");
		sb.append("<MiscRefText>" + miscRefText + "</MiscRefText>\n");
		sb.append("<MiscWillText>" + miscWillText + "</MiscWillText>\n");
		sb.append("</SaveBlock>\n");
		return sb.toString();
	}

	public void updateSaves(DCharacter c) {
		StatBlock sb = c.getStats();
		Vector<ClassSlot> classVector = c.getClassSet().getClassVector();

		baseFort = 0;
		baseWill = 0;
		baseRef = 0;

		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClassDAO db = new CharClassDAO();
			ClassAdvancement ca = db.getAdvancement(cs.getClassName(), cs
					.getLevel());
			String f = ca.getFortSave();
			f = f.substring(f.indexOf("+") + 1, f.length());
			baseFort += Integer.parseInt(f);
			String w = ca.getWillSave();
			w = w.substring(w.indexOf("+") + 1, w.length());
			baseWill += Integer.parseInt(w);
			String r = ca.getRefSave();
			r = r.substring(r.indexOf("+") + 1, r.length());
			baseRef += Integer.parseInt(r);

			abilFort = c.getBonus(sb.getCon());
			abilRef = c.getBonus(sb.getDex());
			abilWill = c.getBonus(sb.getWis());
		}
	}

	public SaveBlock(DCharacter c) {

		StatBlock sb = c.getStats();
		Vector<ClassSlot> classVector = c.getClassSet().getClassVector();

		for (int i = 0; i < classVector.size(); i++) {
			ClassSlot cs = (ClassSlot) classVector.get(i);
			CharClassDAO db = new CharClassDAO();
			ClassAdvancement ca = db.getAdvancement(cs.getClassName(), cs
					.getLevel());
			String f = ca.getFortSave();
			f = f.substring(f.indexOf("+") + 1, f.length());
			baseFort += Integer.parseInt(f);
			String w = ca.getWillSave();
			w = w.substring(w.indexOf("+") + 1, w.length());
			baseWill += Integer.parseInt(w);
			String r = ca.getRefSave();
			r = r.substring(r.indexOf("+") + 1, r.length());
			baseRef += Integer.parseInt(r);

			abilFort = c.getBonus(sb.getCon());
			abilRef = c.getBonus(sb.getDex());
			abilWill = c.getBonus(sb.getWis());
		}
	}

	public int getAbilFort() {
		return abilFort;
	}

	public int getAbilRef() {
		return abilRef;
	}

	public int getAbilWill() {
		return abilWill;
	}

	public void setAbilFort(int i) {
		abilFort = i;
	}

	public void setAbilRef(int i) {
		abilRef = i;
	}

	public void setAbilWill(int i) {
		abilWill = i;
	}

	public int getFort() {
		return baseFort + abilFort + miscFort;
	}

	public int getRef() {
		return baseRef + abilRef + miscRef;
	}

	public int getWill() {
		return baseWill + abilWill + miscWill;
	}

	/**
	 * Get the MiscWillText value.
	 * 
	 * @return the MiscWillText value.
	 */
	public String getMiscWillText() {
		return (StrManip.isNullValOrEmpty(miscWillText) ? "" : miscWillText);
	}

	/**
	 * Set the MiscWillText value.
	 * 
	 * @param newMiscWillText
	 *            The new MiscWillText value.
	 */
	public void setMiscWillText(String newMiscWillText) {
		this.miscWillText = newMiscWillText;
	}

	/**
	 * Get the MiscRefText value.
	 * 
	 * @return the MiscRefText value.
	 */
	public String getMiscRefText() {
		return (StrManip.isNullValOrEmpty(miscRefText) ? "" : miscRefText);
	}

	/**
	 * Set the MiscRefText value.
	 * 
	 * @param newMiscRefText
	 *            The new MiscRefText value.
	 */
	public void setMiscRefText(String newMiscRefText) {
		this.miscRefText = newMiscRefText;
	}

	/**
	 * Get the MiscFortText value.
	 * 
	 * @return the MiscFortText value.
	 */
	public String getMiscFortText() {
		return (StrManip.isNullValOrEmpty(miscFortText) ? "" : miscFortText);
	}

	/**
	 * Set the MiscFortText value.
	 * 
	 * @param newMiscFortText
	 *            The new MiscFortText value.
	 */
	public void setMiscFortText(String newMiscFortText) {
		this.miscFortText = newMiscFortText;
	}

	/**
	 * Get the MiscWill value.
	 * 
	 * @return the MiscWill value.
	 */
	public int getMiscWill() {
		return miscWill;
	}

	/**
	 * Set the MiscWill value.
	 * 
	 * @param newMiscWill
	 *            The new MiscWill value.
	 */
	public void setMiscWill(int newMiscWill) {
		this.miscWill = newMiscWill;
	}

	/**
	 * Get the MiscRef value.
	 * 
	 * @return the MiscRef value.
	 */
	public int getMiscRef() {
		return miscRef;
	}

	/**
	 * Set the MiscRef value.
	 * 
	 * @param newMiscRef
	 *            The new MiscRef value.
	 */
	public void setMiscRef(int newMiscRef) {
		this.miscRef = newMiscRef;
	}

	/**
	 * Get the MiscFort value.
	 * 
	 * @return the MiscFort value.
	 */
	public int getMiscFort() {
		return miscFort;
	}

	/**
	 * Set the MiscFort value.
	 * 
	 * @param newMiscFort
	 *            The new MiscFort value.
	 */
	public void setMiscFort(int newMiscFort) {
		this.miscFort = newMiscFort;
	}

	/**
	 * Get the BaseWill value.
	 * 
	 * @return the BaseWill value.
	 */
	public int getBaseWill() {
		return baseWill;
	}

	/**
	 * Set the BaseWill value.
	 * 
	 * @param newBaseWill
	 *            The new BaseWill value.
	 */
	public void setBaseWill(int newBaseWill) {
		this.baseWill = newBaseWill;
	}

	/**
	 * Get the BaseRef value.
	 * 
	 * @return the BaseRef value.
	 */
	public int getBaseRef() {
		return baseRef;
	}

	/**
	 * Set the BaseRef value.
	 * 
	 * @param newBaseRef
	 *            The new BaseRef value.
	 */
	public void setBaseRef(int newBaseRef) {
		this.baseRef = newBaseRef;
	}

	/**
	 * Get the BaseFort value.
	 * 
	 * @return the BaseFort value.
	 */
	public int getBaseFort() {
		return baseFort;
	}

	/**
	 * Set the BaseFort value.
	 * 
	 * @param newBaseFort
	 *            The new BaseFort value.
	 */
	public void setBaseFort(int newBaseFort) {
		this.baseFort = newBaseFort;
	}

}
