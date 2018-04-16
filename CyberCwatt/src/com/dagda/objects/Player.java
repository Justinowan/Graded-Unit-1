package com.dagda.objects;


import com.dagda.game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Player extends GameObject {
    
    Handler handler;
    Game game;
    
    
    private BufferedImage player_image;

    public Player(int x, int y, ID id, SpriteSheet ss, Handler handler, Game game) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        
        player_image = ss.grabImage(1, 1, 64, 64);
    }

    public void tick() {
        x += velX;
        y += velY;
        
        collision();
        
        if(handler.isUp()) velY = -1;
        else if(!handler.isDown()) velY = 0;
        
        if(handler.isDown()) velY = 1;
        else if(!handler.isUp()) velY = 0;
        
        if(handler.isRight()) velX = 1;
        else if(!handler.isLeft()) velX = 0;
        
        if(handler.isLeft()) velX = -1;
        else if(!handler.isRight()) velX = 0;
        
    }
    
    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.Wall || tempObject.getId() == ID.Table){
                
                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * -1;
                    y += velY * -1;
                }
                
            }
            
            if(tempObject.getId() == ID.Ammo){
                
                if(getBounds().intersects(tempObject.getBounds())){
                    game.ammo += 5;
                    handler.removeObject(tempObject);
                }
                
            }
            
            if(tempObject.getId() == ID.Enemy){
                
                if(getBounds().intersects(tempObject.getBounds())){
                    game.hp --;
                }
                
            }
            
        }
    }

    public void render(Graphics g) {
        //g.setColor(Color.BLUE);
        //g.fillRect(x, y, 32, 48);
        g.drawImage(player_image, x-15, y,null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 50);
        
    }
    
}
