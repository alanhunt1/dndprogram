package initcheck;

import initcheck.database.InitDBC;
import initcheck.graphics.TiledDialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ViewMonsterNotesDialog extends TiledDialog {
		
		
		
		private JTextArea notes = new JTextArea(2, 30);
		private PanelButton saveButton = new PanelButton("Save", 70);
		private PanelButton cancelButton = new PanelButton("Close", 70);
		private JTextField monsterName = new JTextField(20);
		private String id;
		
		private boolean hasNotes = true;
		
		public ViewMonsterNotesDialog(final InitClient owner, String id, String name){
				
				super(owner.getFrame(), "Select Font", true);
				this.id = id;
				
				monsterName.setFont(owner.getDefaultFont());
				notes.setFont(owner.getDefaultFont());
				
				JPanel contents = new JPanel(new BorderLayout());
				JScrollPane scroll = new JScrollPane(notes);
				JPanel buttons = new JPanel();
				
				buttons.add(saveButton);
				buttons.add(cancelButton);
				buttons.setOpaque(false);
				
				JPanel stylePanel = new JPanel();
			
				stylePanel.add(monsterName);
				contents.add(stylePanel, BorderLayout.NORTH);
				contents.add(scroll, BorderLayout.CENTER);
				contents.setOpaque(false);
				setButtonBar(buttons);
				setMainWindow(contents);

				InitDBC db = new InitDBC();
				String noteStr = db.getPlayerNotes(id);
				if (noteStr == null){
						hasNotes = false;
						noteStr = "";
				}
				notes.setText(noteStr);
				monsterName.setText(name);

				pack();
				
				//Finish setting up the frame, and show it.
				addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
																				
								}
						});
				
				saveButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										saveMonsterNotes();
								}
						});

				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});
		}

		public void saveMonsterNotes(){
				InitDBC db = new InitDBC();
				if (hasNotes){
						db.updatePlayerNotes(id,notes.getText(), monsterName.getText());
				}else{
						db.setPlayerNotes(id,notes.getText(), monsterName.getText());
				}
				
		}
}
