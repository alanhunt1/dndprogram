package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.PlayerManager;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DMSheetPrintDialog extends JFrame {

	
	private static final long serialVersionUID = 1L;

	PanelButton printButton = new PanelButton("Print Current Page", 100);

	PanelButton printAllButton = new PanelButton("Print All Pages", 100);

	PanelButton refreshButton = new PanelButton("Refresh");

	int maxChars = 22;

	Vector<DMSheetPrinter> dmSheets = new Vector<DMSheetPrinter>();

	JTextArea test = new JTextArea(50, 50);

	PanelButton page2Button = new PanelButton("Next");

	PanelButton page1Button = new PanelButton("Previous");

	JScrollPane printScroll;

	int page = 1;

	PlayerManager owner;

	DMSheetPrinter currentDMSheet;

	JLabel pageLabel;

	public DMSheetPrintDialog(PlayerManager owner, String color,
			Vector<DCharacter> chars) {

		Color c = Color.green;
		if (color != null) {
			if (color.equals("Green")) {
				c = Color.green;
			} else if (color.equals("Blue")) {
				c = Color.blue;
			} else if (color.equals("Red")) {
				c = Color.red;
			} else if (color.equals("Yellow")) {
				c = Color.yellow;
			} else if (color.equals("Orange")) {
				c = Color.orange;
			} else if (color.equals("Gray")) {
				c = Color.gray;
			}
		}

		this.owner = owner;
		Vector<DCharacter> tmpVector = new Vector<DCharacter>();
		for (int i = 0; i < chars.size(); i++) {
			tmpVector.add((DCharacter)chars.get(i));
			if (i > 0 && i % (maxChars - 1) == 0) {
				DMSheetPrinter hsp = new DMSheetPrinter(tmpVector);
				if (c != null) {
					hsp.setColor(c);
				}
				dmSheets.add(hsp);
				tmpVector = new Vector<DCharacter>();
			}
		}

		pageLabel = new JLabel("Page " + page + " of " + dmSheets.size());
		pageLabel.setForeground(Color.white);
		pageLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

		DMSheetPrinter hsp = new DMSheetPrinter(tmpVector);
		if (c != null) {
			hsp.setColor(c);
		}
		dmSheets.add(hsp);

		printScroll = new JScrollPane((DMSheetPrinter) dmSheets.get(0));
		currentDMSheet = (DMSheetPrinter) dmSheets.get(0);

		// setMainWindow(printScroll);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(pageLabel);
		buttonPanel.add(printButton);
		buttonPanel.add(printAllButton);
		buttonPanel.add(refreshButton);
		buttonPanel.add(page1Button);
		buttonPanel.add(page2Button);

		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printSheet();
			}
		});

		printAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printAllSheets();
			}
		});

		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCharacter();
			}
		});

		page1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});

		page2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});

		TiledPanel p = new TiledPanel();
		p.setLayout(new BorderLayout());
		p.add(printScroll, BorderLayout.CENTER);
		p.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(p);

		pack();
		setVisible(true);
		currentDMSheet.paintImage();

	}

	public void refreshCharacter() {
		// DCharacter dc = owner.getChar();
		// if (dc == null){
		
		// }
		// printPanel.updateCharacter(dc);
		// printPanel2.updateCharacter(dc);
		// if (page == 1){
		// printPanel.repaint();
		// }
		// if (page == 2){
		// printPanel2.repaint();
		// }
	}

	private void goPrevious() {
		if (page > 1) {
			page--;
			pageLabel.setText("Page " + page + " of " + dmSheets.size());
			printScroll
					.setViewportView((DMSheetPrinter) dmSheets.get(page - 1));
			printScroll.revalidate();
			printScroll.repaint();
		}
	}

	private void goNext() {
		if (page < dmSheets.size()) {
			page++;
			pageLabel.setText("Page " + page + " of " + dmSheets.size());
			printScroll
					.setViewportView((DMSheetPrinter) dmSheets.get(page - 1));
			printScroll.revalidate();
			printScroll.repaint();
		}
	}

	private void printSheet() {
		((DMSheetPrinter) dmSheets.get(page - 1)).printSheet();
	}

	public void printAllSheets() {

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
		PageFormat pf = new PageFormat();
		Paper paper = new Paper();
		paper.setImageableArea(20, 20, 568, 748);
		pf.setPaper(paper);
		for (int i = 0; i < dmSheets.size(); i++) {
			book.append((DMSheetPrinter) dmSheets.get(i), pf);
		}

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

}
