package gob.pe.essalud.trx.common.Enum;

public class Enumeradores {

    private String statusText;

    public enum StatusText {
        OK, ERROR
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}