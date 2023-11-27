package gob.pe.essalud.trx.jpa.model.key;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class CitaKey implements Serializable {

    private String actMedNum;
    private String cenAsiCod;
    private String oriCenAsi;
    private boolean indConfirmado;

    public CitaKey() {}

    public CitaKey(String actMedNum, String cenAsiCod, String oriCenAsi, boolean indConfirmado) {
        this.actMedNum = actMedNum;
        this.cenAsiCod = cenAsiCod;
        this.oriCenAsi = oriCenAsi;
        this.indConfirmado = indConfirmado;
    }
}
