package initcheck.character.itembuilder;

import initcheck.character.chooser.ArmorAbilityChooser;
import initcheck.character.chooser.ShieldChooser;
import initcheck.database.Armor;

public class BuildShieldPanel extends BuildArmorPanel {

	private static final long serialVersionUID = 1L;

	public BuildShieldPanel(Armor a) {
		ac = new ShieldChooser();
		aac = new ArmorAbilityChooser("SHIELD");
		aac2 = new ArmorAbilityChooser("SHIELD");
		aac3 = new ArmorAbilityChooser("SHIELD");
		aac4 = new ArmorAbilityChooser("SHIELD");
		aac5 = new ArmorAbilityChooser("SHIELD");

		((ShieldChooser) ac).setSelectedArmor(a);

		bonus.setText(a.getBonus());

		if ((a.getAbility1() != null) && a.getAbility1().getName() != null) {
		
			aac.setSelectedAbility(a.getAbility1());
		}
		if ((a.getAbility2() != null) && a.getAbility2().getName() != null) {
			aac2.setSelectedAbility(a.getAbility2());
		}

		calcCost();

		upgrade = true;
		origBonus = Integer.parseInt(bonus.getText());
		origCost = baseCost;
		origAbBonus = abBonus;
	}

	public BuildShieldPanel() {

		ac = new ShieldChooser();
		aac = new ArmorAbilityChooser("SHIELD");
		aac2 = new ArmorAbilityChooser("SHIELD");
		aac3 = new ArmorAbilityChooser("SHIELD");
		aac4 = new ArmorAbilityChooser("SHIELD");
		aac5 = new ArmorAbilityChooser("SHIELD");

	}

}
