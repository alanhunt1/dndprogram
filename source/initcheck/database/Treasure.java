package initcheck.database;

public class Treasure implements TreasureItem{
		
		String treasure = "";
		String amount = "0";
		String category = "";
		String description = "";
		
		public Treasure(){
			
		}
		
		public Treasure(String s){
			description = s;
		}
		
		public String toString(){
			return description;
		}
		
		/**
		 * Get the Category value.
		 * @return the Category value.
		 */
		public String getCategory() {
				return category;
		}

		/**
		 * Set the Category value.
		 * @param newCategory The new Category value.
		 */
		public void setCategory(String newCategory) {
				this.category = newCategory;
		}

		/**
		 * Get the Amount value.
		 * @return the Amount value.
		 */
		public String getAmount() {
				return amount;
		}

		/**
		 * Set the Amount value.
		 * @param newAmount The new Amount value.
		 */
		public void setAmount(String newAmount) {
				this.amount = newAmount;
		}

		
		/**
		 * Get the Treasure value.
		 * @return the Treasure value.
		 */
		public String getTreasure() {
				return treasure;
		}

		/**
		 * Set the Treasure value.
		 * @param newTreasure The new Treasure value.
		 */
		public void setTreasure(String newTreasure) {
				this.treasure = newTreasure;
		}

		

}
