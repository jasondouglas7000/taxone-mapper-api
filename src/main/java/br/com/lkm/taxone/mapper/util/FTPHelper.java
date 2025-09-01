package br.com.lkm.taxone.mapper.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import br.com.lkm.taxone.mapper.dto.DSColumnDTO;
import br.com.lkm.taxone.mapper.dto.DSTableDTO;
import br.com.lkm.taxone.mapper.dto.DataSourceDTO;
import br.com.lkm.taxone.mapper.enums.ColumnType;

public class FTPHelper {

	public static List<DSColumnDTO> getFileMetadata(DataSourceDTO dsDTO) {
		List<DSColumnDTO> dscList = new ArrayList<>();
		InputStream is = null;
		try {
			String ftpConnectionString = "ftp://" + dsDTO.getUsername() + ":" + dsDTO.getPassword() + "@" + dsDTO.getUrl() + "/";
			List<String> fileNames = Arrays.asList(dsDTO.getResourceNames().split(","));
			for (String f : fileNames) {
				URLConnection urlCon = new URL(ftpConnectionString + f).openConnection();
				urlCon.setDoInput(true);
				urlCon.setDoOutput(true);
				is = urlCon.getInputStream();
				String line = readLine(is);
				String[] labels = line.split(";|,");
				for (String label : labels) {
					DSColumnDTO dscDTO = new DSColumnDTO();
					dscDTO.setName(label);
					dscDTO.setColumnType(ColumnType.VARCHAR);
					DSTableDTO dsTable = new DSTableDTO();
					dsTable.setName(f);
					dscDTO.setDsTable(dsTable);
					dscList.add(dscDTO);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) { try { is.close(); } catch (Exception e) {}}
		}
		return dscList;
	}

	private static String readLine(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int read = 0;
		while ((read = is.read()) != 13) {
			baos.write(read);
		}
		return new String(baos.toByteArray());
	}

	public static void main(String... args) {
		try {
			FakeFtpServer fakeFtpServer = new FakeFtpServer();
	        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/"));

	        FileSystem fileSystem = new UnixFakeFileSystem();
	        fileSystem.add(new DirectoryEntry("/"));
	        fileSystem.add(new FileEntry("/data/safx14.txt", new String(Files.readAllBytes(Paths.get("C:\\jason\\work\\atividades\\mapeamento_envio_taxone\\fileMetadata\\safx14.txt")))));
	        fileSystem.add(new FileEntry("/data/safx15.txt", new String(Files.readAllBytes(Paths.get("C:\\jason\\work\\atividades\\mapeamento_envio_taxone\\fileMetadata\\safx15.txt")))));
	        fakeFtpServer.setFileSystem(fileSystem);
	        fakeFtpServer.setServerControlPort(21);
	        fakeFtpServer.start();

//	        DataSourceDTO dsDTO = new DataSourceDTO();
//	        dsDTO.setUrl("localhost:21/data");
//	        dsDTO.setUsername("user");
//	        dsDTO.setPassword("password");
//	        dsDTO.setResourceNames("safx10.txt,safx12.txt");
//	        List<DSColumnDTO> dscList = getFileMetadata(dsDTO);
//	        dscList.stream().forEach(dsc ->{
//	        	log.info("dsc.dsTableName:" + dsc.getDsTableName() + "dsc.name:" + dsc.getName() + 
//						"dsc.columnType:" + dsc.getColumnType());
//	        });
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
