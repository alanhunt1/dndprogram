package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Poison implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String type;

	private String dc;

	private String initial;

	private String secondary;

	private String price;

	private String source;
	
	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(source, 20);
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Poison() {

	}

	public String listValue(){
		return "Poison : "+name;
	}
	
	public String toString() {
		return name;
	}

	
	public Poison(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
			}
			if (t.getTagName().equals("Dc")) {
				setDc(t.getTagBody());
			}
			if (t.getTagName().equals("Initial")) {
				setInitial(t.getTagBody());
			}
			if (t.getTagName().equals("Secondary")) {
				setSecondary(t.getTagBody());
			}
			if (t.getTagName().equals("Price")) {
				setPrice(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Poison>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<Dc>" + dc + "</Dc>\n");
		sb.append("<Initial>" + initial + "</Initial>\n");
		sb.append("<Secondary>" + secondary + "</Secondary>\n");
		sb.append("<Price>" + price + "</Price>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Poison>\n");
		return sb.toString();
	}

	public String existsAs(){
		PoisonDAO db = new PoisonDAO();
		Vector<Poison> v = db.selectPoison(this);
		if(v.size() > 0){
			return ((Poison)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Poison> getValues(){
		PoisonDAO db = new PoisonDAO();
		return db.getPoisons();
	}
	
	public void save(boolean overwrite){
		PoisonDAO db = new PoisonDAO();
		if (overwrite){
			db.addOrUpdatePoison(this);
		}else{
			db.addPoison(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (type == null) {
			type = "";
		}

		if (dc == null) {
			dc = "";
		}

		if (initial == null) {
			initial = "";
		}

		if (secondary == null) {
			secondary = "";
		}

		if (price == null) {
			price = "";
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
		if (dc != null && !dc.equals("")) {
			try {
				Integer.parseInt(dc);
			} catch (Exception e) {
				errors
						.add("dc is not a valid number.  Please enter a valid number.");
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
	 * Get the value of name
	 * 
	 * @return a <code>String</code> value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Get the value of type
	 * 
	 * @return a <code>String</code> value
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the value of type
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setType(String s) {
		type = s;
	}

	/**
	 * Get the value of dc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDc() {
		return dc;
	}

	/**
	 * Set the value of dc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDc(String s) {
		dc = s;
	}

	/**
	 * Get the value of initial
	 * 
	 * @return a <code>String</code> value
	 */
	public String getInitial() {
		return initial;
	}

	/**
	 * Set the value of initial
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setInitial(String s) {
		initial = s;
	}

	/**
	 * Get the value of secondary
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSecondary() {
		return secondary;
	}

	/**
	 * Set the value of secondary
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSecondary(String s) {
		secondary = s;
	}

	/**
	 * Get the value of price
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Set the value of price
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrice(String s) {
		price = s;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getFullDescription() {
		// TODO Auto-generated method stub
		return name;
	}

}
