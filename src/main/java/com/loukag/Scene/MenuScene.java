package com.loukag.Scene;

import com.loukag.Utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuScene extends Scene{

    private BufferedImage logo;
    private Font font;

    @Override
    public void init() {
        logo = Sprite.load("/textures/ui/logo.png");
        try{
            font = Font.createFont(Font.TRUETYPE_FONT, MenuScene.class.getResourceAsStream("/fonts/Perfect DOS VGA 437.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeUpdate() {

    }

    @Override
    public void beforeRender(Graphics2D g) {
        this.setFont(new Font(font.getName(), Font.PLAIN, 32));
        g.drawImage(logo, (1250-128*3)/2, 32, 128*3, 64*3, null);

        g.setColor(Color.WHITE);
        g.drawString("P L A Y", (1250-128*3)/2, 64*5);
        g.drawString("S E T T I N G S", (1250-128*3)/2, (64*6));
        g.drawString("Q U I T", (1250-128*3)/2 , (64*7));
        g.drawString("2 0 2 3   L O U K A G", (1250-128*3)/2, (64*8));
    }
}
