package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class AseguradoRequestDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private ContactoDto contacto = new ContactoDto();
    private DireccionDto direccion = new DireccionDto();
}
