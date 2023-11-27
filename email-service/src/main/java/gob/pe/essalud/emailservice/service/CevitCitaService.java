package gob.pe.essalud.emailservice.service;

import gob.pe.essalud.emailservice.dto.cevitCitas.RegistrarCevitCitaDto;

public interface CevitCitaService {
    boolean registrarCita(RegistrarCevitCitaDto requestDto);
}
