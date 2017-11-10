package initcheck.database;

import initcheck.graphics.TiledListItem;

public class Potion implements Item, TreasureListItem, TreasureItem, TiledListItem {

		String name;
		String description;
		boolean hasCharges;
		int charges;
		boolean intelligent;
		String displayType = "potion";

		/**
		 * Get the DisplayType value.
		 * @return the DisplayType value.
		 */
		public String getDisplayType() {
				return displayType;
		}

		/**
		 * Set the DisplayType value.
		 * @param newDisplayType The new DisplayType value.
		 */
		public void setDisplayType(String newDisplayType) {
				this.displayType = newDisplayType;
		}

		
		/**
		 * Get the Intelligent value.
		 * @return the Intelligent value.
		 */
		public boolean isIntelligent() {
				return intelligent;
		}

		/**
		 * Set the Intelligent value.
		 * @param newIntelligent The new Intelligent value.
		 */
		public void setIntelligent(boolean newIntelligent) {
				this.intelligent = newIntelligent;
		}

		
		public String toString(){
				String nameStr = "";
			
				nameStr += "Potion of "+name;
				return nameStr;
		}
		
		/**
		 * Get the Charges value.
		 * @return the Charges value.
		 */
		public int getCharges() {
				return charges;
		}

		/**
		 * Set the Charges value.
		 * @param newCharges The new Charges value.
		 */
		public void setCharges(int newCharges) {
				this.charges = newCharges;
		}

		/**
		 * Get the HasCharges value.
		 * @return the HasCharges value.
		 */
		public boolean isHasCharges() {
				return hasCharges;
		}

		/**
		 * Set the HasCharges value.
		 * @param newHasCharges The new HasCharges value.
		 */
		public void setHasCharges(boolean newHasCharges) {
				this.hasCharges = newHasCharges;
		}

		/**
		 * Get the Description value.
		 * @return the Description value.
		 */
		public String getDescription() {
				return description;
		}

		/**
		 * Set the Description value.
		 * @param newDescription The new Description value.
		 */
		public void setDescription(String newDescription) {
				this.description = newDescription;
		}

		/**
		 * Get the Name value.
		 * @return the Name value.
		 */
		public String getName() {
				return name;
		}

		/**
		 * Set the Name value.
		 * @param newName The new Name value.
		 */
		public void setName(String newName) {
				this.name = newName;
		}

}
