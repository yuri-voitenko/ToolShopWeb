package com.epam.preprod.voitenko.captchacleaner;

import com.epam.preprod.voitenko.repository.CaptchaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.INTERRUPTED_EXCEPTION;

public class CaptchaCleaner implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(CaptchaCleaner.class);
    private long timeout;

    public CaptchaCleaner(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long millis = (long) ((timeout * 1000) * 1.6);
                Thread.sleep(millis);
                CaptchaRepository.deleteOutdatedCaptcha(timeout);
            } catch (InterruptedException e) {
                LOGGER.error(INTERRUPTED_EXCEPTION, e);
            }
        }
    }
}