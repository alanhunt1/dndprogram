package initcheck.character;

import initcheck.DCharacter;
import initcheck.DescriptionDialog;
import initcheck.InitColor;
import initcheck.InitImage;
import initcheck.MessageDialog;
import initcheck.PanelButton;
import initcheck.RoundJTextField;
import initcheck.TranslucentGridPanel;
import initcheck.TranslucentPanel;
import initcheck.TransparentButton;
import initcheck.character.chooser.AlignmentChooser;
import initcheck.character.chooser.GenderChooser;
import initcheck.character.chooser.PartyChooser;
import initcheck.character.chooser.RaceChooser;
import initcheck.character.chooser.SizeChooser;
import initcheck.database.Alignment;
import initcheck.database.AlignmentDAO;
import initcheck.database.Deity;
import initcheck.database.DeityDAO;
import initcheck.database.Party;
import initcheck.database.Race;
import initcheck.database.RaceDAO;
import initcheck.graphics.Skin;
import initcheck.graphics.Skinnable;
import initcheck.graphics.TiledGridPanel;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.ToolTipLabel;
import initcheck.images.ImageSelectionDialog;
import initcheck.images.ImageSelectionOwner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoPanel extends TiledGridPanel implements ImageSelectionOwner,
		FocusListener, StatusTab, Skinnable {

	private RoundJTextField name = new RoundJTextField(10);

	private RoundJTextField playerName = new RoundJTextField(10);

	private RaceChooser race = new RaceChooser();

	private AlignmentChooser alignment = new AlignmentChooser();

	private GenderChooser gender = new GenderChooser();

	private RoundJTextField deity = new RoundJTextField(10);

	private RoundJTextField xp = new RoundJTextField(10);

	private RoundJTextField height = new RoundJTextField(10);

	private RoundJTextField weight = new RoundJTextField(10);

	private RoundJTextField hair = new RoundJTextField(10);

	private RoundJTextField eyes = new RoundJTextField(10);

	private RoundJTextField age = new RoundJTextField(10);

	private RoundJTextField fullName = new RoundJTextField(20);

	private PartyChooser parties = new PartyChooser();

	private RoundJTextField marchOrder = new RoundJTextField(10);

	private RoundJTextField sleepShift = new RoundJTextField(10);

	private SizeChooser sizeOverride = new SizeChooser();

	private RoundJTextField dr = new RoundJTextField(10);

	private RoundJTextField sr = new RoundJTextField(10);

	private JLabel expLabel = new JLabel("");

	private JLabel levelLabel = new JLabel("");

	private RoundJTextField addExp = new RoundJTextField(10);

	private PanelButton addButton = new PanelButton("Add");

	private final PlayerStatDialog owner;

	private boolean updateRequired = false;

	private ToolTipLabel fullNameLabel = new ToolTipLabel("Full Name");

	private ToolTipLabel displayNameLabel = new ToolTipLabel("Display Name");

	private JLabel playerNameLabel = new JLabel("Player Name");

	private JLabel partyLabel = new JLabel("Party");

	private JLabel deityLabel = new JLabel("Deity");

	private ToolTipLabel heightLabel = new ToolTipLabel("Height");

	private ToolTipLabel weightLabel = new ToolTipLabel("Weight");

	private ToolTipLabel ageLabel = new ToolTipLabel("Age");

	private JLabel hairLabel = new JLabel("Hair Color");

	private JLabel eyesLabel = new JLabel("Eye Color");

	private JLabel sleepShiftLabel = new JLabel("Sleep Shift");

	private JLabel marchOrderLabel = new JLabel("March Order");

	private JLabel drLabel = new JLabel("Dam. Red.");

	private JLabel srLabel = new JLabel("Spell Resist");

	static Color myred = new Color(148, 24, 16);

	private String picture = "images/chars/party.jpg";

	
	
	private TranslucentGridPanel namePanel = new TranslucentGridPanel(false);

	private TranslucentGridPanel d2Panel = new TranslucentGridPanel(false);

	private TranslucentGridPanel expPanel = new TranslucentGridPanel(false);

	private TranslucentGridPanel demogPanel = new TranslucentGridPanel();

	private TranslucentGridPanel miscPanel = new TranslucentGridPanel();

	private TranslucentPanel iconPanel = new TranslucentPanel(new BorderLayout());

	
	private boolean error = false;
	
	private TransparentButton showRaceButton = new TransparentButton(InitImage.qmarkIcon);

	private TransparentButton showAlignmentButton = new TransparentButton(InitImage.qmarkIcon);

	private TransparentButton showDeityButton = new TransparentButton(InitImage.qmarkIcon);

	private final static int PICTURE_MODE = 1;
	
	private final static int ICON_MODE = 2;
	
	private int imageMode = 1;
	
	TransparentButton setPictureButton = new TransparentButton(new ImageIcon());
	TransparentButton setIconButton = new TransparentButton(new ImageIcon());
	
	public InfoPanel(final PlayerStatDialog owner, DCharacter dc) {
		super();
		
		ImageIcon display = new ImageIcon(owner.getChar().getPicture());
		setPictureButton.setIcon(display);
		
		int gheight = display.getIconHeight();
		int gwidth = display.getIconWidth();

		// pull out the image
		Image img = display.getImage();

		// and scale the image to fit inside a reasonable bound
		int max = gheight;

		if (gwidth > max) {
			max = gwidth;
		}

		double ratio = new Double(max).doubleValue() / 350.0;

		if (ratio <= 0) {
			//ratio = 300 / max;
			//display.setImage(img.getScaledInstance(new Double(gwidth * ratio)
			//		.intValue(), new Double(gheight * ratio).intValue(),
			//		Image.SCALE_FAST));
		} else {

			display.setImage(img.getScaledInstance(new Double(gwidth / ratio)
					.intValue(), new Double(gheight / ratio).intValue(),
					Image.SCALE_FAST));
		}
	
		this.owner = owner;

		int level = new Double(owner.getChar().getLevel()).intValue();
		int next = 0;
		for (int i = 1; i <= level; i++) {
			next += i;
		}
		next = next * 1000;
		expLabel.setText("" + next);
		levelLabel.setText("" + level);

		//setPadX(115);
		//setPadY(10);
		setWeightX(1);
		setWeightY(1);
		setInsets(10);
		
		// setBorder(BorderFactory.createEtchedBorder());
		int ypos = 0;
		namePanel.setPadY(0);
		namePanel.setOpaque(false);
		namePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
		fullNameLabel.setToolTipText("This will appear on the printed sheet");
		//namePanel.doLayout(fullNameLabel, 0);
		//namePanel.doLayout(fullName, 1);
		fullName.setText(owner.getChar().getFullName());
		fullName.addFocusListener(this);
		//namePanel.incYPos();

		JLabel versionLabel = new JLabel(owner.getChar().getRulesetName());
		namePanel.doLayout(new JLabel("Ruleset"),0);
		namePanel.doLayout(versionLabel, 1);
		namePanel.incYPos();
		
		displayNameLabel
				.setToolTipText("This will appear on the party listing");
		namePanel.doLayout(displayNameLabel, 0);
		namePanel.doLayout(name, 1);
		namePanel.incYPos();
		name.setText(owner.getChar().getName());
		name.addFocusListener(this);

		namePanel.doLayout(playerNameLabel, 0);
		namePanel.doLayout(playerName, 1);
		playerName.setText(owner.getChar().getPlayerName());
		playerName.addFocusListener(this);
		
		namePanel.incYPos();

		namePanel.doLayout(partyLabel, 0);
		namePanel.doLayout(parties, 1);
		Party p = new Party();
		p.setId(owner.getChar().getPartyId());
		parties.setSelectedParty(p);
		parties.addFocusListener(this);
		namePanel.incYPos();
		
		namePanel.doLayout(new JLabel("Race : "), 0);
		namePanel.doLayout(race, 1);
		namePanel.doLayout(showRaceButton, 2);
		showRaceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRace();
			}
		});
		race.setSelectedItem(owner.getChar().getRace());
		race.addFocusListener(this);
		//race.setBorder(roundedBorder);
		namePanel.incYPos();
		
		namePanel.doLayout(new JLabel("Gender : "), 0);
		namePanel.doLayout(gender, 1);
		gender.addFocusListener(this);
		gender.setSelectedItem(owner.getChar().getGender());
		namePanel.incYPos();

		
		doLayout(iconPanel, 0, ypos, 1, 3);

		d2Panel.setOpaque(false);

		

		d2Panel.setPadY(0);
		d2Panel.doLayout(new JLabel("Alignment : "), 0);
		d2Panel.doLayout(alignment, 1);
		d2Panel.doLayout(showAlignmentButton, 2);
		showAlignmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAlignment();
			}
		});
		alignment.setSelectedItem(owner.getChar().getAlignment());
		alignment.addFocusListener(this);
		d2Panel.incYPos();

		d2Panel.doLayout(deityLabel, 0);
		d2Panel.doLayout(deity, 1);
		d2Panel.doLayout(showDeityButton, 2);
		showDeityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDeity();
			}
		});
		deity.setText(owner.getChar().getDeity());
		deity.addFocusListener(this);

		doLayout(d2Panel, 1, ypos,1, 2);

		iconPanel.setOpaque(false);
		iconPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel iconWrapper = new JPanel();
		iconWrapper.setOpaque(false);
		iconPanel.add(iconWrapper, BorderLayout.CENTER);
		
		setPictureButton.setPreferredSize(null);
		setIconButton.setPreferredSize(null);
		
		iconWrapper.add(setPictureButton);
		setPictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayerImage();
			}
		});
		
		setIconButton.setIcon(new ImageIcon(owner.getChar().getIcon()));
		setIconButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setPlayerIcon();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setOpaque(false);
		buttonPanel.add(setIconButton, BorderLayout.EAST);
		JPanel subPanel = new JPanel();
		subPanel.add(fullName);
		
		buttonPanel.add(subPanel, BorderLayout.CENTER);
		iconPanel.add(buttonPanel, BorderLayout.NORTH);
		doLayout(namePanel, 2, ypos, 1, 3);
		
		expPanel.setPadY(0);
		expPanel.setOpaque(false);
	
		expPanel.doLayout(new JLabel("Player Level : "), 0);
		expPanel.doLayout(levelLabel, 1);
		expPanel.incYPos();
		expPanel.doLayout(new JLabel("Experience : "), 0);
		expPanel.doLayout(xp, 1);
		xp.setText("" + owner.getChar().getXp());
		xp.addFocusListener(this);
		expPanel.incYPos();
		expPanel.doLayout(addButton, 0);
		expPanel.doLayout(addExp, 1);
		expPanel.incYPos();
		expPanel.doLayout(new JLabel("Next Level : "), 0);
		expPanel.doLayout(expLabel, 1);

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addExpToTotal();
			}
		});

		

		ypos++;

		demogPanel.setOpaque(false);

		// Height
		heightLabel.setToolTipText("In inches and feet, e.g. 5'10\"");
		namePanel.doLayout(heightLabel, 0);
		namePanel.doLayout(height, 1);
		height.setText(owner.getChar().getHeight());
		height.addFocusListener(this);
		namePanel.incYPos();

		// Weight
		weightLabel.setToolTipText("Weight in pounds, e.g. 156");
		namePanel.doLayout(weightLabel, 0);
		namePanel.doLayout(weight, 1);
		weight.setText(owner.getChar().getWeight());
		weight.addFocusListener(this);
		namePanel.incYPos();

		// Age
		ageLabel.setToolTipText("Age in years, e.g. 24");
		namePanel.doLayout(ageLabel, 0);
		namePanel.doLayout(age, 1);
		age.setText(owner.getChar().getAge());
		age.addFocusListener(this);
		namePanel.incYPos();

		// Hair
		namePanel.doLayout(hairLabel, 0);
		namePanel.doLayout(hair, 1);
		hair.setText(owner.getChar().getHair());
		hair.addFocusListener(this);
		namePanel.incYPos();

		// Eyes
		namePanel.doLayout(eyesLabel, 0);
		namePanel.doLayout(eyes, 1);
		eyes.setText(owner.getChar().getEyes());
		eyes.addFocusListener(this);

		//doLayout(demogPanel, 0, ypos);

		miscPanel.setOpaque(false);
		d2Panel.incYPos();
		d2Panel.doLayout(sleepShiftLabel, 0);
		d2Panel.doLayout(sleepShift, 1);
		sleepShift.setText("" + owner.getChar().getSleepShift());
		sleepShift.addFocusListener(this);
		d2Panel.incYPos();

		d2Panel.doLayout(marchOrderLabel, 0);
		d2Panel.doLayout(marchOrder, 1);
		marchOrder.setText("" + owner.getChar().getMarchOrder());
		marchOrder.addFocusListener(this);
		d2Panel.incYPos();

		d2Panel.doLayout(drLabel, 0);
		d2Panel.doLayout(dr, 1);
		dr.setText("" + owner.getChar().getDamageReduction());
		dr.addFocusListener(this);
		d2Panel.incYPos();

		d2Panel.doLayout(srLabel, 0);
		d2Panel.doLayout(sr, 1);
		sr.setText("" + owner.getChar().getSpellResist());
		sr.addFocusListener(this);
		d2Panel.incYPos();

		d2Panel.doLayout(new JLabel("Size Override : "), 0);
		d2Panel.doLayout(sizeOverride, 1);
		sizeOverride.setSelectedItem(owner.getChar().getSizeOverride());
		sizeOverride.addFocusListener(this);

		//doLayout(miscPanel, 1, ypos);

		

		ypos++;

		doLayout(expPanel, 1, ypos, 1, 1);
		
		updateLevelup();

	}

	public void showRace(){
		Race r = new Race();
		RaceDAO rdb = new RaceDAO();
		r.setRace((String)race.getSelectedItem());
		Vector<Race> rv = rdb.selectRace(r);
		r = (Race)rv.get(0);
		DescriptionDialog.display("Race Description", r.getFullDescription());
	}
	
	public void showAlignment(){
		Alignment a = new Alignment();
		AlignmentDAO adb = new AlignmentDAO();
		a.setAlignment((String)alignment.getSelectedItem());
		Vector<Alignment> rv = adb.selectAlignment(a);
		a = (Alignment)rv.get(0);
		DescriptionDialog.display("Alignment Description", a.getFullDescription());
	}
	
	public void showDeity(){
		Deity a = new Deity();
		DeityDAO adb = new DeityDAO();
		a.setName(deity.getText());
		Vector<Deity> rv = adb.selectDeity(a);
		if (rv.size() > 0){
			a = (Deity)rv.get(0);
			DescriptionDialog.display("Deity Description", a.getFullDescription());
		}else{
			DescriptionDialog.display("Deity Description", "No information available.");
		}
	}
	
	public void setSkin(Skin s) {
		this.setPaintBackground(s.isTilePanels());
		this.setBgImage(s.getForegroundPanelIcon());
	}

	public void setImage(String s) {
		picture = s;
		if (imageMode == PICTURE_MODE){
			owner.getChar().setPicture(picture);
			ImageIcon display = new ImageIcon(owner.getChar().getPicture());
			//display = new ImageIcon(picture);
			int gheight = display.getIconHeight();
			int gwidth = display.getIconWidth();
	
			// pull out the image
			Image img = display.getImage();
	
			// and scale the image to fit inside a reasonable bound
			int max = gheight;
	
			if (gwidth > max) {
				max = gwidth;
			}
	
			double ratio = new Double(max).doubleValue() / 350.0;
	
			if (ratio <= 0) {
				//ratio = 300 / max;
				//display.setImage(img.getScaledInstance(new Double(gwidth * ratio)
				//		.intValue(), new Double(gheight * ratio).intValue(),
				//		Image.SCALE_FAST));
			} else {
	
				display.setImage(img.getScaledInstance(new Double(gwidth / ratio)
						.intValue(), new Double(gheight / ratio).intValue(),
						Image.SCALE_FAST));
			}
			
			setPictureButton.setIcon(display);
		}else{
			// We're setting the icon
			owner.getChar().setIcon(picture);
			ImageIcon display = new ImageIcon(owner.getChar().getIcon());
			setIconButton.setIcon(display);
			
		}
	}

	public void setPlayerImage() {
		imageMode = PICTURE_MODE;
		
		@SuppressWarnings("unused")
		ImageSelectionDialog isd = new ImageSelectionDialog(null, this,
				"images/chars");

	}
	
	public void setPlayerIcon() {
		imageMode = ICON_MODE;
		
		@SuppressWarnings("unused")
		ImageSelectionDialog isd = new ImageSelectionDialog(null, this,
				"images/chars");

	}

	public void checkLabels() {

		if (fullName.getText().equals("")) {
			fullNameLabel.setForeground(myred);
		} else {
			fullNameLabel.setForeground(Color.black);
		}

		if (name.getText().equals("")) {
			displayNameLabel.setForeground(myred);
		} else {
			displayNameLabel.setForeground(Color.black);
		}

		if (playerName.getText().equals("")) {
			playerNameLabel.setForeground(myred);
		} else {
			playerNameLabel.setForeground(Color.black);
		}

		if (deity.getText().equals("")) {
			deityLabel.setForeground(myred);
		} else {
			deityLabel.setForeground(Color.black);
		}

		if (height.getText().equals("")) {
			heightLabel.setForeground(myred);
		} else {
			heightLabel.setForeground(Color.black);
		}

		if (weight.getText().equals("")) {
			weightLabel.setForeground(myred);
		} else {
			weightLabel.setForeground(Color.black);
		}

		if (age.getText().equals("")) {
			ageLabel.setForeground(myred);
		} else {
			ageLabel.setForeground(Color.black);
		}

		if (hair.getText().equals("")) {
			hairLabel.setForeground(myred);
		} else {
			hairLabel.setForeground(Color.black);
		}

		if (eyes.getText().equals("")) {
			eyesLabel.setForeground(myred);
		} else {
			eyesLabel.setForeground(Color.black);
		}

		if (sleepShift.getText().equals("")) {
			sleepShiftLabel.setForeground(myred);
		} else {
			sleepShiftLabel.setForeground(Color.black);
		}

		if (marchOrder.getText().equals("")) {
			marchOrderLabel.setForeground(myred);
		} else {
			marchOrderLabel.setForeground(Color.black);
		}

		if (dr.getText().equals("")) {
			drLabel.setForeground(myred);
		} else {
			drLabel.setForeground(Color.black);
		}

		if (sr.getText().equals("")) {
			srLabel.setForeground(myred);
		} else {
			srLabel.setForeground(Color.black);
		}
	}

	public void updateLevelup() {
		try {

			int level = new Double(owner.getChar().getLevel()).intValue();
			levelLabel.setText("" + level);
			int next = 0;
			for (int i = 1; i <= level; i++) {
				next += i;
			}
			next = next * 1000;
			if (next <= Integer.parseInt(xp.getText())) {
				expLabel.setForeground(InitColor.red);
				expLabel.setText(next + " LEVEL UP!");
				setUpdateRequired(true);
			} else {
				expLabel.setForeground(Color.black);
				expLabel.setText("" + next);
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Numeric Error",
					"Error in player experiece field.");
		}
	}

	public void addExpToTotal() {
		try {
			int total = Integer.parseInt(xp.getText());
			int newxp = Integer.parseInt(addExp.getText());
			xp.setText("" + (total + newxp));
			addExp.setText("");
			owner.getChar().setXp(Integer.parseInt(xp.getText()));
			int level = new Double(owner.getChar().getLevel()).intValue();
			int next = 0;
			for (int i = 1; i <= level; i++) {
				next += i;
			}
			next = next * 1000;
			if (next <= Integer.parseInt(xp.getText())) {
				expLabel.setForeground(Color.red);
				expLabel.setText(next + " LEVEL UP!");
			} else {
				expLabel.setForeground(Color.black);
				expLabel.setText("" + next);
			}

		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Numeric Error",
					"You must enter an integer value in the experience box");
		}
	}

	// we need to override the default method to specify the preferred size,
	// or the scrollbars will not work correctly.
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	public void updateAbility() {

	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		checkLabels();
		if (e.getSource() == gender) {
			owner.getChar().setGender((String) gender.getSelectedItem());
		} else if (e.getSource() == name) {
			owner.getChar().setName(name.getText());
		} else if (e.getSource() == playerName) {
			owner.getChar().setPlayerName(playerName.getText());
		} else if (e.getSource() == height) {
			try {
				JumpBlock.convertToInches(height.getText());
				owner.getChar().setHeight(height.getText());
			} catch (Exception ex) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog(
						"Format Error",
						"Height must be entered in feet and inches, with no spaces between.  For example, for a character that is six feet two inches tall, enter 6'2\"");
				height.setText("");
			}
		} else if (e.getSource() == race) {
			owner.getChar().setRace((String) race.getSelectedItem());
		}

		else if (e.getSource() == alignment) {
			owner.getChar().setAlignment((String) alignment.getSelectedItem());
		} else if (e.getSource() == parties) {
			owner.getChar().setPartyId(((Party) parties.getSelectedItem()).getId());
		} else if (e.getSource() == weight) {
			owner.getChar().setWeight(weight.getText());
		} else if (e.getSource() == xp || e.getSource() == addExp) {
			try {
				owner.getChar().setXp(Integer.parseInt(xp.getText()));
				int level = new Double(owner.getChar().getLevel()).intValue();
				int next = 0;
				for (int i = 1; i <= level; i++) {
					next += i;
				}
				next = next * 1000;
				expLabel.setText("" + next);
			} catch (NumberFormatException nfe) {
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog("Numeric Error",
						"You must enter an integer value in the experience box");
			}
		} else if (e.getSource() == deity) {
			owner.getChar().setDeity(deity.getText());
		} else if (e.getSource() == age) {
			owner.getChar().setAge(age.getText());
		} else if (e.getSource() == hair) {
			owner.getChar().setHair(hair.getText());
		} else if (e.getSource() == eyes) {
			owner.getChar().setEyes(eyes.getText());
		} else if (e.getSource() == fullName) {
			owner.getChar().setFullName(fullName.getText());
		} else if (e.getSource() == sleepShift) {
			try {
				owner.getChar().setSleepShift(""+Integer.parseInt(sleepShift.getText()));
			}catch(NumberFormatException nfe){
				@SuppressWarnings("unused")
				MessageDialog md = new MessageDialog("Numeric Error",
						"You must enter an integer value in the sleep shift box");
			
			}
			
		} else if (e.getSource() == marchOrder) {
			owner.getChar().setMarchOrder(marchOrder.getText());
		} else if (e.getSource() == sr) {
			owner.getChar().setSpellResist(sr.getText());
		} else if (e.getSource() == dr) {
			owner.getChar().setDamageReduction(dr.getText());
		} else if (e.getSource() == sizeOverride) {
			owner.getChar().setSizeOverride(
					(String) sizeOverride.getSelectedItem());
		}
	}

	public String getSelectedGender() {
		return (String) gender.getSelectedItem();
	}

	public String getSelectedAlignment() {
		return (String) alignment.getSelectedItem();
	}

	public String getSelectedRace() {
		return (String) race.getSelectedItem();
	}

	public String getName() {
		if (name != null){
			return name.getText();
		}
		return "";
	}

	public String getPlayerName() {
		return playerName.getText();
	}

	public String getFullName() {
		return fullName.getText();
	}

	public String getDeity() {
		return deity.getText();
	}

	public String getCharHeight() {
		return height.getText();
	}

	public String getWeight() {
		return weight.getText();
	}

	public String getAge() {
		return age.getText();
	}

	public String getHair() {
		return hair.getText();
	}

	public String getEyes() {
		return eyes.getText();
	}

	public int getXp() {
		return Integer.parseInt(xp.getText());
	}
	
	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
	}

	public boolean isError(){
		return error;
	}
	
	/**
	 * Set the UpdateRequired value.
	 * 
	 * @param newUpdateRequired
	 *            The new UpdateRequired value.
	 */
	public void setUpdateRequired(boolean newUpdateRequired) {
		this.updateRequired = newUpdateRequired;
	}
}
