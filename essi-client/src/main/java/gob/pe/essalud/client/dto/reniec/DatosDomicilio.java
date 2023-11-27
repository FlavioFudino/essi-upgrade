package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class DatosDomicilio extends gob.pe.essalud.client.base.BaseDto {
    private String codigoUbigeoContinenteDomicilio;
    private String codigoUbigeoDepartamento;
    private String codigoUbigeoDistrito;
    private String codigoUbigeoLocalidad;
    private String codigoUbigeoPaisDomicilio;
    private String codigoUbigeoProvincia;
    private String continenteDomicilio;
    private String departamento;
    private String direccion;
    private String distrito;
    private String localidad;
    private String paisDomicilio;
    private String provincia;
}
