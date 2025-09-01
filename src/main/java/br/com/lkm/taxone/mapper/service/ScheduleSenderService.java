package br.com.lkm.taxone.mapper.service;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lkm.taxone.mapper.dto.DATAHolder;
import br.com.lkm.taxone.mapper.dto.DataDTO;
import br.com.lkm.taxone.mapper.entity.Criteria;
import br.com.lkm.taxone.mapper.entity.DSColumn;
import br.com.lkm.taxone.mapper.entity.DataSourceConfiguration;
import br.com.lkm.taxone.mapper.entity.IntegrationStatus;
import br.com.lkm.taxone.mapper.entity.SAFXColumn;
import br.com.lkm.taxone.mapper.entity.SAFXTable;
import br.com.lkm.taxone.mapper.entity.Schedule;
import br.com.lkm.taxone.mapper.entity.ScheduleLog;
import br.com.lkm.taxone.mapper.entity.TaxOneApi;
import br.com.lkm.taxone.mapper.enums.ColumnType;
import br.com.lkm.taxone.mapper.enums.DataSourceType;
import br.com.lkm.taxone.mapper.enums.ScheduleLogStatus;
import br.com.lkm.taxone.mapper.integration.OncoClinicasTaxtOneService;
import br.com.lkm.taxone.mapper.integration.OncoClinicasTaxtOneServiceBuilder;
import br.com.lkm.taxone.mapper.integration.dto.IncluirResponseDTO;
import br.com.lkm.taxone.mapper.integration.dto.LoteDTO;
import br.com.lkm.taxone.mapper.integration.dto.SAFXTableTaxOneDTO;
import br.com.lkm.taxone.mapper.repository.ScheduleRepository;
import br.com.lkm.taxone.mapper.repository.TaxOneApiRepository;
import br.com.lkm.taxone.mapper.util.DateUtil;
import br.com.lkm.taxone.mapper.util.FileHelpFilter;
import br.com.lkm.taxone.mapper.util.IOUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScheduleSenderService {
	
	private OncoClinicasTaxtOneServiceBuilder oncoIntegrationBuilder; 
	
	private ScheduleRepository scheduleRepository;

	private ScheduleLogService scheduleLogService;
	
	private EmailService emailService;

	private TaxOneApiRepository taxOneApiRepository;

	@Value("${lkm.taxonemapper.integration.codEmpresa}")
	private String codEmpresa;
	@Value("${lkm.taxonemapper.integration.codEstab}")
	private String codEstab;

	@Value("${lkm.taxonemapper.datasource.dateTimeFieldName}")
	private String dateTimeFieldName;
	
	public ScheduleSenderService(OncoClinicasTaxtOneServiceBuilder oncoIntegrationBuilder, ScheduleRepository scheduleRepository, ScheduleLogService scheduleLogService, 
			EmailService emailService, TaxOneApiRepository taxOneApiRepository) {
		this.oncoIntegrationBuilder = oncoIntegrationBuilder;
		this.scheduleRepository = scheduleRepository;
		this.scheduleLogService = scheduleLogService;
		this.emailService = emailService;
		this.taxOneApiRepository = taxOneApiRepository;
	}
	
	
	@Transactional
	public void process() {
		//To filter
		LocalDateTime nowT = LocalDateTime.now();
		LocalDateTime now = nowT.minusHours(1);
		int dayOfWeak = now.getDayOfWeek().getValue()-1;
		List<Schedule> sList = scheduleRepository.
				findByDaysContainingAndLastExecutionLessThanOrDaysAndLastExecutionLessThan(String.valueOf(dayOfWeak), now, "*", now);
		List<Schedule> sToExecute = sList.stream().filter(s -> {
			if (s.getHours().contains("*")) {
				return true;
			}else {
				List<Integer> hours = Arrays.asList(s.getHours().split(",")).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
				return hours.contains(now.getHour()+1); // hour of LocalDateTime is zero index based
			}
		}).collect(Collectors.toList());
		log.info("schedule to run:" + sToExecute);
		if (sToExecute.size() > 0) {
			try {
				OncoClinicasTaxtOneService oncoIntegrationService = oncoIntegrationBuilder.createService(null);
				log.info("Autenticando no api de integracao OncoClinicas");
				TaxOneApi taxOneApi = taxOneApiRepository.getReferenceById(1);
				String token = oncoIntegrationService.authentication(taxOneApi.getUsername(), taxOneApi.getPassword()).execute().body().string();
				
				for (Schedule s : sToExecute) {
					process(s, token);//scheduleRepository.getOne(30913)
				}
			}catch (Exception e) {
				log.error("Erro executando os agendamentos:", e);
			}
		}		
		
	}

	@Transactional
	public void process(Schedule s, String token) throws Exception {
		//Call the log service
		log.info("Iniciando o processamento do agendamento:" + s.getName());
		ScheduleLog sLog = new ScheduleLog();
		LocalDateTime currentExecution = LocalDateTime.now();
		try {
			sLog.setSchedule(s);
			sLog.setStatus(ScheduleLogStatus.PROCESSING);
			scheduleLogService.save(sLog);
			OncoClinicasTaxtOneService oncoIntegrationService = oncoIntegrationBuilder.createService(token);
			LoteDTO lote = oncoIntegrationService.gerarNumLote().execute().body();
//			String sLote = oncoIntegrationService.gerarNumLote().execute().body().string();
//			log.info("sLote:" + sLote);
//			LoteDTO lote = new LoteDTO("afWZUVGnCdCi/55XSopHmZIRIG1RXnP323QlnSuz8sHl1QKp5kwNULUh");
			
			log.info("lote gerado com o codigo:" + lote.getNum_lote());
			
			for (SAFXTable st : s.getSafxTables()) {
				log.info("Iniciando o carregamento dos dados da SAFX:" + st.getName());
				DATAHolder dataHolder = retriveData(st, s.getLastExecution(), currentExecution);
				log.info("linhas retornadas:" + dataHolder.getList().size());
				log.info("dtos:" + dataHolder.getList());
				List<Criteria> criteriaSAFX = s.getCriterias().stream().filter(c -> c.getSafxColumn().getSafxTable().equals(st)).collect(Collectors.toList());
				List<DataDTO> list = filter(dataHolder, criteriaSAFX);
				if (list.size() > 0) {
					log.info("linhas a serem processadas apos filtro:" + list.size());
					SAFXTableTaxOneDTO safIntegracaoDTO = new SAFXTableTaxOneDTO();   
					safIntegracaoDTO.setSafx(st.getName());
					List<Map<String, Object>> registros = new ArrayList<>();
					Map<String, Field> fm = dataHolder.getFieldMappings();
					for (DataDTO safDTO : list) {
						Map<String, Object> registro = new HashMap<>();
						for (String fieldName : fm.keySet()){
							Object value = fm.get(fieldName).get(safDTO);
							//try with group
							ColumnType ct = st.getSafxColumns().stream().filter(sc -> sc.getName().equals(fieldName)).collect(Collectors.toList()).get(0).getColumnType();
							if (ct.equals(ColumnType.DATETIME)) {
								if (value instanceof Date) {
									value = DateUtil.formatyyyyMMdd((Date) value);
								}
								registro.put(fieldName.toLowerCase(), value);
							}else if (ct.equals(ColumnType.NUMERIC)) {
								registro.put(fieldName.toLowerCase(), Long.parseLong(value.toString()));
							}else {
								registro.put(fieldName.toLowerCase(), value.toString());
							}
						}
						registros.add(registro);
					}
					safIntegracaoDTO.setRegistros(registros);
					log.info("safIntegracaoDTO:" + safIntegracaoDTO);
					String data = DateUtil.formatyyyyMMdd(new Date());
					IncluirResponseDTO incluirRespnse = oncoIntegrationService.incluirRegistros(lote.getNum_lote(), codEmpresa, codEstab, 
							data, data, safIntegracaoDTO).execute().body();
					log.info("resposta da incluisao:" + incluirRespnse);
					sLog.setNumLote(lote.getNum_lote());
					sLog.setStatus(ScheduleLogStatus.SENT);
					sLog.setIntegrationStatus(IntegrationStatus.ENVIADO);
					sLog.setExecutionDate(currentExecution);
					scheduleLogService.save(sLog);
				}else {
					sLog.setStatus(ScheduleLogStatus.PROCESSED);
					scheduleLogService.save(sLog);
				}
			}
			s.setLastExecution(currentExecution);
			scheduleRepository.save(s);
		}catch (Exception e) {
			sLog.setStatus(ScheduleLogStatus.PROCESSING_ERROR);
			sLog.setErrorMessage(e.getMessage());
			scheduleLogService.save(sLog);

			s.setLastExecution(currentExecution);
			scheduleRepository.save(s);
			
			emailService.sendErrorEmail(s, "Erro no processamento do agendamento");
			log.error("Erro processando o agendamento:" + s.getName(), e);
		}
		log.info("Finalizando o processamento do agendamento:" + s.getName());
	}

	private List<DataDTO> filter(DATAHolder dataHolder, List<Criteria> criterias) {
		List<DataDTO> list = dataHolder.getList();
//		log.info("dataHolder.getFieldMappings():" + dataHolder.getFieldMappings());
		for (Criteria c : criterias) {
			String cName = c.getSafxColumn().getName();
			ColumnType cType = c.getSafxColumn().getColumnType();
			Field f = dataHolder.getFieldMappings().get(cName);
			String operator = c.getOperator();
			String value = c.getValue();
			Object additionalValue = c.getAdditionalValue();
			log.info("cName:" + cName + " - operator:" + operator + " - value:" + value + " - additionalValue:" + additionalValue  
					+ " type:" + cType + " - f.name:" + f.getName());
			
			list = list.stream().filter(d ->  CriteriaOperations.OPERATIONS.get(operator).test(f, d, c)).collect(Collectors.toList());
//			list = list.stream().filter(d ->  {
//				log.info("d:" + d);
//				return CriteriaOperations.OPERATIONS.get(operator).test(f, d, c);} ).toList();
		}
		return list;
	}
	
	private DATAHolder retriveData(SAFXTable st, LocalDateTime lastExecution, LocalDateTime currentExecution) throws Exception {
		DataSourceConfiguration dsc = st.getDsTable().getDataSourceConfiguration();
		if (dsc.getDataSourceType().equals(DataSourceType.Database)) {
			return retriveDataDB(st, lastExecution, currentExecution);
		}else if (dsc.getDataSourceType().equals(DataSourceType.TXT) || dsc.getDataSourceType().equals(DataSourceType.FTP)) {
			return retriveDataFileFTP(st, lastExecution, currentExecution);
		}
		return null;
	}

	private DATAHolder retriveDataDB(SAFXTable st, LocalDateTime lastExecution, LocalDateTime currentExecution) throws Exception {
		Map<String, Field> fieldMappings = new HashMap<>(); 
		DATAHolder dataHolder = new DATAHolder();
		StringBuilder sql = new StringBuilder();
		Field[] fields = DataDTO.class.getDeclaredFields();
		int counter=0;
		StringBuilder selectFields = new StringBuilder();
		for (SAFXColumn sc : st.getSafxColumns()) {
			if (sc.getDsColumn() != null) {
				fieldMappings.put(sc.getName(), fields[counter++]);
				selectFields.append(sc.getDsColumn().getName()).append(",");
			}
		}
		dataHolder.setFieldMappings(fieldMappings);
		if (selectFields.length() > 0) {
			selectFields.deleteCharAt(selectFields.length()-1);
		}

		List<Field> fList = new ArrayList<>(fieldMappings.values());
		Collections.sort(fList, (a, b) -> Integer.parseInt(a.getName().substring(5))-Integer.parseInt(b.getName().substring(5)));

		sql.append("select " + selectFields + " from " + st.getDsTable().getName());
		//create tne where clause to filter just the not processed register
		sql.append(" where " + dateTimeFieldName + " between '" + DateUtil.formatyyyyMMddhhmmss(lastExecution) 
			+ "' and '" + DateUtil.formatyyyyMMddhhmmss(currentExecution) + "'");
		log.info(sql.toString());
		DataSourceConfiguration dsc = st.getDsTable().getDataSourceConfiguration();
		List<DataDTO> dList = new ArrayList<>();
		try (Connection c = DriverManager.getConnection(dsc.getUrl(), dsc.getUsername(), dsc.getPassword());
			Statement stt = c.createStatement();
			ResultSet rs = stt.executeQuery(sql.toString())){
			while (rs.next()) {
				DataDTO dDTO = new DataDTO();
				int fc=1;
				for (Field f : fList) {
					f.set(dDTO, rs.getObject(fc++));
				}
				dList.add(dDTO);
			}
		}
		dataHolder.setList(dList);
		return dataHolder;
	}
	
	
	private DATAHolder retriveDataFileFTP(SAFXTable st, LocalDateTime lastExecution, LocalDateTime currentExecution) throws Exception {
		Map<String, Field> fieldMappings = new HashMap<>();
		Map<Field, String> fieldTOdsField = new HashMap<>();
		DATAHolder dataHolder = new DATAHolder();
		Field[] fields = DataDTO.class.getDeclaredFields();
		int counter=0;
		for (SAFXColumn sc : st.getSafxColumns()) {
			if (sc.getDsColumn() != null) {
				fieldMappings.put(sc.getName(), fields[counter]);
				fieldTOdsField.put(fields[counter++], sc.getDsColumn().getName()); //podemos dar uma repensada nisso.
			}
		}
		dataHolder.setFieldMappings(fieldMappings);

		List<Field> fList = new ArrayList<>(fieldMappings.values());
		Collections.sort(fList, (a, b) -> Integer.parseInt(a.getName().substring(5))-Integer.parseInt(b.getName().substring(5)));

		DataSourceConfiguration dsc = st.getDsTable().getDataSourceConfiguration();
		Map<String, Integer> dsColumnPosition = new HashMap<>();// st.getDsTable().getDsColumns().stream().map(dsc -> ) //podemos dar uma repensada nisso.
		Collections.sort(st.getDsTable().getDsColumns(), (a,b) -> a.getId().compareTo(b.getId())); // ensure the sort to work
		st.getDsTable().getDsColumns().forEach(dscolumn -> {
//			log.info("dscolumn:" + dscolumn.getName());
		});
		int x=0;
		for (DSColumn dscolumn : st.getDsTable().getDsColumns()) { 
			dsColumnPosition.put(dscolumn.getName(), x++);
		};
		
		List<DataDTO> dList = new ArrayList<>();
		
		if (dsc.getDataSourceType().equals(DataSourceType.TXT)) {
			List<File> files = new ArrayList<>();
			if (st.getDsTable().getName().contains("*")) {
				File[] filesArray = new File(dsc.getUrl()).listFiles(new FileHelpFilter(st.getDsTable().getName()));
				files.addAll(Arrays.asList(filesArray));
			}else {
				files.add(new File(dsc.getUrl(), st.getDsTable().getName()));
			}
			
			for (File f : files) {
				List<String> lines = Files.readAllLines(f.toPath());
				lines.remove(0);// remove header;
				for (String line : lines) {
					DataDTO dDTO = new DataDTO();
					String[] fileFields = line.split(";|,");
					String dataHoraInclusao = (String) fileFields[dsColumnPosition.get(dateTimeFieldName)];
					LocalDateTime dataHoraInclusaoLDT = DateUtil.parseDateyyyyMMddhhmmss(dataHoraInclusao);
					if (dataHoraInclusaoLDT.isAfter(lastExecution) && dataHoraInclusaoLDT.isBefore(currentExecution)) {
						for (Field df : fList) {
							int idx = dsColumnPosition.get(fieldTOdsField.get(df)); //podemos dar uma repensada nisso.
							df.set(dDTO, fileFields[idx]);
						}
						dList.add(dDTO);
					}
				}
			}
		}else {
			InputStream is = null;
			try {
				String ftpConnectionString = "ftp://" + dsc.getUsername() + ":" + dsc.getPassword() + "@" + dsc.getUrl() + "/";
				String f = st.getDsTable().getName();
				URLConnection urlCon = new URL(ftpConnectionString + f).openConnection();
				urlCon.setDoInput(true);
				urlCon.setDoOutput(true);
				is = urlCon.getInputStream();
				String fileContent = new String(IOUtil.readAllBytes(is));
				List<String> lines = new ArrayList<>(Arrays.asList(fileContent.split("\r\n")));
				lines.remove(0);// remove header;
				for (String line : lines) {
					DataDTO dDTO = new DataDTO();
					String[] fileFields = line.split(";|,");
					String dataHoraInclusao = (String) fileFields[dsColumnPosition.get(dateTimeFieldName)];
					LocalDateTime dataHoraInclusaoLDT = DateUtil.parseDateyyyyMMddhhmmss(dataHoraInclusao);
					//verify if is a new registry 
					if (dataHoraInclusaoLDT.isAfter(lastExecution) && dataHoraInclusaoLDT.isBefore(currentExecution)) {
						for (Field df : fList) {
							int idx = dsColumnPosition.get(fieldTOdsField.get(df)); //podemos dar uma repensada nisso.
							df.set(dDTO, fileFields[idx]);
						}
						dList.add(dDTO);
					}
				}
			}finally {
				if (is != null) { try { is.close(); } catch (Exception e) {} }
			}
		}
		
		dataHolder.setList(dList);
		return dataHolder;
	}
}

