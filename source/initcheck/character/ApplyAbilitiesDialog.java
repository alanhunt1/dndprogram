package initcheck.character;

import initcheck.PanelButton;
import initcheck.database.ClassAbilities;
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

public class ApplyAbilitiesDialog extends JFrame implements
		ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	private JList availableList = new JList();

	private JScrollPane availableScroll = new JScrollPane(availableList);

	private JList appliedList = new JList();

	private JScrollPane appliedScroll = new JScrollPane(appliedList);

	private PanelButton closeButton = new PanelButton("OK");

	private PlayerWeaponPanel owner;

	private String weaponId;

	private Vector<ClassAbilities> classAbilities = new Vector<ClassAbilities>();

	private Vector<ClassAbilities> origAbilities = new Vector<ClassAbilities>();

	public ApplyAbilitiesDialog(PlayerWeaponPanel owner,
			Vector<ClassAbilities> inclassAbilities, String weaponId) {

		this.owner = owner;
		this.weaponId = weaponId;
		this.origAbilities = inclassAbilities;
		this.classAbilities.addAll(origAbilities);

		WeaponViewsDAO db = new WeaponViewsDAO();
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		for (int i = 0; i < views.size(); i++) {
			WeaponViews wv = (WeaponViews) views.get(i);
			for (int j = 0; j < classAbilities.size(); j++) {
				ClassAbilities cas = (ClassAbilities) classAbilities.get(j);
				if (cas.getId().equals(wv.getFeatId())) {
					classAbilities.removeElementAt(j);
					break;
				}
			}
		}

		availableList.setListData(classAbilities);
		availableList.addListSelectionListener(this);
		availableList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					applyClassAbilities();
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
					removeClassAbilities();
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

	public void applyClassAbilities() {
		WeaponViewsDAO db = new WeaponViewsDAO();
		ClassAbilities f = (ClassAbilities) availableList.getSelectedValue();
		WeaponViews wv = new WeaponViews();

		wv.setFeatId(f.getId());
		wv.setFeatName(f.getName());
		wv.setPlayerWeaponId(weaponId);
		wv.setType("ABILITY");
		db.addWeaponViews(wv);
		owner.addWeaponFeat(wv);
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		for (int j = 0; j < classAbilities.size(); j++) {
			ClassAbilities cas = (ClassAbilities) classAbilities.get(j);
			if (cas.getId().equals(wv.getFeatId())) {
				classAbilities.removeElementAt(j);
				break;
			}
		}

		availableList.setListData(classAbilities);
	}

	public void removeClassAbilities() {
		WeaponViewsDAO db = new WeaponViewsDAO();
		WeaponViews wv = (WeaponViews) appliedList.getSelectedValue();

		db.deleteWeaponViews(wv);
		owner.removeWeaponFeat(wv);
		Vector<WeaponViews> views = db.getWeaponViews(weaponId);
		appliedList.setListData(views);

		classAbilities = new Vector<ClassAbilities>();
		classAbilities.addAll(origAbilities);

		for (int i = 0; i < views.size(); i++) {
			WeaponViews wv2 = (WeaponViews) views.get(i);
			for (int j = 0; j < classAbilities.size(); j++) {
				ClassAbilities cas = (ClassAbilities) classAbilities.get(j);
				if (cas.getId().equals(wv2.getFeatId())) {
					classAbilities.removeElementAt(j);
					break;
				}
			}
		}

		availableList.setListData(classAbilities);

	}

}
