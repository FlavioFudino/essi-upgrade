package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class ConsultaUsuariosDto extends gob.pe.essalud.client.base.BaseDto {
    private String fecha;
    private int edad;
    private String codRed;
    private String red;
    private String codCentro;
    private String centro;
    private int cantRegistrados;
}
