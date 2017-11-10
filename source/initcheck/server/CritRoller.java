package initcheck.server;

import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.database.Critical;
import initcheck.database.CriticalDAO;
import initcheck.database.Fumble;
import initcheck.database.FumbleDAO;
import initcheck.graphics.TiledGridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class CritRoller extends TiledGridPanel {
	static final long serialVersionUID = 1;

	private JComboBox critType = new JComboBox();
	private PanelButton roll = new PanelButton("Roll Crit");
	private Random rnd = new Random();

	public CritRoller(final InitServer owner, ImageIcon bg) {
		super(bg);
		setBorder(BorderFactory.createEmptyBorder());
		critType.addItem("Fumble");
		critType.addItem("Edged");
		critType.addItem("Blunt");
		critType.addItem("vs Animals");
		critType.addItem("Piercing");

		doLayout(critType, 0);
		doLayout(roll, 1);

		roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rollCrit();
			}
		});
	}

	public void rollCrit() {
		String type = (String) critType.getSelectedItem();
		String s = getCrit();
		if (type.equals("Fumble")) {
			CritDialog.showCritDialog("Fumble!!", s, this);
		} else {
			CritDialog.showCritDialog("Critical Hit!!", s, this);
		}

	}

	public String getCrit() {
		String type = (String) critType.getSelectedItem();
		int roll = getRandom(100);

		if (type.equals("Fumble")) {

			FumbleDAO db = new FumbleDAO();
			Fumble f = new Fumble();
			if (roll < 50) {
				f.setCategory("Frequent");
			} else if (roll > 50 && roll < 75) {
				f.setCategory("Infrequent");
			} else if (roll > 75 && roll < 90) {
				f.setCategory("Rare");
			} else if (roll > 90 && roll < 98) {
				f.setCategory("Very Rare");
			} else {
				f.setCategory("One In A Million");
			}

			Vector<Fumble> effects = db.selectFumble(f);
			f = (Fumble) effects.get(getRandom(effects.size()) - 1);

			return f.getDescription();
		} else {

			CriticalDAO db = new CriticalDAO();

			Critical c = new Critical();
			if (roll < 50) {
				c.setCategory("Frequent");
			} else if (roll > 50 && roll < 75) {
				c.setCategory("Infrequent");
			} else if (roll > 75 && roll < 90) {
				c.setCategory("Rare");
			} else if (roll > 90 && roll < 98) {
				c.setCategory("Very Rare");
			} else {
				c.setCategory("One In A Million");
			}

			c.setType(type);

			Vector<Critical> effects = db.selectCritical(c);
			c = (Critical) effects.get(getRandom(effects.size()) - 1);

			return c.getDescription();
		}
	}

	public void shutDialog() {

	}

	private int getRandom(int i) {
		int j = rnd.nextInt(i);
		if (j == 0) {
			return 1;
		}
		return j + 1;
	}

}
