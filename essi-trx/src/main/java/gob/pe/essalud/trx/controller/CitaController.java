package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.dto.CitaDto;
import gob.pe.essalud.trx.dto.UsuarioDataDto;
import gob.pe.essalud.trx.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(CitaController.CITA)
public class CitaController {

    public static final String CITA = "cita";
    public static final String GUARDAR = "guardar";
    public static final String BUSCAR = "buscar";

    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping(GUARDAR)
    public CitaDto guardar(@RequestBody CitaDto citaDto) {
        CitaDto response = citaService.save(citaDto);
        return response;
    }

    @PostMapping(BUSCAR)
    public List<CitaDto> buscar(@RequestBody UsuarioDataDto paramInput) {
        List<CitaDto> response = citaService.buscar(paramInput);
        return response;
    }

}
