package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConsultaUsuariosDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fecha;
    private int edad;
    private String codRed;
    private String red;
    private String codCentro;
    private String centro;
    private int cantRegistrados;

}
