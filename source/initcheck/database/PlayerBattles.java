package initcheck.database;

import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class PlayerBattles implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String battleId;

	private String numStuns;

	private String roundsStunned;

	private String maxDamage;

	private String maxRoundDamage;

	private String roundsDead;

	private String totalDamage;

	private String totalTaken;

	private String numCrits;

	private String numFumbles;

	private String numDoubleDamage;

	String playerName;

	String numHits;

	String numAttacks;

	String date;

	String party;

	String numKills;

	/**
	 * Get the NumKills value.
	 * 
	 * @return the NumKills value.
	 */
	public String getNumKills() {
		return numKills;
	}

	/**
	 * Set the NumKills value.
	 * 
	 * @param newNumKills
	 *            The new NumKills value.
	 */
	public void setNumKills(String newNumKills) {
		this.numKills = newNumKills;
	}

	/**
	 * Get the Party value.
	 * 
	 * @return the Party value.
	 */
	public String getParty() {
		return party;
	}

	/**
	 * Set the Party value.
	 * 
	 * @param newParty
	 *            The new Party value.
	 */
	public void setParty(String newParty) {
		this.party = newParty;
	}

	/**
	 * Get the Date value.
	 * 
	 * @return the Date value.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set the Date value.
	 * 
	 * @param newDate
	 *            The new Date value.
	 */
	public void setDate(String newDate) {
		this.date = newDate;
	}

	public String getHitPercentage() {
		double hits = Double.parseDouble(numHits);
		double attacks = Double.parseDouble(numAttacks);
		double ratio = 0.0;
		if (attacks > 0)
			ratio = hits / attacks;
		return StrManip.formatDecimal("" + ratio);
	}

	public String getAvgDamage() {
		double hits = Double.parseDouble(numHits);
		double damage = Double.parseDouble(totalDamage);
		double ratio = 0.0;
		if (hits > 0)
			ratio = damage / hits;
		return StrManip.formatDecimal("" + ratio);
	}

	/**
	 * Get the NumAttacks value.
	 * 
	 * @return the NumAttacks value.
	 */
	public String getNumAttacks() {
		return numAttacks;
	}

	/**
	 * Set the NumAttacks value.
	 * 
	 * @param newNumAttacks
	 *            The new NumAttacks value.
	 */
	public void setNumAttacks(String newNumAttacks) {
		this.numAttacks = newNumAttacks;
	}

	/**
	 * Get the NumHits value.
	 * 
	 * @return the NumHits value.
	 */
	public String getNumHits() {
		return numHits;
	}

	/**
	 * Set the NumHits value.
	 * 
	 * @param newNumHits
	 *            The new NumHits value.
	 */
	public void setNumHits(String newNumHits) {
		this.numHits = newNumHits;
	}

	/**
	 * Get the PlayerName value.
	 * 
	 * @return the PlayerName value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the PlayerName value.
	 * 
	 * @param newPlayerName
	 *            The new PlayerName value.
	 */
	public void setPlayerName(String newPlayerName) {
		this.playerName = newPlayerName;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(date + " " + party);

		// sb.append(playerName+" ");
		// if (!numStuns.equals("0")){
		// sb.append(" stn "+numStuns);
		// }
		// if (!roundsStunned.equals("0")){
		// sb.append(" snd "+roundsStunned);
		// }
		// if (!maxDamage.equals("0"))
		// sb.append(" maxd "+maxDamage);
		// if (!maxRoundDamage.equals("0"))
		// sb.append(" rndd "+maxRoundDamage);
		// if (!roundsDead.equals("0"))
		// sb.append(" dead "+roundsDead);
		// if (!totalDamage.equals("0"))
		// sb.append(" ttld "+totalDamage);
		// if (!totalTaken.equals("0"))
		// sb.append(" lost "+totalTaken);
		// if (!numCrits.equals("0"))
		// sb.append(" crt "+numCrits);
		// if (!numFumbles.equals("0"))
		// sb.append(" fmb "+numFumbles);
		// if (!numDoubleDamage.equals("0"))
		// sb.append(" dd "+numDoubleDamage);
		// if (!numAttacks.equals("0"))
		// sb.append(" atk "+numAttacks);
		// if (!numHits.equals("0"))
		// sb.append(" hit "+numHits);

		return sb.toString();
	}

	public PlayerBattles() {

	}

	public PlayerBattles(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("BattleId")) {
				setBattleId(t.getTagBody());
			}
			if (t.getTagName().equals("NumStuns")) {
				setNumStuns(t.getTagBody());
			}
			if (t.getTagName().equals("RoundsStunned")) {
				setRoundsStunned(t.getTagBody());
			}
			if (t.getTagName().equals("MaxDamage")) {
				setMaxDamage(t.getTagBody());
			}
			if (t.getTagName().equals("MaxRoundDamage")) {
				setMaxRoundDamage(t.getTagBody());
			}
			if (t.getTagName().equals("RoundsDead")) {
				setRoundsDead(t.getTagBody());
			}
			if (t.getTagName().equals("TotalDamage")) {
				setTotalDamage(t.getTagBody());
			}
			if (t.getTagName().equals("TotalTaken")) {
				setTotalTaken(t.getTagBody());
			}
			if (t.getTagName().equals("NumCrits")) {
				setNumCrits(t.getTagBody());
			}
			if (t.getTagName().equals("NumFumbles")) {
				setNumFumbles(t.getTagBody());
			}
			if (t.getTagName().equals("NumDoubleDamage")) {
				setNumDoubleDamage(t.getTagBody());
			}
			if (t.getTagName().equals("NumAttacks")) {
				setNumAttacks(t.getTagBody());
			}
			if (t.getTagName().equals("NumHits")) {
				setNumHits(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerBattles>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<BattleId>" + battleId + "</BattleId>\n");
		sb.append("<NumStuns>" + numStuns + "</NumStuns>\n");
		sb.append("<RoundsStunned>" + roundsStunned + "</RoundsStunned>\n");
		sb.append("<MaxDamage>" + maxDamage + "</MaxDamage>\n");
		sb.append("<MaxRoundDamage>" + maxRoundDamage + "</MaxRoundDamage>\n");
		sb.append("<RoundsDead>" + roundsDead + "</RoundsDead>\n");

		sb.append("<TotalDamage>" + totalDamage + "</TotalDamage>\n");
		sb.append("<TotalTaken>" + totalTaken + "</TotalTaken>\n");
		sb.append("<NumCrits>" + numCrits + "</NumCrits>\n");
		sb.append("<NumFumbles>" + numFumbles + "</NumFumbles>\n");
		sb.append("<NumDoubleDamage>" + numDoubleDamage
				+ "</NumDoubleDamage>\n");
		sb.append("<NumAttacks>" + numAttacks + "</NumAttacks>\n");
		sb.append("<NumHits>" + numHits + "</NumHits>\n");
		sb.append("</PlayerBattles>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (battleId == null) {
			battleId = "";
		}

		if (numStuns == null) {
			numStuns = "";
		}

		if (roundsStunned == null) {
			roundsStunned = "";
		}

		if (maxDamage == null) {
			maxDamage = "";
		}

		if (maxRoundDamage == null) {
			maxRoundDamage = "";
		}

		if (roundsDead == null) {
			roundsDead = "";
		}

		if (totalDamage == null) {
			totalDamage = "";
		}

		if (totalTaken == null) {
			totalTaken = "";
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
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors
						.add("playerId is not a valid number.  Please enter a valid number.");
			}
		}
		if (battleId != null && !battleId.equals("")) {
			try {
				Integer.parseInt(battleId);
			} catch (Exception e) {
				errors
						.add("battleId is not a valid number.  Please enter a valid number.");
			}
		}
		if (numStuns != null && !numStuns.equals("")) {
			try {
				Integer.parseInt(numStuns);
			} catch (Exception e) {
				errors
						.add("numStuns is not a valid number.  Please enter a valid number.");
			}
		}
		if (roundsStunned != null && !roundsStunned.equals("")) {
			try {
				Integer.parseInt(roundsStunned);
			} catch (Exception e) {
				errors
						.add("roundsStunned is not a valid number.  Please enter a valid number.");
			}
		}
		if (maxDamage != null && !maxDamage.equals("")) {
			try {
				Integer.parseInt(maxDamage);
			} catch (Exception e) {
				errors
						.add("maxDamage is not a valid number.  Please enter a valid number.");
			}
		}
		if (maxRoundDamage != null && !maxRoundDamage.equals("")) {
			try {
				Integer.parseInt(maxRoundDamage);
			} catch (Exception e) {
				errors
						.add("maxRoundDamage is not a valid number.  Please enter a valid number.");
			}
		}
		if (roundsDead != null && !roundsDead.equals("")) {
			try {
				Integer.parseInt(roundsDead);
			} catch (Exception e) {
				errors
						.add("roundsDead is not a valid number.  Please enter a valid number.");
			}
		}

		if (totalDamage != null && !totalDamage.equals("")) {
			try {
				Integer.parseInt(totalDamage);
			} catch (Exception e) {
				errors
						.add("totalDamage is not a valid number.  Please enter a valid number.");
			}
		}
		if (totalTaken != null && !totalTaken.equals("")) {
			try {
				Integer.parseInt(totalTaken);
			} catch (Exception e) {
				errors
						.add("totalTaken is not a valid number.  Please enter a valid number.");
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
	 * Get the value of battleId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getBattleId() {
		return battleId;
	}

	/**
	 * Set the value of battleId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setBattleId(String s) {
		battleId = s;
	}

	/**
	 * Get the value of numStuns
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNumStuns() {
		return numStuns;
	}

	/**
	 * Set the value of numStuns
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNumStuns(String s) {
		numStuns = s;
	}

	/**
	 * Get the value of roundsStunned
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRoundsStunned() {
		return roundsStunned;
	}

	/**
	 * Set the value of roundsStunned
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRoundsStunned(String s) {
		roundsStunned = s;
	}

	/**
	 * Get the value of maxDamage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMaxDamage() {
		return maxDamage;
	}

	/**
	 * Set the value of maxDamage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMaxDamage(String s) {
		maxDamage = s;
	}

	/**
	 * Get the value of maxRoundDamage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMaxRoundDamage() {
		return maxRoundDamage;
	}

	/**
	 * Set the value of maxRoundDamage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMaxRoundDamage(String s) {
		maxRoundDamage = s;
	}

	/**
	 * Get the value of roundsDead
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRoundsDead() {
		return roundsDead;
	}

	/**
	 * Set the value of roundsDead
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRoundsDead(String s) {
		roundsDead = s;
	}

	/**
	 * Get the value of totalDamage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTotalDamage() {
		return totalDamage;
	}

	/**
	 * Set the value of totalDamage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTotalDamage(String s) {
		totalDamage = s;
	}

	/**
	 * Get the value of totalTaken
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTotalTaken() {
		return totalTaken;
	}

	/**
	 * Set the value of totalTaken
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTotalTaken(String s) {
		totalTaken = s;
	}

	/**
	 * Get the value of numCrits
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNumCrits() {
		return numCrits;
	}

	/**
	 * Set the value of numCrits
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNumCrits(String s) {
		numCrits = s;
	}

	/**
	 * Get the value of numFumbles
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNumFumbles() {
		return numFumbles;
	}

	/**
	 * Set the value of numFumbles
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNumFumbles(String s) {
		numFumbles = s;
	}

	/**
	 * Get the value of numDoubleDamage
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNumDoubleDamage() {
		return numDoubleDamage;
	}

	/**
	 * Set the value of numDoubleDamage
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNumDoubleDamage(String s) {
		numDoubleDamage = s;
	}

}
