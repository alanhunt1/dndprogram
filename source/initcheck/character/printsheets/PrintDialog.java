package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.InitLogger;
import initcheck.PanelButton;
import initcheck.character.PlayerStatDialog;
import initcheck.graphics.TiledPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PrintDialog extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private InitLogger logger = new InitLogger(this);
	
	PanelButton printButton = new PanelButton("Print Current Page", 100);

	PanelButton printAllButton = new PanelButton("Print All Pages", 100);

	PanelButton refreshButton = new PanelButton("Refresh");

	CharacterPrinter printPanel = new CharacterPrinter();

	CharacterPrinter2 printPanel2 = new CharacterPrinter2();


	JTextArea test = new JTextArea(50, 50);

	PanelButton page2Button = new PanelButton("Page 2");

	PanelButton page1Button = new PanelButton("Page 1");

	JScrollPane printScroll = new JScrollPane(printPanel);

	int page = 1;

	PlayerStatDialog owner;

	JComboBox scale = new JComboBox();

	//Vector spellSheets = new Vector();

	public PrintDialog(PlayerStatDialog owner, DCharacter c) {
		this.owner = owner;

		scale.addItem("1.0");
		scale.addItem("1.5");
		scale.addItem("2.0");

		printPanel.setCharacter(c);
		printPanel2.setCharacter(c);
		// printPanel3.setCharacter(c);

		// setMainWindow(printScroll);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(refreshButton);
		buttonPanel.add(scale);
		buttonPanel.add(printButton);
		buttonPanel.add(printAllButton);
		buttonPanel.add(page1Button);
		buttonPanel.add(page2Button);
		// buttonPanel.add(page3Button);

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
				goToPage1();
			}
		});

		page2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToPage2();
			}
		});

		// page3Button.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// goToPage3();
		// }
		// });

		scale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setScale();
			}
		});

		TiledPanel p = new TiledPanel();
		p.setLayout(new BorderLayout());
		p.add(printScroll, BorderLayout.CENTER);
		p.add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(p);

		pack();
		setVisible(true);
		printPanel.paintImage();

	}

	public void setScale() {
		printPanel.setScale(Double
				.parseDouble((String) scale.getSelectedItem()));
		printPanel2.setScale(Double.parseDouble((String) scale
				.getSelectedItem()));
		// printPanel3.setScale(Double.parseDouble((String)scale.getSelectedItem()));
		if (page == 1) {
			goToPage1();
		} else if (page == 2) {
			goToPage2();
		} else {
			// goToPage3();
		}
	}

	public void refreshCharacter() {
		DCharacter dc = owner.getChar();
		if (dc == null) {
			logger.log("error", "NULL CHAR IN REFRESH");
		}
		printPanel.updateCharacter(dc);
		printPanel2.updateCharacter(dc);
		// printPanel3.updateCharacter(dc);

		if (page == 1) {
			printPanel.paintImage();
			printPanel.repaint();
		} else if (page == 2) {
			printPanel2.paintImage();
			printPanel2.repaint();
		} else if (page == 3) {
			// printPanel3.paintImage();
			// printPanel3.repaint();
		}
	}

	private void goToPage1() {
		page = 1;
		printScroll.setViewportView(printPanel);
		printScroll.revalidate();
		printScroll.repaint();
	}

	private void goToPage2() {
		page = 2;
		printScroll.setViewportView(printPanel2);
		printScroll.revalidate();
		printScroll.repaint();
	}

	// private void goToPage3(){
	// page = 3;
	// printScroll.setViewportView(printPanel3);
	// printScroll.revalidate();
	// printScroll.repaint();
	// }

	private void printSheet() {
		if (page == 1) {
			double scale = printPanel.getScale();
			if (scale != 1.0) {
				printPanel.setScale(1.0);
			}
			printPanel.printSheet();
			if (scale != 1.0) {
				printPanel.setScale(scale);
			}
		} else if (page == 2) {
			double scale = printPanel2.getScale();
			if (scale != 1.0) {
				printPanel2.setScale(1.0);
			}
			printPanel2.printSheet();
			if (scale != 1.0) {
				printPanel2.setScale(scale);
			}
		} else if (page == 3) {
			// double scale = printPanel3.getScale();
			// if (scale != 1.0){
			// printPanel3.setScale(1.0);
			// }
			// printPanel3.printSheet();
			// if (scale != 1.0){
			// printPanel3.setScale(scale);
			// }
		}
	}

	public void printAllSheets() {
		double scale1 = printPanel.getScale();
		double scale2 = printPanel2.getScale();
		// double scale3 = printPanel3.getScale();

		if (scale1 != 1.0) {
			printPanel.setScale(1.0);
		}
		if (scale2 != 1.0) {
			printPanel2.setScale(1.0);
		}
		// if (scale3 != 1.0){
		// printPanel3.setScale(1.0);
		// }

		printAllSheets2();

		if (scale1 != 1.0) {
			printPanel.setScale(scale1);
		}
		if (scale2 != 1.0) {
			printPanel2.setScale(scale2);
		}
		// if (scale3 != 1.0){
		// printPanel3.setScale(scale3);
		// }
	}

	public void printAllSheets2() {

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
		book.append(printPanel, pf);
		book.append(printPanel2, pf);
		// book.append(printPanel3, pf);

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
