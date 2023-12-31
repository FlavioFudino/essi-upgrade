package gob.pe.essalud.trx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.dto.CentroDto;
import gob.pe.essalud.trx.repository.CentroMyRepository;
import gob.pe.essalud.trx.service.CentroService;

@Service
public class CentroServiceImpl implements CentroService {

    private final CentroMyRepository centroMyRepository;

    @Autowired
    public CentroServiceImpl(CentroMyRepository centroMyRepository) {
        this.centroMyRepository = centroMyRepository;
    }

    @Override
    public boolean checkApplyCita(String oriCenAsiCod, String cenAsiCod) {
        oriCenAsiCod = oriCenAsiCod == null ? "" : oriCenAsiCod;
        Boolean applyCita = centroMyRepository.checkApplyCita(oriCenAsiCod, cenAsiCod);
        return Boolean.TRUE.equals(applyCita);
    }

    @Override
    public CentroDto getCentro(String cenAsiCod) {
        return centroMyRepository.getCentroData(cenAsiCod);
    }

}
