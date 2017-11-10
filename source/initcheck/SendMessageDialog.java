package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SendMessageDialog extends JDialog {
		
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");
		private InitProgram owner = null;
		private	JTextArea mesg = null;
		public SendMessageDialog(final InitProgram owner, 
														 String title, String message){
				
				super(owner.getFrame(), title, true);
				this.owner = owner;
				mesg = new JTextArea(message, 4, 80);

				PanelButton iconButton = new PanelButton("Close", 30);
				
				TiledPanel contents = new TiledPanel(bgImage);
				contents.setLayout(new BorderLayout());
				contents.setBorder(BorderFactory.createEmptyBorder(
																				20, //top
                                        20, //left
                                        10, //bottom
                                        20) //right
                                        );
				
				iconButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
										sendMessage();
										setVisible(false);
								}
						});				
				
			
				mesg.setOpaque(true);
				mesg.setLineWrap(true);
				mesg.setWrapStyleWord(true);

				contents.add(mesg, BorderLayout.CENTER);
				contents.add(iconButton, BorderLayout.SOUTH);
				
				getContentPane().add(contents);

				setSize(new Dimension(300, 150));
				setLocation(100, 100);
				setVisible(true);
		}
		public void clear(){
				mesg.setText("");
		}
		public void sendMessage(){
				owner.sendMessage(mesg.getText());
		}
}
