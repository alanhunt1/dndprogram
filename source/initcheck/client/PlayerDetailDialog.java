package initcheck.client;

import initcheck.CureRoller;
import initcheck.DCharacter;
import initcheck.HealLabel;
import initcheck.PanelButton;
import initcheck.database.SpellLevel;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PlayerDetailDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	PlayerDamagePanel owner;

	DCharacter dc;

	TiledPanel contents = new TiledPanel();

	PanelButton okButton = new PanelButton("OK");

	JPanel buttonPanel = new JPanel();

	TiledGridPanel spellPanel = new TiledGridPanel("images/rockLighter.jpg");

	JPanel minorPanel = new JPanel();

	private ImageIcon backgroundImage = new ImageIcon("images/rock043.jpg");

	public PlayerDetailDialog(PlayerDamagePanel owner, DCharacter dc, int xpos,
			int ypos) {
		this.owner = owner;
		this.dc = dc;

		Vector<SpellLevel> v = dc.getDivineSpellsPerDay();

		int minor = 0;
		int light = 0;
		int moderate = 0;
		int serious = 0;
		int critical = 0;

		for (int i = 0; i < v.size(); i++) {
			SpellLevel sl = v.get(i);

			String lev0 = sl.getLevel0();
			String lev1 = sl.getLevel1();
			String lev2 = sl.getLevel2();
			String lev3 = sl.getLevel3();
			String lev4 = sl.getLevel4();

			minor += Integer.parseInt(lev0.substring(0, lev0.indexOf("(")));
			minor += Integer.parseInt(lev0.substring(lev0.indexOf("(") + 1,
					lev0.indexOf(")")));

			light += Integer.parseInt(lev1.substring(0, lev1.indexOf("(")));
			light += Integer.parseInt(lev1.substring(lev1.indexOf("(") + 1,
					lev1.indexOf(")")));

			moderate += Integer.parseInt(lev2.substring(0, lev2.indexOf("(")));
			moderate += Integer.parseInt(lev2.substring(lev2.indexOf("(") + 1,
					lev2.indexOf(")")));

			serious += Integer.parseInt(lev3.substring(0, lev3.indexOf("(")));
			serious += Integer.parseInt(lev3.substring(lev3.indexOf("(") + 1,
					lev3.indexOf(")")));

			critical += Integer.parseInt(lev4.substring(0, lev4.indexOf("(")));
			critical += Integer.parseInt(lev4.substring(lev4.indexOf("(") + 1,
					lev4.indexOf(")")));

		}

		minorPanel.setOpaque(false);
		for (int i = 0; i < minor; i++) {
			if (i < dc.getDivineSpellsUsed(0)) {
				minorPanel.add(new HealLabel(dc, 0, "m", true));
			} else {
				minorPanel.add(new HealLabel(dc, 0, "m", false));
			}
		}

		JPanel lightPanel = new JPanel();
		lightPanel.setOpaque(false);
		for (int i = 0; i < light; i++) {
			if (i < dc.getDivineSpellsUsed(1)) {
				lightPanel.add(new HealLabel(dc, 1, "L", true));
			} else {
				lightPanel.add(new HealLabel(dc, 1, "L", false));
			}
		}

		JPanel moderatePanel = new JPanel();
		moderatePanel.setOpaque(false);
		for (int i = 0; i < moderate; i++) {
			if (i < dc.getDivineSpellsUsed(2)) {
				moderatePanel.add(new HealLabel(dc, 2, "M", true));
			} else {
				moderatePanel.add(new HealLabel(dc, 2, "M", false));
			}
		}

		JPanel seriousPanel = new JPanel();
		seriousPanel.setOpaque(false);
		for (int i = 0; i < serious; i++) {
			if (i < dc.getDivineSpellsUsed(3)) {
				seriousPanel.add(new HealLabel(dc, 3, "S", true));
			} else {
				seriousPanel.add(new HealLabel(dc, 3, "S", false));
			}
		}

		JPanel criticalPanel = new JPanel();
		criticalPanel.setOpaque(false);
		for (int i = 0; i < critical; i++) {
			if (i < dc.getDivineSpellsUsed(3)) {
				criticalPanel.add(new HealLabel(dc, 4, "C", true));
			} else {
				criticalPanel.add(new HealLabel(dc, 4, "C", false));
			}
		}

		CureRoller cp = new CureRoller(backgroundImage);

		spellPanel.doLayout(minorPanel);
		spellPanel.incYPos();
		spellPanel.doLayout(lightPanel);
		spellPanel.incYPos();
		spellPanel.doLayout(moderatePanel);
		spellPanel.incYPos();
		spellPanel.doLayout(seriousPanel);
		spellPanel.incYPos();
		spellPanel.doLayout(criticalPanel);
		spellPanel.incYPos();
		spellPanel.doLayout(cp);

		buttonPanel.setOpaque(false);
		buttonPanel.add(okButton);

		contents.setLayout(new BorderLayout());
		contents.add(spellPanel, BorderLayout.CENTER);
		contents.add(buttonPanel, BorderLayout.SOUTH);

		getContentPane().add(contents);
		setLocation(xpos, ypos);
		pack();

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
