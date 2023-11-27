package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class PersonaReniec extends gob.pe.essalud.client.base.BaseDto {
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String nombres;

    private String fechaNacimiento; //devolveremos null
    private String codSexo; //devolveremos null
}
