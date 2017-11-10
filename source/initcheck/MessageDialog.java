package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class MessageDialog extends JDialog {
	static final long serialVersionUID = 1;
	static JTextArea mesg;
	
	//private static MessageDialog md = new MessageDialog();

	public MessageDialog(){
	}
	
	public MessageDialog(final JFrame frame, String title, String message) {

		super(frame, title, true);
		init(title, message);
	}

	public MessageDialog(String title, String message) {
		super();
		init(title, message);
	}

	public static void display(String title, String message) {
		MessageDialog md = new MessageDialog(title, message);	
		md.init(title, message);	
	}
	
	public void setMessage(String message){
		mesg = new JTextArea(message, 4, 80);
	}
	
	private void init(String title, String message) {
		PanelButton iconButton = new PanelButton("Close", 30);

		setTitle(title);

		TiledPanel contents = new TiledPanel();
		contents.setLayout(new BorderLayout());
		contents.setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				10, // bottom
				20) // right
				);
		iconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		mesg = new JTextArea(message, 4, 80);
		mesg.setOpaque(true);
		mesg.setLineWrap(true);
		mesg.setWrapStyleWord(true);
		mesg.setEditable(false);
		contents.add(mesg, BorderLayout.CENTER);
		contents.add(iconButton, BorderLayout.SOUTH);

		getContentPane().add(contents);

		setSize(new Dimension(300, 150));
		setLocationRelativeTo(null);

		setVisible(true);

		iconButton.requestFocusInWindow();
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
		rootPane.registerKeyboardAction(actionListener, stroke,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		rootPane.registerKeyboardAction(actionListener, stroke2,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

}
