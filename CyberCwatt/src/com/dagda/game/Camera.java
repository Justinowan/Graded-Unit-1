package com.dagda.game;


import com.dagda.objects.GameObject;


public class Camera {
    
    private float x, y;
    
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }
    
    public void tick(GameObject object){
        x += ((object.getX() - x) - 1920/2) * 0.05f;
        y += ((object.getY() - y) - 1080/2) * 0.05f;
        
        if(x <= 0) x= 0;
        if(x >= 135) x = 135;
        if(y <= 0) y = 0;
        if(y >= 1096) y = 1096;
        
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    
    
}
