package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class WeaponSizeIncrease implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String damage;

	private String minus1;

	private String minus2;

	private String minus3;

	private String minus4;

	private String minus5;

	private String minus6;

	private String plus1;

	private String plus2;

	private String plus3;

	private String plus4;

	private String plus5;

	private String plus6;

	private String campaignid;

	public WeaponSizeIncrease() {

	}

	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("damage")) {
				setdamage(t.getTagBody());
			}
			if (t.getTagName().equals("minus1")) {
				setminus1(t.getTagBody());
			}
			if (t.getTagName().equals("minus2")) {
				setminus2(t.getTagBody());
			}
			if (t.getTagName().equals("minus3")) {
				setminus3(t.getTagBody());
			}
			if (t.getTagName().equals("minus4")) {
				setminus4(t.getTagBody());
			}
			if (t.getTagName().equals("minus5")) {
				setminus5(t.getTagBody());
			}
			if (t.getTagName().equals("minus6")) {
				setminus6(t.getTagBody());
			}
			if (t.getTagName().equals("plus1")) {
				setplus1(t.getTagBody());
			}
			if (t.getTagName().equals("plus2")) {
				setplus2(t.getTagBody());
			}
			if (t.getTagName().equals("plus3")) {
				setplus3(t.getTagBody());
			}
			if (t.getTagName().equals("plus4")) {
				setplus4(t.getTagBody());
			}
			if (t.getTagName().equals("plus5")) {
				setplus5(t.getTagBody());
			}
			if (t.getTagName().equals("plus6")) {
				setplus6(t.getTagBody());
			}
			if (t.getTagName().equals("campaignid")) {
				setcampaignid(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<WeaponSizeIncrease>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<damage>" + damage + "</damage>\n");
		sb.append("<minus1>" + minus1 + "</minus1>\n");
		sb.append("<minus2>" + minus2 + "</minus2>\n");
		sb.append("<minus3>" + minus3 + "</minus3>\n");
		sb.append("<minus4>" + minus4 + "</minus4>\n");
		sb.append("<minus5>" + minus5 + "</minus5>\n");
		sb.append("<minus6>" + minus6 + "</minus6>\n");
		sb.append("<plus1>" + plus1 + "</plus1>\n");
		sb.append("<plus2>" + plus2 + "</plus2>\n");
		sb.append("<plus3>" + plus3 + "</plus3>\n");
		sb.append("<plus4>" + plus4 + "</plus4>\n");
		sb.append("<plus5>" + plus5 + "</plus5>\n");
		sb.append("<plus6>" + plus6 + "</plus6>\n");
		sb.append("<campaignid>" + campaignid + "</campaignid>\n");
		sb.append("</WeaponSizeIncrease>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (damage == null) {
			damage = "";
		}

		if (minus1 == null) {
			minus1 = "";
		}

		if (minus2 == null) {
			minus2 = "";
		}

		if (minus3 == null) {
			minus3 = "";
		}

		if (minus4 == null) {
			minus4 = "";
		}

		if (minus5 == null) {
			minus5 = "";
		}

		if (minus6 == null) {
			minus6 = "";
		}

		if (plus1 == null) {
			plus1 = "";
		}

		if (plus2 == null) {
			plus2 = "";
		}

		if (plus3 == null) {
			plus3 = "";
		}

		if (plus4 == null) {
			plus4 = "";
		}

		if (plus5 == null) {
			plus5 = "";
		}

		if (plus6 == null) {
			plus6 = "";
		}

		if (campaignid == null) {
			campaignid = "";
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
		if (campaignid != null && !campaignid.equals("")) {
			try {
				Integer.parseInt(campaignid);
			} catch (Exception e) {
				errors
						.add("campaignid is not a valid number.  Please enter a valid number.");
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
	public String getId() {
		return id;
	}

	/**
	 * Set the value of id
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setId(String s) {
		id = s;
	}

	/**
	 * Get the value of damage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getdamage() {
		return damage;
	}

	/**
	 * Set the value of damage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setdamage(String s) {
		damage = s;
	}

	/**
	 * Get the value of minus1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus1() {
		return minus1;
	}

	/**
	 * Set the value of minus1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus1(String s) {
		minus1 = s;
	}

	/**
	 * Get the value of minus2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus2() {
		return minus2;
	}

	/**
	 * Set the value of minus2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus2(String s) {
		minus2 = s;
	}

	/**
	 * Get the value of minus3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus3() {
		return minus3;
	}

	/**
	 * Set the value of minus3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus3(String s) {
		minus3 = s;
	}

	/**
	 * Get the value of minus4
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus4() {
		return minus4;
	}

	/**
	 * Set the value of minus4
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus4(String s) {
		minus4 = s;
	}

	/**
	 * Get the value of minus5
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus5() {
		return minus5;
	}

	/**
	 * Set the value of minus5
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus5(String s) {
		minus5 = s;
	}

	/**
	 * Get the value of minus6
	 * 
	 * @return a <code>String</code> value
	 */
	public String getminus6() {
		return minus6;
	}

	/**
	 * Set the value of minus6
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setminus6(String s) {
		minus6 = s;
	}

	/**
	 * Get the value of plus1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus1() {
		return plus1;
	}

	/**
	 * Set the value of plus1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus1(String s) {
		plus1 = s;
	}

	/**
	 * Get the value of plus2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus2() {
		return plus2;
	}

	/**
	 * Set the value of plus2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus2(String s) {
		plus2 = s;
	}

	/**
	 * Get the value of plus3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus3() {
		return plus3;
	}

	/**
	 * Set the value of plus3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus3(String s) {
		plus3 = s;
	}

	/**
	 * Get the value of plus4
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus4() {
		return plus4;
	}

	/**
	 * Set the value of plus4
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus4(String s) {
		plus4 = s;
	}

	/**
	 * Get the value of plus5
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus5() {
		return plus5;
	}

	/**
	 * Set the value of plus5
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus5(String s) {
		plus5 = s;
	}

	/**
	 * Get the value of plus6
	 * 
	 * @return a <code>String</code> value
	 */
	public String getplus6() {
		return plus6;
	}

	/**
	 * Set the value of plus6
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setplus6(String s) {
		plus6 = s;
	}

	/**
	 * Get the value of campaignid
	 * 
	 * @return a <code>String</code> value
	 */
	public String getcampaignid() {
		return campaignid;
	}

	/**
	 * Set the value of campaignid
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setcampaignid(String s) {
		campaignid = s;
	}

}
