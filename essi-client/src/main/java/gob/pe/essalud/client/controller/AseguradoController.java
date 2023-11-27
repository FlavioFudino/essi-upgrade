package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AseguradoController.ASEGURADO)
public class AseguradoController extends BaseController {

    public static final String ASEGURADO = "asegurado";
    public static final String LOGIN = "login";

    //private final BusqActivaClient busqActivaClient;
    private final SessionService session;

    @Autowired
    public AseguradoController(SessionService session) {
        this.session = session;
    }

    /*
    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody EssiPacienteRequestDto paramInput) {
        paramInput.setCodTipDoc(session.getTipoDocumento());
        paramInput.setNumDoc(session.getNumeroDocumento());
        ResponseDto<AseguradoRequestDto> response = busqActivaClient.aseguradoLogin(paramInput);
        return ResponseEntity.ok(response);
    }*/
}