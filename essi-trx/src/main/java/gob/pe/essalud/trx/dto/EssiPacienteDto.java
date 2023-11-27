package gob.pe.essalud.trx.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EssiPacienteDto {
    @SerializedName("apeMaterno")
    @Expose
    private String apeMaterno;
    @SerializedName("apePaterno")
    @Expose
    private String apePaterno;
    @SerializedName("autogenerado")
    @Expose
    private String autogenerado;
    @SerializedName("autogeneradoTitular")
    @Expose
    private String autogeneradoTitular;
    @SerializedName("codAutorizacionIntercPrestacional")
    @Expose
    private String codAutorizacionIntercPrestacional;
    @SerializedName("codCentro")
    @Expose
    private String codCentro;
    @SerializedName("codEstadoCivil")
    @Expose
    private String codEstadoCivil;
    @SerializedName("codEstadoConsulta")
    @Expose
    private String codEstadoConsulta;
    @SerializedName("codGenero")
    @Expose
    private String codGenero;
    @SerializedName("codIndicadorAtencion")
    @Expose
    private String codIndicadorAtencion;
    @SerializedName("codTipoAcreditacionComplemen")
    @Expose
    private String codTipoAcreditacionComplemen;
    @SerializedName("codTipoDoc")
    @Expose
    private String codTipoDoc;
    @SerializedName("codTipoSeguroSGH")
    @Expose
    private String codTipoSeguroSGH;
    @SerializedName("codUbigeoDomicilio")
    @Expose
    private String codUbigeoDomicilio;
    @SerializedName("codUbigeoNacimiento")
    @Expose
    private String codUbigeoNacimiento;
    @SerializedName("desCentro")
    @Expose
    private String desCentro;
    @SerializedName("desEstadoConsulta")
    @Expose
    private String desEstadoConsulta;
    @SerializedName("desTipoSeg")
    @Expose
    private String desTipoSeg;
    @SerializedName("desUbiDom")
    @Expose
    private String desUbiDom;
    @SerializedName("desUbiNac")
    @Expose
    private String desUbiNac;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fecIns")
    @Expose
    private String fecIns;
    @SerializedName("fecNac")
    @Expose
    private String fecNac;
    @SerializedName("fecVigDesde")
    @Expose
    private String fecVigDesde;
    @SerializedName("fecVigHasta")
    @Expose
    private String fecVigHasta;
    @SerializedName("flagIndicadorActivo")
    @Expose
    private String flagIndicadorActivo;
    @SerializedName("indicadorAfectacionEssaludVida")
    @Expose
    private String indicadorAfectacionEssaludVida;
    @SerializedName("indicadorAfectacionSCTR")
    @Expose
    private String indicadorAfectacionSCTR;
    @SerializedName("indicadorCodigoLatencia")
    @Expose
    private String indicadorCodigoLatencia;
    @SerializedName("indicadorVinculoFam")
    @Expose
    private String indicadorVinculoFam;
    @SerializedName("motivoAcreditacionComplemen")
    @Expose
    private String motivoAcreditacionComplemen;
    @SerializedName("nomCalle")
    @Expose
    private String nomCalle;
    @SerializedName("nombUrbanizacion")
    @Expose
    private String nombUrbanizacion;
    @SerializedName("numCelular")
    @Expose
    private String numCelular;
    @SerializedName("numCorrelativoAcreditacionComplemen")
    @Expose
    private String numCorrelativoAcreditacionComplemen;
    @SerializedName("numDoc")
    @Expose
    private String numDoc;
    @SerializedName("numInteriorLote")
    @Expose
    private String numInteriorLote;
    @SerializedName("numManzanaKm")
    @Expose
    private String numManzanaKm;
    @SerializedName("numTelefono")
    @Expose
    private String numTelefono;
    @SerializedName("priNombre")
    @Expose
    private String priNombre;
    @SerializedName("rucEmpl")
    @Expose
    private String rucEmpl;
    @SerializedName("segNombre")
    @Expose
    private String segNombre;
    @SerializedName("vDataFam")
    @Expose
    private List<Object> vDataFam;
}