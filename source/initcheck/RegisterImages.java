package initcheck;

import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

public class RegisterImages {

	Vector<String> imageFiles = new Vector<String>();

	public void process() {
		//VersionDAO db = new VersionDAO();
		//db.setVersion();

		try {
			File f = new File("imageregister.txt");
			FileWriter out = new FileWriter(f);
			processDirectory("images");
			
			for (int i = 0; i < imageFiles.size(); i++){
				String filename = imageFiles.get(i)+"\n";
				
				out.write(filename, 0, filename.length());
			}
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processDirectory(String dir) {
		File f = new File(dir);
		
		// exclude the cvs directories
		if (f.getName().startsWith("CVS")){
			return;
		}
		
		if (!f.isDirectory()){
			return;
		}
		File [] files = f.listFiles();
		for (int i = 0; i < files.length; i++){
			File fi = files[i];
			if (fi.isDirectory()){
				processDirectory(fi.getPath());
			}else{
				// exclude zip files from the register
				if (!f.getName().endsWith(".zip")){
					imageFiles.add(dir+"\\"+fi.getName());
				}
			}
		}
	}

	public static void main(String[] args) {
		final RegisterImages app = new RegisterImages();

		app.process();
	}

}
