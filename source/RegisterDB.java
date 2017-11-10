import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class RegisterDB {
		static JFrame frame;
		
		public void process(){
				JFileChooser fc = new JFileChooser("register database");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileFilter(new DBFilter());
				int returnVal = fc.showOpenDialog(frame);
				String filename = "c:\\initcheck\\player.mdb";
				if (returnVal == JFileChooser.APPROVE_OPTION) {
						filename = fc.getSelectedFile().getAbsolutePath();
				}
				writeFile(filename);
		}

		public void writeFile(String filename){
				
				
				try {
						FileWriter out = new FileWriter("database.reg");
						
						out.write("Windows Registry Editor Version 5.00\r\n");

						out.write("[HKEY_LOCAL_MACHINE\\SOFTWARE\\ODBC\\ODBC.INI\\player]\r\n\r\n");
						
						out.write("\"Driver\"=\"C:\\\\WINNT\\\\System32\\\\odbcjt32.dll\"\r\n");
						out.write("\"DBQ\"=\""+escapePath(filename)+"\"\r\n");
						out.write("\"DriverId\"=dword:00000019\r\n");
						out.write("\"FIL\"=\"MS Access;\"\r\n");
						out.write("\"SafeTransactions\"=dword:00000000\r\n");
						out.write("\"UID\"=\"\"\r\n\r\n");

						out.write("[HKEY_LOCAL_MACHINE\\SOFTWARE\\ODBC\\ODBC.INI\\player\\Engines]\r\n\r\n");

						out.write("[HKEY_LOCAL_MACHINE\\SOFTWARE\\ODBC\\ODBC.INI\\player\\Engines\\Jet]\r\n\r\n");
						out.write("\"ImplicitCommitSync\"=\"\"\r\n");
						out.write("\"MaxBufferSize\"=dword:00000800\r\n");
						out.write("\"PageTimeout\"=dword:00000005\r\n");
						out.write("\"Threads\"=dword:00000003\r\n");
						out.write("\"UserCommitSync\"=\"Yes\"\r\n\r\n");

						out.write("[HKEY_LOCAL_MACHINE\\SOFTWARE\\ODBC\\ODBC.INI\\ODBC Data Sources]\r\n\r\n");
						out.write("\"player\"=\"Microsoft Access Driver (*.mdb)\"\r\n");

						out.flush();
						out.close();

				}catch (Exception e){
						e.printStackTrace();
				}
		}

		private String escapePath (String s){
				if (s == null || s.indexOf("\\") < 0) {
            return s;
        }

        StringBuffer escapedString = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\') {
                escapedString.append("\\\\");
            } else {
                escapedString.append(s.charAt(i));
            }
        }

        return escapedString.toString();
		}

		/**
		 * Describe <code>main</code> method here.
		 *
		 * @param args a <code>String[]</code> value
		 */
		public static void main(String[] args) {
				final RegisterDB app = new RegisterDB();
				frame = new JFrame("");
				app.process();
				System.exit(0);
		}

}

/* ImageFilter.java is a 1.4 example used by FileChooserDemo2.java. */
class DBFilter extends javax.swing.filechooser.FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("mdb")){
								return true;
            } else {
                return false;
            }
        }

        return false;
    }

		public String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    //The description of this filter
    public String getDescription() {
        return "Access Database Files";
    }
}
