package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ReniecController.RENIEC)
public class ReniecController extends BaseController {
    public final static String RENIEC = "reniec";
    /*
    private final ReniecService reniecService;

    @Autowired
    public ReniecController(ReniecService reniecService) {
        this.reniecService = reniecService;
    }

    @GetMapping
    public ResponseDto<PersonaReniec> getPersona(@RequestParam String dni) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "->getPersona RENIEC");
        ResponseDto<PersonaReniec> response = new ResponseDto<>();
        PersonaReniec data = reniecService.getPersona(dni);
        if (data != null) {
            response.setData(data);
            response.setCodResult(ResponseType.SUCCESS);
        } else {
            response.setCodResult(ResponseType.VALIDATION);
            response.setMessage("El DNI ingresado no es válido");
        }
        return response;
    }*/
}
