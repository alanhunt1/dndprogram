package initcheck.preferences;

import initcheck.InitBase;
import initcheck.PanelButton;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;

public class FontPanel extends JPanel implements Serializable {

	
	private static final long serialVersionUID = 1L;

	// get the graphics environment to provide the available fonts list
	private static GraphicsEnvironment ge = GraphicsEnvironment
			.getLocalGraphicsEnvironment();

	private JList fontList = null;

	private JComboBox fontStyle = new JComboBox();

	private PanelButton closeButton = new PanelButton("OK", 70);

	private JTextField fontSize = new JTextField(2);

	public FontPanel(final InitBase owner) {

		fontStyle.addItem("BOLD");
		fontStyle.addItem("ITALIC");
		fontStyle.addItem("PLAIN");

		String[] fonts = ge.getAvailableFontFamilyNames();

		fontList = new JList(fonts);
		fontList.setCellRenderer(new initcheck.DefaultListCellRenderer());
		fontList.setFont(new Font("Courier", Font.PLAIN, 12));
		fontList.setBackground(Color.lightGray);

		JScrollPane scroll = new JScrollPane(fontList);
		JPanel buttons = new JPanel();
		buttons.add(closeButton);

		JPanel stylePanel = new JPanel();
		stylePanel.add(fontStyle);
		stylePanel.add(fontSize);
		add(stylePanel, BorderLayout.SOUTH);
		add(scroll, BorderLayout.CENTER);
		add(buttons);

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = Font.PLAIN;
				String typeStr = (String) fontStyle.getSelectedItem();
				if (typeStr.equals("BOLD")) {
					type = Font.BOLD;
				} else if (typeStr.equals("ITALIC")) {
					type = Font.ITALIC;
				}
				int size = Integer.parseInt(fontSize.getText());
				owner.setFont(new Font((String) fontList.getSelectedValue(),
						type, size));
			}
		});

	}
}
