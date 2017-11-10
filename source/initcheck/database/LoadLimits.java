package initcheck.database;

import java.io.Serializable;

public class LoadLimits implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String strength = "";

	private String lightLoad = "";

	private String mediumLoad = "";

	private String heavyLoad = "";

	String lift = "";

	String liftGround = "";

	String pushDrag = "";

	public LoadLimits(String s) {
		strength = "0";
		lightLoad = "0";
		mediumLoad = "0";
		heavyLoad = "0";
		lift = "0";
		liftGround = "0";
		pushDrag = "0";
	}

	public void decreaseLimits(int factor) {
		lightLoad = "" + (Integer.parseInt(lightLoad) / factor);
		mediumLoad = "" + (Integer.parseInt(mediumLoad) / factor);
		heavyLoad = "" + (Integer.parseInt(heavyLoad) / factor);
		lift = "" + (Integer.parseInt(lift) / factor);
		liftGround = "" + (Integer.parseInt(liftGround) / factor);
		pushDrag = "" + (Integer.parseInt(pushDrag) / factor);
	}

	public void adjustLimits(int factor) {
		lightLoad = "" + (Integer.parseInt(lightLoad) * factor);
		mediumLoad = "" + (Integer.parseInt(mediumLoad) * factor);
		heavyLoad = "" + (Integer.parseInt(heavyLoad) * factor);
		lift = "" + (Integer.parseInt(lift) * factor);
		liftGround = "" + (Integer.parseInt(liftGround) * factor);
		pushDrag = "" + (Integer.parseInt(pushDrag) * factor);
	}

	public static int getSpeed(int i, String s) {
		if (!s.equals("Light")) {
			if (i == 40) {
				return 30;
			}
			if (i == 30) {
				return 20;
			}
			if (i == 20) {
				return 15;
			}
		}
		return i;
	}

	/**
	 * Get the PushDrag value.
	 * 
	 * @return the PushDrag value.
	 */
	public String getPushDrag() {
		return pushDrag;
	}

	/**
	 * Set the PushDrag value.
	 * 
	 * @param newPushDrag
	 *            The new PushDrag value.
	 */
	public void setPushDrag(String newPushDrag) {
		this.pushDrag = newPushDrag;
	}

	/**
	 * Get the LiftGround value.
	 * 
	 * @return the LiftGround value.
	 */
	public String getLiftGround() {
		return liftGround;
	}

	/**
	 * Set the LiftGround value.
	 * 
	 * @param newLiftGround
	 *            The new LiftGround value.
	 */
	public void setLiftGround(String newLiftGround) {
		this.liftGround = newLiftGround;
	}

	/**
	 * Get the Lift value.
	 * 
	 * @return the Lift value.
	 */
	public String getLift() {
		return lift;
	}

	/**
	 * Set the Lift value.
	 * 
	 * @param newLift
	 *            The new Lift value.
	 */
	public void setLift(String newLift) {
		this.lift = newLift;
	}

	public LoadLimits() {

	}

	public String format(String s) {
		if (s == null || s.equals("null")) {
			return "";
		}
		return s;
	}

	public Object getClone() {
		Object o = null;
		try {
			o = this.clone();
		} catch (Exception e) {
		}
		return o;
	}

	/**
	 * Get the value of strength
	 * 
	 * @return a <code>String</code> value
	 */
	public String getStrength() {
		return strength;
	}

	/**
	 * Set the value of strength
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setStrength(String s) {
		strength = s;
	}

	/**
	 * Get the value of lightLoad
	 * 
	 * @return a <code>String</code> value
	 */
	public String getLightLoad() {
		return lightLoad;
	}

	/**
	 * Set the value of lightLoad
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setLightLoad(String s) {
		lightLoad = s;
	}

	/**
	 * Get the value of mediumLoad
	 * 
	 * @return a <code>String</code> value
	 */
	public String getMediumLoad() {
		return mediumLoad;
	}

	/**
	 * Set the value of mediumLoad
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setMediumLoad(String s) {
		mediumLoad = s;
	}

	/**
	 * Get the value of heavyLoad
	 * 
	 * @return a <code>String</code> value
	 */
	public String getHeavyLoad() {
		return heavyLoad;
	}

	/**
	 * Set the value of heavyLoad
	 * 
	 * @param a
	 *            <code>String</code> value
	 */
	public void setHeavyLoad(String s) {
		heavyLoad = s;
	}

}
