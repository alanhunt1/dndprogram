package initcheck.displaycomponents;

import initcheck.MonsterDisplayPanel;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.database.Monster;
import initcheck.database.MonsterAttacks;
import initcheck.database.MonsterAttacksDAO;
import initcheck.graphics.TiledGridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MonsterAttackListPanel extends TiledGridPanel implements
		ListSelectionListener {

	// hit fields

	private static final long serialVersionUID = 1L;

	private JList attackList = new JList();

	private PanelButton addAttack = new PanelButton("add");

	private PanelButton saveAttack = new PanelButton("save");

	private PanelButton remAttack = new PanelButton("del");

	private PanelButton copyAttack = new PanelButton("copy");

	private JTextField numAttacks = new JTextField(3);

	private JTextField attackName = new JTextField(20);

	private JTextField toHit = new JTextField(5);

	private JTextField damage = new JTextField(5);

	private JCheckBox poison = new JCheckBox("Poison");

	private Monster m;

	private int mode;
	
	public MonsterAttackListPanel() {
		super("images/rockLighter.jpg");
		initValues();
	}

	public MonsterAttackListPanel(Monster m, int mode) {
		super("images/rockLighter.jpg");
		this.mode = mode;
		initValues();
		if (m != null) {
			this.m = m;
			attackList.setListData(m.getAttackList());
			if (m.getAttackList().size() > 0) {
				attackList.setSelectedIndex(0);
				MonsterAttacks ma = (MonsterAttacks) (m.getAttackList().get(0));
				numAttacks.setText(ma.getNumberOfAttacks());
				attackName.setText(ma.getAttackType());
				toHit.setText(ma.getToHit());
				damage.setText(ma.getDamage());
				poison.setSelected(ma.isPoison());
			}
		}
	}

	public void setMonster(Monster m) {
		if (m != null) {
			this.m = m;
			attackList.setListData(m.getAttackList());
			if (m.getAttackList().size() > 0) {
				attackList.setSelectedIndex(0);
				MonsterAttacks ma = (MonsterAttacks) (m.getAttackList().get(0));
				numAttacks.setText(ma.getNumberOfAttacks());
				attackName.setText(ma.getAttackType());
				toHit.setText(ma.getToHit());
				damage.setText(ma.getDamage());
				poison.setSelected(ma.isPoison());
			}
		}
	}

	private void initValues() {
		setOpaque(false);

		attackList.setVisibleRowCount(5);
		attackList.setCellRenderer(new GenericListCellRenderer());
		attackList.addListSelectionListener(this);

		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(attackList, 0, ypos);
		setWeightX(0);
		setWeightY(0);

		incYPos();

		JPanel fieldPanel = new JPanel();
		fieldPanel.setOpaque(false);
		fieldPanel.add(numAttacks);
		fieldPanel.add(attackName);
		fieldPanel.add(toHit);
		fieldPanel.add(damage);
		fieldPanel.add(poison);
		doLayout(fieldPanel, 0, ypos);

		incYPos();

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(addAttack);
		buttonPanel.add(saveAttack);
		buttonPanel.add(remAttack);
		buttonPanel.add(copyAttack);

		if (mode != MonsterDisplayPanel.DISPLAY){
			doLayout(buttonPanel, 0, ypos);
		}

		addAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAttack();
			}
		});

		remAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAttack();
			}
		});

		saveAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAttack();
			}
		});

		copyAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copyAttack();
			}
		});
	}

	public void copyAttack() {
		@SuppressWarnings("unused")
		CopyAttackDialog cad = new CopyAttackDialog(this, m);
	}

	public void addAttack() {
		MonsterAttacks ma = new MonsterAttacks();
		ma.setNumberOfAttacks(numAttacks.getText());
		ma.setAttackType(attackName.getText());
		ma.setToHit(toHit.getText());
		ma.setDamage(damage.getText());
		ma.setMonsterId("" + m.getID());
		ma.setPoison(poison.isSelected());
		MonsterAttacksDAO db = new MonsterAttacksDAO();
		db.addMonsterAttacks(ma);
		updateList();
	}

	public void removeAttack() {
		MonsterAttacks ma = (MonsterAttacks) attackList.getSelectedValue();
		MonsterAttacksDAO db = new MonsterAttacksDAO();
		db.deleteMonsterAttacks(ma);
		updateList();
	}

	public void saveAttack() {
		try{
		MonsterAttacks ma = (MonsterAttacks) attackList.getSelectedValue();
		if (ma != null){
			ma.setNumberOfAttacks(numAttacks.getText());
			ma.setAttackType(attackName.getText());
			ma.setToHit(toHit.getText());
			ma.setDamage(damage.getText());
			ma.setPoison(poison.isSelected());
			MonsterAttacksDAO db = new MonsterAttacksDAO();
			db.updateMonsterAttacks(ma);
			updateList();
		}
		}catch(Exception e){
			
		}
	}

	public void updateList() {
		MonsterAttacksDAO db = new MonsterAttacksDAO();
		MonsterAttacks ma = new MonsterAttacks();
		ma.setMonsterId("" + m.getID());
		Vector<MonsterAttacks> v = db.selectMonsterAttacks(ma);
		attackList.setListData(v);
		m.setAttackList(v);
	}

	public void valueChanged(ListSelectionEvent e) {

		try {
			MonsterAttacks ma = (MonsterAttacks) attackList.getSelectedValue();
			if (ma != null) {
				numAttacks.setText(ma.getNumberOfAttacks());
				attackName.setText(ma.getAttackType());
				toHit.setText(ma.getToHit());
				damage.setText(ma.getDamage());
				poison.setSelected(ma.isPoison());
				attackList.ensureIndexIsVisible(attackList.getSelectedIndex());
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

}
