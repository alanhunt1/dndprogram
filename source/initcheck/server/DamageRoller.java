package initcheck.server;


import java.util.Random;
import java.util.StringTokenizer;


public class DamageRoller {
		
		private Random rnd = new Random();
		
		public int[] getDamage(String toHit, String opposingAc, String damage){
				return getDamage(toHit, opposingAc, damage, "2");
		}
		
		public int[] getDamage(String toHit, String opposingAc, String damage, 
													 String critMult){
				int[] dmgArray = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
				
				int dmg = 0;
				int x = 0;
				// split the toHit value into individual attacks, if there is more than one.
				StringTokenizer split = new StringTokenizer(toHit, "/");
				while (split.hasMoreTokens()){
						toHit = split.nextToken();
						
						if (toHit.startsWith("+")){
								toHit = toHit.substring(1, toHit.length());
						}
						
						
						int roll = getRandom(20);
						if ((roll+Integer.parseInt(toHit)) >= Integer.parseInt(opposingAc)){
								
								String base = "0";
								String bonus = "0";
								
								if (damage.indexOf("*") >= 0){
										dmgArray[0] = 0;
										return dmgArray;
								}
								
								
								// check for damage done by weapon abilities (flaming, shock, etc.)
								if (damage.indexOf("(") >= 0){
										
										String abDamage = damage.substring(damage.indexOf("(")+1, damage.indexOf(")"));
										
										
										damage = (damage.substring(0, damage.indexOf("(")))+(damage.substring(damage.indexOf(")")+1, damage.length()));
										
										String [] parts = abDamage.split(",");
										for (int i = 0; i < parts.length; i++){
												String token = parts[i];
												int numDice = 0;
												String die = "";
												if (token.indexOf("d") > 0){
														numDice = Integer.parseInt(token.substring(0, token.indexOf("d")));
												}
												die = damage.substring(token.indexOf("d")+1, token.length());
												int dieSize = Integer.parseInt(die);
												
												dmg += getRandom(dieSize) * numDice;
										}
								}
								
								
								if (damage.indexOf("+") > 0){
										base = damage.substring(0, damage.indexOf("+"));
										bonus = damage.substring(damage.indexOf("+")+1, damage.length());
								}else{
										base = damage;
								}
								
								
								String numDice = base.substring(0, base.indexOf("d"));
								String dieSize = base.substring(base.indexOf("d")+1, base.length());
								if (dieSize.indexOf("/") >= 0){
										dieSize = dieSize.substring(damage.indexOf("d")+1, damage.indexOf("/"));
								}
								
								int diceCount = Integer.parseInt(numDice);
								if (roll == 20){
										diceCount = diceCount*Integer.parseInt(critMult);
								}

								
								
								for (int i = 0; i < diceCount; i++){
										dmg += getRandom(Integer.parseInt(dieSize));
								}
								
								
								dmg += Integer.parseInt(bonus);
								
						}
						dmgArray[x++] = dmg;
						dmg = 0;
				}
				return dmgArray;
		}

		public int getRandom(int i){
				int j = rnd.nextInt(i);
				return j+1;
		}

}
		
