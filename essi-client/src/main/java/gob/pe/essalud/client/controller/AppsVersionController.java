package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.service.AppsVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("")
public class AppsVersionController extends BaseController {

    private static final String VERSION = "version";

    private final AppsVersionService appsVersionService;

    @Autowired
    public AppsVersionController(AppsVersionService appsVersionService) {
        this.appsVersionService = appsVersionService;
    }

    @GetMapping(VERSION)
    public ResponseEntity<Map> version(@RequestParam("siglas") String siglas) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "-> version");
        return ResponseEntity.ok(appsVersionService.version(siglas));
    }

}
