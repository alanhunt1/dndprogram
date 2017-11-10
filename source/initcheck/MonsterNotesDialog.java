package initcheck;

import initcheck.database.Monster;
import initcheck.database.MonsterDAO;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MonsterNotesDialog extends TiledDialog {
	static final long serialVersionUID = 1;
		
		
		private JList playerList = null;
		private PanelButton viewButton = new PanelButton("View", 70);
		private PanelButton cancelButton = new PanelButton("Close", 70);
		private PanelButton searchButton = new PanelButton("Search", 70);
		private JTextField monsterName = new JTextField(10);
		public MonsterNotesDialog(final InitClient owner){
				
				super(owner.getFrame(), "Select Font", true);
				monsterName.setFont(owner.getDefaultFont());
				JPanel contents = new JPanel(new BorderLayout());
					
				playerList = new JList();
				playerList.setCellRenderer(new DefaultListCellRenderer());
				playerList.setFont(owner.getDefaultFont());
				playerList.setBackground(Color.lightGray);
				
				JScrollPane scroll = new JScrollPane(playerList);
				JPanel buttons = new JPanel();
				buttons.add(viewButton);
				buttons.add(cancelButton);
				
				buttons.setOpaque(false);
				
				JPanel stylePanel = new JPanel();
			
				stylePanel.add(monsterName);
				stylePanel.add(searchButton);
				contents.add(stylePanel, BorderLayout.NORTH);
				contents.add(scroll, BorderLayout.CENTER);
				contents.setOpaque(false);
				setButtonBar(buttons);
				setMainWindow(contents);
											
				pack();

				//Finish setting up the frame, and show it.
				addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
																				
								}
						});
				
				viewButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										Monster m = (Monster)playerList.getSelectedValue();
										ViewMonsterNotesDialog vmd = new ViewMonsterNotesDialog(owner, ""+m.getID(), m.getName());
										vmd.setVisible(true);
								}
						});

				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});

				searchButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										getMonsters();
								}
						});
		}

		public void getMonsters(){
				MonsterDAO db = new MonsterDAO();
				Vector<Monster> v = db.getMonsters(monsterName.getText(), null, "NAME");
				playerList.setListData(v);
		}
}
