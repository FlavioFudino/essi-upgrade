package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.CentroDto;

public interface CentroService {
    boolean checkApplyCita(String cenAsiCod);
    CentroDto getCentro(String cenAsiCod);
}
