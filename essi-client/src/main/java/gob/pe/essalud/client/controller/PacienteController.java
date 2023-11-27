package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.client.GestionAtencionClient;
import gob.pe.essalud.client.client.essi.EssiClient;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.constants.DateFormat;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.dto.RequestCancelaSolicitudDto;
import gob.pe.essalud.client.dto.RequestConsultaSolicitudDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.RequestParametroDto;
import gob.pe.essalud.client.dto.essi.*;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaRequestDto;
import gob.pe.essalud.client.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.client.dto.trx.*;
import gob.pe.essalud.client.service.PacienteService;
import gob.pe.essalud.client.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class  PacienteController extends BaseController {

    private final PacienteService pacienteService;
    private final EssiClient essiClient;
    private final SessionService session;
    private final TrxClient trxClient;

    @PostMapping("getListaSolicitud")
    public ResponseEntity<?> postListaSolicitud(@RequestBody RequestConsultaSolicitudDto paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getListaSolicitud");
        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        EssiResponseDto<List<EssiListaSolicitudDto>> responseDto = pacienteService.getListaSolicitud(paramInput);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("parametroSolicitud")
    public EssiResponseDto<ParametroSolicitudResponseDto> parametroSolicitud(@RequestBody RequestParametroDto paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->parametroSolicitud");
        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return pacienteService.parametroSolicitud(paramInput);
    }

    @PostMapping("getCancelaSolicitud")
    public Map getCancelaSolicitud(@RequestBody RequestCancelaSolicitudDto paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getCancelaSolicitud");
        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return pacienteService.getCancelaSolicitud(paramInput);
    }

    @PostMapping("getCrearSolicitud")
    public Map getCrearSolicitud(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getCrearSolicitud");
        paramInput.put("codTipDoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.getCrearSolicitud(paramInput);
    }

    @PostMapping("getProgramacionSolicitud")
    public Map getProgramacionSolicitud(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getProgramacionSolicitud");
        paramInput.put("codTipdoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.getProgramacionSolicitud(paramInput);
    }

    @PostMapping("citasEmitidas")
    public EssiResponseDto<List<CitasEmitidasResponseDto>> citasEmitidas() {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->citasEmitidas");

        UsuarioDataDto paramInput = new UsuarioDataDto();
        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());

        return pacienteService.citasEmitidas(paramInput);
    }

    @PostMapping("programacionDisponible")
    public Map programacionDisponible(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->programacionDisponible");
        paramInput.put("codTipdoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.programacionDisponible(paramInput);
    }

    @PostMapping("generarCita")
    public Map generarCita(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->generarCita");
        paramInput.put("codTipdoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.generarCita(paramInput);
    }

    @PostMapping("generarCitaSolicitud")
    public Map generarCitaSolicitud(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->generarCitaSolicitud");
        paramInput.put("codTipdoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.generarCitaSolicitud(paramInput);
    }

    @PostMapping("confirmarCita")
    public EssiResponseDto confirmarCita(@RequestBody ConfirmarCitaRequestDto paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->confirmarCita");

        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());

        CitaDto citaRequest = new CitaDto();
        citaRequest.setOriCenAsi(paramInput.getOriCenAsis());
        citaRequest.setCenAsiCod(paramInput.getCodCentro());
        citaRequest.setActMedNum(paramInput.getNumCitaCreada());
        citaRequest.setTipoDocIdent(paramInput.getCodTipDoc());
        citaRequest.setNumeroDocIdent(paramInput.getNumDoc());
        citaRequest.setIndConfirmado(true);

        trxClient.guardarCita(citaRequest);

        EssiResponseDto response = new EssiResponseDto();
        response.setCodError("1");
        response.setDesError("Ok");
        return response;
    }

    @PostMapping("eliminarCita")
    public EssiResponseDto<Void> eliminarCita(@RequestBody CancelarCitaRequestDto paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->eliminarCita");

        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());

        CitaDto citaRequest = new CitaDto();
        citaRequest.setOriCenAsi(paramInput.getOriCenAsis());
        citaRequest.setCenAsiCod(paramInput.getCodCentro());
        citaRequest.setActMedNum(paramInput.getNumCitaCreada());
        citaRequest.setTipoDocIdent(paramInput.getCodTipDoc());
        citaRequest.setNumeroDocIdent(paramInput.getNumDoc());
        citaRequest.setIndConfirmado(false);

        trxClient.guardarCita(citaRequest);

        return pacienteService.eliminarCita(paramInput);
    }

    @PostMapping("getListaReferencia")
    public Map getListaReferencia(@RequestBody Map paramInput) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getListaReferencia");
        paramInput.put("codTipDoc", session.getTipoDocumento());
        paramInput.put("numDoc", session.getNumeroDocumento());
        return pacienteService.getListaReferencia(paramInput);
    }

    @PostMapping("registrarSolCitt")
    public RegistrarSolCittRespDto registrarSolCitt(@RequestBody RegistrarSolCittReqDto input) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->registrarSolCitt");
        input.setSolRegTipDoc(session.getTipoDocumento());
        input.setSolRegNumDoc(session.getNumeroDocumento());
        input.setSolRegCodUsu(session.getNumeroDocumento());
        return pacienteService.registrarSolCitt(input);
    }

    @GetMapping("getListaSolicitudOperaciones")
    public ResponseEntity<?> getListaSolicitudOperaciones() {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getListaSolicitudOperaciones");
        EssiListaSolicitudOperacionRequestDto input = new EssiListaSolicitudOperacionRequestDto();
        input.setTipDoc(session.getTipoDocumento());
        input.setNumDoc(session.getNumeroDocumento());
        input.setFechaIni("01/01/2023");
        input.setFechaFin(DateUtil.format(DateUtil.currentDate(), DateFormat.DD_MM_YYYY));
        input.setCodEstado("P");
        return ResponseEntity.ok(pacienteService.getListaSolicitudOperaciones(input));
    }

    @PostMapping("confirmarSolicitudOperacion")
    public ResponseEntity<?> confirmarSolicitudOperacion(@RequestBody EssiConfirmarSolicitudOperacionRequestDto input) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->confirmarSolicitudOperacion");
        input.setUsuarioExt(session.getNumeroDocumento());
        return ResponseEntity.ok(pacienteService.confirmarSolicitudOperacion(input));
    }

    @GetMapping("getRiesgoDiabetes")
    public ResponseEntity<?> getRiesgoDiabetes() {
        RequestGenericDto requestDto = new RequestGenericDto();
        requestDto.setTipDoc(session.getTipoDocumento());
        requestDto.setNumDoc(session.getNumeroDocumento());
        return ResponseEntity.ok(pacienteService.getRiesgoDiabetes(requestDto));
    }

    @PostMapping("riesgoDiabetesEvaluar")
    public ResponseEntity<?> riesgoDiabetesEvaluar(@RequestBody RiesgoDiabetesEvaluarRequestDto input) {
        input.setTipDoc(session.getTipoDocumento());
        input.setNumDoc(session.getNumeroDocumento());
        return ResponseEntity.ok(pacienteService.riesgoDiabetesEvaluar(input));
    }

    @PostMapping("registrarTeleUrgencia/{codCentro}")
    public ResponseEntity<?> registrarTeleUrgencia(@PathVariable String codCentro) {
        GaRegistrarColaRequestDto input = new GaRegistrarColaRequestDto();
        input.setTipoDoc(session.getTipoDocumento());
        input.setNumDoc(session.getNumeroDocumento());
        input.setCodCentro(codCentro);
        return ResponseEntity.ok(pacienteService.registrarTeleUrgencia(input));
    }
}
