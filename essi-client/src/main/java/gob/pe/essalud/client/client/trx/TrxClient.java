package gob.pe.essalud.client.client.trx;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.*;
import gob.pe.essalud.client.dto.bi.ConsultaCentroLatLongDto;
import gob.pe.essalud.client.dto.bienvenida.BienvenidaListDto;
import gob.pe.essalud.client.dto.familiar.FamiliarPacienteRequestDto;
import gob.pe.essalud.client.dto.paciente.GetRiesgoDiabetesResponseDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarResponseDto;
import gob.pe.essalud.client.dto.publics.IpressCitaDto;
import gob.pe.essalud.client.dto.publics.IpressSinCitaDto;
import gob.pe.essalud.client.dto.publics.RedDto;
import gob.pe.essalud.client.dto.publics.ServicioIpressDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteDetalleRequestDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteDetalleResponseDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteDto;
import gob.pe.essalud.client.dto.seguridad_cliente.SeguridadClienteRequestDto;
import gob.pe.essalud.client.dto.trx.CitaDto;
import gob.pe.essalud.client.dto.trx.UpdateCentroPacienteRequestDto;
import gob.pe.essalud.client.dto.trx.UsuarioDataDto;
import gob.pe.essalud.client.dto.usuario.UsuarioPerfilDto;
import gob.pe.essalud.client.dto.usuario.UsuarioRegisterDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@FeignClient(name = "trxservice", url = "${" + Constantes.URL_ENDPOINT_BASE_TRX + "}")
public interface TrxClient {

    @GetMapping(Constantes.URL_USUARIOS + Constantes.URL_EXIST_USER)
    ResponseDto<Boolean> getUserByCode(@RequestParam String username);

    @PostMapping(Constantes.URL_USUARIOS)
    Map saveUser(@RequestBody UsuarioRegisterDto model);

    @PutMapping(Constantes.URL_USUARIOS_PERFIL)
    ResponseDto savePerfil(@RequestBody UsuarioPerfilDto perfil);

    @GetMapping(Constantes.URL_USUARIOS_PERFIL)
    UsuarioPerfilDto getPerfil(@RequestParam(Constantes.URL_TIPO_DOCUMENTO) String tipoDocumento,
                               @RequestParam(Constantes.URL_NRO_DOCUMENTO) String numeroDocumento);

    @GetMapping(Constantes.URL_FAMILIAR)
    Map getFamiliar(@RequestParam(Constantes.URL_TIPO_DOCUMENTO) String tipoDocumento,
                    @RequestParam(Constantes.URL_NRO_DOCUMENTO) String numeroDocumento);

    @PostMapping(Constantes.URL_FAMILIAR)
    ResponseDto<FamiliarPacienteRequestDto> saveFamiliar(@RequestBody FamiliarPacienteRequestDto model);

    @GetMapping(Constantes.URL_SEGURIDAD_CLIENTE)
    SeguridadClienteDto getSeguridadClienteByIpCliente(@RequestParam String ipCliente);

    @PostMapping(Constantes.URL_SEGURIDAD_CLIENTE)
    SeguridadClienteDto saveSeguridadCliente(@RequestBody SeguridadClienteRequestDto requestDto);

    @PostMapping(Constantes.URL_SEGURIDAD_CLIENTE + Constantes.URL_SEGURIDAD_CLIENTE_GUARDAR_DETALLE)
    SeguridadClienteDetalleResponseDto saveSeguridadClienteDetalle(@RequestBody SeguridadClienteDetalleRequestDto requestDto);

    @GetMapping(Constantes.URL_PARAMETROS_TRX + Constantes.URL_PARAMETROS_TRX_CURRENT_DATE)
    Date getCurrentDate();

    @GetMapping(Constantes.URL_BIENVENIDA_TRX)
    List<BienvenidaListDto> getBienvenidaList();

    @PostMapping(Constantes.URL_GUARDAR_CITA)
    CitaDto guardarCita(@RequestBody CitaDto citaDto);

    @PostMapping(Constantes.URL_BUSCAR_CITAS)
    List<CitaDto> buscarCitas(@RequestBody UsuarioDataDto paramInput);

    @GetMapping(Constantes.URL_BI_CONSULTA_USUARIOS)
    List<ConsultaUsuariosDto> consultarUsuarios();

    @GetMapping(Constantes.URL_CENTRO_GET)
    @Cacheable(value = "trx/centro", key = "#cenAsiCod")
    CentroDto getCentro(@RequestParam String cenAsiCod);

    @GetMapping(Constantes.URL_CONSULTA_CENTRO_LAT_LONG)
    ConsultaCentroLatLongDto consultaCentroLatLong(@PathVariable String cenAsiCod);

    @PostMapping("paciente/getPacienteAsegurado")
    PacienteAseguradoDto getPacienteAsegurado(@RequestBody PacienteAseguradoRequestDto input);

    @PostMapping("paciente/updateCentroPaciente")
    boolean updateCentroPaciente(@RequestBody UpdateCentroPacienteRequestDto input);

    @GetMapping("p/lista-ipress-cita")
    List<IpressCitaDto> getListaIpressCita();

    @GetMapping("p/lista-servicios-ipress/{idIpress}")
    List<ServicioIpressDto> getListaServiciosIpress(@PathVariable int idIpress);

    @GetMapping("p/lista-redes")
    List<RedDto> getListaRedes();

    @GetMapping("p/lista-ipress-sin-cita/{red}")
    List<IpressSinCitaDto> getListaIpressSinCita(@PathVariable String red);

    @PostMapping("paciente/getRiesgoDiabetes")
    GetRiesgoDiabetesResponseDto getRiesgoDiabetes(@RequestBody RequestGenericDto input);

    @PostMapping("paciente/riesgoDiabetesEvaluar")
    RiesgoDiabetesEvaluarResponseDto riesgoDiabetesEvaluar(@RequestBody RiesgoDiabetesEvaluarRequestDto input);
}
