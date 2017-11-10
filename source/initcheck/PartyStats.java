package initcheck;

import initcheck.database.SpellLevel;

import java.util.Vector;

public class PartyStats {

	private int maxCure = 0;
	private int totalCures = 0;
	private int maxHp = 0;
	private int avgHp = 0;
	private int minTotalCure = 0;
	
	
	public int getMinTotalCure() {
		return minTotalCure;
	}

	public void setMinTotalCure(int minTotalCure) {
		this.minTotalCure = minTotalCure;
	}

	public PartyStats(){
		
	}
	
	public PartyStats(Vector<DCharacter> players){
		calcStats(players);
	}

	public void calcStats(Vector<DCharacter> players){
		
		maxCure = 0;
		totalCures = 0;
		maxHp = 0;
		avgHp = 0;
		
		for (int i = 0; i < players.size(); i++){
			DCharacter player = (DCharacter)players.get(i);
			int hp =  Integer.parseInt(player.getHpCalc().getDisplayValue());
			avgHp += hp;
			if (hp > maxHp){
				maxHp = hp;
			}
		}
		
		for (int i = 0; i < players.size(); i++){
			DCharacter player = (DCharacter)players.get(i);
			
			// check for lay hands
			if (player.canLayHands()){
				int hands = calcSpells(player.getLayHands().getDisplayValue());
				totalCures += hands;
				if (hands > maxCure){
					maxCure = hands;
				}
			}
			
			// then check healing spells.
			Vector<SpellLevel> spells = player.getDivineSpellsPerDay();
			for (int j = 0; j < spells.size(); j++){
				SpellLevel sl = spells.get(j);
				int classLevel = player.getClassLevel(sl.getClassName());
				// cure minor
				if (calcSpells(sl.getLevel0()) > 0){
					totalCures += calcSpells(sl.getLevel0());
					minTotalCure += calcSpells(sl.getLevel0());
					if (1 > maxCure){
						maxCure = 1;
					}
				}
				// cure light
				if (calcSpells(sl.getLevel1()) > 0){
					
					totalCures += 8*calcSpells(sl.getLevel1())+classLevel;
					minTotalCure += 1*calcSpells(sl.getLevel1())+classLevel;
					
					if (8+classLevel > maxCure){
						maxCure = 8+classLevel;
					}
				}
				// cure moderate
				if (calcSpells(sl.getLevel2()) > 0){
					totalCures += 16*calcSpells(sl.getLevel2())+classLevel;
					minTotalCure += 2*calcSpells(sl.getLevel2())+classLevel;
					
					if (16+classLevel > maxCure){
						maxCure = 16+classLevel;
					}
				}
				// cure serious
				if (calcSpells(sl.getLevel3()) > 0){
					totalCures += 24*calcSpells(sl.getLevel3())+classLevel;
					minTotalCure += 3*calcSpells(sl.getLevel2())+classLevel;
					if (24+classLevel > maxCure){
						maxCure = 24+classLevel;
					}
				}
				// cure critical
				if (calcSpells(sl.getLevel4()) > 0){
					totalCures += 32*calcSpells(sl.getLevel4())+classLevel;
					minTotalCure += 4*calcSpells(sl.getLevel2())+classLevel;
					if (32+classLevel > maxCure){
						maxCure = 32+classLevel;
					}
				}
				// healing circle
				if (calcSpells(sl.getLevel5()) > 0){
					totalCures += (8*calcSpells(sl.getLevel5())+classLevel)*players.size();
					minTotalCure += (1*calcSpells(sl.getLevel5())+classLevel);
					if ((8+classLevel) > maxCure){
						maxCure = (8+classLevel);
					}
				}
				// heal
				if (calcSpells(sl.getLevel6()) > 0){
					totalCures += (avgHp*calcSpells(sl.getLevel6()));
					minTotalCure += (avgHp*calcSpells(sl.getLevel6()));
					if ((maxHp) > maxCure){
						maxCure = maxHp;
					}
				}
				// mass heal
				if (calcSpells(sl.getLevel8()) > 0){
					totalCures += (avgHp*calcSpells(sl.getLevel8()))*players.size();
					minTotalCure += (avgHp*calcSpells(sl.getLevel8()));
					if ((maxHp) > maxCure){
						maxCure = maxHp;
					}
				}
			}
		}
	}
	
	private int calcSpells(String s){
		if (s.indexOf("(") < 0){
			return Integer.parseInt(s);
		}
		
		return Integer.parseInt(s.substring(0,s.indexOf("(")))+Integer.parseInt(s.substring(s.indexOf("(")+1, s.indexOf(")")));
	}
	
	public int getMaxCure() {
		return maxCure;
	}

	public void setMaxCure(int maxCure) {
		this.maxCure = maxCure;
	}

	public int getTotalCures() {
		return totalCures;
	}

	public void setTotalCures(int totalCures) {
		this.totalCures = totalCures;
	}

	
}
