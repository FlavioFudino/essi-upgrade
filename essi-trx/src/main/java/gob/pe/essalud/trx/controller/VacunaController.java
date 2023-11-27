package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.dto.VacunaDto;
import gob.pe.essalud.trx.repository.VacunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vacuna")
public class VacunaController extends BaseController {
    public static final String PARAMS = "params";
    private final VacunaRepository vacunaRepository;

    @Autowired
    public VacunaController(VacunaRepository vacunaRepository) {
        this.vacunaRepository = vacunaRepository;
    }

    @GetMapping
    public ResponseEntity<?> getList(@RequestParam(required = false) String redEssalud,
                                     @RequestParam(required = false) String region,
                                     @RequestParam(required = false) String provincia,
                                     @RequestParam(required = false) String distrito,
                                     @RequestParam(required = false) String ipress) {
        ResponseDto<List<VacunaDto>> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("redEssalud", redEssalud);
            params.put("region", region);
            params.put("provincia", provincia);
            params.put("distrito", distrito);
            params.put("ipress", ipress);
            List<VacunaDto> lista = vacunaRepository.findAllByCriteria(params);
            response.setData(lista);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(PARAMS)
    public ResponseEntity<?> getListParams(@RequestParam(required = false) String redEssalud,
                                           @RequestParam(required = false) String region,
                                           @RequestParam(required = false) String provincia,
                                           @RequestParam(required = false) String distrito,
                                           @RequestParam(required = false) String ipress) {
        ResponseDto<List<VacunaDto>> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("redEssalud", redEssalud == null ? '0' : '1');
            params.put("region", region == null ? '0' : '1');
            params.put("provincia", provincia == null ? '0' : '1');
            params.put("distrito", distrito == null ? '0' : '1');
            params.put("ipress", ipress == null ? '0' : '1');
            List<VacunaDto> lista = vacunaRepository.findParamsByCriteria(params);
            response.setData(lista);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

}
