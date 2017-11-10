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
public class RemoveMonsterDialog extends JDialog {
		
		
		private JList groupList;
		private InitServer owner;
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

		public RemoveMonsterDialog(final InitServer owner, String title){
				
				super(owner.getFrame(), title, true);
				this.owner = owner;



				JButton removeButton = new JButton("Remove Monster Group");

				TiledPanel contents = new TiledPanel(bgImage);

				removeButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										removeMonsterSet();
								}
						});				
				
				contents.setLayout(new BorderLayout());
				groupList = new JList(owner.getMonsterGroups());
				groupList.setSelectedIndex(0);
				contents.add(groupList, BorderLayout.CENTER);
				contents.add(removeButton, BorderLayout.SOUTH);
				
				getContentPane().add(contents);
				
				setSize(new Dimension(300, 150));
				setLocation(100, 100);
				setVisible(true);
		}
		
		private void removeMonsterSet(){
				int selSet = groupList.getSelectedIndex();
				owner.removeMonsterSet(selSet);
				dispose();
		}
		
}
