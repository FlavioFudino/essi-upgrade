package gob.pe.essalud.client.dto.trx;

import lombok.Data;

@Data
public class CitasEmitidasResponseDto implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String citActMedNum;
    private String citApeNomPac;
    private String citAutoGenCod;
    private String citCenAsiCod;
    private String citCenAsiDes;
    private String citConCod;
    private String citConDes;
    private String citEstCita;
    private String citFecha;
    private String citHora;
    private String citNumDocIden;
    private String citOriCenAsiCod;
    private String citProfAsis;
    private String citServHosCod;
    private String citServHosDes;
    private String citTipAtencion;
    private String citTipDocIden;
    private String codEstCtrlSeg;
    private String desEstCtrlSeg;
    private String dirCoordLatitud;
    private String dirCoordLongitud;
    private String direccionAten;

    private boolean tieneRespuesta; //true = existe registro de accion en nuestro lado
    private boolean indConfirmado; //true = Cita Confirmada / false = Cita Cancelada
    private boolean puedeConfirmar;
    private boolean puedeCancelar;

    public boolean isCitaAnulada() {
        return this.citEstCita.equals("ANULADA");
    }

    public boolean isCitaPendiente() {
        return this.citEstCita.equals("PENDIENTE");
    }
}
