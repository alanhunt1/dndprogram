

import initcheck.database.Equipment;
import initcheck.database.EquipmentDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class ParseItems{

		String filename;

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
								parseLine(line);
								line = in.readLine();
						}
						
				}catch (Exception e){
						e.printStackTrace();
				}
		}

		public void parseLine(String line){
				
				StringTokenizer split = new StringTokenizer(line, "|");
				EquipmentDAO db = new EquipmentDAO();
				Equipment e = new Equipment();
				e.setItemName(split.nextToken());
				e.setWeight(split.nextToken());
				// consume the strange field
				split.nextToken();
				if (split.hasMoreTokens()){
						e.setCost(split.nextToken());		
				}
				else{
						e.setCost("0");
				}
				if(split.hasMoreTokens()){
						e.setDescription(split.nextToken());
				}
				if (!db.exists(e.getItemName())){
						
						db.addOrUpdateEquipment(e);
				}
		}

		/**
		 * Describe <code>main</code> method here.
		 *
		 * @param args a <code>String[]</code> value
		 */
		public static void main(String[] args) {
				final ParseItems app = new ParseItems();
				
				if(args.length > 0) {
						if(args[0] != null) {
								app.setFilename(args[0]);
						}
				}
			
				app.process();
		}

}
