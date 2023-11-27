package gob.pe.essalud.client.dto.contacto;

import lombok.Data;

@Data
public class PersonaCercanaDto extends gob.pe.essalud.client.base.BaseDto {
    private Integer idPersonaCercana;
    private Integer idPersona;
    private String nroTelefonoFijo;
    private String nroCelular;
    private String operador;
    private String email;
    private String nombres;
    private String estado;
}
