package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class MaterialSource implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String sourceId;

	private String sourceName = "Unknown";

	private String dndVersion = "0";

	public MaterialSource() {

	}
	
	public void setId(String s){
		sourceId = s;
	}
	
	public String listFormat(){
		return StrManip.pad(sourceName, 30);
	}

	public String listValue(){
		return "Source : "+sourceName;
	}
	
	public void setName(String s)
	{
		sourceName = s;
	}
	
	public String getName(){
		return sourceName;
		
	}
	
	public String toString(){
		return sourceName+" ("+dndVersion+")";
	}
	
	public MaterialSource(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("SourceId")) {
				setSourceId(t.getTagBody());
			}
			if (t.getTagName().equals("SourceName")) {
				setSourceName(t.getTagBody());
			}
			if (t.getTagName().equals("DndVersion")) {
				setDndVersion(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MaterialSource>\n");
		sb.append("<SourceId>" + sourceId + "</SourceId>\n");
		sb.append("<SourceName>" + sourceName + "</SourceName>\n");
		sb.append("<DndVersion>" + dndVersion + "</DndVersion>\n");
		sb.append("</MaterialSource>\n");
		return sb.toString();
	}

	public String existsAs(){
		MaterialSourceDAO db = new MaterialSourceDAO();
		Vector<MaterialSource> v = db.selectMaterialSource(this);
		if(v.size() > 0){
			return ((MaterialSource)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<MaterialSource> getValues(){
		MaterialSourceDAO db = new MaterialSourceDAO();
		return db.getMaterialSources();
	}
	
	public void save(boolean overwrite){
		MaterialSourceDAO db = new MaterialSourceDAO();
		if (overwrite){
			db.addOrUpdateMaterialSource(this);
		}else{
			db.addMaterialSource(this);
		}
	}
	
	public void convertNulls() {
		if (sourceId == null) {
			sourceId = "";
		}

		if (sourceName == null) {
			sourceName = "";
		}

		if (dndVersion == null) {
			dndVersion = "";
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
	 * Get the value of sourceId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Set the value of sourceId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSourceId(String s) {
		sourceId = s;
	}

	/**
	 * Get the value of sourceName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * Set the value of sourceName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSourceName(String s) {
		sourceName = s;
	}

	/**
	 * Get the value of dndVersion
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDndVersion() {
		return dndVersion;
	}

	/**
	 * Set the value of dndVersion
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDndVersion(String s) {
		dndVersion = s;
	}

	public String getDescription() {
		
		return sourceName+" "+dndVersion;
	}

	public String getFullDescription() {
		
		return sourceName+" "+dndVersion;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return sourceId;
	}

}
