package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.CentroDto;

public interface CentroService {
    boolean checkApplyCita(String oriCenAsiCod,
                           String cenAsiCod);
    CentroDto getCentro(String cenAsiCod);
}
