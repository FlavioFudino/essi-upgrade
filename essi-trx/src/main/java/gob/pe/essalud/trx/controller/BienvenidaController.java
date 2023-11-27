package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.BienvenidaListDto;
import gob.pe.essalud.trx.service.BienvenidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BienvenidaController.BIENVENIDA)
public class BienvenidaController extends BaseController {

    public static final String BIENVENIDA = "bienvenida";

    private final BienvenidaService bienvenidaService;

    @Autowired
    public BienvenidaController(BienvenidaService bienvenidaService) {
        this.bienvenidaService = bienvenidaService;
    }

    @GetMapping
    public List<BienvenidaListDto> getBienvenidaList() {
        return bienvenidaService.getBienvenidaList();
    }

}
