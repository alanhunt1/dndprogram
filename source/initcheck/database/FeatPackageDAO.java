package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class FeatPackageDAO  extends InitBaseDAO {

    public FeatPackageDAO(){

    }

		public int addOrUpdateFeatPackage(FeatPackage o){
				int i = -1;
				if (o.getId() != null){
						updateFeatPackage(o);
				}else{
						addFeatPackage(o);
				}
				try{
						// open the connection
						dbs.open();
						String command = "SELECT MAX(ID) as MAXID FROM FEAT_PACKAGE";
						
						ResultSet result = dbs.executeSQLQuery(command);
						if (result.next()){
							  i = result.getInt("MAXID");
						}

				} catch (Exception uhe){
						logger.log("ERROR : "+uhe.toString());
				}
				
				

				// close the connection
				dbs.close();
				return i;
		}


    public void addFeatPackage(FeatPackage o){
				String valueString = "";
				String insertString = "INSERT INTO FEAT_PACKAGE (";
			
				if (o.getPackageName() != null) {
						insertString += "PACKAGE_NAME,";
						valueString += "'"+dbs.escapeQuotes(o.getPackageName())+"',";
				}
				if (o.getDescription() != null) {
						insertString += "DESCRIPTION,";
						valueString += "'"+dbs.escapeQuotes(o.getDescription())+"',";
				}
				if (insertString.charAt(insertString.length() - 1) == ',')
						{
								insertString = insertString.substring(0, insertString.length() - 1);
						}
				if (valueString.charAt(valueString.length() - 1) == ',')
						{
								valueString = valueString.substring(0, valueString.length() - 1);
						}
				insertString +=  ") VALUES (";
				insertString += valueString;
				insertString += ")";

				try {
						dbs.open();
						logger.log("Executing Insert"+insertString);
						dbs.executeSQLCommand(insertString);
						dbs.close();
				}catch (Exception e) {
						logger.log("error", e.toString());
				}
    }

    public void updateFeatPackage(FeatPackage o){
				String updateString = "update FEAT_PACKAGE set ";
				if (o.getPackageName() != null) {
						updateString += "PACKAGE_NAME='"+dbs.escapeQuotes(o.getPackageName())+"',";
				}
				if (o.getDescription() != null) {
						updateString += "DESCRIPTION='"+dbs.escapeQuotes(o.getDescription())+"'";
				}
				if (updateString.charAt(updateString.length()-1) == ','){
						updateString = updateString.substring(0, updateString.length()-1);
				}
				updateString += " WHERE ID = "+dbs.escapeQuotes(o.getId())+" ";
				try {
						dbs.open();
						dbs.executeSQLCommand(updateString);
						dbs.close();
				}catch (Exception e) {
						logger.log("error",e.toString());
				}
    }

		public FeatPackage getFeatPackage(String packageName){
				FeatPackage fp = new FeatPackage();
				fp.setPackageName(packageName);
				Vector<FeatPackage> pkgs = selectFeatPackage(fp);
				if (pkgs.size() > 0){
						return (FeatPackage)pkgs.get(0);
				}
				return null;
		}

		public Vector<FeatPackage> getFeatPackages(){
				return selectFeatPackage(new FeatPackage());
		}

    public Vector<FeatPackage> selectFeatPackage(FeatPackage o){
				String selectString = "SELECT * FROM FEAT_PACKAGE  ";
				boolean first = true;
				if (o.getId() != null && !o.getId().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " ID = '"+dbs.escapeQuotes(o.getId())+"' ";
				}
				if (o.getPackageName() != null && !o.getPackageName().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " PACKAGE_NAME = '"+dbs.escapeQuotes(o.getPackageName())+"' ";
				}
				if (o.getDescription() != null && !o.getDescription().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " DESCRIPTION = '"+dbs.escapeQuotes(o.getDescription())+"' ";
				}
				Vector<FeatPackage> v = new Vector<FeatPackage>();
				try {
						dbs.open();
						ResultSet result = dbs.executeSQLQuery(selectString);
						while (result.next()){
								FeatPackage obj = new FeatPackage();

								obj.setId(result.getString("ID"));
								obj.setPackageName(result.getString("PACKAGE_NAME"));
								obj.setDescription(result.getString("DESCRIPTION"));

								FeatPackageItemDAO fpidb = new FeatPackageItemDAO();
								obj.setFeats(fpidb.getPackageFeats(obj.getId()));

								v.add(obj);
						}
				}catch (SQLException sqle) {
						logger.log("error",sqle.toString());
				}catch (Exception e) {
						logger.log("error",e.toString());
				}finally{
						dbs.close();
				}
				return v;
    }

    public void deleteFeatPackage(FeatPackage o){

				FeatPackageItemDAO fpidb = new FeatPackageItemDAO();
				fpidb.deleteFeatPackage(o);

				String deleteString = "delete from FEAT_PACKAGE  ";
				deleteString += " WHERE ID = "+dbs.escapeQuotes(o.getId())+"";

				try {
						dbs.open();
						dbs.executeSQLCommand(deleteString);
						dbs.close();
				}catch (Exception e) {
						logger.log("error",e.toString());
				}
    }
}
