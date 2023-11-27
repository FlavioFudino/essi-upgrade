package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.SeguridadClienteDto;
import gob.pe.essalud.trx.dto.SeguridadClienteRequestDto;
import gob.pe.essalud.trx.jpa.model.SeguridadClienteModel;
import gob.pe.essalud.trx.jpa.repository.SeguridadClienteRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.SeguridadClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class SeguridadClienteServiceImpl implements SeguridadClienteService {

    private final SeguridadClienteRepository seguridadClienteRepository;
    private final ParametroRepository parametroRepository;

    @Autowired
    public SeguridadClienteServiceImpl(
            SeguridadClienteRepository seguridadClienteRepository,
            ParametroRepository parametroRepository) {

        this.seguridadClienteRepository = seguridadClienteRepository;
        this.parametroRepository = parametroRepository;
    }

    @Override
    public SeguridadClienteDto findByIpCliente(String ipCliente) {
        SeguridadClienteModel seguridadClienteModel = seguridadClienteRepository.findByIpCliente(ipCliente);
        SeguridadClienteDto seguridadClienteDto = Util.objectToObject(SeguridadClienteDto.class, seguridadClienteModel);
        return seguridadClienteDto;
    }

    @Override
    @Transactional
    public SeguridadClienteDto save(SeguridadClienteRequestDto seguridadClienteDto) {
        Date currentDate = parametroRepository.getFecha();
        SeguridadClienteModel seguridadClienteModel = seguridadClienteRepository.findByIpCliente(seguridadClienteDto.getIpCliente());

        boolean isSeguridadClienteRegistered = seguridadClienteModel != null;
        if (!isSeguridadClienteRegistered) {
            //insert
            seguridadClienteModel = Util.objectToObject(SeguridadClienteModel.class, seguridadClienteDto);
            seguridadClienteModel.setDateCreate(currentDate);
        } else {
            //else
            seguridadClienteModel.setIntentos(seguridadClienteDto.getIntentos());
            seguridadClienteModel.setFechaUltimoIntento(seguridadClienteDto.getFechaUltimoIntento());
            seguridadClienteModel.setBloqueado(seguridadClienteDto.getBloqueado());
            seguridadClienteModel.setFechaInicioBloqueo(seguridadClienteDto.getFechaInicioBloqueo());
            seguridadClienteModel.setFechaFinBloqueo(seguridadClienteDto.getFechaFinBloqueo());
            seguridadClienteModel.setDateModify(currentDate);
        }

        seguridadClienteRepository.save(seguridadClienteModel);
        SeguridadClienteDto response = Util.objectToObject(SeguridadClienteDto.class, seguridadClienteModel);
        return response;
    }

}
