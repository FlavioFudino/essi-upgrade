package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FamiliarBusqController.FAMILIAR_BUSQ)
public class FamiliarBusqController extends BaseController {
    public static final String FAMILIAR_BUSQ = "familiar-busq";
    public static final String DELETE = "delete";

    //private final BusqActivaClient busqActivaClient;
    private final SessionService session;

    @Autowired
    public FamiliarBusqController(
                                  SessionService session) {
        this.session = session;
    }

    /*
    @GetMapping("/{idFamiliar}")
    public Object getFamiliar(@PathVariable Integer idFamiliar) {
        return busqActivaClient.getFamiliar(idFamiliar);
    }

    @PostMapping
    public Object saveFamiliar(@RequestBody Map data) {
        data.put("tipoDocIdent", session.getTipoDocumento());
        data.put("numeroDocIdent", session.getNumeroDocumento());

        return busqActivaClient.saveFamiliar(data);
    }

    @GetMapping
    public Object familiaList(@RequestParam(required = false) String tipoDocumento,
                              @RequestParam(required = false) String numeroDocumento) {
        tipoDocumento = session.getTipoDocumento();
        numeroDocumento = session.getNumeroDocumento();
        return busqActivaClient.familiaList(tipoDocumento, numeroDocumento);
    }

    @PostMapping(DELETE)
    public Object deleteFamiliarf(@RequestParam(required = false) String tipoDocumento,
                                  @RequestParam(required = false) String numeroDocumento,
                                  @RequestParam Integer idFamiliar) {
        tipoDocumento = session.getTipoDocumento();
        numeroDocumento = session.getNumeroDocumento();
        return busqActivaClient.deleteFamiliar(tipoDocumento, numeroDocumento, idFamiliar);
    }


    @DeleteMapping(DELETE)
    public Object deleteFamiliar(@RequestParam(required = false) String tipoDocumento,
                                 @RequestParam(required = false) String numeroDocumento,
                                 @RequestParam Integer idFamiliar) {

        tipoDocumento = session.getTipoDocumento();
        numeroDocumento = session.getNumeroDocumento();
        return busqActivaClient.deleteFamiliar(tipoDocumento, numeroDocumento, idFamiliar);
    }*/

}
