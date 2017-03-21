package com.zalmoxis3d.display;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.zalmoxis3d.display.intersections.BoundingBoxIntersection;
import com.zalmoxis3d.display.intersections.IIntersectionChecker;
import com.zalmoxis3d.event.EventDispatcher;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Petre Popescu on 29-Jan-17.
 *
 * The main abstraction used for managing displayable items. It is capable of holding all the needed information
 * for rendering it using a Model Batch (provided by the Stage).
 * It extends EventDispatcher since this Display object is capable of having events attached to it.
 */
public class DisplayObject extends EventDispatcher {
    private Vector3 coordinates = new Vector3(0, 0, 0);
    private Vector3 globalCoordinates = new Vector3(0, 0, 0);
    private boolean visible = true;
    private DisplayObject parent = null;
    private Set<DisplayObject> children = new LinkedHashSet<DisplayObject>();
    protected ModelInstance modelInstance = null;
    protected Sprite sprite = null;
    protected Decal decal = null;
    protected Model model = null;
    protected BoundingBox boundingBox = null;
    private float alpha = 1;
    protected IIntersectionChecker intersectionChecker = new BoundingBoxIntersection();

    public DisplayObject() {
        super();
    }

    /**
     * Ads another DisplayObject as a child to the current DisplayObject
     * @param child DisplayObject that will be added
     */
    public void addChild(DisplayObject child) {
        if (child == null) return;
        child.setParent(this);
        children.add(child);
        this.boundingBox = null;
    }

    /**
     * Sets the parent displayObject to the current DisplayObject
     * @param parent DisplayObject that will be marked as the parent. Needed for calculating offsets in the global
     *               world and rotation points
     */
    protected void setParent(DisplayObject parent) {
        this.parent = parent;

        // We need to recalculate our global coordinates based on the parent's global coordinates
        calculateGlobalCoordinates();
    }

    /**
     * Retrieve the BoudingBox of the current DisplayObject as well as all children that it currently contains.
     * The BoudingBoxed is CACHED since it is a costly operation. Any changes to the children structure (adding/removing)
     * will invalidate the bounding box
     * @return The BoundingBox of the current DisplayObject
     */
    public BoundingBox getBounds() {
        // The bounding box is cached since calculating it is costly. Only recalculate it if it has been invalidated
        if (this.boundingBox == null) {
            this.boundingBox = new BoundingBox();
            if (this.modelInstance != null) {
                this.boundingBox = this.modelInstance.calculateBoundingBox(this.boundingBox).mul(this.modelInstance.transform);
            }
            for (DisplayObject child:this.children) {
                BoundingBox childBoundingBox = child.getBounds();
                this.boundingBox = this.boundingBox.ext(boundingBox);
            }
        }
        return this.boundingBox;
    }

