package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class RegistrarSolCittRespDto extends gob.pe.essalud.client.base.BaseDto {
    private String codError;
    private String desError;
    private RegistrarSolCittRegDatosDto solRegDatos;
}