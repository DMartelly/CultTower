/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmartelly.culttower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Dominick Martelly
 */
public class Objects extends Sprite{
    
    Rectangle rect;

    Objects(String internalPath) {
        super(new Texture(internalPath));
        rect = new Rectangle(this.getX(), this.getY(),
                    this.getWidth(), this.getHeight());
    }
    
    void multRect(int i) {
        this.rect.setSize(this.getWidth() * i, this.getHeight() * i);
    }

    void setRect(float x, float y) {
        this.rect.setPosition(x, y);
    }
    
    

    public Rectangle getRect() {
        return rect;
    }
    
    
    
}
