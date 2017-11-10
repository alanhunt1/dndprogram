package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class CampaignJournals implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String journalId;
	private String campaignId;
	private String partyId;
	private String playerId;
	private String journalText;
	private String playerName;
	private String partyName;
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public CampaignJournals() {

	}

	public String toString(){
		return partyName+" ("+playerName+")";
	}
	
	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("JournalId")) {
				setJournalId(t.getTagBody());
			}
			if (t.getTagName().equals("CampaignId")) {
				setCampaignId(t.getTagBody());
			}
			if (t.getTagName().equals("PartyId")) {
				setPartyId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("JournalText")) {
				setJournalText(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<CampaignJournals>\n");
		sb.append("<JournalId>" + journalId + "</JournalId>\n");
		sb.append("<CampaignId>" + campaignId + "</CampaignId>\n");
		sb.append("<PartyId>" + partyId + "</PartyId>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<JournalText>" + journalText + "</JournalText>\n");
		sb.append("</CampaignJournals>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (journalId == null) {
			journalId = "";
		}

		if (campaignId == null) {
			campaignId = "";
		}

		if (partyId == null) {
			partyId = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (journalText == null) {
			journalText = "";
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
		if (partyId != null && !partyId.equals("")) {
			try {
				Integer.parseInt(partyId);
			} catch (Exception e) {
				errors
						.add("partyId is not a valid number.  Please enter a valid number.");
			}
		}
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors
						.add("playerId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of journalId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getJournalId() {
		return journalId;
	}

	/**
	 * Set the value of journalId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setJournalId(String s) {
		journalId = s;
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

	/**
	 * Get the value of partyId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPartyId() {
		return partyId;
	}

	/**
	 * Set the value of partyId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPartyId(String s) {
		partyId = s;
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
	 * Get the value of journalText
	 * 
	 * @return a <code>String</code> value
	 */
	public String getJournalText() {
		return journalText;
	}

	/**
	 * Set the value of journalText
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setJournalText(String s) {
		journalText = s;
	}

}
