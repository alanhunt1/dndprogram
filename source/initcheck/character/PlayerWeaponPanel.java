package initcheck.character;

import initcheck.DCharacter;
import initcheck.DescriptionDialog;
import initcheck.DescriptionListDialog;
import initcheck.InitImage;
import initcheck.InitLogger;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.TransparentButton;
import initcheck.character.chooser.MaterialChooser;
import initcheck.character.chooser.SizeChooser;
import initcheck.character.chooser.WeaponAbilityChooser;
import initcheck.character.chooser.WeaponChooser;
import initcheck.character.chooser.WeaponUseChooser;
import initcheck.character.itembuilder.ItemBuilderDialog;
import initcheck.database.Feat;
import initcheck.database.Materials;
import initcheck.database.PlayerFeats;
import initcheck.database.PlayerWeaponsDAO;
import initcheck.database.Weapon;
import initcheck.database.WeaponAbilities;
import initcheck.database.WeaponViews;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerWeaponPanel extends TiledPanel implements ActionListener,
		ListSelectionListener, StatusTab {

	
	private static final long serialVersionUID = 1L;

	private TiledList weaponList = new TiledList();

	private JScrollPane weaponScroll = new JScrollPane(weaponList);

	private TiledList profList = new TiledList();

	private JScrollPane profScroll = new JScrollPane(profList);

	private TiledList calcList = new TiledList();

	private JScrollPane calcScroll = new JScrollPane(calcList);


	private InitLogger logger = new InitLogger(this);
	private TiledList rangedCalcList = new TiledList();

	private JScrollPane rangedCalcScroll = new JScrollPane(rangedCalcList);

	private TiledList damageCalcList = new TiledList();

	private JScrollPane damageCalcScroll = new JScrollPane(damageCalcList);

	private TiledList critRangeList = new TiledList();
	
	private JScrollPane critRangeScroll = new JScrollPane(critRangeList);
	
	private TiledList critMultList = new TiledList();
	
	private JScrollPane critMultScroll = new JScrollPane(critMultList);
	
	private final PlayerStatDialog owner;

	private PanelButton addWeapon = new PanelButton("Add");

	private PanelButton delWeapon = new PanelButton("Rem");

	private PanelButton saveWeapon = new PanelButton("Save");

	private PanelButton decWeapon = new PanelButton("^", 10);

	private PanelButton incWeapon = new PanelButton("v", 10);

	private WeaponChooser weaponSelect = new WeaponChooser();

	private WeaponAbilityChooser abilitySelect = new WeaponAbilityChooser();

	private WeaponAbilityChooser abilitySelect2 = new WeaponAbilityChooser();

	private WeaponAbilityChooser abilitySelect3 = new WeaponAbilityChooser();

	private WeaponAbilityChooser abilitySelect4 = new WeaponAbilityChooser();

	private WeaponAbilityChooser abilitySelect5 = new WeaponAbilityChooser();

	private WeaponAbilityChooser abilitySelect6 = new WeaponAbilityChooser();

	
	private JTextField weaponValue = new JTextField(2);

	private JCheckBox masterwork = new JCheckBox("MW");

	private JCheckBox named = new JCheckBox("Named");
	
	private JTextField trueName = new JTextField(15);
	
	private JPanel weaponPanel = new JPanel();

	
	
	private WeaponUseChooser weaponUseChooser = new WeaponUseChooser();

	private JTextField weaponNotes = new JTextField(30);

	private JTextField critStart = new JTextField(3);

	private JTextField critEnd = new JTextField(3);

	private JTextField critMult = new JTextField(3);

	private JCheckBox critOverride = new JCheckBox("Crit Override");

	private JLabel hitLabel = new JLabel("");

	private JLabel babLabel = new JLabel("BAB : ");

	private JLabel hitLabel2 = new JLabel("");

	private JLabel critLabel = new JLabel("Crit : ");
	
	private JLabel damageLabel = new JLabel("");

	private JLabel avgDamageLabel = new JLabel("");

	private JLabel calcDamageLabel = new JLabel("");

	private JLabel sizeLabel = new JLabel("");

	private JLabel abilityBonusLabel = new JLabel("");
	
	private SizeChooser weaponSize = new SizeChooser();

	private JTextField damageOverride = new JTextField(5);

	private JLabel baseDamageLabel = new JLabel("");

	private JCheckBox useMonk = new JCheckBox("Use Monk Attack");

	private JTextField quantity = new JTextField(3);

	private JCheckBox displaySheet = new JCheckBox("Print on Sheet");

	private JCheckBox displayItem = new JCheckBox("Carried Item");

	private PanelButton applyFeats = new PanelButton("Apply Feats", 100);

	private PanelButton applyMods = new PanelButton("Apply Mods", 100);

	private PanelButton applyAbilities = new PanelButton("Apply Ability", 100);

	private MaterialChooser material = new MaterialChooser();

	private TransparentButton weaponButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponMatButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb1Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb2Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb3Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb4Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb5Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton weaponAb6Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton mcalcButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton rcalcButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton ccalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton dcalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton pcalcButton = new TransparentButton(InitImage.qmarkIcon);
	private boolean smallScreen = true;
	
	
	boolean updateRequired;

	WeaponPopupMenu rClickMenu = new WeaponPopupMenu(this);

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}

	
	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	/**
	 * Set the UpdateRequired value.
	 * 
	 * @param newUpdateRequired
	 *            The new UpdateRequired value.
	 */
	public void setUpdateRequired(boolean newUpdateRequired) {
		this.updateRequired = newUpdateRequired;
	}

	int panelpos = 0;

	DCharacter dc;

	Calculation meleeHit = null;

	Calculation rangedHit = null;

	Calculation damage = null;

	Calculation critRangeCalc = null;
	
	Calculation critMultCalc = null;
	
	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerWeaponPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;
		this.dc = dc;

		weaponSelect.addActionListener(this);
		weaponList.setFont(new Font("Courier", Font.PLAIN, 12));
		weaponList.addListSelectionListener(this);
		weaponList.setCellRenderer(new WeaponListCellRenderer());
		weaponList.setVisibleRowCount(5);
		weaponList.setListData(owner.getChar().getWeapons());
		if (owner.getChar().getWeapons().size() > 0) {
			weaponList.setSelectedIndex(0);
		}

		weaponList.addMouseListener(new MouseAdapter() {

			// check all the clicks for a right click, and trigger the
			// popup menu if you see one.
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		
		profList.setListData(owner.getChar().getWeaponProficiencies());
		profList.setVisibleRowCount(3);
		profList.setCellRenderer(new GenericListCellRenderer());
		calcList.setVisibleRowCount(3);
		calcList.setCellRenderer(new GenericListCellRenderer());
		rangedCalcList.setVisibleRowCount(3);
		rangedCalcList.setCellRenderer(new GenericListCellRenderer());
		damageCalcList.setVisibleRowCount(3);
		damageCalcList.setCellRenderer(new GenericListCellRenderer());
		
		critRangeList.setVisibleRowCount(3);
		critRangeList.setCellRenderer(new GenericListCellRenderer());
		critMultList.setVisibleRowCount(3);
		critMultList.setCellRenderer(new GenericListCellRenderer());
		
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;
		JPanel weaponMovePanel = new JPanel(new GridLayout(2, 1));
		weaponMovePanel.add(decWeapon);
		weaponMovePanel.add(incWeapon);

		TiledGridPanel infoPanel = new TiledGridPanel("images/rockLighter.jpg");

		infoPanel.setPadY(2);
		infoPanel.doLayout(babLabel, 0, infoPanel.ypos);
		infoPanel.incYPos();
		infoPanel.doLayout(damageLabel, 0, infoPanel.ypos);
		infoPanel.doLayout(dcalcButton, 1, infoPanel.ypos);
		infoPanel.incYPos();
		infoPanel.doLayout(hitLabel, 0, infoPanel.ypos);
		infoPanel.doLayout(rcalcButton, 1, infoPanel.ypos);
		infoPanel.incYPos();
		//infoPanel.doLayout(avgDamageLabel, 1, infoPanel.ypos);
		//infoPanel.incYPos();

		infoPanel.doLayout(hitLabel2, 0, infoPanel.ypos);
		infoPanel.doLayout(mcalcButton, 1, infoPanel.ypos);
		infoPanel.incYPos();
		//infoPanel.doLayout(calcDamageLabel, 1, infoPanel.ypos);

		//infoPanel.incYPos();
		infoPanel.doLayout(critLabel, 0, infoPanel.ypos);
		infoPanel.doLayout(ccalcButton, 1, infoPanel.ypos);
		
		infoPanel.incYPos();
		infoPanel.doLayout(new JLabel("Proficiencies"), 0, infoPanel.ypos);
		infoPanel.doLayout(pcalcButton, 1, infoPanel.ypos);
		
		if (!smallScreen){
		infoPanel.setWeightY(0.2);
		infoPanel.setWeightX(0.5);
		infoPanel.doLayout(new JLabel("Range"), 0, infoPanel.ypos);
		infoPanel.doLayout(new JLabel("Mult"), 1, infoPanel.ypos);
		infoPanel.setWeightY(0);
		infoPanel.setWeightX(1);
		infoPanel.incYPos();
		infoPanel.doLayout(critRangeScroll, 0, infoPanel.ypos);
		infoPanel.doLayout(critMultScroll, 1, infoPanel.ypos);
		infoPanel.incYPos();
		infoPanel.doLayout(new JLabel("Melee Calc"), 0, infoPanel.ypos, 2, 1);
		infoPanel.incYPos();
		infoPanel.setWeightY(0.2);
		infoPanel.doLayout(calcScroll, 0, infoPanel.ypos, 2, 1);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();
		infoPanel.doLayout(new JLabel("Ranged Calc"), 0, infoPanel.ypos, 2, 1);
		infoPanel.incYPos();
		infoPanel.setWeightY(0.2);
		infoPanel.doLayout(rangedCalcScroll, 0, infoPanel.ypos, 2, 1);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();
		infoPanel.doLayout(new JLabel("Damage Calc"), 0, infoPanel.ypos, 2, 1);
		infoPanel.incYPos();
		infoPanel.setWeightX(1.0);
		infoPanel.setWeightY(0.2);
		infoPanel.doLayout(damageCalcScroll, 0, infoPanel.ypos, 2, 1);
		infoPanel.setWeightX(0);
		infoPanel.setWeightY(0);
		infoPanel.incYPos();
		infoPanel.doLayout(new JLabel("Proficiencies"), 0, infoPanel.ypos, 2, 1);
		infoPanel.incYPos();
		infoPanel.setWeightY(0.2);
		infoPanel.doLayout(profScroll, 0, infoPanel.ypos, 2, 1);
		infoPanel.incYPos();
		}
		TiledGridPanel mainPanel = new TiledGridPanel("images/rockLighter.jpg");

		mainPanel.setWeightX(1.0);
		mainPanel.setWeightY(0.2);
		mainPanel.doLayout(weaponScroll, 0, mainPanel.ypos);
		mainPanel.setWeightX(0);
		mainPanel.doLayout(weaponMovePanel, 1, mainPanel.ypos);
		mainPanel.setWeightY(0);
		mainPanel.incYPos();
		
		GridPanel weaponInfoPanel = new GridPanel(false);
		weaponInfoPanel.setOpaque(false);
		weaponPanel.setOpaque(false);
		weaponInfoPanel.doLayout(quantity, 0);
		weaponInfoPanel.doLayout(weaponSelect, 1);
		weaponInfoPanel.doLayout(weaponButton, 2);
		weaponInfoPanel.doLayout(new JLabel(" + "), 3);
		weaponInfoPanel.doLayout(weaponValue, 4);
		weaponInfoPanel.incYPos();
		masterwork.setOpaque(false);
		weaponInfoPanel.doLayout(masterwork, 0,weaponInfoPanel.ypos);
		weaponInfoPanel.doLayout(abilityBonusLabel, 2, weaponInfoPanel.ypos, 3, 1);
		
		weaponPanel.add(weaponInfoPanel);
		
		GridPanel abilityPanel = new GridPanel(false);
		abilityPanel.setOpaque(false);
		abilityPanel.doLayout(abilitySelect,0);
		abilityPanel.doLayout(weaponAb1Button,1);
		abilityPanel.doLayout(abilitySelect2,2);
		abilityPanel.doLayout(weaponAb2Button,3);
		abilityPanel.incYPos();
		abilityPanel.doLayout(abilitySelect3,0);
		abilityPanel.doLayout(weaponAb3Button,1);
		
		abilityPanel.doLayout(abilitySelect4,2);
		abilityPanel.doLayout(weaponAb4Button,3);
		abilityPanel.incYPos();
		abilityPanel.doLayout(abilitySelect5,0);
		abilityPanel.doLayout(weaponAb5Button,1);
		abilityPanel.doLayout(abilitySelect6,2);
		abilityPanel.doLayout(weaponAb6Button,3);
		weaponPanel.add(abilityPanel);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(addWeapon);
		buttonPanel.add(delWeapon);
		buttonPanel.add(saveWeapon);
		buttonPanel.add(applyFeats);
		buttonPanel.add(applyMods);
		buttonPanel.add(applyAbilities);

		JPanel critPanel = new JPanel();
		critPanel.setOpaque(false);
		critPanel.add(new JLabel("Use"));
		critPanel.add(weaponUseChooser);
		critPanel.add(new JLabel("Crit Range "));
		critPanel.add(critStart);
		critPanel.add(new JLabel(" - "));
		critPanel.add(critEnd);
		critPanel.add(new JLabel(" X "));
		critPanel.add(critMult);
		critPanel.add(critOverride);
		critPanel.add(named);
		critPanel.add(trueName);
		
		JPanel overridePanel = new JPanel();
		overridePanel.setOpaque(false);
		Materials m = new Materials();
		m.setDefaults();
		material.setSelectedMaterial(m);

		overridePanel.add(material);
		overridePanel.add(weaponMatButton);

		overridePanel.add(new JLabel("Size : "));
		overridePanel.add(sizeLabel);
		overridePanel.add(new JLabel("Override : "));
		overridePanel.add(weaponSize);
		overridePanel.add(new JLabel("Damage : "));
		overridePanel.add(baseDamageLabel);
		overridePanel.add(new JLabel("Override : "));
		overridePanel.add(damageOverride);
		overridePanel.add(useMonk);

		mainPanel.setWeightX(1.0);
		mainPanel.setWeightY(1.0);
		
		panelpos = mainPanel.ypos;
		mainPanel.doLayout(weaponPanel, 0, mainPanel.ypos, 2, 1);
		mainPanel.incYPos();

		mainPanel.doLayout(critPanel, 0, mainPanel.ypos, 2, 1);
		mainPanel.incYPos();

		mainPanel.doLayout(overridePanel, 0, mainPanel.ypos, 2, 1);
		mainPanel.incYPos();

		JPanel notesPanel = new JPanel();
		notesPanel.setOpaque(false);
		notesPanel.add(new JLabel("Notes : "));
		notesPanel.add(weaponNotes);
		notesPanel.add(displaySheet);
		notesPanel.add(displayItem);

		mainPanel.doLayout(notesPanel, 0, mainPanel.ypos, 2, 1);
		mainPanel.incYPos();

		mainPanel.doLayout(buttonPanel, 0, mainPanel.ypos, 2, 1);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				mainPanel, infoPanel);
		split.setResizeWeight(1.0);
		cn.weightx = 1.0;
		cn.weighty = 1.0;

		split.setOpaque(false);
		doLayout(split, 0, ypos);

		babLabel.setText("BAB : " + owner.getChar().getBaseAttackBonus());

		addWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addWeapon();
			}
		});

		saveWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveWeapon();
			}
		});

		delWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeWeapon();
			}
		});

		incWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveWeapon(1);
			}
		});

		decWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveWeapon(-1);
			}
		});

		applyFeats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyFeats();
			}
		});

		applyMods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyMods();
			}
		});

		applyAbilities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyAbilities();
			}
		});
		
		weaponButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponDescription();
			}
		});
		
		weaponMatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponMatDescription();
			}
		});
		
		weaponAb1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb1Description();
			}
		});
		
		weaponAb2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb2Description();
			}
		});
		
		weaponAb3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb3Description();
			}
		});
		
		weaponAb4Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb4Description();
			}
		});
		
		weaponAb5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb5Description();
			}
		});
		
		weaponAb6Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showWeaponAb6Description();
			}
		});
		
		mcalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showMCalc();
			}
		});
		
		rcalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRCalc();
			}
		});
		
		ccalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCCalc();
			}
		});
		
		dcalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDCalc();
			}
		});
		
		pcalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPCalc();
			}
		});

	}

	public void showWeaponDescription(){
		Weapon a = (Weapon)weaponSelect.getSelectedItem();
		DescriptionDialog.display("Weapon Description", a.getFullDescription());
	}
	
	public void showWeaponMatDescription(){
		Materials a = (Materials)material.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb1Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb2Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect2.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb3Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect3.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb4Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect4.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb5Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect5.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showWeaponAb6Description(){
		WeaponAbilities a = (WeaponAbilities)abilitySelect6.getSelectedItem();
		DescriptionDialog.display("Material Description", a.getFullDescription());
	}
	
	public void showMCalc(){
		
		DescriptionListDialog.display("Melee Calc", meleeHit.getElements());
	}
	
	public void showRCalc(){
		
		DescriptionListDialog.display("Ranged Calc", rangedHit.getElements());
	}
	public void showDCalc(){
		
		DescriptionListDialog.display("Damage Calc", damage.getElements());
	}
	public void showPCalc(){
		
		DescriptionListDialog.display("Proficiencies", owner.getChar().getWeaponProficiencies());
	}

	public void showCCalc(){
		Vector<String> v = new Vector<String>();
		v.addAll(critRangeCalc.getElements());
		v.addAll(critMultCalc.getElements());
		DescriptionListDialog.display("Crit Calc", v);
	}
	
	
	public void upgrade() {
		@SuppressWarnings("unused")
		ItemBuilderDialog ibd = new ItemBuilderDialog((Weapon) weaponList
				.getSelectedValue());
	}

	public WeaponPopupMenu getPopupMenu() {
		return rClickMenu;
	}

	public Vector<PlayerFeats> getFeats() {
		return owner.getChar().getFeatList();
	}

	@SuppressWarnings("unchecked")
	public void applyFeats() {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		Vector<PlayerFeats> feats = owner.getChar().getFeatList();
		Vector<Feat> clone = (Vector<Feat>) feats.clone();
		@SuppressWarnings("unused")
		ApplyFeatsDialog afd = new ApplyFeatsDialog(this, clone, f
				.getInstanceId());

	}

	@SuppressWarnings("unchecked")
	public void applyAbilities() {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		Vector abilities = owner.getChar().getClassSet().getAbilities();
		@SuppressWarnings("unused")
		ApplyAbilitiesDialog aad = new ApplyAbilitiesDialog(this,
				(Vector) abilities.clone(), f.getInstanceId());

	}

	public void applyMods() {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		TempModSet mods = owner.getChar().getTempMods();
		ApplyModsDialog amd = new ApplyModsDialog(this,  (TempModSet)mods.clone(),
				f.getInstanceId());

	}

	public void moveWeapon(int i) {
		int idx = weaponList.getSelectedIndex();
		Weapon f = (Weapon) weaponList.getSelectedValue();
		int order = f.getOrder();
		if ((i < 0 && order > 1)
				|| (i > 0 && order < owner.getChar().getWeapons().size())) {

			PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO();
			pwdb.updateOrder(f, "" + owner.getChar().getID(), i);
			owner.getChar().setWeapons(
					pwdb.getPlayerWeapons("" + owner.getChar().getID()));
			weaponList.setListData(owner.getChar().getWeapons());
			weaponList.setSelectedIndex(idx + i);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == weaponSelect) {

			Weapon w = (Weapon) weaponSelect.getSelectedItem();

			if (w.getRangedmelee().equals("Ranged")) {
				abilitySelect.setChooserType("ranged");
				abilitySelect2.setChooserType("ranged");
				abilitySelect3.setChooserType("ranged");
				abilitySelect4.setChooserType("ranged");
				abilitySelect5.setChooserType("ranged");
				abilitySelect6.setChooserType("ranged");
			} else {
				abilitySelect.setChooserType("melee");
				abilitySelect2.setChooserType("melee");
				abilitySelect3.setChooserType("melee");
				abilitySelect4.setChooserType("melee");
				abilitySelect5.setChooserType("melee");
				abilitySelect6.setChooserType("melee");
			}
			
		}
	}

	private void addWeapon() {
		Weapon f = (Weapon) ((Weapon) weaponSelect.getSelectedItem())
				.getClone();
		f.setBonus(weaponValue.getText());
		f.setUse((String) weaponUseChooser.getSelectedItem());

		f.setAbility1((WeaponAbilities) abilitySelect.getSelectedItem());
		f.setAbility2((WeaponAbilities) abilitySelect2.getSelectedItem());
		f.setAbility3((WeaponAbilities) abilitySelect3.getSelectedItem());
		f.setAbility4((WeaponAbilities) abilitySelect4.getSelectedItem());
		f.setAbility5((WeaponAbilities) abilitySelect5.getSelectedItem());
		f.setAbility6((WeaponAbilities) abilitySelect6.getSelectedItem());
	
		f.setMasterwork(masterwork.isSelected());
		
		f.setNotes(weaponNotes.getText());
		f.setQuantity(quantity.getText());
		f.setDisplaySheet(displaySheet.isSelected());
		f.setDisplayItem(displayItem.isSelected());

		f.setCritOverride(critOverride.isSelected());
		f.setCritStart(critStart.getText());
		f.setCritEnd(critEnd.getText());
		f.setCritMult(critMult.getText());
		f.setSizeOverride((String) weaponSize.getSelectedItem());
		f.setDamageOverride(damageOverride.getText());
		f.setMaterial((Materials) material.getSelectedItem());
		f.setUseMonk(useMonk.isSelected());
		f.setNamed(named.isSelected());
		f.setTrueName(trueName.getText());
		
		Vector<String> errors = f.validate();
		if (errors.size() > 0){
			String msg = "The following errors were encountered : \n\n";
			for (int i = 0; i < errors.size(); i++){
				msg += (String)errors.get(i);
				msg += "\n\n";
			}
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",msg);
		}else{
		
		PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO();
		f.setId("" + pwdb.addPlayerWeapons(f, "" + owner.getChar().getID()));

		owner.getChar().setWeapons(
				pwdb.getPlayerWeapons("" + owner.getChar().getID()));
		weaponList.setListData(owner.getChar().getWeapons());
		}
	}

	private void saveWeapon() {
		int idx = weaponList.getSelectedIndex();
		Weapon old = (Weapon) weaponList.getSelectedValue();
		Weapon f = (Weapon) ((Weapon) weaponSelect.getSelectedItem())
				.getClone();
		f.setOrder(old.getOrder());

		f.setBonus(weaponValue.getText());
		f.setUse((String) weaponUseChooser.getSelectedItem());
		f.setInstanceId(((Weapon) weaponList.getSelectedValue())
				.getInstanceId());
		f.setMasterwork(masterwork.isSelected());
		f.setNotes(weaponNotes.getText());
		f.setAbility1((WeaponAbilities) abilitySelect.getSelectedItem());
		f.setAbility2((WeaponAbilities) abilitySelect2.getSelectedItem());
		f.setAbility3((WeaponAbilities) abilitySelect3.getSelectedItem());
		f.setAbility4((WeaponAbilities) abilitySelect4.getSelectedItem());
		f.setAbility5((WeaponAbilities) abilitySelect5.getSelectedItem());
		f.setAbility6((WeaponAbilities) abilitySelect6.getSelectedItem());
		f.setCritOverride(critOverride.isSelected());
		f.setCritStart(critStart.getText());
		f.setCritEnd(critEnd.getText());
		f.setCritMult(critMult.getText());
		f.setDamageOverride(damageOverride.getText());
		f.setSizeOverride((String) weaponSize.getSelectedItem());
		f.setUseMonk(useMonk.isSelected());
		f.setDisplaySheet(displaySheet.isSelected());
		f.setDisplayItem(displayItem.isSelected());
		f.setQuantity(quantity.getText());
		f.setMaterial((Materials) material.getSelectedItem());
		f.setNamed(named.isSelected());
		f.setTrueName(trueName.getText());
		Vector<String> errors = f.validate();
		if (errors.size() > 0){
			String msg = "The following errors were encountered : \n\n";
			for (int i = 0; i < errors.size(); i++){
				msg += (String)errors.get(i);
				msg += "\n\n";
			}
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",msg);
		}else{
			PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO();
			pwdb.updatePlayerWeapons(f, "" + owner.getChar().getID());
			owner.getChar().setWeapons(
				pwdb.getPlayerWeapons("" + owner.getChar().getID()));
			weaponList.setListData(owner.getChar().getWeapons());
			weaponList.setSelectedIndex(idx);
		}
	}

	private void removeWeapon() {

		Weapon w = (Weapon) weaponList.getSelectedValue();
		PlayerWeaponsDAO pwdb = new PlayerWeaponsDAO();
		pwdb.deletePlayerWeapons(w);
		owner.getChar().setWeapons(
				pwdb.getPlayerWeapons("" + owner.getChar().getID()));
		weaponList.setListData(owner.getChar().getWeapons());
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void updateCalcs() {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		rangedHit = owner.getChar().calcRangedToHit(f);
		babLabel.setText("BAB : " + owner.getChar().getBaseAttackBonus());
		hitLabel.setText("Ranged : " + rangedHit.getDisplayValue());
		rangedCalcList.setListData(rangedHit.getListElements());
		meleeHit = owner.getChar().calcMeleeToHit(f);
		hitLabel2.setText("Melee : " + meleeHit.getDisplayValue());
		calcList.setListData(meleeHit.getListElements());
		damage = owner.getChar().calcDamage(f);
		critRangeCalc = owner.getChar().calcCritRange(f);
		critRangeList.setListData(critRangeCalc.getListElements());
		critMultCalc = owner.getChar().calcCritMult(f);
		critMultList.setListData(critMultCalc.getListElements());
		critLabel.setText(critRangeCalc.getDisplayValue()+"/"+critMultCalc.getDisplayValue());
		
		damageLabel.setText("Damage : " + damage.getDisplayValue());
		avgDamageLabel.setText("Avg Damage AC 20 : "
				+ owner.getChar().calcAverageDamage(f, 20));
		calcDamageLabel.setText("Total Damage AC 20 : "
				+ owner.getChar().calcAverageDamage(20));
		damageCalcList.setListData(damage.getListElements());
	}

	public void addWeaponFeat(WeaponViews wv) {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		f.applyFeat(wv);
	}

	public void removeWeaponFeat(WeaponViews wv) {
		Weapon f = (Weapon) weaponList.getSelectedValue();
		f.removeFeat(wv);
	}

	public void valueChanged(ListSelectionEvent e) {

		// this takes a second, so pop up a wait cursor
		setCursor(new Cursor(Cursor.WAIT_CURSOR));

		// a list kicks off two change events if you click on an item. Make sure
		// that we only go to all this trouble on the second one
		if (!e.getValueIsAdjusting()) {
			try {
				// get the weapon selected
				Weapon f = (Weapon) weaponList.getSelectedValue();
				if (f != null) {
					weaponSelect.setSelectedWeapon(f);
					weaponValue.setText(f.getBonus());
					weaponNotes.setText(f.getNotes());
					critOverride.setSelected(f.isCritOverride());
					critStart.setText(f.getCritStart());
					critEnd.setText(f.getCritEnd());
					critMult.setText(f.getCritMult());
					rangedHit = owner.getChar().calcRangedToHit(f);
					hitLabel.setText("Ranged : " + rangedHit.getDisplayValue());
					rangedCalcList.setListData(rangedHit.getListElements());
					meleeHit = owner.getChar().calcMeleeToHit(f);
					hitLabel2.setText("Melee : " + meleeHit.getDisplayValue());
					calcList.setListData(meleeHit.getListElements());
					damage = owner.getChar().calcDamage(f);
					damageLabel.setText("Damage : " + damage.getDisplayValue());
					avgDamageLabel.setText("Avg Damage AC 20 : "
							+ owner.getChar().calcAverageDamage(f, 20));
					calcDamageLabel.setText("Total Damage AC 20 : "
							+ owner.getChar().calcAverageDamage(20));
					damageCalcList.setListData(damage.getListElements());
					sizeLabel.setText("" + f.getSize());
					weaponSize.setSelectedItem(f.getSizeOverride());
					baseDamageLabel.setText(f.getDamage());
					damageOverride.setText(f.getDamageOverride());
					useMonk.setSelected(f.useMonk());
					displaySheet.setSelected(f.isDisplaySheet());
					displayItem.setSelected(f.isDisplayItem());
					quantity.setText(f.getQuantity());
					material.setSelectedMaterial(f.getMaterial());
					critRangeCalc = owner.getChar().calcCritRange(f);
					critRangeList.setListData(critRangeCalc.getListElements());
					critMultCalc = owner.getChar().calcCritMult(f);
					critMultList.setListData(critMultCalc.getListElements());
					critLabel.setText(critRangeCalc.getDisplayValue()+"/"+critMultCalc.getDisplayValue());
					named.setSelected(f.isNamed());
					trueName.setText(f.getTrueName());
					abilityBonusLabel.setText("Ability Bonus : +"+f.getAbilityBonus());
					if ((f.getAbilityBonus() > 6) || (!f.isNamed() && f.getAbilityBonus() > 5)){
						abilityBonusLabel.setForeground(Color.red);
					}else{
						abilityBonusLabel.setForeground(Color.black);
					}
					if (f.getUse() != null) {
						weaponUseChooser.setSelectedItem(f.getUse());
					}
					if (f.getFeatClass().equals("Unarmed")) {
						useMonk.setEnabled(true);
					} else {
						useMonk.setEnabled(false);
					}
					if (f.getAbility1() != null
							&& f.getAbility1().getName() != null) {
						abilitySelect.setSelectedAbility(f.getAbility1());
					}
					if (f.getAbility2() != null
							&& f.getAbility2().getName() != null) {
						abilitySelect2.setSelectedAbility(f.getAbility2());
					}
					if (f.getAbility3() != null
							&& f.getAbility3().getName() != null) {
						abilitySelect3.setSelectedAbility(f.getAbility3());
					}
					if (f.getAbility4() != null
							&& f.getAbility4().getName() != null) {
						abilitySelect4.setSelectedAbility(f.getAbility4());
					}
					if (f.getAbility5() != null
							&& f.getAbility5().getName() != null) {
						abilitySelect5.setSelectedAbility(f.getAbility5());
					}
					if (f.getAbility6() != null
							&& f.getAbility6().getName() != null) {
						abilitySelect6.setSelectedAbility(f.getAbility6());
					}
					masterwork.setSelected(f.isMasterwork());
				}
			} catch (Exception ex) {
				logger.log("error","Error in list change " + ex);
				ex.printStackTrace();
			}
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}



	public boolean isSmallScreen() {
		return smallScreen;
	}



	public void setSmallScreen(boolean smallScreen) {
		this.smallScreen = smallScreen;
	}
}
