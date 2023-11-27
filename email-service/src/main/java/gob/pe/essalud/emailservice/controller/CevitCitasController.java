package gob.pe.essalud.emailservice.controller;

import gob.pe.essalud.emailservice.base.BaseController;
import gob.pe.essalud.emailservice.dto.cevitCitas.RegistrarCevitCitaDto;
import gob.pe.essalud.emailservice.service.CevitCitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cevit-citas")
@RequiredArgsConstructor
public class CevitCitasController extends BaseController {

    private final CevitCitaService _cevitCitaService;

    @PostMapping("registrarCita")
    public boolean registrarCita(@RequestBody RegistrarCevitCitaDto requestDto) {
        return _cevitCitaService.registrarCita(requestDto);
    }
}
