package initcheck.server;

import initcheck.InitServer;
import initcheck.status.StatBless;
import initcheck.status.StatusItem;

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
import javax.swing.JTextField;

public class IndividSpellDialog extends JDialog {
	static final long serialVersionUID = 1;
		private JButton closeButton = new JButton("Apply");
		private JButton cancelButton = new JButton("Cancel");
		private JComboBox statusChoices;
		private JComboBox affected;
		private JTextField duration;
		private InitServer owner;
		
		public IndividSpellDialog(final InitServer owner){
				
				super(owner.getFrame(), "Apply Group Spell", true);

				this.owner = owner;
				
				statusChoices = new JComboBox();
				statusChoices.addItem("Bless");
				statusChoices.addItem("Curse");
				statusChoices.addItem("Silence");
				statusChoices.addItem("Chant");
				
				affected = new JComboBox();
				affected.addItem("All Participants");
				affected.addItem("Characters Only");
				affected.addItem("Monsters Only");
				
				JPanel contents = new JPanel(new GridLayout(4, 2));
				contents.add(new JLabel("Spell : "));
				contents.add(statusChoices);
				contents.add(new JLabel("Affects : "));
				contents.add(affected);
				
				duration = new JTextField(10);
				contents.add(new JLabel("Duration : "));
				contents.add(duration);
				
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
										addGroupSpell();
										setVisible(false);
								}
						});
				
				cancelButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										setVisible(false);
								}
						});
		}

		private void addGroupSpell(){
				StatusItem psi = null;
				StatusItem msi = null;
				int dur = Integer.parseInt(duration.getText());

				switch (statusChoices.getSelectedIndex()){
						case 0: msi = new StatBless(dur);
								psi = new StatBless(dur);
								break;
				}
				
				switch (affected.getSelectedIndex()){
						case 0:
								owner.addGroupSpell(psi, 1);
								owner.addGroupSpell(msi, 2);
								break;
						case 1:
								owner.addGroupSpell(psi, 1);
								break;
						case 2:
								owner.addGroupSpell(msi, 2);
				}
				
		}
}
