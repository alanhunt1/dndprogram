package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SkillChooser;
import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.ClassSkills;
import initcheck.database.ClassSkillsDAO;
import initcheck.database.Skill;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClassSkillPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList classSkillList = new JList();

	private JScrollPane advanceScroll = new JScrollPane(classSkillList);

	Vector<Skill> skillVector = new Vector<Skill>();

	CharClass cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private SkillChooser skillChooser = new SkillChooser();

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public ClassSkillPanel(CharClass cc) {
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			CharClassDAO ccdb = new CharClassDAO();
			skillVector = ccdb.getClassSkills(cc);
			classSkillList.setListData(skillVector);
		}

	}

	public void setCharClass(CharClass cc){
		this.cc = cc;
		if (cc.getId() != null) {
			CharClassDAO ccdb = new CharClassDAO();
			skillVector = ccdb.getClassSkills(cc);
			classSkillList.setListData(skillVector);
		}
	}
	
	private void init() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		classSkillList.setVisibleRowCount(5);
		int ypos = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(advanceScroll, 0, ypos);
		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		JPanel buttons = new JPanel();
		buttons.add(skillChooser);
		buttons.add(addButton);
		buttons.add(remButton);

		doLayout(buttons, 0, ypos);
		ypos++;

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAdvance();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remAdvance();
			}
		});

	}

	private void addAdvance() {
		int idx = classSkillList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}
		Skill f = (Skill) skillChooser.getSelectedItem();

		ClassSkills newClass = new ClassSkills();
		newClass.setClassId(cc.getId());
		newClass.setSkillId(f.getId());

		ClassSkillsDAO cadb = new ClassSkillsDAO();
		cadb.addClassSkills(newClass);

		CharClassDAO ccdb = new CharClassDAO();
		skillVector = ccdb.getClassSkills(cc);
		classSkillList.setListData(skillVector);
		classSkillList.setSelectedIndex(idx);
	}

	private void remAdvance() {
		int idx = classSkillList.getSelectedIndex();
		Skill f = (Skill) classSkillList.getSelectedValue();
		ClassSkillsDAO cadb = new ClassSkillsDAO();
		cadb.deleteClassSkills(f, cc.getId());

		CharClassDAO ccdb = new CharClassDAO();
		skillVector = ccdb.getClassSkills(cc);

		classSkillList.setListData(skillVector);
		classSkillList.setSelectedIndex(idx - 1);
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		add(comp);
	}

}
