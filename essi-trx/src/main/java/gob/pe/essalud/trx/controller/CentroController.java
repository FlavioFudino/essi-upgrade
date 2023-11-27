package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.dto.CentroDto;
import gob.pe.essalud.trx.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(CentroController.CENTERS)
public class CentroController {

    public static final String CENTERS = "centros";
    public static final String APPLY_CITA = "applyCita";

    private final CentroService centroService;

    @Autowired
    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping(APPLY_CITA)
    public ResponseEntity<?> getList(@RequestParam(required = false) String oriCenAsiCod,
                                     @RequestParam String cenAsiCod) {
        boolean applyCita = centroService.checkApplyCita(oriCenAsiCod, cenAsiCod);
        Map<String, Boolean> data = new HashMap<>();
        data.put("applyCita", applyCita);
        return ResponseEntity.ok(data);
    }

    @GetMapping
    public ResponseEntity<CentroDto> getCentro(@RequestParam String cenAsiCod) {
        CentroDto centro = centroService.getCentro(cenAsiCod);
        return ResponseEntity.ok(centro);
    }
}
