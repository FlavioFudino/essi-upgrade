package gob.pe.essalud.trx.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.FarmaciaDto;
import gob.pe.essalud.trx.jpa.model.FarmaciaModel;
import gob.pe.essalud.trx.jpa.repository.FarmaciaRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.FarmaciaService;

@Service
public class FarmaciaServiceImpl extends BaseService implements FarmaciaService {
    private final FarmaciaRepository farmaciaRepository;
    private final ParametroRepository parametroRepository;

    @Autowired
    public FarmaciaServiceImpl(FarmaciaRepository farmaciaRepository, ParametroRepository parametroRepository) {
        this.farmaciaRepository = farmaciaRepository;
        this.parametroRepository = parametroRepository;
    }

    @Override
    public FarmaciaDto save(FarmaciaDto model) {
        Date fechaServer = parametroRepository.getFecha();
        FarmaciaModel farmaciaModel = Util.objectToObject(FarmaciaModel.class, model);
        farmaciaModel.setDateCreate(fechaServer);
        return Util.objectToObject(FarmaciaDto.class, farmaciaRepository.save(farmaciaModel));
    }
}
