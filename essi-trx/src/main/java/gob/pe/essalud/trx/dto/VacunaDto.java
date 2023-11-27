package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class VacunaDto {
    private String id;
    private String redEssalud;
    private String region;
    private String provincia;
    private String distrito;
    private String ipress;
    private String direccion;
    private String latitud;
    private String longitud;
    private String fecha1;
    private String fecha2;
    private String fecha3;
    private String fecha4;
}
