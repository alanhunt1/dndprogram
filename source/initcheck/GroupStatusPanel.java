package initcheck;

import initcheck.status.StatusItem;

import java.awt.Font;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GroupStatusPanel extends JPanel {
	
	static final long serialVersionUID = 1;
	
		// the hit mods
		private AttackMod partyMod = new AttackMod();
		private AttackMod monsterMod = new AttackMod();

		// the party/monster group spells
		private Status partyStatus = new Status();
		private Status monsterStatus = new Status();

		private JLabel partyModCounter;
		private JLabel monsterModCounter;

		private JLabel groupStats = new JLabel("Party : ");
		private JLabel monsterStats = new JLabel("Monster : ");
		
		private InitServer owner;
		private InitLogger logger = new InitLogger(this);

		public GroupStatusPanel (final InitServer owner){
				this.owner = owner;

				partyModCounter = new JLabel("MONSTERS : "+monsterMod);
				partyModCounter.setFont(new Font("Arial", Font.BOLD, 16));	
				partyModCounter.setForeground(InitColor.lightRed);
				monsterModCounter = new JLabel("PARTY : "+partyMod);
				monsterModCounter.setFont(new Font("Arial", Font.BOLD, 16));	
				groupStats.setForeground(InitColor.lightRed);
			  monsterStats.setForeground(InitColor.lightRed);
				monsterModCounter.setForeground(InitColor.lightRed);
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				add(partyModCounter);
				add(monsterModCounter);

				JPanel statPanel = new JPanel();				
				statPanel.add(groupStats);
				statPanel.add(monsterStats);
				statPanel.setOpaque(false);
				
				add(statPanel);
				setOpaque(false);
		}

		public void resetStatus(){
				partyMod = new AttackMod();
				monsterMod = new AttackMod();
				partyModCounter.setText("PARTY :"+partyMod);
				monsterModCounter.setText("MONSTERS :"+monsterMod);
		}
		
		public void advanceStatus(){
				monsterStatus.advanceStatus();
				partyStatus.advanceStatus();
				updateGroupLabels();
				
				if (monsterStatus.isSpellEnding()){
						owner.spellAlert(0);
						Vector<StatusItem> endSpells = monsterStatus.getEndSpells();
						for (int i = 0; i < endSpells.size(); i++){
								StatusItem si = (StatusItem)endSpells.get(i);
								if (si.getAttackMod() != 0){
										monsterMod.setAttackMod(monsterMod.getAttackMod()-
																						si.getAttackMod());
								} 
								if (si.getDamageMod() != 0){
										monsterMod.setDamageMod(monsterMod.getDamageMod()-
																						si.getDamageMod());
								}
								if (si.getSaveMod() != 0){
										monsterMod.setSaveMod(monsterMod.getSaveMod()-
																					si.getSaveMod());
								}
								if (si.getAttackModOpp() != 0){
										partyMod.setAttackMod(partyMod.getAttackMod()-
																					si.getAttackModOpp());
								} 
								if (si.getDamageModOpp() != 0){
										partyMod.setDamageMod(partyMod.getDamageMod()-
																					si.getDamageModOpp());
								}
								if (si.getSaveModOpp() != 0){
										partyMod.setSaveMod(partyMod.getSaveMod()-
																				si.getSaveModOpp());
								}
								
						}
				}
				if (partyStatus.isSpellEnding()){
						owner.spellAlert(1);
						Vector<StatusItem> endSpells = partyStatus.getEndSpells();
						
						for (int i = 0; i < endSpells.size(); i++){
								StatusItem si = (StatusItem)endSpells.get(i);
								if (si.getAttackMod() != 0){
										partyMod.setAttackMod(partyMod.getAttackMod()-
																					si.getAttackMod());
								} 
								if (si.getDamageMod() != 0){
										partyMod.setDamageMod(partyMod.getDamageMod()-
																					si.getDamageMod());
								}
								if (si.getSaveMod() != 0){
										partyMod.setSaveMod(partyMod.getSaveMod()-
																				si.getSaveMod());
								}
								if (si.getAttackModOpp() != 0){
										monsterMod.setAttackMod(monsterMod.getAttackMod()-
																						si.getAttackModOpp());
								} 
								if (si.getDamageModOpp() != 0){
										monsterMod.setDamageMod(monsterMod.getDamageMod()-
																						si.getDamageModOpp());
								}
								if (si.getSaveModOpp() != 0){
										monsterMod.setSaveMod(monsterMod.getSaveMod()-
																					si.getSaveModOpp());
								}
						}
						
				}
				partyModCounter.setText("PARTY :"+partyMod);
				monsterModCounter.setText("MONSTERS :"+monsterMod);
		}

		public AttackMod getPartyMod(){
				return partyMod;
		}

		public AttackMod getMonsterMod(){
				return monsterMod;
		}

		public void clearGroupSpells(){
				String groupStr = "Party : ";
				String monsterStr = "Monster : ";
				partyStatus = new Status();
				monsterStatus = new Status();
				monsterStats.setText(monsterStr);
				groupStats.setText(groupStr);
		}

		private void updateGroupLabels(){
				String groupStr = "Party : ";
				String monsterStr = "Monster : ";
				
				for (int i = 0; i < partyStatus.getStatSize(); i++){
						StatusItem psi = partyStatus.getStatusItem(i);
						groupStr += psi.getName();
				}
				for (int j = 0; j < monsterStatus.getStatSize(); j++){
						StatusItem msi = monsterStatus.getStatusItem(j);
						monsterStr += msi.getName();
				}
				
				monsterStats.setText(monsterStr);
				groupStats.setText(groupStr);
		}

		public void addGroupSpell(StatusItem si, int affected){
				try{
			
						switch (affected){
								case 1:
										partyStatus.addStatusItem(si);
																			 
										if (si.getAttackMod() != 0){
												partyMod.setAttackMod(partyMod.getAttackMod()+
																							si.getAttackMod());
										} 
										if (si.getDamageMod() != 0){
												partyMod.setDamageMod(partyMod.getDamageMod()+
																							si.getDamageMod());
										}
										if (si.getSaveMod() != 0){
												partyMod.setSaveMod(partyMod.getSaveMod()+
																							si.getSaveMod());
										}
										if (si.getAttackModOpp() != 0){
												monsterMod.setAttackMod(monsterMod.getAttackMod()+
																								si.getAttackModOpp());
										} 
										if (si.getDamageModOpp() != 0){
												monsterMod.setDamageMod(monsterMod.getDamageMod()+
																								si.getDamageModOpp());
										}
										if (si.getSaveModOpp() != 0){
												monsterMod.setSaveMod(monsterMod.getSaveMod()+
																							si.getSaveModOpp());
										}
										partyModCounter.setText("PARTY :"+partyMod);
										monsterModCounter.setText("MONSTERS :"+monsterMod);
									
										break;
								case 2:
										monsterStatus.addStatusItem(si);
										if (si.getAttackMod() != 0){
												monsterMod.setAttackMod(monsterMod.getAttackMod()+
																								si.getAttackMod());
										} 
										if (si.getDamageMod() != 0){
												monsterMod.setDamageMod(monsterMod.getDamageMod()+
																								si.getDamageMod());
										}
										if (si.getSaveMod() != 0){
												monsterMod.setSaveMod(monsterMod.getSaveMod()+
																							si.getSaveMod());
										}
										if (si.getAttackModOpp() != 0){
												partyMod.setAttackMod(partyMod.getAttackMod()+
																							si.getAttackModOpp());
										} 
										if (si.getDamageModOpp() != 0){
												partyMod.setDamageMod(partyMod.getDamageMod()+
																							si.getDamageModOpp());
										}
										if (si.getSaveModOpp() != 0){
												partyMod.setSaveMod(partyMod.getSaveMod()+
																							si.getSaveModOpp());
										}
										partyModCounter.setText("PARTY :"+partyMod);
										monsterModCounter.setText("MONSTERS :"+monsterMod);
										
										break;
						}
				}catch (StatusException se){logger.log("Error adding stat");}
				updateGroupLabels();
		}
}
