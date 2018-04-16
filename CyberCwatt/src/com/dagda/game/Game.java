package com.dagda.game;


import com.dagda.inputs.MouseInput;
import com.dagda.inputs.KeyInput;
import com.dagda.objects.Enemy;
import com.dagda.objects.Ammo;
import com.dagda.objects.Handler;
import com.dagda.objects.Wall;
import com.dagda.objects.Table;
import com.dagda.objects.ID;
import com.dagda.objects.SpriteSheet;
import com.dagda.objects.Player;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 1L;
    
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;
    
    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    
    public int ammo = 10;
    public int hp = 100;
    
    public Game(){
        new Window(1920,1080, "CyberCwatt", this);
        start();
        
        handler = new Handler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler));
        
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/Level1Edit.png");
        sprite_sheet = loader.loadImage("/spritesheettest.png");
        
        ss = new SpriteSheet(sprite_sheet);
        
        floor = ss.grabImage(1, 2, 64, 64);
        
        this.addMouseListener(new MouseInput(handler, camera, this, ss));
        
        loadLevel(level);
    }
    
    private void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    
    private void stop(){
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 100000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;
   
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
        }
    
    
    
    public void tick(){
       
        for(int i =0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                camera.tick(handler.object.get(i));
            }
        }
        
        handler.tick();
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        g.setColor(Color.GRAY);
        g.fillRect(0,0,1920,1080);
        
        
        
        g2d.translate(-camera.getX(), -camera.getY());
        
        //for(int xx = 0; xx < 30*72; xx+=64){
        //    for(int yy = 0; yy < 30*72; yy += 64){
        //        g.drawImage(floor, xx, yy, null);
        //    }
        //}

        handler.render(g);
        
        
        
        g2d.translate(camera.getX(), camera.getY());
        
        g.setColor(Color.gray);
        g.fillRect(5, 5, 200, 32);
        g.setColor(Color.green);
        g.fillRect(5, 5, hp*2, 32);
        g.setColor(Color.black);
        g.drawRect(5, 5, 200, 32);
        
        g.setColor(Color.white);
        g.drawString("Ammo: " + ammo, 5, 50);
        
        g.dispose();
        bs.show();
    }
    
    private void loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        
        for(int xx = 0; xx < w; xx++){
            for(int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx,yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                if(red == 255 && blue == 0)
                    handler.addObject(new Wall(xx*32, yy*32, ID.Wall, ss));
                
                if(blue == 255 && red == 0)
                    handler.addObject(new Player(xx*32, yy*32, ID.Player, ss, handler, this ));
                
                if(green == 255 && blue == 0)
                    handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, ss, handler ));
                
                if(red == 255 && blue == 255)
                    handler.addObject(new Ammo(xx*32, yy*32, ID.Ammo, ss));
                
                if(red == 255 && blue == 255 && green == 255)
                    handler.addObject(new Table(xx*32, yy*32, ID.Table, ss));
            }
        }
    }
                
     
    
    public static void main(String[] args) {
        new Game();
    }
}
