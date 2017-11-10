package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class BattleHistory implements Serializable, Cloneable { 

    private String id;
    private String party;
    private String members;
    private String battleDate;
    private String partyLevel;
    private String encounterLevel;
		String maxDamage;
		String maxRoundDamage;
		String totalDamage;
		String totalTaken;
		String numRounds;
		
    public BattleHistory(){

    }
		
		public String toString(){
				return battleDate+" "+party;
		}
		
		public BattleHistory (String s){ 
				Tag t = Tag.getTag(s); 
				while (t != null) { 
						String tagName = t.getTagName();
						String tagBody = t.getTagBody();
						if (t.getTagName().equals("Id")){
								setId(t.getTagBody());
						}
						if (t.getTagName().equals("Party")){
								setParty(t.getTagBody());
						}
						if (t.getTagName().equals("Members")){
								setMembers(t.getTagBody());
						}
						if (t.getTagName().equals("BattleDate")){
								setBattleDate(t.getTagBody());
						}
						if (t.getTagName().equals("PartyLevel")){
								setPartyLevel(t.getTagBody());
						}
						if (t.getTagName().equals("EncounterLevel")){
								setEncounterLevel(t.getTagBody());
						}
						if (t.getTagName().equals("TotalDamage")){
								setTotalDamage(t.getTagBody());
						}
						if (t.getTagName().equals("TotalTaken")){
								setTotalTaken(t.getTagBody());
						}
						if (t.getTagName().equals("MaxDamage")){
								setMaxDamage(t.getTagBody());
						}
						if (t.getTagName().equals("MaxRoundDamage")){
								setMaxRoundDamage(t.getTagBody());
						}
						if (t.getTagName().equals("NumRounds")){
								setNumRounds(t.getTagBody());
						}
										
						s = s.substring((2*tagName.length())+5+tagBody.length(), s.length()); 
						t = Tag.getTag(s); 
				}
		}
		
    public String exportFormat(){ 
				StringBuffer sb = new StringBuffer();
				sb.append("<BattleHistory>\n");
				sb.append("<Id>"+id+"</Id>\n");
				sb.append("<Party>"+party+"</Party>\n");
				sb.append("<Members>"+members+"</Members>\n");
				sb.append("<BattleDate>"+battleDate+"</BattleDate>\n");
				sb.append("<PartyLevel>"+partyLevel+"</PartyLevel>\n");
				sb.append("<EncounterLevel>"+encounterLevel+"</EncounterLevel>\n");
				sb.append("<TotalDamage>"+totalDamage+"</TotalDamage>\n");
				sb.append("<TotalTaken>"+totalTaken+"</TotalTaken>\n");
				sb.append("<MaxDamage>"+maxDamage+"</MaxDamage>\n");
				sb.append("<MaxRoundDamage>"+maxRoundDamage+"</MaxRoundDamage>\n");
				sb.append("<NumRounds>"+numRounds+"</NumRounds>\n");
				sb.append("</BattleHistory>\n");
				return sb.toString();
		}
		
    public void convertNulls(){ 
				if (id == null) {
						id = "";
				}
				
				if (party == null) {
						party = "";
				}
				
				if (members == null) {
						members = "";
				}
				
				if (battleDate == null) {
						battleDate = "";
				}
				
				if (partyLevel == null) {
						partyLevel = "";
				}
				
				if (encounterLevel == null) {
						encounterLevel = "";
				}
				
    }
		
    public Vector<String> validate(){ 
				Vector<String> v = new Vector<String>();
				checkStrings(v);
				checkNumbers(v);
				checkDates(v);
				return v;
		}
		
		
    private void checkStrings(Vector<String> errors){ 
		}
		
    private void checkNumbers(Vector<String> errors){ 
		}
		
    private void checkDates(Vector<String> errors){ 
		}
		
    public String format(String s){ 
				if (s == null || s.equals("null")){
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
     * @return a <code>String</code> value
     */
    public String getId(){
       return id;
    }

    /**
     * Set the value of id
     * @param a <code>String</code> value
     */
    public void setId(String s){
        id = s;
    }

    /**
     * Get the value of party
     * @return a <code>String</code> value
     */
    public String getParty(){
       return party;
    }

    /**
     * Set the value of party
     * @param a <code>String</code> value
     */
    public void setParty(String s){
        party = s;
    }

    /**
     * Get the value of members
     * @return a <code>String</code> value
     */
    public String getMembers(){
       return members;
    }

    /**
     * Set the value of members
     * @param a <code>String</code> value
     */
    public void setMembers(String s){
        members = s;
    }

    /**
     * Get the value of battleDate
     * @return a <code>String</code> value
     */
    public String getBattleDate(){
       return battleDate;
    }

    /**
     * Set the value of battleDate
     * @param a <code>String</code> value
     */
    public void setBattleDate(String s){
        battleDate = s;
    }

    /**
     * Get the value of partyLevel
     * @return a <code>String</code> value
     */
    public String getPartyLevel(){
       return partyLevel;
    }

    /**
     * Set the value of partyLevel
     * @param a <code>String</code> value
     */
    public void setPartyLevel(String s){
        partyLevel = s;
    }

    /**
     * Get the value of encounterLevel
     * @return a <code>String</code> value
     */
    public String getEncounterLevel(){
       return encounterLevel;
    }

    /**
     * Set the value of encounterLevel
     * @param a <code>String</code> value
     */
    public void setEncounterLevel(String s){
        encounterLevel = s;
    }

		/**
		 * Get the TotalTaken value.
		 * @return the TotalTaken value.
		 */
		public String getTotalTaken() {
				return totalTaken;
		}

		/**
		 * Set the TotalTaken value.
		 * @param newTotalTaken The new TotalTaken value.
		 */
		public void setTotalTaken(String newTotalTaken) {
				this.totalTaken = newTotalTaken;
		}

		/**
		 * Get the TotalDamage value.
		 * @return the TotalDamage value.
		 */
		public String getTotalDamage() {
				return totalDamage;
		}

		/**
		 * Set the TotalDamage value.
		 * @param newTotalDamage The new TotalDamage value.
		 */
		public void setTotalDamage(String newTotalDamage) {
				this.totalDamage = newTotalDamage;
		}
		
		/**
		 * Get the MaxRoundDamage value.
		 * @return the MaxRoundDamage value.
		 */
		public String getMaxRoundDamage() {
				return maxRoundDamage;
		}

		/**
		 * Set the MaxRoundDamage value.
		 * @param newMaxRoundDamage The new MaxRoundDamage value.
		 */
		public void setMaxRoundDamage(String newMaxRoundDamage) {
				this.maxRoundDamage = newMaxRoundDamage;
		}
		
		/**
		 * Get the MaxDamage value.
		 * @return the MaxDamage value.
		 */
		public String getMaxDamage() {
				return maxDamage;
		}

		/**
		 * Set the MaxDamage value.
		 * @param newMaxDamage The new MaxDamage value.
		 */
		public void setMaxDamage(String newMaxDamage) {
				this.maxDamage = newMaxDamage;
		}

		/**
		 * Get the NumRounds value.
		 * @return the NumRounds value.
		 */
		public String getNumRounds() {
				return numRounds;
		}

		/**
		 * Set the NumRounds value.
		 * @param newNumRounds The new NumRounds value.
		 */
		public void setNumRounds(String newNumRounds) {
				this.numRounds = newNumRounds;
		}

}
