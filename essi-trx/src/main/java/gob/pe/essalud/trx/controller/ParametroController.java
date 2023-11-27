package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("parametros")
public class ParametroController extends BaseController {

    private final String CURRENT_DATE = "current-date";

    private final ParametroService parametroService;

    @Autowired
    public ParametroController(ParametroService parametroService) {
        this.parametroService = parametroService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam("filters") String filters) {

        HashMap<String, List<ParametroDto>> data = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            data = parametroService.getParams(filters);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(data, httpStatus);
    }

    @GetMapping(CURRENT_DATE)
    public Date currentDate() {
        Date fecha = parametroService.getCurrentDate();
        return fecha;
    }

}
