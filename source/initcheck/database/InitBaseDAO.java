package initcheck.database;

import initcheck.InitLogger;

public class InitBaseDAO {

	InitLogger logger = new InitLogger(this, "defaultLog4j.cfg");
	
	DBSession dbs = InitConnectionFactory.getInstance().getSession();
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
		 dbs = InitConnectionFactory.getInstance().getSession();
		 dbs2 = InitConnectionFactory.getInstance().getSecondarySession();
		 dbs3 = InitConnectionFactory.getInstance().getTertiarySession();
	}

}
