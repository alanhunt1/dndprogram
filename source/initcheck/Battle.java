package initcheck;

import initcheck.database.BattleHistoryDAO;
import initcheck.database.DamageRecord;
import initcheck.database.MonsterKills;
import initcheck.database.PlayerBattles;

import java.io.Serializable;
import java.util.Vector;

public class Battle implements Serializable {

	static final long serialVersionUID = 1;

	DamageRecord dr;

	Vector<BattleRound> attacks = new Vector<BattleRound>();

	String party;

	String battleId;

	Vector<MonsterKills> monsterKills = new Vector<MonsterKills>();

	public Battle(String party) {
		this.party = party;
		BattleHistoryDAO db = new BattleHistoryDAO();
		battleId = "" + db.getBattleId();
	}

	public Vector<BattleRound> getRoundInfo(DCharacter dc){
		Vector<BattleRound> v = new Vector<BattleRound>();
		for (int i = 0; i < attacks.size(); i++){
			BattleRound br = attacks.get(i);
			if (br.getPlayerId().equals(""+dc.getID())){
				v.add(br);
			}
		}		
		return v;
	}
	
	public void addAttack(BattleRound br) {
		attacks.add(br);
	}

	public void addMonsterKill(MonsterKills mk) {
		monsterKills.add(mk);
	}

	public PlayerBattles getBattleStats(String playerId) {
		PlayerBattles pb = new PlayerBattles();

		pb.setPlayerId(playerId);

		int numStuns = 0;
		int roundsStunned = 0;
		int maxDamage = 0;
		int maxRoundDamage = 0;
		int roundsDead = 0;
		int totalDamage = 0;
		int totalTaken = 0;
		int numCrits = 0;
		int numFumbles = 0;
		int numDoubleDamage = 0;
		double numHits = 0;
		double numAttacks = 0;

		for (int i = 0; i < attacks.size(); i++) {

			BattleRound br = (BattleRound) attacks.get(i);
			if (br.getPlayerId().equals(playerId)) {

				Status status = br.getStatus();
				if (status != null) {
					if (status.isDead()) {
						roundsDead++;
					}
					if (status.isStunned()) {
						roundsStunned++;
					}
				}

				totalDamage += br.getDamage();
				if (br.getDamage() > maxRoundDamage) {
					maxRoundDamage = br.getDamage();
				}
				if (br.getMaxDamage() > maxDamage) {
					maxDamage = br.getMaxDamage();
				}

				numStuns += br.getStuns();
				numCrits += br.getCrits();
				numFumbles += br.getFumbles();
				numDoubleDamage += br.getDoubleDamages();

				numHits += br.getNumHits();
				numAttacks += br.getNumAttacks();

			}
		}

		pb.setNumStuns("" + numStuns);
		pb.setRoundsStunned("" + roundsStunned);
		pb.setMaxDamage("" + maxDamage);
		pb.setMaxRoundDamage("" + maxRoundDamage);
		pb.setRoundsDead("" + roundsDead);
		pb.setTotalDamage("" + totalDamage);
		pb.setTotalTaken("" + totalTaken);
		pb.setNumCrits("" + numCrits);
		pb.setNumFumbles("" + numFumbles);
		pb.setNumDoubleDamage("" + numDoubleDamage);
		pb.setNumAttacks("" + numAttacks);
		pb.setNumHits("" + numHits);

		return pb;
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
	 * Get the MonsterKills value.
	 * 
	 * @return the MonsterKills value.
	 */
	public Vector<MonsterKills> getMonsterKills() {
		return monsterKills;
	}

	/**
	 * Set the MonsterKills value.
	 * 
	 * @param newMonsterKills
	 *            The new MonsterKills value.
	 */
	public void setMonsterKills(Vector<MonsterKills> newMonsterKills) {
		this.monsterKills = newMonsterKills;
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

	public void writeToFile(int i) {

	}

}
