package initcheck.images;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import initcheck.InitLogger;
import initcheck.InitServer;
import initcheck.PanelButton;
import initcheck.character.GenericListCellRenderer;
import initcheck.graphics.TiledDialog;
import initcheck.graphics.TiledList;
import initcheck.graphics.TiledListString;

public class ImageSelectionDialog extends TiledDialog {

	private static final long serialVersionUID = 1L;

	private TiledList imageList;

	private PanelButton okButton;

	private PanelButton cancelButton;

	private JButton imageButton;

	private ImageIcon display;

	private String image;

	private JPanel contents;

	private JScrollPane listScroll;

	private ImageSelectionOwner owner = null;

	private InitLogger logger = new InitLogger(this);

	private String directory = "images";
	
	public ImageSelectionDialog(InitServer owner) {
		super(owner.getFrame(), "Pick An Image", true);
		doInit();
	}

	public ImageSelectionDialog(Frame f, ImageSelectionOwner owner,
			String directory) {
		super(f, "Pick An Image", true);
		this.directory = directory;
		this.owner = owner;
		
		doInit();
	}

	private void doInit() {
		// populate the list of images
		
		imageList = new TiledList();
		imageList.setListData(getImageFileList(directory));
		imageList.setSelectedIndex(0);
		imageList.setCellRenderer(new GenericListCellRenderer());
		image = directory+"/" + imageList.getSelectedValue();
		
		logger.log("initial image is " + image);

		JPanel buttonPanel = new JPanel();
		okButton = new PanelButton("Ok");
		cancelButton = new PanelButton("Cancel");
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setOpaque(false);
		// create the displayed image
		display = new ImageIcon(image);
		imageButton = new JButton(display);
		imageButton.setContentAreaFilled(false);
		imageButton.setBorderPainted(false);
		imageButton.setFocusPainted(false);

		contents = new JPanel(new BorderLayout());
		listScroll = new JScrollPane(imageList);
		contents.add(listScroll, BorderLayout.WEST);
		JScrollPane imageScroll = new JScrollPane(imageButton);
		contents.add(imageScroll, BorderLayout.CENTER);
		
		//contents.add(buttonPanel, BorderLayout.SOUTH);

		setMainWindow(contents);
		setButtonBar(buttonPanel);
		
		imageList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				updateImage();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setImage();

			}
		});

		pack();
		setVisible(true);
	}

	public void setDirectory(String s) {
		
		imageList.setListData(getImageFileList(s));
		imageList.setSelectedIndex(0);
	}

	private void setImage() {
		if (owner != null) {
			owner.setImage(image);
		}
		dispose();
	}

	private void updateImage() {
		image = directory+"/" + imageList.getSelectedValue().toString();
		logger.log("loading image " + image);
		display = new ImageIcon(image);
		imageButton.setIcon(display);

	}

	/**
	 * Read all of the jpg and gif files from the images directory to build the
	 * image pick list. Users can import new files into the directory using the
	 * image import dialog.
	 */
	private Vector<TiledListString> getImageFileList(String directory) {
		
		Vector<TiledListString> imageVector = new Vector<TiledListString>();
		File imageDir = new File(directory);
		String[] imageFiles = imageDir.list();
		for (int i = 0; i < imageFiles.length; i++) {
			if (imageFiles[i].indexOf(".jpg") > 0
					|| imageFiles[i].indexOf(".gif") > 0
					|| imageFiles[i].indexOf(".JPG") > 0
					|| imageFiles[i].indexOf(".jpeg") > 0
					|| imageFiles[i].indexOf(".JPEG") > 0
					|| imageFiles[i].indexOf(".GIF") > 0
			) {
				imageVector.add(new TiledListString(imageFiles[i]));
			}
		}
		return imageVector;
	}

}
