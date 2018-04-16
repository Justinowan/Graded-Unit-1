package com.dagda.objects;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;


public class Enemy extends GameObject {
    
    private Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    int velX = 1;
    int velY = -1;
    
    float count = 0.f;
    
    int objx;
    int objy;
    
    private BufferedImage enemy_image;

    public Enemy(int x, int y, ID id, SpriteSheet ss, Handler handler) {
        super(x, y, id, ss);
        this.handler = handler;
        
        enemy_image = ss.grabImage(2, 1, 64, 64);
    }

    public void tick() {
        
        //increase count to decrease speed
        //if count is decreased too far enemies will move too often and through the walls!
        if(count > 8)
        {
             x += velX;
             y += velY;
             
             //reset the counter
             count = 0;
        }
       
        //increase the counter each frame
        count ++;
        
        choose = r.nextInt(10);
        
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Wall || tempObject.getId() == ID.Table){
                if((getBoundsBig().intersects(tempObject.getBounds()))){//)) && (objx != tempObject.getX() && objy != tempObject.getY())){
                    
                     objx = tempObject.getX();
                     objy = tempObject.getY();
            
            
                    //x += (velX*1) * -1;
                    //y += (velY*1) * -1;
                    //velX = 0;
                    //velY = 0;
                    velX *= -1;
                    velY *= -1;
                }//else if(choose == 0){
               //     velX =(r.nextInt(1 - -1) + -1);
                //    velY =(r.nextInt(1 - -1) + -1);
                //}
            }
            
            if(tempObject.getId() == ID.Bullet){
                if(getBounds().intersects(tempObject.getBounds())){
                hp -= 50;
                handler.removeObject(tempObject);
                }
            }
        }
        
        if(hp <= 0) handler.removeObject(this);
        
        
    }

    public void render(Graphics g) {
        //g.setColor(Color.yellow);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(enemy_image, x-17, y-20,null);
        
        
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    
    public Rectangle getBoundsBig(){
        return new Rectangle(x-16, y-16, 64, 64);
    }
    
}
