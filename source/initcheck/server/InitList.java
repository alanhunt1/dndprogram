package initcheck.server;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.Participant;
import initcheck.StatusException;
import initcheck.graphics.TiledList;
import initcheck.status.StatDead;
import initcheck.status.StatStunned;
import initcheck.status.StatusItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

import javax.swing.JOptionPane;

public class InitList extends TiledList {

	static final long serialVersionUID = 1;

	private int listIndex = 0;

	private InitServer owner;

	private InitLogger logger = new InitLogger(this);

	int kills = 0;

	int stuns = 0;

	/**
	 * Get the Stuns value.
	 * 
	 * @return the Stuns value.
	 */
	public int getStuns() {
		return stuns;
	}

	/**
	 * Set the Stuns value.
	 * 
	 * @param newStuns
	 *            The new Stuns value.
	 */
	public void setStuns(int newStuns) {
		this.stuns = newStuns;
	}

	/**
	 * Get the Kills value.
	 * 
	 * @return the Kills value.
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * Set the Kills value.
	 * 
	 * @param newKills
	 *            The new Kills value.
	 */
	public void setKills(int newKills) {
		this.kills = newKills;
	}

	public InitList(InitServer owner) {

		super();
		this.owner = owner;

		setFont(new Font("Courier", Font.PLAIN, 12));
		setBackground(Color.lightGray);
		setCellRenderer(new InitListCellRenderer());
		// Add listener for popup menu.
		MouseListener popupListener = new PopupListener();
		addMouseListener(popupListener);
		setSelectedIndex(listIndex);
	}

	public void resetStats() {
		kills = 0;
		stuns = 0;
	}

	public void setListIndex(int i) {
		
		if (i >= 0) {
			listIndex = i;
			setSelectedIndex(i);
		}
	}

	public int getListIndex() {
		return listIndex;
	}

