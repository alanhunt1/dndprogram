package initcheck.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ClassProficiencyDAO extends InitBaseDAO {

    public ClassProficiencyDAO(){

    }

    public void addClassProficiency(ClassProficiency o){
				String valueString = "";
				String insertString = "INSERT INTO CLASS_PROFICIENCY (";
   
				if (o.getIdType() != null) {
						insertString += "ID_TYPE,";
						valueString += "'"+dbs.escapeQuotes(o.getIdType())+"',";
				}
				if (o.getClassId() != null) {
						insertString += "CLASS_ID,";
						if (o.getClassId().equals("") ) {
								valueString += "null,";    
						}else{
								valueString += dbs.escapeQuotes(o.getClassId())+",";
							
						}
				}
				if (o.getProfType() != null) {
						insertString += "PROF_TYPE,";
						valueString += "'"+dbs.escapeQuotes(o.getProfType())+"',";
				}
				if (o.getProfSubtype() != null) {
						insertString += "PROF_SUBTYPE,";
						valueString += "'"+dbs.escapeQuotes(o.getProfSubtype())+"',";
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
				}finally{
						resetConnection();
				}
    }

    public void updateClassProficiency(ClassProficiency o){
				String updateString = "update CLASS_PROFICIENCY set ";
				if (o.getIdType() != null) {
						updateString += "ID_TYPE='"+dbs.escapeQuotes(o.getIdType())+"',";
				}
				if (o.getClassId() != null) {
						if (!o.getClassId().equals("")) {
								updateString += "CLASS_ID="+dbs.escapeQuotes(o.getClassId())+",";
						}else{
								updateString += "CLASS_ID=null,";
						}
				}
				if (o.getProfType() != null) {
						updateString += "PROF_TYPE='"+dbs.escapeQuotes(o.getProfType())+"',";
				}
				if (o.getProfSubtype() != null) {
						updateString += "PROF_SUBTYPE='"+dbs.escapeQuotes(o.getProfSubtype())+"'";
				}
				if (updateString.charAt(updateString.length()-1) == ','){
						updateString = updateString.substring(0, updateString.length()-1);
				}
				updateString += " WHERE ID = "+dbs.escapeQuotes(o.getId());
				
				try {
						dbs.open();
						dbs.executeSQLCommand(updateString);
						dbs.close();
				}catch (Exception e) {
						logger.log("error",e.toString());
				}finally{
						resetConnection();
				}
    }
		
		public Vector<ClassProficiency> getClassProficiency(CharClass c){
				ClassProficiency cp = new ClassProficiency();
				cp.setIdType("Class");
				cp.setClassId(c.getId());
				return selectClassProficiency(cp);
		}

    public Vector<ClassProficiency> selectClassProficiency(ClassProficiency o){
				String selectString = "SELECT * FROM CLASS_PROFICIENCY  ";
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
				if (o.getIdType() != null && !o.getIdType().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " ID_TYPE = '"+dbs.escapeQuotes(o.getIdType())+"' ";
				}
				if (o.getClassId() != null && !o.getClassId().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " CLASS_ID = "+dbs.escapeQuotes(o.getClassId())+" ";
				}
				if (o.getProfType() != null && !o.getProfType().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " PROF_TYPE = '"+dbs.escapeQuotes(o.getProfType())+"' ";
				}
				if (o.getProfSubtype() != null && !o.getProfSubtype().equals("")){
						if (!first){
								selectString += " AND ";
						} else {
								selectString += " WHERE ";
								first = false;
						}
						selectString += " PROF_SUBTYPE = '"+dbs.escapeQuotes(o.getProfSubtype())+"' ";
				}
				Vector<ClassProficiency> v = new Vector<ClassProficiency>();
				try {
						ResultSet result = null;
						if (dbs2 == null){
								dbs.open();
								result = dbs.executeSQLQuery(selectString);
						}else{
								result = dbs2.executeSQLQuery(selectString);
						}

						while (result.next()){
								ClassProficiency obj = new ClassProficiency();
								
								obj.setId(result.getString("ID"));
								obj.setIdType(result.getString("ID_TYPE"));
								obj.setClassId(result.getString("CLASS_ID"));
								obj.setProfType(result.getString("PROF_TYPE"));
								obj.setProfSubtype(result.getString("PROF_SUBTYPE"));
								v.add(obj);
						}
				}catch (SQLException sqle) {
						logger.log("error",sqle.toString());
				}catch (Exception e) {
						logger.log("error",e.toString());
				}finally{
						if (dbs2 == null){
								dbs.close();
						}
				}
				return v;
    }

		public void clearClassProficiency(String classid){
				String deleteString = "delete from CLASS_PROFICIENCY  ";
				deleteString += " WHERE CLASS_ID = "+classid;
				
				try {
						dbs.open();
        dbs.executeSQLCommand(deleteString);
        dbs.close();
				}catch (Exception e) {
						logger.log("error",e.toString());
				}finally{
						resetConnection();
				}
    }

    public void deleteClassProficiency(ClassProficiency o){
				String deleteString = "delete from CLASS_PROFICIENCY  ";
				deleteString += " WHERE ID = "+dbs.escapeQuotes(o.getId())+"";
				
				try {
						dbs.open();
        dbs.executeSQLCommand(deleteString);
        dbs.close();
				}catch (Exception e) {
						logger.log("error",e.toString());
				}finally{
						resetConnection();
				}
    }
}
