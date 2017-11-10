package initcheck.character;

import initcheck.PanelButton;
import initcheck.character.chooser.SpellChooser;
import initcheck.database.Domain;
import initcheck.database.DomainDAO;
import initcheck.database.DomainSpells;
import initcheck.database.DomainSpellsDAO;
import initcheck.database.Spell;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DomainSpellPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList domainSpellList = new JList();

	private JScrollPane advanceScroll = new JScrollPane(domainSpellList);

	Vector<Spell> spellVector = new Vector<Spell>();

	Domain cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private SpellChooser spellChooser = new SpellChooser();

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public DomainSpellPanel(Domain cc) {
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			DomainDAO ccdb = new DomainDAO();
			spellVector = ccdb.getDomainSpells(cc);
			domainSpellList.setListData(spellVector);
		}

	}

	private void init() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		domainSpellList.setVisibleRowCount(5);
		int ypos = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(advanceScroll, 0, ypos);
		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		JPanel buttons = new JPanel();
		buttons.add(spellChooser);
		buttons.add(addButton);
		buttons.add(remButton);

		doLayout(buttons, 0, ypos);
		ypos++;

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAdvance();
			}
		});

		remButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remAdvance();
			}
		});

	}

	private void addAdvance() {
		int idx = domainSpellList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}
		Spell f = (Spell) spellChooser.getSelectedItem();

		DomainSpells newDomain = new DomainSpells();
		newDomain.setDomainId(cc.getId());
		newDomain.setSpellId(f.getId());

		DomainSpellsDAO cadb = new DomainSpellsDAO();
		cadb.addDomainSpells(newDomain);

		DomainDAO ccdb = new DomainDAO();
		spellVector = ccdb.getDomainSpells(cc);
		domainSpellList.setListData(spellVector);
		domainSpellList.setSelectedIndex(idx);
	}

	private void remAdvance() {
		int idx = domainSpellList.getSelectedIndex();
		Spell f = (Spell) domainSpellList.getSelectedValue();
		DomainSpellsDAO cadb = new DomainSpellsDAO();
		cadb.deleteDomainSpells(f);

		DomainDAO ccdb = new DomainDAO();
		spellVector = ccdb.getDomainSpells(cc);

		domainSpellList.setListData(spellVector);
		domainSpellList.setSelectedIndex(idx - 1);
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		add(comp);
	}

}
