package gob.pe.essalud.client.service;

public interface CaptchaService {
    void process(String captchaToken, String action);
    void incrementClientAttempts();
    void success();
}
