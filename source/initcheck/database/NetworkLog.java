package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class NetworkLog implements Serializable, Cloneable { 

  
	private static final long serialVersionUID = 1L;
	private String logId;
    private String operation;
    private String startTime;
    private String endTime;
    private String machineId;
    private String taskLevel;
    private String taskTime;
    private String sendTime;
    private String lagTime;
    
    public String getSendTime() {
		return sendTime;
	}


	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


	public String getLagTime() {
		return lagTime;
	}


	public void setLagTime(String lagTime) {
		this.lagTime = lagTime;
	}


	public String getTaskTime() {
		return taskTime;
	}


	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}


	public NetworkLog(){

    }


    public void convertNulls(){ 
      if (logId == null) {
        logId = "";
      }

      if (operation == null) {
        operation = "";
      }

      if (startTime == null) {
        startTime = "";
      }

      if (endTime == null) {
        endTime = "";
      }

      if (machineId == null) {
        machineId = "";
      }

      if (taskLevel == null) {
        taskLevel = "";
      }

    }

    public Vector<String> validate(){ 
      Vector<String> v = new Vector<String>();
      checkStrings(v);
      checkNumbers(v);
      checkDates(v);
      return v;
   }


    private void checkStrings(Vector<String> errors){ 
  }

    private void checkNumbers(Vector<String> errors){ 
if (taskLevel != null && !taskLevel.equals("")) {
try {
 Integer.parseInt(taskLevel);
} catch (Exception e) {
   errors.add("taskLevel is not a valid number.  Please enter a valid number.");
}
}
  }

    private void checkDates(Vector<String> errors){ 
  }

    public String format(String s){ 
		if (s == null || s.equals("null")){
return "";
}
return s;
  }
public Object getClone() {
Object o = null;
try {
    o = this.clone();
} catch (Exception e) {
}
return o;
}
    /**
     * Get the value of logId
     * @return a <code>String</code> value
     */
    public String getLogId(){
       return logId;
    }

    /**
     * Set the value of logId
     * @param a <code>String</code> value
     */
    public void setLogId(String s){
        logId = s;
    }

    /**
     * Get the value of operation
     * @return a <code>String</code> value
     */
    public String getOperation(){
       return operation;
    }

    /**
     * Set the value of operation
     * @param a <code>String</code> value
     */
    public void setOperation(String s){
        operation = s;
    }

    /**
     * Get the value of startTime
     * @return a <code>String</code> value
     */
    public String getStartTime(){
       return startTime;
    }

    /**
     * Set the value of startTime
     * @param a <code>String</code> value
     */
    public void setStartTime(String s){
        startTime = s;
    }

    /**
     * Get the value of endTime
     * @return a <code>String</code> value
     */
    public String getEndTime(){
       return endTime;
    }

    /**
     * Set the value of endTime
     * @param a <code>String</code> value
     */
    public void setEndTime(String s){
        endTime = s;
    }

    /**
     * Get the value of machineId
     * @return a <code>String</code> value
     */
    public String getMachineId(){
       return machineId;
    }

    /**
     * Set the value of machineId
     * @param a <code>String</code> value
     */
    public void setMachineId(String s){
        machineId = s;
    }

    /**
     * Get the value of taskLevel
     * @return a <code>String</code> value
     */
    public String getTaskLevel(){
       return taskLevel;
    }

    /**
     * Set the value of taskLevel
     * @param a <code>String</code> value
     */
    public void setTaskLevel(String s){
        taskLevel = s;
    }

}
