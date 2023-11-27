package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.dto.familiar.FamiliarPacienteRequestDto;
import gob.pe.essalud.client.service.FamiliarService;
import gob.pe.essalud.client.service.ServiceException;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(FamiliarController.FAMILIAR)
public class FamiliarController extends BaseController {
    public static final String FAMILIAR = "familiar";
    private final FamiliarService familiarService;
    private final SessionService session;

    @Autowired
    public FamiliarController(FamiliarService familiarService,
                              SessionService session) {
        this.familiarService = familiarService;
        this.session = session;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody FamiliarPacienteRequestDto familiar) {
        this.loggerInfo(Constantes.LOG_LEVEL_INFO, "save");
        ResponseDto<FamiliarPacienteRequestDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            familiar.setTipoDocIdent(session.getTipoDocumento());
            familiar.setNumeroDocIdent(session.getNumeroDocumento());
            FamiliarPacienteRequestDto data = familiarService.save(familiar);
            response.setData(data);
        } catch (HttpStatusCodeException e) {
            String responseErrorServer = e.getResponseBodyAsString();
            response.setMessage(responseErrorServer);
            response.setCodResult(ResponseType.VALIDATION);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

@GetMapping
public Map get(@RequestParam(required = false) String tipoDocumento,
               @RequestParam(required = false) String numeroDocumento) {
    String tipoDoc = tipoDocumento;
    String numDoc = numeroDocumento;

    if (tipoDoc == null || tipoDoc.isEmpty()) {
        tipoDoc = session.getTipoDocumento();
    }

    if (numDoc == null || numDoc.isEmpty()) {
        numDoc = session.getNumeroDocumento();
    }
    return familiarService.get(tipoDoc, numDoc);
}

}
