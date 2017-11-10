package initcheck.database;

import java.sql.ResultSet;

public class VersionDAO extends InitBaseDAO {

	public String getVersion() {

		String selectString = "SELECT * FROM VERSION WHERE VERSION = "
				+ getVersionNumber();
		String version = "Version ";
		try {

			ResultSet result = null;
			if (dbs2 == null) {
				dbs.open();
				result = dbs.executeSQLQuery(selectString);
			} else {
				result = dbs2.executeSQLQuery(selectString);
			}
			if (result.next()) {
				version += result.getString("VERSION");
				version += " [" + result.getDate("RELEASE_DATE") + "]";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log("error", e.toString());
			
		} finally {
			if (dbs2 == null) {
				dbs.close();
			} else {
				dbs2.cleanup();
			}

		}
		return version;
	}

	public int getVersionNumber() {
		String selectString = "SELECT MAX(VERSION) AS MAXVERSION FROM VERSION";
		int version = 0;
		try {
			dbs.open();
			ResultSet result = dbs.executeSQLQuery(selectString);
			if (result.next()) {
				version = result.getInt("MAXVERSION");
			}
		} catch (Exception e) {
			logger.log("error", e.toString());
		} finally {
			dbs.close();

		}
		return version;
	}

	public void setVersion() {
		int current = getVersionNumber();
		
		int newVersion = current + 1;
		
		String insertString = "INSERT INTO VERSION VALUES (" + newVersion
				+ ", date())";
		try {
			dbs.open();
		
			dbs.executeSQLCommand(insertString);
			dbs.close();
		} catch (Exception e) {
			logger.log("error", e.toString());
			e.printStackTrace();
		} finally {
			resetConnection();
		}
	}

}
