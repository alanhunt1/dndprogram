package initcheck.character;

import initcheck.graphics.TiledListItem;

public interface InventoryItem extends TiledListItem {
	
	public String getQuantity();
	public String getWeight();
	public String getCost();
	public String getDescription();
	public String getType();
	public String getName();
	public boolean isTempMod();
	public String getNotes();
	public String getModType();
	public String getFullDescription();
	public String inventoryListFormat();
	
	public void setQuantity(String s);
	
	
}
