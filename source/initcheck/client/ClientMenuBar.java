package initcheck.client;

import initcheck.InitClient;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ClientMenuBar extends JMenuBar implements ActionListener {

	static final long serialVersionUID = 1;

	// the server
	InitClient owner;

	// the file menu
	private JMenu menu;

	private JMenuItem exit = null;

	// the options menu
	private JMenu optionMenu;

	private JMenuItem refresh;

	private JMenuItem whine;

	private JMenuItem preferences;

	private JMenuItem toggleMiniMap;
	
	// the tools menu
	private JMenu toolsMenu;

	private JMenuItem viewNotes;

	private JMenuItem viewHitSheet;
	
	private JMenuItem netStats;
	
	// private JMenuItem damagePanel;

	public void setFont(Font f) {
		if (exit != null) {
			exit.setFont(f);
			refresh.setFont(f);
			viewNotes.setFont(f);
			whine.setFont(f);
			preferences.setFont(f);
			viewHitSheet.setFont(f);
			toggleMiniMap.setFont(f);
		}
	}

	public ClientMenuBar(InitClient owner) {
		super();
		this.owner = owner;

		// Build the file menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		this.add(menu);

		// exit
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);

		// build the options menu
		optionMenu = new JMenu("Options");
		this.add(optionMenu);

		refresh = new JMenuItem("Refresh From Server");
		refresh.addActionListener(this);
		optionMenu.add(refresh);

		whine = new JMenuItem("Whine About Treasure");
		whine.addActionListener(this);
		optionMenu.add(whine);

		preferences = new JMenuItem("Preferences");
		preferences.addActionListener(this);
		optionMenu.add(preferences);

		toggleMiniMap = new JMenuItem("Toggle Mini Map");
		toggleMiniMap.addActionListener(this);
		toggleMiniMap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		optionMenu.add(toggleMiniMap);
		
		// build the tools menu
		toolsMenu = new JMenu("Tools");
		this.add(toolsMenu);

		viewNotes = new JMenuItem("View Notes");
		viewNotes.addActionListener(this);
		toolsMenu.add(viewNotes);

			
		viewHitSheet = new JMenuItem("View Hit Sheet In Window");
		viewHitSheet.addActionListener(this);
		toolsMenu.add(viewHitSheet);

		netStats = new JMenuItem("Network Stats");
		netStats.addActionListener(this);
		toolsMenu.add(netStats);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source == exit) {
			owner.shutdown();
		} else if (source == viewNotes) {
			owner.showMonsterNotes();
		} else if (source == refresh) {
			owner.refreshFromServer();
		} else if (source == whine) {
			owner.whineToServer();
		} else if (source == preferences) {
			owner.showPreferences();
		} else if (source == toggleMiniMap) {
			owner.getMap().toggleMiniMap();
		}else if (source == viewHitSheet) {
			owner.showHitSheetDialog();
		}else if (source == netStats) {
			owner.showNetStats();
		}
	}
}
