package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.ParametrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ParametrosController.PARAMETROS)
public class ParametrosController extends BaseController {
    public static final String PARAMETROS = "parametros";
    private final ParametrosService parametrosService;

    @Autowired
    public ParametrosController(ParametrosService parametrosService) {
        this.parametrosService = parametrosService;
    }

    @GetMapping
    public ResponseEntity<Map> version(@RequestParam("filters") String filters) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "-> version");
        return ResponseEntity.ok(parametrosService.get(filters));
    }

}
