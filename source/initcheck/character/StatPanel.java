package initcheck.character;

import initcheck.DCharacter;
import initcheck.MessageDialog;
import initcheck.RoundJTextField;
import initcheck.TranslucentGridPanel;
import initcheck.TranslucentPanel;
import initcheck.graphics.TiledPanel;
import initcheck.graphics.ToolTipLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class StatPanel extends TiledPanel implements FocusListener, StatusTab {

	private static final long serialVersionUID = 1L;

	private RoundJTextField strText = new RoundJTextField(3);

	private RoundJTextField dexText = new RoundJTextField(3);

	private RoundJTextField conText = new RoundJTextField(3);

	private RoundJTextField wisText = new RoundJTextField(3);

	private RoundJTextField intText = new RoundJTextField(3);

	private RoundJTextField chaText = new RoundJTextField(3);

	private RoundJTextField strRaceText = new RoundJTextField(3);

	private RoundJTextField dexRaceText = new RoundJTextField(3);

	private RoundJTextField conRaceText = new RoundJTextField(3);

	private RoundJTextField wisRaceText = new RoundJTextField(3);

	private RoundJTextField intRaceText = new RoundJTextField(3);

	private RoundJTextField chaRaceText = new RoundJTextField(3);

	private RoundJTextField strMiscText = new RoundJTextField(3);

	private RoundJTextField dexMiscText = new RoundJTextField(3);

	private RoundJTextField conMiscText = new RoundJTextField(3);

	private RoundJTextField wisMiscText = new RoundJTextField(3);

	private RoundJTextField intMiscText = new RoundJTextField(3);

	private RoundJTextField chaMiscText = new RoundJTextField(3);

	private RoundJTextField strBookText = new RoundJTextField(3);

	private RoundJTextField dexBookText = new RoundJTextField(3);

	private RoundJTextField conBookText = new RoundJTextField(3);

	private RoundJTextField wisBookText = new RoundJTextField(3);

	private RoundJTextField intBookText = new RoundJTextField(3);

	private RoundJTextField chaBookText = new RoundJTextField(3);

	private RoundJTextField strLevelText = new RoundJTextField(3);

	private RoundJTextField dexLevelText = new RoundJTextField(3);

	private RoundJTextField conLevelText = new RoundJTextField(3);

	private RoundJTextField wisLevelText = new RoundJTextField(3);

	private RoundJTextField intLevelText = new RoundJTextField(3);

	private RoundJTextField chaLevelText = new RoundJTextField(3);

	private RoundJTextField strMiscExpText = new RoundJTextField(15);

	private RoundJTextField dexMiscExpText = new RoundJTextField(15);

	private RoundJTextField conMiscExpText = new RoundJTextField(15);

	private RoundJTextField wisMiscExpText = new RoundJTextField(15);

	private RoundJTextField intMiscExpText = new RoundJTextField(15);

	private RoundJTextField chaMiscExpText = new RoundJTextField(15);

	private JLabel strLabel = new BigLabel("");

	private JLabel strBonus = new BigLabel("");

	private JLabel tempStrLabel = new JLabel("");

	private JLabel dexLabel = new BigLabel("");

	private JLabel dexBonus = new BigLabel("");

	private JLabel tempDexLabel = new JLabel("");

	private JLabel conLabel = new BigLabel("");

	private JLabel conBonus = new BigLabel("");

	private JLabel tempConLabel = new JLabel("");

	private JLabel wisLabel = new BigLabel("");

	private JLabel wisBonus = new BigLabel("");

	private JLabel tempWisLabel = new JLabel("");

	private JLabel intLabel = new BigLabel("");

	private JLabel intBonus = new BigLabel("");

	private JLabel tempIntLabel = new JLabel("");

	private JLabel chaLabel = new BigLabel("");

	private JLabel chaBonus = new BigLabel("");

	private JLabel tempChaLabel = new JLabel("");

	private JLabel levelPoints = new JLabel("");

	private final PlayerStatDialog owner;

	private StatBlock sb;

	boolean updateRequired = false;

	private boolean error = false;
	
	public boolean isError() {
		return error;
	}



	public void setError(boolean error) {
		this.error = error;
	}

	
	/**
	 * Get the UpdateRequired value.
	 * 
	 * @return the UpdateRequired value.
	 */
	public boolean isUpdateRequired() {
		return updateRequired;
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

	public StatPanel(final PlayerStatDialog owner, DCharacter dc) {
		super("images/rockLighter.jpg");
		this.owner = owner;

	
		
		sb = owner.getChar().getStats();

		setLayout(new BorderLayout(10, 0));
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		TranslucentPanel displayPanel = new TranslucentPanel();
		displayPanel.setOpaque(false);
		displayPanel.setLayout(new GridLayout(7, 3));

		displayPanel.add(new BigLabel("Str "));
		displayPanel.add(strLabel);
		displayPanel.add(strBonus);
		displayPanel.add(new BigLabel("Dex "));
		displayPanel.add(dexLabel);
		displayPanel.add(dexBonus);
		displayPanel.add(new BigLabel("Con "));
		displayPanel.add(conLabel);
		displayPanel.add(conBonus);
		displayPanel.add(new BigLabel("Int "));
		displayPanel.add(intLabel);
		displayPanel.add(intBonus);
		displayPanel.add(new BigLabel("Wis "));
		displayPanel.add(wisLabel);
		displayPanel.add(wisBonus);
		displayPanel.add(new BigLabel("Cha "));
		displayPanel.add(chaLabel);
		displayPanel.add(chaBonus);
		
		add(displayPanel, BorderLayout.WEST);

		TranslucentGridPanel statPanel = new TranslucentGridPanel(false);
		statPanel.setOpaque(false);

		statPanel.doLayout(levelPoints, 0, statPanel.ypos, 9, 1);
		statPanel.incYPos();

		JLabel statLabel = new JLabel("Stat");
		statPanel.doLayout(statLabel, 0, statPanel.ypos);
		ToolTipLabel baseLabel = new ToolTipLabel("Base");
		baseLabel.setToolTipText("What the good lord (or the dice) gave you");
		statPanel.doLayout(baseLabel, 1, statPanel.ypos);
		ToolTipLabel raceLabel = new ToolTipLabel("Race");
		raceLabel.setToolTipText("Racial Modifier");
		statPanel.doLayout(raceLabel, 2, statPanel.ypos);
		ToolTipLabel miscLabel = new ToolTipLabel("Misc");
		miscLabel.setToolTipText("Random Stuff - explain in the notes.");
		statPanel.doLayout(miscLabel, 3, statPanel.ypos);
		ToolTipLabel levelLabel = new ToolTipLabel("Level");
		levelLabel.setToolTipText("Points granted every 4 levels");
		statPanel.doLayout(levelLabel, 4, statPanel.ypos);
		ToolTipLabel bookLabel = new ToolTipLabel("Book");
		bookLabel.setToolTipText("Points from reading Manual/Tome wonderous items.");
		statPanel.doLayout(bookLabel, 5, statPanel.ypos);
		statPanel.doLayout(new JLabel("Misc Notes"), 6, statPanel.ypos);
		ToolTipLabel tempLabel = new ToolTipLabel("Temp");
		tempLabel.setToolTipText("Mods from Temp Mods tab");
		statPanel.doLayout(tempLabel, 7, statPanel.ypos);
		// statPanel.doLayout(new JLabel("Ttl"), 8, statPanel.ypos);
		// statPanel.doLayout(new JLabel("Bonus"), 9, statPanel.ypos);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("STR"), 0, statPanel.ypos);
		statPanel.doLayout(strText, 1, statPanel.ypos);
		statPanel.doLayout(strRaceText, 2, statPanel.ypos);
		statPanel.doLayout(strMiscText, 3, statPanel.ypos);
		statPanel.doLayout(strLevelText, 4, statPanel.ypos);
		statPanel.doLayout(strBookText, 5, statPanel.ypos);
		statPanel.doLayout(strMiscExpText, 6, statPanel.ypos);
		statPanel.doLayout(tempStrLabel, 7, statPanel.ypos);
		// statPanel.doLayout(strLabel, 8, statPanel.ypos);
		// statPanel.doLayout(strBonus, 9, statPanel.ypos);

		strText.addFocusListener(this);
		strRaceText.addFocusListener(this);
		strMiscText.addFocusListener(this);
		strMiscExpText.addFocusListener(this);
		strLevelText.addFocusListener(this);
		strBookText.addFocusListener(this);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("DEX"), 0, statPanel.ypos);
		statPanel.doLayout(dexText, 1, statPanel.ypos);
		statPanel.doLayout(dexRaceText, 2, statPanel.ypos);
		statPanel.doLayout(dexMiscText, 3, statPanel.ypos);
		statPanel.doLayout(dexLevelText, 4, statPanel.ypos);
		statPanel.doLayout(dexBookText, 5, statPanel.ypos);
		statPanel.doLayout(dexMiscExpText, 6, statPanel.ypos);
		statPanel.doLayout(tempDexLabel, 7, statPanel.ypos);
		// statPanel.doLayout(dexLabel,8, statPanel.ypos);
		// statPanel.doLayout(dexBonus,9, statPanel.ypos);

		dexText.addFocusListener(this);
		dexRaceText.addFocusListener(this);
		dexMiscText.addFocusListener(this);
		dexMiscExpText.addFocusListener(this);
		dexLevelText.addFocusListener(this);
		dexBookText.addFocusListener(this);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("CON"), 0, statPanel.ypos);
		statPanel.doLayout(conText, 1, statPanel.ypos);
		statPanel.doLayout(conRaceText, 2, statPanel.ypos);
		statPanel.doLayout(conMiscText, 3, statPanel.ypos);
		statPanel.doLayout(conLevelText, 4, statPanel.ypos);
		statPanel.doLayout(conBookText, 5, statPanel.ypos);
		statPanel.setWeightX(1.0);
		statPanel.doLayout(conMiscExpText, 6, statPanel.ypos);
		statPanel.setWeightX(0);
		statPanel.doLayout(tempConLabel, 7, statPanel.ypos);

		// statPanel.doLayout(conLabel,8, statPanel.ypos);
		// statPanel.doLayout(conBonus,9, statPanel.ypos);

		conText.addFocusListener(this);
		conRaceText.addFocusListener(this);
		conMiscText.addFocusListener(this);
		conMiscExpText.addFocusListener(this);
		conLevelText.addFocusListener(this);
		conBookText.addFocusListener(this);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("INT"), 0, statPanel.ypos);
		statPanel.doLayout(intText, 1, statPanel.ypos);
		statPanel.doLayout(intRaceText, 2, statPanel.ypos);
		statPanel.doLayout(intMiscText, 3, statPanel.ypos);
		statPanel.doLayout(intLevelText, 4, statPanel.ypos);
		statPanel.doLayout(intBookText, 5, statPanel.ypos);
		statPanel.doLayout(intMiscExpText, 6, statPanel.ypos);
		statPanel.doLayout(tempIntLabel, 7, statPanel.ypos);
		// statPanel.doLayout(intLabel,8,statPanel.ypos);
		// statPanel.doLayout(intBonus,9,statPanel.ypos);

		intText.addFocusListener(this);
		intRaceText.addFocusListener(this);
		intMiscText.addFocusListener(this);
		intMiscExpText.addFocusListener(this);
		intLevelText.addFocusListener(this);
		intBookText.addFocusListener(this);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("WIS"), 0, statPanel.ypos);
		statPanel.doLayout(wisText, 1, statPanel.ypos);
		statPanel.doLayout(wisRaceText, 2, statPanel.ypos);
		statPanel.doLayout(wisMiscText, 3, statPanel.ypos);
		statPanel.doLayout(wisLevelText, 4, statPanel.ypos);
		statPanel.doLayout(wisBookText, 5, statPanel.ypos);
		statPanel.doLayout(wisMiscExpText, 6, statPanel.ypos);
		statPanel.doLayout(tempWisLabel, 7, statPanel.ypos);
		// statPanel.doLayout(wisLabel,8,statPanel.ypos);
		// statPanel.doLayout(wisBonus,9,statPanel.ypos);

		wisText.addFocusListener(this);
		wisRaceText.addFocusListener(this);
		wisMiscText.addFocusListener(this);
		wisMiscExpText.addFocusListener(this);
		wisLevelText.addFocusListener(this);
		wisBookText.addFocusListener(this);
		statPanel.incYPos();

		statPanel.doLayout(new JLabel("CHA"), 0, statPanel.ypos);
		statPanel.doLayout(chaText, 1, statPanel.ypos);
		statPanel.doLayout(chaRaceText, 2, statPanel.ypos);
		statPanel.doLayout(chaMiscText, 3, statPanel.ypos);
		statPanel.doLayout(chaLevelText, 4, statPanel.ypos);
		statPanel.doLayout(chaBookText, 5, statPanel.ypos);
		statPanel.doLayout(chaMiscExpText, 6, statPanel.ypos);
		statPanel.doLayout(tempChaLabel, 7, statPanel.ypos);
		// statPanel.doLayout(chaLabel,8,statPanel.ypos);
		// statPanel.doLayout(chaBonus,9,statPanel.ypos);

		chaText.addFocusListener(this);
		chaRaceText.addFocusListener(this);
		chaMiscText.addFocusListener(this);
		chaMiscExpText.addFocusListener(this);
		chaLevelText.addFocusListener(this);
		chaBookText.addFocusListener(this);
		statPanel.incYPos();

		// set the values from the database
		chaText.setText("" + owner.getChar().getStats().getBaseCha());
		intText.setText("" + owner.getChar().getStats().getBaseInt());
		wisText.setText("" + owner.getChar().getStats().getBaseWis());
		strText.setText("" + owner.getChar().getStats().getBaseStr());
		dexText.setText("" + owner.getChar().getStats().getBaseDex());
		conText.setText("" + owner.getChar().getStats().getBaseCon());

		chaLevelText.setText("" + owner.getChar().getStats().getLevelCha());
		intLevelText.setText("" + owner.getChar().getStats().getLevelInt());
		wisLevelText.setText("" + owner.getChar().getStats().getLevelWis());
		strLevelText.setText("" + owner.getChar().getStats().getLevelStr());
		dexLevelText.setText("" + owner.getChar().getStats().getLevelDex());
		conLevelText.setText("" + owner.getChar().getStats().getLevelCon());

		chaRaceText.setText("" + owner.getChar().getStats().getRaceCha());
		intRaceText.setText("" + owner.getChar().getStats().getRaceInt());
		wisRaceText.setText("" + owner.getChar().getStats().getRaceWis());
		strRaceText.setText("" + owner.getChar().getStats().getRaceStr());
		dexRaceText.setText("" + owner.getChar().getStats().getRaceDex());
		conRaceText.setText("" + owner.getChar().getStats().getRaceCon());

		chaMiscText.setText("" + owner.getChar().getStats().getMiscCha());
		intMiscText.setText("" + owner.getChar().getStats().getMiscInt());
		wisMiscText.setText("" + owner.getChar().getStats().getMiscWis());
		strMiscText.setText("" + owner.getChar().getStats().getMiscStr());
		dexMiscText.setText("" + owner.getChar().getStats().getMiscDex());
		conMiscText.setText("" + owner.getChar().getStats().getMiscCon());

		chaMiscExpText
				.setText("" + owner.getChar().getStats().getMiscChaText());
		intMiscExpText
				.setText("" + owner.getChar().getStats().getMiscIntText());
		wisMiscExpText
				.setText("" + owner.getChar().getStats().getMiscWisText());
		strMiscExpText
				.setText("" + owner.getChar().getStats().getMiscStrText());
		dexMiscExpText
				.setText("" + owner.getChar().getStats().getMiscDexText());
		conMiscExpText
				.setText("" + owner.getChar().getStats().getMiscConText());

		chaBookText.setText("" + owner.getChar().getStats().getBookCha());
		intBookText.setText("" + owner.getChar().getStats().getBookInt());
		wisBookText.setText("" + owner.getChar().getStats().getBookWis());
		strBookText.setText("" + owner.getChar().getStats().getBookStr());
		dexBookText.setText("" + owner.getChar().getStats().getBookDex());
		conBookText.setText("" + owner.getChar().getStats().getBookCon());

		// update the totals columns
		updateTotals();

		add(statPanel, BorderLayout.CENTER);
	}

	public void updateTotals() {

		strLabel.setText("" + owner.getChar().getStat("STRENGTH"));
		tempStrLabel.setText("" + owner.getChar().getTempMods().getTempStr());
		int bonus = owner.getChar().getBonus(
				owner.getChar().getStat("STRENGTH"));
		if (bonus < 0) {
			strBonus.setForeground(Color.red);
		} else {
			strBonus.setForeground(Color.black);
		}
		strBonus.setText("" + bonus);

		dexLabel.setText("" + owner.getChar().getStat("DEXTERITY"));
		tempDexLabel.setText("" + owner.getChar().getTempMods().getTempDex());
		bonus = owner.getChar().getBonus(owner.getChar().getStat("DEXTERITY"));
		if (bonus < 0) {
			dexBonus.setForeground(Color.red);
		} else {
			dexBonus.setForeground(Color.black);
		}
		dexBonus.setText("" + bonus);

		conLabel.setText("" + owner.getChar().getStat("CONSTITUTION"));
		tempConLabel.setText("" + owner.getChar().getTempMods().getTempCon());
		bonus = owner.getChar().getBonus(
				owner.getChar().getStat("CONSTITUTION"));
		if (bonus < 0) {
			conBonus.setForeground(Color.red);
		} else {
			conBonus.setForeground(Color.black);
		}
		conBonus.setText("" + bonus);

		wisLabel.setText("" + owner.getChar().getStat("WISDOM"));
		tempWisLabel.setText("" + owner.getChar().getTempMods().getTempWis());
		bonus = owner.getChar().getBonus(owner.getChar().getStat("WISDOM"));
		if (bonus < 0) {
			wisBonus.setForeground(Color.red);
		} else {
			wisBonus.setForeground(Color.black);
		}
		wisBonus.setText("" + bonus);

		intLabel.setText("" + owner.getChar().getStat("INTELLIGENCE"));
		tempIntLabel.setText("" + owner.getChar().getTempMods().getTempInt());
		bonus = owner.getChar().getBonus(
				owner.getChar().getStat("INTELLIGENCE"));
		if (bonus < 0) {
			intBonus.setForeground(Color.red);
		} else {
			intBonus.setForeground(Color.black);
		}
		intBonus.setText("" + bonus);

		chaLabel.setText("" + owner.getChar().getStat("CHARISMA"));
		tempChaLabel.setText("" + owner.getChar().getTempMods().getTempCha());

		bonus = owner.getChar().getBonus(owner.getChar().getStat("CHARISMA"));
		if (bonus < 0) {
			chaBonus.setForeground(Color.red);
		} else {
			chaBonus.setForeground(Color.black);
		}
		chaBonus.setText("" + bonus);

		int levelPts = owner.getChar().getLevelPointsRemaining();
		levelPoints.setText("Stat Points Remaining : " + levelPts);
		
		// if they have level points that they haven't used, mark it as requiring update
		if (levelPts > 0) {
			setUpdateRequired(true);
		} else {
			setUpdateRequired(false);
		}
		
		// if they used more than they have, mark it as an error.
		if (levelPts < 0) {
			setError(true);
		} else {
			setError(false);
		}
		
		owner.checkUpdatesRequired();

		owner.getChar().getSaves().setAbilRef(
				owner.getChar().getBonus(owner.getChar().getStat("DEXTERITY")));
		owner.getChar().getSaves().setAbilFort(
				owner.getChar().getBonus(
						owner.getChar().getStat("CONSTITUTION")));
		owner.getChar().getSaves().setAbilWill(
				owner.getChar().getBonus(owner.getChar().getStat("WISDOM")));
	}

	public void focusGained(FocusEvent e) {

	}

	public void focusLost(FocusEvent e) {
		try {
			// str fields
			if (e.getSource() == strText) {
				owner.getChar().getStats().setBaseStr(
						Integer.parseInt(strText.getText()));
				updateTotals();
			} else if (e.getSource() == strRaceText) {
				owner.getChar().getStats().setRaceStr(
						Integer.parseInt(strRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == strMiscText) {
				owner.getChar().getStats().setMiscStr(
						Integer.parseInt(strMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == strMiscExpText) {
				owner.getChar().getStats().setMiscStrText(
						strMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == strLevelText) {
				owner.getChar().getStats().setLevelStr(
						Integer.parseInt(strLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == strBookText) {
				owner.getChar().getStats().setBookStr(
						Integer.parseInt(strBookText.getText()));
				updateTotals();
			}

			// dex fields
			else if (e.getSource() == dexText) {
				owner.getChar().getStats().setBaseDex(
						Integer.parseInt(dexText.getText()));
				updateTotals();
			} else if (e.getSource() == dexRaceText) {
				owner.getChar().getStats().setRaceDex(
						Integer.parseInt(dexRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == dexMiscText) {
				owner.getChar().getStats().setMiscDex(
						Integer.parseInt(dexMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == dexMiscExpText) {
				owner.getChar().getStats().setMiscDexText(
						dexMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == dexLevelText) {
				owner.getChar().getStats().setLevelDex(
						Integer.parseInt(dexLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == dexBookText) {
				owner.getChar().getStats().setBookDex(
						Integer.parseInt(dexBookText.getText()));
				updateTotals();
			}

			// con fields
			else if (e.getSource() == conText) {
				owner.getChar().getStats().setBaseCon(
						Integer.parseInt(conText.getText()));
				updateTotals();
			} else if (e.getSource() == conRaceText) {
				owner.getChar().getStats().setRaceCon(
						Integer.parseInt(conRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == conMiscText) {
				owner.getChar().getStats().setMiscCon(
						Integer.parseInt(conMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == conMiscExpText) {
				owner.getChar().getStats().setMiscConText(
						conMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == conLevelText) {
				owner.getChar().getStats().setLevelCon(
						Integer.parseInt(conLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == conBookText) {
				owner.getChar().getStats().setBookCon(
						Integer.parseInt(conBookText.getText()));
				updateTotals();
			}

			// wis fields
			else if (e.getSource() == wisText) {
				owner.getChar().getStats().setBaseWis(
						Integer.parseInt(wisText.getText()));
				updateTotals();
			} else if (e.getSource() == wisRaceText) {
				owner.getChar().getStats().setRaceWis(
						Integer.parseInt(wisRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == wisMiscText) {
				owner.getChar().getStats().setMiscWis(
						Integer.parseInt(wisMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == wisMiscExpText) {
				owner.getChar().getStats().setMiscWisText(
						chaMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == wisLevelText) {
				owner.getChar().getStats().setLevelWis(
						Integer.parseInt(wisLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == wisBookText) {
				owner.getChar().getStats().setBookWis(
						Integer.parseInt(wisBookText.getText()));
				updateTotals();
			}

			// int fields
			else if (e.getSource() == intText) {
				owner.getChar().getStats().setBaseInt(
						Integer.parseInt(intText.getText()));
				updateTotals();
			} else if (e.getSource() == intRaceText) {
				owner.getChar().getStats().setRaceInt(
						Integer.parseInt(intRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == intMiscText) {
				owner.getChar().getStats().setMiscInt(
						Integer.parseInt(intMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == intMiscExpText) {
				owner.getChar().getStats().setMiscIntText(
						intMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == intLevelText) {
				owner.getChar().getStats().setLevelInt(
						Integer.parseInt(intLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == intBookText) {
				owner.getChar().getStats().setBookInt(
						Integer.parseInt(intBookText.getText()));
				updateTotals();
			}

			// cha fields
			else if (e.getSource() == chaText) {
				owner.getChar().getStats().setBaseCha(
						Integer.parseInt(chaText.getText()));
				updateTotals();
			} else if (e.getSource() == chaRaceText) {
				owner.getChar().getStats().setRaceCha(
						Integer.parseInt(chaRaceText.getText()));
				updateTotals();
			} else if (e.getSource() == chaMiscText) {
				owner.getChar().getStats().setMiscCha(
						Integer.parseInt(chaMiscText.getText()));
				updateTotals();
			} else if (e.getSource() == chaMiscExpText) {
				owner.getChar().getStats().setMiscChaText(
						chaMiscExpText.getText());
				updateTotals();
			} else if (e.getSource() == chaLevelText) {
				owner.getChar().getStats().setLevelCha(
						Integer.parseInt(chaLevelText.getText()));
				updateTotals();
			} else if (e.getSource() == chaBookText) {
				owner.getChar().getStats().setBookCha(
						Integer.parseInt(chaBookText.getText()));
				updateTotals();
			}
		} catch (NumberFormatException nfe) {
			@SuppressWarnings("unused")
			MessageDialog md = new MessageDialog("Error",
					"You must enter numbers in the stat boxes, or leave them empty.");
		}
	}

	public StatBlock getStats() {
		return sb;
	}

	public int getStrength() {
		return Integer.parseInt(strText.getText());
	}

	public int getDexterity() {
		return Integer.parseInt(dexText.getText());
	}

	public int getConstitution() {
		return Integer.parseInt(conText.getText());
	}

	public int getWisdom() {
		return Integer.parseInt(wisText.getText());
	}

	public int getIntelligence() {
		return Integer.parseInt(intText.getText());
	}

	public int getCharisma() {
		return Integer.parseInt(chaText.getText());
	}
}
