package gob.pe.essalud.trx.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.ParametroService;

@Service
public class ParametroServiceImpl extends BaseService implements ParametroService {

    private final ParametroRepository parametroRepository;

    private static final String KEY_OPERADOR_MOVIL = "operadorMovil";
    private static final String KEY_TIPO_VIA = "tipoVia";
    private static final String KEY_PARENTESCO = "parentesco";

    @Autowired
    public ParametroServiceImpl(ParametroRepository parametroRepository) {
        this.parametroRepository = parametroRepository;
    }

    @Override
    public HashMap<String, List<ParametroDto>> getParams(String filters) {
        HashMap<String, List<ParametroDto>> response = new HashMap<>();

        if (filters.contains(KEY_OPERADOR_MOVIL)) {
            List<ParametroDto> statesCase = parametroRepository.getListOperador();
            response.put(KEY_OPERADOR_MOVIL, statesCase);
        }

        if (filters.contains(KEY_TIPO_VIA)) {
            List<ParametroDto> statesCase = parametroRepository.getListTipoVia();
            response.put(KEY_TIPO_VIA, statesCase);
        }

        if (filters.contains(KEY_PARENTESCO)) {
            List<ParametroDto> statesCase = parametroRepository.getListParentesco();
            response.put(KEY_PARENTESCO, statesCase);
        }

        return response;
    }

    @Override
    public Date getCurrentDate() {
        return parametroRepository.getFecha();
    }
}
