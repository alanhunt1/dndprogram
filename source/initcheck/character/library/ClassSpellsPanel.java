package initcheck.character.library;

import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.database.CharClass;
import initcheck.database.SpellLevel;
import initcheck.database.SpellLevelDAO;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClassSpellsPanel extends JPanel implements ListSelectionListener {

	private InitLogger logger = new InitLogger(this);
	
	private static final long serialVersionUID = 1L;

	private JList spellList = new JList();

	private JScrollPane spellScroll = new JScrollPane(spellList);

	CharClass cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private PanelButton saveButton = new PanelButton("save");

	private JTextField level = new JTextField(2);

	private JTextField level0 = new JTextField(3);

	private JTextField level1 = new JTextField(3);

	private JTextField level2 = new JTextField(3);

	private JTextField level3 = new JTextField(3);

	private JTextField level4 = new JTextField(3);

	private JTextField level5 = new JTextField(3);

	private JTextField level6 = new JTextField(3);

	private JTextField level7 = new JTextField(3);

	private JTextField level8 = new JTextField(3);

	private JTextField level9 = new JTextField(3);

	private boolean alternate = false;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public ClassSpellsPanel(CharClass cc) {
		init();
		this.cc = cc;
		
		if (cc.getId() != null) {
			
			SpellLevelDAO cadb = new SpellLevelDAO();
			SpellLevel ca = new SpellLevel();
			ca.setClassId(cc.getId());
			ca.setClassName(cc.getCharClass());
			ca.setAlternate(false);
			spellList.setListData(cadb.selectSpellLevel(ca));
			spellList.setCellRenderer(new GenericListCellRenderer());
			spellList.setFont(new Font("Courier", Font.PLAIN, 12));
		}
		spellList.addListSelectionListener(this);
	}

	public ClassSpellsPanel(CharClass cc, boolean alternate) {
		this.alternate = alternate;
		init();
		this.cc = cc;
		
		if (cc.getId() != null) {
			
			SpellLevelDAO cadb = new SpellLevelDAO();
			SpellLevel ca = new SpellLevel();
			ca.setClassId(cc.getId());
			ca.setClassName(cc.getCharClass());
			ca.setAlternate(alternate);
			spellList.setListData(cadb.selectSpellLevel(ca));
			spellList.setCellRenderer(new GenericListCellRenderer());
			spellList.setFont(new Font("Courier", Font.PLAIN, 12));
		}
		spellList.addListSelectionListener(this);
	}

	public void setCharClass(CharClass cc, boolean alternate){
		this.alternate = alternate;
		this.cc = cc;
		
		if (cc.getId() != null) {
		
			SpellLevelDAO cadb = new SpellLevelDAO();
			SpellLevel ca = new SpellLevel();
			ca.setClassId(cc.getId());
			ca.setClassName(cc.getCharClass());
			ca.setAlternate(alternate);
			spellList.setListData(cadb.selectSpellLevel(ca));
			spellList.setCellRenderer(new GenericListCellRenderer());
			spellList.setFont(new Font("Courier", Font.PLAIN, 12));
		}
		spellList.addListSelectionListener(this);
	}
	
	private void init() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		spellList.setVisibleRowCount(5);
		int ypos = 0;

		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(spellScroll, 0, ypos);
		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		JPanel panela = new JPanel();
		panela.add(new JLabel("lvl"));
		panela.add(level);
		panela.add(new JLabel("level0"));
		panela.add(level0);
		panela.add(new JLabel("level1"));
		panela.add(level1);
		panela.add(new JLabel("level2"));
		panela.add(level2);
		panela.add(new JLabel("level3"));
		panela.add(level3);
		panela.add(new JLabel("level4"));
		panela.add(level4);

		doLayout(panela, 0, ypos);
		ypos++;

		JPanel panelb = new JPanel();
		panelb.add(new JLabel("level5"));
		panelb.add(level5);
		panelb.add(new JLabel("level6"));
		panelb.add(level6);
		panelb.add(new JLabel("level7"));
		panelb.add(level7);
		panelb.add(new JLabel("level8"));
		panelb.add(level8);
		panelb.add(new JLabel("level9"));
		panelb.add(level9);
		doLayout(panelb, 0, ypos);
		ypos++;

		JPanel buttons = new JPanel();
		buttons.add(addButton);
		buttons.add(remButton);
		buttons.add(saveButton);
		doLayout(buttons, 0, ypos);
		ypos++;

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAdvance();
			}
		});

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

	private void saveAdvance() {
		int idx = spellList.getSelectedIndex();
		SpellLevel f = (SpellLevel) spellList.getSelectedValue();
		f.setClassLevel(level.getText());
		f.setLevel0(level0.getText());
		f.setLevel4(level4.getText());
		f.setLevel2(level2.getText());
		f.setLevel1(level1.getText());
		f.setLevel3(level3.getText());
		f.setLevel5(level5.getText());
		f.setLevel6(level6.getText());
		f.setLevel7(level7.getText());
		f.setLevel8(level8.getText());
		f.setLevel9(level9.getText());

		

		SpellLevelDAO cadb = new SpellLevelDAO();
		cadb.updateSpellLevel(f);

		

		SpellLevel ca = new SpellLevel();
		ca.setClassId(cc.getId());
		ca.setAlternate(alternate);
		spellList.setListData(cadb.selectSpellLevel(ca));
		spellList.setSelectedIndex(idx);
	}

	private void addAdvance() {

		int idx = spellList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}

		SpellLevel newClass = new SpellLevel();
		newClass.setClassId(cc.getId());
		newClass.setClassLevel(level.getText());
		newClass.setClassName(cc.getCharClass());
		newClass.setLevel0(level0.getText());
		newClass.setLevel4(level4.getText());
		newClass.setLevel2(level2.getText());
		newClass.setLevel1(level1.getText());
		newClass.setLevel3(level3.getText());
		newClass.setLevel5(level5.getText());
		newClass.setLevel6(level6.getText());
		newClass.setLevel7(level7.getText());
		newClass.setLevel8(level8.getText());
		newClass.setLevel9(level9.getText());
		newClass.setAlternate(alternate);
		SpellLevelDAO cadb = new SpellLevelDAO();
		cadb.addSpellLevel(newClass);

		SpellLevel ca = new SpellLevel();
		ca.setClassId(cc.getId());
		ca.setAlternate(alternate);
		ca.setClassName(cc.getCharClass());

		spellList.setListData(cadb.selectSpellLevel(ca));
		spellList.setSelectedIndex(idx);
	}

	private void remAdvance() {
		int idx = spellList.getSelectedIndex();
		SpellLevel f = (SpellLevel) spellList.getSelectedValue();
		SpellLevelDAO cadb = new SpellLevelDAO();
		cadb.deleteSpellLevel(f);

		SpellLevel ca = new SpellLevel();
		ca.setClassId(cc.getId());
		ca.setAlternate(alternate);
		spellList.setListData(cadb.selectSpellLevel(ca));
		spellList.setSelectedIndex(idx - 1);
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

	public void valueChanged(ListSelectionEvent e) {
		try {

			SpellLevel f = (SpellLevel) spellList.getSelectedValue();
			level.setText(f.getClassLevel());
			level0.setText(f.getLevel0());
			level4.setText(f.getLevel4());
			level2.setText(f.getLevel2());
			level1.setText(f.getLevel1());
			level3.setText(f.getLevel3());
			level5.setText(f.getLevel5());
			level6.setText(f.getLevel6());
			level7.setText(f.getLevel7());
			level8.setText(f.getLevel8());
			level9.setText(f.getLevel9());

		} catch (Exception ex) {
			logger.log("error",ex.toString());
			ex.printStackTrace();
		}
	}
}
