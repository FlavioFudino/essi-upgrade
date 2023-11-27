package gob.pe.essalud.client.dto.contacto;

import lombok.Data;

@Data
public class ContactoCercanoRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private PersonaCercanaDto personaCercano;
}
