package initcheck.server;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.character.chooser.WeaponClassChooser;
import initcheck.character.chooser.WeaponUseChooser;
import initcheck.database.DamageRecord;
import initcheck.graphics.TiledPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class HighScoreInputDialog extends JDialog {
	static final long serialVersionUID = 1;
	
		HighScoreDialog owner = null;
		TiledPanel contents = new TiledPanel();
		
		JTextField number = new JTextField(15);
		
		PanelButton add = new PanelButton("Add", false);
		
		
		JComboBox<DCharacter> playerChooser;
		WeaponClassChooser wcChooser = new WeaponClassChooser();
		WeaponUseChooser wuChooser = new WeaponUseChooser();
		Vector<DCharacter> chars;
		String battleId;

		

		public HighScoreInputDialog(HighScoreDialog owner, Vector<DCharacter> chars, String battle){
				super();
				
				this.owner = owner;
				this.chars = chars;
				battleId = battle;
				init();
				pack();
			
				
				add.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										submitRecord();
								}
						});	
				setVisible(true);			
		}

		public void setDamageRecord(DamageRecord dr){
				wcChooser.setSelectedItem(dr.getWeaponType());
				wuChooser.setSelectedItem(dr.getWeaponUse());
				number.setText(dr.getDamage());
				for (int i = 0; i < chars.size(); i++){
						DCharacter dc = (DCharacter)chars.get(i);
						if (dc.getName().equals(dr.getPlayerName())){
								playerChooser.setSelectedItem(dc);
								break;
						} 
				}
		}

		private void init(){
						
				playerChooser = new JComboBox<DCharacter>(chars);
				
				TiledPanel addSub = new TiledPanel("images/rockLighter.jpg");
				addSub.setLayout(new GridLayout(1, 2));
				addSub.add(add);
				
				contents.add(playerChooser);
				contents.add(wcChooser);
				contents.add(wuChooser);
				contents.add(number);
				contents.add(addSub);
				
				setLocationRelativeTo(null);
				getContentPane().add(contents);
		}

		private void submitRecord(){
				DCharacter dc = (DCharacter)playerChooser.getSelectedItem();
				
				DamageRecord dr = new DamageRecord();
				dr.setPartyName("Alltime");
				dr.setPlayerId(""+dc.getID());
				dr.setDamage(number.getText());
				dr.setOneshot(true);
				dr.setWeaponType((String)wcChooser.getSelectedItem());
				dr.setBattleId(battleId);
				dr.setWeaponUse((String)wuChooser.getSelectedItem());
				dr.setPlayerName(dc.getName());
				owner.addDamageRecord(dr);
				dispose();
		}
		
		

}
