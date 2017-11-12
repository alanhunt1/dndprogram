package initcheck.character.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.character.chooser.AbilityChooser;
import initcheck.character.chooser.AlignmentChooser;
import initcheck.character.chooser.ClassChooser;
import initcheck.character.chooser.FeatChooser;
import initcheck.character.chooser.FeatTypeChooser;
import initcheck.character.chooser.HitDieChooser;
import initcheck.character.chooser.LevelChooser;
import initcheck.character.chooser.PrereqTypeChooser;
import initcheck.character.chooser.RaceChooser;
import initcheck.character.chooser.RulesetChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.StatChooser;
import initcheck.database.CharClass;
import initcheck.database.CharClassDAO;
import initcheck.database.CharClassPrereq;
import initcheck.database.Feat;
import initcheck.database.Skill;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;

public class CreateCharClassPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JList<CharClassPrereq> charClassList = new JList<CharClassPrereq>();

	private JTextField name = new JTextField(20);

	private JTextField turnMod = new JTextField(2);
	
	private JTextArea description = new JTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private TiledGridPanel main = new TiledGridPanel();

	private JScrollPane listScroll = new JScrollPane(charClassList);

	private ClassAdvancePanel classAdvance;

	private ClassAdvancePanel alternateAdvance;

	private ClassSkillPanel classSkill;

	private ClassSpellsPanel classSpell;

	private ClassSpellsPanel spellsKnown;

	private ClassProficiencyPanel profPanel;

	private ClassAbilityPanel abilityPanel;

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);
	
	private PanelButton remButton = new PanelButton("Remove Prereq", 80);

	private JPanel buttonPanel = new JPanel();

	private CharClass charClass;

	private Vector<CharClassPrereq> prereq;

	private PrereqTypeChooser reqType = new PrereqTypeChooser();

	private JComboBox<?> reqName = new JComboBox<Object>();

	private JTextField reqValue = new JTextField(5);

	private PanelButton addReqButton = new PanelButton("Add Prereq", 70);

	private SourceChooser sourceChooser = new SourceChooser();
	
	private RulesetChooser rulesetChooser = new RulesetChooser();
	
	JPanel prereqPanel = new JPanel();

	CharClassDAO db = new CharClassDAO();

	LibraryPanel owner;

	JPanel infoPanel = new JPanel();
	
	JPanel infoPanel2 = new JPanel();

	JPanel casterPanel = new JPanel();

	private HitDieChooser hitDie = new HitDieChooser();

	private JCheckBox prestige = new JCheckBox("Prestige Class");

	private JCheckBox chained = new JCheckBox("Chain To Selected");

	private JTextField skillPoints = new JTextField(3);

	private JTabbedPane tabPane = new JTabbedPane();

	private JCheckBox arcane = new JCheckBox("Arcane Caster");

	private JCheckBox divine = new JCheckBox("Divine Caster");

	private JCheckBox unprepared = new JCheckBox("Spontaneous Caster");

	public CreateCharClassPanel(LibraryPanel owner) {
		charClass = new CharClass();
		prereq = new Vector<CharClassPrereq>();
		init();
		this.owner = owner;
	}

	public CreateCharClassPanel(CharClass f, LibraryPanel owner) {

		this.owner = owner;
		this.charClass = f;
		prereq = db.getCharClassPrereqs(f.getId());
		charClassList.setListData(prereq);
		description.setText(f.getDescription());

		name.setText(f.getCharClass());
		skillPoints.setText(f.getSkillPoints());
		arcane.setSelected(f.isArcaneCaster());
		divine.setSelected(f.isDivineCaster());
		unprepared.setSelected(f.isUnpreparedCaster());
		turnMod.setText(f.getTurningLevelMod());
		init();
		if (f.getPrestige().equals("Y")) {
			prestige.setSelected(true);
		}
		hitDie.setSelectedItem(f.getHitdie());
		sourceChooser.setSelectedItem(f.getSource());
		rulesetChooser.setSelectedRuleset(f.getRulesetId());
	}

	public void setCharClass(CharClass f){
		this.charClass = f;
		prereq = db.getCharClassPrereqs(f.getId());
		charClassList.setListData(prereq);
		description.setText(f.getDescription());

		name.setText(f.getCharClass());
		skillPoints.setText(f.getSkillPoints());
		arcane.setSelected(f.isArcaneCaster());
		divine.setSelected(f.isDivineCaster());
		unprepared.setSelected(f.isUnpreparedCaster());
		turnMod.setText(f.getTurningLevelMod());
		classAdvance.setCharClass(f, false);
		alternateAdvance.setCharClass(f, true);
		classSkill.setCharClass(f);
		classSpell.setCharClass(f, false);
		spellsKnown.setCharClass(f, true);
		profPanel.setCharClass(f);
		abilityPanel.setCharClass(f);
		if (f.getPrestige().equals("Y")) {
			prestige.setSelected(true);
		}
		hitDie.setSelectedItem(f.getHitdie());
		sourceChooser.setSelectedItem(f.getSource());
		rulesetChooser.setSelectedRuleset(f.getRulesetId());
	}
	
	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		charClassList.setVisibleRowCount(5);
		classAdvance = new ClassAdvancePanel(charClass, false);
		alternateAdvance = new ClassAdvancePanel(charClass, true);
		classSkill = new ClassSkillPanel(charClass);
		classSpell = new ClassSpellsPanel(charClass);
		spellsKnown = new ClassSpellsPanel(charClass, true);
		profPanel = new ClassProficiencyPanel(charClass);
		abilityPanel = new ClassAbilityPanel(charClass);

		infoPanel.add(new JLabel("Class Name"));
		infoPanel.add(name);
		infoPanel.add(new JLabel("Source"));
		infoPanel.add(sourceChooser);
		infoPanel.add(new JLabel("Ruleset"));
		infoPanel.add(rulesetChooser);
		
		infoPanel2.add(new JLabel("Hit Die"));
		infoPanel2.add(hitDie);
		infoPanel2.add(new JLabel("Skill Points"));
		infoPanel2.add(skillPoints);
		infoPanel2.add(prestige);

		prereqPanel.add(reqType);
		prereqPanel.add(reqName);
		prereqPanel.add(reqValue);
		prereqPanel.add(chained);
		prereqPanel.add(addReqButton);
		prereqPanel.add(remButton);

		casterPanel.add(arcane);
		casterPanel.add(divine);
		casterPanel.add(unprepared);
		casterPanel.add(turnMod);
		
		

		if (prereq != null) {
			charClassList.setListData(prereq);

			int ypos = 0;

			main.doLayout(infoPanel, 0, ypos, 2, 1);
			ypos++;

			main.doLayout(infoPanel2, 0, ypos, 2, 1);
			ypos++;
			
			main.doLayout(casterPanel, 0, ypos, 2, 1);
			ypos++;

			main.setWeightX(1.0);
			main.setWeightY(1.0);
			main.doLayout(listScroll, 0, ypos, 2, 1);
			main.setWeightX(0);
			main.setWeightY(0);
			ypos++;

			main.doLayout(prereqPanel, 0, ypos, 2, 1);
			ypos++;

			main.doLayout(classSkill, 0, ypos, 2, 1);
			ypos++;

			main.doLayout(descScroll, 0, ypos, 2, 1);
			ypos++;

		}
		
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		
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

		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCharClass();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePrereq();
			}
		});

		reqType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPrereqName();
			}
		});

		addReqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPrereq();
			}
		});

		tabPane.addTab("Info", main);
		tabPane.addTab("Abilities", abilityPanel);
		tabPane.addTab("Proficiency", profPanel);
		tabPane.addTab("Advancement", classAdvance);
		tabPane.addTab("Alt Advancement", alternateAdvance);
		tabPane.addTab("Spells/Day", classSpell);
		tabPane.addTab("Spells Known", spellsKnown);

		setMainWindow(tabPane);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setCharClass((CharClass) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setCharClass((CharClass) owner.getSelected());
	}
	
	public void selectPrereqName() {
		String s = (String) reqType.getSelectedItem();
		System.out.println("REQ TYPE IS "+s);
		invalidate();
		prereqPanel.remove(reqName);
		prereqPanel.remove(reqValue);
		prereqPanel.remove(chained);
		prereqPanel.remove(addReqButton);
		prereqPanel.remove(remButton);

		if (s.equalsIgnoreCase("CLASS")) {
			reqName = new ClassChooser();
		} else if (s.equalsIgnoreCase("STAT")) {
			reqName = new StatChooser();
		} else if (s.equalsIgnoreCase("SKILL")) {
			reqName = new SkillChooser();
		} else if (s.equalsIgnoreCase("LEVEL")) {
			reqName = new LevelChooser();
		} else if (s.equalsIgnoreCase("ABILITY")) {
			reqName = new AbilityChooser();
		} else if (s.equalsIgnoreCase("ALIGNMENT")) {
			reqName = new AlignmentChooser();
		} else if (s.equalsIgnoreCase("RACE")) {
			reqName = new RaceChooser();
		} else if (s.equalsIgnoreCase("FEAT")) {
			reqName = new FeatChooser();
		} else if (s.equalsIgnoreCase("FEAT CLASS")) {
			reqName = new FeatTypeChooser();
		}

		prereqPanel.add(reqName);
		prereqPanel.add(reqValue);
		prereqPanel.add(chained);
		prereqPanel.add(addReqButton);
		prereqPanel.add(remButton);
		validate();
		pack();
	}

	public void addPrereq() {
		boolean error = false;
		boolean insert = true;
		int index = 0;
		if (chained.isSelected()) {
			CharClassPrereq curr = (CharClassPrereq) charClassList
					.getSelectedValue();
			if (curr.getChained().equals("Y")) {
				error = true;
			}
			curr.setChained("Y");
			index = charClassList.getSelectedIndex();
			insert = true;
		}
		if (!error) {
			CharClassPrereq fp = new CharClassPrereq();
			fp.setPrereqType((String) reqType.getSelectedItem());
			if (fp.getPrereqType().equalsIgnoreCase("FEAT")) {
				fp.setPrereqName(((Feat) reqName.getSelectedItem())
						.getFeatName());
			} else if (fp.getPrereqType().equalsIgnoreCase("SKILL")) {
				fp
						.setPrereqName(((Skill) reqName.getSelectedItem())
								.getSkill());
			} else {
				fp.setPrereqName((String) reqName.getSelectedItem());
			}
			fp.setPrereqVal(reqValue.getText());
			fp.setCharClassId(charClass.getId());
			if (insert) {
				prereq.insertElementAt(fp, index);
			} else {
				prereq.add(fp);
			}
			charClassList.setListData(prereq);
		}
	}

	public void saveCharClass() {
		if (skillPoints.getText().equals("")){
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Required Field Missing", "The number of skill points per level is a required field.");
			return;
		}
	
		charClass.setCharClass(name.getText());
		charClass.setDescription(description.getText());
		charClass.setSkillPoints(skillPoints.getText());
		charClass.setArcaneCaster(arcane.isSelected());
		charClass.setDivineCaster(divine.isSelected());
		charClass.setUnpreparedCaster(unprepared.isSelected());
		charClass.setTurningLevelMod(turnMod.getText());
		charClass.setSource((String)sourceChooser.getSelectedItem());
		charClass.setRulesetId((String)rulesetChooser.getSelectedItem());
		if (prestige.isSelected()) {
			charClass.setPrestige("Y");
		}
		charClass.setHitdie((String) hitDie.getSelectedItem());

		int id = db.addOrUpdateCharClass(charClass);
		charClass.setId("" + id);
		db.clearPrereqs("" + id);

		int chainVal = -1;

		for (int i = 0; i < prereq.size(); i++) {
			CharClassPrereq fp = (CharClassPrereq) prereq.get(i);
			if (fp.getChained().equals("Y")) {
				fp.setChainedTo("" + chainVal);
			}
			fp.setCharClassId("" + id);
			chainVal = db.addOrUpdateCharClassPrereq(fp);
		}
		owner.updateList();
	}

	public void removePrereq() {
		int idx = charClassList.getSelectedIndex();
		if (idx > -1){
			prereq.removeElementAt(idx);
			CharClassPrereq fp = (CharClassPrereq) prereq.get(idx);
			fp.setChained("N");
			charClassList.setListData(prereq);
		}
	}

}
