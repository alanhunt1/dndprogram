package initcheck;

import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ChatPanel extends TiledPanel {

	static final long serialVersionUID = 1;

	private static ImageIcon backgroundImage = new ImageIcon(
			"images/rock043.jpg");

	private JTextArea chatWindow = new JTextArea(10, 30);

	private JTextArea inputWindow = new JTextArea(2, 30);

	private InitProgram owner;

	private PanelButton sendButton = new PanelButton("Send");

	public ChatPanel(InitProgram owner) {
		super(backgroundImage);
		this.owner = owner;
		setLayout(new BorderLayout());
		add(chatWindow, BorderLayout.CENTER);
		JPanel sendPanel = new JPanel();
		sendPanel.add(inputWindow);
		sendPanel.add(sendButton);
		add(sendPanel, BorderLayout.SOUTH);
		setBorder(BorderFactory.createEmptyBorder(20, // top
				20, // left
				20, // bottom
				20) // right
		);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
			}
		});

	}

	public void addMessage(String s, String id) {
	
		chatWindow.append("\n" + id + "->" + s);
	}

	public void sendMessage() {
		owner.sendChatMessage(inputWindow.getText());
		addMessage(inputWindow.getText(), owner.getId());
		inputWindow.setText("");
	}
}
