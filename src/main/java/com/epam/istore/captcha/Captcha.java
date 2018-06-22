package com.epam.istore.captcha;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class Captcha {
    private BufferedImage image;
    private String captchaNumber;
    private LocalDateTime expiredTime;

    public Captcha(BufferedImage image, String captchaNumber, long expiredInterval) {
        this.image = image;
        this.captchaNumber = captchaNumber;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredInterval);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCaptchaNumber() {
        return captchaNumber;
    }

    public void setCaptchaNumber(String captchaNumber) {
        this.captchaNumber = captchaNumber;
    }

    public LocalDateTime getCreationTime() {
        return expiredTime;
    }

    public boolean isExpired() {
        return expiredTime.isBefore(LocalDateTime.now());
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.expiredTime = creationTime;
    }
}
