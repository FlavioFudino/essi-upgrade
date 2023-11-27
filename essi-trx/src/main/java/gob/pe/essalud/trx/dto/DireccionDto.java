package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class DireccionDto {
    private Integer idDireccion;
    private Integer idPersona;
    private String idFarmacia;
    private String descripcion;
    private String referencia;
    private String ubigeo;
    private String nroLatitud;
    private String nroLongitud;
    private String idTipoVia;
}
