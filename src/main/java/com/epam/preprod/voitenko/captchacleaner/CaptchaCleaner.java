package com.epam.preprod.voitenko.captchacleaner;

import com.epam.preprod.voitenko.repository.CaptchaRepository;

public class CaptchaCleaner implements Runnable {
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
                System.out.println("InterruptedException has occurred in CaptchaCleaner");
            }
        }
    }
}