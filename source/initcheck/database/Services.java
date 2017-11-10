package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.character.library.LibraryItem;
import initcheck.graphics.TiledListItem;
import initcheck.utils.StrManip;

public class Services implements Serializable, Cloneable, Exportable, LibraryItem, Importable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String serviceId;

	private String serviceName;

	private String serviceCost;

	private String serviceDescription;

	private String serviceType;

	public Services() {

	}

	public String listFormat(){
		return StrManip.pad(serviceName, 30);
	}
	
	public String listValue(){
		return "Service : " +serviceName;
	}
	
	public String getName(){
		return serviceName;
	}
	
	public void setName(String s){
		serviceName = s;
	}
	
	public String toString(){
		return serviceName;
	}
	
	public Services(String s) {
		readImport(s);
	}
	
	public void readImport(String s){
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("ServiceId")) {
				setServiceId(t.getTagBody());
			}
			if (t.getTagName().equals("ServiceName")) {
				setServiceName(t.getTagBody());
			}
			if (t.getTagName().equals("ServiceCost")) {
				setServiceCost(t.getTagBody());
			}
			if (t.getTagName().equals("ServiceDescription")) {
				setServiceDescription(t.getTagBody());
			}
			if (t.getTagName().equals("ServiceType")) {
				setServiceType(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<Services>\n");
		sb.append("<ServiceId>" + serviceId + "</ServiceId>\n");
		sb.append("<ServiceName>" + serviceName + "</ServiceName>\n");
		sb.append("<ServiceCost>" + serviceCost + "</ServiceCost>\n");
		sb.append("<ServiceDescription>" + serviceDescription
				+ "</ServiceDescription>\n");
		sb.append("<ServiceType>" + serviceType + "</ServiceType>\n");
		sb.append("</Services>\n");
		return sb.toString();
	}
	
	public String existsAs(){
		ServicesDAO db = new ServicesDAO();
		Vector<Services> v = db.selectServices(this);
		if(v.size() > 0){
			return ((Services)v.get(0)).getName();
		}
		return null;
	}
	
	public Vector<Services> getValues(){
		ServicesDAO db = new ServicesDAO();
		return db.getServices();
	}
	
	public void save(boolean overwrite){
		ServicesDAO db = new ServicesDAO();
		if (overwrite){
			db.addOrUpdateServices(this);
		}else{
			db.addServices(this);
		}
	}

	public void convertNulls() {
		if (serviceId == null) {
			serviceId = "";
		}

		if (serviceName == null) {
			serviceName = "";
		}

		if (serviceCost == null) {
			serviceCost = "";
		}

		if (serviceDescription == null) {
			serviceDescription = "";
		}

		if (serviceType == null) {
			serviceType = "";
		}

	}

	public Vector<String> validate() {
		Vector<String> v = new Vector<String>();
		checkStrings(v);
		checkNumbers(v);
		checkDates(v);
		return v;
	}

	private void checkStrings(Vector<String> errors) {
	}

	private void checkNumbers(Vector<String> errors) {
	}

	private void checkDates(Vector<String> errors) {
	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
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
	 * Get the value of serviceId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * Set the value of serviceId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setServiceId(String s) {
		serviceId = s;
	}

	/**
	 * Get the value of serviceName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Set the value of serviceName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setServiceName(String s) {
		serviceName = s;
	}

	/**
	 * Get the value of serviceCost
	 * 
	 * @return a <code>String</code> value
	 */
	public String getServiceCost() {
		return serviceCost;
	}

	/**
	 * Set the value of serviceCost
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setServiceCost(String s) {
		serviceCost = s;
	}

	/**
	 * Get the value of serviceDescription
	 * 
	 * @return a <code>String</code> value
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}

	/**
	 * Set the value of serviceDescription
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setServiceDescription(String s) {
		serviceDescription = s;
	}

	/**
	 * Get the value of serviceType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * Set the value of serviceType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setServiceType(String s) {
		serviceType = s;
	}

	public String getDescription() {
		
		return serviceDescription;
	}

	public String getFullDescription() {
		
		return serviceName+"\n\n"+serviceDescription;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return serviceId;
	}
	
	public void setId(String id){
		serviceId = id;
	}

}
