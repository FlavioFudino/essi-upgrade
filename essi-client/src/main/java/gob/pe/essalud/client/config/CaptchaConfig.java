package gob.pe.essalud.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@Getter
@Setter
public class CaptchaConfig {
    private boolean enabled;
    private String site;
    private String secret;
    private float threshold;
    private int maxAttempts;
    private long blockTimeInMinutes;
}
