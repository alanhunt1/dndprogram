package initcheck.character.library;

import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.chooser.ArmorProfChooser;
import initcheck.character.chooser.ExtraChooser;
import initcheck.character.chooser.ExtraTimingChooser;
import initcheck.character.chooser.ProficiencyTypeChooser;
import initcheck.character.chooser.RacialAbilityTypeChooser;
import initcheck.character.chooser.SaveTypeChooser;
import initcheck.character.chooser.ShieldProfChooser;
import initcheck.character.chooser.SizeChooser;
import initcheck.character.chooser.SkillChooser;
import initcheck.character.chooser.SourceChooser;
import initcheck.character.chooser.StatChooser;
import initcheck.character.chooser.WeaponProfChooser;
import initcheck.database.Race;
import initcheck.database.RaceDAO;
import initcheck.database.RacialAbility;
import initcheck.database.RacialAbilityDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateRacesPanel extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private JTextField name = new JTextField(20);

	private JTextArea description = new JTextArea(5, 40);

	private JScrollPane descScroll = new JScrollPane(description);

	private JList abilityList = new JList();

	private JScrollPane abilityScroll = new JScrollPane(abilityList);

	private Vector<RacialAbility> abilities;

	private TiledGridPanel main = new TiledGridPanel(InitImage.lightRocks);

	private SizeChooser sizeChooser = new SizeChooser();

	private JTextField baseSpeed = new JTextField(20);

	private JTextField favoredClass = new JTextField(20);

	private JComboBox abType = new ExtraChooser();

	private JComboBox abType2 = new ExtraTimingChooser();

	private JTextField abVal = new JTextField(5);

	private RacialAbilityTypeChooser raChooser = new RacialAbilityTypeChooser();

	private PanelButton saveButton = new PanelButton("Save");

	private PanelButton cancelButton = new PanelButton("Close");

	private PanelButton remAbButton = new PanelButton("Rem");

	private PanelButton addAbButton = new PanelButton("Add");
	
	private PanelButton prevButton = new PanelButton("<", 20);

	private PanelButton nextButton = new PanelButton(">", 20);

	private JPanel buttonPanel = new JPanel();

	private JTextField levelAdjustment = new JTextField(5);
	
	private Race race;

	private SourceChooser sourceChooser = new SourceChooser();
	
	JPanel typePanel = new JPanel();

	RaceDAO db = new RaceDAO();

	LibraryPanel owner;

	JPanel typePanel2 = new JPanel();

	public CreateRacesPanel(LibraryPanel owner) {
		race = new Race();

		init();
		this.owner = owner;
	}

	public void setRace(Race f){
		this.race = f;

		description.setText(f.getDescription());
		name.setText(f.getRace());
		sizeChooser.setSelectedItem(f.getSize());
		baseSpeed.setText(f.getBasespeed());
		favoredClass.setText(f.getFavoredclass());
		RacialAbilityDAO radb = new RacialAbilityDAO();
		abilities = radb.getRacialAbilities(f.getRace());
		abilityList.setListData(abilities);
		sourceChooser.setSelectedItem(f.getSource());
		levelAdjustment.setText(f.getLevelAdjustment());
	}
	
	public CreateRacesPanel(Race f, LibraryPanel owner) {

		this.owner = owner;
		setRace(f);
		init();
	}

	public void init() {

		description.setLineWrap(true);
		description.setWrapStyleWord(true);

		typePanel.setLayout(new BorderLayout());
		typePanel2.add(raChooser);
		typePanel2.add(abType);
		typePanel2.add(abType2);
		typePanel2.add(abVal);

		JPanel typePanel3 = new JPanel();
		typePanel3.setOpaque(false);
		typePanel3.add(addAbButton);
		typePanel3.add(remAbButton);

		typePanel.add(typePanel2, BorderLayout.NORTH);
		typePanel.add(typePanel3, BorderLayout.SOUTH);
		
		typePanel.setOpaque(false);
		typePanel2.setOpaque(false);
		
		

		int ypos = 0;

		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(new JLabel("Race Name"));
		topPanel.add(name);
		topPanel.add(new JLabel("Size"));
		topPanel.add(sizeChooser);
		topPanel.add(new JLabel("Source"));
		topPanel.add(sourceChooser);
		
		main.doLayout(topPanel, 0, ypos, 4, 1);
		
		ypos++;

		JPanel secondPanel = new JPanel();
		secondPanel.setOpaque(false);
		
		secondPanel.add(new JLabel("Base Speed"));
		secondPanel.add(baseSpeed);
		secondPanel.add(new JLabel("Favored Class"));
		secondPanel.add(favoredClass);
		secondPanel.add(new JLabel("Level Adjustment"));
		secondPanel.add(levelAdjustment);
		main.doLayout(secondPanel, 0, ypos, 4, 1);
		ypos++;

		
		main.doLayout(abilityScroll, 0, ypos, 4, 1);
		
		ypos++;

		main.doLayout(typePanel, 0, ypos, 4, 1);
		ypos++;

		main.setWeightX(1.0);
		main.setWeightY(1.0);
		main.doLayout(descScroll, 0, ypos, 4, 1);
		main.setWeightX(0);
		main.setWeightY(0);
		ypos++;

		buttonPanel.add(prevButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(nextButton);
		buttonPanel.setOpaque(false);
		
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
				saveRace();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		remAbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAbility();
			}
		});

		addAbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAbility();
			}
		});

		raChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRaTypeName();
			}
		});

		abType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRaType2();
			}
		});

		setMainWindow(main);
		setButtonBar(buttonPanel);
		pack();
		setVisible(true);
	}

	public void showNext() {
		owner.incSelected();
		setRace((Race) owner.getSelected());
	}

	public void showPrev() {
		owner.decSelected();
		setRace((Race) owner.getSelected());
	}
	
	public void addAbility() {
		RacialAbility ra = new RacialAbility();
		ra.setRaceId(race.getId());
		ra.setAbilityName((String) raChooser.getSelectedItem());
		ra.setAbilityType(abType.getSelectedItem().toString());
		ra.setAbilityType2(abType2.getSelectedItem().toString());
		ra.setAbilityValue(abVal.getText());
		RacialAbilityDAO radb = new RacialAbilityDAO();
		radb.addRacialAbility(ra);
		abilities = radb.getRacialAbilities(race.getRace());
		abilityList.setListData(abilities);
	}

	public void removeAbility() {
		int idx = abilityList.getSelectedIndex();
		RacialAbility ra = (RacialAbility) abilities.get(idx);
		RacialAbilityDAO radb = new RacialAbilityDAO();
		radb.deleteRacialAbility(ra);
		abilities = radb.getRacialAbilities(race.getRace());
		abilityList.setListData(abilities);
		abilityList.setSelectedIndex(idx);
	}

	public void setRaTypeName() {
		String s = (String) raChooser.getSelectedItem();

		invalidate();

		typePanel2.remove(abType);
		typePanel2.remove(abType2);
		typePanel2.remove(abVal);

		if (s.equals("EXTRA")) {
			abType = new ExtraChooser();
			abType2 = new ExtraTimingChooser();
		} else if (s.equals("STAT")) {
			abType = new StatChooser();
		} else if (s.equals("SKILL")) {
			abType = new SkillChooser();
		} else if (s.equals("PROFICIENCY")) {
			abType = new ProficiencyTypeChooser();
		} else if (s.equals("SAVING THROW")) {
			abType = new SaveTypeChooser();
		}

		abType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setRaType2();
			}
		});

		typePanel2.add(abType);
		typePanel2.add(abType2);
		typePanel2.add(abVal);

		validate();
	}

	public void setRaType2() {

		String name = (String) raChooser.getSelectedItem();

		invalidate();

		typePanel2.remove(abType2);
		typePanel2.remove(abVal);

		if (name.equals("PROFICIENCY")) {
			String s = (String) abType.getSelectedItem();
			if (s.equals("WEAPON")) {
				abType2 = new WeaponProfChooser();
			} else if (s.equals("ARMOR")) {
				abType2 = new ArmorProfChooser();
			} else if (s.equals("SHIELD")) {
				abType2 = new ShieldProfChooser();
			}
		}

		typePanel2.add(abType2);
		typePanel2.add(abVal);

		validate();
	}

	public void saveRace() {
		race.setRace(name.getText());
		race.setDescription(description.getText());
		race.setBasespeed(baseSpeed.getText());
		race.setFavoredclass(favoredClass.getText());
		race.setSize((String) sizeChooser.getSelectedItem());
		race.setSource((String)sourceChooser.getSelectedItem());
		race.setLevelAdjustment(levelAdjustment.getText());
		db.addOrUpdateRace(race);
		owner.updateList();
	}

	
}
