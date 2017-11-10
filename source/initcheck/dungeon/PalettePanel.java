package initcheck.dungeon;

import initcheck.InitFont;
import initcheck.TransparentButton;
import initcheck.character.GridPanel;
import initcheck.graphics.TiledGridPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PalettePanel extends TiledGridPanel {

	private static final long serialVersionUID = 1L;

	private MapPalette palette;

	private GridPanel modePanel = new GridPanel();

	private GridPanel typePanel = new GridPanel();

	private MapEditorDungeonGUI owner;

	private JLabel modeLabel = new JLabel("Point", SwingConstants.LEFT);

	private JLabel typeLabel = new JLabel("Wall", SwingConstants.LEFT);

	public PalettePanel(String type, MapEditorDungeonGUI owner) {
		super("images/rockLighter.jpg");

		this.owner = owner;

		modeLabel.setForeground(Color.white);
		modeLabel.setFont(InitFont.bigFont);

		typeLabel.setForeground(Color.white);
		typeLabel.setFont(InitFont.bigFont);

		if (type.equals("Dungeon")) {
			palette = new DungeonPalette();
		}
		modePanel.setOpaque(false);
		typePanel.setOpaque(false);

		doLayout(modeLabel);
		ypos++;
		doLayout(typeLabel);
		ypos++;

		doLayout(modePanel);
		ypos++;
		setWeightX(1.0);
		setWeightY(1.0);
		doLayout(typePanel);

		setPaletteGUI();
	}

	private void setPaletteGUI() {
		invalidate();
		modePanel.removeAll();
		typePanel.removeAll();
		modePanel.ypos = 0;
		typePanel.ypos = 0;

		int xpos = 0;
		for (int i = 0; i < palette.getModes().size(); i++) {
			PaletteMode pm = palette.getModes().get(i);

			TransparentButton label = new TransparentButton(pm.getIcon());
			label.setToolTipText(pm.getName());
			
			label.setForeground(Color.white);
			label.setContentAreaFilled(false);

			final PaletteMode pmCopy = pm;

			label.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setMode(pmCopy);
				}
			});

			modePanel.doLayout(label, xpos, modePanel.ypos);
			xpos++;
			if (i > 0 && i % 4 == 0) {
				modePanel.incYPos();
			}
		}

		xpos = 0;
		for (int i = 0; i < palette.getTypes().size(); i++) {
			PaletteType pm = palette.getTypes().get(i);

			TransparentButton label = new TransparentButton(pm.getIcon(), 30, 30);
			label.setToolTipText(pm.getName());
			
			label.setForeground(Color.white);
			label.setContentAreaFilled(false);

			final PaletteType pmCopy = pm;

			label.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setType(pmCopy);
				}
			});

			typePanel.doLayout(label, xpos, typePanel.ypos);
			xpos++;
			if (i > 0 && (i + 1) % 4 == 0) {
				xpos = 0;
				typePanel.incYPos();
			}
		}

		validate();
		repaint();
	}

	public void changePalette(String type) {
		if (type.equals("Dungeon")) {
			palette = new DungeonPalette();
		} else if (type.equals("Terrain")) {
			palette = new TerrainPalette();
		}
		setPaletteGUI();
	}

	private void setMode(PaletteMode pm) {
		owner.setDrawStyle(pm.getName());
		palette.setSelectedMode(pm);
		modeLabel.setText(pm.getName());
	}

	private void setType(PaletteType pm) {
		if (!pm.supportsMode(owner.getDrawStyle())){
			String mode = (String)pm.getValidModes().get(0);
			owner.setDrawStyle(mode);
			modeLabel.setText(mode);
			owner.setPaletteType(pm);
		}
		owner.setDrawMode(pm.getId());
		owner.setPaletteType(pm);
		palette.setSelectedType(pm);
		typeLabel.setText(pm.getName());
	}
}
