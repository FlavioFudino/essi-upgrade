package gob.pe.essalud.trx.dto.publics;

import lombok.Data;

@Data
public class IpressCitaDto {
    private int id;
    private String nombre;
    private String direccion;
    private String longitud;
    private String latitud;
    private String numero;
}
