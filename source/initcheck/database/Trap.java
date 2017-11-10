package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.library.LibraryItem;
import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;

public class Trap implements Serializable, Cloneable, Exportable, LibraryItem, Importable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String trapId;

	private String trapName;

	private String spotDc;

	private String trapDescription;

	private String disableDc;

	private String cr;

	private String trapType;

	private String triggerType;

	private String resetType;

	private boolean bypass;
	
	private String bypassDetectDc;
	
	private String bypassUseDc;
	
	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String listFormat(){
		return StrManip.pad(trapName, 30)+StrManip.pad(cr, 4)+StrManip.pad(source, 20);
	}
	
	public String listValue(){
		return "Trap : "+trapName;
	}
	
	public String getName(){
		return trapName;
	}
	
	public void setName(String s){
		trapName = s;
	}
	
	public boolean isBypass() {
		return bypass;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}

	public String getBypassDetectDc() {
		return bypassDetectDc;
	}

	public void setBypassDetectDc(String bypassDetectDc) {
		this.bypassDetectDc = bypassDetectDc;
	}

	public String getBypassUseDc() {
		return bypassUseDc;
	}

	public void setBypassUseDc(String bypassUseDc) {
		this.bypassUseDc = bypassUseDc;
	}

	public Trap() {

	}

	public String toString() {
		return trapName + " CR" + cr;
	}

	public Trap(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("TrapId")) {
				setTrapId(t.getTagBody());
			}
			if (t.getTagName().equals("TrapName")) {
				setTrapName(t.getTagBody());
			}
			if (t.getTagName().equals("SpotDc")) {
				setSpotDc(t.getTagBody());
			}
			if (t.getTagName().equals("TrapDescription")) {
				setTrapDescription(t.getTagBody());
			}
			if (t.getTagName().equals("DisableDc")) {
				setDisableDc(t.getTagBody());
			}
			if (t.getTagName().equals("Cr")) {
				setCr(t.getTagBody());
			}
			if (t.getTagName().equals("TrapType")) {
				setTrapType(t.getTagBody());
			}
			if (t.getTagName().equals("TriggerType")) {
				setTriggerType(t.getTagBody());
			}
			if (t.getTagName().equals("ResetType")) {
				setResetType(t.getTagBody());
			}
			if (t.getTagName().equals("Bypass")) {
				if (t.getTagBody().equals("true")){
					setBypass(true);
				}else{
					setBypass(false);
				}
			}
			if (t.getTagName().equals("BypassDetectDc")) {
				setBypassDetectDc(t.getTagBody());
			}
			if (t.getTagName().equals("BypassUseDc")) {
				setBypassUseDc(t.getTagBody());
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
		sb.append("<Trap>\n");
		sb.append("<TrapId>" + trapId + "</TrapId>\n");
		sb.append("<TrapName>" + trapName + "</TrapName>\n");
		sb.append("<SpotDc>" + spotDc + "</SpotDc>\n");
		sb.append("<TrapDescription>" + trapDescription
				+ "</TrapDescription>\n");
		sb.append("<DisableDc>" + disableDc + "</DisableDc>\n");
		sb.append("<Cr>" + cr + "</Cr>\n");
		sb.append("<TrapType>" + trapType + "</TrapType>\n");
		sb.append("<TriggerType>" + triggerType + "</TriggerType>\n");
		sb.append("<ResetType>" + resetType + "</ResetType>\n");
		sb.append("<Bypass>" + bypass + "</Bypass>\n");
		sb.append("<BypassDetectDc>" + bypassDetectDc + "</BypassDetectDc>\n");
		sb.append("<BypassUseDc>" + bypassUseDc + "</BypassUseDc>\n");
		sb.append("<Source>" + source + "</Source>\n");
		sb.append("</Trap>\n");
		return sb.toString();
	}

	public String existsAs(){
		TrapDAO db = new TrapDAO();
		Vector<Trap> v = db.selectTrap(this);
		if(v.size() > 0){
			return ((Trap)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Trap> getValues(){
		TrapDAO db = new TrapDAO();
		return db.getTraps();
	}
	
	public void save(boolean overwrite){
		TrapDAO db = new TrapDAO();
		if (overwrite){
			db.addOrUpdateTrap(this);
		}else{
			db.addTrap(this);
		}
	}
	
	public void convertNulls() {
		if (trapId == null) {
			trapId = "";
		}

		if (trapName == null) {
			trapName = "";
		}

		if (spotDc == null) {
			spotDc = "";
		}

		if (trapDescription == null) {
			trapDescription = "";
		}

		if (disableDc == null) {
			disableDc = "";
		}

		if (cr == null) {
			cr = "";
		}

		if (trapType == null) {
			trapType = "";
		}

		if (triggerType == null) {
			triggerType = "";
		}

		if (resetType == null) {
			resetType = "";
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
		if (spotDc != null && !spotDc.equals("")) {
			try {
				Integer.parseInt(spotDc);
			} catch (Exception e) {
				errors
						.add("spotDc is not a valid number.  Please enter a valid number.");
			}
		}
		if (disableDc != null && !disableDc.equals("")) {
			try {
				Integer.parseInt(disableDc);
			} catch (Exception e) {
				errors
						.add("disableDc is not a valid number.  Please enter a valid number.");
			}
		}
		if (cr != null && !cr.equals("")) {
			try {
				Integer.parseInt(cr);
			} catch (Exception e) {
				errors
						.add("cr is not a valid number.  Please enter a valid number.");
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
	 * Get the value of trapId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTrapId() {
		return trapId;
	}

	/**
	 * Set the value of trapId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTrapId(String s) {
		trapId = s;
	}

	/**
	 * Get the value of trapName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTrapName() {
		return trapName;
	}

	/**
	 * Set the value of trapName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTrapName(String s) {
		trapName = s;
	}

	/**
	 * Get the value of spotDc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSpotDc() {
		return spotDc;
	}

	/**
	 * Set the value of spotDc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSpotDc(String s) {
		spotDc = s;
	}

	/**
	 * Get the value of trapDescription
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTrapDescription() {
		return trapDescription;
	}

	/**
	 * Set the value of trapDescription
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTrapDescription(String s) {
		trapDescription = s;
	}

	/**
	 * Get the value of disableDc
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDisableDc() {
		return disableDc;
	}

	/**
	 * Set the value of disableDc
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDisableDc(String s) {
		disableDc = s;
	}

	/**
	 * Get the value of cr
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCr() {
		return cr;
	}

	/**
	 * Set the value of cr
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCr(String s) {
		cr = s;
	}

	/**
	 * Get the value of trapType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTrapType() {
		return trapType;
	}

	/**
	 * Set the value of trapType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTrapType(String s) {
		trapType = s;
	}

	/**
	 * Get the value of triggerType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTriggerType() {
		return triggerType;
	}

	/**
	 * Set the value of triggerType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTriggerType(String s) {
		triggerType = s;
	}

	/**
	 * Get the value of resetType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getResetType() {
		return resetType;
	}

	/**
	 * Set the value of resetType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setResetType(String s) {
		resetType = s;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return trapDescription;
	}

	public String getFullDescription() {
		// TODO Auto-generated method stub
		return trapDescription;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return trapId;
	}

	public void setId(String id){
		trapId = id;
	}
}
