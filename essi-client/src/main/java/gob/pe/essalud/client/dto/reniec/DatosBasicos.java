package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class DatosBasicos extends gob.pe.essalud.client.base.BaseDto {
    private String apellidoCasada;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String caracterVerificacion;
    private String nombres;
    private String numeroDni;
    private Object tipoDocumento;
}
