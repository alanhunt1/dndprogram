package initcheck.database;

import initcheck.InitLogger;

public class InitBaseDAO {

	// database connection information
	public String username = "alan";

	public String dataSource = "jdbc:odbc:player";
	final String fileName = "c:/cvs/source/source/player.mdb";
	// String dataSource = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+fileName;
	
	public String password = "test";

	public String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";

	// the connection to the database
	//DBSession dbs = new DBSession(username, dataSource, password, driverName);
	DBSession dbs = InitConnectionFactory.getInstance().getSession();
	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");

	DBSession dbs2 = InitConnectionFactory.getInstance().getSecondarySession();

	DBSession dbs3 = InitConnectionFactory.getInstance().getTertiarySession();
	
	
	public void resetConnection() {
		try {
			dbs2.close();
			dbs2.open();
		} catch (Exception e) {
			logger.log("error", "Died recycling connection");
		}
	}
	
	public void rebuildDatabase(){
		logger.log("REBUILDING");
		 dbs = new DBSession(username, dataSource, password, driverName);
		 dbs2 = InitConnectionFactory.getInstance().getSession();
		 dbs3 = InitConnectionFactory.getInstance().getSecondarySession();
	}

}
