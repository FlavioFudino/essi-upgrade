package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.ConsultaUsuariosDto;
import gob.pe.essalud.trx.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BiController.BI)
public class BiController extends BaseController {

    public static final String BI = "bi";
    private static final String CONSULTA_USUARIOS = "consulta-usuarios";

    private final ConsultaService consultaService;

    @Autowired
    public BiController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping(CONSULTA_USUARIOS)
    public List<ConsultaUsuariosDto> consultarUsuarios() {
        //return consultaService.consultarUsuarios();
        return null;
    }

}
