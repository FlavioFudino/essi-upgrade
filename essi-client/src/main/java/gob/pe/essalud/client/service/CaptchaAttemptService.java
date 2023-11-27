package gob.pe.essalud.client.service;

public interface CaptchaAttemptService {

    void reCaptchaSucceeded(String key);

    void reCaptchaFailed(String key);

    boolean isBlocked(String key);
}
