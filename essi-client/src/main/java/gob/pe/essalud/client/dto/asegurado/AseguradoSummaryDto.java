package gob.pe.essalud.client.dto.asegurado;

import lombok.Data;

@Data
public class AseguradoSummaryDto extends gob.pe.essalud.client.base.BaseDto {
    private String codTipoDoc;
    private String numDocumento;
    private String apePaterno;
    private String apeMaterno;
    private String nombres;
    private String codSexo;
    private String fecNacimiento;
    private String direccion;
    private String ubigeo;
    private String celular;
    private String telefono;
}
