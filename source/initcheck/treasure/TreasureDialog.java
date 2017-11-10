package initcheck.treasure;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.InitColor;
import initcheck.InitImage;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.database.Ability;
import initcheck.database.Armor;
import initcheck.database.ArmorAbilities;
import initcheck.database.ArmorAbilitiesDAO;
import initcheck.database.InitDBC;
import initcheck.database.Item;
import initcheck.database.ListHeader;
import initcheck.database.Party;
import initcheck.database.PartyDAO;
import initcheck.database.Potion;
import initcheck.database.Ring;
import initcheck.database.Rods;
import initcheck.database.Scroll;
import initcheck.database.SpecificArmor;
import initcheck.database.SpecificWeapon;
import initcheck.database.Staffs;
import initcheck.database.Treasure;
import initcheck.database.TreasureItem;
import initcheck.database.Wand;
import initcheck.database.Weapon;
import initcheck.database.WeaponAbilities;
import initcheck.database.Wonderous;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListItem;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;
import initcheck.utils.StrManip;
import util.Rounding;

public class TreasureDialog extends TiledDialog implements
		ListSelectionListener {

	
	
	private static final long serialVersionUID = 1L;

	private InitServer owner;

	private TiledList bonusList = new TiledList();

	private PanelButton calculate = new PanelButton("Calculate", 80);

	private PanelButton reset = new PanelButton("Reset");

	private JScrollPane scroll = new JScrollPane(bonusList);

	private Random rnd = new Random();

	private TiledTextArea description = new TiledTextArea(5, 30);

	private JScrollPane descScroll = new JScrollPane(description);

	InitDBC db = new InitDBC();

	private JComboBox coinMod;

	private JComboBox goodMod;

	private JComboBox itemMod;

	private TiledPanel modPanel = new TiledPanel(InitImage.lightRocks);

	private JTabbedPane tabs = new JTabbedPane();

	private TiledPanel specificPanel = new TiledPanel(InitImage.lightRocks);

	private JComboBox treasureLevel = new JComboBox();

	private JComboBox treasureType = new JComboBox();

	private JTextField encounterLevel = new JTextField(5);
	
	private JTextField numMonsters = new JTextField(5);
	
	private JTextField monsterLevel = new JTextField(5);
	
	private PanelButton calcEl = new PanelButton("Calc");
	
	private TreasurePopupMenu popup = new TreasurePopupMenu(this);
	
	private Vector<TreasureItem> treasure = new Vector<TreasureItem>();
	
	public TreasureDialog(final InitServer owner) {

		super(owner.getFrame(), "Treasure Calculator", false);
		this.owner = owner;

		MouseListener popupListener = new PopupListener();
		bonusList.addMouseListener(popupListener);
		
		treasureLevel.addItem("mundane");
		treasureLevel.addItem("minor");
		treasureLevel.addItem("medium");
		treasureLevel.addItem("major");

		treasureType.addItem("armor");
		treasureType.addItem("weapons");
		treasureType.addItem("rings");
		treasureType.addItem("wands");
		treasureType.addItem("potions");
		treasureType.addItem("rods");
		treasureType.addItem("staffs");
		treasureType.addItem("scrolls");
		treasureType.addItem("wonderous");

		specificPanel.setLayout(new GridLayout(2, 2));
		specificPanel.add(new JLabel("Level"));
		specificPanel.add(treasureLevel);
		specificPanel.add(new JLabel("Type"));
		specificPanel.add(treasureType);

		Vector<String> modVector = new Vector<String>();
		modVector.add("None");
		modVector.add("Normal");
		modVector.add("Double");
		modVector.add("Triple");

		coinMod = new JComboBox(modVector);
		goodMod = new JComboBox(modVector);
		itemMod = new JComboBox(modVector);

		coinMod.setSelectedIndex(1);
		goodMod.setSelectedIndex(1);
		itemMod.setSelectedIndex(1);

		modPanel.setLayout(new GridLayout(3, 2));
		modPanel.add(new JLabel("Coins"));
		modPanel.add(coinMod);
		modPanel.add(new JLabel("Goods"));
		modPanel.add(goodMod);
		modPanel.add(new JLabel("Items"));
		modPanel.add(itemMod);

		tabs.addTab("Random       ", modPanel);
		tabs.addTab("Items        ", specificPanel);
		tabs.setOpaque(false);
	
		//tabs.setUI(new TestTabUI());
		tabs.setForeground(InitColor.woodText);
		
		bonusList.setFont(new Font("Courier", Font.PLAIN, 12));
		bonusList.addListSelectionListener(this);

		description.setLineWrap(true);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(calculate);
		buttonPanel.add(reset);
		buttonPanel.setOpaque(false);

		TiledGridPanel contents = new TiledGridPanel(InitImage.lightRocks);
		
		JPanel calcPanel = new JPanel();
		calcPanel.setOpaque(false);
		calcPanel.add(new JLabel("Encounter Level"));
		calcPanel.add(encounterLevel);
		calcPanel.add(new JLabel("Num"));
		calcPanel.add(numMonsters);
		calcPanel.add(new JLabel("CR"));
		calcPanel.add(monsterLevel);
		calcPanel.add(calcEl);
		
		contents.setWeightX(0);
		contents.setWeightY(0);
		contents.doLayout(calcPanel, 0, contents.ypos,2,1);
		contents.incYPos();
		contents.doLayout(tabs, 0, contents.ypos);
		contents.setWeightX(1);
		contents.setWeightY(1);
		contents.doLayout(scroll, 1, contents.ypos);
		contents.incYPos();
		
		
		//contents.doLayout(descScroll, 0, contents.ypos, 3, 1);
		
		JSplitPane split = new JSplitPane();
		split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split.setLeftComponent(contents);
		split.setRightComponent(descScroll);
		
		
		calculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCalc();
			}
		});

		calcEl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcEl();
			}
		});

		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doReset();
			}
		});

		calculateEncounterLevel();
		
		setMainWindow(split);
		setButtonBar(buttonPanel);
		setSize(500, 500);

		setVisible(true);
	}

	private void doReset() {

		bonusList.setListData(new Vector<TiledListItem>());
	}

	public void valueChanged(ListSelectionEvent e) {
		// if the list item implements the "Item" interface, then
		// put its description in the description box
		try {
			Item i = (Item) bonusList.getSelectedValue();
			description.setText(i.getDescription());
		} catch (Exception ex) {
			// it didn't implement item (this is a ClassCastException),
			// so do nothing.
		}
	}

	private void calcEl(){
		
		int num = Integer.parseInt(numMonsters.getText());
		int avgCr = Integer.parseInt(monsterLevel.getText());
		double crSum = 0;
		//		 calculate the encounter level of the fight.
		for (int i = 0; i < num; i++) {
			
			// get the cr for the monster
			double cr = new Double(avgCr);
			crSum += Math.pow(2, cr / 2);
		}
		double el = 2 * (Math.log(crSum) / Math.log(2));

		String elround = Rounding.toString(el, 0);

		
		// if it is greater than 20, set it to 20 and adjust the treasure
		// later.
		int elInt = Integer.parseInt(elround);

		// bump this value up some. This is CHEATING. Don't tell anyone.
		elInt += 2;

		
		if (elInt > 20) {
			
			elround = "20";
		}
		encounterLevel.setText(elround);
	}
	
	private void calculateEncounterLevel(){
		Vector<Vector<Object>> hsData = owner.getHitSheetData();
		if (hsData == null){
			encounterLevel.setText("0");
			return;
		}
		double crSum = 0;
		//		 calculate the encounter level of the fight.
		for (int i = 0; i < hsData.size(); i++) {
			// read in a row
			Vector<Object> entry = (Vector<Object>) hsData.get(i);

			// get the cr for the monster
			double cr = Double.parseDouble((String) entry.get(3));
			crSum += Math.pow(2, cr / 2);
		}
		double el = 2 * (Math.log(crSum) / Math.log(2));

		String elround = Rounding.toString(el, 0);

		
		// if it is greater than 20, set it to 20 and adjust the treasure
		// later.
		int elInt = Integer.parseInt(elround);

		// bump this value up some. This is CHEATING. Don't tell anyone.
		elInt += 2;

		
		if (elInt > 20) {
			
			elround = "20";
		}
		encounterLevel.setText(elround);
	}
	
	private void doCalc() {
		@SuppressWarnings("unused")
		double baseXP = 0;
		Vector<Vector<Object>> hsData = owner.getHitSheetData();
		String amt = "0";
		// int numPlayers = owner.getEngine().getDB().getCharacters().size();
		double crSum = 0;
		treasure = new Vector<TreasureItem>();
		
		int randCount = 0;
		bonusList.setCellRenderer(new TreasureListCellRenderer());

		if (tabs.getSelectedIndex() == 0) {
			// calculate the encounter level of the fight.
			for (int i = 0; i < hsData.size(); i++) {
				// read in a row
				Vector<Object> entry = (Vector<Object>) hsData.get(i);

				// get the cr for the monster
				double cr = Double.parseDouble((String) entry.get(3));
				crSum += Math.pow(2, cr / 2);
			}
			double el = 2 * (Math.log(crSum) / Math.log(2));

			String elround = Rounding.toString(el, 0);

			ListHeader lh = new ListHeader();
			lh.setText("ENCOUNTER LEVEL :" + elround);
			treasure.add(new Treasure(lh.getText()));

			elround = encounterLevel.getText();
			
			// if it is greater than 20, set it to 20 and adjust the treasure
			// later.
			int elInt = Integer.parseInt(elround);

			// bump this value up some. This is CHEATING. Don't tell anyone.
			elInt += 2;

			int elDiff = 0;
			if (elInt > 20) {
				elDiff = elInt - 20;
				elround = "20";
			}
			
			
			
			int bonusTreasure = 0;
			switch (elDiff) {
			case 1:
				bonusTreasure = 1;
				break;
			case 2:
				bonusTreasure = 2;
				break;
			case 3:
				bonusTreasure = 4;
				break;
			case 4:
				bonusTreasure = 6;
				break;
			case 5:
				bonusTreasure = 9;
				break;
			case 6:
				bonusTreasure = 12;
				break;
			case 7:
				bonusTreasure = 17;
				break;
			case 8:
				bonusTreasure = 23;
				break;
			case 9:
				bonusTreasure = 31;
				break;
			case 10:
				bonusTreasure = 42;
				break;
			default:

			}

			// now that we have the encounter level, we need to get the three
			// types of treasure for the fight.

			int goods = getRandom(100);
			int items = getRandom(100);

			ListHeader lh2 = new ListHeader();
			lh2.setText("Coins");
			treasure.add(new Treasure(lh2.getText()));

			for (int c = 0; c < coinMod.getSelectedIndex(); c++) {
				int coins = getRandom(100);

				// get the coins
				Treasure cTreasure = db.getTreasure(elround, "coins", ""
						+ coins);

				// find out the random amount
				amt = cTreasure.getAmount();
				randCount = 0;

				// if the amount has a "d" in it, then it is more than zero.
				if (amt.indexOf("d") > 0) {
					int numDice = Integer.parseInt(amt.substring(0, amt
							.indexOf("d")));
					int dieSize = Integer.parseInt(amt.substring(amt
							.indexOf("d") + 1, amt.length()));

					for (int i = 0; i < numDice; i++) {
						randCount += getRandom(dieSize);
					}
					treasure.add(new Treasure(randCount
							+ cTreasure.getTreasure().substring(1,
									cTreasure.getTreasure().length())));
				}
			}

			ListHeader lh3 = new ListHeader();
			lh3.setText("Goods");
			treasure.add(new Treasure(lh3.getText()));
			for (int c = 0; c < goodMod.getSelectedIndex(); c++) {

				// get the goods
				Treasure gTreasure = db.getTreasure(elround, "goods", ""
						+ goods);

				// find out the random amount
				amt = gTreasure.getAmount();
				randCount = 0;

				// if the amount has a "d" in it, then it is more than zero.
				if (amt.indexOf("d") > 0) {
					int numDice = Integer.parseInt(amt.substring(0, amt
							.indexOf("d")));
					int dieSize = Integer.parseInt(amt.substring(amt
							.indexOf("d") + 1, amt.length()));

					for (int i = 0; i < numDice; i++) {
						randCount += getRandom(dieSize);
					}

					// calculate out the value of the treasure in gold.
					int goldValue = 0;
					for (int j = 0; j < randCount; j++) {

						Treasure t = db.getGoodsTreasure("" + getRandom(100),
								gTreasure.getTreasure());

						amt = t.getAmount();
						numDice = Integer.parseInt(amt.substring(0, amt
								.indexOf("d")));
						dieSize = Integer.parseInt(amt.substring(amt
								.indexOf("d") + 1, amt.length()));
						int gp = Integer.parseInt(t.getTreasure().substring(0,
								t.getTreasure().indexOf(" ")));

						int dieCount = 0;
						for (int i = 0; i < numDice; i++) {
							dieCount += getRandom(dieSize);
						}
						goldValue += dieCount * gp;

					}
					treasure.add(new Treasure(randCount + " " + gTreasure.getTreasure() + " ("
							+ goldValue + " GP)"));
				}

			}
			ListHeader lh4 = new ListHeader();
			lh4.setText("Items");
			treasure.add(new Treasure(lh4.getText()));
			for (int c = 0; c < itemMod.getSelectedIndex(); c++) {

				// get the items
				Treasure iTreasure = db
						.getTreasure(elround, "item", "" + items);
				// find out the random amount
				amt = iTreasure.getAmount();
				randCount = 0;

				if (amt.indexOf("d") > 0) {
					int numDice = Integer.parseInt(amt.substring(0, amt
							.indexOf("d")));
					int dieSize = Integer.parseInt(amt.substring(amt
							.indexOf("d") + 1, amt.length()));
					for (int i = 0; i < numDice; i++) {
						randCount += getRandom(dieSize);
					}
				} else {
					randCount = Integer.parseInt(amt);
				}

				for (int i = 0; i < randCount; i++) {
					Vector<TreasureItem> itreas = getItems(1, iTreasure.getTreasure());
					treasure.addAll(itreas);
				}
				if (bonusTreasure > 0) {
					
					Vector<TreasureItem> bonusVector = getItems(bonusTreasure,
							"major");
					treasure.addAll(bonusVector);
				}
			}
		} else {
			Vector<TreasureItem> items = getItems(1, (String) treasureLevel.getSelectedItem(), 
					(String) treasureType.getSelectedItem());
			treasure.addAll(items);
		}
		bonusList.setListData(treasure);
	}

	public Vector<TreasureItem> getMundane(int num) {
		Vector<TreasureItem> v = new Vector<TreasureItem>();
		
		for (int i = 0; i < num; i++) {
			Treasure t = db.getMundaneTreasure("" + getRandom(100));

			String amt = t.getAmount();
			int numDice = Integer.parseInt(amt.substring(0, amt.indexOf("d")));
			int dieSize = Integer.parseInt(amt.substring(amt.indexOf("d") + 1,
					amt.length()));

			int dieCount = 0;
			for (int j = 0; j < numDice; j++) {
				dieCount += getRandom(dieSize);
			}

			if (t.getCategory().equals("armor")) {
				if (getRandom(100) < 11) {
					v.add(new Treasure(dieCount + " small " + t.getTreasure()));
				} else {
					v.add(new Treasure(dieCount + " medium " + t.getTreasure()));
				}
			} else if (t.getCategory().equals("mighty longbow")) {
				v.add(new Treasure(dieCount + " " + t.getTreasure() + "+" + getRandom(4)));
			} else if (t.getCategory().equals("mighty shortbow")) {
				v.add(new Treasure(dieCount + " " + t.getTreasure() + "+" + getRandom(2)));
			} else if (t.getCategory().equals("masterwork armor")) {
				if (getRandom(100) < 40) {
					v.add(new Treasure(dieCount + " small " + t.getTreasure()));
				} else {
					v.add(new Treasure(dieCount + " medium " + t.getTreasure()));
				}
			} else if (t.getCategory().equals("random melee")) {

				Vector<Weapon> melee = db.getMeleeWeapons();
				Weapon tres = (Weapon) melee.get(getRandom(melee.size() - 1));
				v.add(new Treasure(dieCount + " " + tres.getName()));
			} else if (t.getCategory().equals("random ranged")) {

				Vector<Weapon> melee = db.getRangedWeapons();
				Weapon tres = (Weapon) melee.get(getRandom(melee.size() - 1));
				v.add(new Treasure(dieCount + " " + tres.getName()));
			} else if (t.getCategory().equals("random exotic")) {

				Vector<Weapon> melee = db.getExoticWeapons();
				Weapon tres = (Weapon) melee.get(getRandom(melee.size() - 1));
				v.add(new Treasure(dieCount + " " + tres.getName()));
			} else {
				v.add(new Treasure(dieCount + " " + t.getTreasure()));
			}
		}
		return v;
	}

	public Armor getMagicArmor(String treasuretype) {
		// Vector<Object> v = new Vector<Object>();
		
		Armor a = db.getMagicArmor("" + getRandom(100), treasuretype);
		
		if (a.getType().equals("armor") || a.getType().equals("shield")) {

			
			String type = db.getArmorType("" + getRandom(100), a.getType());
			a.setName(type);
		} else if (a.getType().equals("ability")) {

			// we need to determine what we are adding the
			// ability to.
			while (a.getType().equals("ability")) {
				a = db.getMagicArmor("" + getRandom(100), treasuretype);

			}

			if (a.getType().equals("specific armor")
					|| a.getType().equals("specific shield")) {
				a.setType(a.getType().substring(a.getType().indexOf(" ") + 1,
						a.getType().length()));
				String rndarm = "" + getRandom(100);
				
				SpecificArmor ab = db.getSpecificArmor(rndarm, treasuretype, a
						.getType());
				a.setName(ab.getName());
				a.setDescription(ab.getDescription());

			} else {
				String type = db.getArmorType("" + getRandom(100), a.getType());
				a.setName(type);
			}

			
			String rndarm = "" + getRandom(100);
			
			Ability ab = db.getAbility(rndarm, treasuretype, a.getType());

		
			ArmorAbilitiesDAO aadb = new ArmorAbilitiesDAO();
			if (ab.getName().equals("roll twice")) {
				

				while (ab.getName().equals("roll twice")) {
					ab = db.getAbility("" + getRandom(100), treasuretype, a
							.getType());
				}

				
				ArmorAbilities aa1 = new ArmorAbilities();
				aa1.setAbilityName(ab.getName());
				Vector<ArmorAbilities> v = aadb.selectArmorAbilities(aa1);
				
;				a.setAbility1(v.firstElement());
				
				Ability ab2 = new Ability();
				ab2.setName("roll twice");
				while (ab2.getName().equals("roll twice")
						|| ab2.getName().equals(ab.getName())) {
					ab2 = db.getAbility("" + getRandom(100), treasuretype, a
							.getType());
				}
				

				ArmorAbilities aa2 = new ArmorAbilities();
				aa1.setAbilityName(ab2.getName());
				 v = aadb.selectArmorAbilities(v.firstElement());
				
				a.setAbility2(aa2);
			} else {
				ArmorAbilities aa1 = new ArmorAbilities();
				aa1.setAbilityName(ab.getName());
				Vector<ArmorAbilities> v = aadb.selectArmorAbilities(aa1);
				a.setAbility1(v.firstElement());
			}
		} else if (a.getType().equals("specific armor")
				|| a.getType().equals("specific shield")) {

			a.setType(a.getType().substring(a.getType().indexOf(" ") + 1,
					a.getType().length()));
			String rndarm = "" + getRandom(100);
			
			SpecificArmor ab = db.getSpecificArmor(rndarm, treasuretype, a.getType());
			a.setName(ab.getName());
			a.setDescription(ab.getDescription());
		}

		if (getRandom(100) == 1) {
			a.setIntelligent(true);
		}

		return a;
	}

	public Ring getMagicRing(String treasuretype) {
		Ring r = db.getMagicRing("" + getRandom(100), treasuretype);
		if (getRandom(100) == 1) {
			r.setIntelligent(true);
		}
		return r;
	}

	public Rods getMagicRod(String treasuretype) {
		Rods r = db.getMagicRod("" + getRandom(100), treasuretype);
		if (getRandom(100) == 1) {
			r.setIntelligent(true);
		}
		return r;
	}

	public Staffs getMagicStaff(String treasuretype) {
		Staffs r = db.getMagicStaff("" + getRandom(100), treasuretype);
		if (getRandom(100) == 1) {
			r.setIntelligent(true);
		}
		return r;
	}

	public Wand getMagicWand(String treasuretype) {
		Wand r = db.getMagicWand("" + getRandom(100), treasuretype);
		return r;
	}

	public Weapon getMagicWeapon(String treasuretype) {
		Weapon w = new Weapon();
		Vector<Weapon> weapons = new Vector<Weapon>();
		// String name = "";

		// find out what type of weapon it is.
		int rndwpn = getRandom(100);

		

		if (rndwpn >= 1 && rndwpn <= 70) {
			weapons = db.getMeleeWeapons();
		} else if (rndwpn >= 71 && rndwpn <= 80) {
			weapons = db.getExoticWeapons();
		} else {
			weapons = db.getRangedWeapons();
		}

		Weapon rndw = (Weapon) weapons.get(getRandom(weapons.size() - 1));
		w.setName(rndw.getName());
		w.setType(rndw.getType());

	

		// now find out the magic bonus/ability
		Weapon mw = db.getMagicWeapon("" + getRandom(100), treasuretype);

		
		// check for ability/specific weapon
		if (mw.getBonus().equals("0")) {
			
			if (mw.getType().equals("ability")) {
				WeaponAbilities ab = db.getWeaponAbility("" + getRandom(100),
						treasuretype, w.getType());

				if (ab.getName().equals("roll twice")) {
					

					while (ab.getName().equals("roll twice")) {
						ab = db.getWeaponAbility("" + getRandom(100),
								treasuretype, w.getType());
					}

					w.setAbility1(ab);
					
					WeaponAbilities ab2 = new WeaponAbilities();
					ab2.setName("roll twice");
					while (ab2.getName().equals("roll twice")
							|| ab2.getName().equals(ab.getName())) {
						ab2 = db.getWeaponAbility("" + getRandom(100),
								treasuretype, w.getType());
					}
					w.setAbility2(ab2);
				} else {
					
					w.setAbility1(ab);
				}
				// now that we have the abilities sorted out, pick the
				// weapon
				while (mw.getBonus().equals("0")
						&& !mw.getType().equals("specific")) {

					mw = db.getMagicWeapon("" + getRandom(100), treasuretype);
					if (mw.getType().equals("specific")) {
						SpecificWeapon spec = db.getSpecificWeapon(
								"" + getRandom(100), treasuretype);
						w.setName(spec.getName());
						w.setDescription(spec.getDescription());
					} else {
						w.setBonus(mw.getBonus());
						w.setDescription("Magic Weapon");
					}
				}

			} else {
				// the weapon is a specific weapon
				SpecificWeapon spec = db.getSpecificWeapon("" + getRandom(100),
						treasuretype);
				w.setName(spec.getName());
				w.setDescription(spec.getDescription());
			}

		} else {
		
			w.setBonus(mw.getBonus());
		}

		if (getRandom(100) <= 15) {
			w.setIntelligent(true);
		}
		return w;
	}

	public Wonderous getWonderousItem(String treasuretype) {
		Wonderous r = db.getWonderousItem("" + getRandom(100), treasuretype);
		return r;
	}

	public Potion getMagicPotion(String treasuretype) {
		Potion r = db.getPotion("" + getRandom(100), treasuretype);
		return r;
	}

	public Scroll getMagicScroll(String treasuretype) {
		
		Scroll r = new Scroll();
		// figure out if it is arcane or divine
		String type = "arcane";
		if (getRandom(100) >= 71) {
			type = "divine";
		}
		r.setType(type);

		// find out how many spells it has
		int numSpells = 0;
		if (treasuretype.equals("minor")) {
			numSpells = getRandom(3);
		} else if (treasuretype.equals("medium")) {
			numSpells = getRandom(4);
		} else {
			numSpells = getRandom(6);
		}

		// and what level they are.
		int level = db.getScrollLevel("" + getRandom(100), treasuretype);
		r.setLevel(level);
		// get the spells

		for (int j = 0; j < numSpells; j++) {
			r.addSpell(db.getScrollSpell("" + getRandom(100), level, type));
		}
		return r;
	}

	public Vector<TreasureItem> getItems(int num, String treasuretype) {
		return getItems(num, treasuretype, null);
	}

	public Vector<TreasureItem> getItems(int num, String treasuretype, String cat) {
		Vector<TreasureItem> v = new Vector<TreasureItem>();

		if (num == 0) {
			return v;
		}

		if (treasuretype == null) {
			
			return v;
		}

		String category = cat;
		
		// if the treasure is mundane, then look it up.
		if (treasuretype.equals("mundane")) {
			Vector<TreasureItem> mundane = getMundane(num);
			v.addAll(mundane);
		}
		// if the treasure is magical, find out what type.
		else {
			for (int i = 0; i < num; i++) {
				// boolean intelligent = false;
				if (cat == null || cat.equals("")) {
					category = db.getRandomMagicCategory("" + getRandom(100),
							treasuretype);
				}
				

				if (category.equals("armor")) {
					v.add(getMagicArmor(treasuretype));
				} else if (category.equals("rings")) {
					
					v.add(getMagicRing(treasuretype));
				} else if (category.equals("rods")) {
					
					v.add(getMagicRod(treasuretype));
				} else if (category.equals("staffs")) {
					
					v.add(getMagicStaff(treasuretype));
				} else if (category.equals("wands")) {
					
					v.add(getMagicWand(treasuretype));
				} else if (category.equals("weapons")) {
					v.add(getMagicWeapon(treasuretype));
				} else if (category.equals("wonderous")) {
					
					v.add(getWonderousItem(treasuretype));
				} else if (category.equals("potions")) {
					
					v.add(getMagicPotion(treasuretype));
				} else if (category.equals("scrolls")) {
					v.add(getMagicScroll(treasuretype));
				}

			}
		}
		return v;
	}

	public int getRandom(int i) {
		int j = rnd.nextInt(i);
		return j + 1;
	}
	
	public void addToPartyTreasure(){
		if (!StrManip.isNullOrEmpty(owner.getParty()) && bonusList.getSelectedIndex() >= 0){
			PartyDAO pdb = new PartyDAO();
			Party p = new Party();
			p.setName(owner.getParty());
			Vector<Party> party = pdb.selectParty(p);
			if (party.size() > 0){
				p = party.get(0);
				String current = bonusList.getSelectedValue().toString();
				p.setPartyNotes(p.getPartyNotes()+"\n"+current);
				pdb.updateParty(p);
				treasure.remove(bonusList.getSelectedIndex());
				bonusList.setListData(treasure);
			}
		}
	}
	
	class PopupListener extends MouseAdapter {

		
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
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
