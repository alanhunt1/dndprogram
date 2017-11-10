package initcheck.character;

import java.util.Vector;

import initcheck.graphics.TiledListString;

public class Calculation {
		
		Vector<String> elements = new Vector<String>();
		String displayValue="";
		int value = 0;
		String displayValue2="";

		/**
		 * Get the DisplayValue2 value.
		 * @return the DisplayValue2 value.
		 */
		public String getDisplayValue2() {
				return displayValue2;
		}

		/**
		 * Set the DisplayValue2 value.
		 * @param newDisplayValue2 The new DisplayValue2 value.
		 */
		public void setDisplayValue2(String newDisplayValue2) {
				this.displayValue2 = newDisplayValue2;
		}

		public void addElement(String s){
				elements.add(s);
		}

		public void addAllElements(Calculation c){
			elements.addAll(c.getElements());		
		}
		
		/**
		 * Get the Value value.
		 * @return the Value value.
		 */
		public int getValue() {
				return value;
		}

		/**
		 * Set the Value value.
		 * @param newValue The new Value value.
		 */
		public void setValue(int newValue) {
				this.value = newValue;
		}

		/**
		 * Get the DisplayValue value.
		 * @return the DisplayValue value.
		 */
		public String getDisplayValue() {
				return displayValue;
		}

		/**
		 * Set the DisplayValue value.
		 * @param newDisplayValue The new DisplayValue value.
		 */
		public void setDisplayValue(String newDisplayValue) {
				this.displayValue = newDisplayValue;
		}

		/**
		 * Get the Elements value.
		 * @return the Elements value.
		 */
		public Vector<String> getElements() {
				return elements;
		}

		public Vector<TiledListString> getListElements() {
			Vector<TiledListString> v = new Vector<TiledListString>();
			for (String s:elements){
				v.add(new TiledListString(s));
			}
			return v;
		}
		
		/**
		 * Set the Elements value.
		 * @param newElements The new Elements value.
		 */
		public void setElements(Vector<String> newElements) {
				this.elements = newElements;
		}

		

}
