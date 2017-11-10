package initcheck.character;

import initcheck.DCharacter;
import initcheck.utils.BigFraction;

import java.io.Serializable;

public class JumpBlock implements Serializable {

	private static final long serialVersionUID = 1L;

	String runFormula;

	String standFormula;

	String runHighFormula;

	String standHighFormula;

	String backFormula;

	String runHighMax;

	String standMax;

	String runMax;

	String standHighMax;

	String backMax;

	public JumpBlock() {

	}

	public JumpBlock(DCharacter c) {
		
		double height = 0;
		if (c.getHeight() != null && !c.getHeight().equals("null")) {
			
			height = convertToInches(c.getHeight());
		}
		
		runMax = convertToString(height * 6);
		standMax = convertToString(height * 2);
		runHighMax = convertToString(height * 1.5);
		standHighMax = convertToString(height);
		backMax = convertToString(height);
		
		
		double jumpRanks = Double.parseDouble(c.getScore("JUMP")
				.getDisplayValue());
		

		double fullMove = Double.parseDouble(c.getFullMovement().getDisplayValue());

		double runMod = fullMove / 120;
		double runPlus = jumpRanks - 5;

		double standMod = fullMove / 240;
		double standPlus = jumpRanks - 7;

		double runHighMod = fullMove / 480;
		double runHighPlus = jumpRanks - 8;

		double standHighMod = fullMove / 960;
		double standHighPlus = jumpRanks - 8;

		double backMod = fullMove / 960;
		double backPlus = jumpRanks - 9;

		Double fmd = new Double(fullMove);
		int printFull = fmd.intValue();
		
		BigFraction formula = new BigFraction(printFull+"/120");
		
		runFormula = "d20 X " + formula +" ";
		if (12 * runPlus * runMod > 0) {
			runFormula += "+";
		}
		runFormula += convertToString(12 * runPlus * runMod);

		formula = new BigFraction(printFull+"/240");
		standFormula = "d20 X " + formula + " ";
		if (12 * standPlus * standMod > 0) {
			standFormula += "+";
		}
		standFormula += convertToString(12 * standPlus * standMod);

		formula = new BigFraction(printFull+"/480");
		runHighFormula = "d20 X " + formula + " ";
		if (12 * runHighPlus * runHighMod > 0) {
			runHighFormula += "+";
		}
		runHighFormula += convertToString(12 * runHighPlus * runHighMod);

		formula = new BigFraction(printFull+"/960");
		standHighFormula = "d20 X " + formula + " ";
		if (12 * standHighPlus * standHighMod > 0) {
			standHighFormula += "+";
		}
		standHighFormula += convertToString(12 * standHighPlus * standHighMod);

		backFormula = "d20 X " + formula + " ";
		if (12 * backPlus * backMod > 0) {
			backFormula += "+";
		}
		backFormula += convertToString(12 * backPlus * backMod);

	
	}

	private String convertToString(double d) {
		String height = "";
		int i = (new Double(d)).intValue();

		height += i / 12 + "' ";
		height += Math.abs(i % 12) + "\"";
		return height;
	}

	public static double convertToInches(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}

		String feet = s.substring(0, s.indexOf("'"));
		String inches = "0";
		if (s.indexOf("\"") > 0) {
			inches = s.substring(s.indexOf("'") + 1, s.indexOf("\""));
		}
		double f = Double.parseDouble(feet.trim());
		double i = Double.parseDouble(inches.trim());

		return (f * 12) + i;
	}

	/**
	 * Get the BackMax value.
	 * 
	 * @return the BackMax value.
	 */
	public String getBackMax() {
		return backMax;
	}

	/**
	 * Set the BackMax value.
	 * 
	 * @param newBackMax
	 *            The new BackMax value.
	 */
	public void setBackMax(String newBackMax) {
		this.backMax = newBackMax;
	}

	/**
	 * Get the StandHighMax value.
	 * 
	 * @return the StandHighMax value.
	 */
	public String getStandHighMax() {
		return standHighMax;
	}

	/**
	 * Set the StandHighMax value.
	 * 
	 * @param newStandHighMax
	 *            The new StandHighMax value.
	 */
	public void setStandHighMax(String newStandHighMax) {
		this.standHighMax = newStandHighMax;
	}

	/**
	 * Get the RunMax value.
	 * 
	 * @return the RunMax value.
	 */
	public String getRunMax() {
		return runMax;
	}

	/**
	 * Set the RunMax value.
	 * 
	 * @param newRunMax
	 *            The new RunMax value.
	 */
	public void setRunMax(String newRunMax) {
		this.runMax = newRunMax;
	}

	/**
	 * Get the StandMax value.
	 * 
	 * @return the StandMax value.
	 */
	public String getStandMax() {
		return standMax;
	}

	/**
	 * Set the StandMax value.
	 * 
	 * @param newStandMax
	 *            The new StandMax value.
	 */
	public void setStandMax(String newStandMax) {
		this.standMax = newStandMax;
	}

	/**
	 * Get the RunHighMax value.
	 * 
	 * @return the RunHighMax value.
	 */
	public String getRunHighMax() {
		return runHighMax;
	}

	/**
	 * Set the RunHighMax value.
	 * 
	 * @param newRunHighMax
	 *            The new RunHighMax value.
	 */
	public void setRunHighMax(String newRunHighMax) {
		this.runHighMax = newRunHighMax;
	}

	/**
	 * Get the BackFormula value.
	 * 
	 * @return the BackFormula value.
	 */
	public String getBackFormula() {
		return backFormula;
	}

	/**
	 * Set the BackFormula value.
	 * 
	 * @param newBackFormula
	 *            The new BackFormula value.
	 */
	public void setBackFormula(String newBackFormula) {
		this.backFormula = newBackFormula;
	}

	/**
	 * Get the StandHighFormula value.
	 * 
	 * @return the StandHighFormula value.
	 */
	public String getStandHighFormula() {
		return standHighFormula;
	}

	/**
	 * Set the StandHighFormula value.
	 * 
	 * @param newStandHighFormula
	 *            The new StandHighFormula value.
	 */
	public void setStandHighFormula(String newStandHighFormula) {
		this.standHighFormula = newStandHighFormula;
	}

	/**
	 * Get the RunHighFormula value.
	 * 
	 * @return the RunHighFormula value.
	 */
	public String getRunHighFormula() {
		return runHighFormula;
	}

	/**
	 * Set the RunHighFormula value.
	 * 
	 * @param newRunHighFormula
	 *            The new RunHighFormula value.
	 */
	public void setRunHighFormula(String newRunHighFormula) {
		this.runHighFormula = newRunHighFormula;
	}

	/**
	 * Get the StandFormula value.
	 * 
	 * @return the StandFormula value.
	 */
	public String getStandFormula() {
		return standFormula;
	}

	/**
	 * Set the StandFormula value.
	 * 
	 * @param newStandFormula
	 *            The new StandFormula value.
	 */
	public void setStandFormula(String newStandFormula) {
		this.standFormula = newStandFormula;
	}

	/**
	 * Get the RunFormula value.
	 * 
	 * @return the RunFormula value.
	 */
	public String getRunFormula() {
		return runFormula;
	}

	/**
	 * Set the RunFormula value.
	 * 
	 * @param newRunFormula
	 *            The new RunFormula value.
	 */
	public void setRunFormula(String newRunFormula) {
		this.runFormula = newRunFormula;
	}

}
