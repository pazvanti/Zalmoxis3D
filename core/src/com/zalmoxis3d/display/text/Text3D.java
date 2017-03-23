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
import com.badlogic.gdx.math.Matrix4;
import com.zalmoxis3d.display.DisplayObject;

/**
 * Created by Petre Popescu on 02-Feb-17.
 */
public class Text3D extends DisplayObject {
    private String text;
    public Text3D(String text, float scale) {
        this.text = text;
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().scale(scale);

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);

        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int)Math.ceil(glyphLayout.width), (int)Math.ceil(glyphLayout.height), false);

        SpriteBatch batch = new SpriteBatch(100);
        Matrix4 m = new Matrix4();
        m.setToOrtho2D(0, 0, fbo.getWidth(), fbo.getHeight());
        batch.setProjectionMatrix(m);

        fbo.begin();
        batch.begin();
        font.draw(batch, this.text, 0, 0);
        batch.end();
        fbo.end();

        Texture textTexture = fbo.getColorBufferTexture();

        this.decal = Decal.newDecal(glyphLayout.width, glyphLayout.height, new TextureRegion(textTexture));
        this.decal.setPosition(0, 0, 0);
    }
}
