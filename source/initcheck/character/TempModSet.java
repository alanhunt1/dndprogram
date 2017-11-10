package initcheck.character;

import initcheck.database.PlayerTempMod;
import initcheck.database.Weapon;
import initcheck.utils.StrManip;

import java.util.Hashtable;
import java.util.Vector;

public class TempModSet extends Vector<PlayerTempMod>{

	private static final long serialVersionUID = 1L;

	public TempModSet(){
		
	}
	
	public TempModSet(Vector<PlayerTempMod> v){
		this.addAll(v);
	}
	
	public int getTempWill() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			
			if (ptm.getWillMod() != null) {
				if (ptm.isConflict() && ptm.getWillMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getWillMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getWillMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempRef() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getRefMod() != null) {
				if (ptm.isConflict() && ptm.getRefMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getRefMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getRefMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempFort() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getFortMod() != null) {
				if (ptm.isConflict() && ptm.getFortMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getFortMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getFortMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempStr() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getStrMod() != null) {
				if (ptm.isConflict() && ptm.getStrMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getStrMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getStrMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempDex() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getDexMod() != null) {
				if (ptm.isConflict() && ptm.getDexMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getDexMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getDexMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempCon() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getConMod() != null) {
				if (ptm.isConflict() && ptm.getConMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getConMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getConMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempInt() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getIntMod() != null) {
				if (ptm.isConflict() && ptm.getIntMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getIntMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getIntMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempWis() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getWisMod() != null) {
				if (ptm.isConflict() && ptm.getWisMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getWisMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getWisMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempCha() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getChaMod() != null) {
				if (ptm.isConflict() && ptm.getChaMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getChaMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getChaMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempStrCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getStrCheckMod() != null) {
				if (ptm.isConflict() && ptm.getStrCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getStrCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getStrCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempDexCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getDexCheckMod() != null) {
				if (ptm.isConflict() && ptm.getDexCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getDexCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getDexCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempConCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getConCheckMod() != null) {
				if (ptm.isConflict() && ptm.getConCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getConCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getConCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempIntCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getIntCheckMod() != null) {
				if (ptm.isConflict() && ptm.getIntCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getIntCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getIntCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempWisCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getWisCheckMod() != null) {
				if (ptm.isConflict() && ptm.getWisCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getWisCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getWisCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	public int getTempChaCheck() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getChaCheckMod() != null) {
				if (ptm.isConflict() && ptm.getChaCheckMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getChaCheckMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getChaCheckMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}
	
	public int getTempAttack() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getAttackMod() != null && !ptm.isConditional()) {
				if (ptm.isConflict() && ptm.getAttackMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getAttackMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getAttackMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempMelee() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getMeleeMod() != null && !ptm.isConditional()) {
				if (ptm.isConflict() && ptm.getMeleeMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getMeleeMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getMeleeMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempConditionalMelee(Weapon w) {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getMeleeMod() != null && w.isApplied(ptm.getId())) {
				if (ptm.isConflict() && ptm.getMeleeMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getMeleeMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getMeleeMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempRanged() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getRangeMod() != null && !ptm.isConditional()) {
				if (ptm.isConflict() && ptm.getRangeMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getRangeMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getRangeMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempConditionalRanged(Weapon w) {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getRangeMod() != null && w.isApplied(ptm.getId())) {
				if (ptm.isConflict() && ptm.getRangeMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getRangeMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getRangeMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempDamage() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getDamageMod() != null && !ptm.isConditional()) {
				if (ptm.isConflict() && ptm.getDamageMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getDamageMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getDamageMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempConditionalDamage(Weapon w) {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getDamageMod() != null && w.isApplied(ptm.getId())) {
				if (ptm.isConflict() && ptm.getDamageMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getDamageMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getDamageMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}

		return ac;
	}

	public int getTempNatAc() {
		return Integer.parseInt(getTempNatAcCalc().getDisplayValue());
	}
	public Calculation getTempNatAcCalc() {
		Calculation calc = new Calculation();
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (!StrManip.isNullZeroOrEmpty(ptm.getNatMod())) {
				if (ptm.isConflict() && ptm.getNatMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						calc.addElement(ptm.getName() + " : "
								+ Integer.parseInt(ptm.getNatMax()));
						ac += Integer.parseInt(ptm.getNatMax());
					}else{
					calc.addElement(ptm.getName() + " : Conflict ");
					}
				}else{
					calc.addElement(ptm.getName() + " : "
							+ Integer.parseInt(ptm.getNatMod()));
					ac += Integer.parseInt(ptm.getNatMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		calc.setDisplayValue(""+ac);
		return calc;
	}

	public int getTempAc() {
		return Integer.parseInt(getTempAcCalc().getDisplayValue());
	}
	public Calculation getTempAcCalc() {
		Calculation calc = new Calculation();
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (!StrManip.isNullZeroOrEmpty(ptm.getAcMod())) {
				if (ptm.isConflict() && ptm.getAcMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getAcMax());
						calc.addElement(ptm.getName() + " : "
								+ Integer.parseInt(ptm.getAcMax()));
					}else{
					calc.addElement(ptm.getName() + " : Conflict ");
					}
				}else{
					calc.addElement(ptm.getName() + " : "
							+ Integer.parseInt(ptm.getAcMod()));
					ac += Integer.parseInt(ptm.getAcMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		calc.setDisplayValue(""+ac);
		return calc;
	}

	public int getTempRestAc() {
		return Integer.parseInt(getTempRestAcCalc().getDisplayValue());
	}
	public Calculation getTempRestAcCalc() {
		Calculation calc = new Calculation();
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (!StrManip.isNullZeroOrEmpty(ptm.getRestMod())) {
				if (ptm.isConflict() && ptm.getRestMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						calc.addElement(ptm.getName() + " : "
								+ Integer.parseInt(ptm.getRestMax()));
						ac += Integer.parseInt(ptm.getRestMax());
					}else{
					calc.addElement(ptm.getName() + " : Conflict ");
					}
				}else{
					calc.addElement(ptm.getName() + " : "
							+ Integer.parseInt(ptm.getRestMod()));
					ac += Integer.parseInt(ptm.getRestMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		calc.setDisplayValue(""+ac);
		return calc;
	}

	public int getTempTouchAc() {
		int ac = 0;
		Hashtable<String, String> modHash = new Hashtable<String, String>();
		for (int i = 0; i < this.size(); i++) {
			PlayerTempMod ptm = (PlayerTempMod) this.get(i);
			if (ptm.getTouchMod() != null) {
				if (ptm.isConflict() && ptm.getTouchMax() != null ){
					if (modHash.get(ptm.getModType()) == null){
						ac += Integer.parseInt(ptm.getTouchMax());
					}
				}else{
					ac += Integer.parseInt(ptm.getTouchMod());
				}
			}
			modHash.put(ptm.getModType(), "T");
		}
		return ac;
	}

	
}
