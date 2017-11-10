package initcheck.character;

import initcheck.DCharacter;
import initcheck.LoadComparison;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LoadComparisonDialog extends JFrame implements
		ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private JList changeList = new JList();

	private JScrollPane listScroll = new JScrollPane(changeList);

	public LoadComparisonDialog(DCharacter oldchar, DCharacter newchar) {

		changeList.setListData(LoadComparison.compare(oldchar, newchar));

		changeList.addListSelectionListener(this);

		changeList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// setItem();
					// dispose();
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});
		TiledPanel p = new TiledPanel();
		p.setLayout(new BorderLayout());
		p.add(listScroll, BorderLayout.CENTER);

		getContentPane().add(p);

		pack();
		setVisible(true);
	}

	public void valueChanged(ListSelectionEvent e) {
		changeList.ensureIndexIsVisible(changeList.getSelectedIndex());
	}

}
