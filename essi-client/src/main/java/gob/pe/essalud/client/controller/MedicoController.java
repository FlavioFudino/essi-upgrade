package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.constants.DateFormat;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.dto.medico.ListaSolicitudExamenRequestDto;
import gob.pe.essalud.client.dto.medico.ListaSolicitudExamenResponseDto;
import gob.pe.essalud.client.service.MedicoService;
import gob.pe.essalud.client.service.SessionService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(MedicoController.MEDICO)
public class MedicoController extends BaseController {
    public static final String MEDICO = "medico";
    private static final String LOGIN = "login";
    private static final String EXAMEN_AUXILIARES = "listaSolicitudExamen";
    private static final String PROGRAMACION = "programacion";
    private static final String PACIENTES = "pacientes";
    private final MedicoService medicoService;
    private final SessionService session;

    @Autowired
    public MedicoController(MedicoService medicoService,
                            SessionService session) {
        this.medicoService = medicoService;
        this.session = session;
    }

    @PostMapping(EXAMEN_AUXILIARES)
    public ListaSolicitudExamenResponseDto listaSolicitudExamen(@RequestBody ListaSolicitudExamenRequestDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->listaSolicitudExamen");

        paramInput.setTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento()); //test: 30563518
        paramInput.setCodOri("1");
        paramInput.setOpcion("1");        

        Date dCurrentDate = new DateTime().minusMonths(3).toDate(); //Fecha Actual -3 Meses
        String sCurrentDate = DateUtil.format(dCurrentDate, DateFormat.DD_MM_YYYY); //Formato: dd/MM/yyyy
        paramInput.setFecDesde(sCurrentDate);

        return medicoService.listaSolicitudExamen(paramInput);
    }

    /*
    @PostMapping(LOGIN)
    public Map login(@Valid @RequestBody MedicoLoginDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, LOGIN);
        return medicoService.login(paramInput);
    }

    @PostMapping(PROGRAMACION)
    public Map programacion(@Valid @RequestBody ProgramacionRequestDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, PROGRAMACION);
        paramInput.setTipdoc(session.getTipoDocumento());
        paramInput.setNumdoc(session.getNumeroDocumento());
        return medicoService.programacion(paramInput);
    }

    @PostMapping(PACIENTES)
    public Map pacientes(@Valid @RequestBody PacienteCitadosRequestDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, PACIENTES);
        paramInput.setTipdoc(session.getTipoDocumento());
        paramInput.setNumdoc(session.getNumeroDocumento());
        return medicoService.pacientesCitados(paramInput);
    }*/

}
