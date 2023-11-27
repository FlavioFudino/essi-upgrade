package gob.pe.essalud.client.dto.consulta;

import lombok.Data;

@Data
public class RecetaMedicamentoDto {
    private String cantidadSol;
    private String codDiagRel;
    private String codEstAteMed;
    private String codMedicamento;
    private String codUnidadAten;
    private String desEstAteMed;
    private String desMedicamento;
    private int diasDuraCan;
    private String dosifiIndica;
    private String fechaProgEnt;
    private String flagAtenMedFar;
    private String indicacionesAdi;
    private String razSocDirecFarExt;
}
