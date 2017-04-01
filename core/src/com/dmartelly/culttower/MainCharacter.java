/*
 */
package com.dmartelly.culttower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Dominick Martelly <Dominick.Martelly@gmail.com>
 */
class MainCharacter extends Sprite {

    Rectangle rect;
    Animation animation;
    private int moveSpeed = 5;
    private TextureRegion[][] animationFrames;

    MainCharacter() {
        super(new Sprite(new Texture("Cult_Main_Front.png")));
        this.setSize(this.getWidth() * 2, this.getHeight() * 2);
        rect = new Rectangle(this.getX(), this.getY(),
                this.getWidth()/2 + 1, this.getHeight() + 1);
        animationFrames = TextureRegion.split(new Texture("Cult_Main_Back.png"), 16, 16);
        animation = new Animation<TextureRegion>(0.2f,animationFrames[0]);

    }

    void MoveUp() {
        this.translateY(moveSpeed);
        this.setTexture(new Texture("Cult_Main_Back.png"));
        animationFrames = TextureRegion.split(new Texture("Cult_Main_Back.png"), 16, 16);
        animation = new Animation<TextureRegion>(0.12f,animationFrames[0]);
    }

    void MoveDown() {
        this.translateY(-moveSpeed);
        this.setTexture(new Texture("Cult_Main_Front.png"));
        animationFrames = TextureRegion.split(new Texture("Cult_Main_Front.png"), 16, 16);
        animation = new Animation<TextureRegion>(0.12f,animationFrames[0]);
    }

    void MoveLeft() {
        this.translateX(-moveSpeed);
        this.setTexture(new Texture("Cult_Main_Left.png"));
        animationFrames = TextureRegion.split(new Texture("Cult_Main_Left.png"), 16, 16);
        animation = new Animation<TextureRegion>(0.15f,animationFrames[0]);
    }

    void MoveRight() {
        this.translateX(moveSpeed);
        this.setTexture(new Texture("Cult_Main_Right.png"));
        animationFrames = TextureRegion.split(new Texture("Cult_Main_Right.png"), 16, 16);
        animation = new Animation<TextureRegion>(0.15f,animationFrames[0]);
    }

    int getMoveSpeed() {
        return moveSpeed;
    }
    
    void setRect(float x, float y) {
        rect.setPosition(this.getX(), this.getY());
    }
}
