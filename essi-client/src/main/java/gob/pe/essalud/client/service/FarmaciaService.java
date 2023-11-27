package gob.pe.essalud.client.service;

import gob.pe.essalud.client.common.dto.ResponseSalogFarmaciaDto;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.farmacia.PacienteRequestDto;

import java.util.Map;

public interface FarmaciaService {
    /*Farmacias*/
    Map getListFarmacias(Map paramInput);

    PacienteRequestDto getFarmaciaPaciente(PacienteDto paramInput);

    PacienteRequestDto saveFarmaciaPaciente(PacienteRequestDto paciente);

    ResponseSalogFarmaciaDto getTrackingRecetas(Map paramInput);
}
