package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.ClaveChangeRequestDto;
import gob.pe.essalud.client.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.client.service.ClaveService;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(ClaveController.CLAVE)
public class ClaveController extends BaseController {
    public static final String CLAVE = "clave";
    private static final String RECOVERY = "recovery";

    private static final String CLAVE_MOVIL = "cm";
    private static final String RECOVERY_MOVIL = "recm";

    private final ClaveService claveService;
    private final SessionService session;

    @Autowired
    public ClaveController(ClaveService claveService,
                           SessionService session) {
        this.claveService = claveService;
        this.session = session;
    }

    @PostMapping(RECOVERY)
    public ResponseEntity<?> recovery(@Valid @RequestBody ClaveRecoveryRequestDto claveRecoveryRequestDto,
                                                             @RequestParam(required = false) String captchaToken) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> recovery");
        return ResponseEntity.ok(claveService.recovery(claveRecoveryRequestDto, captchaToken,true));
    }

    @PostMapping
    public ResponseEntity<Map> save(@Valid @RequestBody ClaveChangeRequestDto cambioClaveRequestDto,
                                    @RequestParam(required = false) String captchaToken) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> save");
        return ResponseEntity.ok(claveService.save(cambioClaveRequestDto, captchaToken,true));
    }

    @PostMapping(RECOVERY_MOVIL)
    public ResponseEntity<?> recoveryMovil(@Valid @RequestBody ClaveRecoveryRequestDto claveRecoveryRequestDto,
                                      @RequestParam(required = false) String captchaToken) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> recovery");
        return ResponseEntity.ok(claveService.recovery(claveRecoveryRequestDto, captchaToken,false));
    }

    @PostMapping(CLAVE_MOVIL)
    public ResponseEntity<Map> saveMovil(@Valid @RequestBody ClaveChangeRequestDto cambioClaveRequestDto,
                                    @RequestParam(required = false) String captchaToken) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> save");
        return ResponseEntity.ok(claveService.save(cambioClaveRequestDto, captchaToken,false));
    }

}
