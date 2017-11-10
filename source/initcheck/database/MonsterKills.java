package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class MonsterKills implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String monsterId;

	private String monsterName;

	private String playerId;

	private String playerName;

	private String battleId;

	public MonsterKills() {

	}

	public MonsterKills(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("MonsterId")) {
				setMonsterId(t.getTagBody());
			}
			if (t.getTagName().equals("MonsterName")) {
				setMonsterName(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerName")) {
				setPlayerName(t.getTagBody());
			}
			if (t.getTagName().equals("BattleId")) {
				setBattleId(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<MonsterKills>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<MonsterId>" + monsterId + "</MonsterId>\n");
		sb.append("<MonsterName>" + monsterName + "</MonsterName>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<PlayerName>" + playerName + "</PlayerName>\n");
		sb.append("<BattleId>" + battleId + "</BattleId>\n");
		sb.append("</MonsterKills>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (monsterId == null) {
			monsterId = "";
		}

		if (monsterName == null) {
			monsterName = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (playerName == null) {
			playerName = "";
		}

		if (battleId == null) {
			battleId = "";
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
		if (monsterId != null && !monsterId.equals("")) {
			try {
				Integer.parseInt(monsterId);
			} catch (Exception e) {
				errors
						.add("monsterId is not a valid number.  Please enter a valid number.");
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
		if (battleId != null && !battleId.equals("")) {
			try {
				Integer.parseInt(battleId);
			} catch (Exception e) {
				errors
						.add("battleId is not a valid number.  Please enter a valid number.");
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
	 * Get the value of monsterName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMonsterName() {
		return monsterName;
	}

	/**
	 * Set the value of monsterName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMonsterName(String s) {
		monsterName = s;
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
	 * Get the value of playerName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the value of playerName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerName(String s) {
		playerName = s;
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

}
