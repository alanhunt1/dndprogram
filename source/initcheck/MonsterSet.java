package initcheck;

import initcheck.database.Monster;
import initcheck.utils.BigFraction;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class MonsterSet implements Serializable{
	static final long serialVersionUID = 1;
		private Random rnd = new Random();
		private Vector<Monster> monsters = null;
		private int group = 0;
		private Vector<MonsterGroup> monsterGroups;
		private InitLogger logger = new InitLogger(this);

		public MonsterSet(){
				monsters = new Vector<Monster>();
				monsterGroups = new Vector<MonsterGroup>();
		}

		public Vector<Participant> getCurrentGroup(){
				return getMonsterGroup(group-1);
		}

		public Vector<Participant> getMonsterGroup(int findGroup){
				Vector<Participant> mg = new Vector<Participant>();
				for (int i = 0; i < monsters.size(); i++){
						Monster m = (monsters.get(i));
						
						if (m.getGroup() == findGroup){
								mg.add(m);
						}
				}
				return mg;
		}
		
		public Vector<Monster> getPictures(){
				Vector<Monster> pictures = new Vector<Monster>();
				for (int i = 0; i < monsters.size(); i++){
						Monster m = (Monster)(monsters.get(i));
						String pic = m.getPicture();
						boolean add = true;
						for (int j = 0; j < pictures.size(); j++){
								if (((Monster)pictures.get(j)).getPicture().equals(pic)){
										add = false;
								}
						}
						if (add){
								pictures.add((Monster)m.clone());
						}
				}
				return pictures;
		}

		public void removeMonster(Monster m){
				monsters.remove(m);
		}

		public void clearMonsters(){
				monsters = new Vector<Monster>();
				group = 0;
				monsterGroups = new Vector<MonsterGroup>();
		}

		public void clearMonsters(int delGroup){
			
				logger.log("Removing MonsterGroup "+delGroup+" "+
													 monsterGroups.get(delGroup));
				monsterGroups.removeElementAt(delGroup);
				
				for (int i = 0; i < monsters.size(); i++){
						Monster m = (Monster)(monsters.get(i));
						logger.log("Processing Monster "+i+": "+m.getName()+"Group is "+m.getGroup());
						if (m.getGroup() == delGroup){
								logger.log("Removing monster");
								monsters.removeElementAt(i);
								i--;
						}else if (m.getGroup() > delGroup){
								logger.log("Decrementing monster group number");
								m.setGroup(m.getGroup()-1);
						}else{
								logger.log("Doing nothing");
						}
						
				}
				group--;
		}

		public Vector<MonsterGroup> getMonsterGroups(){
				return monsterGroups;
		}

		public void addMonster(Monster m){
				m.setGroup(group-1);
				monsters.add(m);
		}
		
		public int size(){
				return monsters.size();
		}

		public Vector<Monster> getMonsters(){
				return monsters;
		}

		public Monster getMonster(int i){
				return (Monster)monsters.get(i);
		}

		public void addMonsters(int numMonsters, Monster m){
				int startNum = checkForDups(m);
				String name = m.getName().trim();
				if (m.getName().equals("")){
						name = "MONSTER ";
				}
				monsterGroups.add(new MonsterGroup(m, numMonsters, startNum+1, startNum+numMonsters));
				//(m.getName()+(startNum+1)+" through "+(startNum+numMonsters));
				for (int j = 0; j < numMonsters; j++, startNum++){
				
						int hp = calcHitPoints(m.getHitDie());
						m.setHP(hp);
						m.setCurrentHitPoints(hp);
						m.setName(name+" "+(startNum+1));
						m.setBaseName(name);
						m.setGroup(group);
						monsters.add((Monster)m.clone());
				}
				group++;
		}

		public void createMonsters(int numMonsters, Monster m){
				
				String name = m.getName().trim();
				if (m.getName().equals("")){
						name = "MONSTER ";
				}
				monsters = new Vector<Monster>(10, 10);
				group = 0;
				monsterGroups.add(new MonsterGroup(m, numMonsters, 1, numMonsters));
				//monsterGroups.add(m.getName()+" 1 through "+numMonsters);
				for (int j = 0; j < numMonsters; j++){
						int hp = calcHitPoints(m.getHitDie());
						m.setHP(hp);
						m.setCurrentHitPoints(hp);
						m.setName(name+" "+(j+1));
						m.setBaseName(name);
						m.setGroup(group);
						monsters.add((Monster)m.clone());
				}
				group++;
		}
	
		public int checkForDups(Monster m){
				int numDups = 0;
				String origName = m.getName().trim();
				for (int j = 0; j < monsters.size(); j++){
						Monster dm = (Monster)monsters.get(j);
						String name = dm.getName().trim();
						int end = name.lastIndexOf(" ");
						name = name.substring(0, end);
						if (name.equals(origName)){
								numDups++;
						}
				}
				return numDups;
		}
		public int getRandom(int i){
				int j = rnd.nextInt(i);
				if (j == 0){
						return 1;
				}
				return j;
				
		}

		public int calcHitPoints(String s){
				// the hit die field should be in the form of 
				// XDY+Z, where X is the number of hit dice, Y is the
				// type of hit die, and Z is the hitpoint bonus.
				s = s.trim();
				
				logger.log("Parsing hd string"+s);
				int hdIndex = 0;
				if (s.indexOf('d') > 0){
					hdIndex = s.indexOf('d');
				}else{
					if (s.indexOf('D') > 0){
						hdIndex = s.indexOf('D');
					}else{
						// if there is no d in the string, take it to be just a straight number
						// of hitpoints - no calc required.
						return Integer.parseInt(s);
					}
				}
				
				
				
				int plusIndex = s.indexOf('+');
				int minusIndex = s.indexOf('-');
			
				// check for fractional X
				String numString = s.substring(0, hdIndex);
				numString = numString.trim();
				
				int hd = 0;
				int denom = 0;
				if (numString.indexOf("/") != -1){
						BigFraction fraction = new BigFraction(numString);
						denom = fraction.denominator().intValue();
						hd = fraction.numerator().intValue();
				}else{
						hd = Integer.parseInt(s.substring(0, hdIndex).trim());
				}
				int dieType = 0;
				int bonus = 0;
				int minus = 0;
				
				if (plusIndex > 0){
						dieType = Integer.parseInt(s.substring(hdIndex+1, plusIndex).trim());
						bonus = Integer.parseInt(s.substring(plusIndex+1,s.length()).trim()); 
				}else if (minusIndex > 0){
						dieType = Integer.parseInt(s.substring(hdIndex+1, minusIndex).trim());
						minus  = Integer.parseInt(s.substring(minusIndex+1,s.length()).trim()); 
				}else{
						dieType = Integer.parseInt(s.substring(hdIndex+1, s.length()).trim());
				}
				
				int hp = dieType;
				for (int i = 0; i < hd-1; i++){
						hp += getRandom(dieType);
				}
				if (denom != 0){
						hp /= denom;
				}
				hp += bonus;
				hp -= minus;
				
				return hp;
		}
		
}
