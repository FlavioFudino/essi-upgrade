package gob.pe.essalud.emailservice.controller;

import gob.pe.essalud.emailservice.base.BaseController;
import gob.pe.essalud.emailservice.dto.miconsulta.RecuperarClaveMobileRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;
import gob.pe.essalud.emailservice.dto.miconsulta.RegistrarUsuarioRequestDto;
import gob.pe.essalud.emailservice.service.MiConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("miconsulta")
@RequiredArgsConstructor
public class MiConsultaController extends BaseController {

    private final MiConsultaService miConsultaService;

    @PostMapping("registrarUsuario")
    public boolean registrarUsuario(@RequestBody RegistrarUsuarioRequestDto requestDto) {
        return miConsultaService.registrarUsuario(requestDto);
    }

    @PostMapping("recuperarClaveWeb")
    public boolean recuperarClaveWeb(@RequestBody RecuperarClaveWebRequestDto requestDto) {
        return miConsultaService.recuperarClaveWeb(requestDto);
    }

    @PostMapping("recuperarClaveMobile")
    public boolean recuperarClaveMobile(@RequestBody RecuperarClaveMobileRequestDto requestDto) {
        return miConsultaService.recuperarClaveMobile(requestDto);
    }
}
