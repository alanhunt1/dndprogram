package initcheck.character.library;

import initcheck.PanelButton;
import initcheck.character.chooser.SpellClassChooser;
import initcheck.database.SpellClass;
import initcheck.database.SpellClassesDAO;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledGridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SpellClassDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private SpellClassChooser classChooser = new SpellClassChooser();

	private JTextField classLevel = new JTextField(3);

	private TiledGridPanel main = new TiledGridPanel();

	private PanelButton save = new PanelButton("OK");

	@SuppressWarnings("unused")
	private String spellId = null;

	private CreateSpellPanel owner;
	
	public SpellClassDialog(CreateSpellPanel owner) {
		this.owner = owner;
		main.doLayout(classChooser);
		main.doLayout(classLevel, 1);
		main.doLayout(save, 2);

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClassLevel();
			}
		});
		add(main);
		pack();
	}

	private void addClassLevel() {
		String spellId = owner.getSpell().getId();
		if (spellId != null && !spellId.equals("")) {
			SpellClassesDAO db = new SpellClassesDAO();
			SpellClass sc = new SpellClass();
			sc.setSpellId(spellId);
			sc.setSpellClass((String)classChooser.getSelectedItem());
			sc.setSpellLevel(classLevel.getText());
			db.addSpellClasses(sc);
			owner.reloadSpellClasses();
			this.dispose();
		}
	}
}
