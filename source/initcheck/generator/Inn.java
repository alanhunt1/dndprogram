package initcheck.generator;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.InventoryItem;
import initcheck.database.Services;
import initcheck.graphics.TiledListItem;

public class Inn implements Serializable, TiledListItem {

	private static final long serialVersionUID = 1L;

	public String name = "Default";

	public String owner = "Default";

	public int numRooms = 0;

	public String description = "";
	
	public int size;

	private Vector<Services> services = new Vector<Services>();
	
	private Vector<InventoryItem> inventory = new Vector<InventoryItem>();
	
	public Vector<Services> getServices() {
		return services;
	}

	public void setServices(Vector<Services> services) {
		this.services = services;
	}

	public String toString(){
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(int numRooms) {
		this.numRooms = numRooms;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<InventoryItem> getInventory() {
		return inventory;
	}

	public void setInventory(Vector<InventoryItem> inventory) {
		this.inventory = inventory;
	}

}
