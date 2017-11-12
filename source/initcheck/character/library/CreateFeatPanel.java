package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.BonusTypeChooser;
import initcheck.character.chooser.FeatTypeChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.database.Feat;
import initcheck.database.FeatDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;
import initcheck.graphics.ToolTipLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateFeatPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private SourceChooser source = new SourceChooser();
	
	private JTextField name = new JTextField(20);

	private TiledTextArea description = new TiledTextArea(10, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private TiledGridPanel main = new TiledGridPanel("images/rockLighter.jpg");

	private JTextField shortText = new JTextField(20);

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private TiledPanel buttonPanel = new TiledPanel(InitImage.lightRocks);

	private Feat feat;

	private FeatTypeChooser cat1 = new FeatTypeChooser();

	private FeatTypeChooser cat2 = new FeatTypeChooser();

	private FeatTypeChooser cat3 = new FeatTypeChooser();

	private JTextField metamagicLevel = new JTextField(5);

	private JTextField metamagicPrefix = new JTextField(15);

	private JComboBox<String> featType = new JComboBox<String>();

	private JCheckBox checkHp = new JCheckBox("HP Feat");

	private JCheckBox checkAc = new JCheckBox("AC Feat");

	private JCheckBox checkStat = new JCheckBox("Stat Feat");

	private JCheckBox checkSave = new JCheckBox("Save Feat");

	private JCheckBox checkInit = new JCheckBox("Init Feat");

	private JCheckBox checkSkill = new JCheckBox("Skill Feat");

	private JCheckBox checkTemp = new JCheckBox("Temp Bonus");

	private BonusTypeChooser typeChooser = new BonusTypeChooser();

	private FeatPrereqPanel prereqPanel = new FeatPrereqPanel();

	private FeatEffectPanel effectPanel = new FeatEffectPanel();

	private FeatDAO db = new FeatDAO();

	private LibraryPanel owner;

	public CreateFeatPanel(LibraryPanel owner) {
		feat = new Feat();
		featType.addItem("Normal");
		featType.addItem("Weapon");
		featType.addItem("Skill");

		init();
		this.owner = owner;
	}

	public CreateFeatPanel(Feat f, LibraryPanel owner) {

		this.owner = owner;
		this.feat = f;

		featType.addItem("Normal");
		featType.addItem("Weapon");
		featType.addItem("Skill");
		featType.setSelectedItem(f.getFeatType());

		prereqPanel.setFeat(f.getId());
		effectPanel.setFeat(f.getId());

		description.setText(f.getDescription());
		description.setCaretPosition(0);
		name.setText(f.getFeatName());
		shortText.setText(f.getShortText());
		init();
		cat1.setSelectedItem(f.getType());
		if (f.getType2() != null) {
			cat2.setSelectedItem(f.getType2());
		}
		if (f.getType3() != null) {
			cat3.setSelectedItem(f.getType3());
		}

		checkHp.setSelected(f.isHpFeat());
		checkAc.setSelected(f.isAcFeat());
		checkStat.setSelected(f.isStatFeat());
		checkSave.setSelected(f.isSaveFeat());
		checkInit.setSelected(f.isInitFeat());
		checkSkill.setSelected(f.isSkillFeat());
		checkTemp.setSelected(f.isTempBonus());
		typeChooser.setSelectedItem(f.getTempType());
		metamagicLevel.setText("" + f.getMetamagicLevel());
		metamagicPrefix.setText(f.getMetamagicPrefix());
		source.setSelectedItem(feat.getSource());
	}

	public void setFeat(Feat f) {
		this.feat = f;

		featType.setSelectedItem(f.getFeatType());

		prereqPanel.setFeat(f.getId());
		effectPanel.setFeat(f.getId());
		description.setText(f.getDescription());
		description.setCaretPosition(0);
		name.setText(f.getFeatName());
		shortText.setText(f.getShortText());

		cat1.setSelectedItem(f.getType());
		if (f.getType2() != null) {
			cat2.setSelectedItem(f.getType2());
		}
		if (f.getType3() != null) {
			cat3.setSelectedItem(f.getType3());
		}

		checkHp.setSelected(f.isHpFeat());
		checkAc.setSelected(f.isAcFeat());
		checkStat.setSelected(f.isStatFeat());
		checkSave.setSelected(f.isSaveFeat());
		checkInit.setSelected(f.isInitFeat());
		checkSkill.setSelected(f.isSkillFeat());
		checkTemp.setSelected(f.isTempBonus());
		typeChooser.setSelectedItem(f.getTempType());
		metamagicLevel.setText("" + f.getMetamagicLevel());
		metamagicPrefix.setText(f.getMetamagicPrefix());
		source.setSelectedItem(feat.getSource());
	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		TiledPanel typePanel = new TiledPanel("images/rockLighter.jpg");
		typePanel.add(cat1);
		typePanel.add(cat2);
		typePanel.add(cat3);
		ToolTipLabel metaLabel = new ToolTipLabel("Metamagic Level");
		metaLabel.setToolTipText("If the feat is a metamagic feat, record\n"
				+ "the number of levels higher a spell prepared\n"
				+ "with this feat is, and the prefix that should\n"
				+ "appear for a spell so prepared.  For example,\n"
				+ "Extend Spell would have values 1 and 'Extended'.");
		typePanel.add(metaLabel);
		typePanel.add(metamagicLevel);
		typePanel.add(metamagicPrefix);
		TiledPanel checkPanel = new TiledPanel("images/rockLighter.jpg");
		checkPanel.add(checkHp);
		checkPanel.add(checkAc);
		checkPanel.add(checkStat);
		checkPanel.add(checkSave);
		checkPanel.add(checkInit);
		checkPanel.add(checkSkill);
		checkPanel.add(checkTemp);
		checkPanel.add(typeChooser);
		
		int ypos = 0;

		ToolTipLabel typeLabel = new ToolTipLabel("Feat Type");
		typeLabel
				.setToolTipText("Setting this to 'weapon' will make\nthis feat available on the weapons menu");
	
		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		headerPanel.add(new JLabel("Feat Name"));
		headerPanel.add(name);
		headerPanel.add(typeLabel);
		headerPanel.add(featType);
		headerPanel.add(source);
		main.doLayout(headerPanel, 0, ypos, 4, 1);
		
		ypos++;

		main.doLayout(typePanel, 0, ypos, 4, 1);
		ypos++;

		main.doLayout(checkPanel, 0, ypos, 4, 1);
		ypos++;
		main.setWeightX(1);
		main.setWeightY(1);
		
		
		main.doLayout(prereqPanel, 0, ypos, 2, 1);

		main.doLayout(effectPanel, 2, ypos, 2, 1);
		ypos++;
		main.setWeightX(0);
		main.setWeightY(0);
		

		main.doLayout(new JLabel("Short Desc."), 0, ypos);
		main.doLayout(shortText, 1, ypos, 3, 1);
		ypos++;
		main.setWeightX(1);
		main.setWeightY(1);
		
		
		main.doLayout(descScroll, 0, ypos, 4, 1);
		ypos++;
		
		main.setWeightX(0);
		main.setWeightY(0);
		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFeat();
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
		setFeat((Feat) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setFeat((Feat) owner.getSelected());
	}

	public void saveFeat() {
		feat.setFeatName(name.getText());
		feat.setShortText(shortText.getText());
		feat.setDescription(description.getText());
		String c1 = (String) (cat1.getSelectedItem());
		if (!c1.equals("--SELECT--")) {
			feat.setType(c1);
		} else {
			feat.setType(null);
		}
		String c2 = (String) (cat2.getSelectedItem());
		if (!c2.equals("--SELECT--")) {
			feat.setType2(c2);
		} else {
			feat.setType2(null);
		}
		String c3 = (String) (cat3.getSelectedItem());
		if (!c3.equals("--SELECT--")) {
			feat.setType3(c3);
		} else {
			feat.setType3(null);
		}

		String s = (String) featType.getSelectedItem();
		feat.setFeatType(s);
		feat.setAcFeat(checkAc.isSelected());
		feat.setHpFeat(checkHp.isSelected());
		feat.setStatFeat(checkStat.isSelected());
		feat.setSaveFeat(checkSave.isSelected());
		feat.setInitFeat(checkInit.isSelected());
		feat.setSkillFeat(checkSkill.isSelected());
		feat.setTempBonus(checkTemp.isSelected());
		feat.setTempType((String) typeChooser.getSelectedItem());
		if (!metamagicLevel.getText().equals("")) {
			feat.setMetamagicLevel(Integer.parseInt(metamagicLevel.getText()));
		}
		feat.setMetamagicPrefix(metamagicPrefix.getText());
		feat.setSource((String)source.getSelectedItem());
		
		int id = db.addOrUpdateFeat(feat);
		
		prereqPanel.setFeat("" + id);
		effectPanel.setFeat("" + id);
		owner.updateList();

	}

	

}
