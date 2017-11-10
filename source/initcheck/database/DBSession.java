package initcheck.database;

import initcheck.InitLogger;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * DBSession is a class that is intended to act as a middle
 * layer between
 * web applications that require access to a database and the database.  It
 * is envisioned that each web application will not instantiate this
 * class directly, but rather develop a class specific to the application that
 * translates applicable functions into the particular SQL calls and
 * translates the returned objects into the appropriate display classes.  For
 * example, an application that did a database lookup of all documents in an
 * archive would implement a class that contained functions along the lines
 * of "getDocs()", which would return a set of document objects.  The
 * function would be implemented to construct an SQL query and pass it off
 * to this class for execution.  The rationale behind this is that it allows
 * implementation to change in the database access without having to change
 * all the JSP pages which do the access.
 *
 * @author Alan Hunt
 * @version 2.0 Jan 2004
 */
public class DBSession {

		/** the category for log4j logging. */
		public InitLogger logger = new InitLogger(this);

		/** username to connect with. */
		protected String username = null;
		/** database to connect to. */
		protected String dataSource = null;
		/** password to connect with. */
		protected String password = null;
		/** java driver to use. */
		protected String driverName = null;

		/** connection to the db. */
		protected Connection dbConn;

		/** the sql statement being processed. */
		protected Statement stat;

		/** the resource name to use for connection information */
		protected String resourceName = null;

		/** callable statement, if we are using one */
		protected CallableStatement cstat;

		/** vector to hold multiple statements, if we open more than one */
		protected Vector<Statement> statVector = new Vector<Statement>();


		//--------------------------------------------------------------------
		// constructors
		//--------------------------------------------------------------------

		/**
		 * create a new database session.
		 * @param un - The username to use when connecting to the database
		 * @param ds - The datasource string specifying the database to connect
		 *      to.  This is usually of the form jdbc:oracle:thin:SERVER:PORT:DB
		 *      where server is the machine name or ip address to look for the
		 *      database on, port is the port to request, and db is the
		 *      name of the database you are connecting to.  This string will
		 *      be different for non oracle databases.
		 * @param pw - The password for username
		 * @param dn - The string identifying the JDBC driver used for the
		 *      connection.  For oracle data sources, this is
		 *      oracle.jdbc.driver.OracleDriver
		 */
		public DBSession(String un, String ds, String pw, String dn){
				username = un;
				dataSource = ds;
				password = pw;
				driverName = dn;
		}

		/**
		 * create an empty database session.  The variables for the connection
		 * will have to be set before it can be used.
		 */
		public DBSession(){


		}

		/**
		 * create an empty database session.  The variables for the connection
		 * will have to be set before it can be used.
		 * @param resourceName a <code>String</code> value
		 */
		public DBSession(String resourceName){
				this.resourceName = resourceName;
		}

		public void open() {
			final String fileName = "player.mdb";
				
				try {
				    //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				    //String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+fileName;
				    //con = DriverManager.getConnection(url,"","");
				    
				    dbConn=DriverManager.getConnection("jdbc:ucanaccess://"+fileName); 
				    
				} catch (Exception e) {
				    // Handle exceptions
					System.out.println("ERROR OPENING ACCESS "+e);
					logger.log(e.toString());
					e.printStackTrace();
				} finally {
				   
				}
			}
		

		/**
		 * opens a connection to the server.  throws Exception when it fails
		 * to connect to database
		 * @throws Exception
		 */
		public void openOld() throws Exception {

        try {
						Class.forName(driverName);
            dbConn = DriverManager.getConnection(dataSource, username, password);
        } catch (Exception e) {
            logger.log(e.toString());
        }
				
				if (dbConn == null) {
						logger.log("Failed to connect to database");
						throw(new Exception("Failed to connect to Database!"));
				}

		}

		public void open(boolean autocommit)  throws Exception{
				open();
				try{
						dbConn.setAutoCommit(autocommit);
				}catch (Exception e){
						logger.log("error", "unable to set autocommit");
				}
		}

		/**
		 * releases connection to the server and closes any open statements.
		 * throws Exception when it closes the connection
		 * @return int the status of the close request. 1 is success, 0 is fail.
		 */
		public int close(){
				
				for (int i = 0; i < statVector.size(); i++){
						Statement s = (Statement)statVector.get(i);
						if (s != null){
								try {
										s.close();
								} catch (Exception e){
										return 0;
								}
						}
				}
				statVector = new Vector<Statement>();

				if (stat != null){
						try {
								stat.close();
						} catch (Exception e){
								return 0;
						}
				}

				if (cstat != null){
						try{
								cstat.close();
						} catch (Exception e){
								return 0;
						}
				}

				if (dbConn != null){
						try{
								dbConn.commit();
								dbConn.close();
						} catch (Exception e){
								return 0;
						}
				}

				return 1;
		}

