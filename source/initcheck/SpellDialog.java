package initcheck;

import initcheck.status.StatBless;
import initcheck.status.StatChant;
import initcheck.status.StatCurse;
import initcheck.status.StatPrayer;
import initcheck.status.StatSilence;
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

@SuppressWarnings("serial")
public class SpellDialog extends JDialog {
		
		private JButton closeButton = new JButton("Apply");
		private JButton cancelButton = new JButton("Cancel");
		private JButton clearButton = new JButton("Clear All");

		private JComboBox statusChoices;
		private JComboBox affected;
		private JTextField duration;
		private InitServer owner;
		
		public SpellDialog(final InitServer owner){
				
				super(owner.getFrame(), "Apply Group Spell", true);

				this.owner = owner;
				
				statusChoices = new JComboBox();
				statusChoices.addItem("Bless");
				statusChoices.addItem("Curse");
				statusChoices.addItem("Silence");
				statusChoices.addItem("Chant");
				statusChoices.addItem("Prayer");
				
				affected = new JComboBox();
				affected.addItem("Characters");
				affected.addItem("Monsters");
				
				JPanel contents = new JPanel(new GridLayout(4, 2));
				contents.add(new JLabel("Spell : "));
				contents.add(statusChoices);
				contents.add(new JLabel("Cast By : "));
				contents.add(affected);
				
				duration = new JTextField(10);
				contents.add(new JLabel("Duration : "));
				contents.add(duration);
				
				JPanel buttons = new JPanel();
				buttons.add(closeButton);
				buttons.add(cancelButton);
				buttons.add(clearButton);

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
				clearButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										owner.clearGroupSpells();
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
						case 1: msi = new StatCurse(dur);
								psi = new StatCurse(dur);
								break;
						case 2: msi = new StatSilence(dur);
								psi = new StatSilence(dur);
								break;
						case 3: msi = new StatChant(dur);
								psi = new StatChant(dur);
								break;
						case 4: msi = new StatPrayer(dur);
								psi = new StatPrayer(dur);
								break;	
				}
				
				switch (affected.getSelectedIndex()){
						case 0:
								owner.addGroupSpell(psi, 1);
								break;
						case 1:
								owner.addGroupSpell(msi, 2);
				}
				
		}
}
