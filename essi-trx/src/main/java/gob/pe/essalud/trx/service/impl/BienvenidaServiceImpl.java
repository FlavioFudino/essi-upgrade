package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.dto.BienvenidaListDto;
import gob.pe.essalud.trx.repository.BienvenidaMyRepository;
import gob.pe.essalud.trx.service.BienvenidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BienvenidaServiceImpl implements BienvenidaService {

    private final BienvenidaMyRepository bienvenidaMyRepository;

    @Autowired
    public BienvenidaServiceImpl(BienvenidaMyRepository bienvenidaMyRepository) {
        this.bienvenidaMyRepository = bienvenidaMyRepository;
    }

    @Override
    public List<BienvenidaListDto> getBienvenidaList() {
        return bienvenidaMyRepository.getBienvenidaList();
    }

}
