package com.zalmoxis3d.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

/**
 * Created by Petre Popescu on 29-Jan-17.
 */
public class Stage {
    private ModelBatch modelBatch;
    private PerspectiveCamera cam;
    private DisplayObject mainDisplayObject;
    private Environment environment;

    public static float FIELD_OF_VIEW = 100;

    public Stage(DisplayObject mainDisplayObject) {
        this.mainDisplayObject = mainDisplayObject;

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(FIELD_OF_VIEW, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 0f, 0f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    public void zoomIn(int amount) {
        cam.position.set(cam.position.x + amount, 0, 0);
    }
    public void zoomOut(int amount) {
        zoomIn(-amount);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainDisplayObject.render(this.modelBatch);
    }

    public void dispose() {
        mainDisplayObject.dispose();
    }
}
