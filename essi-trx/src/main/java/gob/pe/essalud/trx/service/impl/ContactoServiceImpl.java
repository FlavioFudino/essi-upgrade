package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.ContactoDto;
import gob.pe.essalud.trx.jpa.model.ContactoModel;
import gob.pe.essalud.trx.jpa.repository.ContactoRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactoServiceImpl extends BaseService implements ContactoService {
    private final ContactoRepository contactoRepository;
    private final ParametroRepository parametroRepository;

    @Autowired
    public ContactoServiceImpl(ContactoRepository contactoRepository, ParametroRepository parametroRepository) {
        this.contactoRepository = contactoRepository;
        this.parametroRepository = parametroRepository;
    }

    @Override
    public ContactoDto save(ContactoDto model) {
        Date fechaServer = parametroRepository.getFecha();
        ContactoModel contactoModel = Util.objectToObject(ContactoModel.class, model);
        contactoModel.setDateCreate(fechaServer);
        return Util.objectToObject(ContactoDto.class, contactoRepository.save(contactoModel));
    }
}
