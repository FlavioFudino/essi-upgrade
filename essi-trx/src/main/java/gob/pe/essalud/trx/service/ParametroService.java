package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.ParametroDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ParametroService {
    HashMap<String, List<ParametroDto>> getParams(String filters);
    Date getCurrentDate();
}