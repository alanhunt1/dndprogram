package initcheck.character.library;

import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.ClassAbilityChooser;
import initcheck.database.CharClass;
import initcheck.database.ClassAbilities;
import initcheck.database.ClassAbilitiesDAO;
import initcheck.database.ClassAbilityXref;
import initcheck.database.ClassAbilityXrefDAO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClassAbilityPanel extends GridPanel implements ListSelectionListener {

	private InitLogger logger = new InitLogger(this);
	
	private static final long serialVersionUID = 1L;

	private JList classAbilityList = new JList();

	private JScrollPane abilityScroll = new JScrollPane(classAbilityList);

	Vector<ClassAbilities> abilityVector = new Vector<ClassAbilities>();

	CharClass cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private JTextField level = new JTextField(10);

	private ClassAbilityChooser abilityChooser = new ClassAbilityChooser();

	JPanel buttons = new JPanel();

	public ClassAbilityPanel(CharClass cc) {
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			ClassAbilitiesDAO ccdb = new ClassAbilitiesDAO();
			abilityVector = ccdb.getClassAbilities(cc);
			classAbilityList.setListData(abilityVector);
		}
	}

	public void setCharClass(CharClass cc){
		this.cc = cc;
		if (cc.getId() != null) {
			ClassAbilitiesDAO ccdb = new ClassAbilitiesDAO();
			abilityVector = ccdb.getClassAbilities(cc);
			classAbilityList.setListData(abilityVector);
		}
	}
	
	private void init() {

		classAbilityList.setVisibleRowCount(5);
		classAbilityList.addListSelectionListener(this);
		
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(abilityScroll, 0, ypos);
		setWeightX(0);
		setWeightY(0);
		incYPos();

		buttons.add(abilityChooser);
		buttons.add(level);
		buttons.add(addButton);
		buttons.add(remButton);

		doLayout(buttons, 0, ypos);
		incYPos();

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbility();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remAbility();
			}
		});

		abilityChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAbility();
			}
		});
		
		

	}

	private void setAbility() {

	}

	private void addAbility() {
		int idx = classAbilityList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}

		ClassAbilityXrefDAO caxdb = new ClassAbilityXrefDAO();
		ClassAbilityXref cax = new ClassAbilityXref();
		cax.setClassId(cc.getId());
		cax.setClassAbilityId(((ClassAbilities) abilityChooser
				.getSelectedItem()).getId());
		cax.setClassLevel(level.getText());

		caxdb.addClassAbilityXref(cax);

		ClassAbilitiesDAO ccdb = new ClassAbilitiesDAO();
		abilityVector = ccdb.getClassAbilities(cc);
		classAbilityList.setListData(abilityVector);
		classAbilityList.setSelectedIndex(idx);
	}

	private void remAbility() {
		int idx = classAbilityList.getSelectedIndex();
		ClassAbilitiesDAO cadb = new ClassAbilitiesDAO();

		// ClassAbilityXrefDAO caxdb = new ClassAbilityXrefDAO();
		// ClassAbilityXref cax = new ClassAbilityXref();

		// cadb.deleteClassAbility(f);

		abilityVector = cadb.getClassAbilities(cc);
		classAbilityList.setListData(abilityVector);
		classAbilityList.setSelectedIndex(idx - 1);
	}
	
	public void valueChanged(ListSelectionEvent e) {
		try {
			ClassAbilities ca = (ClassAbilities)classAbilityList.getSelectedValue();
			abilityChooser.setSelectedAbility(ca);
			ClassAbilityXrefDAO caxdb = new ClassAbilityXrefDAO();
			ClassAbilityXref cax = new ClassAbilityXref();
			cax.setClassId(cc.getId());
			cax.setClassAbilityId(ca.getId());
			cax = caxdb.selectClassAbilityXref(cax).get(0);
			level.setText(cax.getClassLevel());
		
		} catch (Exception ex) {
			logger.log("error",ex.toString());
		}
	
	}
	
}
