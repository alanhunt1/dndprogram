package initcheck.generator;

import java.io.Serializable;
import java.util.Vector;

public class Town implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name = "TOWN";

	private int population = 0;

	private String description = "A nondescript hamlet.";
	
	private Vector<Inn> inns = new Vector<Inn>();

	private Vector<Store> stores = new Vector<Store>();

	private Vector<Building> buildings = new Vector<Building>();

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public Town() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addInn(Inn i) {
		inns.add(i);
	}
	
	public void addStore(Store s) {
		stores.add(s);
	}
	
	public void addBuilding(Building b) {
		buildings.add(b);
	}

	public Vector<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(Vector<Building> buildings) {
		this.buildings = buildings;
	}

	public Vector<Inn> getInns() {
		return inns;
	}

	public void setInns(Vector<Inn> inns) {
		this.inns = inns;
	}

	public Vector<Store> getStores() {
		return stores;
	}

	public void setStores(Vector<Store> stores) {
		this.stores = stores;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
