package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

public class CampaignSource implements Serializable, Cloneable, Exportable { 

    
	private static final long serialVersionUID = 1L;
	private String id;
    private String campaignId;
    private String sourceId;
    private String name;
    
    public String toString(){
    	return name;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CampaignSource(){

    }

    public CampaignSource(String s){
    	readImport(s);
    }

   public void readImport(String s){ 
     Tag t = Tag.getTag(s); 
     while (t != null) { 
       String tagName = t.getTagName();
       String tagBody = t.getTagBody();
      if (t.getTagName().equals("id")){
         setId(t.getTagBody());
      }
      if (t.getTagName().equals("campaignId")){
         setCampaignId(t.getTagBody());
      }
      if (t.getTagName().equals("sourceId")){
         setSourceId(t.getTagBody());
      }
     	s = s.substring((2*tagName.length())+5+tagBody.length(), s.length()); 
      t = Tag.getTag(s); 
      }
   }

    public String exportFormat(){ 
      StringBuffer sb = new StringBuffer();
      sb.append("<CampaignSource>\n");
      sb.append("<id>"+id+"</id>\n");
      sb.append("<campaignId>"+campaignId+"</campaignId>\n");
      sb.append("<sourceId>"+sourceId+"</sourceId>\n");
      sb.append("</CampaignSource>\n");
      return sb.toString();
   }

    public void convertNulls(){ 
      if (id == null) {
        id = "";
      }

      if (campaignId == null) {
        campaignId = "";
      }

      if (sourceId == null) {
        sourceId = "";
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
if (campaignId != null && !campaignId.equals("")) {
try {
 Integer.parseInt(campaignId);
} catch (Exception e) {
   errors.add("campaignId is not a valid number.  Please enter a valid number.");
}
}
if (sourceId != null && !sourceId.equals("")) {
try {
 Integer.parseInt(sourceId);
} catch (Exception e) {
   errors.add("sourceId is not a valid number.  Please enter a valid number.");
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
     * Get the value of id
     * @return a <code>String</code> value
     */
    public String getId(){
       return id;
    }

    /**
     * Set the value of id
     * @param a <code>String</code> value
     */
    public void setId(String s){
        id = s;
    }

    /**
     * Get the value of campaignId
     * @return a <code>String</code> value
     */
    public String getCampaignId(){
       return campaignId;
    }

    /**
     * Set the value of campaignId
     * @param a <code>String</code> value
     */
    public void setCampaignId(String s){
        campaignId = s;
    }

    /**
     * Get the value of sourceId
     * @return a <code>String</code> value
     */
    public String getSourceId(){
       return sourceId;
    }

    /**
     * Set the value of sourceId
     * @param a <code>String</code> value
     */
    public void setSourceId(String s){
        sourceId = s;
    }

}
