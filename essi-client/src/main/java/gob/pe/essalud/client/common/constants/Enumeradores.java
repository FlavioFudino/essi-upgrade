package gob.pe.essalud.client.common.constants;

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