package initcheck;

import initcheck.graphics.TiledGridPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class DieRoller extends TiledGridPanel {
	
	static final long serialVersionUID = 1;
	
		private Vector<String> rollVector;
		private Random rnd = new Random();
		private JCheckBox autoRoll = new JCheckBox("Auto Roll");
		private JCheckBox sortasc = new JCheckBox("asc");
		private JCheckBox sort = new JCheckBox("dec");
		private PanelButton rollButton = new PanelButton("Roll");
		private PanelButton sumButton = new PanelButton("Sum");
		private JComboBox dieType = new JComboBox();
		private JTextField numDice = new JTextField(3);
		private JList rollResult = new JList();
		private JTextField dieMod = new JTextField(3);
		private InitLogger logger = new InitLogger(this);
		
		public DieRoller(){
				super(InitImage.lightRocks);
				dieType.setEditable(true);
				dieType.addItem("20");
				dieType.addItem("12");
				dieType.addItem("10");
				dieType.addItem("8");
				dieType.addItem("6");
				dieType.addItem("4");
				dieType.setPreferredSize(new Dimension(10, 20));
				numDice.setText("1");
				dieMod.setText("0");
				
				JScrollPane scrollPane = new JScrollPane(rollResult);
				scrollPane.setPreferredSize(new Dimension(50, 50));
				autoRoll.setOpaque(false);
				sortasc.setOpaque(false);
				sort.setOpaque(false);
					
				doLayout(autoRoll, 0, ypos);
				doLayout(sort, 1, ypos);
				doLayout(sortasc, 2, ypos);

				incYPos();
				
				JLabel typeLabel = new JLabel("Die Type");
				//typeLabel.setForeground(Color.white);
				doLayout(typeLabel, 0, ypos);				
				doLayout(dieType, 1, ypos);
				
				doLayout(scrollPane, 2, ypos, 1, 3);
				
				incYPos();
				
				JLabel numLabel = new JLabel("Num Dice");
				//numLabel.setForeground(Color.white);
				doLayout(numLabel, 0, ypos);
				doLayout(numDice, 1, ypos);
				
				incYPos();
				
				JLabel modLabel = new JLabel("Die Mod");
				//modLabel.setForeground(Color.white);
				doLayout(modLabel, 0, ypos);
				doLayout(dieMod, 1, ypos);

				incYPos();
				
				doLayout(rollButton, 0, ypos, 2, 1);
				doLayout(sumButton, 2, ypos);
				
				rollButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										rollDice(false);
								}
						});
				
				sumButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										rollDice(true);
								}
						});
			
				setBorder(BorderFactory.createEtchedBorder());
				
		}
		
		public void autoRoll(){
				if (autoRoll.isSelected()){
						rollDice(false);
				}
		}

		private void rollDice(boolean sum){
				rollVector = new Vector<String>();
				int dt = Integer.parseInt((String)dieType.getSelectedItem());
				int nd = Integer.parseInt(numDice.getText());
				int mod = Integer.parseInt(dieMod.getText());
				
				int total = 0;
				for (int i = 0; i < nd; i++){
						int roll = getRandom(dt);
						if (!sum){
								if (sort.isSelected()){
										insertRoll(roll, mod, true);
								}else if (sortasc.isSelected()){
										insertRoll(roll, mod, false);
								}else{
									String special = "";
									if (roll == 20){
										special = "CRT";
									}else if (roll == 1){
										special = "FUM";
									}
									rollVector.add(""+(roll+mod)+special);
								}
						}
						total += roll;
				}
				if (sum){
						rollVector.add(""+(total+mod));
				}
				rollResult.setListData(rollVector);
		}
		
		private void insertRoll(int roll, int mod, boolean desc){
				logger.log("inserting roll");
				int idx = 0;
				while (idx < rollVector.size()){
						if (desc){
								if (roll+mod >= Integer.parseInt((String)rollVector.get(idx))){
										rollVector.add(idx,""+(roll+mod));
										return;
								}
						}else{
								if (roll+mod <= Integer.parseInt((String)rollVector.get(idx))){
										rollVector.add(idx,""+(roll+mod));
										return;
								}
						}
						idx++;
				}
				String special = "";
				if (roll == 20){
					special = "CRT";
				}else if (roll == 1){
					special = "FUM";
				}
				rollVector.add(""+(roll+mod)+special);
		}

		public int getRandom(int i){
				int j = rnd.nextInt(i);
				return j+1;
		}
}
