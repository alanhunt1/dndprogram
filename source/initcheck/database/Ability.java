package initcheck.database;

public class Ability{

		String name;
		String description;
		String id;
		String damage = "";

		/**
		 * Get the Damage value.
		 * @return the Damage value.
		 */
		public String getDamage() {
				return damage;
		}

		/**
		 * Set the Damage value.
		 * @param newDamage The new Damage value.
		 */
		public void setDamage(String newDamage) {
				this.damage = newDamage;
		}

		
		public Ability(String s){
				setName(s);
		}

		public Ability(){
				
		}

		public boolean isNull(){
				return (name == null || name.equals("null"));
		}
		
		public String toString(){
				return name;
		}

		
		
		public String exportFormat(){ 
				StringBuffer sb = new StringBuffer();
				sb.append("<Ability>\n");
				sb.append("<id>"+id+"</id>");
				sb.append("</Ability>\n");
				return sb.toString();
		}

		/**
		 * Get the Id value.
		 * @return the Id value.
		 */
		public String getId() {
				return id;
		}

		/**
		 * Set the Id value.
		 * @param newId The new Id value.
		 */
		public void setId(String newId) {
				this.id = newId;
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
