package initcheck.database;

import initcheck.InitLogger;

public class InitConnectionFactory {
		
		// database connection information
		public String username = "alan";
		public String dataSource = "jdbc:odbc:player";
		public String password = "test";
		public String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
		
		
		
		private InitLogger logger = new InitLogger(this);
		// the connection to the database
	  private DBSession dbs = new DBSession(username, dataSource, 
																					password, driverName);

		// the connection to the database
	  private DBSession dbs2 = new DBSession(username, dataSource, 
																					password, driverName);

		private static InitConnectionFactory instance = null;

		private InitConnectionFactory(){
				try{
						dbs.open();
						dbs2.open();
					
				}catch (Exception e){
						logger.log("error","DIED IN CONNECTION FACTORY");
				}
		}

		public static InitConnectionFactory getInstance(){
				if (instance == null){
						instance = new InitConnectionFactory();
				}
				return instance;
		}

		public DBSession getSession(){
				return dbs;
		}

		public DBSession getSecondarySession(){
				return dbs2;
		}
}

