package initcheck.database;

import java.io.Serializable;

import initcheck.graphics.TiledListItem;

public class PlayerItems implements Serializable, Cloneable,TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String itemName;

	private String weight;

	private String quantity;

	private String location;

	private String cost;

	String notes;

	boolean dropped;

	boolean linked;

	boolean hasMod;

	String modType;

	int position;

	/**
	 * Get the Position value.
	 * 
	 * @return the Position value.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Set the Position value.
	 * 
	 * @param newPosition
	 *            The new Position value.
	 */
	public void setPosition(int newPosition) {
		this.position = newPosition;
	}

	public PlayerItems(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			

			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("ItemName")) {
				setItemName(t.getTagBody());
			}
			if (t.getTagName().equals("Weight")) {
				setWeight(t.getTagBody());
			}
			if (t.getTagName().equals("Quantity")) {
				setQuantity(t.getTagBody());
			}
			if (t.getTagName().equals("Location")) {
				setLocation(t.getTagBody());
			}
			if (t.getTagName().equals("Cost")) {
				setCost(t.getTagBody());
			}
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("Position")) {
				setPosition(Integer.parseInt(t.getTagBody()));
			}
			if (t.getTagName().equals("Dropped")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setDropped(true);
				} else {
					setDropped(false);
				}
			}
			if (t.getTagName().equals("HasMod")) {
				if (t.getTagBody().equalsIgnoreCase("true")) {
					setHasMod(true);
				} else {
					setHasMod(false);
				}
			}
			if (t.getTagName().equals("ModType")) {
				setModType(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerItems>");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<ItemName>" + itemName + "</ItemName>\n");
		sb.append("<Weight>" + weight + "</Weight>\n");
		sb.append("<Quantity>" + quantity + "</Quantity>\n");
		sb.append("<Location>" + location + "</Location>\n");
		sb.append("<Cost>" + cost + "</Cost>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		sb.append("<Dropped>" + dropped + "</Dropped>\n");
		sb.append("<HasMod>" + hasMod + "</HasMod>\n");
		sb.append("<ModType>" + modType + "</ModType>\n");
		sb.append("<Position>" + position + "</Position>\n");
		sb.append("</PlayerItems>\n");
		return sb.toString();
	}

	/**
	 * Get the ModType value.
	 * 
	 * @return the ModType value.
	 */
	public String getModType() {
		return modType;
	}

	/**
	 * Set the ModType value.
	 * 
	 * @param newModType
	 *            The new ModType value.
	 */
	public void setModType(String newModType) {
		this.modType = newModType;
	}

	/**
	 * Get the HasMod value.
	 * 
	 * @return the HasMod value.
	 */
	public boolean isHasMod() {
		return hasMod;
	}

	/**
	 * Set the HasMod value.
	 * 
	 * @param newHasMod
	 *            The new HasMod value.
	 */
	public void setHasMod(boolean newHasMod) {
		this.hasMod = newHasMod;
	}

	/**
	 * Get the Linked value.
	 * 
	 * @return the Linked value.
	 */
	public boolean isLinked() {
		return linked;
	}

	/**
	 * Set the Linked value.
	 * 
	 * @param newLinked
	 *            The new Linked value.
	 */
	public void setLinked(boolean newLinked) {
		this.linked = newLinked;
	}

	/**
	 * Get the Dropped value.
	 * 
	 * @return the Dropped value.
	 */
	public boolean isDropped() {
		return dropped;
	}

	/**
	 * Set the Dropped value.
	 * 
	 * @param newDropped
	 *            The new Dropped value.
	 */
	public void setDropped(boolean newDropped) {
		this.dropped = newDropped;
	}

	/**
	 * Get the Notes value.
	 * 
	 * @return the Notes value.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the Notes value.
	 * 
	 * @param newNotes
	 *            The new Notes value.
	 */
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}

	/**
	 * Get the Cost value.
	 * 
	 * @return the Cost value.
	 */
	public String getCost() {
		return cost;
	}

	/**
	 * Set the Cost value.
	 * 
	 * @param newCost
	 *            The new Cost value.
	 */
	public void setCost(String newCost) {
		this.cost = newCost;
	}

	/**
	 * Get the Location value.
	 * 
	 * @return the Location value.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Set the Location value.
	 * 
	 * @param newLocation
	 *            The new Location value.
	 */
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}

	public PlayerItems() {

	}

	public String toString() {
		return pad(quantity, 3)
				+ " "
				+ pad(itemName, 30)
				+ " "
				+ pad(weight, 5)
				+ "("
				+ pad(""
						+ (Double.parseDouble(weight) * Double
								.parseDouble(quantity)), 5) + ")  "
				+ pad(location, 15) + " " + pad(cost, 6) + " " + pad(notes, 30);
	}

	public String pad(String s, int l) {

		if (s == null) {
			s = "";
		}

		if (s.length() > l) {
			return s.substring(0, l);
		}

		char[] chars = new char[l];
		for (int i = 0; i < s.length(); i++) {
			chars[i] = s.charAt(i);
		}
		for (int i = s.length(); i < l; i++) {
			chars[i] = ' ';
		}
		return new String(chars);
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
	 * Get the value of itemName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Set the value of itemName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setItemName(String s) {
		itemName = s;
	}

	/**
	 * Get the value of weight
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * Set the value of weight
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeight(String s) {
		weight = s;
	}

	/**
	 * Get the value of quantity
	 * 
	 * @return a <code>String</code> value
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * Set the value of quantity
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setQuantity(String s) {
		quantity = s;
	}

}
