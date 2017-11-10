package initcheck.character;

import initcheck.database.Services;
import initcheck.database.ServicesDAO;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ServiceChooserDialog extends JFrame implements
		ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private JList servicesList = new JList();

	private JScrollPane listScroll = new JScrollPane(servicesList);

	private ServiceChooserOwner owner;

	public ServiceChooserDialog(ServiceChooserOwner owner) {
		this.owner = owner;
		ServicesDAO db = new ServicesDAO();
		servicesList.setListData(db.getServices());

		servicesList.addListSelectionListener(this);

		servicesList.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setItem();
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
		servicesList.ensureIndexIsVisible(servicesList.getSelectedIndex());
	}

	public void setItem() {
		Services e = (Services) servicesList.getSelectedValue();
		owner.setService(e);

	}

}
