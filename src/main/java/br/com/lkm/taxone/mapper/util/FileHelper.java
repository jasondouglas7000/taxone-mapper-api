package br.com.lkm.taxone.mapper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lkm.taxone.mapper.dto.DSColumnDTO;
import br.com.lkm.taxone.mapper.dto.DSTableDTO;
import br.com.lkm.taxone.mapper.dto.DataSourceDTO;
import br.com.lkm.taxone.mapper.enums.ColumnType;

public class FileHelper {
	
	private static Logger log = LoggerFactory.getLogger(FileHelper.class);
	
	static Predicate<String> WITHOUT_WILD_CARD = rn -> !rn.contains("*");

	static Predicate<String> WILD_CARD = rn -> rn.contains("*");

	public static List<DSColumnDTO> getFileMetadata(DataSourceDTO dsDTO) {
		List<DSColumnDTO> dscList = new ArrayList<>();
		
		try {
			String resourcesWithoutWildCard = withoutWildCard(dsDTO.getResourceNames());
			File[] files = new File(dsDTO.getUrl()).listFiles(new FileHelpFilter(resourcesWithoutWildCard));
			for (File f : files) {
				dscList.addAll(generateDTOs(f));
			}
			String[] resourcesWildCard = withWildCard(dsDTO.getResourceNames()).split(",");
			for (String resourceWildCard : resourcesWildCard) {
				files = new File(dsDTO.getUrl()).listFiles(new FileHelpFilter(resourceWildCard));
				if (files.length > 0) {
					generateDTOs(files[0]).stream().forEach(dsc -> {
						dsc.getDsTable().setName(resourceWildCard); 
						dscList.add(dsc);
					});
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dscList;
	}
	
	public static List<DSColumnDTO> generateDTOs(File f) throws Exception {
		List<DSColumnDTO> dscList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(f))){
			String line = br.readLine();
			String[] labels = line.split(";|,");
			for (String label : labels) {
				DSColumnDTO dscDTO = new DSColumnDTO();
				dscDTO.setName(label);
				dscDTO.setColumnType(ColumnType.VARCHAR);
				DSTableDTO dsTable = new DSTableDTO();
				dsTable.setName(f.getName());
				dscDTO.setDsTable(dsTable);
				dscList.add(dscDTO);
			}
			log.info("file:" + f.getName() + " dscList.size:" + dscList.size());
		}
		return dscList;
	}
	
	public static void main(String... args) {
		log.info("removeFilesWithWildCard:" + withoutWildCard("safx001.txt, safx_wc*.txt"));
	}

	public static String withoutWildCard(String resourceNames) {
		return filterResourceNames(resourceNames, WITHOUT_WILD_CARD);
	}
	
	public static String withWildCard(String resourceNames) {
		return filterResourceNames(resourceNames, WILD_CARD);
	}
	
	private static String filterResourceNames(String resourceNames, Predicate<String> function) {
		String s = Arrays.asList(resourceNames.split(",")).stream().filter(function).collect(Collectors.toList()).toString();
		log.info("s:" + s);
		String files = Arrays.asList(resourceNames.split(",")).stream().filter(function).collect(Collectors.toList()).toString().replaceAll("\\[|\\]|\\s", "");
		log.info("files:" + files);
		return files;
	}

//	public static void main(String... args) {
//		try {
//			DataSourceDTO dsDTO = new DataSourceDTO();
//			dsDTO.setUrl("C:\\jason\\work\\atividades\\mapeamento_envio_taxone\\fileMetadata");
//			dsDTO.setResourceNames("safx10.txt,safx12.txt");
//			List<DSColumnDTO> dscList = getFileMetadata(dsDTO);
//			dscList.stream().forEach(dsc -> {
//				log.info("dsc.dsTableName:" + dsc.getDsTable().getName() + "dsc.name:" + dsc.getName() + 
//						"dsc.columnType:" + dsc.getColumnType());
//			});
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
