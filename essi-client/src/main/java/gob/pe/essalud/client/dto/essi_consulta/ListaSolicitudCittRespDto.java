package gob.pe.essalud.client.dto.essi_consulta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListaSolicitudCittRespDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codError;
    private String desError;

    @JsonProperty("ListaSolDatos")
    private List<ListaSolicitudCittItemRespDto> listaSolDatos;
}