package initcheck.character;

import initcheck.DCharacter;
import initcheck.InitBase;
import initcheck.InitColor;
import initcheck.InitImage;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.PlayerManager;
import initcheck.character.printsheets.PrintDialog;
import initcheck.character.printsheets.SpellSheetPrintDialog;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TestTabUI;
import initcheck.graphics.TiledDialog;
import initcheck.utils.XTabbedPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.looks.plastic.PlasticTabbedPaneUI;

public class PlayerStatDialog extends TiledDialog implements Skinnable {
	private InitLogger logger = new InitLogger(this);
	
	private static final long serialVersionUID = 1L;

	private PanelButton okButton = null;

	private PanelButton cancelButton = new PanelButton("Close", 120);

	private PanelButton printButton = new PanelButton("Print Sheet", 120);

	private PanelButton printSpellsButton = new PanelButton("Print Spells", 120);

	private PanelButton battleButton = new PanelButton("Battle Stats", 120);

	private InfoPanel infoPanel;

	private StatPanel statPanel;

	private SavePanel savePanel;

	private PlayerFeatPanel featPanel;

	private PlayerSkillPanel skillPanel;

	private PlayerWeaponPanel weaponPanel;

	private PlayerAmmoPanel ammoPanel;

	private PlayerItemPanel itemPanel;

	private PlayerArmorPanel armorPanel;

	private PlayerLanguagePanel languagePanel;

	private PlayerClassPanel classPanel;

	private PlayerNotesPanel playerNotesPanel;

	private PlayerTempModPanel playerTempModPanel;

	private PlayerClassFeaturePanel playerClassFeaturePanel;

	private PlayerJournalPanel playerJournalPanel;
	
	private MovementPanel movementPanel;

	private PlayerSpellPanel spellPanel;

	PrintDialog pd;

	SpellSheetPrintDialog spd;

	BattleDialog bd;

	private Object currentTab;

	private DCharacter c;

	public static final int ADD = 1;

	public static final int MOD = 2;
	
	public static final int VIEW_ONLY = 3;
	
	InitBase owner;

	private XTabbedPane tabPane = new XTabbedPane();

	private Skin skin;

