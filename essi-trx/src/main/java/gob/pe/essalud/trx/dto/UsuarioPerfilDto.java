package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class UsuarioPerfilDto {
    private Integer idPersona;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String fechaNacimiento;
    private Boolean indPadomi;
    private Boolean indApplyPadomi;
    private ContactoDto contacto;
    private DireccionDto direccion;
}