package gob.pe.essalud.trx.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.SeguridadClienteDetalleRequestDto;
import gob.pe.essalud.trx.dto.SeguridadClienteDetalleResponseDto;
import gob.pe.essalud.trx.jpa.model.SeguridadClienteDetalleModel;
import gob.pe.essalud.trx.jpa.repository.SeguridadClienteDetalleRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.SeguridadClienteDetalleService;

@Service
public class SeguridadClienteDetalleServiceImpl implements SeguridadClienteDetalleService {

    private final SeguridadClienteDetalleRepository seguridadClienteDetalleRepository;
    private final ParametroRepository parametroRepository;

    @Autowired
    public SeguridadClienteDetalleServiceImpl(
            SeguridadClienteDetalleRepository seguridadClienteDetalleRepository,
            ParametroRepository parametroRepository) {

        this.seguridadClienteDetalleRepository = seguridadClienteDetalleRepository;
        this.parametroRepository = parametroRepository;
    }

    @Override
    @Transactional
    public SeguridadClienteDetalleResponseDto save(SeguridadClienteDetalleRequestDto seguridadClienteDetalleRequestDto) {
        Date currentDate = parametroRepository.getFecha();

        SeguridadClienteDetalleModel model = Util.objectToObject(SeguridadClienteDetalleModel.class, seguridadClienteDetalleRequestDto);
        model.setFecha(currentDate);
        seguridadClienteDetalleRepository.save(model);

        SeguridadClienteDetalleResponseDto response = Util.objectToObject(SeguridadClienteDetalleResponseDto.class, model);
        return response;
    }
}
