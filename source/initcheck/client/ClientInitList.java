package initcheck.client;

import initcheck.ClientInitListCellRenderer;
import initcheck.InitClient;
import initcheck.InitLogger;
import initcheck.Participant;
import initcheck.StatusException;
import initcheck.status.StatStunned;
import initcheck.status.StatusItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JList;

public class ClientInitList extends JList {

	static final long serialVersionUID = 1;

	private int listIndex = 0;

	private InitLogger logger = new InitLogger(this);

	InitClient owner;

	public ClientInitList(InitClient owner) {
		super();
		this.owner = owner;
		setFont(new Font("Courier", Font.PLAIN, 12));
		setBackground(Color.lightGray);
		setCellRenderer(new ClientInitListCellRenderer());

		// Add listener for popup menu.
		MouseListener popupListener = new PopupListener();
		addMouseListener(popupListener);
	}

	public void setListIndex(int i) {
		listIndex = i;
		setSelectedIndex(i);
		
	}

	public void goToCurrent(){
		setSelectedIndex(listIndex);
	}
	
	public int getListIndex() {
		return listIndex;
	}

	public void doCommand(int[] positions, String command) {
		setSelectedIndices(positions);
		if (command.equals("CLEARSPELLS")) {
			clearSpellEffects();
		} else if (command.equals("MARKALIVE")) {
			markListItemAlive();
		} else if (command.equals("UNSTUN")) {
			markListItemUnStunned();
		} else if (command.equals("MOVEDOWN")){
			moveListItemDown();
			
		}else if (command.equals("MOVEUP")){
			moveListItemUp();
			
		}else if (command.equals("DELETE")){
			removeListItem();
			
		}else if (command.equals("CLEARNOTES")){
			clearNotes();
			
		}else{
			logger.log("error", "Received invalid command "+command);
		}
	}

	public void clearNotes() {
		Vector<Participant> v = owner.getCharVector();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = v.get(idx);
			p.setNotes("");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);
	}
	
	public void removeListItem() {

		int selArray[] = getSelectedIndices();
		
		Vector<Participant> delVector = new Vector<Participant>();
		Vector<Participant> mVector = new Vector<Participant>();
		Vector<Participant> v = owner.getCharVector();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			if (listIndex >= idx) {
				listIndex--;
			}
			delVector.add(v.get(idx));
			if (v.get(idx).getPType().equalsIgnoreCase("Monster")) {
				mVector.add((Participant) v.get(idx));
			}
			// v.removeElementAt(idx-i);
		}
		selArray = new int[1];
		selArray[0] = listIndex;

		owner.removeParticipants(delVector);
	
	    v = owner.getCharVector();
		updateList(v, selArray);
	}
	
	public void moveListItemUp() {
		int selArray[] = getSelectedIndices();
		
		int newIdx = 0;
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			newIdx = idx - 1;
			if (idx == 0) {
				return;
			}
			owner.changePosition(idx, newIdx);
			selArray[i] = newIdx;
		}
		Vector<Participant> v = owner.getCharVector();
		updateList(v, selArray);
	}
	
	public void moveListItemDown() {
		int selArray[] = getSelectedIndices();
		
		int newIdx = 0;
		for (int i = selArray.length - 1; i >= 0; i--) {
			int idx = selArray[i];
			newIdx = idx + 1;
			if (idx + 1 == getModel().getSize()) {
				return;
			}
			owner.changePosition(idx, newIdx);
			
			selArray[i] = newIdx;
		}
		Vector<Participant> v = owner.getCharVector();
		updateList(v, selArray);
		//owner.playSound(owner.getSound("listmove"));
	}
	
	public void setStatus(StatusItem s) {
		Vector<Participant> v = owner.getCharVector();

		int selArray[] = getSelectedIndices();
		
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			
			Participant p = v.get(idx);
			if (s.getName().equals("Dead")) {
				p.setDead("TRUE");
			}
			try {
				
				p.addStatusItem((StatusItem) s.clone());
			} catch (StatusException e) {
				
				logger.log(e.toString());
			}
			
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);
	}

	public boolean processListItem(int i) {
		
		Vector<Participant> v = owner.getCharVector();
		if (i < v.size()) {
			Participant p = (Participant) v.get(i);
			p.setRoundDamage(0);
			
			boolean statusCode = p.advanceStatus();
			owner.clearRoundDamage(p.getID());
			return statusCode;
		}
		
		return false;
	}

	public void advanceList(int i) {
		// on the client side, we don't keep the "next" pointer to the list,
		// because
		// that's controlled by the server. But, we want position selections to
		// be handeled differently from the server clicking "next", because next
		// will decrement status. So, we need to decrement status on our side
		// too.
		processListItem(i);

	}

	public void clearSpellEffects() {
		Vector<Participant> v = owner.getCharVector();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			Participant p = v.get(idx);
			p.resetStatus();
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);
	}

	public void markListItemAlive() {
		Vector<Participant> v = owner.getCharVector();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = v.get(idx);
			p.removeStatusItem("Dead");
			p.setDead("FALSE");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);

	}

	public void markListItemUnStunned() {
		Vector<Participant> v = owner.getCharVector();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = v.get(idx);
			p.decrementStatusItem("Stunned");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);
	}
	
	public void markListItemStunned() {
		//stuns++;
		StatStunned stat = new StatStunned();
		setStatus(stat);
		//owner.sendSound("stun");
	}

	private void updateList(Vector<Participant> v, int selArray[]) {
		if (selArray.length > 0) {
			
			owner.setCharVector(v);
			setListData(v);
			setSelectedIndices(selArray);
		}
	}

	public void setStatus(int[] positions, StatusItem status) {
		
		setSelectedIndices(positions);
		setStatus(status);
	}

	class PopupListener extends MouseAdapter {

		// on a double click, pop up the status dialog for the
		// list item. On a single click, just update the
		// client list to show the newly selected item.
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {

			} else if (getSelectedIndex() >= 0) {

			}
		}

		// check all the clicks for a right click, and trigger the
		// popup menu if you see one.
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				// owner.getPopupMenu().show(e.getComponent(),
				// e.getX(), e.getY());
			}
		}
	}

}