	public PlayerStatDialog(final InitBase owner, final DCharacter c,
			final int i, final int mode) {

		super(owner.getFrame(), "Player Stats for " + c.getName(), false, false);
		this.c = c;
		this.owner = owner;

		infoPanel = new InfoPanel(this, c);
		statPanel = new StatPanel(this, c);
		//playerHitPointPanel = new PlayerHitPointPanel(this, c);
		savePanel = new SavePanel(this, c);
		featPanel = new PlayerFeatPanel(this, c);
		skillPanel = new PlayerSkillPanel(this, c);
		weaponPanel = new PlayerWeaponPanel(this, c);
		ammoPanel = new PlayerAmmoPanel(this, c);
		itemPanel = new PlayerItemPanel(this, c);
		armorPanel = new PlayerArmorPanel(this, c);
		languagePanel = new PlayerLanguagePanel(this, c);
		classPanel = new PlayerClassPanel(this, c);
		playerNotesPanel = new PlayerNotesPanel(this, c);
		playerClassFeaturePanel = new PlayerClassFeaturePanel(this, c);
		playerJournalPanel = new PlayerJournalPanel(this, c);
		playerTempModPanel = new PlayerTempModPanel(this, c);
		movementPanel = new MovementPanel(this, c);
		spellPanel = new PlayerSpellPanel(this);

		currentTab = infoPanel;

		if (mode == ADD) {
			okButton = new PanelButton("Add");
		} else {
			okButton = new PanelButton("Save Changes", 120);
		}

		JPanel buttons = new JPanel();
		buttons.add(cancelButton);
		if (mode != VIEW_ONLY) {
			buttons.add(okButton);
			buttons.add(printButton);
			buttons.add(printSpellsButton);
		}
		buttons.add(battleButton);

		buttons.setOpaque(false);
		setButtonBar(buttons);

		tabPane.setOpaque(false);
		tabPane.addTab("Race/Info", infoPanel);
		tabPane.addTab("Stats", statPanel);
		tabPane.addTab("Classes", classPanel);
		//tabPane.addTab("HP", playerHitPointPanel);
		tabPane.addTab("Skills", skillPanel);
		tabPane.addTab("Feats", featPanel);
		tabPane.addTab("Armor", armorPanel);
		tabPane.addTab("Saves", savePanel);
		tabPane.addTab("Weapons", weaponPanel);
		tabPane.addTab("Items", itemPanel);
		tabPane.addTab("Ammo", ammoPanel);
		tabPane.addTab("Languages", languagePanel);
		tabPane.addTab("Notes", playerNotesPanel);
		tabPane.addTab("Temp Mods", playerTempModPanel);
		tabPane.addTab("Movement", movementPanel);
		tabPane.addTab("Class Features", playerClassFeaturePanel);
		tabPane.addTab("Journal", playerJournalPanel);
		tabPane.addTab("Magic", spellPanel);
		checkUpdatesRequired();
		setMainWindow(tabPane);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				if (mode == ADD) {
					((PlayerManager)owner).addCharacter(c);
				} else {
					((PlayerManager)owner).modCharacter(c, i);
				}
				if (pd != null) {
					pd.refreshCharacter();
				}
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrintDialog();
			}
		});

		printSpellsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPrintSpellsDialog();
			}
		});

		battleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBattleDialog();
			}
		});

		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				processTabChange(e);
			}
		});

	}

	public void setSkin(Skin s){
		
		if (skin == null || !s.getName().equals(skin.getName())){
			
			invalidate();
			
			if (!s.isUseGraphics()){				
				tabPane.setUI(new PlasticTabbedPaneUI());			
				tabPane.setForeground(Color.BLACK);				
			}else{
				
				tabPane.setForeground(InitColor.woodText);
			}
				
			for (int i = 0; i < tabPane.getTabCount(); i++){
				Component c = tabPane.getComponentAt(i);
				try {
					Skinnable sk = (Skinnable)c;
					sk.setSkin(s);
				}catch(ClassCastException ce){
					
				}
			}
	        validate();	        
	        skin = s;
		}
	}
	
	public void checkUpdatesRequired() {
		int i = 0;
		try {
			for (i = 0; i < tabPane.getTabCount(); i++) {
				StatusTab tab = (StatusTab) tabPane.getComponentAt(i);

				if (tab.isError()){
					tabPane.setIconAt(i,InitImage.exclamIcon);
				}else{
					tabPane.setIconAt(i, null);
				}
				
				if (tab.isUpdateRequired()) {
					
					
					tabPane.setForegroundAt(i, InitColor.red);
					
				} else {
					
					tabPane.setForegroundAt(i, Color.black);
					
				}
			}
		} catch (ClassCastException cce) {
			logger.log("error", "class cast exception!");	
		}
	}

	public DCharacter getChar() {
		return c;
	}

	public void setCharacter(DCharacter newCharacter) {
		c = newCharacter;
	}

	public void showPrintDialog() {
		pd = new PrintDialog(this, c);
	}

	public void showPrintSpellsDialog() {
		spd = new SpellSheetPrintDialog(this, c);
	}

	public void showBattleDialog() {
		bd = new BattleDialog(this, c);
	}

	// track the tab changes, because updating one tab may need to force updates
	// in other tabs. When the tab changes, trigger the necessary updates for
	// the tab
	// that you are leaving.
	public void processTabChange(ChangeEvent e) {
		if (currentTab != null) {
			if (currentTab == statPanel) {
				// update the saves based on the new stats
				savePanel.updateAbility();
				// update the available skill ranks based on new intel.
				skillPanel.updateAbility();
				// update the init and hp values base on new dex/con
				infoPanel.updateAbility();
				// update the hp values
				//playerHitPointPanel.updateHPLabel();
				movementPanel.updateCharacter();
				classPanel.updateLabels();
			} else if (currentTab == infoPanel) {

			} else if (currentTab == classPanel) {
				// only update the panels if something has changed.
				if (classPanel.isDirty()) {
					// update the saves based on the new class list
					c.getSaves().updateSaves(c);
					savePanel.updateCharacter();

					// update the feats based on the new class list
					featPanel.updateFeatsRemaining();

					// update the skills based on the new class list
					skillPanel.updateCharacter();

					// update stat points remaining based on new level
					statPanel.updateTotals();

					// update the cost to next level box
					infoPanel.updateLevelup();

					// mark the updates as being made
					classPanel.setDirty(false);
				}
			} else if (currentTab == savePanel) {

			} else if (currentTab == featPanel) {
				// update temp mod panel for any temp mods added or removed due
				// to linked feats.
				playerTempModPanel.updateTempMods();
				armorPanel.updateArmorCalc();
			} else if (currentTab == skillPanel) {

			} else if (currentTab == weaponPanel) {
				itemPanel.updateList();
			} else if (currentTab == ammoPanel) {

			} else if (currentTab == itemPanel) {
				playerTempModPanel.updateTempMods();
			} else if (currentTab == armorPanel) {
				movementPanel.updateCharacter();
			} else if (currentTab == languagePanel) {

			} else if (currentTab == playerNotesPanel) {
				c.setPlayerNotes(playerNotesPanel.getNotes());
			} else if (currentTab == playerTempModPanel) {

				armorPanel.updateArmorCalc();
				savePanel.updateCharacter();
				statPanel.updateTotals();
			}
		}
		currentTab = tabPane.getSelectedComponent();
	}

}
