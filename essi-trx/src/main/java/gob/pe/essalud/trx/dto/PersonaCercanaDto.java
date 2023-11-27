package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class PersonaCercanaDto {
    private Integer idPersonaCercana;
    private Integer idPersona;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String operador;
    private String email;
    private String nombres;
    private String estado;
}
