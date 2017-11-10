package initcheck.database;

import initcheck.character.library.LibraryItem;
import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class FeatPackage implements Serializable, Exportable, LibraryItem {

	private static final long serialVersionUID = 1L;

	private String packageName;

	private Vector<FeatPackageItem> feats = new Vector<FeatPackageItem>();

	String description;

	String id;

	public String listFormat(){
		return StrManip.pad(packageName, 30);
	}
	
	public String toString() {
		return packageName;
	}

	public String getDescription() {
		return description;
	}

	public String getFullDescription() {
		String featStr = description + "\n";
		for (int i = 0; i < feats.size(); i++) {
			FeatPackageItem item = (FeatPackageItem) feats.get(i);
			featStr += item.getFeatName() + "\n";
		}
		return featStr;
	}

	public FeatPackage() {

	}

	public FeatPackage(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("FeatPackageItem")) {
				feats.add(new FeatPackageItem(t.getTagBody()));
			}
			if (t.getTagName().equals("PackageName")) {
				setPackageName(t.getTagBody());
			}
			if (t.getTagName().equals("Description")) {
				setDescription(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<FeatPackage>\n");
		sb.append("<PackageName>" + packageName + "</PackageName>\n");
		for (int i = 0; i < feats.size(); i++) {
			FeatPackageItem item = (FeatPackageItem) feats.get(i);
			sb.append(item.exportFormat());
		}
		sb.append("<Description>" + description + "</Description>\n");
		sb.append("</FeatPackage>\n");
		return sb.toString();
	}

	/**
	 * Get the PackageName value.
	 * 
	 * @return the PackageName value.
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * Set the PackageName value.
	 * 
	 * @param newPackageName
	 *            The new PackageName value.
	 */
	public void setPackageName(String newPackageName) {
		this.packageName = newPackageName;
	}

	/**
	 * Get the Feats value.
	 * 
	 * @return the Feats value.
	 */
	public Vector<FeatPackageItem> getFeats() {
		return feats;
	}

	/**
	 * Set the Feats value.
	 * 
	 * @param newFeats
	 *            The new Feats value.
	 */
	public void setFeats(Vector<FeatPackageItem> newFeats) {
		this.feats = newFeats;
	}

	/**
	 * Get the Id value.
	 * 
	 * @return the Id value.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the Id value.
	 * 
	 * @param newId
	 *            The new Id value.
	 */
	public void setId(String newId) {
		this.id = newId;
	}

	/**
	 * Set the Description value.
	 * 
	 * @param newDescription
	 *            The new Description value.
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
