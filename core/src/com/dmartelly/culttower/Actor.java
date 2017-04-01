/*
 */
package com.dmartelly.culttower;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Dominick Martelly <Dominick.Martelly@gmail.com>
 */
public abstract class Actor {
    private int x, y;
    private Texture texture;
    private int width, height;

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Actor(String s, int x, int y) {
        this.texture = new Texture(s);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture t) {
        this.texture = t;
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public int getWidth() {
        return this.texture.getWidth();
    }
}
