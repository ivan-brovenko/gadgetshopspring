package com.epam.istore.captcha;


import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Component
public class CaptchaGenerator {
    private static final int COUNT_OF_NUMBERS_IN_CAPTCHA = 4;
    private static final int FONT_SIZE = 18;
    private static final int SECOND_CODE_POINT_X_COORDINATE = 0;
    private static final int SECOND_CODE_POINT_Y_COORDINATE = 21;
    private static final int FIRST_CODE_POINT_Y_COORDINATE = 0;
    private static final int FIRST_CODE_POINT_X_COORDINATE = 0;
    private static final int NUMBER_OF_CHARACTERS_TO_DRAW = 1;
    private static final int RANDOM_COEFFICIENT = 5;
    private static final int MIN_X_RANDOM_VALUE = 35;
    private static final int MIN_Y_RANDOM_VALUE = 20;
    private final static String FONT_GEORGIA = "Georgia";
    private final static int WIDTH = 200;
    private final static int HEIGHT = 42;
    private final static Color SOFT_GRAY = new Color(154, 154, 154);
    private static final boolean IS_CYCLIC = true;

    public Captcha generateCaptcha(long time){
        char data[] = RandomStringUtils.randomNumeric(COUNT_OF_NUMBERS_IN_CAPTCHA).toLowerCase().toCharArray();
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        configureGraphics2D(graphics2D);
        String captcha = String.copyValueOf(data);
        drawRandomChars(data, graphics2D);
        return new Captcha(bufferedImage, captcha, time);
    }

    private void drawRandomChars(char[] data, Graphics2D graphics2D) {
        Random random = new Random();
        int x = 0;
        int y;
        for (int i = 0; i < data.length; i++) {
            x += MIN_X_RANDOM_VALUE + (Math.abs(random.nextInt()) % RANDOM_COEFFICIENT);
            y = MIN_Y_RANDOM_VALUE + Math.abs(random.nextInt()) % RANDOM_COEFFICIENT;
            graphics2D.drawChars(data, i, NUMBER_OF_CHARACTERS_TO_DRAW, x, y);
        }
        graphics2D.dispose();
    }

    private void configureGraphics2D(Graphics2D graphics2D) {
        Font font = new Font(FONT_GEORGIA, Font.BOLD, FONT_SIZE);
        graphics2D.setFont(font);
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(renderingHints);
        GradientPaint gradientPaint = new GradientPaint(FIRST_CODE_POINT_X_COORDINATE, FIRST_CODE_POINT_Y_COORDINATE,
                SOFT_GRAY, SECOND_CODE_POINT_X_COORDINATE, SECOND_CODE_POINT_Y_COORDINATE, SOFT_GRAY, IS_CYCLIC);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fillRect(FIRST_CODE_POINT_X_COORDINATE, FIRST_CODE_POINT_Y_COORDINATE, WIDTH, HEIGHT);
        graphics2D.setColor(Color.white);
    }
}
