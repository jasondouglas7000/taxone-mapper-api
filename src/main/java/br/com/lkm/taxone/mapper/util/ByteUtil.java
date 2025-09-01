package br.com.lkm.taxone.mapper.util;

public class ByteUtil {

	public static boolean search(byte[] content, byte[] term) {
		for (int x = 0; x < content.length-term.length+1; x++) {
			if (content[x] == term[0]) {
				for (int y = 1; y < term.length; y++) {
					if (content[x+y] == term[y]) {
						if (y == term.length-1) {
							return true;
						}
					}else {
						break;
					}
				}
			}
		}
		return false;
	}

}