    /**
     * Checks if the current object intersects with another object. The BoundingBoxes of each object is used
     * @param other DisplayObject against to check intersection
     * @return true if the the objects intersect, false otherwise
     */
    public boolean intersects(DisplayObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    /**
     * Remove a child from the current DisplayObject. The child is also disposed
     * @param child DisplayObject that will be removed
     * @return true if the child has been removed, false otherwise.
     */
    public boolean removeChild(DisplayObject child) {
        return removeChild(child, true);
    }

    /**
     * Remove a child from the current DisplayObject
     * @param child DisplayObject that will be removed
     * @param dispose A boolean value indicating if the child should be disposed after removal
     * @return true if the child has been removed, false otherwise.
     */
    public boolean removeChild(DisplayObject child, boolean dispose) {
        if (child == null) return false;
        if (children.contains(child)) {
            children.remove(child);
            if (dispose) {
                child.dispose();
            }

            // We invalidate the bounding box since we removed a child
            boundingBox = null;
            return true;
        }
        return false;
    }

    /**
     * Returns the number of children that this display object has. Does not count children of children
     * @return Number of children
     */
    public int numChildren() {
        return this.children.size();
    }

    /**
     * Sets the alpha of the current DisplayObject. All children will inherit the same alpha for consistency
     * @param alpha Alpha value with values between 0 (completely transparent) and 1 (no transparency at all)
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
        if (this.modelInstance != null) {
            BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            blendingAttribute.opacity = alpha;
            Material material = this.modelInstance.materials.get(0);
            material.set(blendingAttribute);
        }
        for (DisplayObject child:children) {
            child.setAlpha(alpha);
        }
    }

    /**
     * Retrieve the current alpha value of the DisplayObject
     * @return Alpha value between 0 and 1
     */
    public float getAlpha() {
        return this.alpha;
    }

    /**
     * Set the DisplayObjects visibility. If set to false, it will not get rendered and will not trigger any interaction
     * events such as OnClick or OnTouch. Children inherit visibility value from the parent
     * @param visible The visibility value
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        for (DisplayObject child:children) {
            child.setVisible(visible);
        }
    }

    /**
     * Returns the visibility value of the current DisplayObject. This method does not check if the item is actually in
     * the camera's view rectangle
     * @return True if the DisplayObject is visible, false otherwise
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * This method is used to calculate the global coordinates for this display object. The object is only logically part
     * of another display object, however the actual model needs to be rendered on the screen at certain coordinates.
     * This method calculates those coordinates using it's parent position and it's logical position inside the parent.
     */
    protected void calculateGlobalCoordinates() {
        this.globalCoordinates = new Vector3(this.coordinates).add(this.parent.globalCoordinates);
        if (this.modelInstance != null) {
            this.modelInstance.transform.set(this.globalCoordinates, new Quaternion());
        }
        if (this.decal != null) {
            this.decal.setPosition(this.globalCoordinates);
        }
        for (DisplayObject child:this.children) {
            child.calculateGlobalCoordinates();
        }
    }

    /**
     * Translate(move) the object
     * @param x Translation amount on the X axis
     * @param y Translation amount on the Y axis
     * @param z Translation amount on the Z axis
     */
    public void translate(float x, float y, float z) {
        coordinates.add(x, y, z);
        globalCoordinates.add(x, y, z);
        for (DisplayObject child:this.children) {
            child.calculateGlobalCoordinates();
        }
    }

    /**
     * Translate(move) the object
     * @param offset The amount to translate the Display Object
     */
    public void translate(Vector3 offset) {
        coordinates.add(offset);
        globalCoordinates.add(offset);
        for (DisplayObject child:this.children) {
            child.calculateGlobalCoordinates();
        }
    }

    /**
     * Retrieve the objects coordinates inside it's parent
     * @return The coordinates
     */
    public Vector3 getCoordinates() {
        return this.coordinates;
    }

    /**
     * Retrieve the display object's X coordinate inside it's parent
     * @return Display Object's X coordinate value
     */
    public float getX() {
        return this.coordinates.x;
    }

    /**
     * Retrieve the display object's Y coordinate inside it's parent
     * @return Display Object's Y coordinate value
     */
    public float getY() {
        return this.coordinates.y;
    }

    /**
     * Retrieve the display object's Z coordinate inside it's parent
     * @return Display Object's Z coordinate value
     */
    public float getZ() {
        return this.coordinates.z;
    }

    /**
     * Dispose the current display object and all it's children
     */
    public void dispose() {
        this.removeEventListeners();
        for (DisplayObject child:children) {
            child.dispose();
        }
        if (this.model != null) {
            this.model.dispose();
        }
    }

    /**
     * Render the current Display Object using a Model Batch and trigger rendering for all it's children
     * @param modelBatch
     */
    protected void render(ModelBatch modelBatch, DecalBatch decalBatch, SpriteBatch spriteBatch) {
        if (this.modelInstance != null) {
            modelBatch.render(this.modelInstance);
        }
        if (this.decal != null) {
            decalBatch.add(decal);
        }
        if (this.sprite != null) {
            spriteBatch.draw(this.sprite.getTexture(), this.globalCoordinates.x, this.globalCoordinates.y);
        }
        for(DisplayObject child:children) {
            if (child.isVisible()) {
                child.render(modelBatch, decalBatch, spriteBatch);
            }
        }
    }

    public float rayCollision(Ray ray) {
        //TODO: Check intersection with the Decal and Sprite if it is present
        if (this.modelInstance == null) return Float.MAX_VALUE;
        Vector3 position = new Vector3();
        this.modelInstance.transform.getTranslation(position);
        float dist2 = ray.origin.dst2(position);
        if (intersectionChecker.intersects(ray, this)) return dist2;
        return Float.MAX_VALUE;
    }
}
