package gob.pe.essalud.client.service;

import java.util.Map;

public interface UbigeoService {

    Map[] getDepartaments();

    Map[] getProvinces(String idDepartamento);

    Map[] getDistricts(String idDepartamento, String idProvincia);

    Map getUbigeo(String codigo);
}
