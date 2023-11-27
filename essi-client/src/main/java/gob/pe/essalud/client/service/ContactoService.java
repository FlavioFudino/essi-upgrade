package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.contacto.ContactoCercanoRequestDto;

import java.util.Map;

public interface ContactoService {
    ContactoCercanoRequestDto save(ContactoCercanoRequestDto model);

    ContactoCercanoRequestDto delete(ContactoCercanoRequestDto model);

    Map findAll(String tipoDocumento, String numeroDocumento);
}
