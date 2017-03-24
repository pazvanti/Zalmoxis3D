package com.zalmoxis3d.display.text;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.zalmoxis3d.display.DisplayObject;
import com.zalmoxis3d.display.Stage;

/**
 * Created by Petre Popescu on 02-Feb-17.
 */
public class Text3D extends DisplayObject {
    private String text;
    private float scale = 1;
    private Color color = Color.WHITE;
    private int width;
    private int height;

    public Text3D(String text) {
        this.text = text;
        initDecal();
    }

    public Text3D(String text, Color color) {
        this.text = text;
        this.color = color;
        initDecal();
    }

    public Text3D(String text, Color color, float scale) {
        this.text = text;
        this.color = color;
        this.scale = scale;
        initDecal();
    }

    public void setText(String text) {
        this.text = text;
        initDecal();
    }

    public String getText() {
        return this.text;
    }

    public void setColor(Color color) {
        this.color = color;
        initDecal();
    }

    public Color getColor() {
        return this.color;
    }

    public void setScale(float scale) {
        this.scale = scale;
        initDecal();
    }

    public float getScale() {
        return this.scale;
    }

    private Texture getTexture() {
        BitmapFont font = new BitmapFont();
        font.setColor(this.color);
        font.getData().scale(this.scale);

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);

        //TODO: Width is not correct. need to see why
        this.width = (int)Math.ceil(glyphLayout.width);
        this.height = (int)Math.ceil(glyphLayout.height);

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        SpriteBatch batch = new SpriteBatch(1);
        batch.setProjectionMatrix(Stage.getInstance().getCamera().combined);

        fbo.begin();
        batch.begin();
        font.draw(batch, this.text, 0, 0);
        batch.end();
        fbo.end();

        Texture texture = fbo.getColorBufferTexture();

        //TODO: We may save memory if we dispose the FBO and Batch. However, if we do this the texture is disposed as well.
        /*fbo.dispose();
        batch.dispose();
        font.dispose();*/

        return texture;
    }

    private void initDecal() {
        Texture textTexture = getTexture();

        this.decal = Decal.newDecal(this.width, this.height, new TextureRegion(textTexture, this.width, this.height));
        this.decal.setPosition(this.globalCoordinates.x, this.globalCoordinates.y, this.globalCoordinates.z);
    }
}
