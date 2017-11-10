package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Artifact implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String weight;

	private String description;

	private String type;
	
	private String source;
	
	public String toString(){
		return name;
	}
	
	public String listFormat(){
		return StrManip.pad(name, 30)+StrManip.pad(source, 20);
	}
	
	public String listValue(){
		return "Artifact : "+name;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Artifact() {

	}

	public void readImport(String s) {
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
			if (t.getTagName().equals("Weight")) {
				setWeight(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Type")) {
				setType(t.getTagBody());
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
		sb.append("<Artifact>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Weight>" + weight + "</Weight>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Type>" + type + "</Type>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Artifact>\n");
		return sb.toString();
	}

	public String existsAs(){
		ArtifactDAO db = new ArtifactDAO();
		Vector<Artifact> v = db.selectArtifacts(this);
		if(v.size() > 0){
			return ((Artifact)v.get(0)).getName();
		}
		return null;
	}
	
	public void save(boolean overwrite){
		ArtifactDAO db = new ArtifactDAO();
		if (overwrite){
			db.addOrUpdateArtifact(this);
		}else{
			db.addArtifacts(this);
		}
	}
	
	public Vector<Artifact> getValues(){
		ArtifactDAO db = new ArtifactDAO();
		return db.getArtifacts();
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (weight == null) {
			weight = "";
		}

		if (description == null) {
			description = "";
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
		if (weight != null && !weight.equals("")) {
			try {
				Integer.parseInt(weight);
			} catch (Exception e) {
				errors
						.add("weight is not a valid number.  Please enter a valid number.");
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
	 * Get the value of description
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of description
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDescription(String s) {
		description = s;
	}

	public String getFullDescription() {
		// TODO Auto-generated method stub
		return description;
	}

}
