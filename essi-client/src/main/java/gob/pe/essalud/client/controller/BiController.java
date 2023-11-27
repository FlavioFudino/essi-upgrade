package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.dto.ConsultaUsuariosDto;
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

    private final TrxClient trxClient;

    @Autowired
    public BiController(TrxClient trxClient) {
        this.trxClient = trxClient;
    }

    @GetMapping(CONSULTA_USUARIOS)
    public List<ConsultaUsuariosDto> consultarUsuarios() {
        return trxClient.consultarUsuarios();
    }

}
