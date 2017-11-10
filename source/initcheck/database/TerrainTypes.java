package initcheck.database;

import java.io.Serializable;
import java.util.Vector;

import initcheck.graphics.TiledListItem;

public class TerrainTypes implements Serializable, Cloneable, TiledListItem {

	private static final long serialVersionUID = 1L;

	private String id;

	private String terrainName = "";

	private String mapType;

	private String terrainTypeId;

	private String icon;

	private String renderType;

	private String movementRate;

	private String primaryColor;

	private String pattern;

	private String secondaryColor;

	private boolean point;

	private boolean line;

	private boolean area;

	private boolean flood;

	private String image1;

	private String image2;

	private String image3;

	
	
	public TerrainTypes() {

	}

	public String toString(){
		return terrainName;
	}
	
	public String toLog(){
		StringBuffer sb = new StringBuffer();
		sb.append("NAME : "+terrainName+"\n");
		sb.append("MAP TYPE : "+mapType+"\n");
		sb.append("ICON : "+icon+"\n");
		sb.append("RENDER : "+renderType+"\n");
		sb.append("MOVEMENT : "+movementRate+"\n");
		sb.append("POINT : "+point+"\n");
		sb.append("LINE : "+line+"\n");
		sb.append("AREA : "+area+"\n");
		sb.append("FLOOD : "+flood+"\n");
		return sb.toString();
	}
	
