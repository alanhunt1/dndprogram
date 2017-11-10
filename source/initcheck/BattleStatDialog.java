package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class BattleStatDialog extends JDialog {
		
		static final long serialVersionUID = 1;
	
		private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");
		JTextPane mesg = new JTextPane();
		TiledPanel contents = new TiledPanel(bgImage);
		Vector<String> messages = new Vector<String>();

		/**
		 * Get the Messages value.
		 * @return the Messages value.
		 */
		public Vector<String> getMessages() {
				return messages;
		}

		/**
		 * Set the Messages value.
		 * @param newMessages The new Messages value.
		 */
		public void setMessages(Vector<String> newMessages) {
				this.messages = newMessages;
		}

		
		public BattleStatDialog(final JFrame frame, 
												 String title, String message){
				
				super(frame, title, false);
				init(title, message);
		}

		public BattleStatDialog(String title, String message){
				super();
				init(title, message);
		}

		private void init(String title, String message){
				contents.setPreferredSize(new Dimension(500, 500));
				
				PanelButton iconButton = new PanelButton("Close", 30);
				
		
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
				
			
				mesg.setOpaque(true);
				//mesg.setLineWrap(true);
				//mesg.setWrapStyleWord(true);
				mesg.setEditable(false);

				Style def = StyleContext.getDefaultStyleContext().
						getStyle(StyleContext.DEFAULT_STYLE);
				
        Style regular = mesg.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

				Style s = mesg.addStyle("round", regular);
        StyleConstants.setBold(s, true);
				StyleConstants.setForeground(s, new Color(0, 150, 0));
				
				s = mesg.addStyle("stun", regular);
				StyleConstants.setBold(s, true);
				StyleConstants.setForeground(s, new Color(0, 50, 150));
				
				s = mesg.addStyle("dead", regular);
				StyleConstants.setBold(s, true);
				StyleConstants.setForeground(s, InitColor.red);
				
				s = mesg.addStyle("poisoned", regular);
				StyleConstants.setBold(s, true);
				StyleConstants.setForeground(s, new Color(51, 255, 0));
				
				JScrollPane scroll = new JScrollPane(mesg);
				contents.add(scroll, BorderLayout.CENTER);
				contents.add(iconButton, BorderLayout.SOUTH);
			

				getContentPane().add(contents);

				setSize(new Dimension(500, 400));
				setLocationRelativeTo(null);
				setVisible(true);

				iconButton.requestFocusInWindow();
		}

		public void addMessage(String s){
				messages.add(s);
				Document d = mesg.getDocument();
				
				try{
					
						d.insertString(d.getLength(), s+"\n",
													 mesg.getStyle("regular"));
						
				}catch (BadLocationException ble){

				}
						//mesg.insert(s+"\n", 0);
				//mesg.setCaretPosition(mesg.getText().length()-1);
				
				contents.paintImmediately(0,0,new Double(getSize().getWidth()).intValue(), new Double(getSize().getHeight()).intValue());
				
		}

		protected JRootPane createRootPane() {
				ActionListener actionListener = new ActionListener() {
								public void actionPerformed(ActionEvent actionEvent) {
										dispose();
								}
						};
				JRootPane rootPane = new JRootPane();
				KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
				KeyStroke stroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
				rootPane.registerKeyboardAction(actionListener, stroke2, JComponent.WHEN_IN_FOCUSED_WINDOW);
				return rootPane;
		}

	
}
