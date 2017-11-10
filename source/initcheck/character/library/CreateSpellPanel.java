package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.GridPanel;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.SpellSchoolChooser;
import initcheck.database.Spell;
import initcheck.database.SpellClass;
import initcheck.database.SpellClassesDAO;
import initcheck.database.SpellDAO;
import initcheck.graphics.TiledDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateSpellPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private SpellSchoolChooser school = new SpellSchoolChooser();

	private JTextField type = new JTextField(20);

	private JTextField subtype = new JTextField(20);

	private JTextField time = new JTextField(20);

	private JTextField level = new JTextField(20);

	private JTextField components = new JTextField(20);

	private JTextField range = new JTextField(20);

	private JTextField duration = new JTextField(20);

	private JTextField effect = new JTextField(20);

	private JTextField effectType = new JTextField(20);

	private JTextField savingThrow = new JTextField(20);

	private JTextField resist = new JTextField(20);

	private JTextField shortDesc = new JTextField(50);

	private JTextArea description = new JTextArea(10, 50);

	private JScrollPane descScroll = new JScrollPane(description);

	private GridPanel main = new GridPanel();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private PanelButton add = new PanelButton("add");

	private PanelButton rem = new PanelButton("rem");

	private JPanel buttonPanel = new JPanel();

	private Spell spell;

	private JList classes = new JList();

	private JScrollPane classScroll = new JScrollPane(classes);

	private SourceChooser sourceChooser = new SourceChooser();
	
	JPanel typePanel = new JPanel();

	// private JCheckBox time = new JCheckBox("Armor Check Penalty");

	SpellDAO db = new SpellDAO();

	LibraryPanel owner;

	public CreateSpellPanel(LibraryPanel owner) {

		this.owner = owner;
		this.spell = new Spell();
		init();

	}

	public CreateSpellPanel(Spell f, LibraryPanel owner) {

		this.owner = owner;
		this.spell = f;
		description.setText(f.getDescription());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		name.setText(f.getSpellName());
		school.setSelectedItem(f.getSchool());
		type.setText(f.getType());
		subtype.setText(f.getSubType());
		time.setText(f.getTime());
		level.setText(f.getLevel());
		components.setText(f.getComponents());
		duration.setText(f.getDuration());
		savingThrow.setText(f.getSavingThrow());
		resist.setText(f.getResist());
		effect.setText(f.getEffect());
		effectType.setText(f.getEffectType());
		range.setText(f.getRange());
		shortDesc.setText(f.getShortDesc());
		classes.setListData(f.getSpellClasses());
		classes.setVisibleRowCount(3);
		sourceChooser.setSelectedItem(f.getSource());
		init();

	}

	public void init() {

		main.doLayout(new JLabel("Spell Name"), 0, main.ypos);
		main.doLayout(name, 1, main.ypos);
		main.doLayout(new JLabel("Source"), 2, main.ypos);
		main.doLayout(sourceChooser, 3, main.ypos);
		
		main.incYPos();

		
		main.doLayout(typePanel, 0, main.ypos, 4, 1);
		main.incYPos();

		main.doLayout(new JLabel("School"), 0, main.ypos);
		main.doLayout(school, 1, main.ypos);
		main.doLayout(new JLabel("Casting Time"), 2, main.ypos);
		main.doLayout(time, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Spell Type"), 0, main.ypos);
		main.doLayout(type, 1, main.ypos);
		main.doLayout(new JLabel("Level"), 2, main.ypos);
		main.doLayout(classScroll, 3, main.ypos);
		main.incYPos();

		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.add(add);
		buttonPanel2.add(rem);
		main.doLayout(buttonPanel2, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Subtype"), 0, main.ypos);
		main.doLayout(subtype, 1, main.ypos);
		main.doLayout(new JLabel("Components"), 2, main.ypos);
		main.doLayout(components, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Range"), 0, main.ypos);
		main.doLayout(range, 1, main.ypos);
		main.doLayout(new JLabel("Duration"), 2, main.ypos);
		main.doLayout(duration, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Effect"), 0, main.ypos);
		main.doLayout(effect, 1, main.ypos);
		main.doLayout(new JLabel("Effect Type"), 2, main.ypos);
		main.doLayout(effectType, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Saving Throw"), 0, main.ypos);
		main.doLayout(savingThrow, 1, main.ypos);
		main.doLayout(new JLabel("Resist"), 2, main.ypos);
		main.doLayout(resist, 3, main.ypos);
		main.incYPos();

		main.doLayout(new JLabel("Short Desc"), 0, main.ypos);
		main.doLayout(shortDesc, 1, main.ypos, 3, 1);
		main.incYPos();

		main.setWeightX(1.0);
		main.setWeightY(1.0);
		main.doLayout(descScroll, 0, main.ypos, 4, 1);
		main.incYPos();

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClassLevel();
			}
		});

		rem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeClassLevel();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSpell();
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

	private void addClassLevel() {
		SpellClassDialog scd = new SpellClassDialog(this);
		scd.setVisible(true);

	}

	private void removeClassLevel() {
		SpellClass sc = (SpellClass) classes.getSelectedValue();
		if (sc != null) {
		
			SpellClassesDAO scd = new SpellClassesDAO();
			scd.deleteSpellClasses(sc);
			reloadSpellClasses();
		}
	}

	public void reloadSpellClasses() {
		Spell s = new Spell();
		s.setId(spell.getId());
		db.resetClassHash();
		spell = (Spell) (db.selectSpell(s)).get(0);
		classes.setListData(spell.getSpellClasses());
		owner.updateList();
	}

	public Spell getSpell() {
		return spell;
	}

	public void showNext() {
		owner.incSelected();
		setSpell((Spell) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setSpell((Spell) owner.getSelected());
	}

	public void setSpell(Spell f) {
		this.spell = f;

		description.setText(f.getDescription());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		name.setText(f.getSpellName());
		school.setSelectedItem(f.getSchool());
		type.setText(f.getType());
		subtype.setText(f.getSubType());
		time.setText(f.getTime());
		level.setText(f.getLevel());
		components.setText(f.getComponents());
		duration.setText(f.getDuration());
		savingThrow.setText(f.getSavingThrow());
		resist.setText(f.getResist());
		effect.setText(f.getEffect());
		effectType.setText(f.getEffectType());
		range.setText(f.getRange());
		shortDesc.setText(f.getShortDesc());
		classes.setListData(f.getSpellClasses());
		sourceChooser.setSelectedItem(f.getSource());
	}

	public void saveSpell() {
		spell.setSpellName(name.getText());
		spell.setDescription(description.getText());
		spell.setSchool((String) school.getSelectedItem());
		spell.setType(type.getText());
		spell.setSubType(subtype.getText());
		spell.setTime(time.getText());
		spell.setLevel(level.getText());
		spell.setComponents(components.getText());
		spell.setDuration(duration.getText());
		spell.setSavingThrow(savingThrow.getText());
		spell.setResist(resist.getText());
		spell.setEffect(effect.getText());
		spell.setEffectType(effectType.getText());
		spell.setRange(range.getText());
		spell.setShortDesc(shortDesc.getText());
		spell.setSource((String)sourceChooser.getSelectedItem());
		db.addOrUpdateSpell(spell);

		owner.updateList();
	}

}
