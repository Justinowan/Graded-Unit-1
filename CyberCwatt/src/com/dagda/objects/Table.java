
package com.dagda.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Table extends GameObject {
    private BufferedImage table_image;

    public Table(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        
        table_image = ss.grabImage(1, 2, 64, 64);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        //g.setColor(Color.black);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(table_image, x, y, null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }
    
}

