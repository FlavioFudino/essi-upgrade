package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiItemSolicitudOperacionDto extends gob.pe.essalud.client.base.BaseDto {
    private String codOperacion;
    private String desOperacion;
}
