package initcheck.character.itembuilder;

import initcheck.database.Armor;
import initcheck.database.Weapon;
import initcheck.graphics.TestTabUI;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ItemBuilderDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	BuildMundaneArmorPanel map = new BuildMundaneArmorPanel();

	BuildMundaneWeaponPanel mwp = new BuildMundaneWeaponPanel();

	BuildArmorPanel ap = new BuildArmorPanel();

	BuildShieldPanel sp = new BuildShieldPanel();

	BuildWeaponPanel wp = new BuildWeaponPanel();

	BuildWonderousPanel wnp = new BuildWonderousPanel();

	BuildPotionPanel pp = new BuildPotionPanel();

	BuildWandPanel wap = new BuildWandPanel();

	BuildRingPanel rp = new BuildRingPanel();

	BuildRodPanel rop = new BuildRodPanel();

	BuildStaffPanel stp = new BuildStaffPanel();

	JTabbedPane tabs = new JTabbedPane();

	public ItemBuilderDialog(Weapon w) {
		
		wp = new BuildWeaponPanel(w);
		init();
		
		tabs.setSelectedIndex(2);
	}

	public ItemBuilderDialog(Armor w) {
		if (w.getType().equals("ARMOR")) {
			ap = new BuildArmorPanel(w);
			init();
			tabs.setSelectedIndex(0);
		} else {
			sp = new BuildShieldPanel(w);
			init();
			tabs.setSelectedIndex(1);
		}

	}

	public ItemBuilderDialog() {
		init();
	}

	public void init() {
		setTitle("Item Builder");
		
		//tabs.setUI(new TestTabUI());
		tabs.setOpaque(false);
		
		map.init();
		tabs.add(map, "Craft Armor");

		mwp.init();
		tabs.add(mwp, "Craft Weapon");

		ap.init();
		tabs.add(ap, "Armor");

		sp.init();
		tabs.add(sp, "Shield");

		wp.init();
		tabs.add(wp, "Weapon");

		wnp.init();
		tabs.add(wnp, "Wonderous Item");

		pp.init();
		tabs.add(pp, "Potion");

		wap.init();
		tabs.add(wap, "Wand");

		rp.init();
		tabs.add(rp, "Ring");

		rop.init();
		tabs.add(rop, "Rod");

		stp.init();
		tabs.add(stp, "Staff");

		getContentPane().add(tabs);

		pack();
		setVisible(true);
	}

}
