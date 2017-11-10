package initcheck.io;

import java.io.Serializable;
import java.util.Vector;

public class InitPacket implements Serializable {

	private static final long serialVersionUID = 1L;

	String command = "NOOP";
	String timeSent = ""+new java.util.Date().getTime();
	
	Vector<Object> arguments = new Vector<Object>(3);

	public InitPacket(String command) {
		this.command = command;
	}

	public InitPacket(String command, Vector<Object> args) {
		this.command = command;
		arguments = args;
	}

	public String toString() {
		return command;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Vector<Object> getArguments() {
		return arguments;
	}

	public void setArguments(Vector<Object> arguments) {
		this.arguments = arguments;
	}

	public void addArgument(Object o) {
		arguments.add(o);
	}

	public String getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(String timeSent) {
		this.timeSent = timeSent;
	}

}
