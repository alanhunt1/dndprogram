package initcheck.generator;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.InventoryItem;
import initcheck.database.Services;
import initcheck.graphics.TiledListItem;

public class Store implements Serializable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String name = "Default";

	private String owner = "Default";

	private Vector<InventoryItem> inventory = new Vector<InventoryItem>();

	private Vector<InventoryItem> weapons = new Vector<InventoryItem>();
	
	private Vector<InventoryItem> armor = new Vector<InventoryItem>();
	
	private Vector<InventoryItem> magic = new Vector<InventoryItem>();
	
	private Vector<Services> services = new Vector<Services>();
	
	private String type = "General";
	
	private String description = "Default";
	
	public Vector<InventoryItem> getMagic() {
		return magic;
	}

	public void setMagic(Vector<InventoryItem> magic) {
		this.magic = magic;
	}

	public String toString(){
		return name;
	}
	
	public Store() {
		type = "General";
	}
	
	public Store(String type) {
		this.type = type;
	}
	
	public void generate() {
		CharacterNameGenerator gen = new CharacterNameGenerator();
		owner = gen.getRandomName();
	}

	public Vector<InventoryItem> getInventory() {
		return inventory;
	}

	public void setInventory(Vector<InventoryItem> inventory) {
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType (String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<Services> getServices() {
		return services;
	}

	public void setServices(Vector<Services> services) {
		this.services = services;
	}

	public Vector<InventoryItem> getArmor() {
		return armor;
	}

	public void setArmor(Vector<InventoryItem> armor) {
		this.armor = armor;
	}

	public Vector<InventoryItem> getWeapons() {
		return weapons;
	}

	public void setWeapons(Vector<InventoryItem> weapons) {
		this.weapons = weapons;
	}
	
}
