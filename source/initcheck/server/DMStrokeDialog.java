package initcheck.server;

import initcheck.InitImage;
import initcheck.InitProgram;
import initcheck.PanelButton;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JDialog;

public class DMStrokeDialog extends JDialog {
	static final long serialVersionUID = 1;
		
		private Vector<String> strokeVector = new Vector<String>();
		private TiledTextArea mesg = new TiledTextArea("", 4, 80);
		private Random rnd = new Random();
		private int index = 0;
		private int idx = 0;
		public DMStrokeDialog(final InitProgram owner, 
													String title){
				
				super(owner.getFrame(), title, true);
				
				strokeVector.add("You can't be a day over 29.");
				strokeVector.add("You are wise beyond belief.");
				strokeVector.add("God am the DM.");
				strokeVector.add("What's a good looking DM like you \n doing with geeks like us?");
				strokeVector.add("Lookout Ladies!  The DM is rolling nothing but 20s tonight!");
				strokeVector.add("My, you look nice today.");
				strokeVector.add("You've lost weight, haven't you?");
				strokeVector.add("You're obviously a man of consequence.");
				strokeVector.add("Damn!  I'm not gay or anything, but you're looking fine!");
				strokeVector.add("Dude, You ROCK!");
				strokeVector.add("You, sir, are a gentleman and a scholar.");
				strokeVector.add("Don't hurt us lowly worms, all powerful DM!");
				strokeVector.add("You Da MAN!");
				strokeVector.add("You are the epitome of DM-itude");
				strokeVector.add("We who hopefully are not about to die salute you!");
				strokeVector.add("Boundless in your mercy, Unparalleled in your wisdom, and good looking in those pants... could you ask for anything more?");
				strokeVector.add("You're My HERO!");
				strokeVector.add("Look at the rippling pecs on that DM!");
				strokeVector.add("Einstein called... he wants his brain back.");
				strokeVector.add("If you got any cooler, we'd all have to put on sweaters.");
				strokeVector.add("Ladies and Gentlemen, please welcome... THE DM!  (Yaaaaaay!)");

				PanelButton iconButton = new PanelButton("Close", 50);
				PanelButton againButton = new PanelButton("Again", 50);
				
				TiledPanel contents = new TiledPanel();
				contents.setLayout(new BorderLayout());
				contents.setBorder(BorderFactory.createEmptyBorder(
																				20, //top
                                        20, //left
                                        10, //bottom
                                        20) //right
                                        );
				iconButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										dispose();
								}
						});				
				
				againButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										resetMessage();
								}
						});	

			
				mesg.setOpaque(true);
				mesg.setLineWrap(true);
				mesg.setWrapStyleWord(true);
				resetMessage();
				contents.add(mesg, BorderLayout.CENTER);
				
				TiledPanel buttonPanel = new TiledPanel(InitImage.lightRocks);
				
				buttonPanel.add(againButton);
				buttonPanel.add(iconButton);
				
				contents.add(buttonPanel, BorderLayout.SOUTH);
				
				getContentPane().add(contents);

				setSize(new Dimension(300, 150));
				setLocationRelativeTo(null);
				setVisible(true);
		}
		
		public void resetMessage(){
				
				while(idx == index){
						idx = rnd.nextInt(strokeVector.size());
				}
				index = idx;
				mesg.setText((String)strokeVector.get(idx));
		}

}
