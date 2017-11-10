package initcheck.database;

import java.io.Serializable;

public class PartyInfo implements Serializable, Cloneable, Exportable {

	private static final long serialVersionUID = 1L;

	String partyName;

	String color;

	String location;

	String notes;

	String type;
	
	String campaign;
	
	String campaignId;

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get the Notes value.
	 * 
	 * @return the Notes value.
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set the Notes value.
	 * 
	 * @param newNotes
	 *            The new Notes value.
	 */
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}

	/**
	 * Get the Location value.
	 * 
	 * @return the Location value.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Set the Location value.
	 * 
	 * @param newLocation
	 *            The new Location value.
	 */
	public void setLocation(String newLocation) {
		this.location = newLocation;
	}

	/**
	 * Get the Color value.
	 * 
	 * @return the Color value.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Set the Color value.
	 * 
	 * @param newColor
	 *            The new Color value.
	 */
	public void setColor(String newColor) {
		this.color = newColor;
	}

	/**
	 * Get the PartyName value.
	 * 
	 * @return the PartyName value.
	 */
	public String getPartyName() {
		return partyName;
	}

	/**
	 * Set the PartyName value.
	 * 
	 * @param newPartyName
	 *            The new PartyName value.
	 */
	public void setPartyName(String newPartyName) {
		this.partyName = newPartyName;
	}

	public String exportFormat() {
		// TODO Auto-generated method stub
		return null;
	}

}
