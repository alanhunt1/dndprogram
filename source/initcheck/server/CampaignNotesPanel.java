package initcheck.server;

import initcheck.Encounter;
import initcheck.InitColor;
import initcheck.InitImage;
import initcheck.InitServer;
import initcheck.InitTextEditor;
import initcheck.dungeon.MapImage;
import initcheck.generator.TownInfoPanel;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TestTabUI;
import initcheck.graphics.TiledPanel;
import initcheck.utils.XTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.jgoodies.looks.plastic.PlasticTabbedPaneUI;

public class CampaignNotesPanel extends TiledPanel implements Serializable,
		Skinnable {

	private static final long serialVersionUID = 1L;

	XTabbedPane tabPane = new XTabbedPane(JTabbedPane.RIGHT);

	Vector<String> tabNames = new Vector<String>();

	Vector<Component> tabPanels = new Vector<Component>();

	String filename = null;

	public static final int TEXT_TAB = 1;

	public static final int MAP_TAB = 2;

	public static final int ENCOUNTER_TAB = 3;

	public static final int TOWN_TAB = 4;

	public JLabel fileLabel = new JLabel("New File");

	private InitServer owner;

	TiledPanel header = new TiledPanel();

	JLabel headerLabel = new JLabel();

	Skin skin = null;

	public InitServer getOwner(){
		return owner;
	}
	
	public CampaignNotesPanel(InitServer owner) {

		this.owner = owner;
		setBorder(null);
		setLayout(new BorderLayout());
		add(tabPane, BorderLayout.CENTER);

		// add(buttonPanel, BorderLayout.SOUTH);
		header.add(headerLabel);
		add(header, BorderLayout.NORTH);

		//tabPane.setOpaque(false);
		//tabPane.setUI(new TestTabUI());
		tabPane.setForeground(InitColor.woodText);
		
	}

	public void removeTab() {
		int confirm = JOptionPane.showConfirmDialog(this,
				"Remove current tab from the campaign file?");
		if (confirm == JOptionPane.YES_OPTION) {
			int selIndex = tabPane.getSelectedIndex();
		
			tabPane.remove(selIndex);
			int newIndex = 0;
			if (selIndex > 0) {
				newIndex = selIndex - 1;
			}
			if (tabPane.getTabCount() > 0) {
				tabPane.setSelectedIndex(newIndex);
			}
		}
	}

	public void setMapAsCurrent(String fileName) {
		owner.loadMapFromFile(fileName);
	}

	public void runEncounter(Encounter e, boolean newEncounter) {
		owner.runEncounter(e, newEncounter);
	}

	public void loadAllTabs() {

		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(getRootPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename = fc.getSelectedFile().getAbsolutePath();
		}

		headerLabel.setText(filename);

		try {
			FileInputStream fis = new FileInputStream(filename);
			GZIPInputStream gzis = new GZIPInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(gzis);
			tabPane.removeAll();
			int tabCount = (Integer) in.readObject();
			for (int i = 0; i < tabCount; i++) {
				String tabName = (String) in.readObject();
				int type = (Integer) in.readObject();
				Object model = in.readObject();

				switch (type) {
				case 1:
					
					InitTextEditor ite = new InitTextEditor();
					ite.setModel(model);
					tabPane.addTab(tabName, InitImage.scrollIcon, ite);
					tabPane.setBackgroundAt(i, InitColor.fadedRed);
					break;
				case 2:
					MapImage mi = new MapImage(this);
					mi.setModel(model);
					tabPane.addTab(tabName, InitImage.mapIcon, mi);
					tabPane.setBackgroundAt(i, InitColor.heavyForest);
					break;
				case 3:
					try{
					EncounterTab et = new EncounterTab(this);
					et.setModel(model);
					tabPane.addTab(tabName, InitImage.swordIcon, et);
					tabPane.setBackgroundAt(i, InitColor.blue);
					}catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 4:
					TownInfoPanel tp = new TownInfoPanel(this);
					tp.setModel(model);
					tabPane.addTab(tabName,InitImage.townIcon, tp);
					tabPane.setBackgroundAt(i, InitColor.woodText);
					break;
				}
				tabNames.add(tabName);
			}
			in.close();
		} catch (java.io.InvalidClassException err) {
			err.printStackTrace();
		} catch (IOException err) {
			err.printStackTrace();

		} catch (ClassNotFoundException err) {
			err.printStackTrace();

		}
		if (skin != null) {
			setSkin(skin);
		}
	}

	public void saveAs() {
		filename = null;
		saveAllTabs();
	}

	public void saveAllTabs() {
		if (filename == null) {
			JFileChooser fc = new JFileChooser("saves");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(getRootPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				filename = fc.getSelectedFile().getAbsolutePath();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			GZIPOutputStream gzos = new GZIPOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(gzos);
			out.writeObject(tabPane.getComponentCount());
			for (int i = 0; i < tabPane.getComponentCount(); i++) {
				String tabName = tabPane.getTitleAt(i);
				
				CampaignNotesTab tabObject = (CampaignNotesTab) tabPane
						.getComponentAt(i);

				out.writeObject(tabName);
				out.writeObject(tabObject.getType());
				out.writeObject(tabObject.getModel());
			}

			out.flush();
			out.close();

		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public void addNewEncounterTab() {

		EncounterTab text = new EncounterTab(this);
		text.setSkin(skin);
		String inputValue = JOptionPane
				.showInputDialog("What would you like the name of this panel to be?");
		tabNames.add(inputValue);
		tabPanels.add(text);
		tabPane.addTab(inputValue, InitImage.swordIcon,text);
	}

	public void addNewSpreadsheetTab() {

		// SpreadsheetPanel text = new SpreadsheetPanel();
		// JScrollPane tableScroll = new JScrollPane(text);
		// String inputValue = JOptionPane
		// .showInputDialog("What would you like the name of this panel to
		// be?");
		// tabNames.add(inputValue);
		// tabPanels.add(tableScroll);
		// tabPane.addTab(inputValue, tableScroll);
	}

	public void renameCurrentTab(String s) {
		int selIndex = tabPane.getSelectedIndex();
		if (selIndex > -1 && selIndex < tabNames.size()) {
			tabPane.setTitleAt(selIndex, s);
			tabNames.setElementAt(s, selIndex);
		}
	}

	public void moveTabUp(){
		int selIndex = tabPane.getSelectedIndex();
		if (selIndex > 0 && selIndex < tabNames.size()) {
				
		}
	}
	
	public void moveTabDown(){
		
	}
	
	public void addNewTownTab() {
		TownInfoPanel text = new TownInfoPanel(this);
		text.setSkin(skin);
		String inputValue = JOptionPane
				.showInputDialog("What would you like the name of this panel to be?");
		tabNames.add(inputValue);
		tabPanels.add(text);
		tabPane.addTab(inputValue, InitImage.townIcon,text);
	}

	public void addNewMapTab() {
		String inputValue = JOptionPane
				.showInputDialog("What would you like the name of this panel to be?");

		JFileChooser fc = new JFileChooser("saves");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(getRootPane());

		String mapFile = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			mapFile = fc.getSelectedFile().getAbsolutePath();
		}

		if (mapFile != null) {
			MapImage text = new MapImage(mapFile, this);
			text.setSkin(skin);
			tabNames.add(inputValue);
			tabPanels.add(text);
			tabPane.addTab(inputValue, InitImage.mapIcon,text);
			tabPane.setSelectedComponent(text);
		}

	}

	public void addNewTextTab() {

		InitTextEditor text = new InitTextEditor();
		text.setSkin(skin);
		String inputValue = JOptionPane
				.showInputDialog("What would you like the name of this panel to be?");
		tabNames.add(inputValue);
		tabPanels.add(text);
		tabPane.addTab(inputValue, InitImage.scrollIcon,text);
		tabPane.setSelectedComponent(text);

	}

	public void setSkin(Skin s) {

		if (skin == null || !s.getName().equals(skin.getName())) {

			invalidate();
			
			if (!s.isUseGraphics()) {
				tabPane.setUI(new PlasticTabbedPaneUI());
				tabPane.setForeground(Color.BLACK);
			} else {
				//tabPane.setUI(new TestTabUI());
				tabPane.setForeground(InitColor.woodText);
			}

			for (int i = 0; i < tabPane.getTabCount(); i++) {
				Component c = tabPane.getComponentAt(i);
				try {
					Skinnable sk = (Skinnable) c;
					sk.setSkin(s);
				} catch (ClassCastException ce) {

				}
			}
			validate();
			skin = s;
		}
	}
}
