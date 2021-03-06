package com.zalmoxis3d.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.event.EventDispatcher;
import com.zalmoxis3d.event.EventHandler;
import com.zalmoxis3d.event.events.Event;
import com.zalmoxis3d.event.events.KeyEvent;
import com.zalmoxis3d.event.events.StageEvent;
import com.zalmoxis3d.event.events.TouchEvent;

import java.util.Set;

/**
 * Created by Petre Popescu on 29-Jan-17.
 */
public class Stage implements Screen, InputProcessor{
    private ModelBatch modelBatch;
    private DecalBatch decalBatch;
    private SpriteBatch spriteBatch;
    private Camera cam;
    private DisplayObject mainDisplayObject;
    private Environment environment = null;
    private Shader shader = null;

    public static float FIELD_OF_VIEW = 100;

    private boolean hasBeenInitialized = false;

    public static final Stage INSTANCE = new Stage();

    public static Stage getInstance() {
        return INSTANCE;
    }

    private Stage() {
    }

    public void init(DisplayObject mainDisplayObject) {
        this.mainDisplayObject = mainDisplayObject;

        if (this.environment == null) {
            environment = new Environment();
            environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
            environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        }

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 8f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = FIELD_OF_VIEW;
        cam.update();

        modelBatch = new ModelBatch();
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam));
        spriteBatch = new SpriteBatch(100);

        if (this.shader == null) {
            this.shader = new BaseShader() {
                @Override
                public void init() {
                }

                @Override
                public int compareTo(Shader other) {
                    return 0;
                }

                @Override
                public boolean canRender(Renderable instance) {
                    return false;
                }
            };
        }
        shader.init();

        Gdx.input.setInputProcessor(this);
    }

    /**
     * Return the camera that is being used
     * @return
     */
    public Camera getCamera() {
        return this.cam;
    }

    /**
     * Zoom in and out on the stage. The camera position is changed.
     * @param amount
     */
    public void zoomIn(int amount) {
        cam.position.set(cam.position.x + amount, 0, 0);
    }

    /**
     * Zoom out on the stage. The camera position is changed.
     * @param amount
     */
    public void zoomOut(int amount) {
        zoomIn(-amount);
    }

    /**
     * Set the shader for the stage. This will be used as the default shader for objects that do not have their own
     * @param shader
     */
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    /**
     * The method that gets called each time a new frame needs to render.
     * This method will trigger the Enter Frame event
     */
    public void render() {
        // A new frame must be rendered. Trigger the ENTER_FRAME event
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(Event.ENTER_FRAME);
        if (itemsWithEvent != null && !itemsWithEvent.isEmpty()) {
            for (EventDispatcher eventDispatcher : itemsWithEvent) {
                eventDispatcher.dispatchEvents(Event.ENTER_FRAME, new Event(Event.ENTER_FRAME));
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        spriteBatch.begin();
        mainDisplayObject.render(this.modelBatch, this.shader, this.decalBatch, this.spriteBatch);
        spriteBatch.end();
        modelBatch.end();
        decalBatch.flush();
    }

    @Override
    public void show() {
        triggerStageEvent(StageEvent.SHOW);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        triggerStageEvent(StageEvent.RESIZE);
    }

    @Override
    public void pause() {
        triggerStageEvent(StageEvent.PAUSE);
    }

    @Override
    public void resume() {
        triggerStageEvent(StageEvent.RESUME);
    }

    @Override
    public void hide() {
        triggerStageEvent(StageEvent.HIDE);
    }

    private void triggerStageEvent(String eventType) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(eventType);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return;

        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            eventDispatcher.dispatchEvents(eventType, new StageEvent(this, eventType));
        }
    }

    public void dispose() {
        mainDisplayObject.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return keyEvent(keycode, KeyEvent.KEY_DOWN);
    }

    @Override
    public boolean keyUp(int keycode) {
        return keyEvent(keycode, KeyEvent.KEY_UP);
    }

    private boolean keyEvent(int keycode, String type) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(type);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return false;

        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            eventDispatcher.dispatchEvents(type, new KeyEvent(keycode, type));
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return touchEvent(screenX, screenY, pointer, button, TouchEvent.TOUCH_DOWN);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return touchEvent(screenX, screenY, pointer, button, TouchEvent.TOUCH_UP);
    }

    /**
     * The screen gets clicked or touched. Detect what object is in the touch zone and trigger it's on touch event
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @param type
     * @return
     */
    private boolean touchEvent(int screenX, int screenY, int pointer, int button, String type) {
        Set<EventDispatcher> itemsWithEvent = getItemsWithEvent(type);
        if (itemsWithEvent == null || itemsWithEvent.isEmpty()) return false;

        Ray collisionRay = cam.getPickRay(screenX, screenY);
        float dist = Float.MAX_VALUE;
        float depth2D = Float.MAX_VALUE;
        EventDispatcher eventDispatcherTriggered = null;
        for(EventDispatcher eventDispatcher:itemsWithEvent) {
            if (eventDispatcher instanceof DisplayObject) {
                DisplayObject displayObject = (DisplayObject)eventDispatcher;
                float calculatedDist = displayObject.rayCollision(collisionRay);
                if (dist > calculatedDist) {
                    dist = calculatedDist;
                    eventDispatcherTriggered = eventDispatcher;
                }

                float touched2DObjectDepth = displayObject.clickCollision(new Vector2(screenX, screenY));
                if (depth2D > touched2DObjectDepth) {
                    depth2D = touched2DObjectDepth;
                    eventDispatcherTriggered = eventDispatcher;
                }
            }
        }

        if (eventDispatcherTriggered != null) {
            eventDispatcherTriggered.dispatchEvents(type, new TouchEvent(button, screenX, screenY, type));
        }

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
