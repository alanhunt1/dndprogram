package initcheck.server;

import initcheck.DCharacter;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.character.chooser.WeaponClassChooser;
import initcheck.character.chooser.WeaponUseChooser;
import initcheck.database.DamageRecord;
import initcheck.graphics.TiledPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConfirmHighScoreDialog extends JDialog {

	
	private static final long serialVersionUID = 1L;

	InitServer owner = null;

	TiledPanel contents = new TiledPanel();

	JTextField number = new JTextField(15);

	PanelButton add = new PanelButton("Confirm", false);

	PanelButton cancel = new PanelButton("Cancel");
	
	JTextField playerChooser;

	WeaponClassChooser wcChooser = new WeaponClassChooser();

	WeaponUseChooser wuChooser = new WeaponUseChooser();

	DCharacter player;

	String battleId;

	DamageRecord dr = null;
	
	public ConfirmHighScoreDialog(InitServer owner, DCharacter player,
			String battle) {
		super(owner.getFrame(), "Confirm Damage Record");

		this.owner = owner;
		this.player = player;
		battleId = battle;
		init();
		pack();

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitRecord();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setVisible(true);
	}

	
	public void setDamageRecord(DamageRecord dr){
		this.dr = dr;
		wcChooser.setSelectedItem(dr.getWeaponType());
		wuChooser.setSelectedItem(dr.getWeaponUse());
		number.setText(dr.getDamage());
	}
	
	private void init() {

		playerChooser = new JTextField(player.getName());

		TiledPanel addSub = new TiledPanel();
		addSub.setLayout(new GridLayout(5, 1));
		
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);
		buttons.add(add);
		buttons.add(cancel);
		
		addSub.add(playerChooser);
		addSub.add(wcChooser);
		addSub.add(wuChooser);
		addSub.add(number);
		addSub.add(buttons);
		
		contents.add(addSub);

		setLocationRelativeTo(null);
		getContentPane().add(contents);
	}

	private void submitRecord() {
		DCharacter dc = player;
		if (dr == null){
			dr = new DamageRecord();
		}
		dr.setPartyName("Alltime");
		dr.setPlayerId("" + dc.getID());
		dr.setDamage(number.getText());
		dr.setOneshot(true);
		dr.setWeaponType((String) wcChooser.getSelectedItem());
		dr.setBattleId(battleId);
		dr.setWeaponUse((String) wuChooser.getSelectedItem());
		dr.setPlayerName(dc.getName());
		owner.setDamageRecord(dr);
		dispose();
	}

}
