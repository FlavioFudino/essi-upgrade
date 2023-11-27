package gob.pe.essalud.client.dto.citt;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ConsultaCittRespDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;
    private String mensaje;
    private String timestamp;
    private List<ConsultaCittItemDto> data;
}