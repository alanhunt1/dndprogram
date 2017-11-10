package initcheck.database;

public class Tag {

		String tagName;
		String tagBody;

		/**
		 * Get the TagBody value.
		 * @return the TagBody value.
		 */
		public String getTagBody() {
				return tagBody;
		}

		/**
		 * Set the TagBody value.
		 * @param newTagBody The new TagBody value.
		 */
		public void setTagBody(String newTagBody) {
				this.tagBody = newTagBody;
		}

		/**
		 * Get the TagName value.
		 * @return the TagName value.
		 */
		public String getTagName() {
				return tagName;
		}

		/**
		 * Set the TagName value.
		 * @param newTagName The new TagName value.
		 */
		public void setTagName(String newTagName) {
				this.tagName = newTagName;
		}

		public static Tag getTag(String s){
				if (s == null || s.equals("") || s.length() < 1){
						return null;
				}
				String tagName = s.substring(1, s.indexOf(">"));
				String tagBody = s.substring(tagName.length()+2, s.indexOf("</"+tagName+">"));
				Tag t = new Tag();
				t.setTagName(tagName);
				t.setTagBody(tagBody);
				return t;
		}

		public Tag(){

		}

}
