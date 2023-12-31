package gob.pe.essalud.trx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.dto.ConsultaUsuariosDto;
import gob.pe.essalud.trx.repository.ConsultaMyRepository;
import gob.pe.essalud.trx.service.ConsultaService;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaMyRepository consultaMyRepository;

    @Autowired
    public ConsultaServiceImpl(ConsultaMyRepository consultaMyRepository) {
        this.consultaMyRepository = consultaMyRepository;
    }

    @Override
    public List<ConsultaUsuariosDto> consultarUsuarios() {
        return consultaMyRepository.getConsultaUsuarios();
    }
}
