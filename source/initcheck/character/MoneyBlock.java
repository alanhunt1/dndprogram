package initcheck.character;

import initcheck.database.Tag;

import java.io.Serializable;

public class MoneyBlock implements Serializable {

	private static final long serialVersionUID = 1L;

	int cp = 0;

	int sp = 0;

	int gp = 0;

	int pp = 0;

	int storedGold;

	/**
	 * Get the StoredGold value.
	 * 
	 * @return the StoredGold value.
	 */
	public int getStoredGold() {
		return storedGold;
	}

	/**
	 * Set the StoredGold value.
	 * 
	 * @param newStoredGold
	 *            The new StoredGold value.
	 */
	public void setStoredGold(int newStoredGold) {
		this.storedGold = newStoredGold;
	}

	public MoneyBlock() {

	}

	public MoneyBlock(int cp, int sp, int gp, int pp, int stored) {
		this.cp = cp;
		this.sp = sp;
		this.gp = gp;
		this.pp = pp;
		this.storedGold = stored;
	}

	public MoneyBlock(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Pp")) {
				setPp(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Sp")) {
				setSp(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Cp")) {
				setCp(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Gp")) {
				setGp(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("StoredGold")) {
				setStoredGold(Integer.parseInt(t.getTagBody()));
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MoneyBlock>\n");
		sb.append("<Pp>" + pp + "</Pp>\n");
		sb.append("<Gp>" + gp + "</Gp>\n");
		sb.append("<Sp>" + sp + "</Sp>\n");
		sb.append("<Cp>" + cp + "</Cp>\n");
		sb.append("<StoredGold>" + storedGold + "</StoredGold>\n");
		sb.append("</MoneyBlock>\n");
		return sb.toString();
	}

	/**
	 * Get the Pp value.
	 * 
	 * @return the Pp value.
	 */
	public int getPp() {
		return pp;
	}

	/**
	 * Set the Pp value.
	 * 
	 * @param newPp
	 *            The new Pp value.
	 */
	public void setPp(int newPp) {
		this.pp = newPp;
	}

	/**
	 * Get the Gp value.
	 * 
	 * @return the Gp value.
	 */
	public int getGp() {
		return gp;
	}

	/**
	 * Set the Gp value.
	 * 
	 * @param newGp
	 *            The new Gp value.
	 */
	public void setGp(int newGp) {
		this.gp = newGp;
	}

	/**
	 * Get the Sp value.
	 * 
	 * @return the Sp value.
	 */
	public int getSp() {
		return sp;
	}

	/**
	 * Set the Sp value.
	 * 
	 * @param newSp
	 *            The new Sp value.
	 */
	public void setSp(int newSp) {
		this.sp = newSp;
	}

	/**
	 * Get the Cp value.
	 * 
	 * @return the Cp value.
	 */
	public int getCp() {
		return cp;
	}

	/**
	 * Set the Cp value.
	 * 
	 * @param newCp
	 *            The new Cp value.
	 */
	public void setCp(int newCp) {
		this.cp = newCp;
	}

}
