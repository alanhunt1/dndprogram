package initcheck;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JScrollPane;

import initcheck.character.GenericListCellRenderer;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListString;
import initcheck.graphics.TiledPanel;

public class NetworkMessagePanel extends TiledPanel {

	private static final long serialVersionUID = 1L;

	private TiledList messageList = new TiledList();

	private Vector<TiledListString> messages = new Vector<TiledListString>();

	private PanelButton clear = new PanelButton("Clear");

	private JScrollPane listScroll = new JScrollPane(messageList);

	public NetworkMessagePanel() {
		setLayout(new BorderLayout());
		add(listScroll, BorderLayout.CENTER);
		add(clear, BorderLayout.SOUTH);
		messageList.setCellRenderer(new GenericListCellRenderer());
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
	}

	public void addMessage(String s) {
		messages.add(new TiledListString(s));
		messageList.setListData(messages);
	}

	public void clear() {
		messages = new Vector<TiledListString>();
		messageList.setListData(messages);
	}

}
