package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.SessionService;
import gob.pe.essalud.client.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(TriajeController.TRIAJE)
public class TriajeController extends BaseController {
    public static final String TRIAJE = "triaje";
    private static final String AUTOEVALUACION = "autoevaluacion";

    private final TriajeService triajeService;
    private final SessionService session;

    @Autowired
    public TriajeController(TriajeService triajeService,
                            SessionService session) {
        this.triajeService = triajeService;
        this.session = session;
    }

    @PostMapping(AUTOEVALUACION)
    public ResponseEntity<Map> autoEvaluacion(@Valid @RequestBody Map paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> autoEvaluacion");
        paramInput.put("dni", session.getNumeroDocumento());
        return ResponseEntity.ok(triajeService.autoEvaluacion(paramInput));
    }

}
