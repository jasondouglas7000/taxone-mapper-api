package br.com.lkm.taxone.mapper.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static byte[] readAllBytes(InputStream is) throws IOException {
    	byte[] localData = new byte[8096];
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	int read = 0;
    	while ((read = is.read(localData)) != -1) {
    		baos.write(localData, 0, read);
    	}
    	return baos.toByteArray();
	}


}
