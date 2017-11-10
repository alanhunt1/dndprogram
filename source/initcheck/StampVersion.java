package initcheck;

import initcheck.database.VersionDAO;

import java.io.File;
import java.io.FileWriter;

public class StampVersion{


		public void process(){
				VersionDAO db = new VersionDAO();
				db.setVersion();
				
				try {
						File f = new File("version.txt");
						FileWriter out = new FileWriter(f);
						String version = ""+db.getVersionNumber();
						out.write(version, 0, version.length());
						out.close();
				} catch (Exception e){
						e.printStackTrace();
				}
		}

		
		public static void main(String[] args) {
				final StampVersion app = new StampVersion();
				
				app.process();
		}

}