		// clean up open statements for long runs;
		public void cleanup(){

				try {
						for (int i = 0; i < statVector.size(); i++){
								Statement s = (Statement)statVector.get(i);
								if (s != null){
										s.close();
								}
						}
						statVector = new Vector<Statement>();

						if (stat != null){
								stat.close();
						}

						if (cstat != null){
								cstat.close();
						}
				} catch (SQLException e){
						logger.log("error", "ERROR Cleaning DB Session : "+e.toString());

				}
		}

		public void commit(){
			try {
				if (dbConn != null){
						   dbConn.commit();

						}

				} catch (SQLException e){
						logger.log("error", "ERROR Committing DB Session : "+e.toString());

				}

		}
		/**
		 *  Rolls back the current connection.
		 */
		public void rollback(){
				try {
						if (dbConn != null){
								dbConn.rollback();
						}
				} catch (SQLException e){
						logger.log("ERROR : "+e.toString());
				}
		}


		/**
		 * Does an execute batch transaction.
		 *
		 * @param batch The vector of transactions to execute
		 * @return an array of the update counts from the transactions
		 * @exception Exception if an error occurs
		 */
		public int[] executeBatch(Vector<String> batch) throws Exception {
				int [] count = null;
				try {
						if (stat != null){
								statVector.add(stat);
						}
                       	stat = dbConn.createStatement();
						for (int i = 0; i < batch.size(); i++){
								logger.log("Batch Statement : "+ batch.get(i));
								stat.addBatch((String)batch.get(i));
						}
						count = stat.executeBatch();
						stat.clearBatch();
						stat.close();

				} catch(BatchUpdateException b) {
						logger.log("SQLException: " + b.getMessage());
						logger.log("SQLState:  " + b.getSQLState());
						logger.log("Message:  " + b.getMessage());
						logger.log("Vendor:  " + b.getErrorCode());
						logger.log("Update counts:  ");
						int [] updateCounts = b.getUpdateCounts();
						for (int i = 0; i < updateCounts.length; i++) {
								logger.log(updateCounts[i] + "   ");
						}
						close();

				} catch (SQLException e){
						close();
						logger.log("ERROR : "+e.toString());
						throw(new Exception(e.toString()));
				}
				return count;
		}


		/**
		 * Does an execute batch transaction. This method DOES NOT close the DB connection
         *  when it completes a batch transaction
		 *
		 * @param batch The vector of transactions to execute
		 * @return an array of the update counts from the transactions
		 * @exception Exception if an error occurs
		 */
		public int[] executeBatch(Vector<String> batch, boolean rollback) throws Exception {
				int [] count = null;
				try {
						if (stat != null){
								statVector.add(stat);
						}
                        dbConn.setAutoCommit(false);
						stat = dbConn.createStatement();
						for (int i = 0; i < batch.size(); i++){
								logger.log("Batch Statement : "+ batch.get(i));
								stat.addBatch((String)batch.get(i));
						}
						count = stat.executeBatch();
                        stat.clearBatch();
						stat.close();

				} catch(BatchUpdateException b) {
						logger.log("SQLException: " + b.getMessage());
						logger.log("SQLState:  " + b.getSQLState());
						logger.log("Message:  " + b.getMessage());
						logger.log("Vendor:  " + b.getErrorCode());
						logger.log("Update counts:  ");
						int [] updateCounts = b.getUpdateCounts();
						for (int i = 0; i < updateCounts.length; i++) {
								logger.log(updateCounts[i] + "   ");
						}
                        if(rollback) {
                            try { dbConn.rollback(); } catch(SQLException sqle) {
                                logger.log("Rollback failed");
                                sqle.printStackTrace();
                                throw new Exception(sqle.toString());
                            }
                        }
						//close(); DONT close
				} catch (SQLException e){
						//close(); DONT close
						logger.log("ERROR : "+e.toString());

                        if(rollback) {
                            try { dbConn.rollback(); } catch(SQLException sqle) {
                                logger.log("Rollback failed");
                                sqle.printStackTrace();
                            }
                        }
						throw(new Exception(e.toString()));
				}
				return count;
		}

		/**
		 * execute an SQL insert, update, or delete.
		 * @param commandString - the string containing the SQL command to
		 * execute.
		 * @return upon successful completion, it will return the count
		 * of modified records, or -1 if there was any error.
		 * @throws Exception
		 */
		public int executeSQLCommand(String commandString)
		throws Exception {

				// Execute the command (Insert, Delete, or Update)
				try {
						//logger.log("executing command "+commandString);
						if (stat != null){
								statVector.add(stat);
						}
						stat = dbConn.createStatement();
						int count = stat.executeUpdate(commandString);
						//stat.close();
						return count;

				} catch (SQLException e){
						close();
						logger.log("ERROR : "+e.toString());
						throw(new Exception(e.toString()));
				}
		}

		/**
		 * execute an SQL query and get the results.
		 * @param queryString - the string containing the SQL query to execute.
		 * @return Upon successful completion of the query, it returns
		 *           the appropriate resultset.  If there was an error, it
		 *           returns null.
		 * @throws Exception
		 */

