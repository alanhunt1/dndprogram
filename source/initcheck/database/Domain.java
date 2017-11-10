package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Domain implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String domainName;

	private String domainPower;

	private String description;

	private Vector<DomainSpells> domainSpells;

	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listValue(){
		return "Domain : "+domainName;
	}
	
	public String listFormat(){
		return StrManip.pad(domainName, 30)+StrManip.pad(source, 20);
	}
	
	public String getName(){
		return domainName;
	}
	
	public void setName(String s){
		domainName = s;
	}
	
	public String getFullDescription() {
		return description;
	}

	/**
	 * Get the DomainSpells value.
	 * 
	 * @return the DomainSpells value.
	 */
	public Vector<DomainSpells> getDomainSpells() {
		return domainSpells;
	}

	/**
	 * Set the DomainSpells value.
	 * 
	 * @param newDomainSpells
	 *            The new DomainSpells value.
	 */
	public void setDomainSpells(Vector<DomainSpells> newDomainSpells) {
		this.domainSpells = newDomainSpells;
	}

	public Domain() {

	}

	public String getDisplayFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append(domainName + "\n");
		sb.append(domainPower + "\n");
		sb.append(description + "\n");

		return sb.toString();

	}

	
	
	public Domain(String s) {
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
			if (t.getTagName().equals("DomainName")) {
				setDomainName(t.getTagBody());
			}
			if (t.getTagName().equals("DomainPower")) {
				setDomainPower(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			if (t.getTagName().equals("Source")) {
				setSource(t.getTagBody());
			}
			if (t.getTagName().equals("DomainSpells")) {
				domainSpells.add(new DomainSpells(t.getTagBody()));
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Domain>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<DomainName>" + domainName + "</DomainName>\n");
		sb.append("<DomainPower>" + domainPower + "</DomainPower>\n");
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("<Source>" + source + "</Source>\n");
		DomainSpellsDAO dsdb = new DomainSpellsDAO();
		DomainSpells ds = new DomainSpells();
		ds.setDomainId(id);
		domainSpells = dsdb.selectDomainSpells(ds);
		for (int i = 0; i < domainSpells.size(); i++) {
			sb.append(((DomainSpells) domainSpells.get(i)).exportFormat());
		}
		sb.append("</Domain>\n");
		return sb.toString();
	}

	public String existsAs(){
		DomainDAO db = new DomainDAO();
		Vector<Domain> v = db.selectDomain(this);
		if(v.size() > 0){
			return ((Domain)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Domain> getValues(){
		DomainDAO db = new DomainDAO();
		return db.getDomains();
	}
	
	public void save(boolean overwrite) {
		if (!overwrite) {
			setId(null);
		}
		DomainDAO ddb = new DomainDAO();
		int id = ddb.addOrUpdateDomain(this);
		DomainSpellsDAO dsdb = new DomainSpellsDAO();
		dsdb.clearDomainSpells("" + id);
		
		if (domainSpells != null){
			for (int i = 0; i < domainSpells.size(); i++) {
				DomainSpells ds = (DomainSpells) domainSpells.get(i);
				dsdb.addDomainSpells(ds);
			}
		}

	}

	public String toString() {
		return domainName;
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (domainName == null) {
			domainName = "";
		}

		if (domainPower == null) {
			domainPower = "";
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
	 * Get the value of domainName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * Set the value of domainName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDomainName(String s) {
		domainName = s;
	}

	/**
	 * Get the value of domainPower
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDomainPower() {
		return domainPower;
	}

	/**
	 * Set the value of domainPower
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDomainPower(String s) {
		domainPower = s;
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

}
