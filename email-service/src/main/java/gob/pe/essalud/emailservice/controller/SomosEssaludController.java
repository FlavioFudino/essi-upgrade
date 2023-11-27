package gob.pe.essalud.emailservice.controller;

import gob.pe.essalud.emailservice.base.BaseController;
import gob.pe.essalud.emailservice.dto.common.ActivarCuentaRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;
import gob.pe.essalud.emailservice.service.SomosEssaludService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("somos-essalud")
@RequiredArgsConstructor
public class SomosEssaludController extends BaseController {

    private final SomosEssaludService somosEssaludService;

    @PostMapping("activarCuenta")
    public boolean activarCuenta(@RequestBody ActivarCuentaRequestDto input) {
        return somosEssaludService.activarCuenta(input);
    }

    @PostMapping("recuperarClave")
    public boolean recuperarClave(@RequestBody RecuperarClaveWebRequestDto input) {
        return somosEssaludService.recuperarClave(input);
    }

}
