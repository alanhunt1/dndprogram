package initcheck;

import initcheck.database.Weapon;

import java.util.Vector;

public class LoadComparison{

		public static Vector<String> compare(DCharacter existing, DCharacter loaded){
				Vector<String> v = new Vector<String>();
				
				// fields
				if (existing.getRace() != null && 
						!existing.getRace().equals(loaded.getRace())){
						v.add("Changed race from "+existing.getRace()+" to "+loaded.getRace());
				}
				if (existing.getRace() != null && 
						!existing.getAlignment().equals(loaded.getAlignment())){
						v.add("Changed alignment from "+existing.getAlignment()+" to "+loaded.getAlignment());
				}
				if (existing.getParty() != null && 
						!existing.getParty().equals(loaded.getParty())){
						v.add("Changed party from "+existing.getParty()+" to "+loaded.getParty());
				}
				if (existing.getMarchOrder() != null &&
						!existing.getMarchOrder().equals(loaded.getMarchOrder())){
						v.add("Changed Marching Order from "+existing.getMarchOrder()+" to "+loaded.getMarchOrder());
				}
				if (existing.getSleepShift() != null &&
						!existing.getSleepShift().equals(loaded.getSleepShift())){
						v.add("Changed Sleep Shift from "+existing.getSleepShift()+" to "+loaded.getSleepShift());
				}
				if (existing.getXp() != loaded.getXp()){
						v.add("Changed xp from "+existing.getXp()+" to "+loaded.getXp());
				}
				if (existing.getHeight() != null &&
						!existing.getHeight().equals(loaded.getHeight())){
						v.add("Changed height from "+existing.getHeight()+" to "+loaded.getHeight());
				}
				if (existing.getWeight() != null &&
						!existing.getWeight().equals(loaded.getWeight())){
						v.add("Changed weight from "+existing.getWeight()+" to "+loaded.getWeight());
				}
				if (existing.getAge() != null &&
						!existing.getAge().equals(loaded.getAge())){
						v.add("Changed age from "+existing.getAge()+" to "+loaded.getAge());
				}
				if (existing.getEyes() != null &&
						!existing.getEyes().equals(loaded.getEyes())){
						v.add("Changed eyes from "+existing.getEyes()+" to "+loaded.getEyes());
				}
				if (existing.getHair() != null && 
						!existing.getHair().equals(loaded.getHair())){
						v.add("Changed hair from "+existing.getHair()+" to "+loaded.getHair());
				}
				if (existing.getFullName() != null &&
						!existing.getFullName().equals(loaded.getFullName())){
						v.add("Changed fullName from "+existing.getFullName()+" to "+loaded.getFullName());
				}
				if (existing.getMiscArmorText() != null && 
						!existing.getMiscArmorText().equals(loaded.getMiscArmorText())){
						v.add("Changed miscArmorText from "+existing.getMiscArmorText()+" to "+loaded.getMiscArmorText());
				}
				if (existing.getDamageReduction() != null &&
						!existing.getDamageReduction().equals(loaded.getDamageReduction())){
						v.add("Changed damageReduction from "+existing.getDamageReduction()+" to "+loaded.getDamageReduction());
				}
				if (existing.getSpellResist() != null &&
						!existing.getSpellResist().equals(loaded.getSpellResist())){
						v.add("Changed spellResist from "+existing.getSpellResist()+" to "+loaded.getSpellResist());
				}
				if (existing.getPlayerName() != null &&
						!existing.getPlayerName().equals(loaded.getPlayerName())){
						v.add("Changed playerName from "+existing.getPlayerName()+" to "+loaded.getPlayerName());
				}
				if (existing.getPlayerNotes() != null &&
						!existing.getPlayerNotes().equals(loaded.getPlayerNotes())){
						v.add("Changed playerNotes from "+existing.getPlayerNotes()+" to "+loaded.getPlayerNotes());
				}
				if (existing.getBaseMovementOverride() != loaded.getBaseMovementOverride()){
						v.add("Changed baseMovementOverride from "+existing.getBaseMovementOverride()+" to "+loaded.getBaseMovementOverride());
				}
				if (existing.getMiscArmor() != loaded.getMiscArmor()){
						v.add("Changed miscArmor from "+existing.getMiscArmor()+" to "+loaded.getMiscArmor());
				}
				if (existing.getNaturalArmor() != loaded.getNaturalArmor()){
						v.add("Changed naturalArmor from "+existing.getNaturalArmor()+" to "+loaded.getNaturalArmor());
				}
				if (existing.getDeity() != null && !existing.getDeity().equals(loaded.getDeity())){
						v.add("Changed deity from "+existing.getDeity()+" to "+loaded.getDeity());
				}
				if (existing.getBaseMovement() != (loaded.getBaseMovement())){
						v.add("Changed baseMovement from "+existing.getBaseMovement()+" to "+loaded.getBaseMovement());
				}
				if (existing.getSizeOverride() != null &&
						!existing.getSizeOverride().equals(loaded.getSizeOverride())){
						v.add("Changed sizeOverride from "+existing.getSizeOverride()+" to "+loaded.getSizeOverride());
				}
				if (existing.getPicture() != null &&
						!existing.getPicture().equals(loaded.getPicture())){
						v.add("Changed picture from "+existing.getPicture()+" to "+loaded.getPicture());
				}
				if (existing.getModNotes() != null &&
						!existing.getModNotes().equals(loaded.getModNotes())){
						v.add("Changed modNotes from "+existing.getModNotes()+" to "+loaded.getModNotes());
				}
				if (existing.getArmor() != null && 
						!existing.getArmor().equals(loaded.getArmor())){
						v.add("Changed armor from "+existing.getArmor()+" to "+loaded.getArmor());
				}
				if (existing.getShield() != null &&
						!existing.getShield().equals(loaded.getShield())){
						v.add("Changed shield from "+existing.getShield()+" to "+loaded.getShield());
				}
				if (existing.getRestArmor() != null && 
						!existing.getRestArmor().equals(loaded.getRestArmor())){
						v.add("Changed restArmor from "+existing.getRestArmor()+" to "+loaded.getRestArmor());
				}
				
				Vector<?> ve = existing.getClassSet().getClassVector();
				Vector<?> vn = loaded.getClassSet().getClassVector();
				
				if (ve.size() != vn.size()){
						v.add("Added classes");
				}

			  ve = existing.getItems();
				vn = loaded.getItems();
				
				if (ve.size() != vn.size()){
						v.add("Added items");
				}

				ve = existing.getWeapons();
				vn = loaded.getWeapons();
				
				if (ve.size() != vn.size()){
						v.add("Added weapons");
				}	

				for (int i = 0; i < ve.size(); i++){
						if (i < vn.size()){
								if (!((Weapon)(ve.get(i))).equals((Weapon)(vn.get(i)))){
										v.add("Changed Weapon in Position "+i);
								}
						}
				}

				ve = existing.getAmmo();
				vn = loaded.getAmmo();
				
				if (ve.size() != vn.size()){
						v.add("Added ammo");
				}

				ve = existing.getTempMods();
				vn = loaded.getTempMods();
				
				if (ve.size() != vn.size()){
						v.add("Added temp mods");
				}
				
				ve = existing.getDomains();
				vn = loaded.getDomains();
				
				if (ve.size() != vn.size()){
						v.add("Added domains");
				}

				ve = existing.getDroppedLocations();
				vn = loaded.getDroppedLocations();
				
				if (ve.size() != vn.size()){
						v.add("Added dropped locs");
				}

				ve = existing.getLanguages();
				vn = loaded.getLanguages();
				
				if (ve.size() != vn.size()){
						v.add("Added languages");
				}

				ve = existing.getFavoredEnemies();
				vn = loaded.getFavoredEnemies();
				
				if (ve.size() != vn.size()){
						v.add("Added favored enemies");
				}

				ve = existing.getSpellsKnown();
				vn = loaded.getSpellsKnown();
				
				if (ve.size() != vn.size()){
						v.add("Added spells");
				}

				
				/*		
		
		// objects
		Vector classVector = new Vector();
		Vector items = new Vector();
		Vector weapons = new Vector();
		Vector ammo = new Vector();
		Vector hpList = new Vector();
		Vector tempMods = new Vector();
		Vector domains = new Vector();
		Vector droppedLocations = new Vector();
		Vector languages = new Vector();
		Vector favoredEnemies = new Vector();
		Vector spellsKnown = new Vector();

	
		StatBlock stats = new StatBlock();
		SaveBlock saves = new SaveBlock();
		MoneyBlock money = new MoneyBlock();
		JumpBlock jump = new JumpBlock();
				*/
				return v;
		} 

}
