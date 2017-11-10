package initcheck;

import initcheck.graphics.TiledPanel;
import initcheck.graphics.TiledTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class DescriptionDialog extends JDialog {
	static final long serialVersionUID = 1;

	private ImageIcon bgImage = new ImageIcon("images/rock043.jpg");

	public DescriptionDialog(final JFrame frame, String title, String message) {

		super(frame, title, true);
		
	}

	public DescriptionDialog(String title, String message) {
		super();
		
	}

	public static void display(String title, String message) {
		DescriptionDialog md = new DescriptionDialog(title, message);	
		md.init(title, message);
	}
	
	private void init(String title, String message) {
		PanelButton iconButton = new PanelButton("Close", 30);

		setTitle(title);

		TiledPanel contents = new TiledPanel(bgImage);
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

		TiledTextArea mesg = new TiledTextArea();
		mesg.setText(message);
		mesg.setOpaque(true);
		mesg.setLineWrap(true);
		mesg.setWrapStyleWord(true);
		mesg.setEditable(false);
		JScrollPane scroll = new JScrollPane(mesg);
		contents.add(scroll, BorderLayout.CENTER);
		contents.add(iconButton, BorderLayout.SOUTH);

		getContentPane().add(contents);

		setSize(new Dimension(400, 400));
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
