package gob.pe.essalud.client.dto;

import lombok.Data;

@Data
public class NotificacionMassage extends gob.pe.essalud.client.base.BaseDto {
    private String title;
    private String body;
    private String token;
}
