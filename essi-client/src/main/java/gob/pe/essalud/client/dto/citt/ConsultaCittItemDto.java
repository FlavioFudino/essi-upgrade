package gob.pe.essalud.client.dto.citt;

import lombok.Data;

@Data
public class ConsultaCittItemDto extends gob.pe.essalud.client.base.BaseDto {
    private String ruc;
    private String nitt;
    private String contingencia;
    private String fechaini;
    private String fechafin;
    private String estado;
    private String observacion;
    private String usuariocreacion;
    private String citt;
    private String tipodocu;
    private String numdocu;
    private String fechacreacion;
    private String horacreacion;
    private String asegurado;
}
