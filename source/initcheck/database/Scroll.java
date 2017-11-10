package initcheck.database;

import java.util.Vector;

public class Scroll implements Item, TreasureListItem, TreasureItem {

		String name;
		String description;
		Vector<String> spells = new Vector<String>();
		String type;
		int level;
		String displayType = "scroll";

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
		 * Get the Level value.
		 * @return the Level value.
		 */
		public int getLevel() {
				return level;
		}

		/**
		 * Set the Level value.
		 * @param newLevel The new Level value.
		 */
		public void setLevel(int newLevel) {
				this.level = newLevel;
		}

		
		/**
		 * Get the Type value.
		 * @return the Type value.
		 */
		public String getType() {
				return type;
		}

		/**
		 * Set the Type value.
		 * @param newType The new Type value.
		 */
		public void setType(String newType) {
				this.type = newType;
		}

		/**
		 * Get the Spells value.
		 * @return the Spells value.
		 */
		public Vector<String> getSpells() {
				return spells;
		}

		/**
		 * Set the Spells value.
		 * @param newSpells The new Spells value.
		 */
		public void setSpells(Vector<String> newSpells) {
				this.spells = newSpells;
		}

		public void addSpell(String s){
				spells.add(s);
		}
		
		public String toString(){
				return "Level "+level+" "+type+" Scroll ";
		}
		
		
		/**
		 * Get the Description value.
		 * @return the Description value.
		 */
		public String getDescription() {
				String nameStr = "";
				for (int i = 0; i < spells.size(); i++){
						nameStr += (String)spells.get(i)+"\n";
				}
				return nameStr;
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
