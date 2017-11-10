package initcheck.dungeon;

import initcheck.database.Tag;

import java.io.Serializable;

public class Door implements Cloneable, Serializable{
		
	
	private static final long serialVersionUID = 1L;
		boolean secret = false;
		boolean locked = false;

		public Object clone(){
				Door clone = new Door();
				clone.secret = secret;
				clone.locked = locked;
				return clone;
		}

		public String exportFormat(){
				StringBuffer sb = new StringBuffer();
				sb.append("<Door>\n");
				sb.append("<Secret>"+secret+"</Secret>\n");
				sb.append("<Locked>"+secret+"</Locked>\n");
				sb.append("</Door>\n");
				return sb.toString();
		}

		public Door(String s){

				Tag t = Tag.getTag(s); 
				while (t != null) { 
						String tagName = t.getTagName();
						String tagBody = t.getTagBody();
						
						if (t.getTagName().equals("Secret")){
								setSecret(new Boolean(t.getTagBody()).booleanValue());
						}
						if (t.getTagName().equals("Locked")){
								setLocked(new Boolean(t.getTagBody()).booleanValue());
						}
						s = s.substring(tagName.length()+tagName.length()+5+tagBody.length(), s.length()); 
						t = Tag.getTag(s); 
				}
		}

		/**
		 * Get the Locked value.
		 * @return the Locked value.
		 */
		public boolean isLocked() {
				return locked;
		}

		/**
		 * Set the Locked value.
		 * @param newLocked The new Locked value.
		 */
		public void setLocked(boolean newLocked) {
				this.locked = newLocked;
		}

		/**
		 * Get the Secret value.
		 * @return the Secret value.
		 */
		public boolean isSecret() {
				return secret;
		}

		/**
		 * Set the Secret value.
		 * @param newSecret The new Secret value.
		 */
		public void setSecret(boolean newSecret) {
				this.secret = newSecret;
		}

		public Door(){

		}

		public void setType(String type){
				if (type.equalsIgnoreCase("Secret")){
						setSecret(true);
				}
				if (type.equalsIgnoreCase("Locked")){
						setLocked(true);
				}
		}
	
}
