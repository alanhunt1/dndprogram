package initcheck.server;

import initcheck.DCharacter;
import initcheck.InitServer;
import initcheck.Participant;
import initcheck.database.Monster;
import initcheck.database.MonsterAttacks;
import initcheck.database.Weapon;

import java.util.Random;
import java.util.Vector;

public class BattleSimulator {

	private Random rnd = new Random();

	private InitServer owner;

	private Vector<DCharacter> chars;

	private Vector<Participant> monsters;

	public BattleSimulator(InitServer owner) {
		this.owner = owner;
	}

	public void simulateBattle() {
		chars = owner.getEngine().getCharacters();
		monsters = owner.getEngine().getMonsters();

		// assign a random person for each monster to attack
		owner.assignRandomOpponent();

		// then go until one side is dead.
		while (liveSides(monsters, chars)) {

			Participant p = owner.getList().getCurrentParticipant();

			// handle monster attacks
			if (p.getPType().equalsIgnoreCase("MONSTER")) {
				Monster m = (Monster) p;

				String s = owner.getHitSheet().getOpponent(p.getName());
				DCharacter dc = null;

				// pull the character from the list
				for (int j = 0; j < chars.size(); j++) {
					dc = (DCharacter) chars.get(j);

					if (dc.getName() != null && dc.getName().equals(s)) {
						break;
					}
				}

				// if their opponent is dead, get another
				while (dc.isDead()) {
					dc = (DCharacter) chars.get(getRandom(chars.size()) - 1);

					// set the player as an opponent in the hit sheet
					if (!dc.isDead()) {
						owner.getHitSheet().setOpponent(dc.getName(),
								m.getName());
					}
				}

				if (m.getAttackList() != null) {

					DamageRoller dr = new DamageRoller();
					int damage = 0;
					for (int j = 0; j < m.getAttackList().size(); j++) {
						MonsterAttacks attack = (MonsterAttacks) m
								.getAttackList().get(j);

						for (int k = 0; k < Integer.parseInt(attack
								.getNumberOfAttacks()); k++) {
							damage += dr.getDamage(attack.getToHit(), ""
									+ dc.getAC(), attack.getDamage())[0];
							if (damage > 0) {
								dc.setCurrentHitPoints(dc.getCurrentHitPoints()
										- damage);
								dc.setDamageTaken(dc.getDamageTaken() + damage);
								owner.updateBattleProgress(m.getName()
										+ " Did " + damage + " Damage to "
										+ dc.getName() + " ["
										+ dc.getCurrentHitPoints() + "/"
										+ dc.getHP() + "]");

								// check for poison in the attack
								if (attack.isPoison()) {
									doPoisonSave(m, dc);
								}
							}

							// if the monster kills the player with attacks
							// left, mark damage and switch to another player.
							if (damage > dc.getCurrentHitPoints()) {
								while (dc.isDead()
										&& liveSides(monsters, chars)) {
									dc = (DCharacter) chars.get(getRandom(chars
											.size()) - 1);

									// set the player as an opponent in the hit
									// sheet
									if (!dc.isDead()) {
										owner.getHitSheet().setOpponent(
												dc.getName(), m.getName());
									}
								}
								damage = 0;
							}
						}
					}

					if (damage > 0) {
						dc.setCurrentHitPoints(dc.getCurrentHitPoints()
								- damage);
						dc.setDamageTaken(dc.getDamageTaken() + damage);
						owner.updateBattleProgress(m.getName() + " Did "
								+ damage + " Damage to " + dc.getName() + " ["
								+ dc.getCurrentHitPoints() + "/" + dc.getHP()
								+ "]");
					} else {
						owner.updateBattleProgress(m.getName() + " missed "
								+ dc.getName() + " ["
								+ dc.getCurrentHitPoints() + "/" + dc.getHP()
								+ "]");
					}

					// if the monster manages to stun the player, mark them
					int stun = dc.getHP() / 5;
					if (dc.getHP() % 5 != 0) {
						stun++;
					}

					if (damage > stun && new Double( dc.getLevel()).intValue() > 4
							&& (dc.getCurrentHitPoints() - damage > 0)) {
						owner.stunPlayer(dc.getName(), false);
						owner.updateBattleProgress(dc.getName()
								+ " Is stunned.");
					}

					// if they manage to kill the player, mark that too.
					if (dc.getCurrentHitPoints() < 1) {
						owner.killMonster(dc.getName());
					}

				} else {
					
				}
			}
			// handle players attacks
			else {
				int dmg = 0;

				// get the current player character
				DCharacter dc = (DCharacter) p;
				Monster m = null;
				DamageRoller dr = new DamageRoller();

				// see if they are assignet to a particular monster
				String s = owner.getHitSheet().getPlayerOpponent(p.getName());

				// if not, pick one at random
				while (s.equals("")) {
					m = (Monster) monsters.get(getRandom(monsters.size()) - 1);
					if (!m.isDead()) {
						s = m.getName();
						// set the player as an opponent in the hit sheet
						owner.getHitSheet().setOpponent(dc.getName(),
								m.getName());
					}

				}

				if (s != "") {
					for (int j = 0; j < monsters.size(); j++) {
						m = (Monster) monsters.get(j);
						if (m.getName() != null && m.getName().equals(s)) {
							break;
						}
					}
					while (m.isDead()) {
						m = (Monster) monsters
								.get(getRandom(monsters.size()) - 1);
						if (!m.isDead()) {
							s = m.getName();
							owner.getHitSheet().setOpponent(dc.getName(),
									m.getName());
						}
					}
				}

				// now calculate the damage done to the opponent
				Weapon w = (Weapon) dc.getWeapons().get(0);
				String damage = dc.calcDamage(w).getDisplayValue();
				String attacks = "";
				if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
					attacks = dc.calcMeleeToHit(w).getDisplayValue();
				} else {
					attacks = dc.calcRangedToHit(w).getDisplayValue();
				}

				int[] dmgArray = dr.getDamage(attacks, "" + m.getAC(), damage,
						w.getCritMult());

				for (int i = 0; i < dmgArray.length && dmg > -1
						&& liveSides(monsters, chars); i++) {
					dmg = dmgArray[i];
					if (dmg > 0) {
						m = applyDamage(m, dc, w, dmg);
					} else {
						owner.updateBattleProgress(p.getName() + " missed "
								+ m.getName() + " [" + m.getCurrentHitPoints()
								+ "/" + m.getHP() + "]");
					}
					owner.getCritStatPanel().setAttacks(
							owner.getCritStatPanel().getAttacks() + 1);
				}

				// if they player is a two weapon fighter, also calculate the
				// off hand attacks.
				if (w.getUse().equals("Primary")
						|| w.getUse().equals("Double Toss 1")
						|| w.getUse().equals("Natural Weapon 1")) {

					w = (Weapon) dc.getWeapons().get(1);
					damage = dc.calcDamage(w).getDisplayValue();
					attacks = "";
					if (w.getRangedmelee().equalsIgnoreCase("MELEE")) {
						attacks = dc.calcMeleeToHit(w).getDisplayValue();
					} else {
						attacks = dc.calcRangedToHit(w).getDisplayValue();
					}

					dmgArray = dr.getDamage(attacks, "" + m.getAC(), damage, w
							.getCritMult());

					for (int i = 0; i < dmgArray.length && dmg > -1
							&& liveSides(monsters, chars); i++) {
						dmg = dmgArray[i];
						if (dmg > 0) {
							m = applyDamage(m, dc, w, dmg);
						} else {
							owner.updateBattleProgress(p.getName() + " missed "
									+ m.getName() + " [" + m.getHP() + "/"
									+ m.getHP() + "]");
						}
						owner.getCritStatPanel().setAttacks(
								owner.getCritStatPanel().getAttacks() + 1);
					}
				}

			}

			owner.getList().advanceList();
		}
	}

	private Monster applyDamage(Monster m, DCharacter dc, Weapon w, int dmg) {
		int drAmount = 0;

		// check for damage reduction
		if (m.getDamageReduction() != null
				&& !m.getDamageReduction().equals("")) {
			drAmount = Integer.parseInt(m.getDamageReduction());
			String drType = m.getDrType();
			// if the damage reduction is based on an
			// enhancement bonus, check the weapon for
			// the appropriate bonus
			if (drType.trim().indexOf("+") == 0) {
				if (!w.getBonus().trim().equals("")) {
					if (Integer.parseInt(w.getBonus()) >= Integer.parseInt(m
							.getDrType().substring(1, m.getDrType().length()))) {
						drAmount = 0;
					}
				}
			} else {
				if (w.getMaterial().getMaterialName().equalsIgnoreCase(drType)) {
					drAmount = 0;
				}
			}
		}

		// subtract out the dr, min of zero
		dmg -= drAmount;
		if (dmg < 0) {
			dmg = 0;
		}

		// m.setCurrentHitPoints(m.getCurrentHitPoints() - dmg);
		owner.getHitSheet().addDamage(dc.getName(), m.getName(), dmg);
		owner.updateBattleProgress(dc.getName() + " Did " + dmg + " Damage to "
				+ m.getName() + " [" + m.getCurrentHitPoints() + "/"
				+ m.getHP() + "]" + drAmount);

		// if the shot killed the monster, switch to another
		if (m.isDead()) {

			while (m.isDead() && liveSides(monsters, chars)) {
				m = (Monster) monsters.get(getRandom(monsters.size()) - 1);
				if (!m.isDead()) {
					// s = m.getName();
					owner.getHitSheet().setOpponent(dc.getName(), m.getName());
				}
			}
			owner.updateBattleProgress("Monster is dead, switching to "
					+ m.getName());
		}

		return m;
	}

	private void doPoisonSave(Monster m, DCharacter dc) {
		int save = Integer.parseInt(dc.calcFortitudeSave().getDisplayValue());
		int poisonDc = Integer.parseInt(m.getPoisonSaveDc());
		int saveRoll = getRandom(20);
		if (saveRoll != 20 && save + saveRoll < poisonDc) {
			String poisonEffect = m.getPoisonInitialDie();
			int poisonPoints = 0;
			if (poisonEffect.indexOf("d") > 0) {
				String numDice = poisonEffect.substring(0, poisonEffect
						.indexOf("d"));
				String dieType = poisonEffect.substring(poisonEffect
						.indexOf("d") + 1, poisonEffect.length());
				for (int i = 0; i < Integer.parseInt(numDice); i++) {
					poisonPoints += getRandom(Integer.parseInt(dieType));
				}
			} else {
				poisonPoints = Integer.parseInt(poisonEffect);

			}

			if (m.getPoisonType().equalsIgnoreCase("Strength")) {
				dc.setStrength(dc.getStrength() - poisonPoints);
			} else if (m.getPoisonType().equalsIgnoreCase("Constitution")) {
				dc.setConstitution(dc.getConstitution() - poisonPoints);
			} else if (m.getPoisonType().equalsIgnoreCase("Dexterity")) {
				dc.setDexterity(dc.getDexterity() - poisonPoints);
			}

			owner.updateBattleProgress(m.getName() + " poisoned "
					+ dc.getName() + " for " + poisonPoints);
		} else {
			owner.updateBattleProgress(dc.getName()
					+ " saved against poison effects");
		}
	}

	private boolean liveSides(Vector<Participant> monsters, Vector<DCharacter> chars) {
		boolean liveMonster = false;
		boolean livePlayer = false;
		for (int i = 0; i < monsters.size(); i++) {
			Monster m = (Monster) monsters.get(i);
			if (!m.isDead()) {
				liveMonster = true;
			}
		}

		for (int i = 0; i < chars.size(); i++) {
			DCharacter m = (DCharacter) chars.get(i);
			if (!m.isDead()) {
				livePlayer = true;
			}
		}

		

		return liveMonster && livePlayer;
	}

	public int getRandom(int i) {
		int j = rnd.nextInt(i);
		return j + 1;
	}

}
