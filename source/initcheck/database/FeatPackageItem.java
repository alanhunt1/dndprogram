package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class FeatPackageItem implements Serializable, Cloneable, Exportable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String featId;

	private String packageId;

	private String featOrder;

	private String featName;

	public void setFeatName(String newFeatName) {
		this.featName = newFeatName;
	}

	public String toString() {
		return featName;
	}

	public FeatPackageItem() {

	}

	public FeatPackageItem(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatId")) {
				setFeatId(t.getTagBody());
			}
			if (t.getTagName().equals("PackageId")) {
				setPackageId(t.getTagBody());
			}
			if (t.getTagName().equals("FeatOrder")) {
				setFeatOrder(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<FeatPackageItem>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<FeatId>" + featId + "</FeatId>\n");
		sb.append("<PackageId>" + packageId + "</PackageId>\n");
		sb.append("<FeatOrder>" + featOrder + "</FeatOrder>\n");
		sb.append("</FeatPackageItem>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (featId == null) {
			featId = "";
		}

		if (packageId == null) {
			packageId = "";
		}

		if (featOrder == null) {
			featOrder = "";
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
		if (featId != null && !featId.equals("")) {
			try {
				Integer.parseInt(featId);
			} catch (Exception e) {
				errors
						.add("featId is not a valid number.  Please enter a valid number.");
			}
		}
		if (featOrder != null && !featOrder.equals("")) {
			try {
				Integer.parseInt(featOrder);
			} catch (Exception e) {
				errors
						.add("featOrder is not a valid number.  Please enter a valid number.");
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
	 * Get the value of featId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFeatId() {
		return featId;
	}

	/**
	 * Set the value of featId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFeatId(String s) {
		featId = s;
	}

	/**
	 * Get the value of packageId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPackageId() {
		return packageId;
	}

	/**
	 * Set the value of packageId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPackageId(String s) {
		packageId = s;
	}

	/**
	 * Get the value of featOrder
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFeatOrder() {
		return featOrder;
	}

	/**
	 * Set the value of featOrder
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFeatOrder(String s) {
		featOrder = s;
	}

	/**
	 * Get the FeatName value.
	 * 
	 * @return the FeatName value.
	 */
	public String getFeatName() {
		return featName;
	}

	/**
	 * Set the FeatName value.
	 * 
	 * @param newFeatName
	 *            The new FeatName value.
	 */
}
