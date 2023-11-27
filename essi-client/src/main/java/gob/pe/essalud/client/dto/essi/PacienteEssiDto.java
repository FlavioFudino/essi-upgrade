package gob.pe.essalud.client.dto.essi;

import gob.pe.essalud.client.base.BaseDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PacienteEssiDto extends BaseDto {

    private String apeMaterno;
    private String apePaterno;
    private String autogenerado;
    private String autogeneradoTitular;
    private String codAutorizacionIntercPrestacional;
    private String codCentro;
    private String codEstadoCivil;
    private String codEstadoConsulta;
    private String codGenero;
    private String codIndicadorAtencion;
    private String codTipoAcreditacionComplemen;
    private String codTipoDoc;
    private String codTipoSeguroSGH;
    private String codUbigeoDomicilio;
    private String codUbigeoNacimiento;
    private String desCentro;
    private String desEstadoConsulta;
    private String desTipoSeg;
    private String desUbiDom;
    private String desUbiNac;
    private String email;
    private String fecIns;
    private String fecNac;
    private String fecVigDesde;
    private String fecVigHasta;
    private String flagIndicadorActivo;
    private String indicadorAfectacionEssaludVida;
    private String indicadorAfectacionSCTR;
    private String indicadorCodigoLatencia;
    private String indicadorVinculoFam;
    private String motivoAcreditacionComplemen;
    private String nomCalle;
    private String nombUrbanizacion;
    private String numCelular;
    private String numCorrelativoAcreditacionComplemen;
    private String numDoc;
    private String numInteriorLote;
    private String numManzanaKm;
    private String numTelefono;
    private String priNombre;
    private String rucEmpl;
    private String segNombre;
    private String[] vDataFam;

    private boolean indicadorCita;
    private boolean indPedirCita;
    private int tipoAlerta = 0;
    private boolean indTeleUrgencia;

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();

        if (priNombre != null)
            sb.append(priNombre.trim());

        if (segNombre != null)
            sb.append(" ").append(segNombre.trim());

        if (apePaterno != null)
            sb.append(" ").append(apePaterno.trim());

        if (apeMaterno != null)
            sb.append(" ").append(apeMaterno.trim());

        return sb.toString();
    }
}
