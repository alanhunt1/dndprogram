package initcheck.database;

public class LibraryItemDAOFactory {

	public static LibraryItemDAO getInstance(String type){
		if (type.equals("Feat")){
			return new FeatDAO();
		}else if (type.equals("Race")){
			return  new RaceDAO();
		}	
		else if (type.equals("Skill")){
			return new SkillDAO();
		}
		else if (type.equals("Class")){
			return new CharClassDAO();
		}
		else if (type.equals("Class Ability")){
			return new ClassAbilitiesDAO();
		}
		else if (type.equals("Armor")){
			return new ArmorDAO();
		}
		else if (type.equals("Specific Armor")){
			return new SpecificArmorDAO();
		}
		else if (type.equals("Weapon")){
			return new WeaponDAO();
		}
		else if (type.equals("Specific Weapon")){
			return new SpecificWeaponDAO();
		}
		else if (type.equals("Spell")){
			return new SpellDAO();
		}
		else if (type.equals("Domain")){
			return new DomainDAO();
		}
		else if (type.equals("Equipment")){
			return new EquipmentDAO();
		}
		else if (type.equals("Service")){
			return new ServicesDAO();
		}
		else if (type.equals("Material")){
			return new MaterialsDAO();
		}
		else if (type.equals("Weapon Ability")){
			return new WeaponAbilitiesDAO();
		}
		else if (type.equals("Armor Ability")){
			return new ArmorAbilitiesDAO();
		}
		else if (type.equals("Wonderous Item")){
			return new WonderousItemsDAO();
		}
		else if (type.equals("Artifact")){
			return new ArtifactDAO();
		}
		else if (type.equals("Ring")){
			return new RingsDAO();
		}
		else if (type.equals("Rod")){
			return new RodsDAO();
		}
		else if (type.equals("Staff")){
			return new StaffsDAO();
		}
		else if (type.equals("Monster")){
			return new MonsterDAO();
		}
		else if (type.equals("Deity")){
			return new DeityDAO();
		}
		else if (type.equals("Trap")){
			return new TrapDAO();
		}
		else if (type.equals("Poison")){
			return new PoisonDAO();
		}
		return null;
	}
	
}
