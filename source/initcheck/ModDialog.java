package initcheck;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModDialog extends JDialog {
	static final long serialVersionUID = 1;
		//private JTextField playerStatus = new JTextField(10);
		private JLabel playerName = new JLabel();
		//private JTextField statusMod = new JTextField(2);
		
		private JButton closeButton = new JButton("Modify");
		private JButton cancelButton = new JButton("Cancel");
		private JComboBox statusChoices;
		
		public ModDialog(final InitServer owner, Participant c, int i){
				
				super(owner.getFrame(), "Modify Player", true);
				//final int index = i;
				//final Participant p = c;
				
				statusChoices = new JComboBox();
				statusChoices.addItem("Normal");
				statusChoices.addItem("Stunned");
				statusChoices.addItem("Dead");
				statusChoices.addItem("Sleeping");
				statusChoices.addItem("Blind");

				statusChoices.setSelectedItem(c.getStatus());
				
				
				JPanel contents = new JPanel(new GridLayout(4, 1));
				contents.add(new JLabel("Name : "));
				playerName.setText(c.getName());
				contents.add(playerName);
				//contents.add(new JLabel("Status : "));
				//playerStatus.setText(c.getStatus());
				//contents.add(statusChoices);
				//contents.add(new JLabel("Modifier : "));
				//statusMod.setText(""+c.getStatusMod());
				//contents.add(statusMod);

				JPanel buttons = new JPanel();
				buttons.add(closeButton);
				buttons.add(cancelButton);
				
				getContentPane().add(contents, BorderLayout.CENTER);
				getContentPane().add(buttons, BorderLayout.SOUTH);
								
				setSize(new Dimension(300, 150));

				//Finish setting up the frame, and show it.
				addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
										
								}
						});
				
				closeButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										//owner.modifyParticipant(index, p);
										setVisible(false);
								}
						});
				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});
		}


}
