package initcheck.preferences;

import initcheck.DefaultListCellRenderer;
import initcheck.InitBase;
import initcheck.graphics.TiledGridPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontDialog extends TiledGridPanel implements Serializable,
		ActionListener, ListSelectionListener {

	static final long serialVersionUID = 1;

	private Font defaultFont = (new Font("Arial", Font.BOLD, 20));

	private JList<String> playerList = null;

	private JComboBox fontStyle = new JComboBox();

	private JSpinner fontSize = new JSpinner();

	private JComboBox fontSizeChooser = new JComboBox();

	private JTextField sampleText = new JTextField("Sample Text");

	private static GraphicsEnvironment ge = GraphicsEnvironment
			.getLocalGraphicsEnvironment();

	private InitBase owner;

	public FontDialog(final InitBase owner) {

		super();
		this.owner = owner;

		defaultFont = owner.getDefaultFont();

		String[] fonts = ge.getAvailableFontFamilyNames();

		playerList = new JList(fonts);
		playerList.setCellRenderer(new DefaultListCellRenderer());
		playerList.setFont(defaultFont);
		playerList.setBackground(Color.lightGray);
		playerList.addListSelectionListener(this);

		playerList.setSelectedValue(defaultFont.getName(), true);

		// set the layout
		setWeightX(1.0);

		@SuppressWarnings("unused")
		int ypos = 0;

		fontSizeChooser.addItem("10");
		fontSizeChooser.addItem("20");
		fontSizeChooser.addItem("30");
		fontSizeChooser.addItem("40");
		fontSizeChooser.addItem("50");
		fontSizeChooser.addItem("60");
		fontSizeChooser.addItem("70");
		fontSizeChooser.addActionListener(this);
		fontSizeChooser.setFont(defaultFont);

		fontStyle.addItem("BOLD");
		fontStyle.addItem("ITALIC");
		fontStyle.addItem("PLAIN");
		fontStyle.setFont(defaultFont);

		fontSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				updateFontSize();
			}
		});
		fontSize.setValue(new Integer(defaultFont.getSize()));
		fontSize.setFont(defaultFont);
		try {
			fontSize.commitEdit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane scroll = new JScrollPane(playerList);

		JPanel stylePanel = new JPanel();
		stylePanel.add(fontStyle);
		stylePanel.add(fontSize);
		stylePanel.add(fontSizeChooser);

		sampleText.setColumns(20);

		setWeightY(0);

		doLayout(stylePanel);
		setWeightY(1.0);
		setWeightX(1.0);
		incYPos();
		doLayout(scroll);
		setWeightY(0);
		setWeightX(0);
		incYPos();
		doLayout(sampleText);
		setOpaque(false);

		playerList.ensureIndexIsVisible(playerList.getSelectedIndex());

	}

	public void save() {
		defaultFont = owner.getDefaultFont();

		playerList.setFont(defaultFont);
		fontStyle.setFont(defaultFont);
		fontSize.setFont(defaultFont);
		owner.setDefaultFont(sampleText.getFont());
		owner.setListFont(sampleText.getFont());
		owner.writePrefsToFile();
	}

	public void repaintScreen() {
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
	}

	public void updateFontSize() {

		int type = Font.PLAIN;
		String typeStr = (String) fontStyle.getSelectedItem();
		String fontName = (String) playerList.getSelectedValue();
		if (typeStr != null && fontName != null) {
			if (typeStr.equals("BOLD")) {
				type = Font.BOLD;
			} else if (typeStr.equals("ITALIC")) {
				type = Font.ITALIC;
			}
			int size = ((Integer) fontSize.getValue()).intValue();

			Font f = new Font(fontName, type, size);
			sampleText.setFont(f);

			repaintScreen();
		}
	}

	public void valueChanged(ListSelectionEvent e) {

		updateFontSize();
	}

	public void actionPerformed(ActionEvent e) {
		fontSize.setValue(new Integer((String) fontSizeChooser
				.getSelectedItem()));
		updateFontSize();
	}

}
