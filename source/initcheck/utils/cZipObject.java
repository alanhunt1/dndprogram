package initcheck.utils;

import java.io.Serializable;

public class cZipObject implements Serializable{
	private int iOriginalSize;
	private byte[] compressData;

	public cZipObject() {
		iOriginalSize = 0;
	}

	public synchronized void setData(byte[] inData, int iOrigSize) {
		compressData = inData;
		iOriginalSize = iOrigSize;
	}

	public synchronized byte[] getData() {
		return compressData;
	}

	public synchronized int getOriginalSize() {
		return iOriginalSize;
	}
}
