package initcheck;

import java.io.Serializable;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class InitLogger implements Serializable {
	static final long serialVersionUID = 1;

	/** the category for log4j logging */
	private transient Logger logger;

	/** the default logging level */
	private String defaultLogLevel = "debug";

	private String className;

	private static boolean configured = false;
	
	/**
	 * The basic log4j constructor. It returns a logger with the default log
	 * level of debug.
	 * 
	 * @param o
	 *            the object that is requesting a logger. This is needed to set
	 *            up the class name in the log4j output, if you are using log4j
	 *            as your logger.
	 */
	public InitLogger(Object o) {
		className = o.getClass().getName(); /* save a copy for later */
		logger = Logger.getLogger(className);
		if (!configured){
			BasicConfigurator.configure();
			configured = true;
		}
	}

	public InitLogger(Object o, String cfg) {
		if (o == null) {
			return;
		}
		className = o.getClass().getName(); /* save a copy for later */
		logger = Logger.getLogger(className);

		// PropertyConfigurator.configure(configFile);
	}

	/**
	 * Set the configuration file.
	 * 
	 * @param configFile
	 *            the file to configure log4j.
	 */
	public void setConfigFile(String configFile) {
		PropertyConfigurator.configure(configFile);
	}

	/**
	 * Set the default logging level. Valid levels are "debug", "info", "warn",
	 * "error", and "fatal".
	 * 
	 * @param level
	 *            the log level to set.
	 */
	public void setDefaultLogLevel(String level) {
		defaultLogLevel = level;
	}

	/**
	 * Write a message to the logger at the specified log level. Valid levels
	 * are "debug", "info", "warn", "error", and "fatal".
	 * 
	 * @param logLevel
	 *            the logging level for this log message
	 * @param s
	 *            the message to log
	 */
	public void log(String logLevel, String s) {
		if (logger == null)
			logger = Logger.getLogger(className);
		if (logLevel.equals("debug")) {
			logger.debug(s);
		} else if (logLevel.equals("info")) {
			logger.info(s);
		} else if (logLevel.equals("warn")) {
			logger.warn(s);
		} else if (logLevel.equals("error")) {
			logger.error(s);
		} else if (logLevel.equals("fatal")) {
			logger.fatal(s);
		}
	}

	/**
	 * Write a message to the logger at the default level.
	 * 
	 * @param s
	 *            the message to log.
	 */
	public void log(String s) {
		log(defaultLogLevel, s);
	}

}
