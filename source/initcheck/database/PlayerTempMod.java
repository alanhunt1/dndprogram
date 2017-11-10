package initcheck.database;

import initcheck.utils.StrManip;

import java.io.Serializable;
import java.util.Vector;

public class PlayerTempMod implements Serializable, Cloneable {

	
	private static final long serialVersionUID = 1L;

	private String id;

	private String playerId;

	private String name;

	private String modType;

	private String linkType;

	private String linkId;

	// various mods
	
	private String strMod;

	private String conMod;

	private String dexMod;

	private String intMod;

	private String wisMod;

	private String chaMod;

	private String natMod;

	private String acMod;

	private String restMod;

	private String touchMod;

	private String meleeMod;

	private String rangeMod;

	private String damageMod;

	private String fortMod;

	private String refMod;

	private String willMod;

	private String attackMod;

	private String strCheckMod;

	private String conCheckMod;

	private String dexCheckMod;

	private String intCheckMod;

	private String wisCheckMod;

	private String chaCheckMod;

	private String featMod;
	
	// max values (for stacking conflicts)
	
	private String strMax;

	private String conMax;

	private String dexMax;

	private String intMax;

	private String wisMax;

	private String chaMax;

	private String natMax;

	private String acMax;

	private String restMax;

	private String touchMax;

	private String meleeMax;

	private String rangeMax;

	private String damageMax;

	private String fortMax;

	private String refMax;

	private String willMax;

	private String attackMax;

	private String strCheckMax;

	private String conCheckMax;

	private String dexCheckMax;

	private String intCheckMax;

	private String wisCheckMax;

	private String chaCheckMax;

	private String featMax;
	
	private boolean conflict;
	
	
	
	boolean conditional;

	public String getChaCheckMod() {
		return chaCheckMod;
	}

	public void setChaCheckMod(String chaCheckMod) {
		this.chaCheckMod = chaCheckMod;
	}

	public String getConCheckMod() {
		return conCheckMod;
	}

	public void setConCheckMod(String conCheckMod) {
		this.conCheckMod = conCheckMod;
	}

	public String getDexCheckMod() {
		return dexCheckMod;
	}

	public void setDexCheckMod(String dexCheckMod) {
		this.dexCheckMod = dexCheckMod;
	}

	public String getIntCheckMod() {
		return intCheckMod;
	}

	public void setIntCheckMod(String intCheckMod) {
		this.intCheckMod = intCheckMod;
	}

	public String getStrCheckMod() {
		return strCheckMod;
	}

	public void setStrCheckMod(String strCheckMod) {
		this.strCheckMod = strCheckMod;
	}

	public String getWisCheckMod() {
		return wisCheckMod;
	}

	public void setWisCheckMod(String wisCheckMod) {
		this.wisCheckMod = wisCheckMod;
	}

	public PlayerTempMod() {

	}

