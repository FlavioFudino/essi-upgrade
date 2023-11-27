package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.medico.*;

import java.util.Map;

public interface MedicoService {
    Map login(MedicoLoginDto paramInput);

    Map programacion(ProgramacionRequestDto paramInput);

    Map pacientesCitados(PacienteCitadosRequestDto paramInput);

    ListaSolicitudExamenResponseDto listaSolicitudExamen(ListaSolicitudExamenRequestDto paramInput);
}
