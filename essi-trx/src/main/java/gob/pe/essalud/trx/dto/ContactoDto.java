package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class ContactoDto {
    private Integer idContacto;
    private Integer idPersona;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String operador;
    private String email;
}
