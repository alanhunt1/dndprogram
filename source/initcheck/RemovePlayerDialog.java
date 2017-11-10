package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;

@SuppressWarnings("serial")
public class RemovePlayerDialog extends JDialog {
		
		
		private JList groupList;
		private InitServer owner;
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

		public RemovePlayerDialog(final InitServer owner, String title){
				
				super(owner.getFrame(), title, true);
				this.owner = owner;


				JButton removeButton = new JButton("Remove Party");

				TiledPanel contents = new TiledPanel(bgImage);
				
				removeButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										removePlayerSet();
								}
						});				
				
				contents.setLayout(new BorderLayout());
				groupList = new JList(owner.getPlayerGroups());
				groupList.setSelectedIndex(0);
				contents.add(groupList, BorderLayout.CENTER);
				contents.add(removeButton, BorderLayout.SOUTH);
				
				getContentPane().add(contents);
				
				setSize(new Dimension(300, 150));
				setLocation(100, 100);
				setVisible(true);
		}
		
		private void removePlayerSet(){
				int selSet = groupList.getSelectedIndex();
				owner.removePlayerSet(selSet);
				dispose();
		}
		
}
