package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class ItemLocation implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String location;

	private String droppable;

	private String weightless;

	public ItemLocation() {

	}

	public String toString() {
		return location;
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (location == null) {
			location = "";
		}

		if (droppable == null) {
			droppable = "";
		}

		if (weightless == null) {
			weightless = "";
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
	 * Get the value of location
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Set the value of location
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLocation(String s) {
		location = s;
	}

	/**
	 * Get the value of droppable
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDroppable() {
		return droppable;
	}

	/**
	 * Set the value of droppable
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDroppable(String s) {
		droppable = s;
	}

	/**
	 * Get the value of weightless
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeightless() {
		return weightless;
	}

	/**
	 * Set the value of weightless
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeightless(String s) {
		weightless = s;
	}

}
