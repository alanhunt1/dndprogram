package initcheck.dungeon;

import initcheck.MapEditor;
import initcheck.PanelButton;
import initcheck.graphics.TiledDialog;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawLineDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	JTextField x2Field = new JTextField(5);

	JTextField y2Field = new JTextField(5);

	JTextField xField = new JTextField(5);

	JTextField yField = new JTextField(5);

	PanelButton addButton = new PanelButton("Add Room", 80);

	JPanel main = new JPanel();

	DungeonGUI map = null;

	MapEditor owner = null;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public DrawLineDialog(DungeonGUI map) {

		super();

		this.map = map;

		buildComponents();
		init();
	}

	public void setX(String x) {
		xField.setText(x);
	}

	public void setY(String y) {
		yField.setText(y);
	}

	public void setWidth(String w) {
		x2Field.setText(w);
	}

	public void setHeight(String h) {
		y2Field.setText(h);
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	@SuppressWarnings("unused")
	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	public void buildComponents() {

		int ypos = 0;

		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;

		doLayout(new JLabel("X1 Position:"), 0, ypos);
		doLayout(xField, 1, ypos);
		doLayout(new JLabel("Y1"), 2, ypos);
		doLayout(yField, 3, ypos);
		ypos++;

		doLayout(new JLabel("X2"), 0, ypos);
		doLayout(x2Field, 1, ypos);
		doLayout(new JLabel("Y2"), 2, ypos);
		doLayout(y2Field, 3, ypos);
		ypos++;

		JPanel generatePanel = new JPanel();
		generatePanel.add(addButton);
		// doLayout(generatePanel,0,ypos,3,1);
		ypos++;

		setMainWindow(main);
		setButtonBar(generatePanel);

	}

	public void init() {
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRoom();
			}
		});
	}

	public void addRoom() {
		map.drawLine(Integer.parseInt(xField.getText()), Integer
				.parseInt(yField.getText()), Integer
				.parseInt(x2Field.getText()), Integer.parseInt(y2Field
				.getText()));
		dispose();
	}

}
