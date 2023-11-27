package gob.pe.essalud.client.dto.medico;

import gob.pe.essalud.client.dto.medico.listaSolicitudExamen.SolicitudExamenDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListaSolicitudExamenResponseDto implements Serializable {
    public static final long serialVersionUID = 1L;

    private String codRpta;
    private String desRpta;
    private List<SolicitudExamenDto> listaSolExa;
}