	public String getText() {
		Vector<Participant> v = owner.getEngine().getSortedList();
		String outStr = "";
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			outStr += p + "\n";
		}
		return outStr;
	}

	public void setStatus(StatusItem s) {
		Vector<Participant> v = owner.getEngine().getSortedList();

		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			Participant p = v.get(idx);
			if (s.getName().equals("Dead")) {
				
				p.setDead("TRUE");
				if (!p.getPType().equals("MONSTER")) {
					owner.recordPlayerDeath(p.getName(), p.getID());
				}
			}
			try {
				p.addStatusItem((StatusItem) s.clone());
			} catch (StatusException e) {
				logger.log(e.toString());
			}

			v.setElementAt(p, idx);
		}
		updateList(v, selArray, s);
	}

	public boolean processListItem(int i) {
		Vector<Participant> v = owner.getEngine().getSortedList();
		
		if (i < v.size()) {
			Participant p = v.get(i);
			p.setRoundDamage(0);
			owner.updateMonsterStun(p.getName());
			boolean statusCode = p.advanceStatus();

			if (statusCode && p.getStatusText().equals("He's feeling fine")) {
				owner.sendSound("unstun");
			}

			return statusCode;
		}


		return false;
	}

	public void advanceList() {

		owner.triggerAdvanceUpdate();

		// if the current position in the party tabs is not the one that the
		// initiative is on, switch to that tab, and call advance again.
		if (owner.currentGroup != owner.getTabIndex()) {
			owner.setInitTab();
			return;
		}

		if (listIndex + 1 == getModel().getSize()) {
			owner.newRound();
			return;
		} else {
			listIndex++;
		}

		if (listIndex > getModel().getSize()) {
			
			return;
		}

		setSelectedIndex(listIndex);
		ensureIndexIsVisible(listIndex);

		// attempt to keep list selection in middle
		int displayStart = getFirstVisibleIndex();
		int displayEnd = getLastVisibleIndex();
		int numDisplayed = displayEnd - displayStart;
		int advanceNumber = listIndex + (numDisplayed / 2);
		if (advanceNumber > getModel().getSize()) {
			ensureIndexIsVisible(getModel().getSize() - 1);
		} else {
			ensureIndexIsVisible(advanceNumber);
		}

		if (owner.getHitSheet() != null) {
			int index = owner.getAltList().getIndex(
					((Participant) getSelectedValue()).getName());
			if (index >= 0) {
				owner.getHitSheet().setSelectedMonster(index);
			}
		}

		owner.getClientTalker().advancePosition(listIndex);
		owner.getClientTalker().updatePosition(listIndex);

		owner.backupToFile();
		owner.doAutoRoll();

		if (processListItem(listIndex)) {
			// Vector v = owner.getEngine().getSortedList();
			// owner.getClientTalker().sendData(v);
			advanceList();
		}

	}

	public void setNotes() {
		String inputValue = JOptionPane.showInputDialog("Enter Notes");
		Vector<Participant> v = owner.getEngine().getSortedList();

		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			Participant p = v.get(idx);
			p.setNotes(inputValue);
			v.setElementAt(p, idx);
		}
		updateList(v, selArray);
	}

	public void clearNotes() {
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = v.get(idx);
			p.setNotes("");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray, "CLEARNOTES");
	}

	public void clearSpellEffects() {
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = (Participant) v.get(idx);
			p.resetStatus();
			v.setElementAt(p, idx);
		}
		updateList(v, selArray, "CLEARSPELLS");
	}

	public void moveListItemUp() {
		int selArray[] = getSelectedIndices();
		int origArray[] = getSelectedIndices();
		int newIdx = 0;
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			newIdx = idx - 1;
			if (idx == 0) {
				return;
			}
			owner.getEngine().changePosition(idx, newIdx);
			selArray[i] = newIdx;
		}
		Vector<Participant> v = owner.getEngine().getSortedList();
		updateList(v, origArray, selArray, "MOVEUP");
		//updateList(v, selArray);
		owner.playSound(owner.getSound("listmove"));
	}

	public Participant getCurrentParticipant() {
		return (Participant) getSelectedValue();
	}

	public void goToCurrent(){
		setSelectedIndex(listIndex);
	}
	
	public void moveListItemDown() {
		int selArray[] = getSelectedIndices();
		int origArray[] = getSelectedIndices();
		int newIdx = 0;
		for (int i = selArray.length - 1; i >= 0; i--) {
			int idx = selArray[i];
			newIdx = idx + 1;
			if (idx + 1 == getModel().getSize()) {
				return;
			}
			owner.getEngine().changePosition(idx, newIdx);
			
			selArray[i] = newIdx;
		}
		Vector<Participant> v = owner.getEngine().getSortedList();
		updateList(v, origArray, selArray, "MOVEDOWN");
		owner.playSound(owner.getSound("listmove"));
	}

	public void holdListItemInit() {
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];
			owner.getEngine().holdInit(idx);
		}
		Vector<Participant> v = owner.getEngine().getSortedList();
		updateList(v, selArray);
		owner.sendSound("hold");
	}

	public void markListItemDead() {
		StatDead stat = new StatDead();
		setStatus(stat);
		owner.sendSound("dead");

		kills++;
		boolean monsters = false;
		Vector<Participant> v = owner.getEngine().getSortedList();
		for (int i = 0; i < v.size(); i++) {
			Participant p = (Participant) v.get(i);
			if (!p.getPType().equalsIgnoreCase("PLAYER")) {

				monsters = true;
				if (!p.isDead()) {

					return;
				}
			}
		}
		if (monsters) {
			owner.sendSound("endbattle");
			owner.endBattle();
		}
	}

	public void markListItemAlive() {
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = (Participant) v.get(idx);
			p.removeStatusItem("Dead");
			p.setDead("FALSE");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray, "MARKALIVE");
		owner.sendSound("alive");
	}
	
	

	public void markListItemStunned() {
		stuns++;
		StatStunned stat = new StatStunned();
		setStatus(stat);
		owner.sendSound("stun");
	}

	public void markListItemUnStunned() {
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = (Participant) v.get(idx);
			p.decrementStatusItem("Stunned");
			v.setElementAt(p, idx);
		}
		updateList(v, selArray, "UNSTUN");
	}

	public void removeListItem() {

		int selArray[] = getSelectedIndices();
		int oldArray[] = getSelectedIndices();
		Vector<Participant> delVector = new Vector<Participant>();
		Vector<Participant> mVector = new Vector<Participant>();
		Vector<Participant> v = owner.getEngine().getSortedList();
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

		owner.getEngine().removeParticipants(delVector);
		owner.removeFromHitSheet(mVector);
		owner.removeFromAltList();
		v = owner.getEngine().getSortedList();
		updateList(v, oldArray, selArray, "DELETE");
	}

	public Vector<Participant> getSelectedParticipants() {
		Vector<Participant> selected = new Vector<Participant>();
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = (Participant) v.get(idx);
			selected.add(p);
		}
		return selected;
	}

	public Vector<DCharacter> getSelectedCharacters() {
		Vector<DCharacter> selected = new Vector<DCharacter>();
		Vector<Participant> v = owner.getEngine().getSortedList();
		int selArray[] = getSelectedIndices();
		for (int i = 0; i < selArray.length; i++) {
			int idx = selArray[i];

			Participant p = (Participant) v.get(idx);
			if (!p.getPType().equalsIgnoreCase("Monster")) {
				selected.add((DCharacter) p);
			}
		}
		return selected;
	}

	public void selectParty(String s){
		int selArray[] = new int[100];
		int idx = 0;
		int count = 0;
		for (Participant p:owner.getEngine().getSortedList()){
			if (p.getParty().equals(s)){
				selArray[idx] = count;
				idx++;
			}
			count++;
		}
		setSelectedIndices(selArray);
	}
	
	private void updateList(Vector<Participant> v, int selArray[]) {
		if (selArray.length > 0) {
			owner.getEngine().setSortedList(v);
			setListData(v);
			setSelectedIndices(selArray);
			owner.getClientTalker().sendData(v);
			owner.getClientTalker().updatePosition(selArray[0]);
		}
	}

	private void updateList(Vector<Participant> v, int selArray[],
			StatusItem status) {
		if (selArray.length > 0) {
			owner.getEngine().setSortedList(v);
			setListData(v);
			setSelectedIndices(selArray);
			owner.getClientTalker().sendStatusUpdate(selArray, status);

		}
	}

	private void updateList(Vector<Participant> v, int origArray[], int selArray[],
			String command) {
		if (selArray.length > 0) {
			owner.getEngine().setSortedList(v);
			setListData(v);
			setSelectedIndices(selArray);
			owner.getClientTalker().sendCommand(origArray, command);
		}
	}
	
	private void updateList(Vector<Participant> v, int selArray[],
			String command) {
		if (selArray.length > 0) {
			owner.getEngine().setSortedList(v);
			setListData(v);
			setSelectedIndices(selArray);
			owner.getClientTalker().sendCommand(selArray, command);
		}
	}

	public void printList() {

		String s = getText();

		/*
		 * Get the representation of the current printer and the current print
		 * job.
		 */
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		/*
		 * Build a book containing pairs of page painters (Printables) and
		 * PageFormats. This example has a single page containing text.
		 */
		Book book = new Book();
		book.append(new InitPrinter(s), new PageFormat());
		/*
		 * Set the object to be printed (the Book) into the PrinterJob. Doing
		 * this before bringing up the print dialog allows the print dialog to
		 * correctly display the page range to be printed and to dissallow any
		 * print settings not appropriate for the pages to be printed.
		 */
		printerJob.setPageable(book);
		/*
		 * Show the print dialog to the user. This is an optional step and need
		 * not be done if the application wants to perform 'quiet' printing. If
		 * the user cancels the print dialog then false is returned. If true is
		 * returned we go ahead and print.
		 */
		boolean doPrint = printerJob.printDialog();
		if (doPrint) {
			try {
				printerJob.print();
			} catch (PrinterException exception) {
				System.err.println("Printing error: " + exception);
			}
		}

	}

	class PopupListener extends MouseAdapter {

		// on a double click, pop up the status dialog for the
		// list item. On a single click, just update the
		// client list to show the newly selected item.
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				owner.showStatusDialog();
			} else if (getSelectedIndex() >= 0) {
				owner.updateClientListPosition(getSelectedIndex());
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
				owner.getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
