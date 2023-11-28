package gob.pe.essalud.client.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.dto.familiar.FamiliarPacienteRequestDto;
import gob.pe.essalud.client.service.FamiliarService;

@Service
public class FamiliarServiceImpl extends BaseService implements FamiliarService {

    private final TrxClient trxClient;

    @Autowired
    public FamiliarServiceImpl(TrxClient trxClient) {
        this.trxClient = trxClient;
    }

    @Override
    public Map get(String tipoDocumento, String numeroDocumento) {
        this.loggerDebug("Inicio get familiar", formatterHour.format(new Date()));
        return trxClient.getFamiliar(tipoDocumento, numeroDocumento);
    }

    @Override
    public FamiliarPacienteRequestDto save(FamiliarPacienteRequestDto familiar) {
        this.loggerDebug("Inicio save familiar", formatterHour.format(new Date()));
        return trxClient.saveFamiliar(familiar).getData();
    }
}
