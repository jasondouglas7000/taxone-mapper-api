package br.com.lkm.taxone.mapper.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.lkm.taxone.mapper.entity.TaxOneApi;
import br.com.lkm.taxone.mapper.integration.dto.ConsultaLoteDTO;
import br.com.lkm.taxone.mapper.integration.dto.ConsultaLoteImportacaoDTO;
import br.com.lkm.taxone.mapper.integration.dto.IncluirResponseDTO;
import br.com.lkm.taxone.mapper.integration.dto.LoteDTO;
import br.com.lkm.taxone.mapper.integration.dto.SAFXTableTaxOneDTO;
import br.com.lkm.taxone.mapper.repository.TaxOneApiRepository;
import br.com.lkm.taxone.mapper.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
@Service
public class OncoClinicasTaxtOneServiceBuilder {

	@Autowired
	private TaxOneApiRepository taxOneApiRepository; 
	
	@Value("${lkm.taxonemapper.integration.codEmpresa}")
	private String codEmpresa;

	@Value("${lkm.taxonemapper.integration.codEstab}")
	private String codEstab;

	public OncoClinicasTaxtOneService createService(String token) {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		httpClient.readTimeout(1, TimeUnit.MINUTES);
		httpClient.writeTimeout(1, TimeUnit.MINUTES);
		
		if ( token != null ) {
	       httpClient.interceptors().clear();
	       httpClient.addInterceptor( chain -> {
	           Request original = chain.request();
	           Request request = original.newBuilder()
	             .header("token", token)
	             .build();
	           return chain.proceed(request);
	       });
		}
		
		@SuppressWarnings("deprecation")
		Gson gson = new GsonBuilder().setLenient().create();

		TaxOneApi taxOneApi = taxOneApiRepository.getReferenceById(1);
		
		Retrofit retrofit = new Retrofit.Builder()
		  .baseUrl(taxOneApi.getUrl())
		  .addConverterFactory(GsonConverterFactory.create(gson))
		  .client(httpClient.build())
		  .build();
		
		return retrofit.create(OncoClinicasTaxtOneService.class);
	}
	
	
	public static void main(String... args) {
		try {
//			String USERNAME = "douglas_trindade-lkm";
//			String PASSWORD = "deqkyk-zaxfox-roPja2";
			OncoClinicasTaxtOneServiceBuilder builder = new OncoClinicasTaxtOneServiceBuilder();
//			builder.TAXONE_ONCOCLINICAS_API = "https://safx.prod.taxone.thomsonreuters.com/api/";
//			OncoClinicasTaxtOneService os = builder.createService(null);
//			String token = os.authentication(USERNAME, PASSWORD).execute().body().string();
			String token = "wot T1RLAQINeNOlGupfLwEJfzoyq3WMm69g4RANZYCWR-xpN4JcwtOgXO-ZAAHQUHd459o0X_uUopbl5-M4EFsewyMI6XfE2xy-sSXkNluoe84OKXfLF0ebFv2hZqbdP79m3xj8iKEgfOhh2KMT6qUoc5KBhxm94zFBgst8SRKjbRl97u-UmOCvgLnq__G3FPhxuCFvmYYmnSQ6Se9zxPTS9QhdbkmAgM3Hw25X0zzS8rM4JidETSrhFZEZ7EPxWLs8_yWTqAcUOiZi2A3VV_1zv_9qJ3Ue2PcPuuVHchJ-Xx70EXXvH9FxGze1REgW2tgR1x7jjiVWe3r0MftsSApYHSgh3Y6dKGe_Q2SR8FIC3kIhzmr4hY1VKPTnG2JwggohPMBBfqAMPkhAju0UAIUHPpx08lKdIEtG-it6h0RNoJJEZTJSIooYd68ibZz_IWMFLjSxpST2J29VDajE5Xvzzpf_ur7DFXtAUMNJ_ICOrtOsGayY8nFAehzChVBBdSO3E5BHUXvFy0KWwCnwTKIOxa6wP2l7zPuSnguspJZZMGwigGL9TcElCU2xrQsgvDQbXN8OQGwy-nO1gpI-GTn2FZRQmePWZiVXrNI27NcflqrTy2YgTgMsWkDlVAbVm-rn0Kjo8r3Dbr4mLOk_XJPMEP5N1zbP3ZDqc3-oDBE*";
			log.info("token:" + token);
			OncoClinicasTaxtOneService aos = builder.createService(token);
//			LoteDTO l = aos.gerarNumLote().execute().body();
			LoteDTO l = new LoteDTO("afWZUVGnCdWk/pxUSYlCnZEXJmhQX3ry3nPVJW2XP9JxpfYeJlm2m9zq");
			log.info("lote:" + l);
			
			SAFXTableTaxOneDTO st = new SAFXTableTaxOneDTO();
//			st.setSafx("SAFX01");
			st.setSafx("SAFX04");
			Map<String, Object> registro = new HashMap<>(); 
//			registro.put("COD_EMPRESA", "33");
//			registro.put("COD_ESTAB", "33");
//			registro.put("DATA_OPERACAO", "20210903");
//			registro.put("CONTA_DEB_CRED", "777");
//			registro.put("IND_DEB_CRE", "7");
//			registro.put("ARQUIVAMENTO", "AA");
//			registro.put("VLR_LANCTO", "3");
			registro.put("IND_FIS_JUR","1");
			registro.put("COD_FIS_JUR","LFA1-LIFNR");//29300555880
			registro.put("IND_CONTEM_COD","1");
			registro.put("RAZAO_SOCIAL","JDO Systems Informatica");
			registro.put("CPF_CGC","kna1-stcd1");
			
			List<Map<String, Object>> registros = new ArrayList<>();
			registros.add(registro);
			st.setRegistros(registros);
			//codEmpresa
			String data = DateUtil.formatyyyyMMdd(new Date());
			IncluirResponseDTO incluirRespnse = aos.incluirRegistros(l.getNum_lote(), builder.codEmpresa, builder.codEstab, 
					data, data, st).execute().body();
			log.info("incluirRespnse:" + incluirRespnse);

			Thread.sleep(10000);
			Response<ConsultaLoteDTO> excConsultaLote = aos.consultaLote(l.getNum_lote()).execute();
			ConsultaLoteDTO cl = excConsultaLote.body();
			if (cl != null) {
				log.info("cl:" + cl);
			}else {
				log.info("error:" + excConsultaLote.errorBody().string());
			}

			ConsultaLoteImportacaoDTO cli = aos.consultaLoteImportacao(l.getNum_lote()).execute().body();
			log.info("cli:" + cli);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
