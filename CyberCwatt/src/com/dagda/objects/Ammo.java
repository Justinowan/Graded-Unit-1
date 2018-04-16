package com.dagda.objects;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Ammo extends GameObject {
    
    private BufferedImage ammo_image;

    public Ammo(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        
        ammo_image = ss.grabImage(3, 1, 64, 64);
        
        
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        //g.setColor(Color.MAGENTA);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(ammo_image,x-17, y-17, null);
        
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    
}
