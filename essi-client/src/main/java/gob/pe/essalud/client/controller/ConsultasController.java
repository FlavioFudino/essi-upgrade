package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.constants.DateFormat;
import gob.pe.essalud.client.common.constants.EstadoReceta;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.dto.ConsultasDto;
import gob.pe.essalud.client.dto.RecetasRequestDto;
import gob.pe.essalud.client.dto.RequestGenericDto;
import gob.pe.essalud.client.dto.citt.PersonaBuscarReqDto;
import gob.pe.essalud.client.dto.consulta.ConsultaEmergenciaDto;
import gob.pe.essalud.client.dto.essi_consulta.DetalleSolicitudCittReqDto;
import gob.pe.essalud.client.dto.essi_consulta.ListaSolicitudCittReqDto;
import gob.pe.essalud.client.service.CittService;
import gob.pe.essalud.client.service.ConsultasService;
import gob.pe.essalud.client.service.SessionService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("")
public class ConsultasController extends BaseController {

    private final ConsultasService consultasService;
    private final CittService cittService;
    private final SessionService session;

    @Autowired
    public ConsultasController(ConsultasService consultasService,
                               CittService cittService,
                               SessionService session) {

        this.consultasService = consultasService;
        this.cittService = cittService;
        this.session = session;
    }

    @PostMapping("consultaExterna")
    public Map consultaExterna(@RequestBody ConsultasDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaExterna");
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return consultasService.consultaExterna(paramInput);
    }

    @PostMapping("consultaEmergencia")
    public List<ConsultaEmergenciaDto> consultaEmergencia(@RequestBody ConsultasDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaEmergencia");
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return consultasService.consultaEmergencia(paramInput);
    }

    @PostMapping("consultaHospitalizacion")
    public Map consultaHospitalizacion(@RequestBody ConsultasDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaHospitalizacion");
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return consultasService.consultaHospitalizacion(paramInput);
    }

    @PostMapping("consultaCentroQuirurgico")
    public Map consultaCentroQuirurgico(@RequestBody ConsultasDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaCentroQuirurgico");
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        return consultasService.consultaCentroQuirurgico(paramInput);
    }

    @PostMapping("consultasRecetas")
    public Map consultasRecetas(@RequestBody(required = false) RecetasRequestDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "consultasRecetas");
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());

        Date dFechaIni = new DateTime().minusDays(90).toDate(); //HOY-180 días
        Date dFechaFin = new DateTime().plusDays(180).toDate(); //HOY+180 días

        paramInput.setFecIni(DateUtil.format(dFechaIni, DateFormat.DD_MM_YYYY)); //dd/MM/yyyy
        paramInput.setFecFin(DateUtil.format(dFechaFin, DateFormat.DD_MM_YYYY)); //dd/MM/yyyy

        paramInput.setEstado(EstadoReceta.TODOS);
        return consultasService.consultasRecetas(paramInput);
    }

    @PostMapping("consultaAtencionesMedicas")
    public ResponseEntity<?> consultaAtencionesMedicas() {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaExterna");
        RequestGenericDto paramInput = new RequestGenericDto();
        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        //paramInput.setNumDoc("07227138"); //test
        return ResponseEntity.ok(consultasService.consultaAtencionesMedicas(paramInput));
    }

    @PostMapping("listaSolicitudCitt")
    public ResponseEntity<?> listaSolicitudCitt() {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->listaSolicitudCitt");
        ListaSolicitudCittReqDto paramInput = new ListaSolicitudCittReqDto();
        paramInput.setLisTipDoc(session.getTipoDocumento());
        paramInput.setLisNumDoc(session.getNumeroDocumento());
        //paramInput.setLisNumDoc("28850994"); //test: 28850994
        return ResponseEntity.ok(consultasService.listaSolicitudCitt(paramInput));
    }

    @GetMapping("detalleSolicitudCitt/{detSolNumNit}")
    public ResponseEntity<?> detalleSolicitudCitt(@PathVariable String detSolNumNit) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->detalleSolicitudCitt");
        DetalleSolicitudCittReqDto paramInput = new DetalleSolicitudCittReqDto();
        paramInput.setDetSolNumNit(detSolNumNit);
        return ResponseEntity.ok(consultasService.detalleSolicitudCitt(paramInput));
    }

    @PostMapping("consultaCittEmitidos")
    public ResponseEntity<?> consultaCittEmitidos() {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->consultaCittEmitidos");
        PersonaBuscarReqDto input = new PersonaBuscarReqDto();
        input.setTipoDoc(session.getTipoDocumento());
        input.setNumDoc(session.getNumeroDocumento());
        return ResponseEntity.ok(cittService.buscar(input));
    }

}