package initcheck.database;

import initcheck.InitLogger;
import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Materials implements Serializable, Cloneable, LibraryItem, Exportable, Importable {

	private static final long serialVersionUID = 1L;

	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

	private String id;

	private String materialName;

	private String description;

	private String weightCalc;

	private String source;
	
	
	public String listFormat(){
		return StrManip.pad(materialName, 30)+StrManip.pad(source, 20);
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDefaults() {
		id = "5";
		materialName = "Normal";
		description = "";
		weightCalc = "1.0";
	}

	public String listValue(){
		return "Material : "+materialName;
	}
	
	public String getName(){
		return materialName;
	}
	
	public void setName(String s){
		materialName = s;
	}
	
	public Materials() {

	}

	public String getFullDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append(materialName+"\r\n");
		sb.append("Source : "+source+"\r\n");
		sb.append(description);
		return sb.toString();
	}

	public String toString() {
		return materialName;
	}

	public Materials(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		logger.log("Reading in Material " + s);
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("MaterialName")) {
				logger.log("Setting name");
				setMaterialName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("WeightCalc")) {
				setWeightCalc(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
		logger.log("Finished Reading " + materialName);
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Materials>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<MaterialName>" + materialName + "</MaterialName>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<WeightCalc>" + weightCalc + "</WeightCalc>\n");
		sb.append("</Materials>\n");
		return sb.toString();
	}

	public String existsAs(){
		MaterialsDAO db = new MaterialsDAO();
		Vector<Materials> v = db.selectMaterials(this);
		if(v.size() > 0){
			return ((Materials)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Materials> getValues(){
		MaterialsDAO db = new MaterialsDAO();
		return db.getMaterials();
	}
	
	public void save(boolean overwrite){
		MaterialsDAO db = new MaterialsDAO();
		if (overwrite){
			db.addOrUpdateMaterials(this);
		}else{
			db.addMaterials(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (materialName == null) {
			materialName = "";
		}

		if (description == null) {
			description = "";
		}

		if (weightCalc == null) {
			weightCalc = "";
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
	 * Get the value of materialName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMaterialName() {
		return materialName;
	}

	/**
	 * Set the value of materialName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMaterialName(String s) {
		materialName = s;
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

	/**
	 * Get the value of weightCalc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWeightCalc() {
		return weightCalc;
	}

	/**
	 * Set the value of weightCalc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWeightCalc(String s) {
		weightCalc = s;
	}

}
