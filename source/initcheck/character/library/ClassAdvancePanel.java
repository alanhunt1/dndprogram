package initcheck.character.library;

import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.database.CharClass;
import initcheck.database.ClassAdvancement;
import initcheck.database.ClassAdvancementDAO;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ClassAdvancePanel extends JPanel implements ListSelectionListener {

	private InitLogger logger = new InitLogger(this);
	
	private static final long serialVersionUID = 1L;

	private JList advancementList = new JList();

	private JScrollPane advanceScroll = new JScrollPane(advancementList);

	Vector<ClassAdvancement> advanceVector = new Vector<ClassAdvancement>();

	CharClass cc;

	private PanelButton addButton = new PanelButton("add");

	private PanelButton remButton = new PanelButton("rem");

	private PanelButton saveButton = new PanelButton("save");

	private JTextField level = new JTextField(2);

	private JTextField bab = new JTextField(10);

	private JTextField will = new JTextField(10);

	private JTextField fort = new JTextField(10);

	private JTextField ref = new JTextField(10);

	private JTextField special = new JTextField(15);

	private boolean alternate = false;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public ClassAdvancePanel(CharClass cc) {
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			ClassAdvancementDAO cadb = new ClassAdvancementDAO();
			ClassAdvancement ca = new ClassAdvancement();
			ca.setClassId(cc.getId());
			ca.setAlternate("N");
			advanceVector = cadb.selectClassAdvancement(ca);
			advancementList.setListData(advanceVector);
			advancementList.setCellRenderer(new GenericListCellRenderer());
			advancementList.setFont(new Font("Courier", Font.PLAIN, 12));
		}
		advancementList.addListSelectionListener(this);
	}

	public ClassAdvancePanel(CharClass cc, boolean alternate) {
		this.alternate = alternate;
		init();
		this.cc = cc;
		if (cc.getId() != null) {
			ClassAdvancementDAO cadb = new ClassAdvancementDAO();
			ClassAdvancement ca = new ClassAdvancement();
			ca.setClassId(cc.getId());
			if (alternate) {
				ca.setAlternate("Y");
			} else {
				ca.setAlternate("N");
			}
			advanceVector = cadb.selectClassAdvancement(ca);
			advancementList.setListData(advanceVector);
			advancementList.setCellRenderer(new GenericListCellRenderer());
			advancementList.setFont(new Font("Courier", Font.PLAIN, 12));
		}
		advancementList.addListSelectionListener(this);
	}

	public void setCharClass(CharClass cc, boolean alternate){
		this.alternate = alternate;
		this.cc = cc;
		if (cc.getId() != null) {
			ClassAdvancementDAO cadb = new ClassAdvancementDAO();
			ClassAdvancement ca = new ClassAdvancement();
			ca.setClassId(cc.getId());
			if (alternate) {
				ca.setAlternate("Y");
			} else {
				ca.setAlternate("N");
			}
			advanceVector = cadb.selectClassAdvancement(ca);
			advancementList.setListData(advanceVector);
		}
	}
	
	private void init() {

		setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.ipadx = 10;
		c.ipady = 10;
		advancementList.setVisibleRowCount(5);
		int ypos = 0;

		c.weightx = 1.0;
		c.weighty = 1.0;
		doLayout(advanceScroll, 0, ypos);
		c.weightx = 0;
		c.weighty = 0;
		ypos++;

		JPanel panela = new JPanel();
		panela.add(new JLabel("lvl"));
		panela.add(level);
		panela.add(new JLabel("bab"));
		panela.add(bab);
		panela.add(new JLabel("fort"));
		panela.add(fort);
		panela.add(new JLabel("ref"));
		panela.add(ref);
		panela.add(new JLabel("will"));
		panela.add(will);
		doLayout(panela, 0, ypos);
		ypos++;

		JPanel panelb = new JPanel();
		panelb.add(new JLabel("special"));
		panelb.add(special);
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
		int idx = advancementList.getSelectedIndex();
		ClassAdvancement f = (ClassAdvancement) advancementList
				.getSelectedValue();
		f.setLevel(level.getText());
		f.setBaseAttack(bab.getText());
		f.setSpecial(special.getText());
		f.setFortSave(fort.getText());
		f.setWillSave(will.getText());
		f.setRefSave(ref.getText());
		if (alternate) {
			f.setAlternate("Y");
		} else {
			f.setAlternate("N");
		}
		ClassAdvancementDAO cadb = new ClassAdvancementDAO();
		cadb.updateClassAdvancement(f);

		ClassAdvancement ca = new ClassAdvancement();
		ca.setClassId(cc.getId());
		if (alternate) {
			ca.setAlternate("Y");
		} else {
			ca.setAlternate("N");
		}
		advanceVector = cadb.selectClassAdvancement(ca);
		advancementList.setListData(advanceVector);
		advancementList.setSelectedIndex(idx);
	}

	private void addAdvance() {
		int idx = advancementList.getSelectedIndex();
		if (idx == -1) {
			idx++;
		}
		ClassAdvancement f = (ClassAdvancement) advancementList
				.getSelectedValue();
		if (f == null) {
			f = new ClassAdvancement();
			f.setClassId(cc.getId());

		}

		ClassAdvancement newClass = new ClassAdvancement();
		newClass.setClassId(f.getClassId());
		newClass.setLevel(level.getText());
		newClass.setBaseAttack(bab.getText());
		newClass.setSpecial(special.getText());
		newClass.setFortSave(fort.getText());
		newClass.setWillSave(will.getText());
		newClass.setRefSave(ref.getText());
		if (alternate) {
			newClass.setAlternate("Y");
		} else {
			newClass.setAlternate("N");
		}
		ClassAdvancementDAO cadb = new ClassAdvancementDAO();
		cadb.addClassAdvancement(newClass);

		ClassAdvancement ca = new ClassAdvancement();
		ca.setClassId(cc.getId());
		if (alternate) {
			ca.setAlternate("Y");
		} else {
			ca.setAlternate("N");
		}
		advanceVector = cadb.selectClassAdvancement(ca);
		advancementList.setListData(advanceVector);
		advancementList.setSelectedIndex(idx);
	}

	private void remAdvance() {
		int idx = advancementList.getSelectedIndex();
		ClassAdvancement f = (ClassAdvancement) advancementList
				.getSelectedValue();
		ClassAdvancementDAO cadb = new ClassAdvancementDAO();
		cadb.deleteClassAdvancement(f);

		ClassAdvancement ca = new ClassAdvancement();
		ca.setClassId(cc.getId());
		advanceVector = cadb.selectClassAdvancement(ca);
		advancementList.setListData(advanceVector);
		advancementList.setSelectedIndex(idx - 1);
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

	/**
	 * Get the AdvanceVector value.
	 * 
	 * @return the AdvanceVector value.
	 */
	public Vector<ClassAdvancement> getAdvanceVector() {
		return advanceVector;
	}

	/**
	 * Set the AdvanceVector value.
	 * 
	 * @param newAdvanceVector
	 *            The new AdvanceVector value.
	 */
	public void setAdvanceVector(Vector<ClassAdvancement> newAdvanceVector) {
		this.advanceVector = newAdvanceVector;
	}

	public void valueChanged(ListSelectionEvent e) {
		try {

			ClassAdvancement f = (ClassAdvancement) advancementList
					.getSelectedValue();
			level.setText(f.getLevel());
			bab.setText(f.getBaseAttack());
			special.setText(f.getSpecial());
			fort.setText(f.getFortSave());
			will.setText(f.getWillSave());
			ref.setText(f.getRefSave());

		} catch (Exception ex) {
			logger.log("error",ex.toString());
		}
	}
}