	public PlayerTempMod(String s) {
		Tag t = Tag.getTag(s);
		while (t != null) {
			String tagName = t.getTagName();
			String tagBody = t.getTagBody();
			if (t.getTagName().equals("Id")) {
				setId(t.getTagBody());
			}
			if (t.getTagName().equals("PlayerId")) {
				setPlayerId(t.getTagBody());
			}
			if (t.getTagName().equals("Name")) {
				setName(t.getTagBody());
			}
			if (t.getTagName().equals("ModType")) {
				setModType(t.getTagBody());
			}
			if (t.getTagName().equals("LinkType")) {
				setLinkType(t.getTagBody());
			}
			if (t.getTagName().equals("LinkId")) {
				setLinkId(t.getTagBody());
			}
			if (t.getTagName().equals("StrMod")) {
				setStrMod(t.getTagBody());
			}
			if (t.getTagName().equals("ConMod")) {
				setConMod(t.getTagBody());
			}
			if (t.getTagName().equals("DexMod")) {
				setDexMod(t.getTagBody());
			}
			if (t.getTagName().equals("IntMod")) {
				setIntMod(t.getTagBody());
			}
			if (t.getTagName().equals("WisMod")) {
				setWisMod(t.getTagBody());
			}
			if (t.getTagName().equals("ChaMod")) {
				setChaMod(t.getTagBody());
			}
			if (t.getTagName().equals("StrCheckMod")) {
				setStrCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("ConCheckMod")) {
				setConCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("DexCheckMod")) {
				setDexCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("IntCheckMod")) {
				setIntCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("WisCheckMod")) {
				setWisCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("ChaCheckMod")) {
				setChaCheckMod(t.getTagBody());
			}
			if (t.getTagName().equals("NatMod")) {
				setNatMod(t.getTagBody());
			}
			if (t.getTagName().equals("AcMod")) {
				setAcMod(t.getTagBody());
			}
			if (t.getTagName().equals("RestMod")) {
				setRestMod(t.getTagBody());
			}
			if (t.getTagName().equals("TouchMod")) {
				setTouchMod(t.getTagBody());
			}
			if (t.getTagName().equals("MeleeMod")) {
				setMeleeMod(t.getTagBody());
			}
			if (t.getTagName().equals("RangeMod")) {
				setRangeMod(t.getTagBody());
			}
			if (t.getTagName().equals("DamageMod")) {
				setDamageMod(t.getTagBody());
			}
			if (t.getTagName().equals("FortMod")) {
				setFortMod(t.getTagBody());
			}
			if (t.getTagName().equals("RefMod")) {
				setRefMod(t.getTagBody());
			}
			if (t.getTagName().equals("WillMod")) {
				setWillMod(t.getTagBody());
			}
			if (t.getTagName().equals("Conditional")) {
				setConditional(Boolean.getBoolean(t.getTagBody()));
			}
			if (t.getTagName().equals("AttackMod")) {
				setAttackMod(t.getTagBody());
			}
			if (t.getTagName().equals("FeatMod")) {
				setFeatMod(t.getTagBody());
			}
			s = s.substring(tagName.length() + tagName.length() + 5
					+ tagBody.length(), s.length());
			t = Tag.getTag(s);
		}
		convertToNull();
	}

