package initcheck;

import initcheck.database.Monster;
import initcheck.database.MonsterDAO;
import initcheck.graphics.TiledDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateEncounterDialog extends TiledDialog{
		
	static final long serialVersionUID = 1;
	
		private PanelButton genButton;
		private PanelButton okButton;
		private PanelButton cancelButton;
		private JComboBox difficulty = new JComboBox();
		private JComboBox monsterCount = new JComboBox();
		private JComboBox monsterType = new JComboBox();
		private JTextField partyLevelStr = new JTextField(5);
		private InitServer owner;
		private Random rnd = new Random();
		
		JTextArea encounterLabel = new JTextArea(5, 50);
		JLabel encounterLabel2 = new JLabel();
		JLabel encounterLabel3 = new JLabel();
		JLabel encounterLabel4 = new JLabel();
		private int numMonsters = 0;
		private int monsterCt[] = null;
		private Monster monster[] = null;
		private JPanel main = new JPanel();
		
		// gridbag layout components
		private GridBagLayout gridbag = new GridBagLayout();
		private GridBagConstraints c = new GridBagConstraints();

		Vector<Monster> validMonsters;

		/**
		 * Get the ValidMonsters value.
		 * @return the ValidMonsters value.
		 */
		public Vector<Monster> getValidMonsters() {
				return validMonsters;
		}

		/**
		 * Set the ValidMonsters value.
		 * @param newValidMonsters The new ValidMonsters value.
		 */
		public void setValidMonsters(Vector<Monster> newValidMonsters) {
				this.validMonsters = newValidMonsters;
		}

		

		public CreateEncounterDialog(InitServer owner){
				super(owner.getFrame(), "Create Random Encounter", true);
				this.owner = owner;
				doInit();
		}
		
		public Vector<Monster> getMonsters(){
				Vector<Monster> v = new Vector<Monster>();
				if (monster == null) return v;
				for (int i = 0; i < monster.length; i++){
						v.add(monster[i]);
				}
				return v;
		}

		public Vector<String> getMonsterCounts(){
				Vector<String> v = new Vector<String>();
				if (monster == null) return v;
				for (int i = 0; i < monster.length; i++){
						v.add(""+monsterCt[i]);
				}
				return v;
		}

		public Vector<MonsterGroup> getMonsterGroups(){
				Vector<MonsterGroup> v = new Vector<MonsterGroup>();
				if (monster == null) return v;
				for (int i = 0; i < monster.length; i++){
						if (monster[i] != null){
								MonsterGroup mg = new MonsterGroup(monster[i],monsterCt[i]);
								v.add(mg);
						}
				}
				return v;
		}

		public CreateEncounterDialog(){
				
		}

		private void doInit(){

				// set the layout
				main.setLayout(gridbag);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.ipadx = 10;
				c.ipady = 10;

				JPanel buttonPanel = new JPanel();
				JPanel textPanel = new JPanel();
				textPanel.setBorder(BorderFactory.createEmptyBorder(
																				20, //top
                                        20, //left
                                        10, //bottom
                                        20) //right
                                        );
				JPanel innerPanel = new JPanel();
				innerPanel.add(encounterLabel);
				innerPanel.setBackground(Color.white);
				textPanel.add(innerPanel);
				

				genButton = new PanelButton("Ok");
				cancelButton = new PanelButton("Cancel");
				okButton = new PanelButton("Run Encounter", 100);
				
				buttonPanel.add(genButton);
				buttonPanel.add(cancelButton);
				buttonPanel.add(okButton);
				buttonPanel.setOpaque(false);

				// set up the difficulty levels.
				difficulty.addItem("Annoyance");
				difficulty.addItem("Minor Exertion");
				difficulty.addItem("A Bit Tricky");
				difficulty.addItem("Work Up A Sweat");
				difficulty.addItem("Medic!");
				difficulty.addItem("The DM is going to have to cheat.");
				
				// set up the relative number of creatures
				monsterCount.addItem("8,132 Orcs");
				monsterCount.addItem("Great Cleave, Here I Come!");
				monsterCount.addItem("1, 2, ... many");
				monsterCount.addItem("An elite band of baddies");
				monsterCount.addItem("Jesus, look at the size of that thing!");

				// set up the relative number of types
				monsterType.addItem("Homogenous as Milk, baby!");
				monsterType.addItem("Token Monster Included");
				monsterType.addItem("Who Got These Guys To Work Together?");
				monsterType.addItem("Is This The Freaking Cantina Scene?");
				
				doLayout(new JLabel("Difficulty"), 0, 0);
				doLayout(difficulty, 1, 0);
				doLayout(new JLabel("Relative Number Of Creatures"), 0, 2);
				doLayout(monsterCount, 1, 2);
				doLayout(new JLabel("Relative Number Of Monster Types"), 0, 3);
				doLayout(monsterType, 1, 3);
				doLayout(new JLabel("Average Party Level"), 0, 4);
				doLayout(partyLevelStr, 1, 4);
				doLayout(textPanel, 0, 5, 2, 1);
			

				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										dispose();
								}
						});

				genButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										generateEncounter();
								}
						});
				
				okButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										runEncounter();
								}
						});

				main.setBorder(BorderFactory.createEmptyBorder(
																											 20, //top
																											 20, //left
																											 10, //bottom
																											 20) //right
											 );
				setMainWindow(main);
				setButtonBar(buttonPanel);
				pack();
				setVisible(true);
		}
		
		private void doLayout(Component comp, int x, int y){
				c.gridx = x;
				c.gridy = y;
				c.gridwidth = 1;
				c.gridheight = 1;
				gridbag.setConstraints(comp, c);
				main.add(comp);
		}
		private void doLayout(Component comp, int x, int y, int width, int height){
				c.gridx = x;
				c.gridy = y;
				c.gridwidth = width;
				c.gridheight = height;
				
				gridbag.setConstraints(comp, c);
				main.add(comp);
		}
		
		private void runEncounter(){
				MonsterDialog md = new MonsterDialog(owner);
				Encounter e = new Encounter();
				e.setMonsterGroups(getMonsterGroups());
				md.setEncounter(e);
				md.pack();
				md.setVisible(true);
				setVisible(false);
		}
		
		public Vector<String>getRandomEncounter(double partyLevel, int partySize,
																		 int difficulty, int density, 
																		 int densityRange, 
																		 int diversity, int diversityRange){

				return generateEncounter(partyLevel, partySize, difficulty,
																 density,densityRange, diversity, 
																 diversityRange);

				
		}
		
		private void generateEncounter(){
				double partyLevel = Double.parseDouble(partyLevelStr.getText());
				int partySize = owner.getEngine().getCharacters().size();
				int selectedDifficulty = difficulty.getSelectedIndex();
				int density = monsterCount.getSelectedIndex();
				int numLevels = monsterCount.getItemCount();
				int hom = monsterType.getSelectedIndex();
				int numHom = monsterType.getItemCount();
				Vector<String> monsterLabels = generateEncounter(partyLevel, partySize,
																								 selectedDifficulty,
																								 density,numLevels,
																								 hom, numHom);

				for (int i = 0; i < monsterLabels.size(); i++){
						encounterLabel.setText(encounterLabel.getText()+(String)monsterLabels.get(i));
				}
		}
		
		private Vector<String> generateEncounter(double partyLevel, int partySize,
																		 int selectedDifficulty, int density,
																		 int numLevels, int hom,
			int numHom){

				Vector<String> monsters = new Vector<String>();
				double base = 1.414;
				
				int partyInt = new Double(partyLevel).intValue();
				double el = partyLevel;
			
				int partyMod = partySize / 4 + 1;
				
				
				// calculate the desired Encounter Level
				

				switch(selectedDifficulty){
						case 0:
								el = partyLevel;
								break;
						case 1:
								el = partyLevel + 1;
								break;
						case 2:
								el = partyLevel + 2;
								break;
						case 3:
								el = partyLevel + 3;
								break;
						case 4:
								el = partyLevel + 4;
								break;
						case 5:
								el = partyLevel + 5;
								break;
				} 
				
				// find out the possible range of monsters
		
				double maxArray[] = new double[partyInt+6];
		
				for (int i = 1; i <= el; i++){
						double maxMonsters = ((Math.pow(base,el))/(Math.pow(base,i)))*partyMod;
						maxArray[i] = maxMonsters;
				}
			
				// find out the density of the monsters
			

				double range = new Double((density+1)).doubleValue()/new Double(numLevels).doubleValue();
				
		
				
				int index = new Double(el*range).intValue();
				if (index > (maxArray.length-1)){
						index = maxArray.length-1;
				}
				numMonsters = new Double(maxArray[index]).intValue();

				// find out the homogeneity of the monsters
			
				@SuppressWarnings("unused") double homRange = new Double((hom+1)).doubleValue()/new Double(numHom).doubleValue();
				int numTypes = hom+1;//new Double(numMonsters*homRange).intValue();
				monster = new Monster[numTypes];
				monsterCt = new int[numTypes];
				encounterLabel.setText("");
				for (int i = 0; i < numTypes; i++){
						MonsterDAO db = new MonsterDAO();
						Vector<Monster> v = new Vector<Monster>();
						if (validMonsters != null){
								v = getMonsters(index);
						}else{
								v = db.getMonsters(null, ""+index, null);
						}
						
						

						if (v.size() > 0){
								
								Monster m = v.get(getRandom(v.size()));
								if (m == null){
										
								}else{
									monster[i] = m;
									int ct = numMonsters/numTypes;
									if (ct == 0){
										ct++;
									}
									monsterCt[i] = ct;
						
									String text = ""+monsterCt[i]+" "+m.getName()+
										"(CR "+m.getChallengeRating()+")\n";
									monsters.add(text);
								}
						}else{
								
						}
				}
				return monsters;
		}
		
		private Vector<Monster> getMonsters(int idx){
				Vector<Monster> v = new Vector<Monster>();
				for (int i = 0; i < validMonsters.size(); i++){
						Monster m = (Monster)validMonsters.get(i);
						if (new Double(Double.parseDouble(m.getChallengeRating())).intValue() == idx ||
								(idx == 0 && Double.parseDouble(m.getChallengeRating()) < 1.0)){
								v.add((Monster)m.clone());
						}
				}
				return v;
		}

		public int getRandom(int i){
				int j = rnd.nextInt(i);
				return j;
				
		}
		
}
