package initcheck.character;

import initcheck.database.ArmorDAO;
import initcheck.database.EquipmentDAO;
import initcheck.database.WeaponDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemChooserDialog extends JFrame implements ListSelectionListener {

	
	private static final long serialVersionUID = 1L;

	private JList equipmentList = new JList();

	private JScrollPane listScroll = new JScrollPane(equipmentList);

	private ItemChooserOwner owner;

	private JTabbedPane tabPane = new JTabbedPane();
	
	private JList armorList = new JList();
	
	private JScrollPane armorScroll = new JScrollPane(armorList);
	
	private JList weaponList = new JList();
	
	private JScrollPane weaponScroll = new JScrollPane(weaponList);
	
	public ItemChooserDialog(ItemChooserOwner owner) {
		this.owner = owner;
		EquipmentDAO db = new EquipmentDAO();
		

		Vector<InventoryItem> items = new Vector<InventoryItem>();
		items.addAll(db.getEquipment("Equip/Misc"));
		equipmentList.setListData(items);
		
		Vector<InventoryItem> armor = new Vector<InventoryItem>();
		armor.addAll(db.getEquipment("Armor/Shield"));
		ArmorDAO adb = new ArmorDAO();
		armor.addAll(adb.getArmor());	
		armorList.setListData(armor);
		
		Vector<InventoryItem> weapons = new Vector<InventoryItem>();
		weapons.addAll(db.getEquipment("Weapon/Ammo"));
		WeaponDAO wdb = new WeaponDAO();
		weapons.addAll(wdb.getWeapons());
		weapons.addAll(wdb.getAmmunition());
		weaponList.setListData(weapons);
		
		init();
	}
		
	public ItemChooserDialog(ItemChooserOwner owner, String type) {
		
		this.owner = owner;
		EquipmentDAO db = new EquipmentDAO();
		Vector<InventoryItem> items = new Vector<InventoryItem>();
		items.addAll(db.getEquipment(type));
		if (type.equals("Armor/Shield")){
			ArmorDAO adb = new ArmorDAO();
			items.addAll(adb.getArmor());	
		}
		else if (type.equals("Weapon/Ammo")){
			WeaponDAO wdb = new WeaponDAO();
			items.addAll(wdb.getWeapons());
			items.addAll(wdb.getAmmunition());
		}
		equipmentList.setListData(items);
		init();
	}
	
	private void init(){
		equipmentList.addListSelectionListener(this);

		equipmentList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setItem();
				}
			}		
		});
		
		weaponList.addListSelectionListener(this);

		weaponList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setItem();
				}
			}		
		});
		
		armorList.addListSelectionListener(this);

		armorList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setItem();
				}
			}		
		});
		
		
		
		tabPane.add(listScroll, "Equip/Misc");
		tabPane.add(weaponScroll, "Weapons/Ammo");
		tabPane.add(armorScroll, "Armor/Shield");
		

		getContentPane().add(tabPane);

		pack();
		setVisible(true);
	}
	

	public JList getList(){
		if (tabPane.getSelectedIndex() == 0){
			return equipmentList;
		}else if(tabPane.getSelectedIndex() == 1){
			return weaponList;
		}
		return armorList;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		getList().ensureIndexIsVisible(getList().getSelectedIndex());
	}

	public void setItem() {
		InventoryItem e = (InventoryItem) getList().getSelectedValue();
		owner.setItem(e);

	}

}
