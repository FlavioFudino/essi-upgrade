package gob.pe.essalud.client.service.impl;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.dto.familiar.FamiliarPacienteRequestDto;
import gob.pe.essalud.client.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class FamiliarServiceImpl extends BaseService implements FamiliarService {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    private final TrxClient trxClient;

    @Autowired
    public FamiliarServiceImpl(TrxClient trxClient) {
        this.trxClient = trxClient;
    }

    @Override
    public Map get(String tipoDocumento, String numeroDocumento) {
        this.loggerInfo("Inicio get familiar", formatter.format(new Date()));
        return trxClient.getFamiliar(tipoDocumento, numeroDocumento);
    }

    @Override
    public FamiliarPacienteRequestDto save(FamiliarPacienteRequestDto familiar) {
        this.loggerInfo("Inicio save familiar", formatter.format(new Date()));
        return trxClient.saveFamiliar(familiar).getData();
    }
}
