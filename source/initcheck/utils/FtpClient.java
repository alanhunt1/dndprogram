package initcheck.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class FtpClient {
	private String hostname;

	FTPClient client = new FTPClient();

	String replyString = "";

	public FtpClient(String hostname) {
		this.hostname = hostname;
	}

	// if you need to do something special...
	public FTPClient getClient() {
		return client;
	}

	public void login(String username, String password) throws Exception {
		try {
			client.connect(hostname);
			replyString = "";
			check(client.getReplyCode());
			replyString += client.getReplyString();
			client.login(username, password);
			check(client.getReplyCode());
			replyString += client.getReplyString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean check(int replyCode) {
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			return false;
		} else {
			return true;
		}
	}

	public void binary() {
		try {
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean cd(String dir) throws IOException {
		replyString = "";
		boolean result = client.changeWorkingDirectory(dir);
		replyString = client.getReplyString();
		return result;
	}

	public void deleteFile(String pathName) {
		try {
			client.deleteFile(pathName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean mkdir(String dir) {
		boolean result = false;
		try {
			replyString = "";
			result = client.makeDirectory(dir);
			replyString = client.getReplyString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Vector<String> listFiles() {
		Vector<String> fileVector = new Vector<String>();
		try {
			FTPFile[] files = client.listFiles();
			for (int i = 0; i < files.length; i++) {
				fileVector.add(files[i].getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileVector;
	}

	public String getCommand() {
		return "";
	}

	public String getResponseString() {
		return replyString;
	}

	public void closeServer() {
		try {
			client.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean putBinary(String nameOnServer, InputStream stream) throws Exception {
		boolean result = false;
		try {
			result = client.storeFile(nameOnServer, stream);
			replyString = client.getReplyString();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public boolean getBinary(String nameOnServer, OutputStream stream) {
		boolean result = false;
		try {
			result = client.retrieveFile(nameOnServer, stream);
			replyString = client.getReplyString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void pwd() {
		try {
			client.printWorkingDirectory();
			replyString = client.getReplyString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterLocalActiveMode() {
		try {
			client.enterLocalActiveMode();
			replyString = client.getReplyString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enterRemoteActiveMode() {
		try {
			client.enterRemoteActiveMode(java.net.InetAddress
					.getByName(hostname), 21);
			replyString = client.getReplyString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
