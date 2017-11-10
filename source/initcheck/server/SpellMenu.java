package initcheck.server;

import initcheck.InitServer;
import initcheck.status.StatAsleep;
import initcheck.status.StatBlind;
import initcheck.status.StatHasted;
import initcheck.status.StatSlowed;
import initcheck.status.StatStunned;
import initcheck.status.StatUnconscious;
import initcheck.status.StatusItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SpellMenu extends JMenu implements ActionListener {

	private static final long serialVersionUID = 1L;

	private InitServer owner;

	private JMenuItem cSpray;

	private JMenuItem haste;

	private JMenuItem slow;

	private JMenuItem sleep;

	private JMenuItem blind;

	private JMenuItem unconscious;

	private JMenuItem custom;

	public SpellMenu(InitServer owner) {
		super("Spells");
		this.owner = owner;

		cSpray = new JMenuItem("Color Spray");
		cSpray.addActionListener(this);
		this.add(cSpray);

		haste = new JMenuItem("Haste");
		haste.addActionListener(this);
		this.add(haste);

		slow = new JMenuItem("Slow");
		slow.addActionListener(this);
		this.add(slow);

		sleep = new JMenuItem("Sleep");
		sleep.addActionListener(this);
		this.add(sleep);

		blind = new JMenuItem("Blind");
		blind.addActionListener(this);
		this.add(blind);

		unconscious = new JMenuItem("Unconscious");
		unconscious.addActionListener(this);
		this.add(unconscious);

		custom = new JMenuItem("Custom");
		custom.addActionListener(this);
		this.add(custom);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cSpray) {
			markListItemColorSprayed();
		} else if (e.getSource() == haste) {
			markListItemHasted();
		} else if (e.getSource() == slow) {
			markListItemSlowed();
		} else if (e.getSource() == sleep) {
			markListItemAsleep();
		} else if (e.getSource() == blind) {
			markListItemBlind();
		} else if (e.getSource() == unconscious) {
			markListItemUnconscious();
		} else if (e.getSource() == custom) {
			getCustomSpell();
		}
	}

	public void getCustomSpell() {
		String name = getSpellName();
		int dur = getDuration(name);
		if (dur > 0) {
			StatusItem stat = new StatusItem();
			stat.setName(name);
			stat.setStatusMod(dur);
			owner.getList().setStatus(stat);
		}
	}

	public void markListItemHasted() {
		int dur = getDuration("Haste");
		if (dur > 0) {
			StatHasted stat = new StatHasted(dur);
			owner.getList().setStatus(stat);
		}
	}

	public void markListItemSlowed() {
		int dur = getDuration("Slow");
		if (dur > 0) {
			StatSlowed stat = new StatSlowed(dur);
			owner.getList().setStatus(stat);
		}
	}

	public void markListItemAsleep() {
		int dur = getDuration("Sleep");
		if (dur > 0) {
			StatAsleep stat = new StatAsleep(dur);
			owner.getList().setStatus(stat);
			owner.sendSound("asleep");
		}
	}

	public void markListItemBlind() {
		int dur = getDuration("Blind");
		if (dur > 0) {
			StatBlind stat = new StatBlind(dur);
			owner.getList().setStatus(stat);
		}
	}

	public void markListItemUnconscious() {
		int dur = getDuration("Unconscious");
		if (dur > 0) {
			StatUnconscious stat = new StatUnconscious(dur);
			owner.getList().setStatus(stat);
		}
	}

	public void markListItemColorSprayed() {

		int u = getDuration("Unconscious");
		if (u > 0) {
			StatUnconscious statu = new StatUnconscious(u, true);
			owner.getList().setStatus(statu);
		}
		int b = getDuration("Blind");
		if (b > 0) {
			StatBlind statb = new StatBlind(b, true);
			owner.getList().setStatus(statb);
		}
		StatStunned stats = new StatStunned(1, true);
		owner.getList().setStatus(stats);
	}

	private int getDuration(String s) {
		String inputValue = JOptionPane
				.showInputDialog("Enter Spell Duration for " + s);
		return Integer.parseInt(inputValue);

	}

	private String getSpellName() {
		String inputValue = JOptionPane.showInputDialog("Enter Spell Name ");
		return inputValue;

	}

}
