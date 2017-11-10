package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.ClassChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.StatChooser;
import initcheck.database.CharClass;
import initcheck.database.Skill;
import initcheck.database.SkillDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;
import initcheck.utils.StrManip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateSkillsPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private SourceChooser source = new SourceChooser();

	private JTextField name = new JTextField(20);

	private TiledTextArea description = new TiledTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private StatChooser ability = new StatChooser();

	private TiledGridPanel main = new TiledGridPanel("images/rockLighter.jpg");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private TiledPanel buttonPanel = new TiledPanel(InitImage.lightRocks);

	private Skill skill;

	private ClassChooser cl1 = new ClassChooser();

	private ClassChooser cl2 = new ClassChooser();

	private ClassChooser cl3 = new ClassChooser();

	private ClassChooser cl4 = new ClassChooser();

	private ClassChooser cl5 = new ClassChooser();

	private JCheckBox trainedOnly = new JCheckBox("Trained Only");

	private JCheckBox armorCheck = new JCheckBox("Armor Check Penalty");

	private JTextField synBonus = new JTextField(5);

	private JTextField synRanks = new JTextField(5);

	private SkillChooser synValue = new SkillChooser();

	private JCheckBox synCheck = new JCheckBox();

	private JTextField synBonus2 = new JTextField(5);

	private JTextField synRanks2 = new JTextField(5);

	private SkillChooser synValue2 = new SkillChooser();

	private JCheckBox synCheck2 = new JCheckBox();

	SkillDAO db = new SkillDAO();

	LibraryPanel owner;

	public CreateSkillsPanel(LibraryPanel owner) {
		skill = new Skill();
		
		init();
		this.owner = owner;
	}

	public CreateSkillsPanel(Skill f, LibraryPanel owner) {

		this.owner = owner;
		this.skill = f;

		

		description.setText(f.getDescription());
		name.setText(f.getSkill());
		init();

		if (f.getClass1() != null) {
			cl1.setSelectedClass(f.getClass1());
		}
		if (f.getClass2() != null) {
			cl2.setSelectedClass(f.getClass2());
		}
		if (f.getClass3() != null) {
			cl3.setSelectedClass(f.getClass3());
		}
		if (f.getClass4() != null) {
			cl4.setSelectedClass(f.getClass4());
		}
		if (f.getClass5() != null) {
			cl5.setSelectedClass(f.getClass5());
		}

		if (f.getTrainedOnly().equals("Y")) {
			trainedOnly.setSelected(true);
		}
		if (f.getArmorCheck().equals("Y")) {
			armorCheck.setSelected(true);
		}
		ability.setSelectedItem(f.getAbility());

		if (!StrManip.isNullOrEmpty(f.getSynValue())
				&& !f.getSynValue().equals("0")) {
			synCheck.setSelected(true);
			synValue.setSelectedSkill(f.getSynValue());
			synBonus.setText(f.getSynBonus());
			synRanks.setText(f.getSynRanks());
		}

		if (!StrManip.isNullOrEmpty(f.getSynValue2())
				&& !f.getSynValue2().equals("0")) {
			synCheck2.setSelected(true);
			synValue2.setSelectedSkill(f.getSynValue2());
			synBonus2.setText(f.getSynBonus2());
			synRanks2.setText(f.getSynRanks2());
		}
		
		source.setSelectedItem(f.getSource());
	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setCaretPosition(0);

		JPanel typePanel = new JPanel();
		typePanel.setOpaque(false);
		typePanel.add(cl1);
		typePanel.add(cl2);
		typePanel.add(cl3);
		typePanel.add(cl4);
		typePanel.add(cl5);

		JPanel checkPanel = new JPanel();
		checkPanel.setOpaque(false);
		checkPanel.add(ability);
		checkPanel.add(trainedOnly);
		checkPanel.add(armorCheck);

		JPanel syn1Panel = new JPanel();
		syn1Panel.setOpaque(false);
		syn1Panel.add(synCheck);
		syn1Panel.add(new JLabel("Synergy Bonus of "));
		syn1Panel.add(synBonus);
		syn1Panel.add(new JLabel(" if you also have "));
		syn1Panel.add(synRanks);
		syn1Panel.add(new JLabel(" or more ranks in "));
		syn1Panel.add(synValue);

		JPanel syn2Panel = new JPanel();
		syn2Panel.setOpaque(false);
		syn2Panel.add(synCheck2);
		syn2Panel.add(new JLabel("Synergy Bonus of "));
		syn2Panel.add(synBonus2);
		syn2Panel.add(new JLabel(" if you also have "));
		syn2Panel.add(synRanks2);
		syn2Panel.add(new JLabel(" or more ranks in "));
		syn2Panel.add(synValue2);

		

		int ypos = 0;

		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.add(new JLabel("Skill Name"));
		headerPanel.add(name);
		headerPanel.add(new JLabel("Source"));
		headerPanel.add(source);
		
		main.doLayout(headerPanel, 0, ypos, 2, 1);
		ypos++;

		main.doLayout(typePanel, 0, ypos, 2, 1);
		ypos++;

		main.doLayout(checkPanel, 0, ypos, 2, 1);
		ypos++;

		main.doLayout(syn1Panel, 0, ypos, 2, 1);
		ypos++;

		main.doLayout(syn2Panel, 0, ypos, 2, 1);
		ypos++;

		main.setWeightX(1);
		main.setWeightY(1);
		
		main.doLayout(descScroll, 0, ypos, 2, 1);
		main.setWeightX(0);
		main.setWeightY(0);
		
		ypos++;

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSkill();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrev();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setSkill((Skill) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setSkill((Skill) owner.getSelected());
	}

	public void setSkill(Skill f) {
		this.skill = f;
		description.setText(f.getDescription());
		name.setText(f.getSkill());

		if (f.getClass1() != null) {
			cl1.setSelectedClass(f.getClass1());
		} else {
			cl1.setSelectedIndex(0);
		}
		if (f.getClass2() != null) {
			cl2.setSelectedClass(f.getClass2());
		} else {
			cl2.setSelectedIndex(0);
		}
		if (f.getClass3() != null) {
			cl3.setSelectedClass(f.getClass3());
		} else {
			cl3.setSelectedIndex(0);
		}
		if (f.getClass4() != null) {
			cl4.setSelectedClass(f.getClass4());
		} else {
			cl4.setSelectedIndex(0);
		}
		if (f.getClass5() != null) {
			cl5.setSelectedClass(f.getClass5());
		} else {
			cl5.setSelectedIndex(0);
		}

		if (f.getTrainedOnly().equals("Y")) {
			trainedOnly.setSelected(true);
		} else {
			trainedOnly.setSelected(false);
		}
		if (f.getArmorCheck().equals("Y")) {
			armorCheck.setSelected(true);
		} else {
			armorCheck.setSelected(false);
		}

		ability.setSelectedItem(f.getAbility());

		if (!StrManip.isNullOrEmpty(f.getSynValue())
				&& !f.getSynValue().equals("0")) {
			synCheck.setSelected(true);
			synValue.setSelectedSkill(f.getSynValue());
			synBonus.setText(f.getSynBonus());
			synRanks.setText(f.getSynRanks());
		} else {
			synCheck.setSelected(false);
			synValue.setSelectedIndex(0);
			synBonus.setText("");
			synRanks.setText("");
		}

		if (!StrManip.isNullOrEmpty(f.getSynValue2())
				&& !f.getSynValue2().equals("0")) {
			synCheck2.setSelected(true);
			synValue2.setSelectedSkill(f.getSynValue2());
			synBonus2.setText(f.getSynBonus2());
			synRanks2.setText(f.getSynRanks2());
		} else {
			synCheck2.setSelected(false);
			synValue2.setSelectedIndex(0);
			synBonus2.setText("");
			synRanks2.setText("");
		}
		source.setSelectedItem(skill.getSource());
	}

	public void saveSkill() {
		skill.setSkill(name.getText());
		skill.setDescription(description.getText());

		String c1 = ((CharClass) (cl1.getSelectedItem())).getCharClass();
		if (!c1.equals("--Select--")) {
			skill.setClass1(c1);
		} else {
			skill.setClass1(null);
		}
		String c2 = ((CharClass) (cl2.getSelectedItem())).getCharClass();
		if (!c2.equals("--Select--")) {
			skill.setClass2(c2);
		} else {
			skill.setClass2(null);
		}
		String c3 = ((CharClass) (cl3.getSelectedItem())).getCharClass();
		if (!c3.equals("--Select--")) {
			skill.setClass3(c3);
		} else {
			skill.setClass3(null);
		}
		String c4 = ((CharClass) (cl4.getSelectedItem())).getCharClass();
		if (!c4.equals("--Select--")) {
			skill.setClass4(c4);
		} else {
			skill.setClass4(null);
		}
		String c5 = ((CharClass) (cl5.getSelectedItem())).getCharClass();
		if (!c5.equals("--Select--")) {
			skill.setClass5(c5);
		} else {
			skill.setClass5(null);
		}
		if (trainedOnly.isSelected()) {
			skill.setTrainedOnly("Y");
		}
		if (armorCheck.isSelected()) {
			skill.setArmorCheck("Y");
		}
		if (synCheck.isSelected()) {
			skill.setSynBonus(synBonus.getText());
			skill.setSynValue(((Skill) synValue.getSelectedItem()).getId());
			skill.setSynRanks(synRanks.getText());
		}
		if (synCheck2.isSelected()) {
			skill.setSynBonus2(synBonus2.getText());
			skill.setSynValue2(((Skill) synValue2.getSelectedItem()).getId());
			skill.setSynRanks2(synRanks2.getText());
		}

		skill.setAbility((String) ability.getSelectedItem());
		skill.setSource((String)source.getSelectedItem());
		db.addOrUpdateSkill(skill);

		owner.updateList();
	}

	

}
