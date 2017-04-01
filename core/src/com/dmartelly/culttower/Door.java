/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmartelly.culttower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Dominick Martelly
 */
class Door extends Sprite {

    Rectangle rect;
    private ArrayList text;

    Door(float x, float y) {
        super(new Sprite(new Texture("Door.png")));
        this.setPosition(x, y);
        this.setSize(this.getWidth() * 2, this.getHeight() * 2);
        this.rect = new Rectangle(this.getX(), this.getY(),
                this.getWidth(), this.getHeight());
        text = new ArrayList();
    }

    void addText(String words) {
        text.add(words);
    }

    Object getText(int i) {
        return text.get(i);
    }

}
