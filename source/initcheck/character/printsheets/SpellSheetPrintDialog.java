package initcheck.character.printsheets;

import initcheck.DCharacter;
import initcheck.PanelButton;
import initcheck.character.PlayerStatDialog;
import initcheck.database.CharClass;
import initcheck.database.PlayerSpells;
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

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SpellSheetPrintDialog extends JFrame {

	
	private static final long serialVersionUID = 1L;

	PanelButton printButton = new PanelButton("Print Current Page", 100);

	PanelButton printAllButton = new PanelButton("Print All Pages", 100);

	PanelButton refreshButton = new PanelButton("Refresh");

	int maxSpells = 55;

	Vector<SpellSheetPrinter> dmSheets = new Vector<SpellSheetPrinter>();

	JTextArea test = new JTextArea(50, 50);

	PanelButton page2Button = new PanelButton("Next");

	PanelButton page1Button = new PanelButton("Previous");

	JScrollPane printScroll;

	int page = 1;

	PlayerStatDialog owner;

	SpellSheetPrinter currentSpellSheet;

	JLabel pageLabel;

	DCharacter dc;

	int sheetCount = 0;

	JComboBox scale = new JComboBox();

	public SpellSheetPrintDialog(PlayerStatDialog owner, DCharacter c) {

		this.owner = owner;
		this.dc = c;

		scale.addItem("1.0");
		scale.addItem("1.5");
		scale.addItem("2.0");

		Vector<CharClass> casterClasses = dc.getCasterClasses();
		Vector<PlayerSpells> tmpVector = new Vector<PlayerSpells>();

		for (int i = 0; i < casterClasses.size(); i++) {
			CharClass cs = (CharClass) casterClasses.get(i);
			String classAbbrev = "unk";
			int bonus = 0;

			if (cs.getCharClass().equals("Wizard")) {
				classAbbrev = "Sorceror/Wizard";
				bonus = dc.getBonus(dc.getStat("INTELLIGENCE"));
			} else if (cs.getCharClass().equals("Sorcerer")) {
				classAbbrev = "Sorceror/Wizard";
				bonus = dc.getBonus(dc.getStat("CHARISMA"));
			} else if (cs.getCharClass().equals("Bard")) {
				classAbbrev = "Bard";
				bonus = dc.getBonus(dc.getStat("CHARISMA"));
			} else if (cs.getCharClass().equals("Cleric")) {
				classAbbrev = "Cleric";
				bonus = dc.getBonus(dc.getStat("WISDOM"));
			} else if (cs.getCharClass().equals("Paladin")) {
				classAbbrev = "Paladin";
				bonus = dc.getBonus(dc.getStat("CHARISMA"));
			} else if (cs.getCharClass().equals("Druid")) {
				classAbbrev = "Druid";
				bonus = dc.getBonus(dc.getStat("WISDOM"));
			}
			PlayerSpells ps = new PlayerSpells();
			ps.setSpellName(classAbbrev);
			tmpVector.add(ps);
			for (int j = 0; j < 10; j++) {
				Vector<PlayerSpells> spellsKnown = dc.getSpellsKnown("" + j, classAbbrev);
				for (int k = 0; k < spellsKnown.size(); k++) {
					ps = (PlayerSpells) spellsKnown.get(k);
					
					ps.setBonus(bonus);
					tmpVector.add(ps);
					if (tmpVector.size() > 0
							&& tmpVector.size() % (maxSpells - 1) == 0) {
						sheetCount++;
						SpellSheetPrinter hsp = new SpellSheetPrinter(tmpVector);
						hsp.setSheetCount(sheetCount);
						hsp.setCharacter(c);
						dmSheets.add(hsp);
						tmpVector = new Vector<PlayerSpells>();
					}
				}
			}
		}

		// now update the sheets themselves to know what the page number is.

		pageLabel = new JLabel("Page " + page + " of " + dmSheets.size());
		pageLabel.setForeground(Color.white);
		pageLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

		SpellSheetPrinter hsp = new SpellSheetPrinter(tmpVector);
		sheetCount++;
		hsp.setSheetCount(sheetCount);
		hsp.setCharacter(c);
		dmSheets.add(hsp);

		for (int i = 0; i < dmSheets.size(); i++) {
			hsp = (SpellSheetPrinter) dmSheets.get(i);
			hsp.setNumSheets(dmSheets.size());
		}

		printScroll = new JScrollPane((SpellSheetPrinter) dmSheets.get(0));
		currentSpellSheet = (SpellSheetPrinter) dmSheets.get(0);

		// setMainWindow(printScroll);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(pageLabel);
		buttonPanel.add(scale);
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
		currentSpellSheet.paintImage();

	}

	public void setScale() {
		for (int i = 0; i < dmSheets.size(); i++) {
			dmSheets.get(i).setScale(
					Double.parseDouble((String) scale.getSelectedItem()));
		}
		printScroll.setViewportView(currentSpellSheet);
		printScroll.revalidate();
		printScroll.repaint();
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
			printScroll.setViewportView((SpellSheetPrinter) dmSheets
					.get(page - 1));
			printScroll.revalidate();
			printScroll.repaint();
		}
	}

	private void goNext() {
		if (page < dmSheets.size()) {
			page++;
			pageLabel.setText("Page " + page + " of " + dmSheets.size());
			printScroll.setViewportView((SpellSheetPrinter) dmSheets
					.get(page - 1));
			printScroll.revalidate();
			printScroll.repaint();
		}
	}

	private void printSheet() {
		((SpellSheetPrinter) dmSheets.get(page - 1)).printSheet();
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
		pf.setOrientation(PageFormat.LANDSCAPE);

		pf.setPaper(paper);
		for (int i = 0; i < dmSheets.size(); i++) {
			book.append((SpellSheetPrinter) dmSheets.get(i), pf);
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
