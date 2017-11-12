package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ArmorProfChooser;
import initcheck.character.chooser.ProficiencyTypeChooser;
import initcheck.character.chooser.ShieldProfChooser;
import initcheck.character.chooser.WeaponProfChooser;
import initcheck.database.CharClass;
import initcheck.database.ClassProficiency;
import initcheck.database.ClassProficiencyDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClassProficiencyPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private JList<ClassProficiency> classProficiencyList = new JList<ClassProficiency>();

	private JScrollPane proficiencyScroll = new JScrollPane(
			classProficiencyList);

	Vector<ClassProficiency> proficiencyVector = new Vector<ClassProficiency>();

	CharClass cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private ProficiencyTypeChooser proficiencyChooser = new ProficiencyTypeChooser();

	private JComboBox<?> subTypeChooser = new WeaponProfChooser();

	JPanel buttons = new JPanel();

	public ClassProficiencyPanel(CharClass cc) {
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			ClassProficiencyDAO ccdb = new ClassProficiencyDAO();
			proficiencyVector = ccdb.getClassProficiency(cc);
			classProficiencyList.setListData(proficiencyVector);
		}
	}

	public void setCharClass(CharClass cc){
		this.cc = cc;
		if (cc.getId() != null) {
			ClassProficiencyDAO ccdb = new ClassProficiencyDAO();
			proficiencyVector = ccdb.getClassProficiency(cc);
			classProficiencyList.setListData(proficiencyVector);
		}
	}
	
	private void init() {

		classProficiencyList.setVisibleRowCount(5);

		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(proficiencyScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		incYPos();

		buttons.add(proficiencyChooser);
		buttons.add(subTypeChooser);
		buttons.add(addButton);
		buttons.add(remButton);

		doLayout(buttons, 0, ypos);
		incYPos();

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProficiency();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remProficiency();
			}
		});

		proficiencyChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setProficiency();
			}
		});

	}

	private void setProficiency() {
		String profType = (String) proficiencyChooser.getSelectedItem();
		invalidate();
		buttons.remove(remButton);
		buttons.remove(addButton);
		buttons.remove(subTypeChooser);
		if (profType.equals("WEAPON")) {
			subTypeChooser = new WeaponProfChooser();
		} else if (profType.equals("ARMOR")) {
			subTypeChooser = new ArmorProfChooser();
		} else if (profType.equals("SHIELD")) {
			subTypeChooser = new ShieldProfChooser();
		}
		buttons.add(subTypeChooser);
		buttons.add(addButton);
		buttons.add(remButton);
		validate();
	}

	private void addProficiency() {
		int idx = classProficiencyList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}

		ClassProficiency newClass = new ClassProficiency();
		newClass.setClassId(cc.getId());
		newClass.setIdType("Class");
		newClass.setProfType((String) proficiencyChooser.getSelectedItem());
		newClass.setProfSubtype((String) subTypeChooser.getSelectedItem());
		ClassProficiencyDAO cadb = new ClassProficiencyDAO();
		cadb.addClassProficiency(newClass);

		ClassProficiencyDAO ccdb = new ClassProficiencyDAO();
		proficiencyVector = ccdb.getClassProficiency(cc);
		classProficiencyList.setListData(proficiencyVector);
		classProficiencyList.setSelectedIndex(idx);
	}

	private void remProficiency() {
		int idx = classProficiencyList.getSelectedIndex();
		ClassProficiency f = (ClassProficiency) classProficiencyList
				.getSelectedValue();
		ClassProficiencyDAO cadb = new ClassProficiencyDAO();
		cadb.deleteClassProficiency(f);
		proficiencyVector = cadb.getClassProficiency(cc);
		classProficiencyList.setListData(proficiencyVector);
		classProficiencyList.setSelectedIndex(idx - 1);
	}
}
