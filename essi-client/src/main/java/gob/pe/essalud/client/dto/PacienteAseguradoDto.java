package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class PacienteAseguradoDto extends gob.pe.essalud.client.base.BaseDto {
    private String nombres;
    private String apePat;
    private String apeMat;
    private String codCentro;
}