package initcheck.database;

public class ListHeader implements TreasureListItem{

		String displayType = "header";
		String text;

		public String toString(){
				return text;
		}
		
		/**
		 * Get the Text value.
		 * @return the Text value.
		 */
		public String getText() {
				return text;
		}

		/**
		 * Set the Text value.
		 * @param newText The new Text value.
		 */
		public void setText(String newText) {
				this.text = newText;
		}

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

		
}
