package initcheck.dungeon;

import initcheck.database.Monster;

import java.util.Vector;

public class DungeonVars {

		
		int rows;
		int cols;
		int levels;
		int numRooms;
		int curliness;
		int maxHeight;
		int maxWidth;
		int minHeight;
		int minWidth;
		int sparseness;
		int deadends;
		boolean cleararea;
		int encounterChance;
		double partyLevel;
		int partySize;
		GenerateDungeonPanel component;
		Vector<Monster> monsters;
		
		/**
		 * Get the Monsters value.
		 * @return the Monsters value.
		 */
		public Vector<Monster> getMonsters() {
				return monsters;
		}

		/**
		 * Set the Monsters value.
		 * @param newMonsters The new Monsters value.
		 */
		public void setMonsters(Vector<Monster> newMonsters) {
				this.monsters = newMonsters;
		}

		/**
		 * Get the PartySize value.
		 * @return the PartySize value.
		 */
		public int getPartySize() {
				return partySize;
		}

		/**
		 * Set the PartySize value.
		 * @param newPartySize The new PartySize value.
		 */
		public void setPartySize(int newPartySize) {
				this.partySize = newPartySize;
		}

		/**
		 * Get the Component value.
		 * @return the Component value.
		 */
		public GenerateDungeonPanel getComponent() {
				return component;
		}

		/**
		 * Set the Component value.
		 * @param newComponent The new Component value.
		 */
		public void setComponent(GenerateDungeonPanel newComponent) {
				this.component = newComponent;
		}

		
		/**
		 * Get the PartyLevel value.
		 * @return the PartyLevel value.
		 */
		public double getPartyLevel() {
				return partyLevel;
		}

		/**
		 * Set the PartyLevel value.
		 * @param newPartyLevel The new PartyLevel value.
		 */
		public void setPartyLevel(double newPartyLevel) {
				this.partyLevel = newPartyLevel;
		}

		

		/**
		 * Get the EncounterChance value.
		 * @return the EncounterChance value.
		 */
		public int getEncounterChance() {
				return encounterChance;
		}

		/**
		 * Set the EncounterChance value.
		 * @param newEncounterChance The new EncounterChance value.
		 */
		public void setEncounterChance(int newEncounterChance) {
				this.encounterChance = newEncounterChance;
		}

		/**
		 * Get the Cleararea value.
		 * @return the Cleararea value.
		 */
		public boolean isCleararea() {
				return cleararea;
		}

		/**
		 * Set the Cleararea value.
		 * @param newCleararea The new Cleararea value.
		 */
		public void setCleararea(boolean newCleararea) {
				this.cleararea = newCleararea;
		}

		/**
		 * Get the Deadends value.
		 * @return the Deadends value.
		 */
		public int getDeadends() {
				return deadends;
		}

		/**
		 * Set the Deadends value.
		 * @param newDeadends The new Deadends value.
		 */
		public void setDeadends(int newDeadends) {
				this.deadends = newDeadends;
		}

		/**
		 * Get the Sparseness value.
		 * @return the Sparseness value.
		 */
		public int getSparseness() {
				return sparseness;
		}

		/**
		 * Set the Sparseness value.
		 * @param newSparseness The new Sparseness value.
		 */
		public void setSparseness(int newSparseness) {
				this.sparseness = newSparseness;
		}

		/**
		 * Get the MinWidth value.
		 * @return the MinWidth value.
		 */
		public int getMinWidth() {
				return minWidth;
		}

		/**
		 * Set the MinWidth value.
		 * @param newMinWidth The new MinWidth value.
		 */
		public void setMinWidth(int newMinWidth) {
				this.minWidth = newMinWidth;
		}

		/**
		 * Get the MinHeight value.
		 * @return the MinHeight value.
		 */
		public int getMinHeight() {
				return minHeight;
		}

		/**
		 * Set the MinHeight value.
		 * @param newMinHeight The new MinHeight value.
		 */
		public void setMinHeight(int newMinHeight) {
				this.minHeight = newMinHeight;
		}

		/**
		 * Get the MaxWidth value.
		 * @return the MaxWidth value.
		 */
		public int getMaxWidth() {
				return maxWidth;
		}

		/**
		 * Set the MaxWidth value.
		 * @param newMaxWidth The new MaxWidth value.
		 */
		public void setMaxWidth(int newMaxWidth) {
				this.maxWidth = newMaxWidth;
		}

		/**
		 * Get the MaxHeight value.
		 * @return the MaxHeight value.
		 */
		public int getMaxHeight() {
				return maxHeight;
		}

		/**
		 * Set the MaxHeight value.
		 * @param newMaxHeight The new MaxHeight value.
		 */
		public void setMaxHeight(int newMaxHeight) {
				this.maxHeight = newMaxHeight;
		}

		/**
		 * Get the Curliness value.
		 * @return the Curliness value.
		 */
		public int getCurliness() {
				return curliness;
		}

		/**
		 * Set the Curliness value.
		 * @param newCurliness The new Curliness value.
		 */
		public void setCurliness(int newCurliness) {
				this.curliness = newCurliness;
		}

		/**
		 * Get the NumRooms value.
		 * @return the NumRooms value.
		 */
		public int getNumRooms() {
				return numRooms;
		}

		/**
		 * Set the NumRooms value.
		 * @param newNumRooms The new NumRooms value.
		 */
		public void setNumRooms(int newNumRooms) {
				this.numRooms = newNumRooms;
		}

		/**
		 * Get the Levels value.
		 * @return the Levels value.
		 */
		public int getLevels() {
				return levels;
		}

		/**
		 * Set the Levels value.
		 * @param newLevels The new Levels value.
		 */
		public void setLevels(int newLevels) {
				this.levels = newLevels;
		}

		/**
		 * Get the Cols value.
		 * @return the Cols value.
		 */
		public int getCols() {
				return cols;
		}

		/**
		 * Set the Cols value.
		 * @param newCols The new Cols value.
		 */
		public void setCols(int newCols) {
				this.cols = newCols;
		}

		/**
		 * Get the Rows value.
		 * @return the Rows value.
		 */
		public int getRows() {
				return rows;
		}
		
		/**
		 * Set the Rows value.
		 * @param newRows The new Rows value.
		 */
		public void setRows(int newRows) {
				this.rows = newRows;
		}

				
}
