package initcheck.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class cZipFactory {

	private int iCompressLevel = Deflater.BEST_COMPRESSION;
	private Deflater cDeflate;
	private Inflater cInflate;

	public cZipFactory(int iCompLevel) {
		iCompressLevel = iCompLevel;
	}

	public cZipObject Compress(Serializable inObj) {
		cZipObject cZip = new cZipObject();

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(inObj);

			byte[] DataArray = byteOut.toByteArray();
			int iOrigSize = DataArray.length;
			cZip.setData(CompressBytes(DataArray), iOrigSize);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cZip;
	}

	public byte[] CompressBytes(byte[] array) {
		byte[] testBytes = new byte[array.length];

		cDeflate = new Deflater(iCompressLevel);
		cDeflate.setInput(array);
		cDeflate.finish();
		cDeflate.deflate(testBytes);

		int iRetLen = cDeflate.getTotalOut();
		byte[] retArray = new byte[iRetLen];
		System.arraycopy(testBytes, 0, retArray, 0, iRetLen);
		return retArray;
	}

	public Object Decompress(cZipObject cZip)
	     {
	         byte[] unzipped = DecompressBytes(cZip.getData(), cZip.getOriginalSize());
	         return ConvertByteToObject(unzipped);
	     }

	public byte[] DecompressBytes(byte[] array, int iLen) {
		byte[] retBytes = new byte[iLen];

		try {
			Inflater decompresser = new Inflater();
			decompresser.setInput(array);
			decompresser.inflate(retBytes);
			decompresser.end();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return retBytes;
	}

	public Object ConvertByteToObject(byte[] data) {
		Object objCache = null;

		try {
			ByteArrayInputStream fIn = new java.io.ByteArrayInputStream(data);
			ObjectInputStream objInput = new ObjectInputStream(fIn);
			objCache = objInput.readObject();
			fIn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return objCache;
	}
}
