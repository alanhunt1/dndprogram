package initcheck.dungeon;

import initcheck.MapEditor;
import initcheck.MonsterDisplayPanel;
import initcheck.MonsterStatDialog;
import initcheck.PanelButton;
import initcheck.database.Monster;
import initcheck.database.MonsterDAO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GenerateDungeonPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Default parameter values
	int levels = 1;

	int cols = 50;

	int rows = 50;

	int curliness = 3;

	int maxHeight = 10;

	int maxWidth = 10;

	int minHeight = 10;

	int minWidth = 10;

	int numRooms = 15;

	int sparseness = 10;

	int deadends = 3;

	int encounter = 4;

	double partyLevel = 1.0;

	int partySize = 4;

	JTextField widthField = new JTextField("50", 5);

	JTextField heightField = new JTextField("50", 5);

	JTextField depthField = new JTextField("1", 5);

	JTextField numRoomsField = new JTextField("15", 5);

	JTextField maxHeightField = new JTextField("10", 5);

	JTextField maxWidthField = new JTextField("10", 5);

	JTextField minHeightField = new JTextField("3", 5);

	JTextField minWidthField = new JTextField("3", 5);

	JTextField partyLevelField = new JTextField("1.0", 5);

	JTextField partySizeField = new JTextField("4", 5);

	JSlider curlinessSlider = new JSlider(1, 30, 5);

	JSlider encounterSlider = new JSlider();

	JSlider sparseSlider = new JSlider(1, 20, 10);

	JSlider deadendSlider = new JSlider(1, 20, 10);

	JProgressBar progressBar = new JProgressBar(1, 100);

	JLabel progressLabel = new JLabel("Progress", SwingConstants.CENTER);

	PanelButton generateButton = new PanelButton("Generate Map", 80);

	JCheckBox restrictMonsters = new JCheckBox("Restrict Monsters");

	Vector<Monster> monsters = new Vector<Monster>();

	Vector<Monster> valid = new Vector<Monster>();

	JPanel main = new JPanel();

	private JList monsterList = new JList();

	private JList validList = new JList();

	JScrollPane monsterScroll = new JScrollPane(monsterList);

	JScrollPane validScroll = new JScrollPane(validList);

	MapEditorDungeonGUI map = null;

	MapEditor owner = null;

	JPanel generatePanel = new JPanel();

	int ypos = 0;

	JDialog container;

	// gridbag layout components
	private GridBagLayout gridbag = new GridBagLayout();

	private GridBagConstraints c = new GridBagConstraints();

	public GenerateDungeonPanel(MapEditorDungeonGUI map, MapEditor owner, ImageIcon bg) {

		super();
		this.owner = owner;
		this.map = map;

		buildComponents();
		init();
	}

	private void doLayout(Component comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = 1;
		c.gridheight = 1;
		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	private void doLayout(Component comp, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;

		gridbag.setConstraints(comp, c);
		main.add(comp);
	}

	public void incValue() {
		setValue(progressBar.getValue() + 1);
	}

	public void incValue(int i) {
		setValue(progressBar.getValue() + i);
	}

	public void setValue(int i) {
		progressBar.setValue(i);
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
	}

	public void buildComponents() {
		progressBar.setValue(0);
		ypos = 0;
		main.setOpaque(false);
		main.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		c.ipady = 5;

		FlowLayout panelLayout = new FlowLayout(FlowLayout.LEFT);
		panelLayout.setHgap(0);
		panelLayout.setVgap(0);
		doLayout(new LightLabel("Size ", SwingConstants.RIGHT), 0, ypos);
		JPanel sizePanel = new JPanel(panelLayout);
		sizePanel.add(widthField);
		sizePanel.add(new JLabel("  X  "));
		sizePanel.add(heightField);
		doLayout(sizePanel, 1, ypos, 3, 1);
		ypos++;

		doLayout(new JLabel("Levels ", SwingConstants.RIGHT), 0, ypos);
		doLayout(depthField, 1, ypos, 3, 1);
		ypos++;

		doLayout(new LightLabel("Rooms Per Level ", SwingConstants.RIGHT), 0,
				ypos);
		doLayout(numRoomsField, 1, ypos, 3, 1);
		ypos++;

		doLayout(new JLabel("Corridors ", SwingConstants.RIGHT), 0, ypos);
		doLayout(new JLabel("Curly", SwingConstants.RIGHT), 1, ypos);
		doLayout(curlinessSlider, 2, ypos);
		doLayout(new JLabel("Straight"), 3, ypos);
		ypos++;

		doLayout(new LightLabel("Max Room Size ", SwingConstants.RIGHT), 0,
				ypos);
		JPanel maxPanel = new JPanel(panelLayout);
		maxPanel.add(maxWidthField);
		maxPanel.add(new JLabel("  X  "));
		maxPanel.add(maxHeightField);
		doLayout(maxPanel, 1, ypos, 3, 1);
		ypos++;

		doLayout(new JLabel("Min Room Size ", SwingConstants.RIGHT), 0, ypos);
		JPanel minPanel = new JPanel(panelLayout);
		minPanel.add(minWidthField);
		minPanel.add(new JLabel("  X  "));
		minPanel.add(minHeightField);
		doLayout(minPanel, 1, ypos, 3, 1);
		ypos++;

		doLayout(new LightLabel("Sparseness ", SwingConstants.RIGHT), 0, ypos);
		doLayout(new JLabel("Dense", SwingConstants.RIGHT), 1, ypos);
		doLayout(sparseSlider, 2, ypos);
		doLayout(new JLabel("Sparse"), 3, ypos);
		ypos++;

		doLayout(new JLabel("Dead Ends ", SwingConstants.RIGHT), 0, ypos);
		doLayout(new JLabel("Fewer", SwingConstants.RIGHT), 1, ypos);
		doLayout(deadendSlider, 2, ypos);
		doLayout(new JLabel("More"), 3, ypos);
		ypos++;

		doLayout(new LightLabel("Encounters ", SwingConstants.RIGHT), 0, ypos);
		doLayout(new JLabel("Never", SwingConstants.RIGHT), 1, ypos);
		doLayout(encounterSlider, 2, ypos);
		doLayout(new JLabel("Always"), 3, ypos);
		ypos++;

		doLayout(new JLabel("Party Level ", SwingConstants.RIGHT), 0, ypos);
		doLayout(partyLevelField, 1, ypos, 3, 1);
		ypos++;

		doLayout(new LightLabel("Party Size ", SwingConstants.RIGHT), 0, ypos);
		doLayout(partySizeField, 1, ypos, 3, 1);
		ypos++;

		doLayout(restrictMonsters, 0, ypos, 4, 1);
		ypos++;

		generatePanel.add(generateButton);
		doLayout(generatePanel, 0, ypos, 4, 1);

		add(main);
	}

	public void signal(String s) {
		progressLabel.setText(s);
		paintImmediately(0, 0, new Double(getSize().getWidth()).intValue(),
				new Double(getSize().getHeight()).intValue());
	}

	public void init() {
		generateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMap();
			}
		});

		restrictMonsters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleMonsterSelect();
			}
		});

		monsterList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Monster m = (Monster) monsterList.getSelectedValue();
					showMonsterStatDialog(m, 0, MonsterDisplayPanel.DISPLAY);
				}
				if (e.getClickCount() == 2) {
					Monster m = (Monster) monsterList.getSelectedValue();
					valid.add(m);
					validList.setListData(valid.toArray());
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});

		validList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Monster m = (Monster) monsterList.getSelectedValue();
					valid.remove(m);
					validList.setListData(valid.toArray());
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});

		monsterList.setCellRenderer(new ValidMonsterCellRenderer());
		validList.setCellRenderer(new ValidMonsterCellRenderer());
		MonsterDAO db = new MonsterDAO();
		monsters = db.getMonsters(null, null, "NAME");
		monsterList.setListData(monsters);

	}

	public void showMonsterStatDialog(Monster m, int i, int mode) {
		@SuppressWarnings("unused")
		MonsterStatDialog d = new MonsterStatDialog(m, mode, null);

	}

	public void toggleMonsterSelect() {
		if (!restrictMonsters.isSelected()) {
			main.remove(monsterScroll);
			main.remove(validScroll);
			ypos--;
		} else {
			main.remove(generatePanel);
			doLayout(monsterScroll, 0, ypos, 2, 1);
			doLayout(validScroll, 2, ypos, 2, 1);
			ypos++;
			doLayout(generatePanel, 0, ypos, 4, 1);
		}
		owner.packDungeonDialog();
	}

	public void generateMap() {
		boolean ok = true;
		DungeonVars vars = new DungeonVars();
		vars.setComponent(this);
		try {
			rows = Integer.parseInt(heightField.getText());
			vars.setRows(rows);
		} catch (Exception err) {
			heightField.setText("-ERR-");
			heightField.selectAll();
			heightField.requestFocus();
			ok = false;
		}
		try {
			cols = Integer.parseInt(widthField.getText());
			vars.setCols(cols);
		} catch (Exception err) {
			widthField.setText("-ERR-");
			widthField.selectAll();
			widthField.requestFocus();
			ok = false;
		}
		try {
			levels = Integer.parseInt(depthField.getText());
			vars.setLevels(levels);
		} catch (Exception err) {
			depthField.setText("-ERR-");
			depthField.selectAll();
			depthField.requestFocus();
			ok = false;
		}
		try {
			numRooms = Integer.valueOf(numRoomsField.getText()).intValue();
			vars.setNumRooms(numRooms);
		} catch (Exception err) {
			numRooms = 15;
			numRoomsField.setText("15");
		}

		curliness = curlinessSlider.getValue();
		vars.setCurliness(curliness);

		try {
			maxHeight = Integer.parseInt(maxHeightField.getText());
			vars.setMaxHeight(maxHeight);
		} catch (Exception err) {
			maxHeight = 10;
			maxHeightField.setText("10");
		}
		try {
			maxWidth = Integer.parseInt(maxWidthField.getText());
			vars.setMaxWidth(maxWidth);
		} catch (Exception err) {
			maxWidth = 10;
			maxWidthField.setText("10");
		}
		try {
			minHeight = Integer.parseInt(minHeightField.getText());
			vars.setMinHeight(minHeight);
		} catch (Exception err) {
			minHeight = 3;
			minHeightField.setText("3");
		}
		try {
			minWidth = Integer.parseInt(minWidthField.getText());
			vars.setMinWidth(minWidth);
		} catch (Exception err) {
			minWidth = 3;
			minWidthField.setText("3");
		}

		sparseness = sparseSlider.getValue();
		vars.setSparseness(sparseness);

		deadends = deadendSlider.getValue();
		vars.setDeadends(deadends);

		encounter = encounterSlider.getValue();
		vars.setEncounterChance(encounter);

		try {
			partyLevel = Double.parseDouble(partyLevelField.getText());
			vars.setPartyLevel(partyLevel);
		} catch (Exception err) {
			partyLevel = 1;
			partyLevelField.setText("1.0");
		}

		try {
			partySize = Integer.parseInt(partySizeField.getText());
			vars.setPartySize(partySize);
		} catch (Exception err) {
			partySize = 4;
			partySizeField.setText("4");
		}

		if (valid != null && valid.size() > 0) {
			vars.setMonsters(valid);
		}

		if (ok) {

			main.invalidate();
			main.remove(generatePanel);
			JPanel progressPanel = new JPanel(new BorderLayout());
			progressPanel.add(progressLabel, BorderLayout.NORTH);
			progressPanel.add(progressBar, BorderLayout.CENTER);
			doLayout(progressPanel, 0, ypos, 4, 1);
			main.validate();

			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			map.generateDungeon(vars);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			owner.hideDungeonDialog();

			main.invalidate();
			main.remove(progressPanel);
			doLayout(generatePanel, 0, ypos, 4, 1);
			main.validate();

			setValue(0);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}
