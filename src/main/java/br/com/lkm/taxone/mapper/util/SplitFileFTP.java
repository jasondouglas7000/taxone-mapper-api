package br.com.lkm.taxone.mapper.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitFileFTP {
	
	private static Logger log = LoggerFactory.getLogger(SplitFileFTP.class);
	
	public static void main(String[] args) {
		String ftpConnectionString = "ftp://user:password@localhost:21/data/";
		String f = "safx10.txt";
		InputStream is = null;
		try {
			URLConnection urlCon = new URL(ftpConnectionString + f).openConnection();
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			is = urlCon.getInputStream();
			String fileContent = new String(IOUtil.readAllBytes(is));
			String[] lines = fileContent.split("\r\n");
			for (String line : lines) {
				log.info("line:" + line);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (is != null) { try { is.close(); } catch (Exception e) {} }
		}

	}
}
