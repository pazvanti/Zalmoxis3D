package com.zalmoxis3d.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

/**
 * Created by Petre Popescu on 29-Jan-17.
 */
public class Stage {
    private ModelBatch modelBatch;
    private DecalBatch decalBatch;
    private SpriteBatch spriteBatch;
    private OrthographicCamera cam;
    private DisplayObject mainDisplayObject;
    private Environment environment;

    public static float FIELD_OF_VIEW = 100;

    public Stage(DisplayObject mainDisplayObject) {
        this.mainDisplayObject = mainDisplayObject;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 8f, 8f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        modelBatch = new ModelBatch();
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        spriteBatch = new SpriteBatch(100);
    }

    public void zoomIn(int amount) {
        cam.position.set(cam.position.x + amount, 0, 0);
    }
    public void zoomOut(int amount) {
        zoomIn(-amount);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        mainDisplayObject.render(this.modelBatch, this.decalBatch);
        modelBatch.end();
        decalBatch.flush();
    }

    public void dispose() {
        mainDisplayObject.dispose();
    }
}
