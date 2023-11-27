package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.service.UbigeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("")
public class UbigeoController extends BaseController {
    public static final String UBIGEO = "ubigeo";
    public static final String DEPARTAMENTS = "departamentos";
    public static final String PROVINCES = "provincias";
    public static final String DISTRICTS = "distritos";

    private final UbigeoService ubigeoService;

    @Autowired
    public UbigeoController(UbigeoService ubigeoService) {
        this.ubigeoService = ubigeoService;
    }

    @GetMapping(UBIGEO + "/{codigo}")
    public Map get(@PathVariable String codigo) {
        return ubigeoService.getUbigeo(codigo);
    }

    @GetMapping(UBIGEO + "/" + DEPARTAMENTS)
    public Map[] getDepartaments() {
        return ubigeoService.getDepartaments();
    }

    @GetMapping(UBIGEO + "/" + PROVINCES)
    public Map[] getDepartaments(@RequestParam String codigoDepartamento) {
        return ubigeoService.getProvinces(codigoDepartamento);
    }

    @GetMapping(UBIGEO + "/" + DISTRICTS)
    public Map[] getDistricts(@RequestParam String codigoDepartamento, @RequestParam String codigoProvincia) {
        return ubigeoService.getDistricts(codigoDepartamento, codigoProvincia);
    }

}
