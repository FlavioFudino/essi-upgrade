package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.reniec.PersonaReniec;

public interface ReniecService {
    PersonaReniec getPersona(String numDocumento);
}
