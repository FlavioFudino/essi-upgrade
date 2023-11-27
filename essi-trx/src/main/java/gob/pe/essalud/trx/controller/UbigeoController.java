package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.dto.UbigeoDataDto;
import gob.pe.essalud.trx.dto.UbigeoDto;
import gob.pe.essalud.trx.service.UbigeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UbigeoController.UBIGEO)
public class UbigeoController extends BaseController {
    public static final String UBIGEO = "ubigeo";
    public static final String DEPARTMENTS = "departamentos";
    public static final String PROVINCES = "provincias";
    public static final String DISTRICTS = "distritos";

    private final UbigeoService ubigeoService;

    @Autowired
    public UbigeoController(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    @GetMapping(value = "/{codigo}")
    public UbigeoDataDto get(@PathVariable String codigo) {
        return ubigeoService.get(codigo);
    }

    @GetMapping(value = DEPARTMENTS)
    public List<ParametroDto> getDepartments() {
        return ubigeoService.getDepartments();
    }

    @GetMapping(value = PROVINCES)
    public List<ParametroDto> getProvinces(@RequestParam String codigoDepartamento) {
        return ubigeoService.getProvinces(codigoDepartamento);
    }

    @GetMapping(value = DISTRICTS)
    public List<UbigeoDto> getDistricts(@RequestParam String codigoDepartamento,
                                        @RequestParam String codigoProvincia) {
        return ubigeoService.getDistricts(codigoDepartamento, codigoProvincia);
    }

}
