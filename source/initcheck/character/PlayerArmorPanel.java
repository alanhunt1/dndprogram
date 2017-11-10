package initcheck.character;

import initcheck.DCharacter;
import initcheck.DescriptionDialog;
import initcheck.DescriptionListDialog;
import initcheck.InitImage;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.TransparentButton;
import initcheck.character.chooser.ArmorAbilityChooser;
import initcheck.character.chooser.ArmorChooser;
import initcheck.character.chooser.MaterialChooser;
import initcheck.character.chooser.ShieldChooser;
import initcheck.character.itembuilder.ItemBuilderDialog;
import initcheck.database.Ability;
import initcheck.database.Armor;
import initcheck.database.ArmorAbilities;
import initcheck.database.Materials;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledPanel;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PlayerArmorPanel extends TiledPanel implements FocusListener,
		StatusTab {

	private static final long serialVersionUID = 1L;

	private TiledList featList = new TiledList();

	private JScrollPane featScroll = new JScrollPane(featList);

	private TiledList restAcList = new TiledList();
	
	private JScrollPane restScroll = new JScrollPane(restAcList);
	
	private TiledList touchAcList = new TiledList();
	
	private JScrollPane touchScroll = new JScrollPane(touchAcList);
	
	private TiledList restTouchAcList = new TiledList();
	
	private JScrollPane restTouchScroll = new JScrollPane(restTouchAcList);
	
	private TiledList checkList = new TiledList();

	private JScrollPane checkScroll = new JScrollPane(checkList);

	private TiledList profList = new TiledList();

	private JScrollPane profScroll = new JScrollPane(profList);

	private ArmorChooser armorChooser = new ArmorChooser();

	private ShieldChooser shieldChooser = new ShieldChooser();

	private ArmorChooser restArmorChooser = new ArmorChooser();

	private JTextField armorBonus = new JTextField(2);

	private JTextField shieldBonus = new JTextField(2);

	private JTextField restArmorBonus = new JTextField(2);

	private JTextField naturalArmor = new JTextField(5);

	private JTextField miscArmor = new JTextField(5);

	private JTextField armorText = new JTextField(20);

	private JTextField shieldText = new JTextField(20);

	private JTextField restArmorText = new JTextField(20);

	private JTextField miscArmorText = new JTextField(20);

	private JCheckBox masterwork = new JCheckBox("MW");

	private JCheckBox shieldMasterwork = new JCheckBox("MW");

	private JCheckBox restMasterwork = new JCheckBox("MW");

	private JLabel acLabel;
	private JLabel restLabel;
	private JLabel touchLabel;
	private JLabel checkLabel;

	private MaterialChooser armorMaterial = new MaterialChooser();

	private MaterialChooser shieldMaterial = new MaterialChooser();

	private MaterialChooser restMaterial = new MaterialChooser();

	private ArmorAbilityChooser armorAb1 = new ArmorAbilityChooser("ARMOR");

	private ArmorAbilityChooser shieldAb1 = new ArmorAbilityChooser("SHIELD");

	private ArmorAbilityChooser restAb1 = new ArmorAbilityChooser("ARMOR");

	private ArmorAbilityChooser armorAb2 = new ArmorAbilityChooser("ARMOR");

	private ArmorAbilityChooser shieldAb2 = new ArmorAbilityChooser("SHIELD");

	private ArmorAbilityChooser restAb2 = new ArmorAbilityChooser("ARMOR");

	private PanelButton armorUpgrade = new PanelButton("Upgrade");

	private PanelButton shieldUpgrade = new PanelButton("Upgrade");

	private PanelButton restArmorUpgrade = new PanelButton("Upgrade");

	private TransparentButton armorButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton shieldButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton restButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton armorMatButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton shieldMatButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton restMatButton = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton armorAb1Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton shieldAb1Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton restAb1Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton armorAb2Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton shieldAb2Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton restAb2Button = new TransparentButton(InitImage.qmarkIcon);
	
	private TransparentButton restCalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton acCalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton touchCalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton checkCalcButton = new TransparentButton(InitImage.qmarkIcon);
	private TransparentButton profButton = new TransparentButton(InitImage.qmarkIcon);
	private boolean smallScreen = true;
	boolean updateRequired;

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

	private final PlayerStatDialog owner;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints cn = new GridBagConstraints();

	public PlayerArmorPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

		masterwork.setOpaque(false);
		restMasterwork.setOpaque(false);
		shieldMasterwork.setOpaque(false);
		masterwork.addFocusListener(this);
		restMasterwork.addFocusListener(this);
		shieldMasterwork.addFocusListener(this);
		armorMaterial.addFocusListener(this);
		shieldMaterial.addFocusListener(this);
		restMaterial.addFocusListener(this);
		armorAb1.addFocusListener(this);
		shieldAb1.addFocusListener(this);
		restAb1.addFocusListener(this);
		armorAb2.addFocusListener(this);
		shieldAb2.addFocusListener(this);
		restAb2.addFocusListener(this);

		Calculation armorCheck = owner.getChar().getArmorCheckPenalty();
		Calculation armorCalc = owner.getChar().getArmorCalc();
		Calculation restArmorCalc = owner.getChar().getRestArmorCalc();
		Calculation touchArmorCalc = owner.getChar().getTouchCalc();
		Calculation restTouchArmorCalc = owner.getChar().getRestTouchCalc();
		
		featList.setListData(armorCalc.getListElements());
		profList.setListData(owner.getChar().getArmorProficiencies());
		checkList.setListData(armorCheck.getListElements());
		restAcList.setListData(restArmorCalc.getListElements());
		touchAcList.setListData(touchArmorCalc.getListElements());
		restTouchAcList.setListData(restTouchArmorCalc.getListElements());
		
		featList.setCellRenderer(new GenericListCellRenderer());
		profList.setCellRenderer(new GenericListCellRenderer());
		checkList.setCellRenderer(new GenericListCellRenderer());
		restAcList.setCellRenderer(new GenericListCellRenderer());
		touchAcList.setCellRenderer(new GenericListCellRenderer());
		restTouchAcList.setCellRenderer(new GenericListCellRenderer());
		
		checkList.setVisibleRowCount(3);
		profList.setVisibleRowCount(3);
		
		
		setLayout(gridbag);
		cn.fill = GridBagConstraints.BOTH;
		cn.ipadx = 10;
		cn.ipady = 10;

		int ypos = 0;

		JPanel headerPanel = new JPanel();
		
		acLabel = new JLabel("AC " + armorCalc.getDisplayValue());
		restLabel = new JLabel("Rest "	+ owner.getChar().getRestAc());
		touchLabel = new JLabel("Touch "+ owner.getChar().getTouchAc());
		checkLabel = new JLabel(" Check Penalty " + armorCheck.getDisplayValue());
		JLabel profLabel = new JLabel(" Proficient ");
		acLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		restLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		checkLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		touchLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		
		headerPanel.add(acLabel);
		headerPanel.add(acCalcButton);
		headerPanel.add(restLabel);
		headerPanel.add(restCalcButton);
		headerPanel.add(touchLabel);
		headerPanel.add(touchCalcButton);
		headerPanel.add(checkLabel);
		headerPanel.add(checkCalcButton);
		headerPanel.add(profLabel);
		headerPanel.add(profButton);
		setBorder(BorderFactory.createEtchedBorder());

		doLayout(headerPanel, 0, ypos, 6, 1);
		ypos++;

		cn.weightx = 0.25;
		GridPanel armorPanel = new GridPanel(false);
		armorPanel.setOpaque(false);
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(new JLabel("Armor"));
		topPanel.add(armorUpgrade);
		armorPanel.doLayout(topPanel, 0, armorPanel.ypos);
		armorPanel.incYPos();
		armorPanel.doLayout(armorChooser, 0, armorPanel.ypos);
		armorPanel.doLayout(armorButton, 1, armorPanel.ypos);
		armorPanel.incYPos();
		JPanel armorSubPanel1 = new JPanel();
		armorSubPanel1.setOpaque(false);

		armorChooser.addFocusListener(this);

		armorSubPanel1.add(new JLabel(" + "));
		armorSubPanel1.add(armorBonus);
		armorBonus.addFocusListener(this);
		armorSubPanel1.add(masterwork);
		armorPanel.doLayout(armorSubPanel1, 0, armorPanel.ypos);
		armorPanel.incYPos();
		armorPanel.doLayout(armorMaterial, 0, armorPanel.ypos);
		armorPanel.doLayout(armorMatButton, 1, armorPanel.ypos);
		armorPanel.incYPos();
		armorPanel.doLayout(new JLabel(""), 0, armorPanel.ypos);
		armorPanel.incYPos();
		armorPanel.doLayout(armorAb1, 0, armorPanel.ypos);
		armorPanel.doLayout(armorAb1Button, 1, armorPanel.ypos);
		armorPanel.incYPos();
		armorPanel.doLayout(armorAb2, 0, armorPanel.ypos);
		armorPanel.doLayout(armorAb2Button, 1, armorPanel.ypos);
		armorPanel.incYPos();
		JPanel armorNotesPanel = new JPanel();
		armorNotesPanel.setOpaque(false);
		armorNotesPanel.add(new JLabel(" Notes "));
		armorNotesPanel.add(armorText);
		armorPanel.doLayout(armorNotesPanel, 0, armorPanel.ypos);
		armorText.addFocusListener(this);
		Armor a = owner.getChar().getArmor();

		if (a != null) {
			for (int i = 0; i < armorChooser.getItemCount(); i++) {
				Armor ca = (Armor) armorChooser.getItemAt(i);
				if (ca.getId().equals(a.getId())) {
					armorChooser.setSelectedIndex(i);
				}
			}
			armorBonus.setText(a.getBonus());
			armorText.setText(a.getNotes());
			masterwork.setSelected(a.isMasterwork());
			armorMaterial.setSelectedMaterial(a.getMaterial());
			armorAb1.setSelectedAbility(a.getAbility1());
			armorAb2.setSelectedAbility(a.getAbility2());

		}else{
			for (int i = 0; i < armorChooser.getItemCount(); i++) {
				Armor ca = (Armor) armorChooser.getItemAt(i);
				if ("No Armor".equalsIgnoreCase(ca.getName())){
					armorChooser.setSelectedIndex(i);
				}
			}
		}
		doLayout(armorPanel, 0, ypos);

		GridPanel shieldPanel = new GridPanel(false);
		shieldPanel.setOpaque(false);
		JPanel topPanel2 = new JPanel();
		topPanel2.setOpaque(false);
		topPanel2.add(new JLabel("Shield"));
		topPanel2.add(shieldUpgrade);
		shieldPanel.doLayout(topPanel2, 0, shieldPanel.ypos);
		shieldPanel.incYPos();
		shieldPanel.doLayout(shieldChooser, 0, shieldPanel.ypos);
		shieldPanel.doLayout(shieldButton, 1, shieldPanel.ypos);
		shieldPanel.incYPos();

		JPanel shieldSubPanel1 = new JPanel();
		shieldSubPanel1.setOpaque(false);

		shieldChooser.addFocusListener(this);
		shieldSubPanel1.add(new JLabel(" + "));
		shieldSubPanel1.add(shieldBonus);
		shieldBonus.addFocusListener(this);
		shieldSubPanel1.add(shieldMasterwork);
		shieldPanel.doLayout(shieldSubPanel1, 0, shieldPanel.ypos);
		shieldPanel.incYPos();
		shieldPanel.doLayout(shieldMaterial, 0, shieldPanel.ypos);
		shieldPanel.doLayout(shieldMatButton, 1, shieldPanel.ypos);
		shieldPanel.incYPos();
		shieldPanel.doLayout(new JLabel(""), 0, shieldPanel.ypos);
		shieldPanel.incYPos();
		shieldPanel.doLayout(shieldAb1, 0, shieldPanel.ypos);
		shieldPanel.doLayout(shieldAb1Button, 1, shieldPanel.ypos);
		shieldPanel.incYPos();
		shieldPanel.doLayout(shieldAb2, 0, shieldPanel.ypos);
		shieldPanel.doLayout(shieldAb2Button, 1, shieldPanel.ypos);
		shieldPanel.incYPos();
		JPanel shieldNotesPanel = new JPanel();
		shieldNotesPanel.setOpaque(false);
		shieldNotesPanel.add(new JLabel(" Notes "));
		shieldNotesPanel.add(shieldText);
		shieldPanel.doLayout(shieldNotesPanel, 0, shieldPanel.ypos);
		shieldText.addFocusListener(this);
		a = owner.getChar().getShield();

		if (a != null) {
			for (int i = 0; i < shieldChooser.getItemCount(); i++) {
				Armor ca = (Armor) shieldChooser.getItemAt(i);
				if (ca.getId().equals(a.getId())) {
					shieldChooser.setSelectedIndex(i);
				}
			}
			shieldBonus.setText(a.getBonus());
			shieldText.setText(a.getNotes());
			shieldMasterwork.setSelected(a.isMasterwork());
			shieldMaterial.setSelectedMaterial(a.getMaterial());
			shieldAb1.setSelectedAbility(a.getAbility1());
			shieldAb2.setSelectedAbility(a.getAbility2());

		}
		doLayout(shieldPanel, 1, ypos);

		GridPanel restArmorPanel = new GridPanel(false);
		restArmorPanel.setOpaque(false);
		JPanel topPanel3 = new JPanel();
		topPanel3.setOpaque(false);
		topPanel3.add(new JLabel("Rest Armor"));
		topPanel3.add(restArmorUpgrade);
		restArmorPanel.doLayout(topPanel3, 0, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		restArmorPanel.doLayout(restArmorChooser, 0, restArmorPanel.ypos);
		restArmorPanel.doLayout(restButton, 1, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		JPanel restArmorSubPanel1 = new JPanel();
		restArmorSubPanel1.setOpaque(false);

		restArmorChooser.addFocusListener(this);
		restArmorSubPanel1.add(new JLabel(" + "));
		restArmorSubPanel1.add(restArmorBonus);
		restArmorBonus.addFocusListener(this);
		restArmorSubPanel1.add(restMasterwork);
		restArmorPanel.doLayout(restArmorSubPanel1, 0, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		restArmorPanel.doLayout(restMaterial, 0, restArmorPanel.ypos);
		restArmorPanel.doLayout(restMatButton, 1, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		restArmorPanel.doLayout(new JLabel(""), 0, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		restArmorPanel.doLayout(restAb1, 0, restArmorPanel.ypos);
		restArmorPanel.doLayout(restAb1Button, 1, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		restArmorPanel.doLayout(restAb2, 0, restArmorPanel.ypos);
		restArmorPanel.doLayout(restAb2Button, 1, restArmorPanel.ypos);
		restArmorPanel.incYPos();
		JPanel restArmorNotesPanel = new JPanel();
		restArmorNotesPanel.setOpaque(false);
		restArmorNotesPanel.add(new JLabel(" Notes "));
		restArmorNotesPanel.add(restArmorText);
		restArmorPanel.doLayout(restArmorNotesPanel, 0, restArmorPanel.ypos);
		restArmorText.addFocusListener(this);
		a = owner.getChar().getRestArmor();

		if (a != null) {
			for (int i = 0; i < restArmorChooser.getItemCount(); i++) {
				Armor ca = (Armor) restArmorChooser.getItemAt(i);
				if (ca.getId().equals(a.getId())) {
					restArmorChooser.setSelectedIndex(i);
				}
			}
			restArmorBonus.setText(a.getBonus());
			restArmorText.setText(a.getNotes());
			restMasterwork.setSelected(a.isMasterwork());
			restMaterial.setSelectedMaterial(a.getMaterial());
			restAb1.setSelectedAbility(a.getAbility1());
			restAb2.setSelectedAbility(a.getAbility2());

		}
		doLayout(restArmorPanel, 2, ypos);
		
		if (!smallScreen) {
		GridPanel calcPanel = new GridPanel();
		calcPanel.setWeightX(1);
		calcPanel.setWeightY(1);
		calcPanel.setOpaque(false);
		calcPanel.doLayout(new JLabel("Check Penalty"));
		calcPanel.incYPos();
		calcPanel.doLayout(checkScroll);
		calcPanel.incYPos();
		calcPanel.doLayout(new JLabel("Armor Proficiency"));
		calcPanel.incYPos();
		calcPanel.doLayout(profScroll);
		doLayout(calcPanel, 3, ypos);
		}
		
		cn.weightx = 0;
		ypos++;

		JPanel natPanel = new JPanel();
		natPanel.setOpaque(false);

		natPanel.add(new JLabel("Natural"));
		natPanel.add(naturalArmor);
		naturalArmor.addFocusListener(this);
		naturalArmor.setText("" + owner.getChar().getNaturalArmor());
		doLayout(natPanel, 0, ypos);

		JPanel miscPanel = new JPanel();
		miscPanel.setOpaque(false);
		miscPanel.add(new JLabel("Misc"));
		miscPanel.add(miscArmor);
		miscArmor.addFocusListener(this);

		doLayout(miscPanel, 1, ypos);

		JPanel notesPanel = new JPanel();
		notesPanel.setOpaque(false);
		notesPanel.add(new JLabel(" Notes "));
		notesPanel.add(miscArmorText);
		miscArmorText.addFocusListener(this);
		miscArmor.setText("" + owner.getChar().getMiscArmor());
		miscArmorText.setText(owner.getChar().getMiscArmorText());

		doLayout(notesPanel, 2, ypos);

		ypos++;

		if (!smallScreen) {
		cn.weightx = 0.33;

		doLayout(new JLabel("AC Calculation"), 0, ypos);
		doLayout(new JLabel("Rest AC Calculation"), 1, ypos);
		doLayout(new JLabel("Touch AC Calculation"), 2, ypos);
		doLayout(new JLabel("Rest Touch AC Calculation"), 3, ypos);
		
		ypos++;
		cn.weighty = 1.0;
		doLayout(featScroll, 0, ypos);
		doLayout(restScroll, 1, ypos);
		doLayout(touchScroll, 2, ypos);
		doLayout(restTouchScroll, 3, ypos);
		}
		
		armorUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upgradeArmor();
			}
		});

		shieldUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upgradeShield();
			}
		});

		restArmorUpgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upgradeRestArmor();
			}
		});

		armorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showArmorDescription();
			}
		});
		
		restButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRestArmorDescription();
			}
		});
		
		shieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showShieldDescription();
			}
		});
		
		armorMatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showArmorMatDescription();
			}
		});
		
		restMatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRestArmorMatDescription();
			}
		});
		
		shieldMatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showShieldMatDescription();
			}
		});
		
		armorAb1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showArmorAb1Description();
			}
		});
		
		restAb1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRestArmorAb1Description();
			}
		});
		
		shieldAb1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showShieldAb1Description();
			}
		});
		
		armorAb2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showArmorAb1Description();
			}
		});
		
		restAb2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRestArmorAb1Description();
			}
		});
		
		shieldAb2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showShieldAb1Description();
			}
		});
		
		acCalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAcCalc();
			}
		});
		restCalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRestCalc();
			}
		});
		touchCalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showTouchCalc();
			}
		});
		checkCalcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCheckCalc();
			}
		});
		
		profButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showProf();
			}
		});
	}

	public void showArmorDescription(){
		Armor a = (Armor)armorChooser.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showShieldDescription(){
		Armor a = (Armor)shieldChooser.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showRestArmorDescription(){
		Armor a = (Armor)restArmorChooser.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showArmorMatDescription(){
		Materials a = (Materials)armorMaterial.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showShieldMatDescription(){
		Materials a = (Materials)shieldMaterial.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showRestArmorMatDescription(){
		Materials a = (Materials)restMaterial.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showArmorAb1Description(){
		ArmorAbilities a = (ArmorAbilities)armorAb1.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showShieldAb1Description(){
		ArmorAbilities a = (ArmorAbilities)shieldAb1.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showRestArmorAb1Description(){
		ArmorAbilities a = (ArmorAbilities)restAb1.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showArmorAb2Description(){
		ArmorAbilities a = (ArmorAbilities)armorAb2.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showShieldAb2Description(){
		ArmorAbilities a = (ArmorAbilities)shieldAb2.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void showRestArmorAb2Description(){
		ArmorAbilities a = (ArmorAbilities)restAb2.getSelectedItem();
		DescriptionDialog.display("Race Description", a.getFullDescription());
	}
	
	public void  showAcCalc(){
		DescriptionListDialog.display("Armor Class Calc", owner.getChar().getArmorCalc().getElements());
	}
	
	public void  showRestCalc(){
		DescriptionListDialog.display("Rest Armor Class Calc", owner.getChar().getRestArmorCalc().getElements());
	}
	
	public void  showTouchCalc(){
		DescriptionListDialog.display("Armor Class Calc", owner.getChar().getTouchCalc().getElements());
	}
	
	public void  showCheckCalc(){
		DescriptionListDialog.display("Armor Class Calc", owner.getChar().getArmorCheckPenalty().getElements());
	}
	
	public void  showProf(){
		DescriptionListDialog.display("Armor Proficiencies", owner.getChar().getArmorProficiencies());
	}
	
	public void upgradeArmor() {
		@SuppressWarnings("unused")
		ItemBuilderDialog ibd = new ItemBuilderDialog(getArmor());
	}

	public void upgradeShield() {
		@SuppressWarnings("unused")
		ItemBuilderDialog ibd = new ItemBuilderDialog(getShield());
	}

	public void upgradeRestArmor() {
		@SuppressWarnings("unused")
		ItemBuilderDialog ibd = new ItemBuilderDialog(getRestArmor());
	}

	public void updateArmorCalc() {
		Calculation armorCheck = owner.getChar().getArmorCheckPenalty();
		Calculation armorCalc = owner.getChar().getArmorCalc();
		Calculation restArmorCalc = owner.getChar().getRestArmorCalc();
		Calculation touchArmorCalc = owner.getChar().getTouchCalc();
		Calculation restTouchArmorCalc = owner.getChar().getRestTouchCalc();
		
		acLabel.setText("AC " + armorCalc.getDisplayValue());
		restLabel.setText("Rest "+ owner.getChar().getRestAc());
		touchLabel.setText("Touch "	+ owner.getChar().getTouchAc());
		checkLabel.setText("Check Penalty " + armorCheck.getDisplayValue());
		
		featList.setListData(armorCalc.getListElements());
		profList.setListData(owner.getChar().getArmorProficiencies());
		checkList.setListData(armorCheck.getListElements());
		restAcList.setListData(restArmorCalc.getListElements());
		touchAcList.setListData(touchArmorCalc.getListElements());
		restTouchAcList.setListData(restTouchArmorCalc.getListElements());
	}

	private void doLayout(Component comp, int x, int y) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = 1;
		cn.gridheight = 1;
		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	private void doLayout(Component comp, int x, int y, int width, int height) {
		cn.gridx = x;
		cn.gridy = y;
		cn.gridwidth = width;
		cn.gridheight = height;

		gridbag.setConstraints(comp, cn);
		add(comp);
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {

		if (e.getSource() == armorChooser || e.getSource() == armorBonus
				|| e.getSource() == armorText || e.getSource() == masterwork
				|| e.getSource() == armorMaterial || e.getSource() == armorAb1
				|| e.getSource() == armorAb2) {
			owner.getChar().setArmor(getArmor());
		} else if (e.getSource() == restArmorChooser
				|| e.getSource() == restArmorBonus
				|| e.getSource() == restArmorText
				|| e.getSource() == restMasterwork
				|| e.getSource() == restMaterial || e.getSource() == restAb1
				|| e.getSource() == restAb2) {
			owner.getChar().setRestArmor(getRestArmor());
		} else if (e.getSource() == shieldChooser
				|| e.getSource() == shieldBonus || e.getSource() == shieldText
				|| e.getSource() == shieldMasterwork
				|| e.getSource() == shieldMaterial
				|| e.getSource() == shieldAb1 || e.getSource() == shieldAb2) {
			owner.getChar().setShield(getShield());
		} else if (e.getSource() == naturalArmor) {
			owner.getChar().setNaturalArmor(getNaturalArmor());
		} else if (e.getSource() == miscArmor) {
			owner.getChar().setMiscArmor(getMiscArmor());
		} else if (e.getSource() == miscArmorText) {
			owner.getChar().setMiscArmorText(getMiscArmorText());
		}
		updateArmorCalc();
	}

	public Armor getArmor() {
	
		String id = "";
		
		if (owner.getChar().getArmor() != null){
			id = owner.getChar().getArmor().getInstanceId();
		}
		
		Armor a = (Armor) armorChooser.getSelectedItem();
		a.setPlayerId("" + owner.getChar().getID());
		a.setBonus(armorBonus.getText());
		a.setNotes(armorText.getText());
		a.setMasterwork(masterwork.isSelected());
		a.setInstanceId(id);
		a.setMaterial(((Materials) armorMaterial.getSelectedItem()));
		a.setAbility1((ArmorAbilities) armorAb1.getSelectedItem());
		a.setAbility2((ArmorAbilities) armorAb2.getSelectedItem());
		a.setAtRest("N");
		return a;
	}

	public Armor getRestArmor() {
		String id = "";
		if (owner.getChar().getRestArmor() != null){
			id = owner.getChar().getRestArmor().getInstanceId();
		}
		Armor a = (Armor) restArmorChooser.getSelectedItem();
		a.setPlayerId("" + owner.getChar().getID());
		a.setBonus(restArmorBonus.getText());
		a.setNotes(restArmorText.getText());
		a.setMasterwork(restMasterwork.isSelected());
		a.setInstanceId(id);
		a.setMaterial(((Materials) restMaterial.getSelectedItem()));
		a.setAbility1((ArmorAbilities) restAb1.getSelectedItem());
		a.setAbility2((ArmorAbilities) restAb2.getSelectedItem());
		a.setAtRest("Y");
		return a;
	}

	public Armor getShield() {
		String id = "";
		if (owner.getChar().getShield() != null){
			id = owner.getChar().getShield().getInstanceId();
		}
		
		Armor a = (Armor) shieldChooser.getSelectedItem();
		a.setPlayerId("" + owner.getChar().getID());
		a.setDisplayType("Shield");
		a.setBonus(shieldBonus.getText());
		a.setNotes(shieldText.getText());
		a.setMasterwork(shieldMasterwork.isSelected());
		a.setInstanceId(id);
		a.setMaterial(((Materials) shieldMaterial.getSelectedItem()));
		a.setAbility1((ArmorAbilities) shieldAb1.getSelectedItem());
		a.setAbility2((ArmorAbilities) shieldAb2.getSelectedItem());
		a.setAtRest("N");
		return a;
	}

	public int getNaturalArmor() {
		String na = naturalArmor.getText();
		int i = 0;
		try {
			if (!na.equals("")) {
				i = Integer.parseInt(na);
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter a number in the Natural Armor box, or leave it empty");
		}
		return i;
	}

	public int getMiscArmor() {
		String na = miscArmor.getText();
		int i = 0;
		try {
			if (!na.equals("")) {
				i = Integer.parseInt(na);
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter a number in the Misc Armor box, or leave it empty");
		}
		return i;
	}

	public String getMiscArmorText() {
		return miscArmorText.getText();
	}
}