	public void readImport(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("TerrainName")) {
				setTerrainName(t.getTagBody());
			}
			if (t.getTagName().equals("MapType")) {
				setMapType(t.getTagBody());
			}
			if (t.getTagName().equals("TerrainTypeId")) {
				setTerrainTypeId(t.getTagBody());
			}
			if (t.getTagName().equals("Icon")) {
				setIcon(t.getTagBody());
			}
			if (t.getTagName().equals("RenderType")) {
				setRenderType(t.getTagBody());
			}
			if (t.getTagName().equals("MovementRate")) {
				setMovementRate(t.getTagBody());
			}
			if (t.getTagName().equals("PrimaryColor")) {
				setPrimaryColor(t.getTagBody());
			}
			if (t.getTagName().equals("Pattern")) {
				setPattern(t.getTagBody());
			}
			if (t.getTagName().equals("SecondaryColor")) {
				setSecondaryColor(t.getTagBody());
			}
			if (t.getTagName().equals("Point")) {
				setPoint(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("Line")) {
				setLine(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("Area")) {
				setArea(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("Flood")) {
				setFlood(t.getTagBody().equals("true"));
			}
			if (t.getTagName().equals("Image1")) {
				setImage1(t.getTagBody());
			}
			if (t.getTagName().equals("Image2")) {
				setImage2(t.getTagBody());
			}
			if (t.getTagName().equals("Image3")) {
				setImage3(t.getTagBody());
			}
			s = s.substring((2 * tagName.length()) + 5 + tagBody.length(), s
					.length());
			t = Tag.getTag(s);
		}
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<TerrainTypes>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<TerrainName>" + terrainName + "</TerrainName>\n");
		sb.append("<MapType>" + mapType + "</MapType>\n");
		sb.append("<TerrainTypeId>" + terrainTypeId + "</TerrainTypeId>\n");
		sb.append("<Icon>" + icon + "</Icon>\n");
		sb.append("<RenderType>" + renderType + "</RenderType>\n");
		sb.append("<MovementRate>" + movementRate + "</MovementRate>\n");
		sb.append("<PrimaryColor>" + primaryColor + "</PrimaryColor>\n");
		sb.append("<Pattern>" + pattern + "</Pattern>\n");
		sb.append("<SecondaryColor>" + secondaryColor + "</SecondaryColor>\n");
		sb.append("<Point>" + point + "</Point>\n");
		sb.append("<Line>" + line + "</Line>\n");
		sb.append("<Area>" + area + "</Area>\n");
		sb.append("<Flood>" + flood + "</Flood>\n");
		sb.append("<Image1>" + image1 + "</Image1>\n");
		sb.append("<Image2>" + image2 + "</Image2>\n");
		sb.append("<Image3>" + image3 + "</Image3>\n");
		sb.append("</TerrainTypes>\n");
		return sb.toString();
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (terrainName == null) {
			terrainName = "";
		}

		if (mapType == null) {
			mapType = "";
		}

		if (terrainTypeId == null) {
			terrainTypeId = "";
		}

		if (icon == null) {
			icon = "";
		}

		if (renderType == null) {
			renderType = "";
		}

		if (movementRate == null) {
			movementRate = "";
		}

		if (primaryColor == null) {
			primaryColor = "";
		}

		if (pattern == null) {
			pattern = "";
		}

		if (secondaryColor == null) {
			secondaryColor = "";
		}

		
		if (image1 == null) {
			image1 = "";
		}

		if (image2 == null) {
			image2 = "";
		}

		if (image3 == null) {
			image3 = "";
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
		if (terrainTypeId != null && !terrainTypeId.equals("")) {
			try {
				Integer.parseInt(terrainTypeId);
			} catch (Exception e) {
				errors
						.add("terrainTypeId is not a valid number.  Please enter a valid number.");
			}
		}
		if (movementRate != null && !movementRate.equals("")) {
			try {
				Integer.parseInt(movementRate);
			} catch (Exception e) {
				errors
						.add("movementRate is not a valid number.  Please enter a valid number.");
			}
		}
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
	 * Get the value of id
	 * 
	 * @return a <code>String</code> value
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the value of id
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setId(String s) {
		id = s;
	}

	/**
	 * Get the value of terrainName
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTerrainName() {
		return terrainName;
	}

	/**
	 * Set the value of terrainName
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTerrainName(String s) {
		terrainName = s;
	}

	/**
	 * Get the value of mapType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMapType() {
		return mapType;
	}

	/**
	 * Set the value of mapType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMapType(String s) {
		mapType = s;
	}

	/**
	 * Get the value of terrainTypeId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTerrainTypeId() {
		return terrainTypeId;
	}

	/**
	 * Set the value of terrainTypeId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTerrainTypeId(String s) {
		terrainTypeId = s;
	}

	/**
	 * Get the value of icon
	 * 
	 * @return a <code>String</code> value
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Set the value of icon
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setIcon(String s) {
		icon = s;
	}

	/**
	 * Get the value of renderType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRenderType() {
		return renderType;
	}

	/**
	 * Set the value of renderType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRenderType(String s) {
		renderType = s;
	}

	/**
	 * Get the value of movementRate
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMovementRate() {
		return movementRate;
	}

	/**
	 * Set the value of movementRate
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMovementRate(String s) {
		movementRate = s;
	}

	/**
	 * Get the value of primaryColor
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPrimaryColor() {
		return primaryColor;
	}

	/**
	 * Set the value of primaryColor
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPrimaryColor(String s) {
		primaryColor = s;
	}

	/**
	 * Get the value of pattern
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * Set the value of pattern
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPattern(String s) {
		pattern = s;
	}

	/**
	 * Get the value of secondaryColor
	 * 
	 * @return a <code>String</code> value
	 */
	public String getSecondaryColor() {
		return secondaryColor;
	}

	/**
	 * Set the value of secondaryColor
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setSecondaryColor(String s) {
		secondaryColor = s;
	}

	
	public boolean isArea() {
		return area;
	}

	public void setArea(boolean area) {
		this.area = area;
	}

	public boolean isFlood() {
		return flood;
	}

	public void setFlood(boolean flood) {
		this.flood = flood;
	}

	public boolean isLine() {
		return line;
	}

	public void setLine(boolean line) {
		this.line = line;
	}

	public boolean isPoint() {
		return point;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}

	/**
	 * Get the value of image1
	 * 
	 * @return a <code>String</code> value
	 */
	public String getImage1() {
		return image1;
	}

	/**
	 * Set the value of image1
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setImage1(String s) {
		image1 = s;
	}

	/**
	 * Get the value of image2
	 * 
	 * @return a <code>String</code> value
	 */
	public String getImage2() {
		return image2;
	}

	/**
	 * Set the value of image2
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setImage2(String s) {
		image2 = s;
	}

	/**
	 * Get the value of image3
	 * 
	 * @return a <code>String</code> value
	 */
	public String getImage3() {
		return image3;
	}

	/**
	 * Set the value of image3
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setImage3(String s) {
		image3 = s;
	}

}
