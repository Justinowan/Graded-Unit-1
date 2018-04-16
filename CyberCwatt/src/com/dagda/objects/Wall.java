package com.dagda.objects;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Wall extends GameObject{
    
    private BufferedImage wall_image;

    public Wall(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        
        wall_image = ss.grabImage(5, 1, 64, 64);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        //g.setColor(Color.black);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(wall_image, x, y, null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }
    
}
