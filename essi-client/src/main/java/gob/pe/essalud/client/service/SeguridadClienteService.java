package gob.pe.essalud.client.service;

public interface SeguridadClienteService {
    void verificarAcceso();

    int incrementarIntento(String modulo,
                           String refCampo,
                           String refValor,
                           String refCampo2,
                           String refValor2,
                           String refNota);

    void reiniciarIntentos();
    int obtenerIntentosRestantes();

    String getClientIP();
}
