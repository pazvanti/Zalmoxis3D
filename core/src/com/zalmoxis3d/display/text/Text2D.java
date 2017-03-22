package com.zalmoxis3d.display.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by petre.popescu on 2017-03-22.
 */
public class Text2D extends DisplayObject {
    private String text = null;
    private BitmapFont font = new BitmapFont();
    private Color color = Color.WHITE;

    public Text2D(String text) {
        this.text = text;
        font.setColor(color);
    }

    public Text2D(String text, Color color) {
        this.color = color;
        this.text = text;
        this.font.setColor(color);
    }

    public Text2D(String text, BitmapFont font) {
        this.font = font;
        this.text = text;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void render(ModelBatch modelBatch, Shader defaultShader, DecalBatch decalBatch, SpriteBatch spriteBatch) {
        font.draw(spriteBatch, this.text, this.globalCoordinates.x, this.globalCoordinates.y);
    }

    @Override
    public void addChild(DisplayObject child) {
        throw new UnsupportedOperationException("Can't add a child to a text");
    }

    @Override
    public boolean removeChild(DisplayObject child) {
        throw new UnsupportedOperationException("Can't remove a child from a text");
    }

    @Override
    public boolean removeChild(DisplayObject child, boolean dispose) {
        throw new UnsupportedOperationException("Can't remove a child from a text");
    }
}