		public ResultSet executeSQLQuery(String queryString)
		throws Exception {

				// Make the query and get the results
				try {
						if (stat != null){
								statVector.add(stat);
						}
						stat = dbConn.createStatement();
						logger.log("Executing Query : "+queryString);
						ResultSet result = stat.executeQuery(queryString);

						return result;

				} catch (SQLException e){
						close();
						logger.log("ERROR : "+e.toString());
						throw(new Exception(e.toString()));
				}
		}

		/**
		* oracle escape any single quotes in the target string.
		* Notes : this takes a string and converts all single quotes to
		*         escaped single quotes.  This avoids problems when inserting
		*         in tables, since the single quote is the field delimiter
		*         character in Oracle.
		* @param s - the raw string that you wish to escape
		*
		* @return String - the string with quotes escaped
		*
		*/
		public String escapeQuotes(String s){

				if (s == null || s.indexOf("'") < 0){
						return s;
				}

				StringTokenizer split = new StringTokenizer(s, "'");
				String escapedString = "";

				while (split.hasMoreTokens()){
						String token = split.nextToken();
						//logger.log("processing token "+token);
						escapedString += token;
						if (split.hasMoreTokens()){
								escapedString += "''";
						}
				}
				if (s.charAt(s.length()-1) == '\''){
						escapedString += "''";
				}
				//logger.log("Escaped "+s+" was "+escapedString);
				return escapedString;
		}

		/**
		 * See getDBID(String, String).
		 * @param tableName - the name of the table to get a new ID for
		 * @return - a new unique id for the specified table.
		 * @throws Exception
		 */
		public int getDBID(String tableName)
		throws Exception {

			try {
				return getDBID(tableName, "");
			}catch(Exception e) {
				close();
				throw e;
			}
		}

		/**
		 * Notes : This only works with predefined tables, as it
		 *         relies on sequences for the ID.
		 * @param tableName - the name of the table in the database that
		 *          you want a new id number for
		 * @param field - deprecated
		 * @return a new integer id, or -1 if there was an error
		 * @throws Exception
		 */
		public int getDBID(String tableName, String field)
		throws Exception {

				int recordCount = 0;

				try {

						String seqName = tableName+"_S1.nextval";
						logger.log("Selecting from Sequence "+seqName);
						if (stat != null){
								statVector.add(stat);
						}
					  stat = dbConn.createStatement();
						logger.log("Created statement");
						if (stat == null){
								logger.log("NULL STATEMENT");
						}
						ResultSet result = stat.executeQuery("select "+seqName+" from "+ "dual");
						logger.log("Selected value");

						if (result.next()){
								String idString = result.getString("NEXTVAL");
								logger.log("read result");
								if (idString == null){
										idString = "0";
								}
								logger.log("result was "+idString);
								recordCount = Integer.parseInt(idString);
								logger.log("parsed it");
						}
						//result.close();
						//stat.close();

				} catch (SQLException e){
						close();
						logger.log("ERROR : "+e.toString());
						throw(new Exception(e.toString()));
				}

				logger.log("Returning DBID "+(recordCount+1));
				return recordCount+1;
		}



		/**
		 * Execute an callable statement query and return a result set.  This assumes
		 * that the return is a result set, and that it is contained in the first
		 * parameter of the call.
		 * @param queryString - the string containing the SQL query to execute.
		 * @return Upon successful completion of the query, it returns
		 *           the appropriate resultset.  If there was an error, it
		 *           returns null.
		 * @throws Exception
		 */

		public ResultSet executeCallableSQLQuery(String queryString)
				throws Exception {

				// Make the query and get the results
				try {
						if (cstat != null){
								statVector.add(cstat);
						}
						cstat = prepareCall(queryString);
						//cstat.registerOutParameter(1, OracleTypes.CURSOR);
						logger.log("Executing Query : "+queryString);
						cstat.execute();
						ResultSet result = (ResultSet) cstat.getObject(1);
						return result;

				} catch (SQLException e){
						close();
						logger.log("error", "ERROR : "+e.toString());
						throw(new Exception(e.toString()));
				}
		}

		/**
		 * this creates a prepared statement from a string containing the
		 * SQL you want to run.
		 * @param s the string containing the SQL call
		 * @return an oracle prepared statement
		 * @exception Exception if an error occurs
		 */
		public CallableStatement prepareCall(String s) throws Exception {
 				CallableStatement cs = null;
				try {
						cs = (CallableStatement)(dbConn.prepareCall(s));
						return cs;
				}catch (SQLException e) {
                        e.printStackTrace();
						throw (new Exception(e.toString()));
				}
		}

		/**
		 * Describe <code>prepareStatement</code> method here.
		 *
		 * @param s a <code>String</code> value
		 * @return a <code>PreparedStatement</code> value
		 * @exception Exception if an error occurs
		 */
		public PreparedStatement prepareStatement(String s) throws Exception {
				PreparedStatement ps = null;
				try {
						ps = dbConn.prepareStatement(s);
				} catch (Exception e) {
						e.printStackTrace();
						throw (new Exception(e.toString()));
				}
				return ps;
		}

}
