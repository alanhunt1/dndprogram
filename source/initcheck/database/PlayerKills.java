package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class PlayerKills implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String monsterId;

	private String dateKilled;

	private String playerName;

	private String battleId;

	private String monsterName;

	public String toString() {
		return monsterName;
	}

	public PlayerKills() {

	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (monsterId == null) {
			monsterId = "";
		}

		if (dateKilled == null) {
			dateKilled = "";
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
		if (monsterId != null && !monsterId.equals("")) {
			try {
				Integer.parseInt(monsterId);
			} catch (Exception e) {
				errors
						.add("monsterId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of monsterId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMonsterId() {
		return monsterId;
	}

	/**
	 * Set the value of monsterId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMonsterId(String s) {
		monsterId = s;
	}

	/**
	 * Get the value of dateKilled
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDateKilled() {
		return dateKilled;
	}

	/**
	 * Set the value of dateKilled
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDateKilled(String s) {
		dateKilled = s;
	}

	/**
	 * Get the BattleId value.
	 * 
	 * @return the BattleId value.
	 */
	public String getBattleId() {
		return battleId;
	}

	/**
	 * Set the BattleId value.
	 * 
	 * @param newBattleId
	 *            The new BattleId value.
	 */
	public void setBattleId(String newBattleId) {
		this.battleId = newBattleId;
	}

	/**
	 * Get the MonsterName value.
	 * 
	 * @return the MonsterName value.
	 */
	public String getMonsterName() {
		return monsterName;
	}

	/**
	 * Set the MonsterName value.
	 * 
	 * @param newMonsterName
	 *            The new MonsterName value.
	 */
	public void setMonsterName(String newMonsterName) {
		this.monsterName = newMonsterName;
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

}
