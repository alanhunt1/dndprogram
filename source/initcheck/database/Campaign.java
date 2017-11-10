package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class Campaign implements Serializable, Cloneable, Exportable, LibraryItem, Importable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String notes;

	private String rulesetId;
	
	private String rulesetName;
	
	public String getRulesetName() {
		return rulesetName;
	}

	public void setRulesetName(String rulesetName) {
		this.rulesetName = rulesetName;
	}

	private Vector<CampaignSource> sources = new Vector<CampaignSource>();
	
	private Vector<CampaignWhiteList> whiteList = new Vector<CampaignWhiteList>();
	
	private Vector<CampaignBlackList> blackList = new Vector<CampaignBlackList>();
	
	public String getRulesetId() {
		return rulesetId;
	}

	public void setRulesetId(String rulesetId) {
		this.rulesetId = rulesetId;
	}

	
	public Vector<CampaignBlackList> getBlackList() {
		return blackList;
	}

	public void setBlackList(Vector<CampaignBlackList> blackList) {
		this.blackList = blackList;
	}

	public Vector<CampaignWhiteList> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(Vector<CampaignWhiteList> whiteList) {
		this.whiteList = whiteList;
	}

	public Vector<CampaignSource> getSources() {
		return sources;
	}

	public void setSources(Vector<CampaignSource> sources) {
		this.sources = sources;
	}
	
	public Campaign() {

	}

	public boolean isValidSource(String source){
		boolean valid = false;
		for (int i = 0; i < sources.size(); i++){
			CampaignSource cs = sources.get(i);
			if (cs.getName().equalsIgnoreCase(source)){
				valid = true;
			}
		}
		return valid;
	}
	
	public boolean isBlackListed(Feat f){
		
		boolean blackListed = !isValidSource(f.getSource());
		
		
		
		for (int i = 0; i < blackList.size(); i++){
			CampaignBlackList cbl = blackList.get(i);
			if (cbl.getXrefId().equals(f.getId()) && cbl.getType().equals("Feat")){
				blackListed = true;
				
			}
		}
		return blackListed;
	}
	
	public String listFormat(){
		return StrManip.pad(name, 30);
	}
	
	public String toString(){
		return name;
	}
	
	public String listValue(){
		return "Campaign : "+name;
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
			if (t.getTagName().equals("Notes")) {
				setNotes(t.getTagBody());
			}
			if (t.getTagName().equals("CampaignSource")) {
				sources.add(new CampaignSource(t.getTagBody()));
			}
			if (t.getTagName().equals("CampaignWhiteList")) {
				whiteList.add(new CampaignWhiteList(t.getTagBody()));
			}
			if (t.getTagName().equals("CampaignBlackList")) {
				blackList.add(new CampaignBlackList(t.getTagBody()));
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Campaign>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<Notes>" + notes + "</Notes>\n");
		for (int i = 0; i < sources.size(); i++){
			sb.append(sources.get(i).exportFormat());
		}
		for (int i = 0; i < whiteList.size(); i++){
			sb.append(whiteList.get(i).exportFormat());
		}
		for (int i = 0; i < blackList.size(); i++){
			sb.append(blackList.get(i).exportFormat());
		}
		sb.append("</Campaign>\n");
		return sb.toString();
	}

	public String existsAs(){
		CampaignDAO db = new CampaignDAO();
		Vector<Campaign> v = db.selectCampaign(this);
		if(v.size() > 0){
			return ((Campaign)v.get(0)).getName();
		}
		return null;
	}
	
	public void save(boolean overwrite){
		CampaignDAO db = new CampaignDAO();
		CampaignSourceDAO sdb = new CampaignSourceDAO();
		sdb.clear(id);
		if (overwrite){
			db.addOrUpdateCampaign(this);
		}else{
			db.addCampaign(this);
		}
		for (int i = 0; i < sources.size(); i++){
			sdb.addCampaignSource(sources.get(i));
		}
		
	}
	
	public Vector<Campaign> getValues(){
		CampaignDAO db = new CampaignDAO();
		return db.getCampaigns();
	}
	
	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (name == null) {
			name = "";
		}

		if (notes == null) {
			notes = "";
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
	 * Get the value of notes
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the value of notes
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNotes(String s) {
		notes = s;
	}

	public String getDescription() {
		
		return notes;
	}

	public String getFullDescription() {
	
		return notes;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

}
