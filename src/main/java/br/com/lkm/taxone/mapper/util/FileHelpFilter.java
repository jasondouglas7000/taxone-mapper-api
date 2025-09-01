package br.com.lkm.taxone.mapper.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelpFilter implements FilenameFilter {
	
	private List<String> filePatterns = new ArrayList<String>();
	
	public FileHelpFilter(String filePatterns) {
		this.filePatterns = Arrays.asList(filePatterns.split(","));
	}

	@Override
	public boolean accept(File dir, String name) {
		for (String filePattern : filePatterns){
			if (filePattern.contains("*")) {
				String[] fileNameParts = filePattern.split("\\*");
				if (name.startsWith(fileNameParts[0]) && name.endsWith(fileNameParts[1])) {
					return true;
				}else {
					return false;
				}
			}else if (name.equals(filePattern)) {
				return true;
			}
		}
		return false;
	}

}
