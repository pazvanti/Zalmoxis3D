package com.zalmoxis3d.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.event.EventDispatcher;
import com.zalmoxis3d.event.EventHandler;
import com.zalmoxis3d.event.events.KeyEvent;
import com.zalmoxis3d.event.events.TouchEvent;

import java.util.Set;

/**
 * Created by Petre Popescu on 29-Jan-17.
 */
public class Stage implements Screen, InputProcessor{
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
        cam.far = FIELD_OF_VIEW;
        cam.update();

        modelBatch = new ModelBatch();
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        spriteBatch = new SpriteBatch(100);

        Gdx.input.setInputProcessor(this);
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        mainDisplayObject.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(KeyEvent.KEY_DOWN);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return false;

        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            eventDispatcher.dispatchEvents(KeyEvent.KEY_DOWN, new KeyEvent(keycode));
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(KeyEvent.KEY_UP);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return false;

        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            eventDispatcher.dispatchEvents(KeyEvent.KEY_UP, new KeyEvent(keycode));
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(TouchEvent.TOUCH_DOWN);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return false;

        Ray collisionRay = cam.getPickRay(screenX, screenY);
        float dist = Float.MAX_VALUE;
        EventDispatcher eventDispatcherTriggered = null;
        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            if (eventDispatcher instanceof DisplayObject) {
                DisplayObject displayObject = (DisplayObject)eventDispatcher;
                float calculatedDist = displayObject.rayCollision(collisionRay);
                if (dist > calculatedDist) {
                    dist = calculatedDist;
                    eventDispatcherTriggered = eventDispatcher;
                }
            }
        }

        if (eventDispatcherTriggered != null) {
            eventDispatcherTriggered.dispatchEvents(TouchEvent.TOUCH_DOWN, new TouchEvent(button));
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private Set<EventDispatcher> getItemsWithEvent(String eventType) {
        EventHandler eventHandler = EventHandler.getInstance();
        Set<EventDispatcher> itemsWithEvent = eventHandler.getItemsTriggered(eventType);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return null;
        return itemsWithEvent;
    }
}
