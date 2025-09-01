package br.com.lkm.taxone.mapper.integration;

import br.com.lkm.taxone.mapper.integration.dto.ConsultaLoteDTO;
import br.com.lkm.taxone.mapper.integration.dto.ConsultaLoteImportacaoDTO;
import br.com.lkm.taxone.mapper.integration.dto.ImportarDTO;
import br.com.lkm.taxone.mapper.integration.dto.IncluirResponseDTO;
import br.com.lkm.taxone.mapper.integration.dto.LoteDTO;
import br.com.lkm.taxone.mapper.integration.dto.SAFXTableTaxOneDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OncoClinicasTaxtOneService {

	@POST("authentication")
	public Call<ResponseBody> authentication(@Header("login") String login, @Header("password") String password);
	
	@POST("carga/geraNumLote")
	public Call<LoteDTO> gerarNumLote();
	
	@POST("carga/1")
	public Call<IncluirResponseDTO> incluirRegistros(@Header("numLote") String numLote,  @Header("codEmpresa") String codEmpresa,
			@Header("codEstab") String codEstab, @Header("dataIni") String dataIni, @Header("dataFim") String dataFim,  
			@Body SAFXTableTaxOneDTO st);
	
	@POST("carga/consultaLote")
	public Call<ConsultaLoteDTO> consultaLote(@Header("numLote") String numLote);
	
	@POST("carga/importar")
	public Call<ImportarDTO> importar(@Header("numLote") String numLote);
	
	@POST("carga/consultaLoteImportacao")
	public Call<ConsultaLoteImportacaoDTO> consultaLoteImportacao(@Header("numLote") String numLote);
	
}

