package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PlantillaQrController.PLANTILLA_QR)
public class PlantillaQrController extends BaseController {
    static final String PLANTILLA_QR = "PlantillaQr";

    //private final PlantillaQrClient plantillaQrClient;

    @Autowired
    public PlantillaQrController() {

    }

    /*
    @PostMapping
    public Object getPlantillaQr(@RequestBody Object request) {
        // TODO: review
        return plantillaQrClient.get(request);
    }*/

}
