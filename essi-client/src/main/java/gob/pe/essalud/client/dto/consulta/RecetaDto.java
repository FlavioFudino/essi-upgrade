package gob.pe.essalud.client.dto.consulta;

import lombok.Data;

import java.util.List;

@Data
public class RecetaDto {
    private String codActHos;
    private String codAreaHos;
    private String codAutGen;
    private String codCenAsis;
    private String codEmergencia;
    private String codEspHos;
    private String codEstAtenRece;
    private String codOriCenAsi;
    private String codReceta;
    private String codSubActHos;
    private String codTopicoEmer;
    private String codUsuCre;
    private String desActHos;
    private String desAreaHos;
    private String desCenQui;
    private String desCentroAsistencial;
    private String desEmergencia;
    private String desEspHos;
    private String desEstAtenRece;
    private String desFarEss;
    private String desSubActHos;
    private String desTopicoEmer;
    private String direcCasAsis;
    private String edadPacAten;
    private String fecVigencia;
    private String fechaCre;
    private String fechaEmision;
    private String flagAtencionFar;
    private String numActMedOri;
    private String numHisCli;
    private String profAsiApeNom;
    private String profNumDocIde;
    private String profTipDocIde;
    private List<RecetaMedicamentoDto> recetaMedicamentos;
}
