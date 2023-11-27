package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.dto.bienvenida.BienvenidaListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BienvenidaController.BIENVENIDA)
public class BienvenidaController extends BaseController {

    public static final String BIENVENIDA = "bienvenida";

    private final TrxClient trxClient;

    @Autowired
    public BienvenidaController(TrxClient trxClient) {
        this.trxClient = trxClient;
    }

    @GetMapping
    public List<BienvenidaListDto> getBienvenidaList() {
        return trxClient.getBienvenidaList();
    }

}
