package gob.pe.essalud.client.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CentroDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String oriCenAsi;
    private String cenAsiCod;
    private String descripcion;
    private String codRed;
    private boolean indPadomi;
    private boolean indPedirCita;
    private boolean indTeleUrgencia;
}
