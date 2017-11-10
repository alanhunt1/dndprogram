package initcheck.generator;

import initcheck.InitFont;
import initcheck.InitImage;
import initcheck.PanelButton;
import initcheck.character.InventoryItem;
import initcheck.character.ItemChooserDialog;
import initcheck.character.ItemChooserOwner;
import initcheck.database.ArmorDAO;
import initcheck.database.EquipmentDAO;
import initcheck.database.WeaponDAO;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledList;
import initcheck.utils.RandomRoll;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InventoryPanel extends TiledGridPanel implements ItemChooserOwner {

	private static final long serialVersionUID = 1L;

	private Vector<InventoryItem> inventory = new Vector<InventoryItem>();

	private TiledList inventoryList = new TiledList();

	private JScrollPane invScroll = new JScrollPane(inventoryList);

	private PanelButton addButton = new PanelButton("Add", 50);

	private PanelButton remButton = new PanelButton("Remove", 50);
	
	private PanelButton clearButton  = new PanelButton("Clear", 50);

	private PanelButton incButton = new PanelButton("+");

	private PanelButton decButton = new PanelButton("-");

	private RandomButton randomButton = new RandomButton();

	private String inventoryType;

	public InventoryPanel(String type) {
		super(InitImage.lightRocks);
		this.inventoryType = type;

		inventoryList.setFont(InitFont.courier);
		inventoryList.setCellRenderer(new InventoryListCellRenderer());

		setBorder(BorderFactory.createEmptyBorder());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(incButton);
		buttonPanel.add(decButton);
		buttonPanel.add(addButton);
		buttonPanel.add(remButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(randomButton);

		setWeightX(1);
		setWeightY(1);
		doLayout(invScroll);
		incYPos();
		setWeightX(0);
		setWeightY(0);
		doLayout(buttonPanel);

		incButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeQuantity(1);
			}
		});

		decButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeQuantity(-1);
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInventory();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remInventory();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInventory();
			}
		});


		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateRandomInventory();
			}
		});
	}

	public void changeQuantity(int i) {
		int selIndex = inventoryList.getSelectedIndex();
		if (selIndex >= 0) {
			InventoryItem e = inventory.get(selIndex);
			String quant = e.getQuantity();
			int count = 0;
			if (quant != null && !quant.equals("")) {
				count = Integer.parseInt(quant);
				count += i;
			}
			e.setQuantity("" + count);
			inventoryList.setListData(new Vector<InventoryItem>(inventory));
			inventoryList.setSelectedIndex(selIndex);
		}
	}

	public void generateRandomInventory() {
		if (inventory == null){
			inventory = new Vector<InventoryItem>();
		}
		
		EquipmentDAO db = new EquipmentDAO();
		Vector<InventoryItem> equipment = new Vector<InventoryItem>();
		equipment.addAll(db.getEquipment(inventoryType));
		if (inventoryType.equals("Armor/Shield")){
			ArmorDAO adb = new ArmorDAO();
			equipment.addAll(adb.getArmor());	
		}
		else if (inventoryType.equals("Weapon/Ammo")){
			WeaponDAO wdb = new WeaponDAO();
			equipment.addAll(wdb.getWeapons());
			equipment.addAll(wdb.getAmmunition());
		}
		// how many items in inventory?
		int itemCount = RandomRoll.getRandom(30);

		for (int i = 0; i < itemCount; i++) {
			InventoryItem e = (InventoryItem) equipment.get(RandomRoll
					.getRandom(equipment.size()));
			e.setQuantity(""+RandomRoll.getRandom(10));
			inventory.add(e);
		}
		inventoryList.setListData(new Vector<InventoryItem>(inventory));
	}

	public void addInventory() {
		@SuppressWarnings("unused")
		ItemChooserDialog icd = new ItemChooserDialog(this, inventoryType);
	}

	public void clearInventory(){
		inventory = new Vector<InventoryItem>();
		inventoryList.setListData(inventory);
	}
	
	public void remInventory() {
		int selIndex = inventoryList.getSelectedIndex();
		if (selIndex >= 0) {
			inventory.remove(selIndex);
			inventoryList.setListData(new Vector<InventoryItem>(inventory));
			if (selIndex > 0) {
				inventoryList.setSelectedIndex(selIndex);
			} else if (inventory.size() > 0) {
				inventoryList.setSelectedIndex(0);
			}
		}
	}

	public Vector<InventoryItem> getInventory() {
		return inventory;
	}

	public void setInventory(Vector<InventoryItem> inventory) {
		if (inventory == null){
			inventory = new Vector<InventoryItem>();
		}
		this.inventory = inventory;
		
		inventoryList.setListData(new Vector<InventoryItem>(inventory));
		
	}

	public Vector<InventoryItem> getModel() {
		return inventory;
	}

	public void setItem(InventoryItem e) {
		
		int selIndex = inventoryList.getSelectedIndex();
		if (inventory == null){
			inventory = new Vector<InventoryItem>();
		}
		inventory.add(e);
		inventoryList.setListData(new Vector<InventoryItem>(inventory));
		if (selIndex >= 0) {
			inventoryList.setSelectedIndex(selIndex);
		}
	}

}