	public String exportFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("<PlayerTempMod>\n");
		sb.append("<Id>" + id + "</Id>\n");
		sb.append("<PlayerId>" + playerId + "</PlayerId>\n");
		sb.append("<Name>" + name + "</Name>\n");
		sb.append("<ModType>" + modType + "</ModType>\n");
		sb.append("<LinkType>" + linkType + "</LinkType>\n");
		sb.append("<LinkId>" + linkId + "</LinkId>\n");
		sb.append("<StrMod>" + strMod + "</StrMod>\n");
		sb.append("<ConMod>" + conMod + "</ConMod>\n");
		sb.append("<DexMod>" + dexMod + "</DexMod>\n");
		sb.append("<IntMod>" + intMod + "</IntMod>\n");
		sb.append("<WisMod>" + wisMod + "</WisMod>\n");
		sb.append("<ChaMod>" + chaMod + "</ChaMod>\n");
		sb.append("<StrCheckMod>" + strCheckMod + "</StrCheckMod>\n");
		sb.append("<ConCheckMod>" + conCheckMod + "</ConCheckMod>\n");
		sb.append("<DexCheckMod>" + dexCheckMod + "</DexCheckMod>\n");
		sb.append("<IntCheckMod>" + intCheckMod + "</IntCheckMod>\n");
		sb.append("<WisCheckMod>" + wisCheckMod + "</WisCheckMod>\n");
		sb.append("<ChaCheckMod>" + chaCheckMod + "</ChaCheckMod>\n");
		sb.append("<NatMod>" + natMod + "</NatMod>\n");
		sb.append("<AcMod>" + acMod + "</AcMod>\n");
		sb.append("<RestMod>" + restMod + "</RestMod>\n");
		sb.append("<TouchMod>" + touchMod + "</TouchMod>\n");
		sb.append("<MeleeMod>" + meleeMod + "</MeleeMod>\n");
		sb.append("<RangeMod>" + rangeMod + "</RangeMod>\n");
		sb.append("<DamageMod>" + damageMod + "</DamageMod>\n");
		sb.append("<FortMod>" + fortMod + "</FortMod>\n");
		sb.append("<RefMod>" + refMod + "</RefMod>\n");
		sb.append("<WillMod>" + willMod + "</WillMod>\n");
		sb.append("<Conditional>" + conditional + "</Conditional>\n");
		sb.append("<AttackMod>" + attackMod + "</AttackMod>\n");
		sb.append("<FeatMod>" + featMod + "</FeatMod>\n");
		sb.append("</PlayerTempMod>\n");
		return sb.toString();
	}

	public String toString() {
		String tostr = pad(name, 40) + " " + pad(modType, 15) + " "
				+ pad(strMod, 3) + " " + pad(conMod, 3) + " " + pad(dexMod, 3)
				+ " " + pad(intMod, 3) + " " + pad(wisMod, 3) + " "
				+ pad(chaMod, 3) + " " + pad(strCheckMod, 3) + " " + pad(conCheckMod, 3) + " " + pad(dexCheckMod, 3)
				+ " " + pad(intCheckMod, 3) + " " + pad(wisCheckMod, 3) + " "
				+ pad(chaCheckMod, 3) + " "  + pad(natMod, 3) + " " + pad(acMod, 3)
				+ " " + pad(restMod, 3) + " " + pad(touchMod, 3) + " "
				+ pad(meleeMod, 3) + " " + pad(rangeMod, 3) + " "
				+ pad(damageMod, 3) + " " + pad(fortMod, 3) + " "
				+ pad(refMod, 3) + " " + pad(willMod, 3) + " "
				+ pad(attackMod, 3) + " "+ pad(featMod, 3);

		if (conditional) {
			tostr += "Cond";
		}

		return tostr;

	}

	public String pad(String s, int l) {
		if (s == null) {
			s = "0";
		}
		int len = l;
		if (s.length() > len){
			len = s.length();
		}
		char[] chars = new char[l];
		for (int i = 0; i < s.length(); i++) {
			chars[i] = s.charAt(i);
		}
		for (int i = s.length(); i < l; i++) {
			chars[i] = ' ';
		}
		return new String(chars);
	}

	public void convertNulls() {
		if (id == null) {
			id = "";
		}

		if (playerId == null) {
			playerId = "";
		}

		if (name == null) {
			name = "";
		}

		if (modType == null) {
			modType = "";
		}

		if (linkType == null) {
			linkType = "";
		}

		if (linkId == null) {
			linkId = "";
		}

		if (strMod == null) {
			strMod = "";
		}

		if (conMod == null) {
			conMod = "";
		}

		if (dexMod == null) {
			dexMod = "";
		}

		if (intMod == null) {
			intMod = "";
		}

		if (wisMod == null) {
			wisMod = "";
		}

		if (chaMod == null) {
			chaMod = "";
		}

		if (natMod == null) {
			natMod = "";
		}

		if (acMod == null) {
			acMod = "";
		}

		if (restMod == null) {
			restMod = "";
		}

		if (touchMod == null) {
			touchMod = "";
		}

		if (meleeMod == null) {
			meleeMod = "";
		}

		if (rangeMod == null) {
			rangeMod = "";
		}

		if (damageMod == null) {
			damageMod = "";
		}

		if (fortMod == null) {
			fortMod = "";
		}

		if (refMod == null) {
			refMod = "";
		}

		if (willMod == null) {
			willMod = "";
		}
		
		if (attackMod == null) {
			attackMod = "";
		}
		
		if (featMod == null) {
			featMod = "";
		}
		if (strCheckMod == null) {
			strCheckMod = "";
		}

		if (conCheckMod == null) {
			conCheckMod = "";
		}

		if (dexCheckMod == null) {
			dexCheckMod = "";
		}

		if (intCheckMod == null) {
			intCheckMod = "";
		}

		if (wisCheckMod == null) {
			wisCheckMod = "";
		}

		if (chaCheckMod == null) {
			chaCheckMod = "";
		}
		

	}

	public void convertToNull() {
		if (id.equals("null")) {
			id = null;
		}

		if (playerId.equals("null")) {
			playerId = null;
		}

		if (name.equals("null")) {
			name = null;
		}

		if (modType.equals("null")) {
			modType = null;
		}

		if (linkType.equals("null")) {
			linkType = null;
		}

		if (linkId.equals("null")) {
			linkId = null;
		}

		if (strMod.equals("null")) {
			strMod = null;
		}

		if (conMod.equals("null")) {
			conMod = null;
		}

		if (dexMod.equals("null")) {
			dexMod = null;
		}

		if (intMod.equals("null")) {
			intMod = null;
		}

		if (wisMod.equals("null")) {
			wisMod = null;
		}

		if (chaMod.equals("null")) {
			chaMod = null;
		}

		if (natMod.equals("null")) {
			natMod = null;
		}

		if (acMod.equals("null")) {
			acMod = null;
		}

		if (restMod.equals("null")) {
			restMod = null;
		}

		if (touchMod.equals("null")) {
			touchMod = null;
		}

		if (meleeMod.equals("null")) {
			meleeMod = null;
		}

		if (rangeMod.equals("null")) {
			rangeMod = null;
		}

		if (damageMod.equals("null")) {
			damageMod = null;
		}

		if (fortMod.equals("null")) {
			fortMod = null;
		}

		if (refMod.equals("null")) {
			refMod = null;
		}

		if (willMod.equals("null")) {
			willMod = null;
		}

	}

	public void convertEmpty() {

		if (strMod.equals("")) {
			strMod = "null";
		}

		if (conMod.equals("")) {
			conMod = "null";
		}

		if (dexMod.equals("")) {
			dexMod = "null";
		}

		if (intMod.equals("")) {
			intMod = "null";
		}

		if (wisMod.equals("")) {
			wisMod = "null";
		}

		if (chaMod.equals("")) {
			chaMod = "null";
		}

		if (strCheckMod.equals("")) {
			strCheckMod = "null";
		}

		if (conCheckMod.equals("")) {
			conCheckMod = "null";
		}

		if (dexCheckMod.equals("")) {
			dexCheckMod = "null";
		}

		if (intCheckMod.equals("")) {
			intCheckMod = "null";
		}

		if (wisCheckMod.equals("")) {
			wisCheckMod = "null";
		}

		if (chaCheckMod.equals("")) {
			chaCheckMod = "null";
		}
		
		if (natMod.equals("")) {
			natMod = "null";
		}

		if (acMod.equals("")) {
			acMod = "null";
		}

		if (restMod.equals("")) {
			restMod = "null";
		}

		if (touchMod.equals("")) {
			touchMod = "null";
		}

		if (meleeMod.equals("")) {
			meleeMod = "null";
		}

		if (rangeMod.equals("")) {
			rangeMod = "null";
		}

		if (damageMod.equals("")) {
			damageMod = "null";
		}

		if (fortMod.equals("")) {
			fortMod = "null";
		}

		if (refMod.equals("")) {
			refMod = "null";
		}

		if (willMod.equals("")) {
			willMod = "null";
		}

		if (attackMod.equals("")){
			attackMod = "null";
		}
		
		if (featMod.equals("")){
			featMod = "null";
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
		if (name.equals("")) {
			errors.add("You must enter a name for the mod.");
		}

		if (modType.equals("")) {
			errors.add("Mod type is a required field.");
		}
	}

	private void checkNumbers(Vector<String> errors) {
		if (playerId != null && !playerId.equals("")) {
			try {
				Integer.parseInt(playerId);
			} catch (Exception e) {
				errors
						.add("playerId is not a valid number.  Please enter a valid number.");
			}
		}
		if (strMod != null && !strMod.equals("")) {
			try {
				Integer.parseInt(strMod);
			} catch (Exception e) {
				errors
						.add("strMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (conMod != null && !conMod.equals("")) {
			try {
				Integer.parseInt(conMod);
			} catch (Exception e) {
				errors
						.add("conMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (dexMod != null && !dexMod.equals("")) {
			try {
				Integer.parseInt(dexMod);
			} catch (Exception e) {
				errors
						.add("dexMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (intMod != null && !intMod.equals("")) {
			try {
				Integer.parseInt(intMod);
			} catch (Exception e) {
				errors
						.add("intMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (wisMod != null && !wisMod.equals("")) {
			try {
				Integer.parseInt(wisMod);
			} catch (Exception e) {
				errors
						.add("wisMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (chaMod != null && !chaMod.equals("")) {
			try {
				Integer.parseInt(chaMod);
			} catch (Exception e) {
				errors
						.add("chaMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (natMod != null && !natMod.equals("")) {
			try {
				Integer.parseInt(natMod);
			} catch (Exception e) {
				errors
						.add("natMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (acMod != null && !acMod.equals("")) {
			try {
				Integer.parseInt(acMod);
			} catch (Exception e) {
				errors
						.add("acMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (restMod != null && !restMod.equals("")) {
			try {
				Integer.parseInt(restMod);
			} catch (Exception e) {
				errors
						.add("restMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (touchMod != null && !touchMod.equals("")) {
			try {
				Integer.parseInt(touchMod);
			} catch (Exception e) {
				errors
						.add("touchMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (meleeMod != null && !meleeMod.equals("")) {
			try {
				Integer.parseInt(meleeMod);
			} catch (Exception e) {
				errors
						.add("meleeMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (rangeMod != null && !rangeMod.equals("")) {
			try {
				Integer.parseInt(rangeMod);
			} catch (Exception e) {
				errors
						.add("rangeMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (damageMod != null && !damageMod.equals("")) {
			try {
				Integer.parseInt(damageMod);
			} catch (Exception e) {
				errors
						.add("damageMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (fortMod != null && !fortMod.equals("")) {
			try {
				Integer.parseInt(fortMod);
			} catch (Exception e) {
				errors
						.add("fortMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (refMod != null && !refMod.equals("")) {
			try {
				Integer.parseInt(refMod);
			} catch (Exception e) {
				errors
						.add("refMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (willMod != null && !willMod.equals("")) {
			try {
				Integer.parseInt(willMod);
			} catch (Exception e) {
				errors
						.add("willMod is not a valid number.  Please enter a valid number.");
			}
		}
		if (attackMod != null && !attackMod.equals("")) {
			try {
				Integer.parseInt(attackMod);
			} catch (Exception e) {
				errors
						.add("attackMod is not a valid number.  Please enter a valid number.");
			}
		}
	}

	public void checkConflict(Vector<PlayerTempMod> v){
		if (v == null)
			return;
			
		for (PlayerTempMod curr:v){
			if (curr.getId() == this.id)
				continue;
			
			if (!StrManip.isNullZeroOrEmpty(curr.getStrMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getStrMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getStrMod());
					int thisVal = Integer.parseInt(strMod);
					if (StrManip.isNullZeroOrEmpty(strMax)){
						strMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(strMax);
						strMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getConMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getConMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getConMod());
					int thisVal = Integer.parseInt(conMod);
					if (StrManip.isNullZeroOrEmpty(conMax)){
						conMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(conMax);
						conMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getDexMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getDexMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getDexMod());
					int thisVal = Integer.parseInt(dexMod);
					if (StrManip.isNullZeroOrEmpty(dexMax)){
						dexMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(dexMax);
						dexMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getIntMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getIntMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getIntMod());
					int thisVal = Integer.parseInt(intMod);
					if (StrManip.isNullZeroOrEmpty(intMax)){
						intMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(intMax);
						intMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getWisMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getWisMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getWisMod());
					int thisVal = Integer.parseInt(wisMod);
					if (StrManip.isNullZeroOrEmpty(wisMax)){
						wisMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(wisMax);
						wisMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getChaMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getChaMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getChaMod());
					int thisVal = Integer.parseInt(chaMod);
					if (StrManip.isNullZeroOrEmpty(chaMax)){
						chaMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(dexMax);
						chaMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getNatMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getNatMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getNatMod());
					int thisVal = Integer.parseInt(natMod);
					if (StrManip.isNullZeroOrEmpty(natMax)){
						natMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(natMax);
						natMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getAcMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getAcMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getAcMod());
					int thisVal = Integer.parseInt(acMod);
					if (StrManip.isNullZeroOrEmpty(acMax)){
						acMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(acMax);
						acMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getRestMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getRestMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getRestMod());
					int thisVal = Integer.parseInt(restMod);
					if (StrManip.isNullZeroOrEmpty(restMax)){
						restMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(restMax);
						restMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getTouchMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getTouchMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getTouchMod());
					int thisVal = Integer.parseInt(touchMod);
					if (StrManip.isNullZeroOrEmpty(touchMax)){
						touchMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(touchMax);
						touchMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getMeleeMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getMeleeMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getMeleeMod());
					int thisVal = Integer.parseInt(meleeMod);
					if (StrManip.isNullZeroOrEmpty(meleeMax)){
						meleeMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(meleeMax);
						meleeMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getRangeMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getRangeMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getRangeMod());
					int thisVal = Integer.parseInt(rangeMod);
					if (StrManip.isNullZeroOrEmpty(rangeMax)){
						rangeMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(rangeMax);
						rangeMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getDamageMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getDamageMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getDamageMod());
					int thisVal = Integer.parseInt(damageMod);
					if (StrManip.isNullZeroOrEmpty(damageMax)){
						damageMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(damageMax);
						damageMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getFortMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getFortMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getFortMod());
					int thisVal = Integer.parseInt(fortMod);
					if (StrManip.isNullZeroOrEmpty(fortMax)){
						fortMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(fortMax);
						fortMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getRefMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getRefMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getRefMod());
					int thisVal = Integer.parseInt(refMod);
					if (StrManip.isNullZeroOrEmpty(refMax)){
						refMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(refMax);
						refMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getWillMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getWillMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getWillMod());
					int thisVal = Integer.parseInt(willMod);
					if (StrManip.isNullZeroOrEmpty(willMax)){
						willMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(willMax);
						willMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getAttackMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getAttackMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getAttackMod());
					int thisVal = Integer.parseInt(attackMod);
					if (StrManip.isNullZeroOrEmpty(attackMax)){
						attackMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(attackMax);
						attackMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getStrCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getStrCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getStrCheckMod());
					int thisVal = Integer.parseInt(strCheckMod);
					if (StrManip.isNullZeroOrEmpty(strCheckMax)){
						strCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(strCheckMax);
						strCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getConCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getConCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getConCheckMod());
					int thisVal = Integer.parseInt(conCheckMod);
					if (StrManip.isNullZeroOrEmpty(conCheckMax)){
						conCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(conCheckMax);
						conCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getDexCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getDexCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getDexCheckMod());
					int thisVal = Integer.parseInt(dexCheckMod);
					if (StrManip.isNullZeroOrEmpty(dexCheckMax)){
						dexCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(dexCheckMax);
						dexCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getIntCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getIntCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getIntCheckMod());
					int thisVal = Integer.parseInt(intCheckMod);
					if (StrManip.isNullZeroOrEmpty(intCheckMax)){
						intCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(intCheckMax);
						intCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getWisCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getWisCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getWisCheckMod());
					int thisVal = Integer.parseInt(wisCheckMod);
					if (StrManip.isNullZeroOrEmpty(wisCheckMax)){
						wisCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(wisCheckMax);
						wisCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getChaCheckMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getChaCheckMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getChaCheckMod());
					int thisVal = Integer.parseInt(chaCheckMod);
					if (StrManip.isNullZeroOrEmpty(chaCheckMax)){
						chaCheckMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(chaCheckMax);
						chaCheckMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
			}

			if (!StrManip.isNullZeroOrEmpty(curr.getFeatMod())){
				if (!StrManip.isNullZeroOrEmpty(this.getFeatMod())){
					conflict = true;
					int currVal = Integer.parseInt(curr.getFeatMod());
					int thisVal = Integer.parseInt(featMod);
					if (StrManip.isNullZeroOrEmpty(featMax)){
						featMax = ""+Math.max(currVal, thisVal);
					}else{
						int maxVal = Integer.parseInt(featMax);
						featMax = ""+Math.max(Math.max(currVal, thisVal), maxVal);
					}					
				}
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
	 * Get the value of playerId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * Set the value of playerId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setPlayerId(String s) {
		playerId = s;
	}

	/**
	 * Get the value of name
	 * 
	 * @return a <code>String</code> value
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the value of name
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * Get the value of modType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getModType() {
		return modType;
	}

	/**
	 * Set the value of modType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setModType(String s) {
		modType = s;
	}

	/**
	 * Get the value of linkType
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLinkType() {
		return linkType;
	}

	/**
	 * Set the value of linkType
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLinkType(String s) {
		linkType = s;
	}

	/**
	 * Get the value of linkId
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLinkId() {
		return linkId;
	}

	/**
	 * Set the value of linkId
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLinkId(String s) {
		linkId = s;
	}

	/**
	 * Get the value of strMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getStrMod() {
		return strMod;
	}

	/**
	 * Set the value of strMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setStrMod(String s) {
		strMod = s;
	}

	/**
	 * Get the value of conMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getConMod() {
		return conMod;
	}

	/**
	 * Set the value of conMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setConMod(String s) {
		conMod = s;
	}

	/**
	 * Get the value of dexMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDexMod() {
		return dexMod;
	}

	/**
	 * Set the value of dexMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDexMod(String s) {
		dexMod = s;
	}

	/**
	 * Get the value of intMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getIntMod() {
		return intMod;
	}

	/**
	 * Set the value of intMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setIntMod(String s) {
		intMod = s;
	}

	/**
	 * Get the value of wisMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWisMod() {
		return wisMod;
	}

	/**
	 * Set the value of wisMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWisMod(String s) {
		wisMod = s;
	}

	/**
	 * Get the value of chaMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getChaMod() {
		return chaMod;
	}

	/**
	 * Set the value of chaMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setChaMod(String s) {
		chaMod = s;
	}

	/**
	 * Get the value of natMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getNatMod() {
		return natMod;
	}

	/**
	 * Set the value of natMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setNatMod(String s) {
		natMod = s;
	}

	/**
	 * Get the value of acMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getAcMod() {
		return acMod;
	}

	/**
	 * Set the value of acMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setAcMod(String s) {
		acMod = s;
	}

	/**
	 * Get the value of restMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRestMod() {
		return restMod;
	}

	/**
	 * Set the value of restMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRestMod(String s) {
		restMod = s;
	}

	/**
	 * Get the value of touchMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getTouchMod() {
		return touchMod;
	}

	/**
	 * Set the value of touchMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setTouchMod(String s) {
		touchMod = s;
	}

	/**
	 * Get the value of meleeMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMeleeMod() {
		return meleeMod;
	}

	/**
	 * Set the value of meleeMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMeleeMod(String s) {
		meleeMod = s;
	}

	/**
	 * Get the value of rangeMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRangeMod() {
		return rangeMod;
	}

	/**
	 * Set the value of rangeMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRangeMod(String s) {
		rangeMod = s;
	}

	/**
	 * Get the value of damageMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getDamageMod() {
		return damageMod;
	}

	/**
	 * Set the value of damageMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setDamageMod(String s) {
		damageMod = s;
	}

	/**
	 * Get the value of fortMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getFortMod() {
		return fortMod;
	}

	/**
	 * Set the value of fortMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setFortMod(String s) {
		fortMod = s;
	}

	/**
	 * Get the value of refMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getRefMod() {
		return refMod;
	}

	/**
	 * Set the value of refMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setRefMod(String s) {
		refMod = s;
	}

	/**
	 * Get the value of willMod
	 * 
	 * @return a <code>String</code> value
	 */
	public String getWillMod() {
		return willMod;
	}

	/**
	 * Set the value of willMod
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setWillMod(String s) {
		willMod = s;
	}

	public String getAttackMod() {
		return attackMod;
	}

	public void setAttackMod(String attackMod) {
		this.attackMod = attackMod;
	}

	/**
	 * Get the Conditional value.
	 * 
	 * @return the Conditional value.
	 */
	public boolean isConditional() {
		return conditional;
	}

	/**
	 * Set the Conditional value.
	 * 
	 * @param newConditional
	 *            The new Conditional value.
	 */
	public void setConditional(boolean newConditional) {
		this.conditional = newConditional;
	}

	public String getFeatMod() {
		return featMod;
	}

	public void setFeatMod(String featMod) {
		this.featMod = featMod;
	}

	public boolean isConflict() {
		return conflict;
	}

	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}

	public String getStrMax() {
		return strMax;
	}

	public void setStrMax(String strMax) {
		this.strMax = strMax;
	}

	public String getConMax() {
		return conMax;
	}

	public void setConMax(String conMax) {
		this.conMax = conMax;
	}

	public String getDexMax() {
		return dexMax;
	}

	public void setDexMax(String dexMax) {
		this.dexMax = dexMax;
	}

	public String getIntMax() {
		return intMax;
	}

	public void setIntMax(String intMax) {
		this.intMax = intMax;
	}

	public String getWisMax() {
		return wisMax;
	}

	public void setWisMax(String wisMax) {
		this.wisMax = wisMax;
	}

	public String getChaMax() {
		return chaMax;
	}

	public void setChaMax(String chaMax) {
		this.chaMax = chaMax;
	}

	public String getNatMax() {
		return natMax;
	}

	public void setNatMax(String natMax) {
		this.natMax = natMax;
	}

	public String getAcMax() {
		return acMax;
	}

	public void setAcMax(String acMax) {
		this.acMax = acMax;
	}

	public String getRestMax() {
		return restMax;
	}

	public void setRestMax(String restMax) {
		this.restMax = restMax;
	}

	public String getTouchMax() {
		return touchMax;
	}

	public void setTouchMax(String touchMax) {
		this.touchMax = touchMax;
	}

	public String getMeleeMax() {
		return meleeMax;
	}

	public void setMeleeMax(String meleeMax) {
		this.meleeMax = meleeMax;
	}

	public String getRangeMax() {
		return rangeMax;
	}

	public void setRangeMax(String rangeMax) {
		this.rangeMax = rangeMax;
	}

	public String getDamageMax() {
		return damageMax;
	}

	public void setDamageMax(String damageMax) {
		this.damageMax = damageMax;
	}

	public String getFortMax() {
		return fortMax;
	}

	public void setFortMax(String fortMax) {
		this.fortMax = fortMax;
	}

	public String getRefMax() {
		return refMax;
	}

	public void setRefMax(String refMax) {
		this.refMax = refMax;
	}

	public String getWillMax() {
		return willMax;
	}

	public void setWillMax(String willMax) {
		this.willMax = willMax;
	}

	public String getAttackMax() {
		return attackMax;
	}

	public void setAttackMax(String attackMax) {
		this.attackMax = attackMax;
	}

	public String getStrCheckMax() {
		return strCheckMax;
	}

	public void setStrCheckMax(String strCheckMax) {
		this.strCheckMax = strCheckMax;
	}

	public String getConCheckMax() {
		return conCheckMax;
	}

	public void setConCheckMax(String conCheckMax) {
		this.conCheckMax = conCheckMax;
	}

	public String getDexCheckMax() {
		return dexCheckMax;
	}

	public void setDexCheckMax(String dexCheckMax) {
		this.dexCheckMax = dexCheckMax;
	}

	public String getIntCheckMax() {
		return intCheckMax;
	}

	public void setIntCheckMax(String intCheckMax) {
		this.intCheckMax = intCheckMax;
	}

	public String getWisCheckMax() {
		return wisCheckMax;
	}

	public void setWisCheckMax(String wisCheckMax) {
		this.wisCheckMax = wisCheckMax;
	}

	public String getChaCheckMax() {
		return chaCheckMax;
	}

	public void setChaCheckMax(String chaCheckMax) {
		this.chaCheckMax = chaCheckMax;
	}

	public String getFeatMax() {
		return featMax;
	}

	public void setFeatMax(String featMax) {
		this.featMax = featMax;
	}

	
}
