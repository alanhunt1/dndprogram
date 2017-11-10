package initcheck.database;

import initcheck.utils.StrManip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerBattlesDAO extends InitBaseDAO {

	public PlayerBattlesDAO() {

	}

	public void updateId(String oldId, String id) {
		String sqlString = "UPDATE PLAYER_BATTLES SET PLAYER_ID = " + id
				+ " WHERE " + "PLAYER_ID = " + oldId;

		try {
			dbs.open();
			dbs.executeSQLCommand(sqlString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void addPlayerBattles(PlayerBattles o) {
		String valueString = "";
		String insertString = "INSERT INTO PLAYER_BATTLES (";
		if (o.getId() != null) {
			insertString += "ID,";
			valueString += "'" + dbs.escapeQuotes(o.getId()) + "',";
		}
		if (o.getPlayerId() != null) {
			insertString += "PLAYER_ID,";
			if (o.getPlayerId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getPlayerId()) + ",";

			}
		}
		if (o.getBattleId() != null) {
			insertString += "BATTLE_ID,";
			if (o.getBattleId().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getBattleId()) + ",";

			}
		}
		if (o.getNumStuns() != null) {
			insertString += "NUM_STUNS,";
			if (o.getNumStuns().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumStuns()) + ",";

			}
		}
		if (o.getRoundsStunned() != null) {
			insertString += "ROUNDS_STUNNED,";
			if (o.getRoundsStunned().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getRoundsStunned()) + ",";

			}
		}
		if (o.getMaxDamage() != null) {
			insertString += "MAX_DAMAGE,";
			if (o.getMaxDamage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMaxDamage()) + ",";

			}
		}
		if (o.getMaxRoundDamage() != null) {
			insertString += "MAX_ROUND_DAMAGE,";
			if (o.getMaxRoundDamage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getMaxRoundDamage()) + ",";

			}
		}
		if (o.getRoundsDead() != null) {
			insertString += "ROUNDS_DEAD,";
			if (o.getRoundsDead().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getRoundsDead()) + ",";

			}
		}

		if (o.getTotalDamage() != null) {
			insertString += "TOTAL_DAMAGE,";
			if (o.getTotalDamage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getTotalDamage()) + ",";

			}
		}
		if (o.getTotalTaken() != null) {
			insertString += "TOTAL_TAKEN,";
			if (o.getTotalTaken().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getTotalTaken()) + ",";

			}
		}

		if (o.getNumCrits() != null) {
			insertString += "NUM_CRITS,";
			if (o.getNumCrits().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumCrits()) + ",";

			}
		}
		if (o.getNumFumbles() != null) {
			insertString += "NUM_FUMBLES,";
			if (o.getNumFumbles().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumFumbles()) + ",";

			}
		}
		if (o.getNumDoubleDamage() != null) {
			insertString += "NUM_DOUBLE_DAMAGE,";
			if (o.getNumDoubleDamage().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumDoubleDamage()) + ",";

			}
		}
		if (o.getNumAttacks() != null) {
			insertString += "NUM_ATTACKS,";
			if (o.getNumAttacks().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumAttacks()) + ",";

			}
		}
		if (o.getNumHits() != null) {
			insertString += "NUM_HITS,";
			if (o.getNumHits().equals("")) {
				valueString += "null,";
			} else {
				valueString += dbs.escapeQuotes(o.getNumHits()) + ",";

			}
		}

		if (insertString.charAt(insertString.length() - 1) == ',') {
			insertString = insertString.substring(0, insertString.length() - 1);
		}
		if (valueString.charAt(valueString.length() - 1) == ',') {
			valueString = valueString.substring(0, valueString.length() - 1);
		}
		insertString += ") VALUES (";
		insertString += valueString;
		insertString += ")";

		try {
			dbs.open();
			logger.log("Executing Insert" + insertString);
			dbs.executeSQLCommand(insertString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public void updatePlayerBattles(PlayerBattles o) {
		String updateString = "update PLAYER_BATTLES set ";
		if (o.getPlayerId() != null) {
			if (!o.getPlayerId().equals("")) {
				updateString += "PLAYER_ID="
						+ dbs.escapeQuotes(o.getPlayerId()) + ",";
			} else {
				updateString += "PLAYER_ID=null,";
			}
		}
		if (o.getBattleId() != null) {
			if (!o.getBattleId().equals("")) {
				updateString += "BATTLE_ID="
						+ dbs.escapeQuotes(o.getBattleId()) + ",";
			} else {
				updateString += "BATTLE_ID=null,";
			}
		}
		if (o.getNumStuns() != null) {
			if (!o.getNumStuns().equals("")) {
				updateString += "NUM_STUNS="
						+ dbs.escapeQuotes(o.getNumStuns()) + ",";
			} else {
				updateString += "NUM_STUNS=null,";
			}
		}
		if (o.getRoundsStunned() != null) {
			if (!o.getRoundsStunned().equals("")) {
				updateString += "ROUNDS_STUNNED="
						+ dbs.escapeQuotes(o.getRoundsStunned()) + ",";
			} else {
				updateString += "ROUNDS_STUNNED=null,";
			}
		}
		if (o.getMaxDamage() != null) {
			if (!o.getMaxDamage().equals("")) {
				updateString += "MAX_DAMAGE="
						+ dbs.escapeQuotes(o.getMaxDamage()) + ",";
			} else {
				updateString += "MAX_DAMAGE=null,";
			}
		}
		if (o.getMaxRoundDamage() != null) {
			if (!o.getMaxRoundDamage().equals("")) {
				updateString += "MAX_ROUND_DAMAGE="
						+ dbs.escapeQuotes(o.getMaxRoundDamage()) + ",";
			} else {
				updateString += "MAX_ROUND_DAMAGE=null,";
			}
		}
		if (o.getRoundsDead() != null) {
			if (!o.getRoundsDead().equals("")) {
				updateString += "ROUNDS_DEAD="
						+ dbs.escapeQuotes(o.getRoundsDead()) + ",";
			} else {
				updateString += "ROUNDS_DEAD=null,";
			}
		}

		if (o.getTotalDamage() != null) {
			if (!o.getTotalDamage().equals("")) {
				updateString += "TOTAL_DAMAGE="
						+ dbs.escapeQuotes(o.getTotalDamage()) + ",";
			} else {
				updateString += "TOTAL_DAMAGE=null,";
			}
		}
		if (o.getTotalTaken() != null) {
			if (!o.getTotalTaken().equals("")) {
				updateString += "TOTAL_TAKEN="
						+ dbs.escapeQuotes(o.getTotalTaken()) + ",";
			} else {
				updateString += "TOTAL_TAKEN=null,";
			}
		}
		if (o.getNumCrits() != null) {
			if (!o.getNumCrits().equals("")) {
				updateString += "NUM_CRITS="
						+ dbs.escapeQuotes(o.getNumCrits()) + ",";
			} else {
				updateString += "NUM_CRITS=null,";
			}
		}
		if (o.getNumFumbles() != null) {
			if (!o.getNumFumbles().equals("")) {
				updateString += "NUM_FUMBLES="
						+ dbs.escapeQuotes(o.getNumFumbles()) + ",";
			} else {
				updateString += "NUM_FUMBLES=null,";
			}
		}
		if (o.getNumDoubleDamage() != null) {
			if (!o.getNumDoubleDamage().equals("")) {
				updateString += "NUM_DOUBLE_DAMAGE="
						+ dbs.escapeQuotes(o.getNumDoubleDamage()) + ",";
			} else {
				updateString += "NUM_DOUBLE_DAMAGE=null,";
			}
		}
		if (o.getNumAttacks() != null) {
			if (!o.getNumAttacks().equals("")) {
				updateString += "NUM_ATTACKS="
						+ dbs.escapeQuotes(o.getNumAttacks()) + ",";
			} else {
				updateString += "NUM_ATTACKS=null,";
			}
		}
		if (o.getNumHits() != null) {
			if (!o.getNumHits().equals("")) {
				updateString += "NUM_HITS=" + dbs.escapeQuotes(o.getNumHits())
						+ ",";
			} else {
				updateString += "NUM_HITS=null,";
			}
		}

		if (updateString.charAt(updateString.length() - 1) == ',') {
			updateString = updateString.substring(0, updateString.length() - 1);
		}
		updateString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";
		try {
			dbs.open();
			dbs.executeSQLCommand(updateString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}

	public String getLifetimeDamage(String playerId) {
		Vector<PlayerBattles> v = getPlayerBattles(playerId);
		double damage = 0;
		double hits = 0;

		for (int i = 0; i < v.size(); i++) {
			PlayerBattles pb = (PlayerBattles) v.get(i);
			damage += Double.parseDouble(pb.getTotalDamage());
			hits += Double.parseDouble(pb.getNumHits());
		}

		double ratio = 0.0;
		if (hits > 0)
			ratio = damage / hits;
		return StrManip.formatDecimal("" + ratio);
	}

	public String getLifetimeHit(String playerId) {
		Vector<PlayerBattles> v = getPlayerBattles(playerId);
		double attacks = 0;
		double hits = 0;

		for (int i = 0; i < v.size(); i++) {
			PlayerBattles pb = (PlayerBattles) v.get(i);
			attacks += Double.parseDouble(pb.getNumAttacks());
			hits += Double.parseDouble(pb.getNumHits());
		}

		double ratio = 0.0;
		if (attacks > 0)
			ratio = hits / attacks;
		return StrManip.formatDecimal("" + ratio);
	}

	public Vector<PlayerBattles> getPlayerBattles(String playerId) {
		PlayerBattles o = new PlayerBattles();
		o.setPlayerId(playerId);
		return selectPlayerBattles(o);
	}

	public Vector<PlayerBattles> getLifetimeSummaries(String party) {
		String selectString = "SELECT pb.player_id, sum(pb.num_stuns) as num_stuns, "
				+ " sum(pb.rounds_stunned) as rounds_stunned, max(pb.max_damage) as max_damage, "
				+ " max(pb.max_round_damage) as max_round_damage, sum(pb.rounds_dead) as rounds_dead, sum(pb.total_damage) as total_damage, "
				+ " sum(pb.total_taken) as total_taken, sum(pb.num_crits) as num_crits, "
				+ " sum(pb.num_fumbles) as num_fumbles, sum(pb.num_double_damage) as num_double_damage, "
				+ " sum(pb.num_attacks) as num_attacks, sum(pb.num_hits) as num_hits, p.NAME FROM PLAYER_BATTLES  pb, PLAYER p, Battle_History bh WHERE pb.PLAYER_ID = p.ID and pb.battle_id = bh.id ";

		if (party != null) {
			selectString += " and bh.party = '" + party + "'";
		}

		selectString += "group by p.name, pb.player_id ";

		Vector<PlayerBattles> v = new Vector<PlayerBattles>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				PlayerBattles obj = new PlayerBattles();

				// obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				// obj.setBattleId(result.getString("BATTLE_ID"));
				obj.setNumStuns(result.getString("NUM_STUNS"));
				obj.setRoundsStunned(result.getString("ROUNDS_STUNNED"));
				obj.setMaxDamage(result.getString("MAX_DAMAGE"));
				obj.setMaxRoundDamage(result.getString("MAX_ROUND_DAMAGE"));
				obj.setRoundsDead(result.getString("ROUNDS_DEAD"));
				obj.setTotalDamage(result.getString("TOTAL_DAMAGE"));
				obj.setTotalTaken(result.getString("TOTAL_TAKEN"));
				obj.setNumCrits(result.getString("NUM_CRITS"));
				obj.setNumFumbles(result.getString("NUM_FUMBLES"));
				obj.setNumDoubleDamage(result.getString("NUM_DOUBLE_DAMAGE"));
				obj.setNumAttacks(result.getString("NUM_ATTACKS"));
				obj.setNumHits(result.getString("NUM_HITS"));

				// obj.setParty(result.getString("PARTY"));
				obj.setPlayerName(result.getString("NAME"));

				PlayerKillsDAO pkdb = new PlayerKillsDAO();
				PlayerKills pk = new PlayerKills();
				pk.setPlayerId(obj.getPlayerId());
				obj.setNumKills("" + (pkdb.selectPlayerKills(pk).size()));

				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return v;
	}

	public Vector<PlayerBattles> selectPlayerBattles(PlayerBattles o) {
		String selectString = "SELECT pb.*, bh.battle_date, bh.party, p.NAME FROM PLAYER_BATTLES  pb, BATTLE_HISTORY bh, PLAYER p WHERE pb.BATTLE_ID = bh.ID AND pb.PLAYER_ID = p.ID ";

		boolean first = false;

		if (o.getId() != null && !o.getId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ID = " + dbs.escapeQuotes(o.getId()) + " ";
		}
		if (o.getPlayerId() != null && !o.getPlayerId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " PLAYER_ID = " + dbs.escapeQuotes(o.getPlayerId())
					+ " ";
		}
		if (o.getBattleId() != null && !o.getBattleId().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " pb.BATTLE_ID = "
					+ dbs.escapeQuotes(o.getBattleId()) + " ";
		}
		if (o.getNumStuns() != null && !o.getNumStuns().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " NUM_STUNS = '"
					+ dbs.escapeQuotes(o.getNumStuns()) + "' ";
		}
		if (o.getRoundsStunned() != null && !o.getRoundsStunned().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ROUNDS_STUNNED = '"
					+ dbs.escapeQuotes(o.getRoundsStunned()) + "' ";
		}
		if (o.getMaxDamage() != null && !o.getMaxDamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MAX_DAMAGE = '"
					+ dbs.escapeQuotes(o.getMaxDamage()) + "' ";
		}
		if (o.getMaxRoundDamage() != null && !o.getMaxRoundDamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " MAX_ROUND_DAMAGE = '"
					+ dbs.escapeQuotes(o.getMaxRoundDamage()) + "' ";
		}
		if (o.getRoundsDead() != null && !o.getRoundsDead().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " ROUNDS_DEAD = '"
					+ dbs.escapeQuotes(o.getRoundsDead()) + "' ";
		}

		if (o.getTotalDamage() != null && !o.getTotalDamage().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TOTAL_DAMAGE = '"
					+ dbs.escapeQuotes(o.getTotalDamage()) + "' ";
		}
		if (o.getTotalTaken() != null && !o.getTotalTaken().equals("")) {
			if (!first) {
				selectString += " AND ";
			} else {
				selectString += " WHERE ";
				first = false;
			}
			selectString += " TOTAL_TAKEN = '"
					+ dbs.escapeQuotes(o.getTotalTaken()) + "' ";
		}

		Vector<PlayerBattles> v = new Vector<PlayerBattles>();
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			while (result.next()) {
				PlayerBattles obj = new PlayerBattles();

				obj.setId(result.getString("ID"));
				obj.setPlayerId(result.getString("PLAYER_ID"));
				obj.setBattleId(result.getString("BATTLE_ID"));
				obj.setNumStuns(result.getString("NUM_STUNS"));
				obj.setRoundsStunned(result.getString("ROUNDS_STUNNED"));
				obj.setMaxDamage(result.getString("MAX_DAMAGE"));
				obj.setMaxRoundDamage(result.getString("MAX_ROUND_DAMAGE"));
				obj.setRoundsDead(result.getString("ROUNDS_DEAD"));
				obj.setTotalDamage(result.getString("TOTAL_DAMAGE"));
				obj.setTotalTaken(result.getString("TOTAL_TAKEN"));
				obj.setNumCrits(result.getString("NUM_CRITS"));
				obj.setNumFumbles(result.getString("NUM_FUMBLES"));
				obj.setNumDoubleDamage(result.getString("NUM_DOUBLE_DAMAGE"));
				obj.setNumAttacks(result.getString("NUM_ATTACKS"));
				obj.setNumHits(result.getString("NUM_HITS"));
				obj.setDate(result.getString("BATTLE_DATE"));
				obj.setParty(result.getString("PARTY"));
				obj.setPlayerName(result.getString("NAME"));

				PlayerKillsDAO pkdb = new PlayerKillsDAO();
				PlayerKills pk = new PlayerKills();
				pk.setPlayerId(obj.getPlayerId());
				pk.setBattleId(obj.getBattleId());

				obj.setNumKills("" + (pkdb.selectPlayerKills(pk).size()));

				v.add(obj);
			}
		} catch (SQLException sqle) {
			logger.log("error", sqle.toString());
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
		return v;
	}

	public void deletePlayerBattles(PlayerBattles o) {
		String deleteString = "delete from PLAYER_BATTLES  ";
		deleteString += " WHERE ID = " + dbs.escapeQuotes(o.getId()) + " ";

		try {
			dbs.open();
			dbs.executeSQLCommand(deleteString);

		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();
		}
	}
}
