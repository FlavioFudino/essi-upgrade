package gob.pe.essalud.client.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import gob.pe.essalud.client.config.CaptchaConfig;
import gob.pe.essalud.client.service.CaptchaAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaAttemptServiceImpl implements CaptchaAttemptService {

    private final CaptchaConfig captchaConfig;

    private LoadingCache<String, Integer> attemptsCache;

    @Autowired
    public CaptchaAttemptServiceImpl(CaptchaConfig captchaConfig) {
        super();

        this.captchaConfig = captchaConfig;

        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(captchaConfig.getBlockTimeInMinutes(), TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public void reCaptchaSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    @Override
    public void reCaptchaFailed(String key) {
        incrementCurrentAttempts(key);
    }

    @Override
    public boolean isBlocked(String key) {
        //return false; //se validara a nivel de base de datos
        int currentAttempts = getCurrentAttempts(key);
        return currentAttempts >= captchaConfig.getMaxAttempts();
    }

    private int getCurrentAttempts(String key) {
        return attemptsCache.getUnchecked(key);
    }

    private int incrementCurrentAttempts(String key) {
        int currentAttempts = (getCurrentAttempts(key) + 1);
        attemptsCache.put(key, currentAttempts);
        int attemptsLeft = (captchaConfig.getMaxAttempts() - currentAttempts);
        return attemptsLeft;
    }
}
