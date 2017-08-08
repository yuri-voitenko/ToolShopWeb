package com.epam.preprod.voitenko.captcha;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Random;

public class Captcha {
    private static final long LOWER_RANGE = 1_000_000_000L;
    private static final long UPPER_RANGE = 9_999_999_999L;

    private static final int NUMBER_DIGITS = 10;
    private static final int IMAGE_HEIGHT = 40;
    private static final int IMAGE_WIDTH = 250;

    private int id;
    private long secretCode;
    private Image image;
    private LocalDateTime creationDate;

    private static int lastId = 0;

    public Captcha() {
        this.id = ++lastId;
        this.secretCode = generateSecretCode();
        this.image = generateImage();
        this.creationDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public long getSecretCode() {
        return secretCode;
    }

    public Image getImage() {
        return image;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Captcha captcha = (Captcha) o;
        if (id != captcha.id) {
            return false;
        }
        if (secretCode != captcha.secretCode) {
            return false;
        }
        if (image != null ? !image.equals(captcha.image) : captcha.image != null) {
            return false;
        }
        return creationDate != null ? creationDate.equals(captcha.creationDate) : captcha.creationDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (secretCode ^ (secretCode >>> 32));
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    public void updateImage() {
        this.secretCode = generateSecretCode();
        this.image = generateImage();
    }

    private long generateSecretCode() {
        Random random = new Random();
        return LOWER_RANGE + (long) (random.nextDouble() * (UPPER_RANGE - LOWER_RANGE));
    }

    private Image generateImage() {
        Random random = new Random();
        Font fntStyle = new Font("Arial", Font.BOLD, 30);
        String sImageCode = String.valueOf(this.secretCode);

        /*
        * TYPE_INT_RGB - does not support transpatency, TYPE_INT_ARGB - support transpatency
        */
        BufferedImage biImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) biImage.getGraphics();

        // Draw background rectangle and noisey filled round rectangles
        int iCircle = 15;
        g2dImage.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        for (int i = 0; i < iCircle; i++) {
            g2dImage.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            int iRadius = (int) (random.nextDouble() * IMAGE_HEIGHT / 2.0);
            int iX = (int) (random.nextDouble() * IMAGE_WIDTH - iRadius);
            int iY = (int) (random.nextDouble() * IMAGE_HEIGHT - iRadius);
            g2dImage.fillRoundRect(iX, iY, iRadius * 2, iRadius * 2, 100, 100);
        }
        g2dImage.setFont(fntStyle);
        for (int i = 0; i < NUMBER_DIGITS; i++) {
            g2dImage.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            if (i % 2 == 0) {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 24);
            } else {
                g2dImage.drawString(sImageCode.substring(i, i + 1), 25 * i, 35);
            }
        }
        return biImage;
    }
}