package gob.pe.essalud.client.dto.consulta;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsultaEmergenciaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cenAsi;
    private String fechaAdmision;
    private String desPriAten;
    private String obserAdmision;
}
