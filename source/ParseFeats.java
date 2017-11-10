
import initcheck.database.Feat;
import initcheck.database.FeatDAO;
import initcheck.database.PlayerItems;
import initcheck.database.PlayerItemsDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class ParseFeats{

		String filename;
		String genCategory;

		/**
		 * Get the GenCategory value.
		 * @return the GenCategory value.
		 */
		public String getGenCategory() {
				return genCategory;
		}

		/**
		 * Set the GenCategory value.
		 * @param newGenCategory The new GenCategory value.
		 */
		public void setGenCategory(String newGenCategory) {
				this.genCategory = newGenCategory;
		}

		/**
		 * Get the Filename value.
		 * @return the Filename value.
		 */
		public String getFilename() {
				return filename;
		}

		/**
		 * Set the Filename value.
		 * @param newFilename The new Filename value.
		 */
		public void setFilename(String newFilename) {
				this.filename = newFilename;
		}

		
		public void process(){
				try{
						BufferedReader in
								= new BufferedReader(new FileReader(filename));
						
						String line = in.readLine();
						while (line != null){
								addFeat(line);
								line = in.readLine();
						}
				}catch (Exception e){
						e.printStackTrace();
				}
		}

		public void addFeat(String feat){
				FeatDAO db = new FeatDAO();
				
				StringTokenizer split = new StringTokenizer(feat, "\t");
				String name = split.nextToken();
				String type = "";
				String desc = "";
				String req = "";
				@SuppressWarnings("unused") String ref = "";
				if (split.hasMoreTokens()){
						type = split.nextToken().trim();
				}
				if (split.hasMoreTokens()){
						ref = split.nextToken().trim();
				}
				if (split.hasMoreTokens()){
						desc = split.nextToken().trim();
				}
				if (split.hasMoreTokens()){
						req = split.nextToken().trim();
				}
				
				Feat f = new Feat();
				f.setFeatName(name);
				f.setDescription(desc+" Prerequisites : "+req);
				f.setType(type);
				if (!db.featExists(name)){
						db.addOrUpdateFeat(f);
				}
		}

		public void updateCase(){
				FeatDAO db = new FeatDAO();
				Vector<Feat> v = db.getFeats();
				for (int i = 0; i < v.size(); i++){
						Feat f = (Feat)v.get(i);
						String name = f.getFeatName();
						char [] chars = name.toCharArray();
						
						for (int j = 0; j < chars.length; j++){
								if (j == 0 || chars[j-1] == ' '){
										chars[j] = Character.toUpperCase(chars[j]);
								}else{
										chars[j] = Character.toLowerCase(chars[j]);
								}
						}
						f.setFeatName(new String(chars));
						db.addOrUpdateFeat(f);
				}
		}

		public void fixItemPosition(){
				PlayerItemsDAO db = new PlayerItemsDAO();
				Vector<PlayerItems> items = db.selectPlayerItems(new PlayerItems());
				int count = 1;
				String player = "";
				for (int i = 0; i < items.size(); i++){
						PlayerItems item = (PlayerItems)items.get(i);
						if (!item.getPlayerId().equals(player)){
								player = item.getPlayerId();
								count = 1;
						}
						item.setPosition(count);
						count++;
						db.updatePlayerItems(item);
				}
		}

		/**
		 * Describe <code>main</code> method here.
		 *
		 * @param args a <code>String[]</code> value
		 */
		public static void main(String[] args) {
				final ParseFeats app = new ParseFeats();
				
				if(args.length > 0) {
						if(args[0] != null) {
								app.setFilename(args[0]);
						}
				}
				
				if(args.length > 1) {
						if(args[1] != null) {
								app.setGenCategory(args[1]);
						}
				}

				//	app.process();
				app.fixItemPosition();
		}

}
