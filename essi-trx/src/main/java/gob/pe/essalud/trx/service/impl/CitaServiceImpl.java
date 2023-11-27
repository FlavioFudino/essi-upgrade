package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.CitaDto;
import gob.pe.essalud.trx.dto.UsuarioDataDto;
import gob.pe.essalud.trx.jpa.model.CitaModel;
import gob.pe.essalud.trx.jpa.repository.CitaRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final ParametroRepository parametroRepository;

    @Autowired
    public CitaServiceImpl(
            CitaRepository citaRepository,
            ParametroRepository parametroRepository) {

        this.citaRepository = citaRepository;
        this.parametroRepository = parametroRepository;
    }

    @Override
    public CitaDto save(CitaDto citaDto) {
        Date currentDate = parametroRepository.getFecha();

        CitaModel citaModel = Util.objectToObject(CitaModel.class, citaDto);
        citaModel.setDateCreate(currentDate);
        citaRepository.save(citaModel);

        CitaDto response = Util.objectToObject(CitaDto.class, citaModel);
        return response;
    }

    @Override
    public List<CitaDto> buscar(UsuarioDataDto paramInput) {
        List<CitaModel> list = citaRepository.findAllByTipoDocIdentAndNumeroDocIdent(paramInput.getCodTipDoc(),paramInput.getNumDoc());
        List<CitaDto> response = Util.listObjectToListObject(CitaDto.class, list);
        return response;
    }
}
