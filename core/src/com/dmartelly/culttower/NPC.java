/*
 */
package com.dmartelly.culttower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 * Need to get rid of this
 *
 * @author Dominick Martelly <Dominick.Martelly@gmail.com>
 */
class NPC extends Sprite {

    private final Texture npcFront = new Texture("Cult_NPC_Front.png");
    private final Texture npcRight = new Texture("Cult_NPC_Right.png");
    private final Texture npcLeft = new Texture("Cult_NPC_Left.png");
    private final Texture npcRear = new Texture("Cult_NPC_Back.png");

    private final float startX, startY;
    private final long waitTime = 2200; //in Milliseconds
    private final long stepInterval = 20; //in Milliseconds
    Rectangle rect;
    private long currentTime = System.currentTimeMillis(), pastTime;
    private boolean start = true;
    private ArrayList<String> Dictate;

    NPC(float x, float y) {
        super(new Sprite(new Texture("Cult_NPC_Front.png")));
        this.setPosition(x, y);
        this.startX = x;
        this.startY = y;
        this.rect = new Rectangle(this.getX(), this.getY(),
                this.getWidth(), this.getHeight());
        this.Dictate = new ArrayList<String>();
    }

    NPC(String textureName, int x, int y) {
        super(new Sprite(new Texture(textureName)));
        this.setPosition(x, y);
        this.startX = x;
        this.startY = y;
        rect = new Rectangle(this.getX(), this.getY(),
                this.getWidth() * 4, this.getHeight() * 4);
        this.Dictate = new ArrayList<String>();
    }

    void addDictate(String s) {
        this.Dictate.add(s);
    }

    String getDictate(int i) {
        return Dictate.get(i);
    }
    
    void pacingLtoR() {
        pastTime = System.currentTimeMillis();

        if (start) {
            if (getX() - startX >= 128) // Walk length in px
            {
                this.setTexture(npcFront);
                currentTime = System.currentTimeMillis() + waitTime;
                start = false;
            }
            if (pastTime - currentTime > stepInterval) {
                this.setTexture(npcRight);
                this.currentTime = System.currentTimeMillis();
                this.setX(getX() + 4);
            }
        } else {
            if (pastTime - currentTime > stepInterval) {
                this.setTexture(npcLeft);
                this.currentTime = System.currentTimeMillis();
                this.setX(getX() - 4);
            }
            if (getX() - startX <= 0) {
                this.setTexture(npcFront);
                currentTime = System.currentTimeMillis() + waitTime;
                start = true;
            }
        }
    }

    void pacingUtoD() {
        pastTime = System.currentTimeMillis();

        if (start) {
            if (getY() - startY >= 96) // Walk length in px
            {
                this.setTexture(npcRear);
                currentTime = System.currentTimeMillis() + waitTime;
                start = false;
            }
            if (pastTime - currentTime > stepInterval) {
                this.setTexture(npcRear);
                this.currentTime = System.currentTimeMillis();
                this.setY(getY() + 4);
            }
        } else {
            if (pastTime - currentTime > stepInterval) {
                this.setTexture(npcFront);
                this.currentTime = System.currentTimeMillis();
                this.setY(getY() - 4);
            }
            if (getY() - startY <= 0) {
                this.setTexture(npcFront);
                currentTime = System.currentTimeMillis() + waitTime;
                start = true;
            }
        }
    }
    void setRect(float x, float y) {
        rect.setPosition(this.getX(), this.getY());
    }

}
