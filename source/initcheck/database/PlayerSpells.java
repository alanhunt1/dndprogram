package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class PlayerSpells extends Spell implements Serializable, Cloneable,
		Exportable, TiledListItem {

	
	private static final long serialVersionUID = 1L;

	private String psid;

	private String playerId;

	private String spellId;

	// the casting class
	private String spellClass;

	// the level of the spell for the casting class
	private String spellLevel;

	// the stat bonus for the relevant stat to calculate DC.
	private int bonus;

	// this marks if the spell has been used (cast) or not.
	private boolean used;

	// any metamagic feats applied to the spell
	private Vector<Feat> metamagic = new Vector<Feat>();
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	
	
	/**
	 * Get the Bonus value.
	 * 
	 * @return the Bonus value.
	 */
	public int getBonus() {
		return bonus;
	}

	/**
	 * Set the Bonus value.
	 * 
	 * @param newBonus
	 *            The new Bonus value.
	 */
	public void setBonus(int newBonus) {
		this.bonus = newBonus;
	}

	public PlayerSpells() {

	}
	
	public PlayerSpells(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setPsid(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("SpellId")) {
				setSpellId(t.getTagBody());
			}
			if (t.getTagName().equals("Used")) {
				setUsed(new Boolean(t.getTagBody()).booleanValue());
			}
			if (t.getTagName().equals("Bonus")) {
				setBonus(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("SpellLevel")) {
				setSpellLevel(t.getTagBody());
			}
			if (t.getTagName().equals("SpellClass")) {
				setSpellClass(t.getTagBody());
			}
			if (t.getTagName().equals("Metamagic")) {
				FeatDAO fdb = new FeatDAO();
				metamagic.add(fdb.getFeat(t.getTagBody()));
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public void addMetamagic(Feat f){
		metamagic.add(f);
	}
	
	public Vector<Feat> getMetamagic(){
		return metamagic;
	}
	
	public String exportFormat() {
		return exportFormat("PlayerSpells");
	}

	public String getMemorizedListValue(){
		StringBuffer listValue = new StringBuffer();
		for (int i = 0; i < metamagic.size(); i++){
			listValue.append(((Feat)metamagic.get(i)).getMetamagicPrefix()+" ");
		}
		listValue.append(getSpellName()+"  ("+getSpellClass()+" "+getSpellLevel()+")");
		return listValue.toString();
	}
	
	public String exportFormat(String s) {
		StringBuffer sb = new StringBuffer();
		sb.append("<" + s + ">\n");
		sb.append("<Id>" + psid + "</Id>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<SpellId>" + spellId + "</SpellId>\n");
		sb.append("<Used>"+used+"</Used>");
		sb.append("<Bonus>"+bonus+"</Bonus>");
		sb.append("<SpellLevel>"+spellLevel+"</SpellLevel>");
		sb.append("<SpellClass>"+spellClass+"</SpellClass>");
		for (int i = 0; i < metamagic.size(); i++){
			sb.append("<Metamagic>"+((Feat)metamagic.get(i)).getId()+"</Metamagic>");
		}
		sb.append("</" + s + ">\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (psid == null) {
			psid = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (spellId == null) {
			spellId = "";
		}

	}

	public Vector<String> validate() {
		Vector<String> v = new Vector<String>();
		checkStrings(v);
		checkNumbers(v);
		checkDates(v);
		return v;
	}

	private void checkStrings(Vector<String> errors) {
	}

	private void checkNumbers(Vector<String> errors) {
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors.add("playerId is not a valid number");
			}
		}
		if (spellId != null && !spellId.equals("")) {
			try {
				Integer.parseInt(spellId);
			} catch (Exception e) {
				errors.add("spellId is not a valid number");
			}
		}
	}

	private void checkDates(Vector<String> errors) {

	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
			return "";
		}
		return s;
	}

	public Object getClone() {
		Object o = null;
		try {
			o = this.clone();
		} catch (Exception e) {
		}
		return o;
	}

	/**
	 * Get the value of id
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPsid() {
		return psid;
	}

	/**
	 * Set the value of id
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPsid(String s) {
		psid = s;
	}

	/**
	 * Get the value of playerId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the value of playerId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerId(String s) {
		playerId = s;
	}

	/**
	 * Get the value of spellId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpellId() {
		return spellId;
	}

	/**
	 * Set the value of spellId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpellId(String s) {
		spellId = s;
	}

	public String getSpellClass() {
		return spellClass;
	}

	public void setSpellClass(String spellClass) {
		this.spellClass = spellClass;
	}

	public String getSpellLevel() {
		return spellLevel;
	}

	public void setSpellLevel(String spellLevel) {
		this.spellLevel = spellLevel;
	}

}
