package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;


public class ClassAbilityXrefDAO extends InitBaseDAO{

    public ClassAbilityXrefDAO(){

    }

    public void addClassAbilityXref(ClassAbilityXref o){
				String valueString = "";
				String insertString = "INSERT INTO CLASS_ABILITY_XREF (";
		
				if (o.getClassId() != null) {
						insertString += "CLASS_ID,";
						if (o.getClassId().equals("") ) {
								valueString += "null,";    
						}else{
								valueString += dbs.escapeQuotes(o.getClassId())+",";
    
						}
				}
				if (o.getClassLevel() != null) {
						insertString += "CLASS_LEVEL,";
						if (o.getClassLevel().equals("") ) {
								valueString += "null,";    
						}else{
								valueString += dbs.escapeQuotes(o.getClassLevel())+",";
    
						}
				}
				if (o.getClassAbilityId() != null) {
						insertString += "CLASS_ABILITY_ID,";
						if (o.getClassAbilityId().equals("") ) {
								valueString += "null,";    
						}else{
								valueString += dbs.escapeQuotes(o.getClassAbilityId())+",";
    
						}
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
          
				}catch (Exception e) {
						logger.log("error", e.toString());
				}
				finally{
						dbs.close();
				}
    }

    public void updateClassAbilityXref(ClassAbilityXref o){
				String updateString = "update CLASS_ABILITY_XREF set ";
				if (o.getClassId() != null) {
						if (!o.getClassId().equals("")) {
								updateString += "CLASS_ID="+dbs.escapeQuotes(o.getClassId())+",";
						}else{
								updateString += "CLASS_ID=null,";
						}
				}
				if (o.getClassLevel() != null) {
						if (!o.getClassLevel().equals("")) {
								updateString += "CLASS_LEVEL="+dbs.escapeQuotes(o.getClassLevel())+",";
						}else{
								updateString += "CLASS_LEVEL=null,";
						}
				}
				if (o.getClassAbilityId() != null) {
						if (!o.getClassAbilityId().equals("")) {
								updateString += "CLASS_ABILITY_ID="+dbs.escapeQuotes(o.getClassAbilityId())+"";
						}else{
								updateString += "CLASS_ABILITY_ID=null";
						}
				}
				if (updateString.charAt(updateString.length()-1) == ','){
						updateString = updateString.substring(0, updateString.length()-1);
				}
				updateString += " WHERE ID = "+dbs.escapeQuotes(o.getId())+" ";
				try {
						dbs.open();
						dbs.executeSQLCommand(updateString);
          
				}catch (Exception e) {
						logger.log("error",e.toString());
				}
				finally{
						dbs.close();
				}
    }

    public Vector<ClassAbilityXref> selectClassAbilityXref(ClassAbilityXref o){
				String selectString = "SELECT * FROM CLASS_ABILITY_XREF  ";
				boolean first = true;
				if (o.getId() != null && !o.getId().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " ID = "+dbs.escapeQuotes(o.getId())+" ";
				}
				if (o.getClassId() != null && !o.getClassId().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " CLASS_ID = "+dbs.escapeQuotes(o.getClassId())+"";
				}
				if (o.getClassLevel() != null && !o.getClassLevel().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " CLASS_LEVEL = '"+dbs.escapeQuotes(o.getClassLevel())+"' ";
				}
				if (o.getClassAbilityId() != null && !o.getClassAbilityId().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " CLASS_ABILITY_ID = "+dbs.escapeQuotes(o.getClassAbilityId())+" ";
				}
				Vector<ClassAbilityXref> v = new Vector<ClassAbilityXref>();
				try {
						dbs.open();
						ResultSet result = dbs.executeSQLQuery(selectString);
						while (result.next()){
								ClassAbilityXref obj = new ClassAbilityXref();

								obj.setId(result.getString("ID"));
								obj.setClassId(result.getString("CLASS_ID"));
								obj.setClassLevel(result.getString("CLASS_LEVEL"));
								obj.setClassAbilityId(result.getString("CLASS_ABILITY_ID"));
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

    public void deleteClassAbilityXref(ClassAbilityXref o){
				String deleteString = "delete from CLASS_ABILITY_XREF  ";
				deleteString += " WHERE ID = "+dbs.escapeQuotes(o.getId())+" ";

				try {
						dbs.open();
						dbs.executeSQLCommand(deleteString);
        
				}catch (Exception e) {
						logger.log("error",e.toString());
				}
				finally{
						dbs.close();
				}
    }
}
