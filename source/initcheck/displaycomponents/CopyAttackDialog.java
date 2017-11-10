package initcheck.displaycomponents;

import initcheck.database.Monster;
import initcheck.database.MonsterAttacks;
import initcheck.database.MonsterAttacksDAO;
import initcheck.database.MonsterDAO;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CopyAttackDialog extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private JList monsterList = new JList();

	private JScrollPane listScroll = new JScrollPane(monsterList);

	private MonsterAttackListPanel owner;

	private Monster m;

	public CopyAttackDialog(MonsterAttackListPanel owner, Monster m) {
		this.m = m;
		this.owner = owner;
		MonsterDAO db = new MonsterDAO();
		monsterList.setListData(db.getMonsters());

		monsterList.addListSelectionListener(this);

		monsterList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setItem();
					dispose();
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});
		TiledPanel p = new TiledPanel();
		p.setLayout(new BorderLayout());
		p.add(listScroll, BorderLayout.CENTER);

		getContentPane().add(p);

		pack();
		setVisible(true);
	}

	public void valueChanged(ListSelectionEvent e) {
		monsterList.ensureIndexIsVisible(monsterList.getSelectedIndex());

	}

	public void setItem() {
		Monster mCopy = (Monster) monsterList.getSelectedValue();
		MonsterAttacksDAO madb = new MonsterAttacksDAO();

		MonsterAttacks ma = new MonsterAttacks();
		ma.setMonsterId("" + mCopy.getID());
		Vector<MonsterAttacks> v = madb.selectMonsterAttacks(ma);

		for (int i = 0; i < v.size(); i++) {
			ma = (MonsterAttacks) v.get(i);
			ma.setMonsterId("" + m.getID());
			ma.setId(null);
			madb.addMonsterAttacks(ma);
		}
		owner.updateList();

	}

}
