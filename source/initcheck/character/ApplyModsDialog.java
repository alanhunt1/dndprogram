package initcheck.character;

import initcheck.PanelButton;
import initcheck.database.PlayerTempMod;
import initcheck.database.WeaponViews;
import initcheck.database.WeaponViewsDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ApplyModsDialog extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private JList availableList = new JList();

	private JScrollPane availableScroll = new JScrollPane(availableList);

	private JList appliedList = new JList();

	private JScrollPane appliedScroll = new JScrollPane(appliedList);

	private PanelButton closeButton = new PanelButton("OK");

	private PlayerWeaponPanel owner;

	private String weaponId;

	private TempModSet mods = new TempModSet();

	private TempModSet origMods = new TempModSet();

	public ApplyModsDialog(PlayerWeaponPanel owner,
			TempModSet inmods, String weaponId) {

		this.owner = owner;
		this.weaponId = weaponId;
		this.origMods = inmods;
		this.mods.addAll(origMods);

		WeaponViewsDAO db = new WeaponViewsDAO();
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		for (int i = 0; i < views.size(); i++) {
			WeaponViews wv = (WeaponViews) views.get(i);
			for (int j = 0; j < mods.size(); j++) {
				PlayerTempMod feat = (PlayerTempMod) mods.get(j);
				if (feat.getId().equals(wv.getFeatId())) {
					mods.removeElementAt(j);
					break;
				}
			}
		}

		availableList.setListData(mods);
		availableList.addListSelectionListener(this);
		availableList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					applyFeat();
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

		appliedList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					removeFeat();
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

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAndClose();
			}
		});

		GridPanel p = new GridPanel();
		p.setWeightX(0.5);
		p.setWeightY(1.0);
		p.doLayout(availableScroll, 0);
		p.doLayout(appliedScroll, 1);
		p.setWeightX(0);
		p.setWeightY(0);
		p.incYPos();
		p.doLayout(closeButton, 0, p.ypos, 2, 1);
		getContentPane().add(p);

		pack();
		setVisible(true);
	}

	public void updateAndClose() {
		owner.updateCalcs();
		dispose();
	}

	public void valueChanged(ListSelectionEvent e) {
		availableList.ensureIndexIsVisible(availableList.getSelectedIndex());
	}

	public void applyFeat() {
		WeaponViewsDAO db = new WeaponViewsDAO();
		PlayerTempMod f = (PlayerTempMod) availableList.getSelectedValue();
		WeaponViews wv = new WeaponViews();

		wv.setFeatId(f.getId());
		wv.setFeatName(f.getName());
		wv.setPlayerWeaponId(weaponId);
		wv.setType("ITEM");
		db.addWeaponViews(wv);
		owner.addWeaponFeat(wv);
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		for (int j = 0; j < mods.size(); j++) {
			PlayerTempMod feat = (PlayerTempMod) mods.get(j);
			if (feat.getId().equals(wv.getFeatId())) {
				mods.removeElementAt(j);
				break;
			}
		}

		availableList.setListData(mods);
	}

	public void removeFeat() {
		WeaponViewsDAO db = new WeaponViewsDAO();
		WeaponViews wv = (WeaponViews) appliedList.getSelectedValue();

		db.deleteWeaponViews(wv);
		owner.removeWeaponFeat(wv);
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		mods = new TempModSet();
		mods.addAll(origMods);

		for (int i = 0; i < views.size(); i++) {
			WeaponViews wv2 = (WeaponViews) views.get(i);
			for (int j = 0; j < mods.size(); j++) {
				PlayerTempMod feat = (PlayerTempMod) mods.get(j);
				if (feat.getId().equals(wv2.getFeatId())) {
					mods.removeElementAt(j);
					break;
				}
			}
		}

		availableList.setListData(mods);

	}

}
