

import initcheck.database.Feat;
import initcheck.database.FeatDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class ReadSpells{

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
						String header = line;
						String feat = "";
						while (line != null){
								if (line.indexOf("[") > 0 && feat != ""){
										addFeat(feat, header);
										feat = "";
										header = line;
								}
								feat += line;
								line = in.readLine();
						}
						addFeat(feat, header);
				}catch (Exception e){
					e.printStackTrace();
				}
		}

		public void addFeat(String feat, String header){
				FeatDAO db = new FeatDAO();
				String name = header.substring(0, header.indexOf("[")-1);
				
				String type = header.substring(header.indexOf("[")+1, header.indexOf("]"));
				
				StringTokenizer split = new StringTokenizer(type, ",");
				String type1 = split.nextToken().trim();
				String type2 = null;
				@SuppressWarnings("unused") String type3 = null;
				if (split.hasMoreTokens()){
						type2 = split.nextToken().trim();
				}
				if (split.hasMoreTokens()){
						type3 = split.nextToken().trim();
				}
				Feat f = new Feat();
				f.setFeatName(name);
				f.setDescription(feat);
				
				f.setType(genCategory);
				f.setType2(type1);
				f.setType3(type2);
				db.addOrUpdateFeat(f);
		}

		/**
		 * Describe <code>main</code> method here.
		 *
		 * @param args a <code>String[]</code> value
		 */
		public static void main(String[] args) {
				final ReadSpells app = new ReadSpells();
				
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

				app.process();
		}

}
