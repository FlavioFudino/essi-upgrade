package gob.pe.essalud.client.dto.gestion_atencion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class GaRegistrarColaRequestDto implements Serializable {
    private String tipoDoc;
    private String numDoc;
    private String codRed;

    @JsonIgnore
    private String codCentro;
}
