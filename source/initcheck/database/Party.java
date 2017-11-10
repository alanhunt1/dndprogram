package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Party implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String partyName;

	private String partyColor;

	private String partyLocation;

	private String partyNotes;

	private String partyType;

	private String campaignId;

	private String elapsedTime;
	
	public String listFormat(){
		return StrManip.pad(partyName, 30);
	}
	
	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String listValue(){
		return "Party : "+partyName;
	}
	
	public String getName(){
		return partyName;
	}
	
	public void setName(String s){
		partyName = s;
	}
	
	public String toString(){
		return partyName;
	}
	
	public Party() {

	}

	public Party(String s) {
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
			if (t.getTagName().equals("PartyName")) {
				setPartyName(t.getTagBody());
			}
			if (t.getTagName().equals("PartyColor")) {
				setPartyColor(t.getTagBody());
			}
			if (t.getTagName().equals("PartyLocation")) {
				setPartyLocation(t.getTagBody());
			}
			if (t.getTagName().equals("PartyNotes")) {
				setPartyNotes(t.getTagBody());
			}
			if (t.getTagName().equals("PartyType")) {
				setPartyType(t.getTagBody());
			}
			if (t.getTagName().equals("CampaignId")) {
				setCampaignId(t.getTagBody());
			}
			if (t.getTagName().equals("ElapsedTime")) {;
				setElapsedTime(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Party>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PartyName>" + partyName + "</PartyName>\n");
		sb.append("<PartyColor>" + partyColor + "</PartyColor>\n");
		sb.append("<PartyLocation>" + partyLocation + "</PartyLocation>\n");
		sb.append("<PartyNotes>" + partyNotes + "</PartyNotes>\n");
		sb.append("<PartyType>" + partyType + "</PartyType>\n");
		sb.append("<CampaignId>" + campaignId + "</CampaignId>\n");
		sb.append("<ElapsedTime>" + campaignId + "</ElapsedTime>\n");
		sb.append("</Party>\n");
		return sb.toString();
	}

	public String existsAs(){
		PartyDAO db = new PartyDAO();
		Vector<Party> v = db.selectParty(this);
		if(v.size() > 0){
			return ((Party)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Party> getValues(){
		PartyDAO db = new PartyDAO();
		return db.getParties();
	}
	
	public void save(boolean overwrite){
		PartyDAO db = new PartyDAO();
		if (overwrite){
			db.addOrUpdateParty(this);
		}else{
			db.addParty(this);
		}
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (partyName == null) {
			partyName = "";
		}

		if (partyColor == null) {
			partyColor = "";
		}

		if (partyLocation == null) {
			partyLocation = "";
		}

		if (partyNotes == null) {
			partyNotes = "";
		}

		if (partyType == null) {
			partyType = "";
		}

		if (campaignId == null) {
			campaignId = "";
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
		if (campaignId != null && !campaignId.equals("")) {
			try {
				Integer.parseInt(campaignId);
			} catch (Exception e) {
				errors
						.add("campaignId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of partyName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * Set the value of partyName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyName(String s) {
		partyName = s;
	}

	/**
	 * Get the value of partyColor
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyColor() {
		return partyColor;
	}

	/**
	 * Set the value of partyColor
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyColor(String s) {
		partyColor = s;
	}

	/**
	 * Get the value of partyLocation
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyLocation() {
		return partyLocation;
	}

	/**
	 * Set the value of partyLocation
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyLocation(String s) {
		partyLocation = s;
	}

	/**
	 * Get the value of partyNotes
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyNotes() {
		return partyNotes;
	}

	/**
	 * Set the value of partyNotes
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyNotes(String s) {
		partyNotes = s;
	}

	/**
	 * Get the value of partyType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyType() {
		return partyType;
	}

	/**
	 * Set the value of partyType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyType(String s) {
		partyType = s;
	}

	/**
	 * Get the value of campaignId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getCampaignId() {
		return campaignId;
	}

	/**
	 * Set the value of campaignId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setCampaignId(String s) {
		campaignId = s;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFullDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

}
