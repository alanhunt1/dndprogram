package initcheck.generator;

import initcheck.InitImage;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class StoreInfoPanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private Store store = new Store();

	private TiledTextArea description = new TiledTextArea(15, 30);

	private JScrollPane descScroll = new JScrollPane(description);

	private JTextField name = new JTextField(30);

	private JTextField owner = new JTextField(30);

	private StoreTypeChooser typeChooser = new StoreTypeChooser();

	private RandomButton randomNameButton = new RandomButton();

	private RandomButton randomOwnerButton = new RandomButton();

	private CharacterNameGenerator ownerGen = new CharacterNameGenerator();

	private StoreNameGenerator nameGen = new StoreNameGenerator();

	private InventoryPanel invPanel = new InventoryPanel("Equip/Misc");

	private InventoryPanel weaponPanel = new InventoryPanel("Weapon/Ammo");
	
	private InventoryPanel armorPanel = new InventoryPanel("Armor/Shield");
	
	private InventoryPanel magicPanel = new InventoryPanel("Magic");
	
	private ServicesPanel servicePanel = new ServicesPanel();
	
	private JTabbedPane tabPane = new JTabbedPane();
	
	public StoreInfoPanel() {
		super(InitImage.lightRocks);
		setWeightX(1);
		setWeightY(0);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setText(store.getDescription());
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Name"));
		namePanel.add(name);
		namePanel.add(randomNameButton);
		namePanel.setOpaque(false);
		doLayout(namePanel);

		incYPos();

		JPanel ownerPanel = new JPanel();
		ownerPanel.add(new JLabel("Owner"));
		ownerPanel.add(owner);
		ownerPanel.add(randomOwnerButton);
		// doLayout(new JLabel("Owner"));
		// doLayout(owner, 1);
		doLayout(ownerPanel);
		ownerPanel.setOpaque(false);
		incYPos();

		JPanel typePanel = new JPanel();
		typePanel.add(new JLabel("Type"));
		typePanel.add(typeChooser);
		typePanel.setOpaque(false);
		doLayout(typePanel);

		setWeightY(0.25);
		incYPos();
		doLayout(descScroll);
		setWeightY(0.75);
		incYPos();
		tabPane.add(invPanel, "Equipment");
		tabPane.add(weaponPanel, "Weapons");
		tabPane.add(armorPanel, "Armor");
		tabPane.add(magicPanel, "Magic");
		tabPane.add(servicePanel, "Services");
		
		doLayout(tabPane);

		randomNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateStoreName();
			}
		});

		randomOwnerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateOwnerName();
			}
		});

	}

	public void generateStoreName() {
		name.setText(nameGen.getRandomName());
	}

	public void generateOwnerName() {
		owner.setText(ownerGen.getRandomName());
	}

	public void setStore(Store s) {
		setCurrentValues();
		store = s;
		name.setText(store.getName());
		owner.setText(store.getOwner());
		description.setText(store.getDescription());
		invPanel.setInventory(store.getInventory());
		weaponPanel.setInventory(store.getWeapons());
		armorPanel.setInventory(store.getArmor());
		magicPanel.setInventory(store.getMagic());
		typeChooser.setSelectedItem(store.getType());
	}

	private void setCurrentValues() {
		store.setName(name.getText());
		store.setOwner(owner.getText());
		store.setDescription(description.getText());
		store.setInventory(invPanel.getModel());
		store.setWeapons(weaponPanel.getModel());
		store.setArmor(armorPanel.getModel());
		store.setMagic(magicPanel.getModel());
		store.setType((String) typeChooser.getSelectedItem());
	}

	public Store getModel() {
		setCurrentValues();
		return store;
	}

}
